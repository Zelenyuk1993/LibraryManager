package com.company.model;

/**
 * @author Dima Zelenyuk
 */
public class Book {

    private Long id;

    private String name;

    private Author author = new Author();

    private Boolean available;

    public Book() {
    }

    public Book(String name, Boolean available) {
        this.name = name;
        this.available = available;
    }

    public Book(String book_name, String author_name, String author_last_name) {
        this.name = book_name;
        this.author.setName(author_name);
        this.author.setLastName(author_last_name);
        this.available = true;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}
