package vttp2022.ssf.assessment.models;

import jakarta.json.JsonObject;

public class Articles {

    private String id;
    private String published_on;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private String categories;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPublished_on() {
        return published_on;
    }
    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    // construct the model from json object
    public static Articles create(JsonObject jo) {
        Articles articles = new Articles();
        articles.setId(jo.getString("id"));
        articles.setPublished_on(jo.get("published_on").toString());
        articles.setTitle(jo.getString("title"));
        articles.setUrl(jo.getString("url"));
        articles.setImageurl(jo.getString("imageurl"));
        articles.setBody(jo.getString("body"));
        articles.setTags(jo.getString("tags"));
        articles.setCategories(jo.getString("categories"));
        return articles;
    }
    
    
}
