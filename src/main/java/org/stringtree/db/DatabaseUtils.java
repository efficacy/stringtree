package org.stringtree.db;

import java.io.IOException;

import javax.sql.DataSource;

import org.stringtree.fetcher.FetcherNumberHelper;
import org.stringtree.fetcher.FetcherStringHelper;
import org.stringtree.finder.MapStringKeeper;
import org.stringtree.finder.StringFinder;
import org.stringtree.finder.StringKeeper;
import org.stringtree.util.spec.SpecReader;

public class DatabaseUtils {
	public static DataSource getDatasource(StringFinder config, String schema, String user, String password) {
		if (null == schema) schema = config.get("db.schema");
		if (null == user) user = config.get("db.user");
		if (null == password) password = config.get("db.password");
		String host = FetcherStringHelper.getString(config, "db.host", "localhost");
		int port = FetcherNumberHelper.getInt(config, "db.port", 3306);
		
		return new MySQLDataSource(schema, user, password, host, port);
	}

	public static DataSource getDatasource(StringFinder config, String user, String password) {
		return getDatasource(config, null, user, password);
	}

	public static StringFinder getNamedConfig(String name) {
		StringKeeper config = new MapStringKeeper();
		try {
			SpecReader.load(config, "src/main/config/" + name + ".spec");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	public static DataSource getNamedDatasource(String name) {
		return getDatasource(getNamedConfig(name), null, null);
	}

	public static DataSource getNamedDatasource(String name, String schema, String user, String password) {
		return getDatasource(getNamedConfig(name), schema, user, password);
	}
}
