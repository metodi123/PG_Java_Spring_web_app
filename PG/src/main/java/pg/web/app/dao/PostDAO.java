package pg.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pg.web.app.model.Post;

@Repository
public class PostDAO implements PostDataAccess {
	
	public Post getPost(int id) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Post.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		Post post;
		
		try {		
			session.beginTransaction();

			post = (Post) session.createQuery("from Post where id = :id")
										.setParameter("id", id)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return post;
	}
	
	public List<Post> getAllPosts() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Post.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<Post> posts = new ArrayList<Post>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<Post> tQuery = session.createQuery("from Post post ORDER BY post.datePublished DESC", Post.class);
			posts = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return posts;
	}

	public void createPost(Post post) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Post.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			session.beginTransaction();
				
			session.save(post);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void updatePost(Post post) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Post.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.update(post);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void deletePost(Post post) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Post.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.delete(post);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}
