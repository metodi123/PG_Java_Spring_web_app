package pg.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pg.web.app.model.Fine;
import pg.web.app.model.FinePaymentInfo;

@Repository
public class FineDAO implements FineDataAccess {
	
	public Fine getFine(int id) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Fine.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		Fine fine;
		
		try {		
			session.beginTransaction();

			fine = (Fine) session.createQuery("from Fine where id = :id")
										.setParameter("id", id)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return fine;
	}
	
	public List<Fine> getAllFines() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Fine.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<Fine> fines = new ArrayList<Fine>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<Fine> tQuery = session.createQuery("from Fine", Fine.class);
			fines = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return fines;
	}

	public void newFine(Fine fine) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Fine.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		if(fine.getFinePaymentInfo() == null){
			fine.setFinePaymentInfo(new FinePaymentInfo());
		}
		
		try {	
			session.beginTransaction();
				
			session.save(fine);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void payFine(Fine fine) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Fine.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		Fine oldFine = this.getFine(fine.getId());
		
		try {
			session.beginTransaction();
			
			session.createQuery("update Fine set paid = :paid where id = :id")
					.setParameter("paid", fine.isPaid())
					.setParameter("id", oldFine.getId())
					.executeUpdate();
			session.createQuery("update FinePaymentInfo set paidAmount = :paidAmount where id = :id")
					.setParameter("paidAmount", fine.getFinePaymentInfo().getPaidAmount())
					.setParameter("id", oldFine.getFinePaymentInfo().getId())
					.executeUpdate();
			session.createQuery("update FinePaymentInfo set paidBy = :paidBy where id = :id")
					.setParameter("paidBy", fine.getFinePaymentInfo().getPaidBy())
					.setParameter("id", oldFine.getFinePaymentInfo().getId())
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}
