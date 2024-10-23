package org.mycompany.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transmition {
    
    public void processTransmition(HashMap<String, Long> executionList) throws IOException, URISyntaxException, InterruptedException {
        URI uri = new URI("http://assessment:8080/v1/result");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> jsonObjects = new ArrayList<>();

        for (Map.Entry<String, Long> entry : executionList.entrySet()) {
            String workLoadID = entry.getKey();
            Long timeDiference = entry.getValue();

            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("customerId", workLoadID);
            jsonObject.put("consumption", timeDiference);

            jsonObjects.add(jsonObject);
        }

    
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", jsonObjects);

        String jsonData = objectMapper.writeValueAsString(jsonObjects);
        jsonData = "{\"result\":" + jsonData + "}";
        
        System.out.println(jsonData);


        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                                .uri(uri)
                                                .header("Content-Type", "application/json")
                                                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                                                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response); 

    }
}
