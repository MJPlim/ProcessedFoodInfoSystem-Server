package com.plim.plimserver.domain.post.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_image_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post postI;
	
	@Column(name = "post_image_address", nullable = false)
	private String postImageAddress;

	@Builder
	public PostImage(Post postI, String postImageAddress) {
		this.postI = postI;
		this.postImageAddress = postImageAddress;
		this.postI.getPostImageList().add(this);
	}
	
	
}
