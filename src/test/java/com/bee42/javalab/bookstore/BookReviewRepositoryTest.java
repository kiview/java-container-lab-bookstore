package com.bee42.javalab.bookstore;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BookReviewRepositoryTest {

    @Rule
    public ElasticsearchContainer elasticsearch = new ElasticsearchContainer();

    private BookReviewRepository repository;
    private RestHighLevelClient restClient;

    @Before
    public void setUp() {
        repository = new BookReviewRepository(elasticsearch.getHttpHostAddress());

        restClient = new RestHighLevelClient(
                RestClient.builder(HttpHost.create(elasticsearch.getHttpHostAddress()))
        );
    }

    @After
    public void tearDown() throws IOException {
        restClient.close();
        repository.close();
    }

    @Test
    public void afterSavingAReview_itCanBeFetched() throws IOException {
        BookReview review = new BookReview("Moby Dick", 42, "A great whale adventure!");

        String id = repository.save(review);
        BookReview fetchedReview = repository.fetch(id);

        assertThat(fetchedReview, is(review));
    }


}