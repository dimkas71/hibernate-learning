package ua.selftaught.entity.compositeid;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PK implements Serializable {
	
	private static final long serialVersionUID = 8763304612970876276L;
	
	private String subsystem;
	private String username;
}
