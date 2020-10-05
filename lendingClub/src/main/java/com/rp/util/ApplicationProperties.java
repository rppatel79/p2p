package com.rp.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Properties;

public class ApplicationProperties
{
    private static final String DEFAULT_PROPERTIES="p2p.properties";
    private static final String VM_PROPERTIES="properties";
    private static final Object LOCK = new Object();
    private static ApplicationProperties INSTANCE;

    private static final Logger logger_ = Logger.getLogger(ApplicationProperties.class);
    private final Properties properties_ = new Properties();

    private ApplicationProperties() throws IOException
    {
        String propertiesFileName = DEFAULT_PROPERTIES;
        if (System.getProperty(VM_PROPERTIES) != null)
        {
            propertiesFileName = System.getProperty(VM_PROPERTIES);
        }

        BufferedInputStream bufferedInputStream=null;
        try {
            URL fileUrl = ApplicationProperties.class.getClassLoader().getResource(propertiesFileName);

            if (fileUrl == null)
                throw new FileNotFoundException("File does not exist. ["+ propertiesFileName +"]");
            File propertiesFile = new File(fileUrl.getFile());
            if (!propertiesFile.exists())
                throw new FileNotFoundException("File does not exist. ["+propertiesFile.getAbsolutePath()+"]");
            bufferedInputStream=new BufferedInputStream(new FileInputStream(propertiesFile));
            properties_.load(bufferedInputStream);
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

    public static ApplicationProperties getInstance() throws IOException {
        synchronized(LOCK) {
            if (INSTANCE == null) {
                INSTANCE = new ApplicationProperties();
            }

            return INSTANCE;
        }
    }

    public String getProperty(String key)
    {
        return properties_.getProperty(key);
    }

    public Properties getProperties()
    {
        return properties_;
    }

    public Properties getProperties(String regex)
    {
        Properties ret = new Properties();
        for (Map.Entry<Object,Object> entry : properties_.entrySet())
        {
            if (entry.getKey().toString().matches(regex))
                ret.put(entry.getKey(),entry.getValue());
        }
        return ret;
    }
}
