package ua.selftaught.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

class OneToOneTest {
	
	private static EntityManagerFactory emf;
	
	private EntityManager em;
	
	@BeforeAll
	static void setUpAll() {
		emf = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa.test");
	}
	
	@BeforeEach
	void setUp() {
		this.em = emf.createEntityManager();
		
	}
	
	@AfterAll
	static void tearDownAll() {
		emf.close();
	}
	
	void tearDown() {
		em.close();
	}
	
	
	@Test
	@DisplayName("When create a user entity then the size of user's list is equal to one")
	void whenCreateUserThenListSizeNonZero() {
		
		//act
		User u = em.createQuery("Select u from User u where u.name = :name", User.class)
			.setParameter("name", "Dimkas")
			.getSingleResult();
		
		if (u == null) {
			
			//arrange
			User user = new User(null, "Dimkas", "secret", null);
			save(user);
		}
		
		
		List<User> users = em.createQuery("Select u from User u", User.class)
			.getResultList();
		
		//assert
		assertEquals(1, users.size(), "Size should be 1");
		assertEquals(1L, users.get(0).getId(), () -> "Should be equal to 1");
		assertEquals("secret", users.get(0).getPassword(), () -> "the 'secret' should be an answer for it");
		
	}
	
	@Test
	@DisplayName("when a user with role 'Admin' was created then user's role should be equal to 'Admin'")
	void whenUserWithRoleAdminIsCreatedThenUsersRoleShouldBeAdmin() {
		
		Role r = new Role(null, "Admin");
		save(r);
		
		// act
		User u = em.createQuery("Select u from User u where u.name = :name", User.class).setParameter("name", "Dimkas")
				.getSingleResult();

		if (u == null) {
			// arrange
			User user = new User(null, "Dimkas", "secret", r);
			save(user);
		}
		
		User created = em.createQuery("Select u from User u", User.class)
			.getSingleResult();
		
		
		assertEquals("Admin", created.getRole().getName(), () -> "User with role 'Admin' should be created" );
		
		
	}
	
	private <T> void save(T t) {
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch(PersistenceException e) {
			em.getTransaction().rollback();
		}
	}
	
	private <T> void delete(T t) {
		try {
			em.getTransaction().begin();
			em.remove(t);
			em.getTransaction().commit();
		} catch(PersistenceException e) {
			em.getTransaction().rollback();
		}
	}
	
	private void deleteAllUsers() {
		try {
			em.getTransaction().begin();
			int rowsUpdated = em.createQuery("Delete User u")
				.executeUpdate();
			System.out.println("Updated rows----------------------------------------" + rowsUpdated);
			em.getTransaction().commit();
		} catch(PersistenceException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
		
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
class Role {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
class User {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String password;
	
	@OneToOne(fetch = FetchType.LAZY, optional =  true)
	private Role role;
}
