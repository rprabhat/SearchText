package com.optus.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prabhatranjan on 27/01/2016.
 */

@JsonAutoDetect
public class SearchResult {

    Map<String, Integer> counts = new HashMap<>();

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public void setCounts(Map<String, Integer> counts) {
        this.counts = counts;
    }
}
