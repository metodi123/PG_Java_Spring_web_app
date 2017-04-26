package pg.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pg.web.app.model.Parking;

@Repository
public class ParkingDAO implements ParkingDataAccess {
	
	public Parking getParking(int number) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Parking.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		Parking parking;
		
		try {		
			session.beginTransaction();

			parking = (Parking) session.createQuery("from Parking where number = :number")
										.setParameter("number", number)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return parking;
	}
	
	public List<Parking> getAllParkings() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Parking.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<Parking> parkings = new ArrayList<Parking>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<Parking> tQuery = session.createQuery("from Parking", Parking.class);
			parkings = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return parkings;
	}

	public void createParking(Parking parking) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Parking.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			session.beginTransaction();
				
			session.save(parking);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void updateParking(Parking parking) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Parking.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.update(parking);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void deleteParking(Parking parking) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Parking.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.delete(parking);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
}
