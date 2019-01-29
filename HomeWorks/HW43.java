package HomeWorks;

import java.sql.*;

public class HW43 {

    private static final String url = "jdbc:postgresql://localhost:5433/postgres";
    private static final String user = "postgres";
    private static final String password = "1977";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static void main(String[] args) {
        HW43 dbWorker = new HW43();
        /*dbWorker.register("Ariana", "ari@dsd",
                "qwerty");*/



        if(dbWorker.authorize("Ariana","qwerty")){
            System.out.println("Authorized!");
        } else {System.out.println("Not Authorized!");};

    }

    public boolean register(String login, String email,
                            String password) {
        String SQL =
                "insert into test.users_acad (login, email, password, date_register) " +
                        "values (?, ?, ?, ?)";
        try (Connection conn = connect()) {
            PreparedStatement stmt =
                    conn.prepareStatement(SQL);

            password = Shifr(password);
            stmt.setString(1, login);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setTimestamp(4,
                    new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Successfully");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
    public boolean authorize(String login, String password) {
        String SQL =
                "SELECT count(*) FROM test.users_acad " +
                        "where lower(login) = lower(?) and password = ?";

        int count = 0;

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(SQL);

            password = Shifr(password);
            stmt.setString(1, login);
            stmt.setString(2, password);

            System.out.println(login);
            System.out.println(password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return count==0 ? false : true;
    }


    public static String Shifr(String password){
        String result = new StringBuffer(password).reverse().toString();
        String temp = result.substring(result.length()-1);
        result = result+temp;
        return  result;
    }
}
