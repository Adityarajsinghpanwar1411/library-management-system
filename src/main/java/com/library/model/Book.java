package com.library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    // Constructors
    public Book() {}

    public Book(String title, String author, String genre, int totalCopies) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // Getters and setters (generate with IDE: Alt+Insert in IntelliJ)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    @Override
    public String toString() {
        return String.format("[%d] %s by %s | Genre: %s | Available: %d/%d",
                id, title, author, genre, availableCopies, totalCopies);
    }
}
