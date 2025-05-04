import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

@Schema()
export class User extends Document {
  @Prop({ required: true })
  fullName: string;

  @Prop({ required: true, unique: true })
  email: string;

  @Prop({ required: true, unique: true })
  studentId: string;

  @Prop({ required: true })
  password: string;

  @Prop({ required: false }) // Optional image field
  profileImage?: string; // Store image filename or path
}

export const UserSchema = SchemaFactory.createForClass(User);
