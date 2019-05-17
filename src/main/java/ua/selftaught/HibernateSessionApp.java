package ua.selftaught;

import java.io.IOException;
import java.util.List;

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
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.cfg.AttributeConversionInfo;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class HibernateSessionApp {
	
	private static final Logger log =  Logger.getLogger(HibernateSessionApp.class);
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa");
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		
		final EntityManager em = EMF.createEntityManager();
		
		
		List<Object[]> users = em.createNativeQuery("select u.name, u.password from user1 u where u.id > ?")
			.setParameter(1, 3)	
			.getResultList();
		
		for (Object[] o : users) {
			log.infov("{0}", new Pair<Object, Object>(o[0], o[1]));
		}

		
		List<Tuple> results = em.createNativeQuery("select u.name as name, u.password as password from User1 u", Tuple.class)
			.getResultList();

		for (Tuple t : results) {
			log.infov("Name {0}, password {1}", t.get("name"), t.get("password"));
		}
		
		em.getTransaction().begin();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaUpdate<User> update = cb
			.createCriteriaUpdate(User.class);
			
		Root<User> root = update.from(User.class);
		
		update.set("password", "prettl");
		update.where(cb.equal(root.get("id"), 1));
		
		em.createQuery(update).executeUpdate();
		
		
		CriteriaDelete<User> delete = cb.createCriteriaDelete(User.class);
		Root<User> rootDelete = delete.from(User.class);
		
		delete.where(cb.equal(rootDelete.get("id"), 2));
		em.createQuery(delete).executeUpdate();
		
		em.getTransaction().commit();
		
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
@NamedQueries({
	@NamedQuery(
			name = "get_all_roles",
			query = "select r from Role r"
	)
})
@Entity
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
class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@Convert(converter = PasswordConverter.class)
	private String password;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Role role;
	
}


@Converter(autoApply = true)
class PasswordConverter implements AttributeConverter<String, String> {

	@Override
	public String convertToDatabaseColumn(String attribute) {
		return "abc";
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return "ddd";
	}
	
}
