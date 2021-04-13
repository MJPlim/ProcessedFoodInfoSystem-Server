package com.plim.plimserver.domain.post.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "post")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@Column(name = "user_id")			//아직 매핑 안함
	private Long userId;

	@OneToMany(mappedBy = "postC", cascade = CascadeType.ALL, orphanRemoval = true) // 생명주기를 함께합니다. 게시글이 지워지면 댓글이 지워집니다.
	private List<Comment> commentList = new ArrayList<>();

	@OneToMany(mappedBy = "postI", cascade = CascadeType.ALL, orphanRemoval = true) // 마찬가지로 게시글이 지워지면 이미지도 지워집니다.
	private List<PostImage> postImageList = new ArrayList<>();
	
	@OneToMany(mappedBy = "postL", cascade = CascadeType.ALL, orphanRemoval = true) // 마찬가지로 게시글이 지워지면 좋아요도 지워집니다.
	private List<PostLike> postLikeList = new ArrayList<>();

	@Column(name = "post_title", nullable = false)
	private String postTitle;

	@Lob
	@Column(name = "post_description", nullable = false)
	private String postDescription;

	@Column(name = "views")
	@ColumnDefault("0")
	private Long view;

	@CreationTimestamp
	@Column(name = "created_date")
	private Timestamp createdDate;

	@UpdateTimestamp
	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "post_category", nullable = false)
	private PostCategoryType category;

	@Enumerated(EnumType.STRING)
	@Column(name = "post_state", nullable = false)
	private PostStateType state;

	@Builder
	public Post(Long userId, String postTitle, String postDescription, PostCategoryType category, Long view, PostStateType state) {
		this.userId = userId;
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.view = view;
		this.category = category;
		this.state = state;
	}
	
	@PrePersist						//default값 설정을 위함
	public void prePersistView() {
		this.view = this.view == null ? 0: this.view;
	}
	
	public void postUpdate(String postTitle, String postDescription, PostCategoryType category) {
		this.postTitle = postTitle;
		this.postDescription = postDescription;
		this.category = category;
	}
	
	public void postStateUpdate(PostStateType state) {
		this.state = state;
	}
	
}
