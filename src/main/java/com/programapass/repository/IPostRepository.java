package com.programapass.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.programapass.entity.Post;
@Repository
public interface IPostRepository extends CrudRepository<Post, Long> {

}
