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

    private static void insertRecord(Integer id, String name, String pub, Integer price) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Book(bookid, bookname, publisher, price) VALUES (?, ?, ?, ?)"
            );
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, pub);
            stmt.setInt(4, price);
            stmt.execute();

            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void deleteRecord(Integer id) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "DELETE FROM Book WHERE bookid = ?"
            );
            stmt.setInt(1, id);
            stmt.execute();

            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printAllRecord() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
            System.out.println("bookid      bookname        publisher       price");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t\t\t" + rs.getString(2)
                        + "\t\t" + rs.getString(3) + "\t\t" + rs.getInt(4));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("레코드  도중 오류 발생.");
        }
    }

    private static String getInput(String target) {
        System.out.print(((target.isEmpty()) ? "" : " " + target) + " > ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static void main(String[] args) {

        /* Connection 생성 */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.56.101:3308/madang",
                    "root", "1234"
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        
        /* 커맨드 */
        LOOP: while (true) {
            char cmd = printMenu();

            switch (cmd) {
                case 'I':
                case 'i':
                    insertRecord(
                            Integer.parseInt(getInput("Book ID")),
                            getInput("제목"),
                            getInput("출판사"),
                            Integer.parseInt(getInput("가격"))
                    );
                    break;

                case 'D':
                case 'd':
                    deleteRecord(Integer.parseInt(getInput("삭제할 Book ID")));
                    break;

                case 'P':
                case 'p':
                    printAllRecord();
                    break;

                case 'Z':
                case 'z':
                    break LOOP;
            }
        }
        
        /* Connection 닫기 */
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
