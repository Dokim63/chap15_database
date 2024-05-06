package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TakesU {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            updateTakes(conn, scanner);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // end of main

    private static void updateTakes(Connection conn, Scanner scanner) {
        System.out.println("[수정할 성적정보 입력]");
        System.out.print("학생 ID: ");
        String id = scanner.nextLine();
        System.out.println();
        System.out.print("과목명 : ");
        String name = scanner.nextLine();
        System.out.println();
        System.out.print("점수 : ");
        String office = scanner.nextLine();
        System.out.println();

        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE takes SET subject = ?, score = ? WHERE id = ?";

            pstmt = conn.prepareStatement(sql);

            // 쿼리문 실행 시에 전달할 파라미터(?)를 세팅한다.
            // setString(파라미터 인덱스, 파라미터 값) : 파라미터 인덱스는 1부터 시작
            pstmt.setString(1, name);
            pstmt.setString(2, office);
            pstmt.setString(3, id);

            // 쿼리문의 파라미터인 ? 를 채운 후 쿼리 실행
            pstmt.executeUpdate(); // 쿼리 실행 저장/수정/삭제는 executeUpdate() 메소드 사용
            System.out.println("성적정보가 성공적으로 수정되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
