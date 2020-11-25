package com.programapass.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programapass.entity.Post;
import com.programapass.exceptions.NewsNotFound;
import com.programapass.service.IPostService;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private IPostService postService;

	@GetMapping
	public String listPost(Model modelo) {

		List<Post> listaPost = postService.listarPost();
		modelo.addAttribute("lista", listaPost);
		modelo.addAttribute("administrar", "Administrar Publicación");

		return "/views/gestionPost/listPost";
	}

	@GetMapping("/createPost")
	public String createPost(Model modelo) {

		Post post = new Post();

		modelo.addAttribute("post", post);
		modelo.addAttribute("accion", "Guardar");
		return "/views/gestionPost/createPost";
	}

	@PostMapping("/save")
	public String savePost(@Valid @ModelAttribute Post post, BindingResult result, Model modelo,
			RedirectAttributes attribute) {

		if (result.hasErrors()) {
			modelo.addAttribute("post", post);
			modelo.addAttribute("accion", "Guardar");
			System.out.println(post.toString());
			return "/views/gestionPost/createPost";
		} else {
			postService.guardarPost(post);
			attribute.addFlashAttribute("success", "Publicación guardada con éxito!");
			System.out.println(post.toString());
			return "redirect:/post";
		}

	}

	@GetMapping("/viewPost/{id}")
	public String mostrar(@PathVariable("id") Long idPost, Model modelo, RedirectAttributes attribute)
			throws NewsNotFound {

		Post post = null;
		post = postService.buscarPorIdPost(idPost);

		/*
		 * if (idPost > 0) { post = postService.buscarPorIdPost(idPost);
		 * 
		 * if (post == null) {
		 * 
		 * attribute.addFlashAttribute("error",
		 * "ATENCIÓN: el ID de la publicación no existe!"); return "redirect:/post"; } }
		 * else { attribute.addFlashAttribute("error",
		 * "ATENCIÓN: Error con el ID de la publicación!"); return "redirect:/post"; }
		 */

		modelo.addAttribute("post", post);
		return "/views/gestionPost/viewsPost";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long idPost, Model modelo, RedirectAttributes attribute)
			throws NewsNotFound {

		Post post = null;

		if (idPost > 0) {
			post = postService.buscarPorIdPost(idPost);

			if (post == null) {
				attribute.addFlashAttribute("error", "ATENCIÓN: el ID de la publicación no existe!");
				return "redirect:/post";
			}
		} else {
			attribute.addFlashAttribute("error", "ATENCIÓN: Error con el ID de la publicación!");
			return "redirect:/post";
		}

		modelo.addAttribute("post", post);
		return "/views/gestionPost/createPost";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long idPost, RedirectAttributes attribute) throws NewsNotFound {

		Post post = null;

		if (idPost > 0) {
			post = postService.buscarPorIdPost(idPost);

			if (post == null) {
				attribute.addFlashAttribute("error", "ATENCIÓN: el ID de la publicación no existe!");
				return "redirect:/post";
			}
		} else {
			attribute.addFlashAttribute("error", "ATENCIÓN: Error con el ID de la publicación!");
			return "redirect:/post";
		}

		postService.eliminarPost(idPost);
		attribute.addFlashAttribute("warning", "Publicación eliminada con éxito!");
		return "redirect:/post";
	}

}
