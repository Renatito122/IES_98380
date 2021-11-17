package com.example.ex3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ex3.repositories.*;
import com.example.ex3.entities.*;


import java.util.List;
@Service
public class QuoteService {
    @Autowired
    private QuoteRepository repository;

    public Quote saveQuote(Quote quote) {
        return repository.save(quote);
    }

    public List<Quote> saveQuotes(List<Quote> products) {
        return repository.saveAll(products);
    }

    public List<Quote> getQuotes() {
        return repository.findAll();
    }

    public Quote getQuoteById(long id) {
        return repository.findById(id).orElse(null);
    }
    public List<Quote> getQuoteByMovie(Movie movie){
        long id = movie.getId();
        return repository.findByMovieId(id);

    }
    public String deleteQuote(long id) {
        repository.deleteById(id);
        return "quote removed !! " + id;
    }
}