package co.th.ford.tms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import co.th.ford.tms.model.SetStopETA;


@Repository("setStopETADao")
public class SetStopETADaoImpl extends AbstractDao<Integer, SetStopETA> implements SetStopETADao {

	/*public User findByUsername(String user) {
		return getByKey(user);
	}*/

	public void saveSetStopETA(SetStopETA setStopETA) {
		persist(setStopETA);
	}

	
	public void deleteSetStopETAByID(int setStopETAID) {
		Query query = getSession().createSQLQuery("delete from tb_setstopeta where id = :setStopETAID");
		query.setInteger("setStopETAID", setStopETAID);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<SetStopETA> findAllSetStopETAs() {
		Criteria criteria = createEntityCriteria();
		return (List<SetStopETA>) criteria.list();
	}
	
	
	public SetStopETA findSetStopETAByLoadStopID(int loadStopid) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("loadStopID", loadStopid));
		return (SetStopETA) criteria.uniqueResult();
	}

	public SetStopETA findSetStopETAByID(int setStopETAID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", setStopETAID));
		return (SetStopETA) criteria.uniqueResult();
	}
	
}
