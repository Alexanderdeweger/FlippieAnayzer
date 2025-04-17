package Main.java;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;



public class Advertenties {
    private final Database database = new Database();
    private final Connection con = database.connect();
    private final Statement stmt = con.createStatement();

    public Advertenties() throws SQLException {
    }

    public void nieuweAdvertentiesLijst() throws SQLException, ParseException {
        advertentiesjson advertentiesjson = new advertentiesjson();
        String lijst = advertentiesjson.getJson();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(lijst);
        JSONArray array = (JSONArray) jsonObject.get("advertenties");

        for (Object obj : array) {
            JSONObject advertentie = (JSONObject) obj;
            JSONObject doelgroep = (JSONObject) advertentie.get("target_audience");
            String titel = (String) advertentie.get("titel");
            String leeftijd = (String) doelgroep.get("leeftijd");
            String locatie = (String) doelgroep.get("locatie");
            String gender = (String) doelgroep.get("gender");
            Long bereik = (Long) advertentie.get("bereik");
            int actief = 0;
            if ((boolean) advertentie.get("actief")) {
                actief = 1;
            }
            String gepostOp = (String) advertentie.get("geplaatst_op");
            JSONArray platformen = (JSONArray) advertentie.get("platformen");
            String platformenString = platformen.toString().replace("[\"", "").replace("\"]", "").replace("\",\"", ", ");

            String SQL = "INSERT INTO `flippielyzer`.`advertenties`(`titel`,`leeftijd`,`locatie`,`gender`,`bereik`,`actief`,`datumgepost`,`platformen`)VALUES (\"" + titel + "\", \"" + leeftijd + "\", \"" + locatie + "\", \"" + gender + "\", \"" + bereik + "\", " + actief + ", \"" + gepostOp + "\", \"" + platformenString + "\")";
            stmt.execute(SQL);
        }
    }

    public void advertentiesRefreshen(LocalDate laatsRefreshed) throws ParseException, SQLException {
        advertentiesjson advertentiesjson = new advertentiesjson();
        String lijst = advertentiesjson.getJson();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(lijst);
        JSONArray array = (JSONArray) jsonObject.get("advertenties");

        for (Object obj : array) {
            JSONObject advertentie = (JSONObject) obj;
            String gepostOp = (String) advertentie.get("geplaatst_op");
            LocalDate postedOp = LocalDate.parse(gepostOp);
            if (laatsRefreshed.isAfter(postedOp)) {
                JSONObject doelgroep = (JSONObject) advertentie.get("target_audience");
                String titel = (String) advertentie.get("titel");
                String leeftijd = (String) doelgroep.get("leeftijd");
                String locatie = (String) doelgroep.get("locatie");
                String gender = (String) doelgroep.get("gender");
                Long bereik = (Long) advertentie.get("bereik");
                int actief = 0;
                if ((boolean) advertentie.get("actief")) {
                    actief = 1;
                }

                JSONArray platformen = (JSONArray) advertentie.get("platformen");
                String platformenString = platformen.toString().replace("[\"", "").replace("\"]", "").replace("\",\"", ", ");

                String SQL = "INSERT INTO `flippielyzer`.`advertenties`(`titel`,`leeftijd`,`locatie`,`gender`,`bereik`,`actief`,`datumgepost`,`platformen`)VALUES (\"" + titel + "\", \"" + leeftijd + "\", \"" + locatie + "\", \"" + gender + "\", \"" + bereik + "\", " + actief + ", \"" + gepostOp + "\", \"" + platformenString + "\")";
                stmt.execute(SQL);
            }
        }

    }
}
