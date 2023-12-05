package service;

import java.util.ArrayList;
import java.util.List;

import dao.AdminDao;
import dao.UserDao;
import model.Admin;
import model.Book;
import model.User;

/**
 * A layer for operating on regular user data.
 * @author Administrator
 *
 */
public class UserService{
	UserDao userDao = new UserDao();
	AdminDao adminDao = new AdminDao();
	
	//Verify the format of account and password.
	private boolean checkCode(String userName, String userPassword) {
		//Determination of account correctness, distinction between administrator and regular user.
		if(userName.length()<2 || userName.contains(" ")) {
			//Incorrect account input.
//			System.out.println("Incorrect account input.");
			return false;
		}else if(userPassword.length()<5 || userPassword.contains(" ")){
			//Incorrect password input.
//			System.out.println("Incorrect password input.");
			return false;
		}else {
			return true;
		}
	}
	
	// Regular user login
	public User login(String userName, String userPassword) {
		boolean flag = checkCode(userName, userPassword);
		if (flag) {
			// Password format is correct, check if registered
			User user = userDao.findAllByUserAndPasswd(userName, userPassword);
			if (user != null) {
				return user;
			} else {
				return null;
			}
		} else {
			// Password format is incorrect, return null
			return null;
		}
	}
	
	// Administrator login
	public Admin adminLogin(String userName, String userPassword) {
		boolean flag = checkCode(userName, userPassword);
		if (flag) {
			// Password format is correct, check if registered
			Admin admin = adminDao.findAllByAdminAndPasswd(userName, userPassword);
			if (admin != null) {
				return admin;
			} else {
				return null;
			}
		} else {
			// Password format is incorrect, return null
			return null;
		}
	}
	
	/**
	 * Search books
	 */
	public List<Book> findBooks(String findMsg, int flag) {
		List<Book> books = new ArrayList<>();
		if(findMsg.equals("") && flag != 3) {
			return books;
		}else {
			switch(flag) {
			case 0:
				books = userDao.findBookByBookname(findMsg);
				break;
			case 1:
				books = userDao.findBookByAuthor(findMsg);
				break;
			case 2:
				books = userDao.findBookByNum(findMsg);
				break;
			case 3:
				books = userDao.findAllBook();
				break;
			}
		}
		
		return books;
	}

	public List<Book> borrowBooks(String findMsg, int flag){
		List<Book> books = new ArrayList<>();

			if(findMsg.equals("") && flag != 2) {
			return books;
		}else {
			switch(flag) {
			case 0:
				books = userDao.borrowBookByBookname(findMsg);
				break;
			case 1:
				books = userDao.borrowBookByAuthor(findMsg);
				break;
			case 2:
				books = userDao.borrowBookByNum(findMsg);
				break;
		}
	}
	return books;
   }

   public List<Book> returnBooks(String findMsg, int flag){
		List<Book> books = new ArrayList<>();

		if(findMsg.equals("") && flag != 2) {
			return books;
		}else {
			switch(flag) {
			case 0:
				books = userDao.returnBookByBookname(findMsg);
				break;
			case 1:
				books = userDao.returnBookByAuthor(findMsg);
				break;
			case 2:
				books = userDao.returnBookByNum(findMsg);
				break;
		}
	}
	return books;
   }


	private String selectedbook;

	// Method to get the selected book.

	public void setSelectedBook(String bookname) {
		this.selectedbook = bookname;
	}
    
}

