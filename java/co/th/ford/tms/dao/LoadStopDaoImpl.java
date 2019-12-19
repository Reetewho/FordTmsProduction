package co.th.ford.tms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import co.th.ford.tms.model.LoadStop;


@Repository("loadStopDao")
public class LoadStopDaoImpl extends AbstractDao<Integer, LoadStop> implements LoadStopDao {

	/*public User findByUsername(String user) {
		return getByKey(user);
	}*/

	public void saveLoadStop(LoadStop loadStop) {
		persist(loadStop);
	}

	public void deleteLoadStopByID(int loadStopID) {
		
		Query query = getSession().createSQLQuery(
				  " delete tb_loadstop,tb_setstopeta "
				+ " from tb_loadstop  left join tb_setstopeta  on tb_loadstop.id=tb_setstopeta.loadStopID   "
				+ " where tb_loadstop.id = :loadStopID");
		
		query.setInteger("loadStopID", loadStopID);
		query.executeUpdate();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LoadStop> findAllLoadStops() {
		Criteria criteria = createEntityCriteria();
		return (List<LoadStop>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<LoadStop> findLoadStopByLoadID(int loadID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("loadID", loadID));
		return (List<LoadStop>) criteria.list();
	}

	public LoadStop findLoadStopByID(int loadStopid) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", loadStopid));
		return (LoadStop) criteria.uniqueResult();
	}
	
}
