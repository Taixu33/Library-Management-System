package service;

import dao.RegistDao;
import model.Admin;
import model.AdminCode;
import model.Register;
import model.User;

public class RegistService {
	
	private RegistDao registDao = new RegistDao();
	
	/**
 	* - Registration
 	* - Logic: Firstly, check whether the name, account, and password contain spaces or are empty. Yes --> return error message
 	* - No --> judge whether it is a regular user registration. Yes --> register
 	* - No --> judge whether the key is correct. Yes -> register
 	*      No -> end registration, return error message
 	* @param register
 	* @return
 	*/
	public String regist(Register register) {
		String msg = "";
		if(register.getUsername().contains(" ") || register.getUsername().equals("") ||  
				register.getPassword().contains(" ") || register.getPassword().equals("")) {
			msg = "Input cannot contain spaces or be left empty.";
		}else if(register.getUsername().length() < 2 ||  register.getPassword().length()<5){
			//Username at least 2 characters. Password at least 5 characters.
			msg = "Username at least 2 characters. Password at least 5 characters.";
		}else if (isExist(register)) {
			//This username already exists.
			msg = "This username already exists.";
		}else if(register.getMold().equals("Student")) {
			//student registration
			msg = registDao.userRegist(register);
		}else if(checkAdminCode(register.getAdminCode())){//Administrator registration, first verify if the key is correct.
			//Key is correct, administrator registration.
			msg = registDao.adminRegist(register);
		}else {
			//Key is incorrect.
			msg = "The key is incorrect or has been used too many times.";
		}
		return msg;
	}

	/**
	 * verify the key
	 * @param adminCode
	 * @return
	 */
	private boolean checkAdminCode(String adminCode) {
		AdminCode adminCodeMold = new AdminCode();
		adminCodeMold = registDao.checkAdminCode(adminCode);
		// Check if the key exists
		if (adminCodeMold != null) {
			// Check if the key usage count has not been depleted
			if (adminCodeMold.getCount() != 0) {
				// Not depleted, decrease the valid count by 1
				registDao.updateAdminCode(adminCodeMold);
				return true;
			} else {
				// Depleted
				return false;
			}
		} else {
			// Does not exist
			return false;
		}
	}	
	
	/**
	 * 	Determine whether the user or administrator is registered.
	 * @param register
	 * @return
	 */
	private boolean isExist(Register register) {
		User user = registDao.findUserByCode(register.getUsername());
		if (user != null) {
			// User exists
			return true;
		} else {
			// User does not exist, check if it's an administrator
			Admin admin = registDao.findAdminByCode(register.getUsername());
			if (admin != null) {
				// Administrator
				return true;
			}
		}
		// Neither a regular user nor an administrator = does not exist
		return false;
	}	
}
