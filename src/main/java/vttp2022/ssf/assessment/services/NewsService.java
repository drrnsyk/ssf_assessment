package vttp2022.ssf.assessment.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.assessment.models.Articles;

@Service
public class NewsService {
    
    // API call from crypto compare
    public static final String URL = "https://min-api.cryptocompare.com/data/v2/news/";

    // inject in the key
    @Value("${API_KEY}")
    private String key;

    public List<Articles> getArticles(String language) {

        String payloadStr;

        System.out.println("Getting news Articles from CryptoCompare.com");

        try {
            String url = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("fsym", URLEncoder.encode(language, "UTF-8"))
                            .queryParam("appid", key)
                            .toUriString();

            // create a request entity
            // create the GET request, GET url
            RequestEntity<Void> req = RequestEntity.get(url).build();
            // to make the call to cryptocompare
            // need to create restTemplate
            RestTemplate template = new RestTemplate();
            // make the call
            ResponseEntity<String> resp;
            resp = template.exchange(req, String.class);
            // get the payload and do something with it
            payloadStr = resp.getBody();
            // prints out the payload 
            // System.out.println("payload from API: " + payloadStr);
            // prints out the query string
            // System.out.println("query string: " + language);

        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return Collections.emptyList();
        }

        // convert payload string (json string) to json object
        // create a StringReader to read the payload string
        Reader strReader = new StringReader(payloadStr);
        // create a JsonReader to read the StringReader
        JsonReader jsonReader = Json.createReader(strReader);
        // read the payload as a json object (entire API result)
        JsonObject payloadJsonObject = jsonReader.readObject();
        // get the array from the json object (entired array from the data portion)
        JsonArray data = payloadJsonObject.getJsonArray("Data");

        // print out data size to check content
        System.out.println("This is the size of the data json array:" + data.size());
        // System.out.println(data.get(0));

        // instantiate a list to store the data
        List<Articles> list = new LinkedList<>();

        for (int i = 0; i < data.size(); i++) {
            JsonObject dataJsonObject = data.getJsonObject(i);
            list.add(Articles.create(dataJsonObject));
        }

        System.out.println("This is the size of list of articles: " + list.size());

        return list;
        
    }

}
