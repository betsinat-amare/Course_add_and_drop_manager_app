// auth/schemas/user.schema.ts
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

  profileImage: {
    type: String, // Store URL
    required: false,
  }
}

export const UserSchema = SchemaFactory.createForClass(User);
