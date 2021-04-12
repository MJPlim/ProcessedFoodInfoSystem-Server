package com.plim.plimserver.domain.post.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	private Long userId;				//아직 매핑 안함
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post postC;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentLike> commentLikeList = new ArrayList<>();
	
	@Lob							//생각해보니 게시글 제한수랑 댓글 제한수? 몇자?
	@Column(name = "comment_description", nullable = false)
	private String commentDescription;
	
	@CreationTimestamp
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@UpdateTimestamp
	@Column(name = "modified_date")
	private Timestamp modifiedDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "comment_state")
	private PostStateType state;

	@Builder
	public Comment(Long userId, Post postC, String commentDescription, 
			PostStateType state) {
		this.userId = userId;
		this.postC = postC;
		this.commentDescription = commentDescription;
		this.state = state;
		this.postC.getCommentList().add(this);
	}
	
	
	
}
