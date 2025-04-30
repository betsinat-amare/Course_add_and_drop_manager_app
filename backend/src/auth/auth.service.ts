import { BadRequestException, Injectable, UnauthorizedException } from '@nestjs/common';
import { CreateAuthDto } from './dto/create-auth.dto';
import { UpdateAuthDto } from './dto/update-auth.dto';
import { SignupDto } from './dtos/signup.dto';
import { InjectModel } from '@nestjs/mongoose';
import{User} from './schemas/user.schema'
import {Model} from 'mongoose'
import * as bcrypt from 'bcrypt';
import { LoginDto } from './dtos/login.dto';

@Injectable()
export class AuthService {
  constructor(@InjectModel(User.name) private UserModel:Model<User>){

  }
  async signup(signupData: SignupDto) {
    const { email, password, studentId, fullName,profileImage  } = signupData;
  
    // Check if email is already used
    const emailInUse = await this.UserModel.findOne({ email });
    if (emailInUse) {
      throw new BadRequestException('Email already in use');
    }
  
    // Optional: Check for duplicate studentId
    const idInUse = await this.UserModel.findOne({ studentId });
    if (idInUse) {
      throw new BadRequestException('Student ID already in use');
    }
  
    // Hash the password
    const hashedPassword = await bcrypt.hash(password, 10);
  
    // Create new user
    await this.UserModel.create({
      email,
      studentId,
       profileImage,
      fullName,
     
      password: hashedPassword,
    });
  
    return {
      message: 'Signup successful',
    };
  }
  
  
async login(credentials:LoginDto){
  const {studentId,password}=credentials;
  //find user exists by email
  const user = await this.UserModel.findOne({studentId});
  if(!user){
    throw new UnauthorizedException("Invalid credentials")
  }
  //compare password
  const passwordMatch= await bcrypt.compare(password,user.password);
  if(!passwordMatch){
    throw new UnauthorizedException("Invalid credentials")
  }
  return{
    message:"Login successful"
  }

  }
  
}
