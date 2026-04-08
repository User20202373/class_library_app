package com.tenco.library.dao;

import com.tenco.library.dto.Book;
import com.tenco.library.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

public class BookDAO {

    public BookDAO() {
    }

    //도서추가
    public void addBook(Book book) throws SQLException {
        String sql = """
                INSERT INTO books(title,author,publisher,publisher,getPublicationYear,isbn)
                values(?, ?, ?, ?, ?)
                """;


        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setInt(4, book.getPublicationYear());
            pstmt.setString(5, book.getIsbn());
        }
    }

    //도서 전체 조회

    public List<Book> getAllBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = """
                select * from books order by id
                """;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);

             ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()){
                    bookList.add(mapToBook(rs));
                }
            }
        return bookList;
    }


    // 제목으로 도서 검색
    public List<Book> searchBooksByTitle(String title) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String sql = """
                select * from books where title like ?
                """;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookList.add(mapToBook(rs));
                }
            }
        }
        return bookList;
    }


    private Book mapToBook(ResultSet rs) throws SQLException {
        return Book.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .author(rs.getString("author"))
                .publisher(rs.getString("publisher"))
                .publicationYear(rs.getInt("publication_year"))
                .isbn(rs.getString("isbn"))
                .available(rs.getBoolean("available"))
                .build();
    }




}
