package co.th.ford.tms.service;

import java.util.List;

import co.th.ford.tms.model.User;


public interface UserService {	
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByUsername(String username);

	List<User> findAllUsers(); 
	
	User findUserByusername(String username);

	boolean isUsernameUnique(String username);
	
}
