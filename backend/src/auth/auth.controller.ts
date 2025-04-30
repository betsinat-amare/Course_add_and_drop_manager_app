import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { AuthService } from './auth.service';
import { CreateAuthDto } from './dto/create-auth.dto';
import { UpdateAuthDto } from './dto/update-auth.dto';
import{SignupDto} from './dtos/signup.dto'
import { LoginDto } from './dtos/login.dto';

@Controller('auth')
export class AuthController {
  constructor(private readonly authService: AuthService) {}
  @Post('signup') //auth/signup
  async signUp(@Body() signupData:SignupDto){
    return this.authService.signup(signupData);

  }
  @Post('login')
  async login(@Body() credentials:LoginDto){
    // return this.authService.login(credentials)

  }

  
}
