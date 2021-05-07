package com.example.book_web.entityForm;

import com.example.book_web.entity.Books;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter @Getter
public class Response {
    public int code;
    public String message;
    public ArrayList<Books> data;

    public Response(int code, String message, ArrayList<Books> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
