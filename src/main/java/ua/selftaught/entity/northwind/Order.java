package ua.selftaught.entity.northwind;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders", schema = "northwind")
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	private Employee employee;
	
	@OneToOne
	private Customer customer;
	
	@Column(name = "order_date")
	private LocalDate date;
	
	@Column(name = "shipped_date")
	private LocalDate shippedDate;
	
	@Column(name = "ship_name")
	private String shipName;
	
	@Column(name = "ship_address1")
	private String shipAddress1;
	
	@Column(name = "ship_address2")
	private String shipAddress2;
	
	@Column(name = "ship_city")
	private String shipCity;
	
	@Column(name = "ship_state")
	private String shipState;
	
	@Column(name = "ship_postal_code")
	private String shipPostalCode;
	
	@Column(name = "ship_country")
	private String shipCountry;
	
	@Column(name = "shipping_fee")
	private Double shippingFee;
	
	@Column(name = "payment_type")
	private String paymentType;
	
	@Column(name = "paid_date")
	private LocalDate paidDate;
	
	@Column(name = "order_status")
	private String orderStatus;
	
	@OneToMany(mappedBy = "order")
	@Fetch(FetchMode.JOIN)
	private Set<OrderDetail> details = new HashSet<>();
	
	
	
	
}
