package org.stringtree.db;


public abstract class MultipleLiteralPopulator extends MultiColumnLiteralPopulator {

	protected int rows;

	public MultipleLiteralPopulator(String sql, int columns, int rows) {
		super(sql, columns);
		this.rows = rows;

		StringBuffer updateBuffer = new StringBuffer(update);
		for (int i = 0; i < rows-1; ++i) {
			updateBuffer.append(",");
			updateBuffer.append(value);
		}
		update = updateBuffer.toString();
	}
}
