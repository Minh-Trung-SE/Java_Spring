import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class book_favourite {
    long user_id;
    int book_id;
    Timestamp date_add;

    public void showFavouriteBook(String user_phone, Connection connection) throws SQLException {
        String query;
        Statement statement = connection.createStatement();
        ArrayList<String> book_id = new ArrayList<String>();
        query = "SELECT book.book_favourite.book_id FROM book.book_favourite WHERE user_phone = " + user_phone + ";";

        ResultSet result = statement.executeQuery(query);
        while (result.next()){
            book_id.add(result.getString("book_id"));
        }
        System.out.println("Ok");
        for (String s: book_id){
            System.out.println("BookID: " + s);
            query = "SELECT book.book_storage.link_photo, book.book_storage.book_title, book.book_post.user_phone, book.book_storage.release_year, book.book_storage.price " +
                    "FROM book.book_favourite " +
                    "JOIN book.book_post ON book.book_post.book_id = book.book_favourite.book_id " +
                    "JOIN book.book_storage ON book.book_storage.book_id = book.book_favourite.book_id "+
                    "WHERE book.book_favourite.book_id = '" + s + "';";

            result = statement.executeQuery(query);

            while (result.next()){
                System.out.println("    Link photo: " + result.getString("link_photo"));
                System.out.println("    Book title: " +  result.getString("book_title"));
                System.out.println("    Phone contact: " + result.getString("user_phone"));
                System.out.println("    Year release: " + result.getString("release_year"));
                System.out.println("    Price:  " + result.getString("price"));
            }
        }

        book_favourite book_favourite = new book_favourite();
        book_favourite.dislike(user_phone, connection, book_id);


    }

    public void dislike(String user_phone, Connection connection, ArrayList<String> id_book) throws SQLException {
        String query = null, book_id;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID you want dislike: ");
        book_id = scanner.nextLine();
        book_id = book_id.toUpperCase();

        for(String s: id_book){
            if (book_id.equals(s)) {
                query = "DELETE FROM `book`.`book_favourite` WHERE (`user_phone` = '"+ user_phone + "') and (`book_id` = '" + book_id + "');";
                break;
            }
        }
        if(query != null){
            Statement statement = connection.createStatement();
            System.out.println(query);
            statement.executeUpdate(query);
            System.out.println("Dislike success!");
        }else {
            System.out.println("Dislike failed!");
        }
    }

}
