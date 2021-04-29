package com.example.book_web.model;

import com.example.book_web.dbconnection.DBConnector;
import com.example.book_web.entity.Books;
import com.example.book_web.entity.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Book_Services {
    Connection connection;
    public Book_Services() {
        this.connection = DBConnector.connection;
    }

    public HashMap<Integer, String> getCategories(){
        HashMap<Integer, String> listCategories = new HashMap<Integer, String>();
        String query = "SELECT * FROM `book`.`book_category`;";

        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                listCategories.put(resultSet.getInt("category_id"), resultSet.getString("category_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listCategories;
    }

    public boolean isValidCategory(int category_id, HashMap<Integer, String> listCategories){
        if(listCategories.get((Integer) category_id) == null){
            System.out.println(category_id + " not exist!");
            return false;
        }
        return true;
    }

    public Response getBookOrderBy(int category_id, int code, String orderBy, String order){
        ArrayList<Books> listBooks = new ArrayList<Books>();
        Books book = new Books();
        String query, message = null;
        if(code == 200){
            message = "Success!";
        }else {
            return new Response(-1, "Request failed", null);
        }
        if(order == null || ( !(order.equals("ASC")) && !(order.equals("DESC"))) ){
            order = "ASC";
        }
        query= "SELECT * FROM `book`.`book_storage` WHERE `category_id` = '" + category_id +"' ORDER BY " + "'" + orderBy + "' " + order + ";";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                book.setBook_id(resultSet.getString("book_id"));
                book.setBook_title(resultSet.getString("book_title"));
                book.setCategory_id(resultSet.getInt("category_id"));
                book.setLink_photo(resultSet.getString("link_photo"));
                book.setRelease_year((resultSet.getInt("release_year")));
                book.setDescription(resultSet.getString("description"));
                book.setAuthor(resultSet.getString("author"));
                book.setPrice(resultSet.getLong("price"));
                book.setAmount(resultSet.getInt("amount"));
                listBooks.add(book);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Response(code, message, listBooks);
    }
}
