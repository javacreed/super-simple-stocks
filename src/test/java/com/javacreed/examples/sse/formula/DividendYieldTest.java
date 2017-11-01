package com.javacreed.examples.sse.formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javacreed.examples.sse.formula.DividendYield.Common;
import com.javacreed.examples.sse.formula.DividendYield.Preferred;
import com.javacreed.examples.sse.model.FixedDivident;
import com.javacreed.examples.sse.model.LastDivident;
import com.javacreed.examples.sse.model.ParValue;
import com.javacreed.examples.sse.model.TickerPrice;

public class DividendYieldTest {

  @Test
  public void computeCommon() {
    final List<String[]> list = new ArrayList<>();
    list.add(new String[] { "8", "100.61", "0.0795" });

    for (int i = 0, size = list.size(); i < size; i++) {
      final String[] values = list.get(i);

      final LastDivident divident = LastDivident.of(values[0]);
      final TickerPrice price = TickerPrice.of(values[1]);
      final BigDecimal value = new BigDecimal(values[2]);

      final Common common = DividendYield.computeCommon(divident, price);
      Assert.assertNotNull(common);
      Assert.assertEquals(value, common.getValue());
      Assert.assertEquals(divident, common.getDivident());
      Assert.assertEquals(price, common.getPrice());
    }
  }

  @Test
  public void computePreferred() {
    final List<String[]> list = new ArrayList<>();
    list.add(new String[] { "0.02", "100", "100.61", "0.0199" });

    for (int i = 0, size = list.size(); i < size; i++) {
      final String[] values = list.get(i);

      final FixedDivident divident = FixedDivident.of(values[0]);
      final ParValue parValue = ParValue.of(values[1]);
      final TickerPrice price = TickerPrice.of(values[2]);
      final BigDecimal value = new BigDecimal(values[3]);

      final Preferred preferred = DividendYield.computePreferred(divident, parValue, price);
      Assert.assertNotNull(preferred);
      Assert.assertEquals(value, preferred.getValue());
      Assert.assertEquals(divident, preferred.getDivident());
      Assert.assertEquals(parValue, preferred.getParValue());
      Assert.assertEquals(price, preferred.getPrice());
    }
  }
}
