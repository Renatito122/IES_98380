package com.example.demo;

import java.util.List;
import java.util.Random;

public class Shows {
    private static int idCount = 0;
    private int id;
    private String name;
    private List<String> quotes;

    public Shows(String name, List<String> quotes) {
        id = idCount++;
        this.name = name;
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }

    public String[] getQuote() {
        Random rand = new Random();
        return new String[]{name, quotes.get(rand.nextInt(quotes.size()))};
    }

    public boolean hasQuotes() {
        return quotes.size() > 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getQuotes() {
        return quotes;
    }
}
