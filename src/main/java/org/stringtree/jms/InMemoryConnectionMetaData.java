package org.stringtree.jms;

import java.util.Enumeration;

import javax.jms.ConnectionMetaData;

import org.stringtree.util.enumeration.EmptyEnumeration;

public class InMemoryConnectionMetaData implements ConnectionMetaData {
    
    @SuppressWarnings("unchecked")
	private static final Enumeration empty = new EmptyEnumeration();

    public String getJMSVersion() {
        return "1.1";
    }

    public int getJMSMajorVersion() {
        return 1;
    }

    public int getJMSMinorVersion() {
        return 1;
    }

    public String getJMSProviderName() {
        return "Stringtree In-Memory JMS";
    }

    public String getProviderVersion() {
        return "1.1.0";
    }

    public int getProviderMajorVersion() {
        return 1;
    }

    public int getProviderMinorVersion() {
        return 1;
    }

    @SuppressWarnings("unchecked")
	public Enumeration getJMSXPropertyNames() {
        return empty;
    }
}
