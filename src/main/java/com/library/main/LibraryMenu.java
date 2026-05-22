package com.library.main;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.MemberDAO;
import com.library.model.Book;
import com.library.model.Member;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class LibraryMenu {

    static Scanner sc = new Scanner(System.in);
    static BookDAO bookDAO = new BookDAO();
    static MemberDAO memberDAO = new MemberDAO();
    static BorrowDAO borrowDAO = new BorrowDAO();

    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");
        while (true) {
            System.out.println("\n1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. View Active Borrows");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        bookMenu();
                        break;
                    case 2:
                        memberMenu();
                        break;
                    case 3:
                        borrowBook();
                        break;
                    case 4:
                        returnBook();
                        break;
                    case 5:
                        borrowDAO.getActiveBorrows();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    // ─── BOOK MENU ───────────────────────────────────────────
    static void bookMenu() throws SQLException {
        System.out.println("\n--- Book Management ---");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search by Title");
        System.out.println("4. Delete Book");
        System.out.print("Choice: ");

        int c = 0;
        try {
            c = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        switch (c) {
            case 1:
                System.out.print("Enter Title: ");
                String title = sc.nextLine();

                System.out.print("Enter Author: ");
                String author = sc.nextLine();

                System.out.print("Enter Genre: ");
                String genre = sc.nextLine();

                System.out.print("Enter Number of Copies: ");
                int copies = 0;
                try {
                    copies = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Defaulting to 1.");
                    copies = 1;
                }

                bookDAO.addBook(new Book(title, author, genre, copies));
                break;

            case 2:
                List<Book> allBooks = bookDAO.getAllBooks();
                if (allBooks.isEmpty()) {
                    System.out.println("No books found in the library.");
                } else {
                    System.out.println("\n--- All Books ---");
                    for (Book b : allBooks) {
                        System.out.println(b);
                    }
                }
                break;

            case 3:
                System.out.print("Enter keyword to search: ");
                String keyword = sc.nextLine();
                List<Book> results = bookDAO.searchByTitle(keyword);
                if (results.isEmpty()) {
                    System.out.println("No books found matching: " + keyword);
                } else {
                    for (Book b : results) {
                        System.out.println(b);
                    }
                }
                break;

            case 4:
                System.out.print("Enter Book ID to delete: ");
                try {
                    int bookId = Integer.parseInt(sc.nextLine().trim());
                    bookDAO.deleteBook(bookId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID entered.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    // ─── MEMBER MENU ─────────────────────────────────────────
    static void memberMenu() throws SQLException {
        System.out.println("\n--- Member Management ---");
        System.out.println("1. Register New Member");
        System.out.println("2. View All Members");
        System.out.print("Choice: ");

        int c = 0;
        try {
            c = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        switch (c) {
            case 1:
                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Email: ");
                String email = sc.nextLine();

                System.out.print("Enter Phone: ");
                String phone = sc.nextLine();

                memberDAO.addMember(new Member(name, email, phone));
                break;

            case 2:
                List<Member> members = memberDAO.getAllMembers();
                if (members.isEmpty()) {
                    System.out.println("No members registered yet.");
                } else {
                    System.out.println("\n--- All Members ---");
                    for (Member m : members) {
                        System.out.println(m);
                    }
                }
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    // ─── BORROW A BOOK ───────────────────────────────────────
    static void borrowBook() throws SQLException {
        System.out.print("Enter Member ID: ");
        int memberId = 0;
        try {
            memberId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Member ID.");
            return;
        }

        System.out.print("Enter Book ID: ");
        int bookId = 0;
        try {
            bookId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Book ID.");
            return;
        }

        borrowDAO.borrowBook(memberId, bookId);
    }

    // ─── RETURN A BOOK ───────────────────────────────────────
    static void returnBook() throws SQLException {
        System.out.print("Enter Member ID: ");
        int memberId = 0;
        try {
            memberId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Member ID.");
            return;
        }

        System.out.print("Enter Book ID: ");
        int bookId = 0;
        try {
            bookId = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Book ID.");
            return;
        }

        borrowDAO.returnBook(memberId, bookId);
    }
}
