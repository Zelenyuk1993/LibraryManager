package com.company.consoleUI;
/**
 * @author Dima Zelenyuk
 */
import com.company.config.DbConnector;
import com.company.model.Author;
import com.company.model.Book;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private String menu = "";
    private Scanner sc = new Scanner(System.in);
    private String value[] = null;
    private DbConnector dc = new DbConnector();

    public void start() throws Exception {
        System.out.println("Hello in Library Manager:");
        while (true) {
            switch (menu) {
                case "":
                    menu = checkKey(sc.nextLine());
                    break;
                case "add":
                    Author author = new Author();
                    Book book = new Book();
                    author.setName(value[1]);
                    if (value.length <= 3){
                        book.setName(value[2]);
                    }else {
                        author.setLastName(value[2]);
                        book.setName(value[3]);
                    }
                    book.setAuthor(author);
                    dc.insertBook(book);
                    if(value.length <= 3)
                        System.out.println("book " + value[1] + ". " + value[2] + " was added");
                        else
                        System.out.println("book " + value[1] + ". " + value[2] + " \"" + value[3] + "\" was added");
                    menu = "";
                    break;
                case "remove":
                    Book bookRemove = dc.selectBookByName(this.value);
                    if (!bookRemove.getAvailable()){
                        System.out.println("Book is't available");
                        menu = "";
                        break;
                    }
                    System.out.println("book " + bookRemove.getAuthor().getName() +
                            " " + bookRemove.getAuthor().getLastName() +
                            " “"+ bookRemove.getName() +"” was removed ");
                    menu = "";
                    break;
                case "edit":
                    Book bookEdit = dc.selectBookByName(this.value);
                    if (!bookEdit.getAvailable()){
                        System.out.println("Book is't available");
                        menu = "";
                        break;
                    }
                    System.out.println("user enters new name: ");
                    bookEdit = dc.editBook(sc.nextLine());
                    System.out.println("book has new name is " + bookEdit.getName());
                    menu = "";
                    break;
                case "all":
                    Integer count = 1;
                    List<Book> books = dc.getAll();
                    if (books.isEmpty()){
                        System.out.println("Book is't available");
                        menu = "";
                        break;
                    }
                    for (Book book1 : books) {
                        if (book1.getAuthor().getLastName().equals("null")){
                            book1.getAuthor().setLastName("");
                            System.out.println(count + ". " + book1.getAuthor().getName() + ". " + book1.getAuthor().getLastName() + " " + book1.getName());
                        }else {
                            System.out.println(count + ". " + book1.getAuthor().getName() + ". " + book1.getAuthor().getLastName() + " \"" + book1.getName() + "\"");
                        }
                        count ++;
                    }
                    menu = "";
                    break;
            }
        }
    }

    private String checkKey(String value) {
        this.value = value.split(" ");
        if(this.value.length > 3 && value.split("\"").length > 1) {
            this.value[3] = value.split("\"")[1];
        }
        return this.value[0].toLowerCase();
    }
}
