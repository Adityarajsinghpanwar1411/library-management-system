# library-management-system
A console-based Library Management System built with Java, JDBC and MySQL

## Features
- Add, search, and delete books
- Register and view library members
- Borrow and return books with real-time availability tracking
- Transaction-based borrow system to ensure data consistency
- Active borrow records viewer

## Tech Stack
- Java 17
- JDBC (Java Database Connectivity)
- MySQL 8.x
- Maven

## Project Structure
src/main/java/
├── com.library.main      → LibraryMenu.java (entry point)
├── com.library.model     → Book, Member, BorrowRecord
├── com.library.dao       → BookDAO, MemberDAO, BorrowDAO
└── com.library.util      → DBConnection (Singleton)

## How to Run
1. Clone the repo:  git clone https://github.com/yourusername/library-management-system.git
2. Create the database using the SQL script in /sql/library_db.sql
3. Update DB credentials in DBConnection.java
4. Run LibraryMenu.java as Java Application

## Key Concepts Demonstrated
- DAO Design Pattern
- Singleton Pattern
- JDBC Transactions with commit and rollback
- PreparedStatement for SQL injection prevention
- 3-layer architecture (UI → DAO → Database)
