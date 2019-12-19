package co.th.ford.tms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import co.th.ford.tms.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	/*public User findByUsername(String user) {
		return getByKey(user);
	}*/

	public void saveUser(User user) {
		persist(user);
	}

	public void deleteEmployeeByUsername(String username) {
		Query query = getSession().createSQLQuery("delete from tb_user where username = :username");
		query.setString("username", username);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria();
		return (List<User>) criteria.list();
	}

	public User findByUsername(String username) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}
	
}
