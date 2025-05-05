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

before((done: Done) => {
    const db = new sqlite3.Database('./college.db');
    db.serialize(() => {
        db.run('INSERT OR IGNORE INTO users (id, username, password, role, email) VALUES (?, ?, ?, ?, ?)', 
            [1, 'teststudent', bcrypt.hashSync('password123', 10), 'Student', 'teststudent@example.com']);
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

describe('Courses API', () => {
    it('should get all courses', (done: Done) => {
        request(app as Express)
            .get('/api/courses')
            .set('Authorization', `Bearer ${studentToken}`)
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.be.an('array');
                expect(res.body.length).to.be.greaterThan(0);
                expect(res.body[0]).to.have.property('id');
                expect(res.body[0]).to.have.property('title');
                done();
            });
    });
});