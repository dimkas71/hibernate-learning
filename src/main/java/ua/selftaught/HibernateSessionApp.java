package ua.selftaught;

import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Table;

import org.jboss.logging.Logger;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.selftaught.entity.northwind.Customer;
import ua.selftaught.entity.northwind.Employee;
import ua.selftaught.entity.northwind.Product;

public class HibernateSessionApp {
	
	private static final Logger log =  Logger.getLogger(HibernateSessionApp.class);
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa");
	
	public static void main(String[] args) {
		
		
		final EntityManager em = EMF.createEntityManager();
		
		try {
		
		em.getTransaction().begin();
	
		
		List<Customer> customers = em.createQuery("select c from Customer c", Customer.class)
			.getResultList();
		
		log.infov("{0}", customers);
		
		List<Employee> employees = em.createQuery("select e from Employee e", Employee.class)
			.getResultList();
		
		log.infov("{0}", employees);
		
		log.infov("{0}", employees
				.stream()
				.map(e -> e.getAvatar())
				.distinct()
				.collect(Collectors.toList()));
		
		List<Product> products = em.createQuery("select p from Product p", Product.class)
			.getResultList();
		
		log.infov("{0}", products);
		
		ua.selftaught.entity.northwind.Order order = em.createQuery("select o from Order o where o.id = :id", ua.selftaught.entity.northwind.Order.class)
				.setParameter("id", 4011)
				.getSingleResult();
				
		log.infov("{0}",order);		
		
		em.getTransaction().commit();
		} catch (PersistenceException ex) {
			log.infov("{0}", ex.getMessage());
			
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		em.close();
		
	}
	
	private static class Pair<T1, T2> {
		private T1 first;
		private T2 second;
		
		public Pair(T1 first, T2 second) {
			super();
			this.first = first;
			this.second = second;
		}

		public T1 getFirst() {
			return first;
		}

		public T2 getSecond() {
			return second;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Pair [first=").append(first).append(", second=").append(second).append("]");
			return builder.toString();
		}
		
		
		
	}
	

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user"})
@NamedQueries({
	@NamedQuery(
			name = "get_all_roles",
			query = "select r from Role r"
	)
})
@Entity
@Table(name = "roles")
class Role {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@OneToOne
	private User user;
	
}

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "User1")
@Table(name = "users")
class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@Convert(converter = PasswordConverter.class)
	private String password;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Role role;
	
}


@Converter(autoApply = false)
class PasswordConverter implements AttributeConverter<String, String> {

	private static final String Algorithm = "AES/ECB/PKCS5Padding";
	private static final byte[] KEY = "MySuperSecretKey".getBytes();
	
	@Override
	public String convertToDatabaseColumn(String attribute) {
		Key key = new SecretKeySpec(KEY, "AES");
		try {
			Cipher cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		Key key = new SecretKeySpec(KEY, "AES");
		try {
			Cipher cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Base64.getDecoder().decode(dbData.getBytes())));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
