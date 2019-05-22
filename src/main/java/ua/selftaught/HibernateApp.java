package ua.selftaught;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

import ua.selftaught.entity.compositeid.Book;
import ua.selftaught.entity.compositeid.Library;

public class HibernateApp {
private static final Logger log =  Logger.getLogger(HibernateSessionApp.class);
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa.h2");
	
	public static void main(String[] args) {
			
		doInJPA(() -> EMF, em -> {
			
			Library lib = em.find(Library.class, 1);
			
			log.infov("{0}", lib);
			
			Book b1 = em.find(Book.class, 2L);
			
			lib.getBooks().add(b1);
			
			em.persist(lib);
			
			//lib.getBooks().add(b1);
			
			//em.persist(lib);
			
			//Library lib =em.find(Library.class, 1);
			
			
			//em.persist(lib);
			
			//List<Book> books =  em.createQuery("select b from Book b", Book.class)
			//		.getResultList();
		
			
			//Library lib = em.createQuery("select l from Library l where l.id = :id", Library.class)
			//	.setParameter("id", 1)
			//	.getSingleResult();
			
			
			
			//log.infov("Books : {0}", books);
			
			//log.infov("Library : {0}", lib);
			
			//em.remove(lib);
			
		});
		
		doInJPA(() -> EMF, em -> {
			
		});
	
	}

}
