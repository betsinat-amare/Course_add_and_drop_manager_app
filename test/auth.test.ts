import { expect } from 'chai';
import request from 'supertest';
import { Express } from 'express';
import app from '../src/index';
import sqlite3 from 'sqlite3';
import bcrypt from 'bcrypt';
import { Done } from 'mocha';

// Increase Mocha timeout for async operations
describe('Auth API', function() {
    this.timeout(5000); // Increase timeout to 5 seconds

    before((done: Done) => {
        const db = new sqlite3.Database('./college.db');
        db.serialize(() => {
            db.run('INSERT OR IGNORE INTO users (id, username, password, role, email) VALUES (?, ?, ?, ?, ?)', 
                [1, 'teststudent', bcrypt.hashSync('password123', 10), 'Student', 'teststudent@example.com']);
            db.run('INSERT OR IGNORE INTO users (id, username, password, role, email) VALUES (?, ?, ?, ?, ?)', 
                [2, 'testregistrar', bcrypt.hashSync('password123', 10), 'Registrar', 'testregistrar@example.com']);
        });
        db.close();
        done();
    });

    it('should login a student and return a token', (done: Done) => {
        request(app as Express)
            .post('/api/auth/login')
            .send({ username: 'teststudent', password: 'password123' })
            .expect(200)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('token');
                done();
            });
    });

    it('should reject invalid credentials', (done: Done) => {
        request(app as Express)
            .post('/api/auth/login')
            .send({ username: 'teststudent', password: 'wrongpassword' })
            .expect(401)
            .end((err, res) => {
                if (err) return done(err);
                expect(res.body).to.have.property('error', 'Invalid credentials');
                done();
            });
    });
});