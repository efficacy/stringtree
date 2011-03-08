package org.stringtree.db;

public abstract class MultiColumnLiteralPopulator implements LiteralPopulator {
	
	protected String sql;
	protected int columns;
	protected String value;
	protected String update;

	public MultiColumnLiteralPopulator(String sql, int columns) {
		this.sql = sql;
		this.columns = columns;
		
		StringBuffer valueBuffer = new StringBuffer("(");
		for (int i = 0; i < columns; ++i) {
			if (i > 0) valueBuffer.append(",");
			valueBuffer.append("?");
		}
		valueBuffer.append(")");
		value = valueBuffer.toString();
		
		StringBuffer updateBuffer = new StringBuffer(sql);
		updateBuffer.append(" values ");
		updateBuffer.append(value);
		update = updateBuffer.toString();
	}
	
	public String getSQL() {
		return update;
	}
}
