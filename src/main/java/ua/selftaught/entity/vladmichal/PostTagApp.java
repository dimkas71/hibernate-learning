package ua.selftaught.entity.vladmichal;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.jboss.logging.Logger;


public class PostTagApp {
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa.h2");
	private static final Logger log = Logger.getLogger(PostTagApp.class);
	
	public static void main(String[] args) {
		/*
		Tag misc = new Tag("Misc");
		Tag jdbc = new Tag("JDBC");
		Tag jooq = new Tag("JOOQ");
		Tag hibernate = new Tag("Hibernate");
		*/
		
		doInJPA(()-> EMF, em -> {
			/*
			em.persist(misc);
			em.persist(jdbc);
			em.persist(jooq);
			em.persist(hibernate);
			*/
			
			List<Tag> tags = em.createQuery("Select t from Tag t", Tag.class)
				.getResultList();
			
			log.infov("Tags : {0}", tags);
			
			
			Session session = em.unwrap(Session.class);
			
			Tag misc = session.bySimpleNaturalId(Tag.class)
				.load("Misc");
			
			Tag hibernate = session.bySimpleNaturalId(Tag.class)
				.load("Hibernate");
			
			Tag jooq = session.bySimpleNaturalId(Tag.class)
					.load("JOOQ");
			
			Tag jdbc = session.bySimpleNaturalId(Tag.class)
					.load("JDBC");
					
			
			log.infov("{0}", jdbc);
			
			
			Post hpjp1 = new Post("High-Performance Java Persistence 1st edition");
			
			hpjp1.setId(1);
			
			hpjp1.addTag(misc);
			hpjp1.addTag(jdbc);
			hpjp1.addTag(jooq);
			hpjp1.addTag(hibernate);
			
			session.save(hpjp1);
			
			Post hpjp2 = new Post(
				    "High-Performance Java Persistence 2nd edition"
				);
				hpjp2.setId(2);
				 
				hpjp2.addTag(jdbc);
				hpjp2.addTag(hibernate);
				hpjp2.addTag(jooq);
				 
				session.save(hpjp2);
			
			
		});
		

	}

}
