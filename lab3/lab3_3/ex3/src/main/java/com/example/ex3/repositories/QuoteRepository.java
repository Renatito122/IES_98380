package com.example.ex3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex3.entities.Quote;
import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>{

    List<Quote> findByMovieId(Long id);
}
