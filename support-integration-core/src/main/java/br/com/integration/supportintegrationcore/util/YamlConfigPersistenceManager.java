package br.com.integration.supportintegrationcore.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.felix.cm.PersistenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class YamlConfigPersistenceManager implements PersistenceManager {

    private static final File configDir = new File("/config");

    @Override
    public boolean exists(String pid) {
        return this.getFile(pid).exists();
    }

    @Override
    public Dictionary<String, Object> load(String pid) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            Map<String, Object> map = objectMapper.readValue(this.getFile(pid), new TypeReference<HashMap<String, Object>[]>() {
            });
            map.put("service.pid", pid);
            return CollectionUtils.convertFromMapToDictiorary(map);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load dictionary for pid" + pid);
        }
    }

    @Override
    public Enumeration<Dictionary<String, Object>> getDictionaries() throws IOException {
        String[] configs = configDir.list((dir, name) -> name.endsWith(".yml"));
        if (configs == null) {
            return null;
        }
        List<Dictionary<String, Object>> dicts = stream(configs).map(FilenameUtils::removeExtension).map(this::load).collect(toList());
        return Collections.enumeration(dicts);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void store(String pid, Dictionary dictionary) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(getFile(pid))) {
            Properties properties = (Properties) CollectionUtils.convertFromEnumerationToStream(dictionary.keys())
                    .filter(key -> key != "service.pid")
                    .collect(toMap(key -> key, dictionary::get, (k, v) -> v, Properties::new));
            properties.store(outputStream, null);
        }
    }

    @Override
    public void delete(String pid) throws IOException {
        File file = this.getFile(pid);
        if (file.exists()) {
            file.delete();
        }
    }

    private File getFile(String pid) {
        return new File(configDir, pid.concat(".yml"));
    }
}
