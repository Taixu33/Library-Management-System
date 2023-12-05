package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Admin;
import model.AdminCode;
import model.Register;
import model.User;
import utils.JDBCUtil;

public class RegistDao {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * Check if the admin code exists
	 * @param adminCode The admin code to check
	 * @return AdminCode object containing admin code details if it exists, otherwise null
	 */
	public AdminCode checkAdminCode(String adminCode) {
		AdminCode adminCodeMold = new AdminCode();
		try {
			String sql = "select * from admin_code where code = ?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, adminCode);

			rs = ps.executeQuery();

			if (rs.next()) {
				adminCodeMold.setId(rs.getInt("id"));
				adminCodeMold.setCode(rs.getString("code"));
				adminCodeMold.setCount(rs.getInt("count"));
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(rs, ps, conn);
		}
		return adminCodeMold;
	}

	/**
	 * Update the admin code usage count after its usage
	 * @param adminCodeMold The AdminCode object with updated count
	 */
	public void updateAdminCode(AdminCode adminCodeMold) {
		try {
			String sql = "update admin_code set count = ? where code = ?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, adminCodeMold.getCount()-1);
			ps.setString(2, adminCodeMold.getCode());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(ps, conn);
		}
	}

	/**
	 * Register a new user
	 * @param register The Register object containing user details
	 * @return A message indicating the success or failure of the registration process
	 */
	public String userRegist(Register register) {
		String msg = "";
		try {
			String sql = "insert into users (username,password) values (?,?)";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, register.getUsername());
			ps.setString(2, register.getPassword());

			int flag = ps.executeUpdate();
			if (flag == 1) {
				msg = "Registration successful!";
			} else {
				msg = "Registration failed!";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(ps, conn);
		}
		return msg;
	}

	/**
	 * Register a new admin
	 * @param register The Register object containing admin details
	 * @return A message indicating the success or failure of the registration process
	 */
	public String adminRegist(Register register) {
		String msg = "";
		try {
			String sql = "insert into admin (username,password) values (?,?)";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, register.getUsername());
			ps.setString(2, register.getPassword());

			int flag = ps.executeUpdate();
			if (flag == 1) {
				msg = "Registration successful!";
			} else {
				msg = "Registration failed!";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(ps, conn);
		}
		return msg;
	}

	/**
	 * Find a normal user by username
	 * @param name The username to search for
	 * @return User object representing the found user, or null if not found
	 */
	public User findUserByCode(String name) {
		User user = null;
		try {
			String sql = "select * from users where username = ?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				Integer uid = rs.getInt("uid");
				String username = rs.getString("username");
				String password = rs.getString("password");
				user = new User(uid, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(rs, ps, conn);
		}
		return user;
	}

	/**
	 * Find an admin by username
	 * @param name The username to search for
	 * @return Admin object representing the found admin, or null if not found
	 */
	public Admin findAdminByCode(String name) {
		Admin admin = null;
		try {
			String sql = "select * from admin where username = ?";
			conn = JDBCUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				Integer aid = rs.getInt("adminID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				admin = new Admin(aid, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.release(rs, ps, conn);
		}
		return admin;
	}
}
