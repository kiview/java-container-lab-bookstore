package com.bee42.javalab.bookstore;

import java.util.Objects;

class BookReview {

    private String bookName;
    private int rating;
    private String review;

    BookReview(String bookName, int rating, String review) {
        this.bookName = bookName;
        this.rating = rating;
        this.review = review;
    }

    public String getBookName() {
        return bookName;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookReview that = (BookReview) o;
        return rating == that.rating &&
                Objects.equals(bookName, that.bookName) &&
                Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookName, rating, review);
    }

    @Override
    public String toString() {
        return "BookReview{" +
                "bookName='" + bookName + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
