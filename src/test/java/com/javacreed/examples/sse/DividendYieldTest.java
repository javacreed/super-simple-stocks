package com.javacreed.examples.sse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.examples.sse.DividendYield.Common;
import com.javacreed.examples.sse.DividendYield.Preferred;

public class DividendYieldTest {

  @Test
  public void computeCommon() {
    final List<String[]> list = new ArrayList<>();
    list.add(new String[] { "8", "100.61", "0.079515" });

    for (int i = 0, size = list.size(); i < size; i++) {
      final String[] values = list.get(i);

      final LastDividend dividend = LastDividend.of(values[0]);
      final TickerPrice price = TickerPrice.of(values[1]);
      final BigDecimal value = new BigDecimal(values[2]);

      final Common common = DividendYield.computeCommon(dividend, price);
      Assert.assertNotNull(common);
      Assert.assertEquals(value, common.getValue());
      Assert.assertEquals(dividend, common.getDividend());
      Assert.assertEquals(price, common.getPrice());
    }
  }

  @Test
  public void computePreferred() {
    final List<String[]> list = new ArrayList<>();
    list.add(new String[] { "0.02", "100", "100.61", "0.0199" });

    for (int i = 0, size = list.size(); i < size; i++) {
      final String[] values = list.get(i);

      final FixedDividend dividend = FixedDividend.of(values[0]);
      final ParValue parValue = ParValue.of(values[1]);
      final TickerPrice price = TickerPrice.of(values[2]);
      final BigDecimal value = new BigDecimal(values[3]);

      final Preferred preferred = DividendYield.computePreferred(dividend, parValue, price);
      Assert.assertNotNull(preferred);
      Assert.assertEquals(value, preferred.getValue());
      Assert.assertEquals(dividend, preferred.getDividend());
      Assert.assertEquals(parValue, preferred.getParValue());
      Assert.assertEquals(price, preferred.getPrice());
    }
  }
}
