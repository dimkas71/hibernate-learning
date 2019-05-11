package ua.selftaught.test;

import static org.hibernate.testing.transaction.TransactionUtil.doInHibernate;
import static org.hibernate.testing.transaction.TransactionUtil.doInJPA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.RollbackException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

class OneToOneTest {
	
	private static EntityManagerFactory emf;
		
	@BeforeAll
	static void setUpAll() {
		emf = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa.test");
	}
	
	@AfterAll
	static void tearDownAll() {
		emf.close();
	}
	
	
	@Test
	@DisplayName("when create a new user then size of the table increase by one")
	void whenCreateANewUserThenSizeOfTheTableIncreaseByOne() {
		
		Long increment = doInHibernate(() -> emf.unwrap(SessionFactory.class), null, session -> {
			
			Long countBefore = session.createQuery("select count(u) from User u", Long.class)
					.getSingleResult();
			
			User u = new User(null, "Dimkas", "secret", null);
			session.save(u);
			
			Long countAfter = session.createQuery("select count(u) from User u", Long.class)
					.getSingleResult();
			return countAfter - countBefore;
		});
		
		assertEquals(1, increment, () -> "Should be 1");
	}
	
	@Test
	@DisplayName("when a user with role 'Admin' was created then user's role should be equal to 'Admin'")
	void whenUserWithRoleAdminIsCreatedThenUsersRoleShouldBeAdmin() {
		
		Long count = doInJPA(() -> emf, (em) -> {
			Role r = new Role(null, "Admin");
			
			em.persist(r);
			
			User u = new User(null, "Dimkas", "secret", r);
			em.persist(u);
			
			Long tmp = em.createQuery("select count(u) from User u where u.role.name = :name", Long.class)
					.setParameter("name", r.getName())
					.getSingleResult();
			return tmp;
		}, null);
		
		assertTrue(count > 0, () -> "Count should be greater then zero");
		
		
	}
	
	@Test
	@DisplayName("When an entity is deleted then exceptions is thrown")
	void whenAnExternalEntityIsDeletedThenExceptionIsThrown() {
		
		RollbackException re = Assertions.assertThrows(RollbackException.class, () -> {
			doInJPA(() -> emf, em -> {

				Role r = new Role(null, "Admin");

				em.persist(r);

				User u = new User(null, "Dimkas", "secret", r);

				em.persist(u);

				em.remove(r);

			});
		});
		
		assertEquals("Error while committing the transaction", re.getMessage());
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
