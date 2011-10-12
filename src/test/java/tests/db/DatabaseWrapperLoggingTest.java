package tests.db;

import java.io.StringWriter;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.stringtree.db.DatabaseWrapper;
import org.stringtree.db.stub.StubDataSource;

public class DatabaseWrapperLoggingTest extends TestCase {
	StringWriter buf;
	DataSource datasource;
	DatabaseWrapper dw;
	
	public void setUp() {
		buf = new StringWriter();
		datasource = new StubDataSource();
		dw = new DatabaseWrapper(datasource);
		dw.setCommentWriter(buf);
	}
	
	public void testSimpleUpdate() {
		dw.update("delete from test");
		assertEquals("db.UPDATE  delete from test\n", buf.toString());
	}
	
	public void testUpdateWithOneArg() {
		dw.update("delete from test where id=?", "xx");
		assertEquals("db.UPDATE  delete from test where id=? [xx]\n", buf.toString());
	}
	
	public void testUpdateWithTwoArgs() {
		dw.update("delete from test where id=? or id=?", "xx", "yy");
		assertEquals("db.UPDATE  delete from test where id=? or id=? [xx,yy]\n", buf.toString());
	}
}
