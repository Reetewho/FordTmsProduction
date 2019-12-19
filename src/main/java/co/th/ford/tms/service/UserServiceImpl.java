package co.th.ford.tms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.th.ford.tms.dao.UserDao;
import co.th.ford.tms.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	

	public void saveUser(User user) {
		dao.saveUser(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User u) {
		User entity = dao.findByUsername(u.getUsername());
		if(entity!=null){
			entity.setName(u.getName());
			entity.setJoiningDate(u.getJoiningDate());
			entity.setDepartment(u.getDepartment());
			entity.setPassword(u.getPassword());
			entity.setRole(u.getRole());
			entity.setStatus(u.getStatus());
			entity.setLogoutDate(u.getLogoutDate());
			entity.setEmail(u.getEmail());
			entity.setLastname(u.getLastname());
			entity.setContactnumber(u.getContactnumber());
		}
	}

	public void deleteUserByUsername(String username) {
		dao.deleteEmployeeByUsername(username);
	}
	
	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public User findUserByusername(String username) {
		return dao.findByUsername(username);
	}

	public boolean isUsernameUnique(String username) {
		User user = findUserByusername(username);
		return ( user == null);
	}
	
}
