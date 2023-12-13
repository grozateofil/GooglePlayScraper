package googlePlayScraper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import googlePlayScraper.entity.Application;
import googlePlayScraper.util.HibernateUtils;

public class ApplicationDao implements EntityDao<Application, Integer> {

	private Session session = HibernateUtils.getSessionFactory().getCurrentSession();

	private Transaction transaction;

	public Session openCurrentSession() {
		session = HibernateUtils.getSessionFactory().openSession();
		return session;
	}

	public Session openCurrentSessionwithTransaction() {
		session = HibernateUtils.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		return session;
	}

	public void closeCurrentSession() {
		session.close();
	}

	public void closeCurrentSessionwithTransaction() {
		transaction.commit();
		session.close();
	}

	@Override
	public void persist(Application entity) {
		session.save(entity);
	}

	@Override
	public List<Application> showAll() {
		return session.createQuery("From Applications", Application.class).list();
	}

}
