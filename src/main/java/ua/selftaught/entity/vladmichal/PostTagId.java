package ua.selftaught.entity.vladmichal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode
@Embeddable
public class PostTagId implements Serializable {
	
	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "tag_id")
	private Integer tagId;
}
