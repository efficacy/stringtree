package org.stringtree.db;

public abstract class LiteralRowListener extends ResultRowListener implements LiteralListener {

    protected String sql;
    
    public LiteralRowListener(String sql) {
        this.sql = sql;
    }

    public String getSQL() {
        return sql;
    }
}
