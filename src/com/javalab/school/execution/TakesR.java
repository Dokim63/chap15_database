package com.javalab.school.execution;

import com.javalab.school.domain.Takes;

import java.sql.*;

public class TakesR {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            // 성적 목록 호출 메소드
            displayTakes(conn);

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

    private static void displayTakes(Connection conn) {

        System.out.println("등록된 학과 목록:");

        String sql = "SELECT t.id , t.subject , t.score " +
                "FROM takes t " +
                "ORDER BY t.id ";


        PreparedStatement pstmt = null ;
        ResultSet rs = null;
        try  {

            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String subject = rs.getString("subject");
                String score = rs.getString("score");
                System.out.println(id + "\t" + subject + "\t" + score);
                // ResultSet 에 있는 행들을 하나씩 학생 객체
                Takes t = new Takes(id,subject,score);

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
