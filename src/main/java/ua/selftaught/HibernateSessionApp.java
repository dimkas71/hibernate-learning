package ua.selftaught;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.selftaught.entity.Employee;

public class HibernateSessionApp {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
	
		
		TypeReference<List<Employee>> tr = new TypeReference<List<Employee>>() {};
		
		Path userDir = Paths.get(System.getProperty("user.dir"));
		
		List<Employee> e = mapper.readValue(userDir.resolve("employees.json").toFile(),tr);
		
		System.out.println(e);
		
		
	}

}
