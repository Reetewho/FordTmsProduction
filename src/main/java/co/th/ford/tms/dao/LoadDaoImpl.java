package co.th.ford.tms.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import co.th.ford.tms.model.Load;


@Repository("loadDao")
public class LoadDaoImpl extends AbstractDao<Integer, Load> implements LoadDao {

	/*public User findByUsername(String user) {
		return getByKey(user);
	}*/

	public void saveLoad(Load load) {
		persist(load);
	}

	public void deleteLoadByID(int loadID) {
		Query query = getSession().createSQLQuery(
				  " delete tb_load,tb_loadstop,tb_setstopeta "
				+ " from tb_load lef join tb_loadstop on tb_load.loadID=tb_loadstop.loadID "
				+ " left join tb_setstopeta  on tb_loadstop.id=tb_setstopeta.loadStopID   "
				+ " where tb_load.loadID = :loadID ");
		query.setInteger("loadID", loadID);
		query.executeUpdate();
		
		/*query = getSession().createSQLQuery("delete from tb_load where loadID = :loadID");
		query.setInteger("loadID", loadID);
		query.executeUpdate();*/
	}
	
	
	public Load deleteLoadByLoadID(int loadID) {
		Query query = getSession().createSQLQuery(
				  " Delete "
				+ " FROM tb_load "
				+ " WHERE tb_load.loadID = :loadID ");
		query.setInteger("loadID", loadID);
		query.executeUpdate();
		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("loadID", loadID));
		return (Load) criteria.uniqueResult();
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Load> findAllLoads() {
		Criteria criteria = createEntityCriteria();
		return (List<Load>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Load> findLoadByCarrierID(int carrierID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("carrierID", carrierID));
		criteria.add(Restrictions.or(Restrictions.ne("loadAction", "MANUAL"), Restrictions.isNull("loadAction")));
		return (List<Load>) criteria.list();
	}

	public Load findLoadByID(int loadID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("loadID", loadID));
		return (Load) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Load> findLoadByusername(String driverid) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("driverid", driverid));
		return (List<Load>) criteria.list();
	}
	
	public Load findLoadByCarrierID_SystemLoadID(int carrierID, int systemLoadID) {
		  Criteria criteria = createEntityCriteria();
		  criteria.add(Restrictions.eq("carrierID", carrierID));
		  criteria.add(Restrictions.eq("systemLoadID", systemLoadID));
		  return (Load) criteria.uniqueResult();
	}
	
	
	
}
