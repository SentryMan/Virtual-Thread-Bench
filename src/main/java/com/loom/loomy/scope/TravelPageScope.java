package com.loom.loomy.scope;

import java.util.Optional;

import com.loom.loomy.exception.TravelPageException;
import com.loom.loomy.model.PageComponent;
import com.loom.loomy.model.Quotation;
import com.loom.loomy.model.TravelPage;
import com.loom.loomy.model.Weather;

// scope for getting different types
public class TravelPageScope extends GenericStructuredTaskScope<PageComponent> {

private Weather weather = Weather.UNKNOWN;
  private Quotation quotation;

  public TravelPage travelPage() {

    if (results.size() > 2) throw new IllegalStateException("Only Two Tasks Are allowed");

    results.forEach(c->{
    	   if (c instanceof final Quotation q) quotation=q;
    	   else if (c instanceof final Weather w) weather = w;
    });

    final var quote =
        Optional.ofNullable(quotation)
            .orElseThrow(() -> new TravelPageException("fail lmao", exceptions));

    return new TravelPage(quote, weather);
  }
}
