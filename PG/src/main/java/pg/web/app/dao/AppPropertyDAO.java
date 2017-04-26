package pg.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pg.web.app.model.AppProperty;

@Repository
public class AppPropertyDAO implements AppPropertyDataAccess {
	
	public AppProperty getAppProperty(String key) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AppProperty.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		AppProperty appProperty;
		
		try {		
			session.beginTransaction();

			appProperty = (AppProperty) session.createQuery("from AppProperty where key = :key")
										.setParameter("key", key)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return appProperty;
	}
	
	public List<AppProperty> getAllAppProperties() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AppProperty.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<AppProperty> appProperties = new ArrayList<AppProperty>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<AppProperty> tQuery = session.createQuery("from AppProperty", AppProperty.class);
			appProperties = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return appProperties;
	}

	public void createAppProperty(AppProperty appProperty) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AppProperty.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			session.beginTransaction();
				
			session.save(appProperty);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void updateAppProperty(AppProperty appProperty) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AppProperty.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("update AppProperty set value = :value where key = :key")
					.setParameter("value", appProperty.getValue())
					.setParameter("key", appProperty.getKey())
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void deleteAppProperty(String key) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(AppProperty.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("delete from AppProperty where key = :key")
					.setParameter("key", key)
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}