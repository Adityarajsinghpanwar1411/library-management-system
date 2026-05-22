package com.library.dao;

import com.library.model.BorrowRecord;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {
	 public boolean borrowBook(int memberId, int bookId) throws SQLException {
	        Connection conn = DBConnection.getConnection();
	        try {
	            conn.setAutoCommit(false);  // start transaction

	            // Check availability
	            String checkSql = "SELECT available_copies FROM books WHERE id = ? FOR UPDATE";
	            try (PreparedStatement check = conn.prepareStatement(checkSql)) {
	                check.setInt(1, bookId);
	                ResultSet rs = check.executeQuery();
	                if (!rs.next() || rs.getInt("available_copies") < 1) {
	                    System.out.println("Sorry, no copies available.");
	                    conn.rollback();
	                    return false;
	                }
	            }

	            // Insert borrow record
	            String insertSql = "INSERT INTO borrow_records (book_id, member_id) VALUES (?, ?)";
	            try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
	                insert.setInt(1, bookId);
	                insert.setInt(2, memberId);
	                insert.executeUpdate();
	            }

	            // Decrease available copies
	            String updateSql = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ?";
	            try (PreparedStatement update = conn.prepareStatement(updateSql)) {
	                update.setInt(1, bookId);
	                update.executeUpdate();
	            }

	            conn.commit();  // all good — commit
	            System.out.println("Book borrowed successfully!");
	            return true;

	        } catch (SQLException e) {
	            conn.rollback();  // something failed — undo everything
	            throw e;
	        } finally {
	            conn.setAutoCommit(true);
	        }
	    }

	    // Return a book
	    public boolean returnBook(int memberId, int bookId) throws SQLException {
	        Connection conn = DBConnection.getConnection();
	        try {
	            conn.setAutoCommit(false);

	            // Mark record as RETURNED
	            String updateRecord = "UPDATE borrow_records SET status = 'RETURNED', return_date = CURRENT_DATE " +
	                      "WHERE book_id = ? AND member_id = ? AND status = 'BORROWED'";
	            try (PreparedStatement ps = conn.prepareStatement(updateRecord)) {
	                ps.setInt(1, bookId);
	                ps.setInt(2, memberId);
	                int rows = ps.executeUpdate();
	                if (rows == 0) {
	                    System.out.println("No active borrow record found.");
	                    conn.rollback();
	                    return false;
	                }
	            }

	            // Increase available copies
	            String updateBook = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
	            try (PreparedStatement ps = conn.prepareStatement(updateBook)) {
	                ps.setInt(1, bookId);
	                ps.executeUpdate();
	            }

	            conn.commit();
	            System.out.println("Book returned successfully!");
	            return true;

	        } catch (SQLException e) {
	            conn.rollback();
	            throw e;
	        } finally {
	            conn.setAutoCommit(true);
	        }
	    }

	    // View all currently borrowed books
	    public List<BorrowRecord> getActiveBorrows() throws SQLException {
	        List<BorrowRecord> list = new ArrayList<>();
	        String sql = "SELECT br.*, b.title, m.name " +
	                "FROM borrow_records br " +
	                "JOIN books b ON br.book_id = b.id " +
	                "JOIN members m ON br.member_id = m.id " +
	                "WHERE br.status = 'BORROWED'";
	        try (Connection conn = DBConnection.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	            while (rs.next()) {
	                System.out.printf("Record[%d] | Book: %-30s | Member: %-20s | Since: %s%n",
	                        rs.getInt("id"),
	                        rs.getString("title"),
	                        rs.getString("name"),
	                        rs.getDate("borrow_date"));
	            }
	        }
	        return list;
	    }
}
