package org.stringtree.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultRowListener implements RowListener {

    public void start() {
        // do nothing unless overridden
    }

    /**
     * return null to continue, or an object to signify processing complete
     * 
     * @throws SQLException
     */
    public abstract Object row(ResultSet results, int rowNumber)
            throws SQLException;

    public Object finish() {
        // do nothing unless overridden
        return null;
    }
}
