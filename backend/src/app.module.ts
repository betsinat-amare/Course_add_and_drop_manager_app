import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { AuthModule } from './auth/auth.module';
import { MongooseModule } from '@nestjs/mongoose';

@Module({
  imports: [  MongooseModule.forRootAsync({
    useFactory: () => ({
      uri: 'mongodb://127.0.0.1:27017/AuthExampleDB',
    }),
  }),   AuthModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
