package org.stringtree.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleValueResultListener extends ResultRowListener {

    public Object row(ResultSet results, int rowNumber) throws SQLException {
        return results.getObject(1);
    }
}
