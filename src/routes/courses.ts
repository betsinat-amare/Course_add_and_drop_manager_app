import { Router } from 'express';
import sqlite3 from 'sqlite3';
import { authenticate } from '../middleware/auth';
import { AuthenticatedRequest } from '../types';

const router = Router();

router.use(authenticate);

router.get('/', async (req: AuthenticatedRequest, res) => {
    const db = new sqlite3.Database('./college.db');
    db.all('SELECT * FROM courses', (err, courses) => {
        if (err) {
            db.close();
            return res.status(500).json({ error: 'Internal server error' });
        }
        db.close();
        res.status(200).json(courses);
    });
});

export default router;