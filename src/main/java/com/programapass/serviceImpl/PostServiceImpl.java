package com.programapass.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programapass.entity.Post;
import com.programapass.exceptions.NewsNotFound;
import com.programapass.repository.IPostRepository;
import com.programapass.service.IPostService;

@Service
public class PostServiceImpl implements IPostService {

	@Autowired
	private IPostRepository postRepository;

	@Override
	public List<Post> listarPost() {

		List<Post> listaPost = new ArrayList<Post>();
		listaPost = (List<Post>) postRepository.findAll();
		return listaPost;
	}

	@Override
	public void guardarPost(Post post) {

		postRepository.save(post);

	}

	@Override
	public Post buscarPorIdPost(Long id) throws NewsNotFound {
		Optional<Post> post = postRepository.findById(id);
		if(post.isEmpty()) {
			throw new NewsNotFound();
		}
		 
		return post.get();
	}

	@Override
	public void eliminarPost(Long id) {
		postRepository.deleteById(id);

	}

}
