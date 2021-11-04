package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;

import com.example.demo.Quotes;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {

  //private static final String template = "Here's Johnny %s!";
  private final AtomicLong counter = new AtomicLong();

  @GetMapping("/quote")
    public Quote quote(@RequestParam(value = "show", defaultValue = "") String idStr) {
        String[] quote;
        if (idStr.equals(""))
            quote = Quotes.randomQuote();
        else {
            try {
                quote = Quotes.randomQuote(Integer.parseInt(idStr));
            } catch (NumberFormatException e) {
                System.err.println("Invalid 'show' parameter: '" + idStr + "', fetching random quote.");
                quote =  Quotes.randomQuote();
            }
        }
        return new Quote(counter.incrementAndGet(), quote[0], quote[1]);
    }


  @GetMapping("/shows")
  public List<Show> shows() {
        List<Show> list = new ArrayList<>();
        Quotes.shows().forEach(show -> list.add(new Show(show)));
        return list;
    }

}
