package com.loom.loomy.model;

public record Quotation(String agency, int quotation) implements PageComponent {
  public static class QuotationException extends RuntimeException {}
}
