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

    // 학생 이름 학번수정하기
    //1. 학생 이름 학번 입력후 학생 있는지 찾기
    public Student getStudentInfo(String name, String studentId) throws SQLException {
        String infoSql = """
                SELECT id
                  FROM students
                  WHERE name = ? AND student_id = ?
                """;

        try (Connection conn = DatabaseUtil.getConnection();
        PreparedStatement infoPstmt = conn.prepareStatement(infoSql)) {

        }


    }
    //2. 해당 학생 이름 수정하기

    //2. 해당 학생 학번 수정하기


    public Student modifyStudentInfo(String name, String studentId, int id) throws SQLException {

        String modifySql = """
                UPDATE student SET name = ?, student_id = ? WHERE id = ?
                """;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(modifySql)) {


        }

        return null;
    }

    //ResultSet -> Student 변환 메서드
    private Student mapToStudent(ResultSet rs) throws SQLException {
        return Student.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .studentId(rs.getString("student_id"))
                .build();
    }

}
