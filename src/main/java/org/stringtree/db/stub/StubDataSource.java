package org.stringtree.db.stub;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class StubDataSource implements DataSource {
	private PrintWriter logWriter;
	
	public StubDataSource() {
		logWriter = new PrintWriter(System.err); 
	}

	@Override public PrintWriter getLogWriter() throws SQLException {
		return logWriter;
	}

	@Override public void setLogWriter(PrintWriter out) throws SQLException {
		this.logWriter = out;
	}

	@Override public void setLoginTimeout(int seconds) throws SQLException {
	}

	@Override public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override public Connection getConnection() throws SQLException {
		return new StubConnection();
	}

	@Override public Connection getConnection(String username, String password) throws SQLException {
		return null;
	}
}
