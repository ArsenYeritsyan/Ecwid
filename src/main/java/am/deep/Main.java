package am.deep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        books.add(new Book("writer1", "book1"));
        books.add(new Book("writer2", "book2"));
        books.add(new Book("writer3", "book3"));
        books.add(new Book("writer4", "book4"));

Man man1= new Man("Man1",45, books);

        Man man2 = DeepCopyUtils.deepCopy(man1);
        man1.setName("Another");
        man1.getFavoriteBooks().get(0).setTitle("AnotherTitle");

System.out.println("Original Name: " + man1.getName() + " book: " + man1.getFavoriteBooks().toString());
System.out.println("Copy Name: " + man2.getName() + " book: " + man2.getFavoriteBooks().toString());
    }


}