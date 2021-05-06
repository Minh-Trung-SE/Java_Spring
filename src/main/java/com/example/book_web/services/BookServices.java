package com.example.book_web.services;

import com.example.book_web.entity.BookFavourite;
import com.example.book_web.entity.BookPosted;
import com.example.book_web.entity.Books;
import com.example.book_web.entity.Response;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
@Service
public class BookServices {
    /*Injection object connection using constructor injection*/
    private final Connection connection;
    public BookServices(Connection connection) {
        this.connection = connection;
    }

    //Method purpose to get list book categories
    public HashMap<Integer, String> getCategories(){
        //Initialize the array to get list book categories
        HashMap<Integer, String> listCategories = new HashMap<>();
        //Initialize string query
        String query = "SELECT * FROM `book`.`book_category`;";
        //Get list book categories from database
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                //add key and value each object to hash map
                listCategories.put(resultSet.getInt("category_id"), resultSet.getString("category_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        //Return hash map contains list key = categoryId and value = description categories
        return listCategories;
    }

    //Method to check available category on database
    public boolean isValidCategory(int category_id){
        //Initialize string query
        String query;
        try {
            //Build query
            query = "SELECT * FROM `book`.`book_category` WHERE `category_id` = '" + category_id + "';";
            //Create statement
            Statement statement = connection.createStatement();
            //Crete result set to receive result after execute query
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                //If resultSet after execute query have lest one result return true, this mean category available
                return true;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        //Return false if categoryId invalid or an exception has occurred
        return false;
    }

    //Method will be return a response contain list book order by book filed
    public Response getBookOrderBy(int categoryId, String orderBy, String order){
        //Initialize arraylist to save object book
        ArrayList<Books> listBooks = new ArrayList<>();
        //Initialize variable code to return code status when execute request
        int code;
        //Initialize string message to return status when execute request
        String query, message;

        /* If order invalid order, order will be set default vale = ASC */
        if( !(order.equals("ASC")) && !(order.equals("DESC")) ){
            order = "ASC";
        }
        //Build query
        query= "SELECT * FROM `book`.`book_storage` WHERE `category_id` = '" + categoryId +"' ORDER BY " + "'" + orderBy + "' " + order + ";";

        /*Block code will return list book order by filed book from database*/
        try {
            //Create statement
            Statement statement = connection.createStatement();
            //Create result set to receive list book after execute query on data base
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                //Create distinct object with datatype is book for each book
                Books book = new Books();
                //Set attribute for each book
                book.setBookId(resultSet.getString("book_id"));
                book.setBookTitle(resultSet.getString("book_title"));
                book.setCategoryId(resultSet.getInt("category_id"));
                book.setLinkPhoto(resultSet.getString("link_photo"));
                book.setReleaseYear((resultSet.getInt("release_year")));
                book.setDescription(resultSet.getString("description"));
                book.setAuthor(resultSet.getString("author"));
                book.setPrice(resultSet.getLong("price"));
                book.setAmount(resultSet.getInt("amount"));
                //Add object book to list book
                listBooks.add(book);
            }
            /* If categoryID available after value code = 200
            else if categoryID not exist value code = -1  */
            code = 200 ;
            message = "Success!";
            return new Response(code, message, listBooks);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Response(-1, "Request failed", null);
    }

    //Method show user book favorite
    public ArrayList<BookFavourite> getFavoriteBook(String userPhone){
        ArrayList<BookFavourite> listFavoriteBook = new ArrayList<>();
        String query;
        try {
            //Build query
            query = "SELECT `book`.`book_storage`.`link_photo`, `book`.`book_storage`.`book_title`, `book`.`book_post`.`user_phone`, `book`.`book_storage`.`release_year`, `book`.`book_storage`.`price`  \n" +
                    "FROM `book`.`book_favourite` \n" +
                    "JOIN `book`.`book_storage` ON `book`.`book_favourite`.`book_id` = `book`.`book_storage`.`book_id` \n" +
                    "JOIN `book`.`book_post` ON `book`.`book_favourite`.`book_id` = `book`.`book_post`.`book_id` \n" +
                    "WHERE `book`.`book_favourite`.`user_phone` = " + userPhone;
            System.out.println(query);
            //Create statement
            Statement statement = connection.createStatement();
            //Create result set to receive
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                BookFavourite bookFavourite = new BookFavourite();
                bookFavourite.setBookTitle(resultSet.getString("book_title"));
                bookFavourite.setLinkPhoto(resultSet.getString("link_photo"));
                bookFavourite.setPhoneContact(resultSet.getString("user_phone"));
                bookFavourite.setYearRelease(resultSet.getString("release_year"));
                bookFavourite.setPrice(resultSet.getLong("price"));
                listFavoriteBook.add(bookFavourite);
            }
            return listFavoriteBook;
        }catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    //Method dislike book
    public void disLikeBook(String userPhone, String bookId){
        String query;
        query = "DELETE FROM `book`.`book_favourite` WHERE (`user_phone` = '" + userPhone + "') and (`book_id` = '" + bookId + "');";
        try {
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate(query);
            if(flag > 0){
                System.out.println("Dislike success!");
            }else {
                System.out.println("Dislike failed!");
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

    //Method show post book
    public ArrayList<BookPosted> showBookPosted(String userPhone){
        String query;
        ArrayList<BookPosted>  bookPosts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            query = "SELECT `date`, `link_photo`, `book_title`, `price`, `release_year` FROM `book`.book_post\n" +
                    "LEFT JOIN `book`.`book_storage` ON book_storage.book_id = book_post.book_id\n" +
                    "WHERE book_post.user_phone = " + userPhone + ";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                BookPosted bookPosted = new BookPosted();
                bookPosted.setTimePosted(resultSet.getTimestamp("date"));
                bookPosted.setBookTitle(resultSet.getString("book_title"));
                bookPosted.setPrice(resultSet.getLong("price"));
                bookPosted.setLinkPhoto(resultSet.getString("link_photo"));
                bookPosted.setReleaseYear(resultSet.getString("release_year"));
                bookPosts.add(bookPosted);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        return bookPosts;
    }

    //Method edit posted book
    public void changeInformationPostedBook(String bookId, String bookTitle, String linkPhoto, String releaseYear, long price){
        String query;
        query = "UPDATE `book`.`book_storage` SET ";
        if(!bookTitle.isEmpty()){
            query = query + " `book_title` = " + bookTitle + "'";
        }
        if(!linkPhoto.isEmpty()){
            query = query + ", `link_photo` = " + linkPhoto + "'";
        }
        if(!releaseYear.isEmpty()){
            query = query + ", `release_year` = " + releaseYear + "'";
        }
        if(price != 0){
            query = query + ", `price` = '" + price + "'";
        }
        query = query + " WHERE `book_id` = '" + bookId + "';";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        }catch(Exception exception){
            exception.printStackTrace();
            System.out.println("Update failed!");
        }
        System.out.println("Update success!");
    }

    public ArrayList<Books> searchBookByTitle(String bookTitle){
        ArrayList<Books> listBooks = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return listBooks;
    }
}
