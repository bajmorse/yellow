package com.tddrampup;

import com.google.inject.AbstractModule;
import com.tddrampup.singletons.FakeListings;
import com.tddrampup.singletons.ListingsInterface;

/**
 * Created by WX009-PC on 2/24/14.
 */
public class TestApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ListingsInterface.class).to(FakeListings.class);
    }
}
