package com.scherule.calendaring;

import com.google.inject.Module;
import com.intapp.vertx.guice.GuiceVertxLauncher;
import com.intapp.vertx.guice.VertxModule;
import com.scherule.calendaring.controllers.ControllersModule;
import com.scherule.calendaring.domain.services.ServicesModule;
import com.scherule.calendaring.modules.CalendaringDomainModule;
import com.scherule.calendaring.modules.CalendaringQueueModule;
import com.scherule.calendaring.modules.PersistenceModule;
import io.vertx.core.Vertx;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class CalendaringApplication extends GuiceVertxLauncher {

    public static final List<Module> APPLICATION_MODULES = unmodifiableList(
            asList(
                    new ServicesModule(),
                    new CalendaringQueueModule(),
                    new PersistenceModule(),
                    new CalendaringDomainModule(),
                    new ControllersModule()
            )
    );

    public static void main(String[] args) {
        new CalendaringApplication().dispatch(args);
    }

    @Override
    protected List<Module> getModules(Vertx vertx) {
        List<Module> modules = super.getModules(vertx);
        modules.add(new VertxModule(vertx));
        modules.addAll(APPLICATION_MODULES);
        return modules;
    }

}