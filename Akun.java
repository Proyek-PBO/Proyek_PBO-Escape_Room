package pbo.projek;

import java.util.ArrayList;
import java.sql.*;

public class Akun {
    private String currUser;
    private ArrayList<String> listuser = new ArrayList<>();
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/escaperoom";
    static final String USER = "root";
    static final String PASS = "";
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public Akun() {
        this.currUser = null;
        loaduser();
    }

    public Akun(String currUser) {
        this.currUser = currUser;
        loaduser();
    }
    
    public void loaduser(){
        listuser.clear();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                listuser.add(rs.getString("username"));
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean cek_user_ada(String username){
        for(int i = 0; i < listuser.size(); i++){
            if(listuser.get(i).equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }
    
    public boolean isAccount_Exist(String username, String pass){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + pass + "'";
            rs = stmt.executeQuery(sql);
            boolean ada;
            if(rs.next()){
                ada = true;
            }else{
                ada = false;
            }
            stmt.close();
            conn.close();
            return ada;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void regis(String username, String pass){
        String[] level = new String[]{"level1", "level2", "level3", "level4"};
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "INSERT INTO user VALUES(NULL,'" + username + "','" + pass + "')";
            stmt.executeUpdate(sql);
            for (int i = 0; i < 4; i++) {
                sql = "INSERT INTO " + level[i] + " VALUES(NULL,'" + username + "'," + Integer.MAX_VALUE + ")";
                stmt.executeUpdate(sql);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getListuser() {
        return listuser;
    }

    public String getCurrUser() {
        return currUser;
    }
    
    public void reset(String username, String data){
        String[] level = new String[]{"level1", "level2", "level3", "level4"};
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            if (data.equalsIgnoreCase("$3mu4")) {
                String sql = "DELETE FROM user";
                stmt.executeUpdate(sql);
                for (int i = 0; i < 4; i++) {
                    sql = "DELETE FROM " + level[i];
                    stmt.executeUpdate(sql);
                }
            }else if (data.equalsIgnoreCase("4kuN")) {
                for (int i = 0; i < 4; i++) {
                    String sql = "UPDATE " + level[i] + " SET score = " + Integer.MAX_VALUE + " WHERE username = '" + username + "'";
                    stmt.executeUpdate(sql);
                }
            }else{
                String sql = "DELETE FROM user WHERE username = '" + username + "'";
                stmt.executeUpdate(sql);
                for (int i = 0; i < 4; i++) {
                    sql = "DELETE FROM " + level[i] + " WHERE username = '" + username + "'";
                    stmt.executeUpdate(sql);
                }
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
