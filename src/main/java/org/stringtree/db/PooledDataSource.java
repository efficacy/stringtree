package org.stringtree.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.stringtree.util.Delegator;

public class PooledDataSource extends Delegator implements DataSource {

	DatabaseConnectionPool pool;

	public PooledDataSource(DataSource other, int timeout, int min) {
		super(other);
		this.pool = new DatabaseConnectionPool(other, timeout, min);
	}

	public PooledDataSource(DataSource other, int timeout) {
		super(other);
		this.pool = new DatabaseConnectionPool(other, timeout);
	}

	protected DataSource realDataSource() {
		return (DataSource) other;
	}

	@Override public Connection getConnection() throws SQLException {
		return pool.claim();
	}

	@Override public Connection getConnection(String username, String password)
			throws SQLException {
		return realDataSource().getConnection(username, password);
	}

	@Override public PrintWriter getLogWriter() throws SQLException {
		return realDataSource().getLogWriter();
	}

	@Override public int getLoginTimeout() throws SQLException {
		return realDataSource().getLoginTimeout();
	}

	@Override public void setLogWriter(PrintWriter out) throws SQLException {
		realDataSource().setLogWriter(out);
	}

	@Override public void setLoginTimeout(int seconds) throws SQLException {
		realDataSource().setLoginTimeout(seconds);
	}

	@Override public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return realDataSource().isWrapperFor(iface);
	}

	@Override public <T> T unwrap(Class<T> iface) throws SQLException {
		return realDataSource().unwrap(iface);
	}

	@Override public Logger getParentLogger()
			throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}
}
