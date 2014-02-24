package com.tddrampup;

import com.google.inject.AbstractModule;
import com.tddrampup.singletons.Listings;
import com.tddrampup.singletons.ListingsInterface;

/**
 * Created by WX009-PC on 2/24/14.
 */
public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ListingsInterface.class).to(Listings.class);
    }
}
