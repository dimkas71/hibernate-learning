package ua.selftaught.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

public class Employee {
	
	public enum Male {
		MALE,
		FEMALE
	}
	
	private String uuid;
	private String code;
	private String name;
	private LocalDate birthDate;
	private Male male;
	
	public Employee() {}

	public Employee(String uuid, String code, String name, LocalDate birthDate, Male male) {
		super();
		this.uuid = uuid;
		this.code = code;
		this.name = name;
		this.birthDate = birthDate;
		this.male = male;
	}

	
	public String getUuid() {
		return uuid;
	}

	@JsonProperty("UUID")
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCode() {
		return code;
	}
	@JsonProperty("Code")
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	@JsonProperty("BirthDate")
	@JsonDeserialize(converter = LocalDateDeserializer.class)
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Male getMale() {
		return male;
	}

	@JsonProperty("Male")
	public void setMale(Male male) {
		this.male = male;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee(uuid=").append(uuid).append(", code=").append(code).append(", name=").append(name)
				.append(", birthDate=").append(birthDate).append(", male=").append(male).append(")");
		return builder.toString();
	}
	
	
	
}

class LocalDateDeserializer implements Converter<String, LocalDate> {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:SS");
	
	@Override
	public LocalDate convert(String value) {
		return LocalDate.parse(value, FORMATTER);
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		return typeFactory.constructType(String.class);
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		return typeFactory.constructType(LocalDate.class);
	}
	
	
}
