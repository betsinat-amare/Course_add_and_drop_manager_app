import dotenv from 'dotenv';
import path from 'path';

// Load .env from project root
dotenv.config({ path: path.resolve(__dirname, '../.env') });
process.env.NODE_ENV = 'test';

// Debug: Verify JWT_SECRET
console.log('JWT_SECRET in test setup:', process.env.JWT_SECRET || 'undefined');