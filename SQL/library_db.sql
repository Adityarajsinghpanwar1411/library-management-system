CREATE DATABASE library_db;
USE library_db;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    genre VARCHAR(50),
    total_copies INT DEFAULT 1,
    available_copies INT DEFAULT 1
);

CREATE TABLE members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    joined_date DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE borrow_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    borrow_date DATE DEFAULT (CURRENT_DATE),
    return_date DATE,
    status ENUM('BORROWED', 'RETURNED') DEFAULT 'BORROWED',
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Sample data
INSERT INTO books (title, author, genre, total_copies, available_copies) VALUES
('Clean Code', 'Robert C. Martin', 'Technology', 3, 3),
('The Alchemist', 'Paulo Coelho', 'Fiction', 2, 2),
('Atomic Habits', 'James Clear', 'Self-Help', 4, 4);

INSERT INTO members (name, email, phone) VALUES
('Aarav Singh', 'aarav@example.com', '9876543210'),
('Diya Mehta', 'diya@example.com', '9123456789');


SELECT * FROM BOOKS;


