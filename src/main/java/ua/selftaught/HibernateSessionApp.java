package ua.selftaught;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HibernateSessionApp {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String birthDate = "07.12.1981 0:00:00";
		
		
		LocalDate date = mapper.readValue(birthDate, LocalDate.class);
		System.out.println(date);
		
		
	}

}
