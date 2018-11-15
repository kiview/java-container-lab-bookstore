package com.bee42.javalab.bookstore;

import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class BookInventoryRepositoryTest {


    @Test
    public void givenTwoDifferentBooks_fetchesInventoryCountFromInventoryService() throws IOException, URISyntaxException {
        Book mobyDick = new Book("Moby Dick", "Herman Melville");
        Book computerProgramming = new Book("The Art of Computer Programming", "Donald Knuth");

        try (MockServerContainer mockServer = new MockServerContainer()) {
            mockServer.start();

            MockServerClient mockServerClient = new MockServerClient(mockServer.getContainerIpAddress(), mockServer.getFirstMappedPort());
            mockServerClient
                    .when(request("/inventory")
                            .withQueryStringParameter("name", mobyDick.getName()))
                    .respond(response("42"));

            mockServerClient
                    .when(request("/inventory")
                            .withQueryStringParameter("name", computerProgramming.getName()))
                    .respond(response("4711"));


            BookInventoryRepository bookInventoryRepository = new BookInventoryRepository(mockServer.getEndpoint() + "/inventory");

            int mobyCount = bookInventoryRepository.fetchInventoryCount(mobyDick);
            assertThat(mobyCount, is(42));

            int computerCount = bookInventoryRepository.fetchInventoryCount(computerProgramming);
            assertThat(computerCount, is(4711));
        }

    }

}