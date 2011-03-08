package org.stringtree.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementPopulator {
    void populate(PreparedStatement ps) throws SQLException;
}
