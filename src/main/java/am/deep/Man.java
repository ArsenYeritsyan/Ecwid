package am.deep;

import java.util.List;

class Man {
    private String name;
    private int age;
    private List<Book> favoriteBooks;

    public Man(String name, int age, List<Book> favoriteBooks) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
    }

    public Man() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getFavoriteBooks() {
        return favoriteBooks;
    }
}
