import express, { Express } from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import authRoutes from './routes/auth';
import courseRoutes from './routes/courses';
import addRoutes from './routes/adds';
import dropRoutes from './routes/drops';

dotenv.config();

const app: Express = express();
app.use(cors());
app.use(express.json());

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/courses', courseRoutes);
app.use('/api/adds', addRoutes);
app.use('/api/drops', dropRoutes);

// Global error handler
app.use((err: Error, req: express.Request, res: express.Response, next: express.NextFunction) => {
    console.error(err.stack);
    res.status(500).json({ error: 'Internal server error' });
});

// Only start the server if not in test mode
if (process.env.NODE_ENV !== 'test') {
    const PORT: number = parseInt(process.env.PORT as string, 10) || 5000;
    app.listen(PORT, () => {
        console.log(`Server running on port ${PORT}`);
    });
}

export default app;