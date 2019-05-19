package ua.selftaught.entity.northwind;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Entity
@Table(schema = "northwind", name = "customers")
public class Customer {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "first_name")
	private String firstName;
	
	private String email;
	
	private String company;
	
	private String phone;
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private String state;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	private String country;

}
