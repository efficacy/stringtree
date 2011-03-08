package org.stringtree.db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.stringtree.finder.StringFinder;
import org.stringtree.template.InlineTemplater;
import org.stringtree.util.ExceptionHandler;
import org.stringtree.util.ReaderUtils;
import org.stringtree.util.ResourceUtils;
import org.stringtree.util.StderrExceptionHandler;
import org.stringtree.util.StreamUtils;

@SuppressWarnings("deprecation")
public class DatabaseWrapper {
    protected static final String scriptfile = "dbscripts.properties";

    private static final StatementPopulator dummyPopulator = new StatementPopulator() {
        public void populate(PreparedStatement ps) {
            // do nothing, the statement is assumed to be already populated
        }
    };
    
    private DataSource ds = null;
    private ExceptionHandler logger = StderrExceptionHandler.it;

    protected void init(DataSource datasource, ExceptionHandler logger) {
        this.ds = datasource;
        this.logger = logger;
    }

    protected void init(DataSource datasource) {
        this.ds = datasource;
    }

    protected void init(String jndiName) throws NamingException {
        InitialContext ctx;
        ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(jndiName);
        init(ds);
    }

    public DatabaseWrapper(DataSource datasource, ExceptionHandler logger) {
        init(datasource, logger);
    }

    public DatabaseWrapper(DataSource datasource) {
        init(datasource);
    }

    public DatabaseWrapper(String jndiName) throws NamingException {
        init(jndiName);
    }

    public Object query(String sql, RowListener listener) {
System.err.println("db.query  [" + sql + "]");
        return query(sql, dummyPopulator, listener);
    }

    public Object query(LiteralListener listener) {
        String sql = listener.getSQL();
System.err.println("db.query  [" + sql + "]");
		return query(sql, dummyPopulator, listener);
    }

    public Object query(LiteralPopulatorListener literal) {
        String sql = literal.getSQL();
System.err.println("db.query  [" + sql + "]");
		return query(sql, literal, literal);
    }

    public Object singleValueQuery(String script) {
System.err.println("db.query  [" + script + "]");
        return query(script, dummyPopulator, new SingleValueResultListener());
    }

    public Object singleValueQuery(String script, StatementPopulator populator) {
        return query(script, populator, new SingleValueResultListener());
    }

    public Object query(String sql, StatementPopulator populator, RowListener listener) {
//StopWatch watch = new StopWatch();
        try {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet results = null;
            Object ret = null;
            try {
                connection = ds.getConnection();
                ps = connection.prepareStatement(sql);
                populator.populate(ps);
                boolean hasResults = ps.execute();
                listener.start();
                if (hasResults) {
                    results = ps.getResultSet();
	                for (int rowNumber = 0; results.next() && ret == null; ++rowNumber) {
	                    ret = listener.row(results, rowNumber);
	                }
                }
                if (ret == null) {
                    ret = listener.finish();
                }
            } finally {
                if (results != null) {
                    results.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
//System.err.println(".. took " + watch.sofar() + "ms");
            }
            
            return ret;
        } catch (Exception e) {
            if (null != logger) logger.handle(e, sql);
        }
        
//System.err.println(".. took " + watch.sofar() + "ms");
        return null;
    }

    public Object query(String sql, RowListener listener, Object... args) {
System.err.println("db.query  [" + sql + "] args=" + Arrays.toString(args));
    	return query(sql, new ArrayPopulator(args), listener);
    }
    
    public int update(LiteralPopulator populator) {
        String sql = populator.getSQL();
System.err.println("db.update [" + sql + "]");
		return update(sql, populator);
    }

    public int update(String sql) {
System.err.println("db.update [" + sql + "]");
        return update(sql, dummyPopulator);
    }

    public void update(String sql, StringFinder context) {
    	InlineTemplater templater = new InlineTemplater(context);
        update(templater.expand(sql));
    }

    public int update(String sql, Object... args) {
System.err.println("db.update [" + sql + "] args=" + Arrays.toString(args));
        return update(sql, new ArrayPopulator(args));
    }

    public int update(String sql, List<Object> args) {
System.err.println("db.update [" + sql + "] args=" + args);
        return update(sql, new ArrayPopulator(args));
    }

    public int update(String sql, StatementPopulator populator) {
        try {
            Connection connection = null;
            PreparedStatement ps = null;
            
            try {
                connection = ds.getConnection();
                ps = connection.prepareStatement(sql);
                populator.populate(ps);
                if (!ps.execute()) {
                	return ps.getUpdateCount();
                }
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            if (null != logger) logger.handle(e, sql);
        }
        return 0;
    }
    
    public void script(InputStream source, String sep) throws SQLException, IOException {
        Connection connection = null;
        
        try {
        	BufferedReader in = new BufferedReader(new InputStreamReader(source));
            connection = ds.getConnection();
            StringBuffer buf = new StringBuffer();
            for (int c = in.read(); c > -1; c = in.read()) {
            	if (sep.indexOf(c) < 0) {
            		buf.append((char)c);
            	} else {
            		scriptStatement(connection, buf);
            	}
            }
            if (buf.length() > 0) {
            	scriptStatement(connection, buf);
            }
        } finally {
            StreamUtils.closeInput(source);
            if (null != connection && !connection.isClosed()) {
                try { connection.close(); } catch (SQLException e) {}
            }
        }
    }

	private void scriptStatement(Connection connection, StringBuffer buf) throws SQLException {
		Statement st;
		String line = buf.toString().trim();
		if (line.length() > 0) {
		    st = connection.createStatement();
		    try {
System.err.println("db.script [" + line + "]");
		        st.execute(line);
		    } finally {
		        st.close();
		    }
		}
		buf.setLength(0);
	}
    
	public void script(InputStream source, StringFinder context, String sep) throws SQLException, IOException {
   		String loaded = ReaderUtils.readInputStream(source);
    	InlineTemplater templater = new InlineTemplater(context);
        String expanded = templater.expand(loaded);
		script(new StringBufferInputStream(expanded), sep);
    }
    
	public void script(InputStream source, StringFinder context) throws SQLException, IOException {
		script(source, context, ";");
    }
    
    public void scriptFile(String filename, String sep) throws SQLException, IOException {
       	script(new FileInputStream(filename), sep);
    }

    public void scriptFile(String filename) throws SQLException, IOException {
    	scriptFile(filename, ";");
    }
    
	public void scriptResource(ClassLoader loader, String filename, String sep) throws SQLException, IOException {
       	InputStream resourceStream = ResourceUtils.getResourceStream(loader, filename);
       	if (null == resourceStream) throw new IOException("cannot find resource '" + filename + "'");
		script(resourceStream, sep);
    }

	public void scriptResource(ClassLoader loader, String filename) throws SQLException, IOException {
    	scriptResource(loader, filename, ";");
    }
    
    @SuppressWarnings("unchecked")
	public void scriptResource(Class self, String filename, String sep) throws SQLException, IOException {
       	InputStream resourceStream = self.getResourceAsStream(filename);
       	if (null == resourceStream) throw new IOException("cannot find resource '" + filename + "'");
    	script(resourceStream, sep);
    }
    
    @SuppressWarnings("unchecked")
	public void scriptResource(Class self, String filename, StringFinder context, String sep) throws SQLException, IOException {
       	InputStream resourceStream = self.getResourceAsStream(filename);
       	if (null == resourceStream) throw new IOException("cannot find resource '" + filename + "'");
    	script(resourceStream, context, sep);
    }
    
    @SuppressWarnings("unchecked")
	public void scriptResource(Class self, String filename, StringFinder context) throws SQLException, IOException {
    	scriptResource(self, filename, context, ";");
    }

    @SuppressWarnings("unchecked")
	public void scriptResource(Class self, String filename) throws SQLException, IOException {
    	scriptResource(self, filename, ";");
    }

	public void scriptResource(String filename) throws SQLException, IOException {
    	scriptResource(getClass(), filename, ";");
    }
	
	public void scriptString(String script) throws SQLException, IOException {
		script(new StringBufferInputStream(script), ";");
	}
	
	public void scriptString(String script, String sep) throws SQLException, IOException {
		script(new StringBufferInputStream(script), sep);
	}
	
	public void scriptString(String script, StringFinder context) throws SQLException, IOException {
		script(new StringBufferInputStream(script), context, ";");
	}
	
	public void scriptString(String script, StringFinder context, String sep) throws SQLException, IOException {
		script(new StringBufferInputStream(script), context, sep);
	}
	
	public void interact(InputStream in, OutputStream out) throws IOException {
		StringBuilder builder = new StringBuilder();
		for (int c = in.read(); c >= 0; c = in.read()) {
			if (('\r' == c || '\n' == c || ';' == c)) {
				if (builder.length() > 0) {
					interact(builder.toString(), out);
					builder.setLength(0);
				}
			} else if (0x03 == c || 0x04 == c ) {
				break;
			} else {
				builder.append((char)c);
			}
		}
	
		System.out.println("Session terminated by user input.");
	}

	public void interact(String script, OutputStream out) {
		final PrintStream print = StreamUtils.ensurePrint(out);
		
		query(script, new RowListener() {
			int cols = 0;
			int rows = 0;
			String[] names;
			int[] widths;
			
			List<String[]> data = new ArrayList<String[]>();
			
			@Override public void start() {}
			
			@Override public Object row(ResultSet rs, int rowNumber) throws SQLException {
				if (0 == rowNumber) {
					ResultSetMetaData meta = rs.getMetaData();
					cols = meta.getColumnCount();
					names = new String[cols];
					widths = new int[cols];
					for (int col = 0; col < cols; ++col) {
						names[col] = meta.getColumnLabel(col+1);
						widths[col] = names[col].length();
					}
				}
				
				String[] row = new String[cols];
				for (int col = 0; col < cols; ++col) {
					Object value = rs.getObject(col+1);
					String string = null==value ? "NULL" : value.toString();
					row[col] = string;

					int len = null==value ? 0 : string.length();
					if (len > widths[col]) widths[col] = len;
				}
				data.add(row);
				
				++rows;
				return null;
			}
			
			@Override public Object finish() {
				if (0 == rows) print.println("0 values returned.");
				for (int col = 0; col < cols; ++col) {
					pad(print, names[col], widths[col]);
				}
				print.println();

				int max = 0;
				for (int col = 0; col < cols; ++col) {
					max += widths[col];
				}
				max += cols-1;
				for (int i = 0; i < max; ++i) {
					print.print("-");
				}
				print.println();
				
				for (String[] row : data) {
					for (int col = 0; col < cols; ++col) {
						pad(print, row[col], widths[col]);
					}
					print.println();
				}
				print.flush();
				return null;
			}

			private void pad(PrintStream print, String string, int width) {
				int extra = width - string.length() + 1;
				print.print(string);
				for (int i = 0; i < extra; ++i) {
					print.print(' ');
				}
			}
		});
		
	}
}
