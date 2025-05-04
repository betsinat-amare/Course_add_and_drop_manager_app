import {Injectable, CanActivate, ExecutionContext, UnauthorizedException, Logger} from '@nestjs/common'
import { JwtService } from '@nestjs/jwt';
import { Observable } from 'rxjs'

@Injectable()
export class AuthGuard implements CanActivate{
    constructor(private jwtService:JwtService){

    }

  canActivate(
        context:ExecutionContext
    ):boolean | Promise<boolean> | Observable<boolean>{
      const request = context.switchToHttp().getRequest();
      const token = this.extractTokenFromHeader(request);
      if(!token){
        throw new UnauthorizedException('Invalid token');
      }
      try{
        const payload = this.jwtService.verify(token);
        request.userId = payload.userId
      } catch(e){
        Logger.error(e.message)
        throw new UnauthorizedException('Invalid Token')
      }
      return true;
        
    }
    private extractTokenFromHeader(request:Request):string | undefined{
        const authHeader = (request.headers as any).authorization;
        return authHeader?.split(' ')[1];}
        
    }