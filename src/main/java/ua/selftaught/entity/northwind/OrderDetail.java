package ua.selftaught.entity.northwind;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details", schema = "northwind")
public class OrderDetail implements Serializable {
	
	private static final long serialVersionUID = -8971567024956350925L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@NotNull
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Id
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	private Double quantity;
	
	@Column(name = "unit_price")
	private Double price;
	
	@NotNull
	private Double discount;
	
	@Column(name = "order_detail_status")
	private String status;
	
	@Column(name = "date_allocated")
	private LocalDate dateAllocated;
	

}
