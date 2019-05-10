package ua.selftaught;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.Parent;
import org.hibernate.annotations.Target;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class HibernateSessionApp {
	
	private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ua.selftaught.hibernate.jpa");
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		
		EntityManager em = EMF.createEntityManager();
		try {
			
			//em.getTransaction().begin();
			//em.persist(new City(null, "Chernivtsy", new GPS(23.333, 23.444)));
			
			List<City> cities = em.createQuery("Select c from City c", City.class).getResultList();
			
			//em.getTransaction().commit();
			System.out.println(cities);
			
			
		} catch (PersistenceException e) {
			//em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}	
		
	
	}

}


interface Coordinates {
	double x();
	double y();
}

@Embeddable 
class GPS implements Coordinates {
	
	private double latitude;
	private double longitude;
	
	@Parent
	private City city;
	
	private GPS() {}
	
	public GPS(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public City getCity() {
		return city;
	}
	
	public void setCity(City aCity) {
		this.city = aCity;
	}

	@Override
	public double x() {
		return latitude;
	}

	@Override
	public double y() {
		return longitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GPS other = (GPS) obj;
		return Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GPS [latitude=").append(latitude).append(", longitude=").append(longitude).append("]");
		return builder.toString();
	}
	
	
	
	
}

@Entity
class City {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@Embedded
	@Target(GPS.class)
	private Coordinates coordinates;
	
	public City() {}

	public City(Long id, String name, Coordinates coordinates) {
		super();
		this.id = id;
		this.name = name;
		this.coordinates = coordinates;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coordinates, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		return Objects.equals(coordinates, other.coordinates) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [id=").append(id).append(", name=").append(name).append(", coordinates=")
				.append(coordinates).append("]");
		return builder.toString();
	}
	
	
	
	
	
}

