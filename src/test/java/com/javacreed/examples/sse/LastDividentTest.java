package com.javacreed.examples.sse;

import org.junit.Assert;
import org.junit.Test;

public class LastDividentTest {

  @Test
  public void equalityIgnoreScale() {
    final LastDivident a = LastDivident.of("1");
    final LastDivident b = LastDivident.of("1.00");

    Assert.assertEquals(a, b);
    Assert.assertEquals(b, a);
    Assert.assertEquals(a.hashCode(), b.hashCode());
    Assert.assertEquals(0, a.compareTo(b));
    Assert.assertEquals(0, b.compareTo(a));
  }
}
