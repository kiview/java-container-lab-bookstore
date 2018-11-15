package com.bee42.javalab.bookstore;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

class BookInventoryRepository {

    private String baseUrl;

    BookInventoryRepository(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    int fetchInventoryCount(Book b) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(baseUrl)
                .setParameter("name", b.getName());

        Response response = Request.Get(uriBuilder.build()).execute();

        String body = response.returnContent().asString();
        return Integer.parseInt(body);
    }
}
