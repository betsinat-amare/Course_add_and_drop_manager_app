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
        db.run('INSERT OR IGNORE INTO courses (id, title, code, description, credit_hours) VALUES (?, ?, ?, ?, ?)', 
            [2, 'Course 2', 'C102', 'Desc 2', 20]);
        db.run('INSERT OR IGNORE INTO courses (id, title, code, description, credit_hours) VALUES (?, ?, ?, ?, ?)', 
            [3, 'Course 3', 'C103', 'Desc 3', 4]);
    });
    db.close();
    done();
});

beforeEach((done: Done) => {
    const db = new sqlite3.Database('./college.db');
    db.serialize(() => {
        db.run('DELETE FROM adds'); // Reset adds table
        db.run('INSERT INTO adds (id, student_id, course_id, approval_status) VALUES (?, ?, ?, ?)', 
            [1, 1, 1, 'approved']); // 10 credit hours
        db.run('INSERT INTO adds (id, student_id, course_id, approval_status) VALUES (?, ?, ?, ?)', 
            [2, 1, 2, 'pending']); // 20 credit hours
    });
    db.close();
    done();
});

describe('Adds API', () => {
    it('should add a course with pending status', (done: Done) => {
        request(app as Express)
            .post('/api/adds')
            .set('Authorization', `Bearer ${studentToken}`)
            .send({ course_id: 3 })
            .expect(201)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('id');
                expect(res.body).to.have.property('approval_status', 'pending');
                done();
            });
    });

    it('should not add a course if approved credit hours exceed 35', (done: Done) => {
        request(app as Express)
            .post('/api/adds')
            .set('Authorization', `Bearer ${studentToken}`)
            .send({ course_id: 2 })
            .expect(201)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('id');
                expect(res.body).to.have.property('approval_status', 'pending');
                done();
            });
    });

    it('should allow registrar to approve an add', (done: Done) => {
        request(app as Express)
            .put('/api/adds/2')
            .set('Authorization', `Bearer ${registrarToken}`)
            .send({ approval_status: 'approved' })
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('message', 'Add updated');
                done();
            });
    });

    it('should prevent registrar from approving an add exceeding 35 credit hours', (done: Done) => {
        const db = new sqlite3.Database('./college.db');
        db.run('UPDATE adds SET approval_status = ? WHERE id = ?', ['approved', 2], (err) => {
            if (err) {
                db.close();
                return done(err);
            }
            db.close();
            request(app as Express)
                .post('/api/adds')
                .set('Authorization', `Bearer ${studentToken}`)
                .send({ course_id: 2 })
                .expect(201)
                .end((err, res) => {
                    if (err) return done(err);
                    const addId = res.body.id;
                    request(app as Express)
                        .put(`/api/adds/${addId}`)
                        .set('Authorization', `Bearer ${registrarToken}`)
                        .send({ approval_status: 'approved' })
                        .expect(400)
                        .end((err, res) => {
                            if (err) return done(err);
                            expect(res.body).to.have.property('error', 'Cannot approve add: Total credit hours would exceed 35');
                            done();
                        });
                });
        });
    });

    it('should allow registrar to delete a pending add', (done: Done) => {
        request(app as Express)
            .delete('/api/adds/2')
            .set('Authorization', `Bearer ${registrarToken}`)
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('message', 'Add deleted');
                done();
            });
    });

    it('should get adds for a student', (done: Done) => {
        request(app as Express)
            .get('/api/adds')
            .set('Authorization', `Bearer ${studentToken}`)
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.be.an('array');
                expect(res.body[0]).to.have.property('approval_status');
                done();
            });
    });

    it('should get all adds for a registrar', (done: Done) => {
        request(app as Express)
            .get('/api/adds')
            .set('Authorization', `Bearer ${registrarToken}`)
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.be.an('array');
                done();
            });
    });
});