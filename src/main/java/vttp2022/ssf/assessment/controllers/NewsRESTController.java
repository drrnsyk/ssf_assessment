package vttp2022.ssf.assessment.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.ssf.assessment.models.Articles;
import vttp2022.ssf.assessment.services.NewsService;

@RestController
@RequestMapping("/news")
public class NewsRESTController {

    @Autowired
    private NewsService newsSvc;
    
    @GetMapping(path="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getArticle (@PathVariable String id ) {

        // get the opt from boardgame services after passing in the id to retrive payload from repo
        Optional<Articles> opt = newsSvc.getArticleById(id);

        // payload format is in opt
        if (opt.isEmpty()) {
            JsonObject err = Json.createObjectBuilder()
                .add("error", "Id %s not found".formatted(id))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(err.toString());
        }

        Articles article = opt.get();

        return ResponseEntity.ok(article.readModelCreateJsonObj().toString());


    }
}
