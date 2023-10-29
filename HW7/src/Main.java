import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Connection con;

    private static char printMenu() {
        System.out.println("------------------------------------------------------");
        System.out.println("            Book Table Manipulation Test              ");
        System.out.println("------------------------------------------------------");
        System.out.println(" 레코드 삽입 = I            레코드 삭제 = D");
        System.out.println(" 레코드 검색 = S            레코드 출력 = P");
        System.out.println(" 종료       = Z                         ");
        System.out.println("------------------------------------------------------");

        System.out.print(" > ");
        Scanner sc = new Scanner(System.in);
        return sc.next().charAt(0);
    }



    public static void main(String[] args) {

        /* Connection 생성 */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.56.101:3308/madang",
                    "root", "1234"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Book");

            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2)
                        + " " + rs.getString(3));
            }

        } catch (Exception e) {
            System.out.println("DB 연결 도중 오류 발생.");
            System.out.println(e);
        }
        
        /* 커맨드 */
        while (true) {
            char cmd = printMenu();




        }
        
        /* Connection 닫기 */
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
