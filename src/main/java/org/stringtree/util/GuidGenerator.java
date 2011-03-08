package org.stringtree.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

public class GuidGenerator implements IdSource {
    
    private static GuidGenerator generator = null;
    
    private String text;
    private Random space;
    private Random time;
    
    public GuidGenerator() {
        text = environment();
        space = new Random(text.hashCode());
        time = new Random(System.currentTimeMillis());
    }

    public synchronized String next() {
        return encode(Long.toHexString(time.nextLong()) + text + Long.toHexString(space.nextLong()));
    }

    public static String create() {
        synchronized(GuidGenerator.class) {
            if (null == generator) {
                generator = new GuidGenerator();
            }
        }
        return generator.next();
    }

    @SuppressWarnings("unchecked")
	private String environment() {
        StringBuffer raw = new StringBuffer();
        
        //Loop through all the system properties and add them to the hashingString
        Properties systemProps = System.getProperties();
        Enumeration systemPropKeys = systemProps.keys();
        while(systemPropKeys.hasMoreElements())
        {
            String key = (String) systemPropKeys.nextElement();
            raw.append(" " + key + " = " + systemProps.getProperty(key) + "\n");   
        }

        //Do a hardware dependent test and add the result to the hashingString
        long speed = 0; 
        for(long end = System.currentTimeMillis() + 5; System.currentTimeMillis() < end; ++speed ) {
            // as fast as we can
        }
        raw.append("Cycle Test = " + speed + "\n");
        
        //Loop through all the locales that are installed and add them to the hashingString
        Locale[] locales = Locale.getAvailableLocales();
        for (int iCounter = 0; iCounter < locales.length; iCounter++)
        {
            raw.append(locales[iCounter].getCountry() + " " + locales[iCounter].getDisplayCountry() + " " + locales[iCounter].getDisplayName() + "\n");
        }
        
        //Get as much machine dependent stuff out of the runtime as
        //you can and add them to the hashingString
        Runtime runtime = Runtime.getRuntime();
        raw.append("Available proc = " + runtime.availableProcessors() + "\n");
        raw.append("free memory = " + runtime.freeMemory() + "\n");
        raw.append("max memory = " +  runtime.maxMemory() + "\n");
        raw.append("total memory = " + runtime.totalMemory() + "\n");
        
        //try to get machines IP address and add to the hash string
        try {       
            raw.append("IP address = " + InetAddress.getLocalHost().toString());
        } catch(UnknownHostException e) {
            //Do nothing
        }

        return raw.toString();
    }

    private String encode(String text) {
        String ret = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            
            md.reset();
            md.update(text.getBytes());
            
            byte[] encoded = md.digest();
            StringBuffer buf = new StringBuffer();
            
            for (int i = 0; i < encoded.length; i++) {
                if ((encoded[i] & 0xff) < 0x10) {
                    buf.append("0");
                }
            
                buf.append(Long.toString(encoded[i] & 0xff, 16));
            }
            
            ret = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            ret = Integer.toHexString(text.hashCode());
        }
        
        return ret;
    }

    // TODO this is a bit simplistic - could probably be tighter
    public boolean valid(String id) {
        return id.length() != 32;
    }
}
