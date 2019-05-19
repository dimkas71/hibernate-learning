package ua.selftaught.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import ua.selftaught.entity.Employee.Male;

@Entity(name = "Employee1")
@Table(name = "employees")
public class Employee {
	
	public enum Male {
		MALE,
		FEMALE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	private String uuid;
	private String code;
	private String name;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	private String uniqueId;
	
	@Enumerated(EnumType.STRING)
	private Male male;
	
	

	public Employee() {}

	public Employee(Long id, String uuid, String code, String name, LocalDate birthDate, String uniqueId, Male male) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.code = code;
		this.name = name;
		this.birthDate = birthDate;
		this.uniqueId = uniqueId;
		this.male = male;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long newId) {
		this.id = newId;
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
	@JsonDeserialize(converter = LocalDateConverter.class)
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Male getMale() {
		return male;
	}

	@JsonProperty("Male")
	@JsonDeserialize(converter = MaleConverter.class)
	public void setMale(Male male) {
		this.male = male;
	}
	
	public String getUniqueId() {
		return uniqueId;
	}
	
	@JsonProperty("UniqueId")
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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
				.append(", birthDate=").append(birthDate).append(", uniqueId=").append(uniqueId).append(", male=")
				.append(male).append(")");
		return builder.toString();
	}

	
	
	
}

class LocalDateConverter implements Converter<String, LocalDate> {

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

class MaleConverter implements Converter<String, Employee.Male> {
	private static final String MALE = "Мужской";
	private static final String FEMALE = "Женский";
	
	@Override
	public Male convert(String value) {
		Employee.Male m = Employee.Male.MALE;
		if (value.equalsIgnoreCase(FEMALE)) {
			m = Male.FEMALE;
		}
		return m;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		return typeFactory.constructType(String.class);
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		return typeFactory.constructType(Employee.Male.class);
	}
	
}
