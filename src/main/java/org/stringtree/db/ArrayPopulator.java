package org.stringtree.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ArrayPopulator implements StatementPopulator {
	private Object[] args;

	public ArrayPopulator(Object[] args) {
		this.args = args;
	}

	public ArrayPopulator(List<Object> args) {
		this.args = args.toArray();
	}

	@Override
	public void populate(PreparedStatement ps) throws SQLException {
		for (int i = 0; i < args.length; ++i) {
			ps.setObject(i+1, args[i]);
		}
	}

}
