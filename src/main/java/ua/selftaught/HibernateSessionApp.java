package ua.selftaught;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class HibernateSessionApp {
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa");
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		
		
		
	
	}

}


