package googlePlayScraper.service;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import googlePlayScraper.dao.ApplicationDao;
import googlePlayScraper.entity.Application;
import googlePlayScraper.entity.Review;
import googlePlayScraper.util.HibernateUtils;

@SuppressWarnings("rawtypes")
public class ApplicationService {
	private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
	private ApplicationDao applicationDao;
	private Query query;

	/**
	 *
	 * Constructor
	 *
	 * @param applicationDao
	 */
	public ApplicationService() {
		super();
		this.applicationDao = new ApplicationDao();
	}

	public void addApplication(ArrayList<Application> applications) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		for (Application application : applications) {

			query = session.createNativeQuery(
					"INSERT INTO Applications (AppScrappingDate, Name, Developer, Link, Description,ReviewsNumber,Rating,DownloadsNumber,AgeRating,Price,Permissions) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11)");

			query.setParameter(1, application.getAppScrappingDate());
			query.setParameter(2, application.getName());
			query.setParameter(3, application.getDeveloper());
			query.setParameter(4, application.getLink());
			query.setParameter(5, application.getDescription());
			query.setParameter(6, application.getReviewsNumber());
			query.setParameter(7, application.getRating());
			query.setParameter(8, application.getDownloadsNumber());
			query.setParameter(9, application.getAgeRating());
			query.setParameter(10, application.getPrice());
			query.setParameter(11, application.getPermissions().toString());

			query.executeUpdate();

			if (application.getReviews() != null) {
				for (Review review : application.getReviews()) {
					query = session.createNativeQuery(
							"insert into Reviews (Author,StarsNumber,Date,Review,application_No) values (?1,?2,?3,?4,?5)");
					query.setParameter(1, review.getAuthor());
					query.setParameter(2, review.getStarsNumber());
					query.setParameter(3, review.getDate());
					query.setParameter(4, review.getReview());
					int applicationNo = (int) session.createNativeQuery("select No from Applications where Name=:name")
							.setParameter("name", application.getName()).getSingleResult();

					query.setParameter(5, applicationNo);
					query.executeUpdate();
				}
			}

		}
		session.getTransaction().commit();
	}

	public void saveApplication(Application application) {
		applicationDao.openCurrentSessionwithTransaction();
		applicationDao.persist(application);
		applicationDao.closeCurrentSessionwithTransaction();
	}

}
