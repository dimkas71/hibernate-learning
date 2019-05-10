package ua.selftaught.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	@DisplayName("when create a new user then size of the table increase by one")
	void whenCreateANewUserThenSizeOfTheTableIncreaseByOne() {
		
		Long countBefore = em.createQuery("select count(u) from User u", Long.class)
			.getSingleResult();
		
		User u = new User(null, "Dimkas", "secret", null);
		
		save(u);
		
		Long countAfter = em.createQuery("select count(u) from User u", Long.class)
							.getSingleResult();
		
		assertEquals(1, countAfter - countBefore, () -> "Should be 1");
		
	}
	
	@Test
	@DisplayName("when a user with role 'Admin' was created then user's role should be equal to 'Admin'")
	void whenUserWithRoleAdminIsCreatedThenUsersRoleShouldBeAdmin() {
		
		Role r = new Role(null, "Admin");
		save(r);
		
		User u = new User(null, "Dimkas", "secret", r);
		save(u);
		
		
		Long count = em.createQuery("select count(u) from User u where u.role.name = :name", Long.class)
			.setParameter("name", r.getName())
			.getSingleResult();
		
		assertTrue(count > 0, () -> "Count should be greater then zero");
		
	
		
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
