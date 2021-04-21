package com.bookweb.com;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class book {
    String book_id;
    String book_title;
    int category_id;
    String link_photo;
    int release_year;
    String description;
    String author;
    int price;
    int amount;

    void showTitleBookByCategory(String user_phone, Connection connection) throws SQLException {
        String user_name, query;
        int id;
        /*Show user name*/
        Statement statement = connection.createStatement();
        query = "SELECT `user_name` FROM `book`.`users` WHERE user_phone = '"+ user_phone +"';" ;
        ResultSet result = statement.executeQuery(query);
        if(result.next()){
            user_name = result.getString("user_name");
            System.out.println("User Name: "+user_name);
        }
        /*Get list category*/
        ArrayList <book_category> categories = new ArrayList<book_category>();
        query = "SELECT * FROM book.book_category;";
        result = statement.executeQuery(query);
        while (result.next()){
            categories.add(new book_category(result.getInt("category_id"),result.getString("category_name")));
        }
        /*Show title book group by category*/
        for (book_category category: categories) {
            System.out.println("\n"+category.getCategory_name());
            id = category.getCategory_id();

            query = "SELECT book_title FROM book.book_storage WHERE category_id ='" + id +"';";
            result = statement.executeQuery(query);
            ArrayList <String> title_book = new ArrayList<String>();
            while (result.next()){
                title_book.add(result.getString("book_title"));
            }
            for (String title: title_book) {
                System.out.println("\t"+title);
            }
        }

    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getLink_photo() {
        return link_photo;
    }

    public void setLink_photo(String link_photo) {
        this.link_photo = link_photo;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

