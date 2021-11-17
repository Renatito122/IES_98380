package com.example.ex3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ex3.repositories.*;
import com.example.ex3.entities.Movie;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repository;

    public Movie saveMovie(Movie movie) {
        return repository.save(movie);
    }

    public List<Movie> saveMovies(List<Movie> movie) {
        return repository.saveAll(movie);
    }

    public List<Movie> getMovies() {
        return repository.findAll();
    }

    public Movie getMovieById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteMovie(long id) {
        repository.deleteById(id);
        return "Movie removed !! " + id;
    }
}
