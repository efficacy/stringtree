package org.stringtree.db;

public class MySQLDataSource extends DriverManagerDataSource {

    public MySQLDataSource(String database, String user, String password, String host, int port) {
        super("com.mysql.jdbc.Driver", "jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
    }

    public MySQLDataSource(String database, String user, String password, String host) {
        this(database, user, password, host, 3306);
    }

}
