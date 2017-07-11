package com.scherule.calendaring;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.inject.AbstractModule;

public class CalendaringDomainContext extends AbstractModule {

    @Override
    protected void configure() {
        bindObjectMapper();
    }

    private void bindObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        bind(ObjectMapper.class).toInstance(objectMapper);
    }

}
