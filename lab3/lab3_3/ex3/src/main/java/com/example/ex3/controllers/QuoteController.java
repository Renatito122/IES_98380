package com.example.ex3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ex3.entities.*;
import com.example.ex3.services.QuoteService;
import com.example.ex3.services.MovieService;
import java.util.*;


@RestController
public class QuoteController {

    @Autowired
    private QuoteService service;

    @Autowired
    private MovieService movie_service;

    
    @PostMapping("/quote")
    public Quote addProduct(@RequestBody Quote quote) {
        if (quote.getMovie().equals(null)){
            movie_service.saveMovie(quote.getMovie());
        }
        return service.saveQuote(quote);
    }

    @GetMapping("/quotes")
    public List<Quote> findAllQuotes() {
        return service.getQuotes();
    }

    @GetMapping("/shows")
    public List<Movie> getMovies(){
        return movie_service.getMovies();
    }

    @GetMapping("/quote/{id}")
    public Quote findQuoteById(@RequestParam(value = "id", required = false) long id) {
        return service.getQuoteById(id);
    }
}