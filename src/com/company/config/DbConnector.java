package com.company.config;
/**
 * @author Dima Zelenyuk
 */
import com.company.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.company.config.DbQueries.CREATE_TABLE_AUTHOR_QUERY;
import static com.company.config.DbQueries.CREATE_TABLE_BOOK_QUERY;

public class DbConnector {


    private String username = "root";
    private String password = "11021993";
    private String nameEdit;

    private Connection connect = null;
    private Statement statement = null;

    private void connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect = DriverManager.getConnection(String.format(DbQueries.CONNECTION_QUERY, username, password));
        statement = connect.createStatement();
        ResultSet resultSetAuthor = connect.getMetaData().getTables(null, null, "author", null);
        ResultSet resultSetBook = connect.getMetaData().getTables(null, null, "book", null);
        if (!resultSetAuthor.next())
            statement.executeUpdate(CREATE_TABLE_AUTHOR_QUERY);
        if (!resultSetBook.next())
            statement.executeUpdate(CREATE_TABLE_BOOK_QUERY);
    }

    private void closeConnection() throws SQLException {
        connect.close();
    }

    public void insertBook(Book book) throws Exception {
        connection();
        if(book.getAuthor()!= null && !checkAuthor(book.getAuthor().getName(), book.getAuthor().getLastName())){
            statement.executeUpdate(String.format(DbQueries.INSERT_AUTHOR_QUERY,
                    book.getAuthor().getName(),
                    book.getAuthor().getLastName()));
        }
        statement.executeUpdate(String.format(DbQueries.INSERT_USER_QUERY,
                book.getName(),
                book.getAuthor().getName(),
                book.getAuthor().getLastName()));
        closeConnection();
    }

    public Book selectBookByName(String[] value) throws Exception {
        connection();
        String name = "";
        for (int i = 1; i <= value.length - 1; i++){
            name += value[i];
            if (i < value.length - 1) name += " ";
        }
        ResultSet resultSet = statement.executeQuery(String.format(DbQueries.SELECT_BOOK_BY_NAME_QUERY, name));
        if(!resultSet.first()) {
            return new Book("Not Available this book", false);
        }
        if (value[0].equals("remove"))
            removeBookByName(name);
        if (value[0].equals("edit")){
            nameEdit = name;
            return new Book("", true);
        }
        return writeResultSet(resultSet).get(0);
    }

    public List<Book> getAll() throws Exception {
        connection();
        ResultSet resultSet = statement.executeQuery(String.format(DbQueries.SELECT_ALL_BOOKS_QUERY));
        return writeResultSet(resultSet);
    }


    private void removeBookByName(String name) throws Exception {
        connection();
        statement.execute(String.format(DbQueries.DELETE_BOOK_BY_NAME_QUERY, name));
        closeConnection();
    }

    private List<Book> writeResultSet(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()) {
            books.add(new Book(
                    resultSet.getString("book_name"),
                    resultSet.getString("author_name"),
                    resultSet.getString("author_last_name")));
        }
        closeConnection();
        return books;
    }

    public Book editBook(String name) throws Exception {
        connection();
        statement.executeUpdate(String.format(
                DbQueries.UPDATE_USER_BY_ID_QUERY,
                name, nameEdit));
        closeConnection();
        return new Book(name, true);
    }

    private Boolean checkAuthor(String name, String lastName) throws SQLException {
        ResultSet resultSet = statement.executeQuery(String.format(DbQueries.SELECT_AUTHOR_ID_BY_NAME_QUERY, name, lastName));
        if(resultSet.first())
           return true;
        return false;
    }


//    public Book getBooks() throws Exception {
//        connection();
//        Book book = new Book();
//        return ;
//    }

}
