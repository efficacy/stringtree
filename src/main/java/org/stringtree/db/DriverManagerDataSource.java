package org.stringtree.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.stringtree.util.iterator.Spliterator;

/**
 * Data Source which uses the "old" DriverManager method of creating a connection
 */
public class DriverManagerDataSource implements DataSource {
	private String classname;
	private String url;
	private String user;
	private String password;

	public DriverManagerDataSource(String classname, String url, String user, String password) {
		init(classname, url, user, password);
	}

	public DriverManagerDataSource(String url, String user, String password) {
		this(null, url, user, password);
	}

	public DriverManagerDataSource(String spec) {
        spec = spec.trim();
        Spliterator it = new Spliterator(spec," ");
        init(nextString(it), nextString(it), nextString(it), nextString(it));
	}

	private String nextString(Spliterator it) {
		return it.hasNext() ? it.nextString() : null;
	}

	public DriverManagerDataSource(String classname, String url) {
		this(classname, url, null, null);
	}

	private void init(String classname, String url, String user, String password) {
		this.classname = classname;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	private void ensureClass() throws SQLException {
		try {
			Class.forName(classname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	public Connection getConnection() throws SQLException {
		ensureClass();

		return user == null 
			? DriverManager.getConnection(url) 
			: DriverManager.getConnection(url, user, password);
	}

	public Connection getConnection(String user, String password) throws SQLException {
		ensureClass();

		return DriverManager.getConnection(url, user, password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	public void setLogWriter(PrintWriter log) throws SQLException {
		DriverManager.setLogWriter(log);
	}

	public void setLoginTimeout(int timeout) throws SQLException {
		DriverManager.setLoginTimeout(timeout);
	}

	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}
	
	@SuppressWarnings("rawtypes")
	public boolean isWrapperFor(Class clz) {
        return false;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
    public Object unwrap(Class clz) throws SQLException {
        throw new SQLException("Not a Wrapper for " + clz);
    }
}
