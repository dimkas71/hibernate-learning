package ua.selftaught.entity.compositeid;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity(name = "SystemUser2")
@Table(schema = "compositeid", name = "systemuser2")
@IdClass(PK.class)
public class SystemUser2 {
	
	@Id
	private String subsystem;
	
	@Id
	private String username;
	
	private String name;
	
}
