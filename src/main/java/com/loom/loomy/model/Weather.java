package com.loom.loomy.model;

public record Weather(String agency, String weather) implements PageComponent {

  public static final Weather UNKNOWN = new Weather("", "Mostly Sunny");
}
