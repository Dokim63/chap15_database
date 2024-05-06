package com.javalab.school.execution;

import com.javalab.school.domain.Professor;
import com.javalab.school.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorR {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            // 교수 목록 호출 메소드
            displayProfessor(conn);

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

    private static void displayProfessor(Connection conn) {

        System.out.println("등록된 교수 목록:");

        String sql = "SELECT p.professor_id, p.name, p.department_id,p.grade,p.hiredate " +
                "FROM professor p " +
                "ORDER BY p.professor_id";


        PreparedStatement pstmt = null ;
        ResultSet rs = null;
        try  {

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String professorId = rs.getString("professor_id");
                String name = rs.getString("name");
                int departmentId = rs.getInt("department_id");
                String grade = rs.getString("grade");
                String hiredate = rs.getString("hiredate");
                System.out.println(professorId + "\t" + name + "\t" + departmentId + "\t" + grade + "\t" + hiredate);
                // ResultSet 에 있는 행들을 하나씩 학생 객체
                Professor p = new Professor(professorId, name, departmentId,grade,hiredate);

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
