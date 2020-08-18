package br.com.integration.supportintegrationcore.bootstrap;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Dictionary;

public class ConfigUpdater implements ManagedService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUpdater.class);

    @Override
    public void updated(Dictionary dictionary) throws ConfigurationException {
        logger.info("Called configUpdater: " + dictionary);
    }
}
