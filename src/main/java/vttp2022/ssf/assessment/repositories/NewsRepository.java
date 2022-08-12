package vttp2022.ssf.assessment.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.ssf.assessment.models.Articles;

@Repository
public class NewsRepository {

    @Autowired 
    @Qualifier("redislab")
	private RedisTemplate<String, String> template;
    
    public void save (List<Articles> saveList) {

        for (int i = 0; i < saveList.size(); i++) {
            String id = saveList.get(i).getId();
            
            JsonObject jo = Json.createObjectBuilder()
            .add("id", saveList.get(i).getId())
            .add("published_on", saveList.get(i).getPublished_on())
            .add("title", saveList.get(i).getTitle())
            .add("url", saveList.get(i).getUrl())
            .add("imageurl", saveList.get(i).getImageurl())
            .add("body", saveList.get(i).getBody())
            .add("tags", saveList.get(i).getTags())
            .add("categories", saveList.get(i).getCategories())
            .build();

            String payloadJsonStr = jo.toString();

            ValueOperations<String, String> valueOp = template.opsForValue();
            valueOp.set(id, payloadJsonStr);

            // ListOperations<String, String> listOps = template.opsForList();
            // long l = listOps.size(id);
            // if (l > 0)
            //     listOps.trim(id, 0, l);
            // listOps.leftPushAll(id, 
            //         jsonStr
		    // );

        }

    }

    public String getFromRedis (String id) {

        // give the id (taken from the path variable in url) and retrive the payload from redis
        ValueOperations<String, String> valueOp = template.opsForValue();
        // get the payload and store it in a String
        String value = valueOp.get(id);

        return value;
        
    }

}
