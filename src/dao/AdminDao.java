package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.Book;
import utils.JDBCUtil;

public class AdminDao extends UserDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/*
	 * Query administrators by username and password
	 */
	public Admin findAllByAdminAndPasswd(String userName, String passWord) {
		Admin admin = null;
		try {
			String sql = "select * from admin where username=? and password= ?";
			conn = JDBCUtil.getConnection(); // Establishing a connection to the database
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, passWord);
			rs = ps.executeQuery(); // Executing the SQL query to retrieve admin data

			if (rs.next()) {
				Integer id = rs.getInt("adminID");
				String username = rs.getString("username");
				String password = rs.getString("password");

				admin = new Admin(id, username, password); // Creating an Admin object from retrieved data
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handling SQL exceptions
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handling class not found exceptions
		} finally {
			JDBCUtil.release(rs, ps, conn); // Releasing resources
		}

		return admin; // Returning the Admin object
	}

	/**
	 * Add a new book to the database
	 * @param book The Book object to be added
	 * @return A message indicating the success or failure of the operation
	 */
	public String addBook(Book book) {
		String msg = "";
		try {
			String sql = "insert into book (bookname, author, number, borrow, quantity, location) values (?,?,?,?,?,?)";
			conn = JDBCUtil.getConnection(); // Establishing a connection to the database
			ps = conn.prepareStatement(sql);
			ps.setString(1, book.getBookname());
			ps.setString(2, book.getAuthor());
			ps.setLong(3, book.getNum());
			ps.setString(4, book.getBorrow());
			ps.setInt(5, book.getQuantity());
			ps.setString(6, book.getLocation());

			int flag = ps.executeUpdate(); // Executing the SQL query to add a book

			if (flag == 1) {
				msg = "Successfully Added!";
			} else {
				msg = "Failed to Add!";
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handling SQL exceptions
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handling class not found exceptions
		} finally {
			JDBCUtil.release(ps, conn); // Releasing resources
		}
		return msg; // Returning the status message
	}

	/**
	 * Delete a book from the database by its book number
	 * @param num The number of the book to be deleted
	 * @return A message indicating the success or failure of the operation
	 */
	public String deleteByNum(String num) {
		String msg = "";
		try {
			String sql = "delete from book where number = ?";
			conn = JDBCUtil.getConnection(); // Establishing a connection to the database
			ps = conn.prepareStatement(sql);
			ps.setString(1, num);
			int flag = ps.executeUpdate(); // Executing the SQL query to delete a book

			if (flag == 1) {
				msg = "Successfully Deleted!";
			} else {
				msg = "Failed to Delete";
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handling SQL exceptions
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handling class not found exceptions
		} finally {
			JDBCUtil.release(ps, conn); // Releasing resources
		}
		return msg; // Returning the status message
	}

	/**
	 * Update book details in the database
	 * @param book The Book object containing updated information
	 * @return A message indicating the success or failure of the operation
	 */
	public String updateBook(Book book) {
		String msg = "";
		try {
			String sql = "update book set bookname=?, author=?, number=?, borrow=?, quantity=?, location=? where bookid=?";
			conn = JDBCUtil.getConnection(); // Establishing a connection to the database
			ps = conn.prepareStatement(sql);
			ps.setString(1, book.getBookname());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getNum().toString());
			ps.setString(4, book.getBorrow());
			ps.setString(5, book.getQuantity().toString());
			ps.setString(6, book.getLocation());
			ps.setString(7, book.getBookid().toString());

			int flag = ps.executeUpdate(); // Executing the SQL query to update book details

			if (flag == 1) {
				msg = "Successfully Updated!";
			} else {
				msg = "Failed to Update!";
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Handling SQL exceptions
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); // Handling class not found exceptions
		} finally {
			JDBCUtil.release(ps, conn); // Releasing resources
		}
		return msg; // Returning the status message
	}
}
