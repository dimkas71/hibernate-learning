package ua.selftaught.entity.vladmichal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Entity(name = "PostTag")
@Table(name = "post_tag", schema = "vladmichal")
public class PostTag {
	
	@EmbeddedId
	private PostTagId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("postId")
	private Post post;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("tagId")
	private Tag tag;
	
	public PostTag(Post post, Tag tag) {
		this.post = post;
		this.tag = tag;
		this.id = new PostTagId(post.getId(), tag.getId());
	}
	

}
