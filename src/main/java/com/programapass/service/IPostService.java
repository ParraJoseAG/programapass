package com.programapass.service;

import java.util.List;

import com.programapass.entity.Post;
import com.programapass.exceptions.NewsNotFound;

public interface IPostService {
	
	public List<Post> listarPost();

	public void guardarPost(Post post);

	public Post buscarPorIdPost(Long id) throws NewsNotFound;

	public void eliminarPost(Long id);

}
