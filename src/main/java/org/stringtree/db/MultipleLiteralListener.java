package org.stringtree.db;

public abstract class MultipleLiteralListener<T> extends CollectingResultRowListener<T> implements LiteralListener {

    protected String sql;
    
    public MultipleLiteralListener(String sql) {
        this.sql = sql;
    }

    public String getSQL() {
        return sql;
    }
}
