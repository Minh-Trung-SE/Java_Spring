package com.bookweb.com;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class book_post {
    String user_id;
    int book_id;
    Timestamp date;
    //Done post Book
    public void postBook(String user_phone, Connection connection) throws SQLException {
        String book_id, book_title, link_book, description, author, query;
        int category_id, quantity, year_release;
        long price;
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Post Book Page ===");
        System.out.print("Enter book id: ");
        book_id = scanner.nextLine();
        System.out.print("Enter book title: ");
        book_title = scanner.nextLine();
        System.out.print("Enter link book: ");
        link_book = scanner.nextLine();
        System.out.print("Enter description: ");
        description = scanner.nextLine();
        System.out.print("Enter name author: ");
        author = scanner.nextLine();
        System.out.print("Enter price: ");
        price = scanner.nextLong();
        System.out.print("Enter quantity: ");
        quantity = scanner.nextInt();
        System.out.print("Enter year release: ");
        year_release = scanner.nextInt();
        System.out.print("Enter category: ");
        category_id = scanner.nextInt();
        Statement statement = connection.createStatement();

        query = "INSERT INTO book.book_storage(book_id, book_title, category_id, link_photo, release_year, description, author, price, amount) " +
                "VALUES('" + book_id + "','" + book_title +"','" + category_id + "','" + link_book + "','" + year_release + "','" + description + "','" + author + "','" + price + "','" + quantity + "');";
        statement.executeUpdate(query);

        query = "INSERT INTO `book`.book_post(user_phone, book_id) VALUES ('" + user_phone + "', '" + book_id + "');";
        statement.executeUpdate(query);

        System.out.println("Success!");
    }


    public void showPostBook(String user_phone, Connection connection) throws SQLException {
        String query;
        Statement statement = connection.createStatement();
        ArrayList<String> book_id = new ArrayList<String>();

        query = "SELECT book_id FROM `book`.`book_post` WHERE user_phone = '" + user_phone +"';";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
            book_id.add(resultSet.getString("book_id"));
        }

        for (String s : book_id) {
            System.out.println("BookID: " + s);

            query = "SELECT  `book_storage`.`link_photo`, `book_storage`.`book_title`, `book_post`.`date`, `book_storage`.`release_year`, `book_storage`.`price` " +
                    "FROM `book`.`book_post`" +
                    "INNER JOIN `book`.`book_storage` ON `book`.`book_storage`.`book_id` = `book`.`book_post`.`book_id`" +
                    "WHERE `book`.`book_post`.`book_id` = '" + s + "';";

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("    Link photo: " + resultSet.getString("link_photo"));
                System.out.println("    Book Title: " + resultSet.getString("book_title"));
                System.out.println("    Date update: " + resultSet.getString("date"));
                System.out.println("    Year release: " + resultSet.getString("release_year"));
                System.out.println("    Price: " + resultSet.getString("price"));
            }
        }

    }

    public void editPostBook(String user_phone, Connection connection) throws SQLException {
        String query = "";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

}
