import { expect } from 'chai';
import request from 'supertest';
import { Express } from 'express';
import app from '../src/index';
import jwt from 'jsonwebtoken';
import sqlite3 from 'sqlite3';
import { Done } from 'mocha';
import bcrypt from 'bcrypt';

const JWT_SECRET = process.env.JWT_SECRET || 'course_add_and_drop';

const studentToken = jwt.sign(
    { id: 1, role: 'Student' },
    JWT_SECRET,
    { expiresIn: '1h' }
);

const registrarToken = jwt.sign(
    { id: 2, role: 'Registrar' },
    JWT_SECRET,
    { expiresIn: '1h' }
);

before((done: Done) => {
    const db = new sqlite3.Database('./college.db');
    db.serialize(() => {
        db.run('INSERT OR IGNORE INTO users (id, username, password, role, email) VALUES (?, ?, ?, ?, ?)', 
            [1, 'teststudent', bcrypt.hashSync('password123', 10), 'Student', 'teststudent@example.com']);
        db.run('INSERT OR IGNORE INTO users (id, username, password, role, email) VALUES (?, ?, ?, ?, ?)', 
            [2, 'testregistrar', bcrypt.hashSync('password123', 10), 'Registrar', 'testregistrar@example.com']);
        db.run('INSERT OR IGNORE INTO courses (id, title, code, description, credit_hours) VALUES (?, ?, ?, ?, ?)', 
            [1, 'Course 1', 'C101', 'Desc 1', 10]);
        db.run('INSERT OR IGNORE INTO adds (id, student_id, course_id, approval_status) VALUES (?, ?, ?, ?)', 
            [1, 1, 1, 'approved']);
        db.run('INSERT OR IGNORE INTO drops (id, student_id, course_id, add_id, approval_status) VALUES (?, ?, ?, ?, ?)', 
            [1, 1, 1, 1, 'pending']);
    });
    db.close();
    done();
});

describe('Drops API', () => {
    it('should create a pending drop for an approved add', (done: Done) => {
        request(app as Express)
            .post('/api/drops')
            .set('Authorization', `Bearer ${studentToken}`)
            .send({ add_id: 1, course_id: 1 })
            .expect(201)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('id');
                expect(res.body).to.have.property('approval_status', 'pending');
                done();
            });
    });

    it('should allow registrar to approve a drop', (done: Done) => {
        request(app as Express)
            .put('/api/drops/1')
            .set('Authorization', `Bearer ${registrarToken}`)
            .send({ approval_status: 'approved' })
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('message', 'Drop updated');
                done();
            });
    });

    it('should get drops for a student', (done: Done) => {
        request(app as Express)
            .get('/api/drops')
            .set('Authorization', `Bearer ${studentToken}`)
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.be.an('array');
                expect(res.body[0]).to.have.property('approval_status');
                done();
            });
    });

    it('should get all drops for a registrar', (done: Done) => {
        request(app as Express)
            .get('/api/drops')
            .set('Authorization', `Bearer ${registrarToken}`)
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.be.an('array');
                done();
            });
    });
});