package ua.selftaught;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.selftaught.entity.Employee;
import ua.selftaught.entity.HartTrophyWinners;
import ua.selftaught.entity.User;

public class HibernateSessionApp {
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa");
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
	
		
		TypeReference<List<Employee>> tr = new TypeReference<List<Employee>>() {};
		
		Path userDir = Paths.get(System.getProperty("user.dir"));
		
		List<Employee> e = mapper.readValue(userDir.resolve("employees.json").toFile(),tr);
		
		
		SessionFactory sf = EMF.unwrap(SessionFactory.class);
		
		
		Session session = sf.openSession();
		
		Query<User> query = session.createQuery("Select u from User u");
		
		List<User> users = query.getResultList();
		
		Query<HartTrophyWinners> q = session.createQuery("Select htw from HartTrophyWinners htw");

		List<HartTrophyWinners> htws = q.getResultList();
		
		System.out.println(users);
		
		System.out.println(htws);
		
		
		session.close();
		
		sf.close();
		
		
	}

}
