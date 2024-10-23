package org.mycompany.app;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Reader {
    JSONObject jsonObject;

    public Reader() {
        
    }

    public void read() throws IOException, URISyntaxException, InterruptedException {
        URI uri = new URI("http://assessment:8080/v1/dataset");
        HttpRequest request = HttpRequest.newBuilder(uri).build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        jsonObject = new JSONObject(response.body());
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
