package com.optus.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.optus.model.SearchRequest;
import sun.misc.BASE64Encoder;

import java.util.ArrayList;
import java.util.List;

public class Base64Test {
    public Base64Test() {
    }

    public static void main(String[] args) throws JsonProcessingException {
        System.err.println(new BASE64Encoder().encode("admin:password".getBytes()));

        SearchRequest sr = new SearchRequest();
        List<String> search = new ArrayList<String>();
        search.add("abc");
        sr.setSearchText(search);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(sr);

        System.out.println(json);

    }
}
