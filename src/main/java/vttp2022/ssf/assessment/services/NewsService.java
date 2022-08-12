package vttp2022.ssf.assessment.services;

import java.io.Reader;
import java.io.StringReader;
// import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import vttp2022.ssf.assessment.repositories.NewsRepository;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepo;
    
    // API call from crypto compare
    public static final String URL = "https://min-api.cryptocompare.com/data/v2/news/";

    // inject in the key
    @Value("${API_KEY}")
    private String key;

    public List<Articles> getArticles() {

        String payloadStr;

        // System.out.println("Getting news Articles from CryptoCompare.com");

        try {
            String url = UriComponentsBuilder.fromUriString(URL)
                            //.queryParam("lang", URLEncoder.encode(language, "UTF-8"))
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
        // System.out.println("This is the size of the data json array:" + data.size());
        // System.out.println(data.get(0));

        // instantiate a list to store the data
        List<Articles> list = new LinkedList<>();
        // Articles articles = new Articles();

        for (int i = 0; i < data.size(); i++) {
            Articles articles = new Articles();
            JsonObject jo = data.getJsonObject(i);
            articles.setId(jo.getString("id"));
            articles.setPublished_on(jo.get("published_on").toString());
            articles.setTitle(jo.getString("title"));
            articles.setUrl(jo.getString("url"));
            articles.setImageurl(jo.getString("imageurl"));
            articles.setBody(jo.getString("body"));
            articles.setTags(jo.getString("tags"));
            articles.setCategories(jo.getString("categories"));
            list.add(articles);
            // list.add(Articles.create(dataJsonObject));
        }

        // System.out.println("This is the size of list of articles: " + list.size());

        return list;
        
    }


    public void saveArticles(List<Articles> list) {

        newsRepo.save(list);

    }


    public Optional<Articles> getArticleById(String id) {

        Articles article = new Articles();
        
        String payloadStr = newsRepo.getFromRedis(id);
        if (payloadStr == null) {
            return Optional.empty();
        }
        else
        {

            // JsonObject jo = article.readStrCreateJsonObject(payloadStr);
            StringReader strReader = new StringReader(payloadStr);
            JsonReader jsonReader = Json.createReader(strReader);
            JsonObject payloadJsonObject = jsonReader.readObject();

            // return Optional.of(article.readJsonObjCreateBoardgame(jo));
            article.setId(payloadJsonObject.getString("id"));
            article.setPublished_on(payloadJsonObject.get("published_on").toString());
            article.setTitle(payloadJsonObject.getString("title"));
            article.setUrl(payloadJsonObject.getString("url"));
            article.setImageurl(payloadJsonObject.getString("imageurl"));
            article.setBody(payloadJsonObject.getString("body"));
            article.setTags(payloadJsonObject.getString("tags"));
            article.setCategories(payloadJsonObject.getString("categories"));
            return Optional.of(article);
        }

    }

}
