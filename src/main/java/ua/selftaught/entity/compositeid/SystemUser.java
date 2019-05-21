package ua.selftaught.entity.compositeid;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
@Table(schema = "compositeid")
public class SystemUser {
	
	@EmbeddedId
	private PK pk;
	
	private String name;
	
}
