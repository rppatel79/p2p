package com.rp.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.function.ObjDoubleConsumer;

public class ApplicationProperties
{
    private static final String DEFAULT_PROPERTIES="p2p.properties";
    private static final String VM_PROPERTIES="properties";
    private static final Object LOCK = new Object();
    private static ApplicationProperties INSTANCE;

    private final Logger logger_ = Logger.getLogger(ApplicationProperties.class);
    private Properties properties_ = new Properties();
    private String propertiesFileName_;

    private ApplicationProperties() throws FileNotFoundException,IOException
    {
        propertiesFileName_ = DEFAULT_PROPERTIES;
        if (System.getProperty(VM_PROPERTIES) != null)
        {
            propertiesFileName_ = System.getProperty(VM_PROPERTIES);
        }

        BufferedInputStream bufferedInputStream=null;
        try {
            File propertiesFile = new File(propertiesFileName_);
            if (!propertiesFile.exists())
                throw new FileNotFoundException("File does not exist. ["+propertiesFile.getAbsolutePath()+"]");
            bufferedInputStream=new BufferedInputStream(new FileInputStream(propertiesFileName_));
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
