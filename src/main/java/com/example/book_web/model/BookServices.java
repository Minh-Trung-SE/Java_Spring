package com.example.book_web.model;

import com.example.book_web.dbconnection.DBConnector;
import com.example.book_web.entity.BookFavourite;
import com.example.book_web.entity.Books;
import com.example.book_web.entity.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BookServices {
    Connection connection;
    public BookServices() {
        this.connection = DBConnector.getConnectionDB();
    }

    //Method purpose to get list book categories
    public HashMap<Integer, String> getCategories(){
        //Initialize the array to get list book categories
        HashMap<Integer, String> listCategories = new HashMap<Integer, String>();
        //Initialize statement query
        String query = "SELECT * FROM `book`.`book_category`;";
        //Get list book categories from database
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

    //Method to check available category on database
    public boolean isValidCategory(int category_id, HashMap<Integer, String> listCategories){
        if(listCategories.get((Integer) category_id) == null){
            System.out.println(category_id + " not exist!");
            return false;
        }
        return true;
    }

    //Method will be return a response contain list book order by book filed
    public Response getBookOrderBy(int categoryId, int code, String orderBy, String order){
        ArrayList<Books> listBooks = new ArrayList<Books>();
        Books book = new Books();
        String query, message = null;

        /* If categoryID available after value code = 200
        else if categoryID not exist value code = -1  */
        if(code == 200){
            message = "Success!";
        }else {
            return new Response(-1, "Request failed", null);
        }

        /* If order is null or invalid order, order will be set default vale = ASC */
        if(order == null || ( !(order.equals("ASC")) && !(order.equals("DESC"))) ){
            order = "ASC";
        }
        query= "SELECT * FROM `book`.`book_storage` WHERE `category_id` = '" + categoryId +"' ORDER BY " + "'" + orderBy + "' " + order + ";";

        /*Block code will return list book order by filed book from database*/
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                book.setBookId(resultSet.getString("book_id"));
                book.setBookTitle(resultSet.getString("book_title"));
                book.setCategoryId(resultSet.getInt("categoryId"));
                book.setLinkPhoto(resultSet.getString("link_photo"));
                book.setReleaseYear((resultSet.getInt("release_year")));
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

    //Method show user book favorite
    public void getFavoriteBook(String userPhone){
        ArrayList<Books> listFavoriteBook = new ArrayList<Books>();
        String query = null;
        try {
            Statement statement = connection.createStatement();
            query = "SELECT " +
                    "`book`.`book_storage`.`link_photo`, " +
                    "`book`.`book_storage`.`book_title`, " +
                    "`book`.`users`.`user_phone`, " +
                    "`book`.`book_storage`.`release_year`, " +
                    "`book`.`book_storage`.`price` " +
                    " FROM " +
                    "`book`.`book_favourite` " +
                    "JOIN `book`.`book_storage` ON `book`.`book_favourite`.`book_id` = `book`.`book_storage`.`book_id` " +
                    "JOIN `book`.`users` ON `book`.`book_favourite`.`user_phone` = `book`.`users`.`user_phone` " +
                    "WHERE " +
                    "`book`.`book_favourite`.`user_phone` = '" + userPhone + "';";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                System.out.println("ok");
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    //Method post new book
    public void postBook(Books book, String userPhone){
        boolean categoryExist;
        String query;
        try{
            Statement statement = connection.createStatement();
            //Insert book to table "book_storage"
            query = "INSERT INTO `book`.`book_storage` (`book_id`, `book_title`, `category_id`, `link_photo`, `release_year`, `description`, `author`, `price`, `amount`) " +
                    "VALUES ('" + book.getBookId()+"', '" +
                     book.getBookTitle() +" '," +
                    " '" + book.getCategoryId() + "', " +
                    "'" + book.getLinkPhoto() + "'," +
                    " '" + book.getReleaseYear() + "'," +
                    " '" + book.getDescription() + "'," +
                    " '" + book.getAuthor() + "'," +
                    " '" + book.getPrice() + "'," +
                    " '" + book.getAmount() + "');\n";
            statement.executeUpdate(query);
            //Insert information about user post book to table "book_post"
            query = "INSERT INTO `book`.`book_post` (`user_phone`, `book_id`) " +
                    "VALUES ('" + userPhone + "', '" + book.getBookId() + "');";
            statement.executeUpdate(query);
            System.out.println("Posted book success!");
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    //Method show book favorite
    public ArrayList<BookFavourite> showFavoriteBook(String userPhone){
        ArrayList<BookFavourite> listBookFavourite = new ArrayList<BookFavourite>();
        BookFavourite bookFavourite = new BookFavourite();
        String query;
        //Build query
        query = "SELECT book.book_favourite.book_id, book.book_storage.book_title, book.book_storage.release_year, book.users.user_phone, book.book_storage.price, book.book_storage.price, book.book_storage.link_photo " +
                "FROM book.book_favourite " +
                "INNER JOIN book.book_storage ON book.book_favourite.book_id = book.book_storage.book_id " +
                "INNER JOIN book.book_post ON book.book_post.book_id = book.book_favourite.book_id " +
                "INNER JOIN book.users ON book.users.user_phone = book.book_post.user_phone ;";
        //Get data from database
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //Get detail favourite book one by one include( title book, link photo, contact, year release
            while (resultSet.next()){
                bookFavourite.setBookTitle(resultSet.getString("book_title"));
                bookFavourite.setLinkPhoto(resultSet.getString("link_photo"));
                bookFavourite.setPhoneContact(resultSet.getString("user_phone"));
                bookFavourite.setYearRelease(resultSet.getString("release_year"));
                bookFavourite.setPrice(resultSet.getLong("price"));
                listBookFavourite.add(bookFavourite);
            }
            return listBookFavourite;
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
