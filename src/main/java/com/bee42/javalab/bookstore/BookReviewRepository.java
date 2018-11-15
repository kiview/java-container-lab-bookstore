package com.bee42.javalab.bookstore;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class BookReviewRepository {

    private static final String INDEX_REVIEWS = "reviews";
    private static final String TYPE_REVIEWS = "doc";
    private RestHighLevelClient client;

    BookReviewRepository(String httpHostAddress) {
        client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create(httpHostAddress))
        );
    }

    void close() throws IOException {
        client.close();
    }


    String save(BookReview review) throws IOException {

        IndexRequest request = new IndexRequest(INDEX_REVIEWS, TYPE_REVIEWS);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("bookName", review.getBookName());
        jsonMap.put("rating", review.getRating());
        jsonMap.put("review", review.getReview());

        request.source(jsonMap);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        return response.getId();
    }

    BookReview fetch(String id) throws IOException {

        GetRequest getRequest = new GetRequest(INDEX_REVIEWS, TYPE_REVIEWS, id);
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);

        Map<String, Object> fields = response.getSourceAsMap();

        return new BookReview(
                (String) fields.get("bookName"),
                (int) fields.get("rating"),
                (String) fields.get("review")
        );
    }
}
