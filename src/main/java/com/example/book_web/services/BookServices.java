package com.example.book_web.services;

import com.example.book_web.entity.BookCategories;
import com.example.book_web.repository.BookCategoryRepository;
import com.example.book_web.repository.BookRepository;
import com.example.book_web.urlcontroler.responseModel.ResponseListBookFavourite;
import com.example.book_web.urlcontroler.responseModel.ResponseListBookPosted;
import com.example.book_web.entity.Books;
import com.example.book_web.urlcontroler.requestModel.PostBookForm;
import com.example.book_web.urlcontroler.responseModel.ResponseListBookOrderByCategory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
@Service
public class BookServices {
    /*Injection object connection using constructor injection*/
    private final Connection connection;
    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;
    public BookServices(Connection connection, BookRepository bookRepository, BookCategoryRepository bookCategoryRepository) {
        this.connection = connection;
        this.bookRepository = bookRepository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    //Method purpose to get list book categories
    public ArrayList<BookCategories> getCategories(){
        //Initialize the array to get list book categories
        ArrayList<BookCategories> listCategories = new ArrayList<>();
        try{
              listCategories = (ArrayList<BookCategories>) bookCategoryRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listCategories;
    }

    //Method to check available category on database
    public boolean isValidCategory(int category_id){
        try {
            if(bookCategoryRepository.findBookCategoriesByCategoryId(category_id) != null){
                return true;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        //Return false if categoryId invalid or an exception has occurred
        return false;
    }

    //Method will be return a response contain list book order by book filed
    public ResponseListBookOrderByCategory getBookOrderBy(int categoryId, String orderBy, String order){
        //Initialize arraylist to save object book
        ArrayList<Books> listBooks = new ArrayList<>();
        int code;
        String message;
        if(isValidCategory(categoryId)){
            listBooks = bookRepository.findAllByCategoryId(categoryId);
            code = 200 ;
            message = "Success!";
            return new ResponseListBookOrderByCategory(code, message, listBooks);
        }else {
            return new ResponseListBookOrderByCategory(-1, "Request failed", null);
        }
    }

    //Method show user book favorite
    public ArrayList<ResponseListBookFavourite> getFavoriteBook(String userPhone, String orderBy, String order){
        ArrayList<ResponseListBookFavourite> listFavoriteBook = new ArrayList<>();
        String query;
        /* If order invalid order, order will be set default vale = ASC */
        if( !(order.equals("ASC")) && !(order.equals("DESC")) ){
            order = "ASC";
        }
        try {
            //Build query
            query = "SELECT `book`.`book_storage`.`link_photo`, `book`.`book_storage`.`book_id`, `book`.`book_storage`.`book_title`, `book`.`book_post`.`user_phone`, `book`.`book_storage`.`release_year`, `book`.`book_storage`.`price`  \n" +
                    "FROM `book`.`book_favourite` \n" +
                    "JOIN `book`.`book_storage` ON `book`.`book_favourite`.`book_id` = `book`.`book_storage`.`book_id` \n" +
                    "JOIN `book`.`book_post` ON `book`.`book_favourite`.`book_id` = `book`.`book_post`.`book_id` \n" +
                    "WHERE `book`.`book_favourite`.`user_phone` = " + userPhone + " ORDER BY " + orderBy + " " + order;
            //Create statement
            Statement statement = connection.createStatement();
            //Create result set to receive
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                ResponseListBookFavourite responseListBookFavourite = new ResponseListBookFavourite();
                responseListBookFavourite.setBookId(resultSet.getInt("book_id"));
                responseListBookFavourite.setBookTitle(resultSet.getString("book_title"));
                responseListBookFavourite.setLinkPhoto(resultSet.getString("link_photo"));
                responseListBookFavourite.setPhoneContact(resultSet.getString("user_phone"));
                responseListBookFavourite.setYearRelease(resultSet.getString("release_year"));
                responseListBookFavourite.setPrice(resultSet.getLong("price"));
                listFavoriteBook.add(responseListBookFavourite);
            }
            return listFavoriteBook;
        }catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    //Method disfavoured book
    public String disFavouriteBook(String userPhone, String bookId){
        String query;
        query = "DELETE FROM `book`.`book_favourite` WHERE (`user_phone` = '" + userPhone + "') and (`book_id` = '" + bookId + "');";
        try {
            Statement statement = connection.createStatement();
            if(statement.executeUpdate(query) == 1){
                return "Dislike success!";
            }
        }catch (SQLException exception){
            exception.printStackTrace();
            return  "Server internal error. Dislike failed!";
        }
        return  "Dislike failed!";
    }

    //Method add favoured book
    public String addFavouriteBook(String userPhone, String bookId){
        String query;

        try {
            Statement statement = connection.createStatement();
            query = "SELECT book_id FROM `book`.`book_favourite` WHERE book_id = '" + bookId + "' AND user_phone = '" + userPhone + "';";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()){
                return "Duplicate data. Add failed!";
            }else {
                query = "INSERT INTO `book`.`book_favourite` (`user_phone`, `book_id`) VALUES ('" + userPhone + "', '" + bookId + "');";
                if(statement.executeUpdate(query) == 1){
                    return "Add success!";
                }
            }
        }catch (SQLException exception){
            exception.printStackTrace();
            return  "Server internal error. Add failed!";
        }
        return  "Add failed!";
    }
    //Method post new book
    public String postBook(PostBookForm bookForm){
        boolean categoryExist;
        String query;
        try{
            Statement statement = connection.createStatement();
            for(int i = 0; i < bookForm.listPostBook.size(); i++){

                //Insert book to table "book_storage"
                query = "INSERT IGNORE INTO `book`.`book_storage` (`book_id`, `book_title`, `category_id`, `link_photo`, `release_year`, `description`, `author`, `price`, `amount`) \n" +
                        "VALUES ('" + bookForm.listPostBook.get(i).getBookId() +"', '" +
                        bookForm.listPostBook.get(i).getBookTitle() + " '," +
                        " '" + bookForm.listPostBook.get(i).getCategoryId() + "', " +
                        "'" + bookForm.listPostBook.get(i).getLinkPhoto() + "'," +
                        " '" + bookForm.listPostBook.get(i).getReleaseYear() + "'," +
                        " '" + bookForm.listPostBook.get(i).getDescription() + "'," +
                        " '" + bookForm.listPostBook.get(i).getAuthor() + "'," +
                        " '" + bookForm.listPostBook.get(i).getPrice() + "'," +
                        " '" + bookForm.listPostBook.get(i).getAmount() + "');\n";
                statement.executeUpdate(query);

                //Insert information about user post book to table "book_post"
                query = "INSERT IGNORE INTO `book`.`book_post` (`user_phone`, `book_id`) " +
                        "VALUES ('" + bookForm.getUserPhone() + "', '" + bookForm.listPostBook.get(i).getBookId() + "');";
                statement.executeUpdate(query);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Post book failed!";
        }
        return "Update success!";
    }

    //Method show post book
    public ArrayList<ResponseListBookPosted> showBookPosted(String userPhone, String orderBy ,String order){
        String query;
        ArrayList<ResponseListBookPosted>  listBookPosted = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            query = "SELECT `date_post`, `link_photo`, `book_title`, `price`, `release_year` FROM `book`.book_post\n" +
                    "LEFT JOIN `book`.`book_storage` ON book_storage.book_id = book_post.book_id\n" +
                    "WHERE book_post.user_phone = " + userPhone + " ORDER BY " + orderBy + " " + order +";";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                ResponseListBookPosted bookPosted = new ResponseListBookPosted();
                bookPosted.setTimePosted(resultSet.getTimestamp("date_post"));
                bookPosted.setBookTitle(resultSet.getString("book_title"));
                bookPosted.setPrice(resultSet.getLong("price"));
                bookPosted.setLinkPhoto(resultSet.getString("link_photo"));
                bookPosted.setReleaseYear(resultSet.getString("release_year"));
                listBookPosted.add(bookPosted);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        return listBookPosted;
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
