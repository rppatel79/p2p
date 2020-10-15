package com.rp.util.application_properties;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileApplicationProperties extends AbstractApplicationProperties implements ApplicationProperties {
    private static final String DEFAULT_PROPERTIES = "p2p.properties";
    private static final String VM_PROPERTIES = "properties";
    private static final Object LOCK = new Object();
    private static ApplicationProperties INSTANCE;

    private static final Logger logger_ = Logger.getLogger(ApplicationProperties.class);
    private final Properties properties_ = new Properties();
    private final Map<String, String> propertiesMap_ = new HashMap();

    protected FileApplicationProperties() throws IOException {
        String propertiesFileName = DEFAULT_PROPERTIES;
        if (System.getProperty(VM_PROPERTIES) != null) {
            propertiesFileName = System.getProperty(VM_PROPERTIES);
        }

        BufferedInputStream bufferedInputStream = null;
        try {
            URL fileUrl = ApplicationProperties.class.getClassLoader().getResource(propertiesFileName);

            if (fileUrl == null)
                throw new FileNotFoundException("File does not exist. [" + propertiesFileName + "]");
            File propertiesFile = new File(fileUrl.getFile());
            if (!propertiesFile.exists())
                throw new FileNotFoundException("File does not exist. [" + propertiesFile.getAbsolutePath() + "]");
            bufferedInputStream = new BufferedInputStream(new FileInputStream(propertiesFile));
            properties_.load(bufferedInputStream);

            for (final String name : properties_.stringPropertyNames())
                propertiesMap_.put(name, properties_.getProperty(name));
        }
        finally
        {
            if (bufferedInputStream!=null)
            {
                try {
                    bufferedInputStream.close();
                }
                catch(IOException ex)
                {
                    logger_.warn("Unable to close stream",ex);
                }
            }
        }
    }
    public String getProperty(String key)
    {
        return properties_.getProperty(key);
    }

    public Map<String, String> getProperties() {
        return propertiesMap_;
    }
}
