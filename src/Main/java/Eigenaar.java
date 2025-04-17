package Main.java;

import org.json.simple.parser.ParseException;

import java.sql.*;
import java.time.LocalDate;

public class Eigenaar {
    Database database = new Database();
    Connection con = database.connect();
    public void refreshAds() throws SQLException, ParseException {
        String SQL = "SELECT max(laatstChecked) FROM laatsterefresh";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        rs.next();
        Date laatstRefreshedSQL = rs.getDate("max(laatstChecked)");
        LocalDate laatstRefreshed = laatstRefreshedSQL.toLocalDate();
        Advertenties advertenties = new Advertenties();
        advertenties.advertentiesRefreshen(laatstRefreshed);

    }
}
