package ua.selftaught.entity.vladmichal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Tag")
@Table(name = "tag", schema = "vladmichal")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tag {
	
	@Id @GeneratedValue
	private Integer id;
	
	@NaturalId
	private String name;
	
	@OneToMany(
			mappedBy = "tag",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<PostTag> posts = new ArrayList<>();
	
	
	public Tag(String name) {
		this.name = name;
	}
}
