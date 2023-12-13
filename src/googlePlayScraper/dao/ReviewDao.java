package googlePlayScraper.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import googlePlayScraper.entity.Review;
import googlePlayScraper.util.HibernateUtils;

public class ReviewDao implements EntityDao<Review, Integer> {

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
	public void persist(Review entity) {
		session.save(entity);
	}

	@Override
	public List<Review> showAll() {
		return session.createQuery("From Reviews", Review.class).list();
	}

}
