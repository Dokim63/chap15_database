package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ProfessorU {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            updateProfessor(conn, scanner);

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

    private static void updateProfessor(Connection conn, Scanner scanner) {
        System.out.println("[수정할 교수정보 입력]");
        System.out.print("교수 ID: ");
        String id = scanner.nextLine();
        System.out.println();
        System.out.print("주민번호: ");
        String jumin = scanner.nextLine();
        System.out.println();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.println();
        System.out.print("학과 ID: ");
        int departmentId = scanner.nextInt();
        System.out.print("직급 : ");
        String grade = scanner.nextLine();
        System.out.println();
        System.out.print("입사 년도 : ");
        String hiredate = scanner.nextLine();
        System.out.println();

        PreparedStatement pstmt = null;
        try {
            String sql = "UPDATE professor SET jumin = ?, name = ?, grade = ?, hiredate = ?, department_id = ? WHERE professor_id = ?";

            pstmt = conn.prepareStatement(sql);

            // 쿼리문 실행 시에 전달할 파라미터(?)를 세팅한다.
            // setString(파라미터 인덱스, 파라미터 값) : 파라미터 인덱스는 1부터 시작
            pstmt.setString(1, jumin); // pstmt.setString(1, "값") : 첫번째 ?에 값 세팅
            pstmt.setString(2, name);
            pstmt.setInt(3, departmentId);  // pstmt.setInt(2, 100) : 두번째 ?에 100 세팅
            pstmt.setString(4, grade);
            pstmt.setString(5, hiredate);
            pstmt.setString(6, id);

            // 쿼리문의 파라미터인 ? 를 채운 후 쿼리 실행
            pstmt.executeUpdate(); // 쿼리 실행 저장/수정/삭제는 executeUpdate() 메소드 사용
            System.out.println("교수정보가 성공적으로 등록되었습니다.");
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
