package org.stringtree.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.stringtree.pool.Pool;
import org.stringtree.pool.Verifiable;

public class VerifiableConnection extends DelegatedConnection implements Verifiable {
	Pool<VerifiableConnection> pool;
	private int timeout;

	public VerifiableConnection(Connection other, Pool<VerifiableConnection> pool, int timeout) {
		super(other);
		this.pool = pool;
		this.timeout = timeout;
	}

	@Override
	public boolean valid() {
		try {
			return !realConnection().isClosed() && realConnection().isValid(timeout);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void close() {
		if (null != pool) pool.release(this);
	}
	
	@Override
	public Connection realConnection() {
		return super.realConnection();
	}
}
