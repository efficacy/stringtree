package org.stringtree.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowListener {

    void start();
    
    /**
     * return null to continue, or an object to signify processing complete
     * 
     * @throws SQLException
     */
    Object row(ResultSet results, int rowNumber) throws SQLException;

    Object finish();
}
