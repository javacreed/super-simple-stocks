package com.javacreed.examples.sse.model;

public enum Type {

  COMMON("Common"), PREFERRED("Preferred");

  private final String title;

  private Type(final String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return title;
  }
}
