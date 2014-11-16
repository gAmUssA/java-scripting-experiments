#!/usr/bin/env jjs -scripting -J-Djava.class.path=/Users/apple/projects/cdb4.1_hsql_db/hsqldb.jar
/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com) on 9/21/13.
 * @since 0.0.1
 */

// region imports
var DriverManager = Java.type("java.sql.DriverManager");
var Class = Java.type("java.lang.Class");
// endregion

try {
    var sql = java.sql;
    Class.forName('org.hsqldb.jdbcDriver').newInstance();
    var conn = DriverManager.getConnection('jdbc:hsqldb:hsql://localhost:9002/cleardb', 'sa', '');
    for (var warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning()) {
        var log = ("SQL Warning:\n State: ${warn.getSQLState()}\n Message: ${warn.getMessage()}\n Error: {warn.getErrorCode()}");
        print(log);
    }
    //var myQuery = "select * from company c;";
    //var myQuery = "select * from company_associate c;";
    var myQuery = "select ca.ASSOCIATE, c.COMPANY from company c, company_associate ca where c.ID = ca.company_id";
    print("My query " + myQuery + "\n---------------");
    var rs = conn.createStatement().executeQuery(myQuery);
    while (rs.next()) {
        print(rs.getString(1) + ":\t" + rs.getString(2));
    }
    // print(__FILE__, __LINE__, __DIR__);
}// region catch block
catch (se) {
    print("SQL Exception:");

    // Loop through the SQL Exceptions
    while (se != null) {
        print("state: ${se.getSQLState()}, message: ${se.getMessage()}, error: ${se.getErrorCode()}");
        se = se.getNextException();
    }
}// endregion
