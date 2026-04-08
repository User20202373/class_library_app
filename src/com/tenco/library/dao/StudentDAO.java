package com.tenco.library.dao;

import com.tenco.library.dto.Student;
import com.tenco.library.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // 학생 등록
    public void addStudent(Student student) throws SQLException {

        String sql = """
                INSERT INTO students(name, student_id) VALUES (? , ?)
                """;

        //url, user, pw ... ...
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getStudentId());
            pstmt.executeUpdate();
        }

    }

    //전체 학생 조회
    public List<Student> getAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String sql = """
                SELECT * FROM students ORDER BY id
                """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
//                Student student = new Student();
//                student.setId(rs.getInt("id"));
//                student.setName(rs.getString("name"));
//                student.setStudentId(rs.getString("student_id"));
                //studentList.add(student);
                studentList.add(mapToStudent(rs));

            }
        }
        return studentList;
    }


    // 학번으로 학생 조회 - 로그인
    public Student authenticateStudent(String studentId) throws SQLException {
        String sql = """
                SELECT * FROM students WHERE student_id = ?
                """;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
//                    Student student = new Student();
//                    student.setId(rs.getInt("id"));
//                    student.setName(rs.getString("name"));
//                    student.setStudentId(rs.getString("student_id"));

                    return mapToStudent(rs);
                }
            }
        }
        return null;
    }

//    // 학생 이름 학번수정하기
//    public Student modifyStudentInfo(int id, String name, String studentId) throws SQLException {
//        Connection conn = null; // 트랜 잭션 시작
//        try {
//            conn = DatabaseUtil.getConnection();
//            conn.setAutoCommit(false);
//
//            //1. 학생 이름 학번 입력후 학생 있는지 찾기
//            String infoSql = """
//                    SELECT id
//                      FROM students
//                      WHERE id = ?
//                    """;
//            int studentInfo;
//            try (PreparedStatement infoPstmt = conn.prepareStatement(infoSql)) {
//                infoPstmt.setInt(1, id);
//
//                try (ResultSet rs = infoPstmt.executeQuery()) {
//                    if (rs.next() == false) {
//                        throw new SQLException("등록 되지 않은 학생입니다");
//                    }
//                   studentInfo =rs.getInt("id");
//                }
//            }
//            //2. 해당 학생 이름 수정하기
//
//            String modifyStudentNameSql = """
//                    UPDATE student SET name = ?  WHERE id = ?
//                    """;
//
//            try (PreparedStatement modfiyPstmt = conn.prepareStatement(modifyStudentNameSql)) {
//                modfiyPstmt.setString(1, name);
//                modfiyPstmt.executeUpdate();
//            }
//
//
//            // 3. 해당 학생 학번 수정하기
//            String modifyStudentIdSql = """
//                    UPDATE student SET student_id = ?  WHERE id = ?
//                    """;
//
//            try (PreparedStatement modfiyPstmt = conn.prepareStatement(modifyStudentIdSql)) {
//                modfiyPstmt.setString(1, studentId);
//                modfiyPstmt.executeUpdate();
//            }
//            conn.commit();
//
//        } catch (SQLException e) {
//            if (conn != null) {
//                conn.rollback();
//            }
//            System.out.println("오류 발생 : " + e.getMessage());
//        } finally {
//            if (conn != null) {
//                conn.setAutoCommit(true);
//                conn.close();
//            }
//        }
//    }


    //ResultSet -> Student 변환 메서드
    private Student mapToStudent(ResultSet rs) throws SQLException {
        return Student.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .studentId(rs.getString("student_id"))
                .build();
    }

}
