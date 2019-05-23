package ua.selftaught.entity.vladmichal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Post")
@Table(name = "post", schema = "vladmichal")
public class Post {
	
	@Id @GeneratedValue
	private Integer id;
	
	private String title;
	
	@OneToMany(mappedBy = "post",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<PostTag> tags = new ArrayList<>();
			
	public Post(String title) {
		this.title = title;
	}
	
	public void addTag(Tag tag) {
		PostTag postTag = new PostTag(this, tag);
		tags.add(postTag);
		tag.getPosts().add(postTag);
	}
	
	public void removeTag(Tag tag) {
		for (Iterator<PostTag> it = tags.iterator(); it.hasNext();) {
			PostTag pt = it.next();
			if (pt.getPost().equals(this) && 
					pt.getTag().equals(tag)) {
				it.remove();
				pt.getTag().getPosts().remove(pt);
				pt.setPost(null);
				pt.setTag(null);
			}
		}
		
	}
	
}
