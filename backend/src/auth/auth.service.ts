import {
  BadRequestException,
  Injectable,
  UnauthorizedException,
} from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { v4 as uuidv4 } from 'uuid';
import * as bcrypt from 'bcrypt';
import { JwtService } from '@nestjs/jwt'; 
import { SignupDto } from './dtos/signup.dto';
import { LoginDto } from './dtos/login.dto';
import { User } from './schemas/user.schema';
import { RefreshToken } from './schemas/refresh-token.schema';

@Injectable()
export class AuthService {
  constructor(
    @InjectModel(User.name) private userModel: Model<User>,
    private readonly jwtService: JwtService, 
    @InjectModel(RefreshToken.name) private RefreshTokenModel:Model<RefreshToken>,
  ) {}

  async signup(signupData: SignupDto) {
    const { email, password, studentId, fullName, profileImage } = signupData;

    // Check if email or studentId is already used
    const existingUser = await this.userModel.findOne({
      $or: [{ email }, { studentId }],
    });

    if (existingUser) {
      if (existingUser.email === email) {
        throw new BadRequestException('Email already in use');
      }
      if (existingUser.studentId === studentId) {
        throw new BadRequestException('Student ID already in use');
      }
    }

    // Hash the password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Create new user
    const newUser = await this.userModel.create({
      email,
      studentId,
      fullName,
      password: hashedPassword,
      profileImage: profileImage ?? '', // use empty string if undefined
    });

    return {
      message: 'Signup successful',
      userId: newUser._id,
    };
  }

  async login(credentials: LoginDto) {
    const { studentId, password } = credentials;

    const user = await this.userModel.findOne({ studentId });
    if (!user) {
      throw new UnauthorizedException('Invalid credentials');
    }

    const passwordMatch = await bcrypt.compare(password, user.password);
    if (!passwordMatch) {
      throw new UnauthorizedException('Invalid credentials');
    }
    const tokens= await this.generateUserTokens(user._id as string);
    return{
     ...tokens,
     userId: user._id,
    }
 

    
  }
  async refreshTokens(refreshToken:String){
    const token = await this.RefreshTokenModel.findOne({
      token:refreshToken,
      expiryDate:{$gte:new Date()},
    });
    if(!token){
      throw new UnauthorizedException("Refresh Token is invalid");
    }
  

  }

  async generateUserTokens(userId: string) {
    const accessToken = this.jwtService.sign({ userId }, { expiresIn: '1h' });
    const refreshToken= uuidv4();
    await this.storeRefreshToken(refreshToken,userId);
    return {
      accessToken,
      refreshToken,
    };
  }

  async storeRefreshToken(token:String,userId){
    const   expiryDate = new Date(); 

  expiryDate.setDate(expiryDate.getDate()+3);
    await this.RefreshTokenModel.updateOne({userId,},{$set:{expiryDate,token}},{
      upsert:true,
    }

    )
  }
}
