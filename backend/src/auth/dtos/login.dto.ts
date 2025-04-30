import { IsEmail, IsNotEmpty, IsString, Matches, MinLength } from 'class-validator';
import { Exclude, Transform } from 'class-transformer';
export class LoginDto{
  
    @IsString()
   password: string;

    @IsString()
    studentId: string;
}