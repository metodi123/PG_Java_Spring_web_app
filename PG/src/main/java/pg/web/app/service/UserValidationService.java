package pg.web.app.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import pg.web.app.exception.InvalidUserException;
import pg.web.app.model.Admin;
import pg.web.app.model.Employee;

@Service
public class UserValidationService {
	
	public static boolean isUserValid(Employee employee) throws InvalidUserException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		int count = 0;
		
		try {		
			session.beginTransaction();

			count = ((Long) session.createQuery("select count(*) from Employee where username = :username and password = :password")
					.setParameter("username", employee.getUsername())
					.setParameter("password", employee.getPassword())
					.getSingleResult())
					.intValue();

			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		if(count == 1){
        	return true;
        }
        else {
        	throw new InvalidUserException("User is not in database");
        }
	}

	public static boolean isUserValid(Admin admin) throws InvalidUserException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Admin.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		int count = 0;
		
		try {		
			session.beginTransaction();

			count = ((Long) session.createQuery("select count(*) from Admin where username = :username and password = :password")
					.setParameter("username", admin.getUsername())
					.setParameter("password", admin.getPassword())
					.getSingleResult())
					.intValue();

			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		if(count == 1){
        	return true;
        }
        else {
        	throw new InvalidUserException("User is not in database");
        }
	}
}
