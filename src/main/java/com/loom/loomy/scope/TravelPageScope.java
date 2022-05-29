package com.loom.loomy.scope;

import java.util.concurrent.Future;

import com.loom.loomy.model.PageComponent;
import com.loom.loomy.model.Quotation;
import com.loom.loomy.model.Quotation.QuotationException;
import com.loom.loomy.model.TravelPage;
import com.loom.loomy.model.Weather;

import jdk.incubator.concurrent.StructuredTaskScope;

public class TravelPageScope extends StructuredTaskScope<PageComponent> {

    private volatile  Quotation quotation;
    private volatile Weather weather = Weather.UNKNOWN;
    private volatile QuotationException exception;

    @Override
    protected void handleComplete(Future<PageComponent> future) {
        switch (future.state()) {
            case RUNNING -> throw new IllegalStateException("Task is still running");
            case SUCCESS -> {

                if (future.resultNow() instanceof final Quotation q) quotation=q;
                if (future.resultNow() instanceof final Weather w) weather = w;
            }
            case FAILED -> {

                if (!(future.exceptionNow() instanceof final QuotationException e)) throw new RuntimeException(future.exceptionNow());
        exception = e;

            }
            case CANCELLED -> {
            	System.out.println("Cancelled" );

            }
        }
    }

    public TravelPage travelPage() {
        if (quotation != null) return new TravelPage(quotation, weather);
    throw exception;
    }
}