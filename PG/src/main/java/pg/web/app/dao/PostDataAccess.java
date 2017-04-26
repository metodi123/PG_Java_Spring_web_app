package pg.web.app.dao;

import java.util.List;

import pg.web.app.model.Post;

public interface PostDataAccess {
	public Post getPost(int id) throws Exception;
	
	public List<Post> getAllPosts() throws Exception;

	public void createPost(Post post) throws Exception;
	
	public void updatePost(Post post) throws Exception;
	
	public void deletePost(Post post) throws Exception;
}