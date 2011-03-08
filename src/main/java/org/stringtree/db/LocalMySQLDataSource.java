package org.stringtree.db;

public class LocalMySQLDataSource extends MySQLDataSource {

    public LocalMySQLDataSource(String database, String user, String password) {
        super(database, user, password, "localhost");
    }

}
