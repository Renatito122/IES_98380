package com.example.ex3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex3.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
