import { Router } from 'express';
import sqlite3 from 'sqlite3';
import { authenticate, restrictTo } from '../middleware/auth';
import { AuthenticatedRequest } from '../types';

const router = Router();

router.use(authenticate);

router.post('/', restrictTo('Student'), async (req: AuthenticatedRequest, res) => {
    const { add_id, course_id } = req.body;
    const student_id = req.user!.id;

    if (!add_id || !course_id) {
        return res.status(400).json({ error: 'Add ID and course ID are required' });
    }

    const db = new sqlite3.Database('./college.db');
    db.get(
        'SELECT * FROM adds WHERE id = ? AND student_id = ? AND course_id = ? AND approval_status = ?',
        [add_id, student_id, course_id, 'approved'],
        (err, add: any) => {
            if (err || !add) {
                db.close();
                return res.status(404).json({ error: 'Approved add not found' });
            }

            db.run(
                'INSERT INTO drops (student_id, course_id, add_id, approval_status) VALUES (?, ?, ?, ?)',
                [student_id, course_id, add_id, 'pending'],
                function(err) {
                    if (err) {
                        db.close();
                        return res.status(500).json({ error: 'Internal server error' });
                    }
                    db.close();
                    res.status(201).json({ id: this.lastID, student_id, course_id, add_id, approval_status: 'pending' });
                }
            );
        }
    );
});

router.put('/:id', restrictTo('Registrar'), async (req: AuthenticatedRequest, res) => {
    const { id } = req.params;
    const { approval_status } = req.body;

    if (!['approved', 'rejected'].includes(approval_status)) {
        return res.status(400).json({ error: 'Invalid approval status' });
    }

    const db = new sqlite3.Database('./college.db');
    db.run(
        'UPDATE drops SET approval_status = ? WHERE id = ?',
        [approval_status, id],
        (err) => {
            if (err) {
                db.close();
                return res.status(500).json({ error: 'Internal server error' });
            }
            db.close();
            res.status(200).json({ message: 'Drop updated' });
        }
    );
});

router.get('/', async (req: AuthenticatedRequest, res) => {
    const db = new sqlite3.Database('./college.db');
    if (req.user!.role === 'Student') {
        db.all('SELECT * FROM drops WHERE student_id = ?', [req.user!.id], (err, drops) => {
            if (err) {
                db.close();
                return res.status(500).json({ error: 'Internal server error' });
            }
            db.close();
            res.status(200).json(drops);
        });
    } else {
        db.all('SELECT * FROM drops', (err, drops) => {
            if (err) {
                db.close();
                return res.status(500).json({ error: 'Internal server error' });
            }
            db.close();
            res.status(200).json(drops);
        });
    }
});

export default router;