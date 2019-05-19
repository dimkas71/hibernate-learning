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
@NoArgsConstructor
@Entity
@Table(name = "products", schema = "northwind")
public class Product {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "product_code")
	private String code;
	
	@Column(name = "product_name")
	private String name;
	
	private String description;

	@Column(name = "standard_cost")
	private Double cost;
	
	@Column(name = "list_price")
	private Double price;
	
	@Column(name = "target_level")
	private Integer targetLevel;

	@Column(name = "reorder_level")
	private Integer reorderLevel;
	
	@Column(name = "minimum_reorder_quantity")
	private Integer minReorderQuantity;

	@Column(name = "quantity_per_unit")
	private String quantityPerUnit;
	
	private Integer discontinued;
	
	private String category;
	
}
