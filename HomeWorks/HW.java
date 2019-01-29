package HomeWorks;

import java.sql.*;

public class HW {
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
        HW data = new HW();
        //data.connect();
        // System.out.println(getStudentsCount());
        // getListOfGroup();
        getSalaryOfTrainers();
        //getSumOfSalary();
    }

    public static int getStudentsCount() {
        String SQL = "SELECT count(*) FROM test.students where lower(name) like '%а%'";
        int count = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return count;
    }

    public static void getListOfGroup() {
        String SQL = "SELECT * from test.gruppa";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                System.out.println(rs.getString("id") + " " + rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void getSalaryOfTrainers() {
        String SQL = "SELECT test.trainers.id_trainer, test.trainers.name, test.sports.salary from test.trainers " +
                "inner join test.sports on test.trainers.sport_id=test.sports.id";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                System.out.println(rs.getString("id_trainer") + " " + rs.getString("name") + " " + rs.getString("salary"));
                if(rs.getString("name").length()>3){
                    System.out.println(" Molodets");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void getSumOfSalary(){
        String SQL = "select sum(test.sports.salary) from test.trainers " +
                "inner join test.sports on test.trainers.sport_id=test.sports.id";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


