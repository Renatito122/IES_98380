package com.example.ex3.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "quotes")
public class Quote {

    private final long id;
    private final String content;

    @ManyToOne
    private Movie movie;
 
    public Quote(long id, String content) {
         this.id = id;
         this.content = content;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        public long getId() {
        return id;
    }
 
    @Column(name = "content", nullable = false)
    public String getContent() {
        return content;
    }

    @Column(name = "movie", nullable = false)
    public Movie getMovie(){
        return movie;
    }

    public void setMovie(Movie movie){
        this.movie=movie;
    }

    @Override
    public String toString() {
        return "Quote [id=" + id + ", content=" + content + "]";
    }
 
}

