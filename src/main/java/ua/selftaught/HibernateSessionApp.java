package ua.selftaught;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.selftaught.entity.Employee;

public class HibernateSessionApp {
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa");
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		TypeReference<List<Employee>> tr = new TypeReference<List<Employee>>() {
		};

		Path userDir = Paths.get(System.getProperty("user.dir"));

		List<Employee> employees = mapper.readValue(userDir.resolve("employees.json").toFile(), tr);

		
		System.out.println(employees);
		
		EntityManager em = EMF.createEntityManager();
		try {
			em.getTransaction().begin();

			employees.forEach(e -> {
				e.setId(null);
				em.persist(e);
			});
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	
	}

}
