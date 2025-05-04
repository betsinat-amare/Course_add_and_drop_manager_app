import { Controller, Get, Post, Body, Patch, Param, Delete, UseInterceptors, UploadedFile } from '@nestjs/common';
import { AuthService } from './auth.service';
import { CreateAuthDto } from './dto/create-auth.dto';
import { UpdateAuthDto } from './dto/update-auth.dto';
import{SignupDto} from './dtos/signup.dto'
import { LoginDto } from './dtos/login.dto';
import { FileInterceptor } from '@nestjs/platform-express';
import { RefreshTokenDto } from './dtos/refresh-tokens.dto';


@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}
  @Post('signup')
  @UseInterceptors(FileInterceptor('profileImage')) // MUST match Postman field name
  async signup(
    @UploadedFile() file: Express.Multer.File,
    @Body() signupDto: SignupDto,
  ) {
    if (file) {
      signupDto.profileImage = file.filename; // you can use file.path if needed
    }
    return this.authService.signup(signupDto);
  }
  @Post('login')
  async login(@Body() credentials:LoginDto){
    return this.authService.login(credentials)

  }
  @Post('refresh')
async refreshTokens(@Body() refreshTokenDto: RefreshTokenDto) {
  return this.authService.refreshTokens(refreshTokenDto.refreshToken);
}


  
}
