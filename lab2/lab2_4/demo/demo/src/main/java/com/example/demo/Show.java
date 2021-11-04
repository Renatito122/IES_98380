package com.example.demo;

public class Show {
    private int id;
    private String name;

    public Show(Shows show) {
        id = show.getId();
        name = show.getName();
    }

    public Show(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
