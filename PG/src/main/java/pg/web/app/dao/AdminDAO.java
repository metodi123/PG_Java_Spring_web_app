package pg.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pg.web.app.model.Admin;

@Repository
public class AdminDAO implements AdminDataAccess {

	public Admin getUser(String username) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		Admin admin;
		
		try {		
			session.beginTransaction();

			admin = (Admin) session.createQuery("from Admin where username = :username")
										.setParameter("username", username)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return admin;
	}
	
	public List<Admin> getAllUsers() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<Admin> admins = new ArrayList<Admin>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<Admin> tQuery = session.createQuery("from Admin", Admin.class);
			admins = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return admins;
	}

	public void createUser(Admin admin) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			session.beginTransaction();
				
			session.save(admin);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void updateUser(Admin admin) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("update Admin set password = :password where username = :username")
					.setParameter("password", admin.getPassword())
					.setParameter("username", admin.getUsername())
					.executeUpdate();
			session.createQuery("update Admin  set firstName = :firstName where username = :username")
					.setParameter("firstName", admin.getFirstName())
					.setParameter("username", admin.getUsername())
					.executeUpdate();
			session.createQuery("update Admin set lastName = :lastName where username = :username")
					.setParameter("lastName", admin.getLastName())
					.setParameter("username", admin.getUsername())
					.executeUpdate();
			session.createQuery("update Admin set email = :email where username = :username")
					.setParameter("email", admin.getEmail())
					.setParameter("username", admin.getUsername())
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void deleteUser(String username) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("delete from Admin where username = :username")
					.setParameter("username", username)
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}
