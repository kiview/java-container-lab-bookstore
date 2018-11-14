package com.bee42.javalab.bookstore;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

class BookRepository {

    private final String jdbcUrl;
    private final String username;
    private final String password;
    private Connection connection;

    BookRepository(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    void init() {

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            String createSql = "CREATE TABLE books (" +
                    "  id SERIAL," +
                    "  name varchar(255)," +
                    "  author varchar(255)" +
                    ");";

            connection.createStatement().execute(createSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void save(Book book) {
        String insertBookSql = "INSERT INTO books (name, author) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertBookSql)) {

            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    long count() {
        try (Statement s = connection.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT COUNT (*) FROM books");
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Book> findAllByAuthor(String author) {
        String selectSql = "SELECT name FROM books WHERE author = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectSql)) {

            ps.setString(1, author);

            ResultSet rs = ps.executeQuery();

            List<Book> books = new LinkedList<>();
            while (rs.next()) {
                String name = rs.getString(1);
                books.add(new Book(name, author));
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
