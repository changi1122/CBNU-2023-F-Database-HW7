/*
*   데이터베이스시스템 HW7 : JDBC 이용
*   작성자 : 2019038074 이우창
*/

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Connection con;

    // 프로그램 메뉴를 출력하는 함수
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

    // 레코드를 삽입
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

    // 레코드 삭제
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

    // 레코드 검색 (by 애트리뷰트에 대해 %query%와 일치하는 레코드 찾기)
    private static void searchBook(String by, String query) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM Book WHERE " + by + " LIKE '%" + query + "%'"
            );
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n[\"" + query + "\" 검색 결과]");
            System.out.println("bookid      bookname        publisher       price");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t\t\t" + rs.getString(2)
                        + "\t\t" + rs.getString(3) + "\t\t" + rs.getInt(4));
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 전체 레코드 출력
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

    // '>' 출력 후 사용자 입력 받기
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

            // 입력에 따라 기능 수행
            switch (cmd) {
                case 'I':
                case 'i': // 레코드 삽입
                    insertRecord(
                            Integer.parseInt(getInput("Book ID")),
                            getInput("제목"),
                            getInput("출판사"),
                            Integer.parseInt(getInput("가격"))
                    );
                    break;

                case 'D':
                case 'd': // 레코드 삭제
                    deleteRecord(Integer.parseInt(getInput("삭제할 Book ID")));
                    break;

                case 'S':
                case 's': // 레코드 검색
                    if (getInput("제목으로 검색(Y/N)").equals("Y")) {
                        searchBook("bookname", getInput("검색할 제목"));
                    }
                    else {
                        searchBook("publisher", getInput("검색할 출판사"));
                    }
                    break;

                case 'P':
                case 'p': // 전체 레코드 출력
                    printAllRecord();
                    break;

                case 'Z':
                case 'z': // 종료
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
