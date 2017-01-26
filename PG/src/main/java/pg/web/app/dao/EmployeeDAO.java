package pg.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pg.web.app.model.Employee;

@Repository
public class EmployeeDAO implements EmployeeDataAccess {
	
	public Employee getUser(String username) {
		
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		Employee employee;
		
		try {		
			session.beginTransaction();
			
			employee = (Employee) session.createQuery("from Employee where username = :username")
										.setParameter("username", username)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return employee;
	}
	
	public List<Employee> getAllUsers() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<Employee> employees = new ArrayList<Employee>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<Employee> tQuery = session.createQuery("from Employee", Employee.class);
			employees = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return employees;
	}

	public void createUser(Employee employee) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			session.beginTransaction();
				
			session.save(employee);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void updateUser(Employee employee) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("update Employee set password = :password where username = :username")
					.setParameter("password", employee.getPassword())
					.setParameter("username", employee.getUsername())
					.executeUpdate();
			session.createQuery("update Employee  set firstName = :firstName where username = :username")
					.setParameter("firstName", employee.getFirstName())
					.setParameter("username", employee.getUsername())
					.executeUpdate();
			session.createQuery("update Employee set lastName = :lastName where username = :username")
					.setParameter("lastName", employee.getLastName())
					.setParameter("username", employee.getUsername())
					.executeUpdate();
			session.createQuery("update Employee set email = :email where username = :username")
					.setParameter("email", employee.getEmail())
					.setParameter("username", employee.getUsername())
					.executeUpdate();
			session.createQuery("update Employee set parkingNumber = :parkingNumber where username = :username")
					.setParameter("parkingNumber", employee.getParkingNumber())
					.setParameter("username", employee.getUsername())
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void deleteUser(String username) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Employee.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("delete from Employee where username = :username")
					.setParameter("username", username)
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
}
