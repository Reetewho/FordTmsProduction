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
	
	
	@SuppressWarnings("unchecked")
	public List<Load> findAllLoads() {
		Criteria criteria = createEntityCriteria();
		return (List<Load>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Load> findLoadByCarrierID(int carrierID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("carrierID", carrierID));
		return (List<Load>) criteria.list();
	}

	public Load findLoadByID(int loadID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("loadID", loadID));
		return (Load) criteria.uniqueResult();
	}
	
}
