package org.stringtree.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.stringtree.pool.ObjectFactory;
import org.stringtree.pool.Pool;

public class VerifiableConnectionFactory implements ObjectFactory<VerifiableConnection> {
	DataSource ds;
	Pool<VerifiableConnection> pool;
	int timeout;
	
	public VerifiableConnectionFactory(DataSource ds, Pool<VerifiableConnection> pool, int timeout) {
		this.ds = ds;
		this.pool = pool;
		this.timeout = timeout;
	}
	
	public VerifiableConnectionFactory(DataSource ds, int timeout) {
		this(ds,null,timeout);
	}
	
	public void setPool(Pool<VerifiableConnection> pool) {
		this.pool = pool;
	}
	
	@Override public VerifiableConnection create() {
		try {
			return new VerifiableConnection(ds.getConnection(), pool, timeout);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void dispose(VerifiableConnection c) {
		try {
			if (!c.isClosed()) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
