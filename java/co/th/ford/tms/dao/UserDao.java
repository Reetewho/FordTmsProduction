package co.th.ford.tms.dao;

import java.util.List;

import co.th.ford.tms.model.User;

public interface UserDao {

	User findByUsername(String username);

	void saveUser(User user);
	
	void deleteEmployeeByUsername(String username);
	
	List<User> findAllUsers();
	

}
