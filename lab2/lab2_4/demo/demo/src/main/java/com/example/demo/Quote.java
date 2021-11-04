package com.example.demo;

public class Quote {
    
    private final long id;
      private final String showname;
    private final String content;
  
    public Quote(long id, String content, String showname) {
          this.showname = showname;
      this.id = id;
      this.content = content;
    }
      public String getshowname() {
      return showname;
    }
  
    public long getId() {
      return id;
    }
  
    public String getContent() {
      return content;
    }
  }
