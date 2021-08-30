package com.api.backend;

import org.json.JSONObject;

import java.io.File;
import java.sql.*;

public class Database {
    private static String dbDir = null;

    static {
        try {
            dbDir = "jdbc:sqlite:" + new Store().getDir() + "/database/";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getDbDir() {
        return dbDir;
    }

    /**
     * Database connection
     * @param dbName the database file name
     */
    private Connection connect(String dbName) throws ClassNotFoundException {
        //SQLite Connection String
        Class.forName("org.sqlite.JDBC");

        String url = getDbDir() + dbName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Connecting to a sample database
     * @param dbName the database filename
     */
    public static void createNewDatabase(String dbName) {
        String url = getDbDir() + dbName;

        try (Connection conn = DriverManager.getConnection(url)){
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creating a new table in the sports database
     */
    public static void createNewTable(String dbName, String sqlStatement) {
        //SQLite connection string
        String url = getDbDir() + dbName;

        try (Connection conn = DriverManager.getConnection(url)){
            Statement stmt = conn.createStatement();
            //create a new table
            stmt.execute(sqlStatement);
        } catch (SQLException e) {
            System.out.println("CREATE TABLE ERROR: " + e.getMessage());
        }
    }

    /**
     * Adding the seasons to a database
     */
    public void addFixtures(int year, String league_name, String country_name, String current, String top_scorers, String injuries){
        String dbName = "fixtures.db";

        File db = new File(dbName);
        if (!db.exists()){
            createNewDatabase(dbName);
        }
        String sql = "CREATE TABLE `fixtures` (\n" +
                "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t`league_name`\tTEXT NOT NULL,\n" +
                "\t`year`\tTEXT NOT NULL,\n" +
                "\t`country_name`\tTEXT NOT NULL,\n" +
                "\t`current`\tTEXT NOT NULL,\n" +
                "\t`top_scorers`\tTEXT NOT NULL,\n" +
                "\t`injuries`\tTEXT\n" +
                ");";

        createNewTable(dbName, sql);

        String insert = "INSERT INTO `fixtures`(`year`,`league_name`,`country_name`,`current`, `top_scorers`, `injuries`) VALUES (?,?,?,?,?,?);";

        try (Connection conn = this.connect(dbName);
             PreparedStatement pstmt = conn.prepareStatement(insert)) {
            pstmt.setString(1, String.valueOf(year));
            pstmt.setString(2, league_name);
            pstmt.setString(3, country_name);
            pstmt.setString(4, current);
            pstmt.setString(5, top_scorers);
            pstmt.setString(6, injuries);
            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
