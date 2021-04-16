package com.plim.plimserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import com.plim.plimserver.domain.post.domain.Comment;
import com.plim.plimserver.domain.post.domain.CommentLike;
import com.plim.plimserver.domain.post.domain.Post;
import com.plim.plimserver.domain.post.domain.PostCategoryType;
import com.plim.plimserver.domain.post.domain.PostImage;
import com.plim.plimserver.domain.post.domain.PostLike;
import com.plim.plimserver.domain.post.domain.PostStateType;
import com.plim.plimserver.domain.post.repository.CommentLikeRepository;
import com.plim.plimserver.domain.post.repository.CommentRepository;
import com.plim.plimserver.domain.post.repository.PostImageRepository;
import com.plim.plimserver.domain.post.repository.PostLikeRepository;
import com.plim.plimserver.domain.post.repository.PostRepository;
import com.plim.plimserver.global.config.DatabaseConfig;

@DataJpaTest
@Import(DatabaseConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PostTest {

	@Autowired
	private EntityManager em;

	@Autowired
	private PostRepository postrepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostImageRepository postImageRepository;

	@Autowired
	private PostLikeRepository postLikeRepository;

	@Autowired
	private CommentLikeRepository commentLikeRepository;
	//-----------------------------CREATE------------------------------------
	@Test
	@Rollback(false)
	public void createTest() {
		Post post = Post.builder().userId(162L).postTitle("테스트게시글타이틀").postDescription("테스트게시글내용")
				.category(PostCategoryType.ICECREAM).state(PostStateType.NORMAL).build();
		postrepository.save(post);

		Comment comment = Comment.builder().userId(162L).postC(post).commentDescription("테스트댓글 내용")
				.state(PostStateType.NORMAL).build();
		commentRepository.save(comment);

		PostImage postImage = PostImage.builder().postI(post).postImageAddress("테스트 게시글 이미지주소").build();
		postImageRepository.save(postImage);
		
		PostLike postLike = PostLike.builder().postL(post).userId(162L).build();
		postLikeRepository.save(postLike);
		
		CommentLike commentLike = CommentLike.builder().comment(comment).userId(162L).build();
		commentLikeRepository.save(commentLike);
	}
	
	//-----------------------------READ------------------------------------
	@Test
	@Rollback(false)
	public void postReadTest() {
		em.flush();
		em.clear();
		
		Optional<Post> findPost = postrepository.findById(217L);
		
		assertThat(findPost.get().getPostTitle()).isEqualTo("테스트게시글타이틀");
		assertThat(findPost.get().getPostDescription()).isEqualTo("테스트게시글내용");
		assertThat(findPost.get().getCategory()).isEqualTo(PostCategoryType.ICECREAM);
		assertThat(findPost.get().getState()).isEqualTo(PostStateType.NORMAL);
	}
	
	@Test
	@Rollback(false)
	public void commentReadTest() {
		em.flush();
		em.clear();
		
		Optional<Comment> findComment = commentRepository.findById(118L);
		
		assertThat(findComment.get().getCommentDescription()).isEqualTo("테스트댓글 내용");
		assertThat(findComment.get().getState()).isEqualTo(PostStateType.NORMAL);
		assertThat(findComment.get().getPostC().getPostTitle()).isEqualTo("테스트게시글타이틀");
	}
	
	@Test
	@Rollback(false)
	public void postImageReadTest() {
		em.flush();
		em.clear();
		
		Optional<PostImage> findPostImage = postImageRepository.findById(62L);
		assertThat(findPostImage.get().getPostImageAddress()).isEqualTo("테스트 게시글 이미지주소");
		assertThat(findPostImage.get().getPostI().getPostTitle()).isEqualTo("테스트게시글타이틀");
	}
	
	@Test
	@Rollback(false)
	public void postLikeReadTest() {
		em.flush();
		em.clear();
		
		Optional<PostLike> findPostLike = postLikeRepository.findById(9L);
		System.out.println(findPostLike.get().getPostL().getId());
		System.out.println(findPostLike.get().getUserId());
		
		assertThat(findPostLike.get().getPostL().getPostTitle()).isEqualTo("테스트게시글타이틀");
		assertThat(findPostLike.get().getUserId()).isEqualTo(162L);
	}
	
	@Test
	@Rollback(false)
	public void commentLikeReadTest() {
		em.flush();
		em.clear();
		
		Optional<CommentLike> findCommentLike = commentLikeRepository.findById(9L);
		System.out.println(findCommentLike.get().getComment().getId());
		System.out.println(findCommentLike.get().getUserId());
		
		assertThat(findCommentLike.get().getComment().getCommentDescription()).isEqualTo("테스트댓글 내용");
		assertThat(findCommentLike.get().getUserId()).isEqualTo(162L);
	}
	
	//-----------------------------UPDATE------------------------------------
	@Test
	@Rollback(false)
	public void postUpdateTest() {
		em.flush();
		em.clear();
		
		Optional<Post> findPost = postrepository.findById(217L);
		findPost.get().postUpdate("변경된 게시글 제목", "변경된 게시글 내용", PostCategoryType.SNACK);
		findPost.get().postStateUpdate(PostStateType.DELETED);
		findPost.get().plusViewCount();
	}
	
	@Test
	@Rollback(false)
	public void commentUpdateTest() {
		em.flush();
		em.clear();
		
		Optional<Comment> findComment = commentRepository.findById(118L);
		findComment.get().commentUpdate("변경된 댓글 내용");
		findComment.get().commentStateUpdate(PostStateType.DELETED);
	}
	
	//-----------------------------DELETE-----------------------------------
	@Test
	@Rollback(false)
	public void postDeleteTest() {
		em.flush();
		em.clear();
		
		Optional<Post> findPost = postrepository.findById(217L);
		postrepository.delete(findPost.get());
		
		Optional<Post> deletedPost = postrepository.findById(217L);
		assertFalse(deletedPost.isPresent());
	}
	
	@Test
	@Rollback(false)
	public void commentDeleteTest() {
		em.flush();
		em.clear();
		
		Optional<Comment> findComment = commentRepository.findById(118L);
		commentRepository.delete(findComment.get());
		
		Optional<Comment> deletedComment = commentRepository.findById(118L);
		assertFalse(deletedComment.isPresent());
	}
	@Test
	@Rollback(false)
	public void postImageDeleteTest() {
		em.flush();
		em.clear();
		
		Optional<PostImage> findPostImage = postImageRepository.findById(62L);
		postImageRepository.delete(findPostImage.get());
		
		Optional<PostImage> deletedPostImage = postImageRepository.findById(62L);
		assertFalse(deletedPostImage.isPresent());
	}
	@Test
	@Rollback(false)
	public void postLikeDeleteTest() {
		em.flush();
		em.clear();
		
		Optional<PostLike> findPostLike = postLikeRepository.findById(9L);
		postLikeRepository.delete(findPostLike.get());
		
		Optional<PostLike> deletedPostLike = postLikeRepository.findById(9L);
		assertFalse(deletedPostLike.isPresent());
	}
	@Test
	@Rollback(false)
	public void commentLikeDeleteTest() {
		em.flush();
		em.clear();
		
		Optional<CommentLike> findCommentLike = commentLikeRepository.findById(9L);
		commentLikeRepository.delete(findCommentLike.get());
		
		Optional<CommentLike> deletedCommentLike = commentLikeRepository.findById(9L);
		assertFalse(deletedCommentLike.isPresent());
	}
}
