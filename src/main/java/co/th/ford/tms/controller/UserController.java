package co.th.ford.tms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import co.th.ford.tms.model.User;
import co.th.ford.tms.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {

	
	
	@Autowired
	UserService uservice;
	
	@Autowired
	MessageSource messageSource;
	
	private List<String> ROLE = Arrays.asList("ADMIN", "USER");

	
	
	/*
	 * This method will list all existing User.
	 */
	@RequestMapping(value = {"/userList" }, method = RequestMethod.GET)
	public String listUser(ModelMap model) {
		List<User> users = uservice.findAllUsers();
		model.addAttribute("users", users);
		return "userList";
	}

	/*
	 * This method will provide the medium to add a new User.
	 */
	@RequestMapping(value = { "/newUser" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		User user = new User();
		user.setJoiningDate(LocalDate.now());
		model.addAttribute("user", user);
		model.addAttribute("edit", false);
		model.addAttribute("roleList", ROLE);
		return "userForm";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newUser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, BindingResult result,
			ModelMap model) {
		
		model.addAttribute("roleList", ROLE);
		if (result.hasErrors()) {			
			return "userForm";
		}

		if(!uservice.isUsernameUnique(user.getUsername())){			
			model.addAttribute("usernameErr", messageSource.getMessage("non.unique.username", new String[]{user.getUsername()}, Locale.getDefault()));
			return "userForm";
		}
		
		uservice.saveUser(user);
		
		List<User> users = uservice.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("saveSuccess", messageSource.getMessage("save.success", new String[]{user.getUsername()}, Locale.getDefault()));
		
		return "userList";
	}


	/*
	 * This method will provide the medium to update an existing employee.
	 */
	@RequestMapping(value = { "/edit-{username}-user" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String username, ModelMap model) {
		/*Employee employee = service.findEmployeeBySsn(ssn);
		model.addAttribute("employee", employee);*/
		User user =uservice.findUserByusername(username);
		model.addAttribute("user", user);
		model.addAttribute("roleList", ROLE);
		model.addAttribute("edit", true);
		return "userForm";
	}
	
	/*
	 * This method will be called on form submission, handling POST request for
	 * updating employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{username}-user" }, method = RequestMethod.POST)
	public String updateEmployee(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String username) {
		
		model.addAttribute("roleList", ROLE);
		if (result.hasErrors()) {
			return "userForm";
		}	
		
		uservice.updateUser(user);
		
		List<User> users = uservice.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("saveSuccess",  messageSource.getMessage("update.success", new String[]{user.getUsername()}, Locale.getDefault()));
		return "userList";
	}

	
	/*
	 * This method will delete an User by it's username value.
	 */
	@RequestMapping(value = { "/delete-{username}-user" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable String username, ModelMap model) {
		uservice.deleteUserByUsername(username);
		
		List<User> users = uservice.findAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("saveSuccess",  messageSource.getMessage("update.success", new String[]{username}, Locale.getDefault()));
		
		return "userList";
	}

}
