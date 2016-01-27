package com.optus.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import java.util.List;

/**
 * Created by prabhatranjan on 27/01/2016.
 */
@JsonAutoDetect
public class SearchRequest {

    List<String> searchText;

    public List<String> getSearchText() {
        return searchText;
    }

    public void setSearchText(List<String> searchText) {
        this.searchText = searchText;
    }
}
