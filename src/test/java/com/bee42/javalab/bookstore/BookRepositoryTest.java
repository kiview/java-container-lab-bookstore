package com.bee42.javalab.bookstore;

import org.junit.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BookRepositoryTest {

    private BookRepository bookRepository;

    @Rule
    public PostgreSQLContainer postgres = new PostgreSQLContainer<>();

    @Before
    public void setUp() {
        bookRepository = new BookRepository(postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword());
        bookRepository.init();
    }

    @After
    public void tearDown() {
        bookRepository.close();
    }

    @Test
    public void emptyRepository_isEmpty() {
        assertThat(bookRepository.count(), is(0L));
    }

    @Test
    public void containsOneBook_afterSavingIt() {
        Book b = new Book("Moby Dick", "Herman Melville");
        bookRepository.save(b);

        assertThat(bookRepository.count(), is(1L));
    }

    @Test
    public void afterSavingMultipleBooks_andSearchingForAuthor_findsBooksOfGivenAuthor() {
        Book mobyDick = new Book("Moby Dick", "Herman Melville");
        String terryPratchett = "Terry Pratchett";
        Book magic = new Book("The Colour of Magic", terryPratchett);
        Book elephant = new Book("The Fifth Elephant", terryPratchett);

        Stream.of(mobyDick, magic, elephant)
                .forEach(b -> bookRepository.save(b));

        List<Book> foundBooks = bookRepository.findAllByAuthor(terryPratchett);

        assertThat(foundBooks.size(), is(2));
        assertThat(foundBooks, hasItems(magic, elephant));
    }


}