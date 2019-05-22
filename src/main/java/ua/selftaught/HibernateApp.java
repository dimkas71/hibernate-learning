package ua.selftaught;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

import ua.selftaught.entity.northwind.Customer;

public class HibernateApp {
private static final Logger log =  Logger.getLogger(HibernateSessionApp.class);
	
	private static final EntityManagerFactory EMF;
	static {
		
		Map<String, String> props = new HashMap<>();
		
		props.put("javax.persistence.jdbc.url", "jdbc:h2:tcp://localhost/~/test");
		props.put("javax.persistence.jdbc.driver","org.h2.Driver");
		props.put("javax.persistence.jdbc.user", "sa");
		props.put("javax.persistence.jdbc.password", "");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		
		EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa.h3", props);
	}
	
	public static void main(String[] args) {
			
		doInJPA(() -> EMF, em -> {
			
			List<Customer> customers = em.createQuery("select c from Customer c", Customer.class)
				.getResultList();
			
			
			log.infov("{0}", "Hello JPA");
			
			log.infov("Customers : {0}", customers);
			
		});
		
	
	}

}
