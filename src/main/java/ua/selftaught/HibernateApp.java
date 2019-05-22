package ua.selftaught;

import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

import ua.selftaught.entity.compositeid.Book;

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
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		loadBooks();
		
		
		doInJPA(() -> EMF, em -> {
			
			
			
		});
		
	
	}
	
	
	private static void loadBooks() throws URISyntaxException, IOException {
		
		URI uri = HibernateApp.class.getClassLoader().getResource("books.csv").toURI();
		
		final List<Book> books = new ArrayList();
		
		if (Files.exists(Paths.get(uri))) {
			
			books.addAll(
					Files.lines(Paths.get(uri))
				.map(s -> {
					String[] splitted = s.split(";");
					return new Book(
							null,
							splitted[0].trim().replaceAll("\"", ""),
							splitted[1].trim().replaceAll("\"", ""));
				})
				.collect(Collectors.toList())
				);
			
			
		} else {
			log.infov("File books.csv is not found");
		}
		
		
		
		
		doInJPA(() -> EMF, em -> {
			
			em.createQuery("select b from Book b", Book.class)
				.getResultStream()
				.forEach(em::remove);
			
			books.forEach(em::persist);
		});
		
	}

}
