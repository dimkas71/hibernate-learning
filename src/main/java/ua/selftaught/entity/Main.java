package ua.selftaught.entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class Main {
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa");
	private static final String CSV_FILE = "hart-trophy-winners.csv";
	
	public static void main(String[] args) throws IOException {
		//loadUsers();
		Path userDir = Paths.get(System.getProperty("user.dir"));

		Path csvPath = userDir.resolve(CSV_FILE);
		if (Files.exists(csvPath)) {
			try (Stream<String> lines = Files.lines(csvPath)) {

				List<HartTrophyWinners> htws = lines.filter(s -> (!s.contains("2004–05")) && (!s.contains("Season")))
						.peek(System.out::println).map(s -> {
							String[] row = s.split(";");
							return new HartTrophyWinners(null, row[0].trim(), row[1].trim(), row[2].trim(),
									row[3].trim(), Integer.parseInt(row[4].trim()));
						}).collect(Collectors.toList());

				
				//save all users
				IntStream.range(0, htws.size())
					.mapToObj(id -> {
						
						HartTrophyWinners htw = htws.get(id);
						
						return new HartTrophyWinners(Long.valueOf(id),
													 htw.getSeason(),
													 htw.getWinner(),
													 htw.getTeam(),
													 htw.getPosition(),
													 htw.getWin());
					})
					.forEach(htw -> save(htw));
					

				
			}
		}
		
	}

	
	
	private static void loadUsers() {
		
		save(new User(3L, "Adam"));
		
		update(new User(2L, "Orest"));
		
		List<User> users = findAll();
		users.stream()
			.forEach(System.out::println);
		
		delete(1L);
		
		System.out.println("After deleting user with id = 1");
		
		users = findAll();
		users.stream()
			.forEach(System.out::println);
	}

	private static void save(HartTrophyWinners htw) {
		
		EntityManager em = null;
		
		try {
			em = EMF.createEntityManager();
			em.getTransaction().begin();
			em.persist(htw);
			em.getTransaction().commit();
		} catch (PersistenceException ex) {
			System.out.println(ex.getMessage());
		} finally {
			em.close();
		}
		
	}
	
	private static void save(User u) {
		
		EntityManager em = null;
		
		try {
			em = EMF.createEntityManager();
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
		} catch (PersistenceException ex) {
			System.out.println(ex.getMessage());
		} finally {
			em.close();
		}
		
	}
	
	private static List<User> findAll() {
		
		List<User> users = new ArrayList<>();
		
		EntityManager em = null;
		
		try {
			em = EMF.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<User> q = em.createQuery("from User", User.class);
			users = q.getResultList();
			em.getTransaction().commit();
		} catch (IllegalArgumentException | PersistenceException ex) {
			System.out.println(ex.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		return Collections.unmodifiableList(users);
	}
	
	private static void update(User u) {
		EntityManager em = null;
		
		try {
			em = EMF.createEntityManager();
			em.getTransaction().begin();
			User found = em.find(User.class, u.getId());
			
			found.setName(u.getName());
			em.persist(found);
			em.getTransaction().commit();
		} catch (IllegalArgumentException | PersistenceException ex) {
			System.out.println(ex.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	private static void delete(Long id) {
		EntityManager em = null;
		
		User user = null;
		try {
			em = EMF.createEntityManager();
			em.getTransaction().begin();
			user = em.find(User.class, id);
			em.remove(user);
			em.getTransaction().commit();
		} catch (IllegalArgumentException | PersistenceException ex) {
			System.out.println(ex.getMessage());
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	
	
	
}
