package com.rp.p2p.analytics;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Generator
{
    private final static Logger logger_ =Logger.getLogger(Md5Generator.class);
    private static final Md5Generator instance_ = new Md5Generator();

    private Md5Generator()
    {}

    public final static Md5Generator getInstance()
    {
        return instance_;
    }

    public String convert(String value) throws NoSuchAlgorithmException {
        MessageDigest md =MessageDigest.getInstance("MD5");
        md.update(value.getBytes());
        byte[] mdbytes=md.digest();

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<mdbytes.length;i++) {
            String hex=Integer.toHexString(0xff & mdbytes[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }

        String ret = hexString.toString();
        logger_.debug("Converting:\'" + value + "\'  to \'" + ret + "\'");

        return ret;
    }

}
