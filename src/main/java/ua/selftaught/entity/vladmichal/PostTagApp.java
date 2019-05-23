package ua.selftaught.entity.vladmichal;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.jboss.logging.Logger;


public class PostTagApp {
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa.h2");
	private static final Logger log = Logger.getLogger(PostTagApp.class);
	
	public static void main(String[] args) {
		
		doInJPA(()-> EMF, em -> {
			
			
			Session session = em.unwrap(Session.class);
			
			Tag misc = session.bySimpleNaturalId(Tag.class)
					.load("Misc");
			
			log.infov("{0}", misc.getName());
			
			Post p = em.createQuery("select p from Post p where p.id = :id", Post.class)
				.setParameter("id", 13)
				.getSingleResult();
			
			log.infov("{0}", p.getTitle());
			
			p.removeTag(misc);
			
		});
		

	}

}
