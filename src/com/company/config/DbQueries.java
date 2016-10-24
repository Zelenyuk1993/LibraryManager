package com.company.config;
/**
 * @author Dima Zelenyuk
 */
public interface DbQueries {
    String SELECT_ALL_BOOKS_QUERY = "SELECT book.name book_name, author.name author_name, author.last_name author_last_name " +
            "FROM author INNER JOIN book ON " +
            "author.id = book.author_id ORDER BY book.name";
    String DELETE_BOOK_BY_NAME_QUERY = "DELETE book, author FROM book INNER JOIN author ON book.author_id = author.id WHERE book.name like '%1$s'";
    String SELECT_AUTHOR_ID_BY_NAME_QUERY = "SELECT id FROM author WHERE name like '%1$s' and last_name like '%2$s'";
    String SELECT_BOOK_BY_NAME_QUERY = "SELECT book.name book_name, author.name author_name, author.last_name author_last_name " +
            "FROM author INNER JOIN book ON " +
            "author.id = book.author_id" +
            " WHERE book.name = '%1$s'";
    String UPDATE_USER_BY_ID_QUERY = "UPDATE book SET name = '%1$s' WHERE name like '%2$s'";
    String INSERT_USER_QUERY = "INSERT INTO book (name, author_id) VALUES ('%1$s', (SELECT id FROM author WHERE name like '%2$s' and last_name like '%3$s'))";
    String INSERT_AUTHOR_QUERY = "INSERT INTO author (name, last_name) VALUES ('%1$s', '%2$s')";
    String CREATE_TABLE_BOOK_QUERY = " CREATE TABLE book (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) not NULL, author_id int, CONSTRAINT fk_book_author FOREIGN KEY book(author_id) REFERENCES author(id) ON DELETE CASCADE ON UPDATE CASCADE) ENGINE=InnoDB;";
    String CREATE_TABLE_AUTHOR_QUERY = "CREATE TABLE author (id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) not NULL, last_name VARCHAR(255)) ENGINE=InnoDB";

    String CONNECTION_QUERY = "jdbc:mysql://localhost/library?useSSL=false&serverTimezone=UTC&user=%s&password=%s";
}