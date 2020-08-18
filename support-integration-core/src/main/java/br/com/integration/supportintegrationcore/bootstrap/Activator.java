package br.com.integration.supportintegrationcore.bootstrap;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;

import static org.osgi.framework.Constants.SERVICE_PID;

public class Activator implements BundleActivator {

    private static final Logger logger = LoggerFactory.getLogger(Activator.class);

    private ServiceRegistration serviceRegistration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        logger.info("Starting activator...");
        Hashtable<String, Object> properties = new Hashtable<String, Object>();
        properties.put(SERVICE_PID, "produce-kafka-template");
        this.serviceRegistration = bundleContext
                .registerService(ManagedService.class.getName(), new ConfigUpdater(), properties);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        this.serviceRegistration.unregister();
    }
}