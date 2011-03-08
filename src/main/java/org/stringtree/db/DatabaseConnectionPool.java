package org.stringtree.db;

import javax.sql.DataSource;

import org.stringtree.pool.ObjectFactory;
import org.stringtree.pool.ObjectPool;

public class DatabaseConnectionPool extends ObjectPool<VerifiableConnection>{
	public DatabaseConnectionPool(ObjectFactory<VerifiableConnection> factory) {
		super(factory);
	}

	public DatabaseConnectionPool(DataSource ds, int timeout) {
		super(new VerifiableConnectionFactory(ds, timeout));
	}

	public DatabaseConnectionPool(DataSource ds, int timeout, int min) {
		super(new VerifiableConnectionFactory(ds, timeout), min);
	}
}
