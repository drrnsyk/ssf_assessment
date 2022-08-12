package vttp2022.ssf.assessment.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.ssf.assessment.models.Articles;
import vttp2022.ssf.assessment.services.NewsService;

@Controller
@RequestMapping("/")
public class NewsController {

    @Autowired
    private NewsService newsSvc;
    
    @GetMapping
    public String getArticles (Model model) {

        // if (language.isEmpty()) {
        //     language = "EN";
        // }
    
        // converts all input to uppercase, search and match in uppercase to prevent case mismatch
        //language = language.toUpperCase();
        // System.out.println(language);

        // calls service to perform the API call and data extraction
        List<Articles> listOfArticles = new LinkedList<>();
        listOfArticles = newsSvc.getArticles();

        // data injection into thymeleaf html
        // model.addAttribute("language", language);
        model.addAttribute("listOfArticles", listOfArticles);

        // boolean flag = false;
        // model.addAttribute("flag", flag);
        
        return "news";

    }

    @PostMapping("/articles")
    public String saveArticles (@RequestBody MultiValueMap<String, String> form, Model model) {

        Articles articles = new Articles();
        articles.setId(form.getFirst("id"));
        articles.setPublished_on(form.getFirst("published"));
        articles.setTitle(form.getFirst("title"));
        articles.setUrl(form.getFirst("url"));
        articles.setImageurl(form.getFirst("imageurl"));
        articles.setBody(form.getFirst("body"));
        articles.setTags(form.getFirst("tags"));
        articles.setCategories(form.getFirst("categories"));

        List<Articles> saveList = new LinkedList<>();
        saveList.add(articles);

        newsSvc.saveArticles(saveList);

        // System.out.println(form.getFirst("flag"));

        // String id = form.getFirst("id");
        // String published_on = form.getFirst("published");
        // String title = form.getFirst("title");
        // String url = form.getFirst("url");
        // String imageurl = form.getFirst("imageurl");
        // String body = form.getFirst("body");
        // String tags = form.getFirst("tags");
        // String categories = form.getFirst("categories");

        // JsonObject jo = Json.createObjectBuilder()
        //     .add("id", id)
        //     .add("published_on", published_on)
        //     .add("title", title)
        //     .add("url", url)
        //     .add("imageurl", imageurl)
        //     .add("body", body)
        //     .add("tags", tags)
        //     .add("categories", categories)
        //     .build();

        // String jsonStr = jo.toString();

        return "redirect:/";
    }
    
}
