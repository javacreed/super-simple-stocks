# Super Simple Stocks

The super simple stocks is a simple application that uses domain driven design to build a small trading platform that performs few small things.

The `Stocks` class contains all stocks that are being traded, including both common stock and preferred stock.


```
final Stocks stocks = Stocks.create();
```

The _common_ or _preferred_ stock can be accessed by invoking the `common()` or `preferred()` methods on the `stocks` instance respectively.   

```
Stocks.Common commonStocks = stocks.common();
Stocks.Preferred preferredStocks = stocks.preferred();
```

The stocks start empty.  To register a _common_ stock, simply invoke the `getOrCreate()` method.  This will only create a new stock entry if one is not found for the given symbol, otherwise, it returns the existing entry.

```
final Stock.Common pop = commonStocks.getOrCreate(StockSymbol.of("POP"), () -> LastDividend.of(new BigDecimal("50")));
```

The above code fragment returns a common stock with the symbol: `POP` and the last dividend of `50`.  In the event that another common stock already exists with that name, the existing one is returned instead and the second parameter is never used.  Therefore, the returned stock may or may not have the last dividend of 50.

Stocks can be traded by using the `buy()` and/or `sell()` methods respectively.  Following are some trading examples.
    
```
pop.buy(TradeParameters.of(Quantity.of(100), Price.of("15.43")));
pop.buy(TradeParameters.of(Quantity.of(150), Price.of("15.42")));
pop.sell(TradeParameters.of(Quantity.of(200), Price.of("15.25")));
```

Some of the computations require a ticker price.  This is not saved as part of the stock object state and must be provided when required.  The ticker price can be created using the `of()` static method on the `TickerPrice` class, as shown next.

```
final TickerPrice tradePrice = TickerPrice.of(new BigDecimal("15.40"));
```

The dividend yield can be calculated by simply providing the ticker price to the stock.

```
final DividendYield dividendYield = pop.computeDividendYield(tradePrice);
```

The same applies to the PE ratio.

```
final PeRatio ratio = pop.computePeRatio(tradePrice);
```

The geometric mean and the stock price work with the trades made on this stock as shown next.

```
final GeometricMean geometricMean = pop.computeGeometricMean();
final StockPrice stockPrice = pop.computeStockPrice();
```


This demo does not use any frameworks nor provides a stand-alone application.  It is a simple plain old java project that uses domain driven design.  Inheritance is preferred over the state and the stock types (_common_ and _preferred_) are represented by different objects, the `Stock.Common` and `Stock.Preferred`, instead.