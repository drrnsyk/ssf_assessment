package vttp2022.ssf.assessment.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.ssf.assessment.models.Articles;
import vttp2022.ssf.assessment.services.NewsService;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsSvc;
    
    @GetMapping
    public String getPrice (@RequestParam String language, 
                            Model model) {

        if (language.isEmpty())
            language = "EN";
    
        // converts all input to uppercase, search and match in uppercase to prevent case mismatch
        language = language.toUpperCase();
        // System.out.println(language);

        // calls service to perform the API call and data extraction
        // Crypto crypto = cryptoSvc.getCrypto(symbol, currency);
        List<Articles> listOfArticles = new LinkedList<>();
        listOfArticles = newsSvc.getArticles(language);
        
        // System.out.println(crypto.getCryptoSymbol());
        // System.out.println(crypto.getCryptoPrice().getCurrencyName());
        // System.out.println(crypto.getCryptoPrice().getPrice());

        // data injection into thymeleaf html
        model.addAttribute("language", language);
        model.addAttribute("listOfArticles", listOfArticles);
        
        return "news";

    }
    
}
