package com.javalab.school.execution;

import com.javalab.school.domain.Department;
import com.javalab.school.domain.Professor;

import java.sql.*;

public class DepartMentR {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            // 학과 목록 호출 메소드
            displayDepartment(conn);

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

    private static void displayDepartment(Connection conn) {

        System.out.println("등록된 학과 목록:");

        String sql = "SELECT d.department_id, d.name, d.office " +
                "FROM department d " +
                "ORDER BY d.department_id";


        PreparedStatement pstmt = null ;
        ResultSet rs = null;
        try  {

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int departmentId = rs.getInt("department_id");
                String name = rs.getString("name");
                String office = rs.getString("office");
                System.out.println(departmentId + "\t" + name + "\t" + office);
                // ResultSet 에 있는 행들을 하나씩 학생 객체
                Department d = new Department(departmentId,name,office);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // ResultSet, PreparedStatement, Connection 순으로 닫기
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("-----------------------------------------------------------------------");
    }

}
