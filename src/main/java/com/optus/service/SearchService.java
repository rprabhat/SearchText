package com.optus.service;



import com.optus.model.SearchRequest;
import com.optus.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
// @RequestMapping("/counter-api")
public class SearchService {

    @Autowired
    private WordCount wordCount;

    @RequestMapping(value = "/top/{x}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/csv"})
    @ResponseBody
    public String getTopX(@PathVariable int x) {

        SearchResult result = wordCount.topX(x);
        StringBuilder sb = new StringBuilder();


        for(Map.Entry<String, Integer> entry : result.getCounts().entrySet()){
            sb.append(entry.getKey()).append("|").append(entry.getValue());
            sb.append("\n");
        }

        return sb.toString();
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public SearchResult search(@RequestBody SearchRequest request) {

        return wordCount.searchWords(request.getSearchText());
    }
}
