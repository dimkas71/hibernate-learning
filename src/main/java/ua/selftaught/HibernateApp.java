package ua.selftaught;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

import ua.selftaught.entity.compositeid.Book;
import ua.selftaught.entity.compositeid.SystemUser;
import ua.selftaught.entity.compositeid.SystemUser2;

public class HibernateApp {
private static final Logger log =  Logger.getLogger(HibernateSessionApp.class);
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa.h2");
	
	public static void main(String[] args) {
			
		doInJPA(() -> EMF, em -> {
			List<Book> books =  em.createQuery("select b from Book b", Book.class)
					.getResultList();
			log.infov("{0}", books);
		});
		
		doInJPA(() -> EMF, em -> {
			
			
			List<SystemUser> systemUsers = em.createQuery("select s from SystemUser s", SystemUser.class)
				.getResultList();
			
			log.infov("System users: {0}", systemUsers);
			
			
			//em.persist(new SystemUser2("unix", "root", "dimkas"));
			//em.persist(new SystemUser2("windows", "Admin", "Dima"));
			
			List<SystemUser2> systemUsers2 = em.createQuery("select s from SystemUser2 s", SystemUser2.class)
				.getResultList();
			
			log.infov("System user 2", systemUsers2);
			
		});
	
	}

}
