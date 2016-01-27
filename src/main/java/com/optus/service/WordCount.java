package com.optus.service;

import com.optus.model.SearchResult;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by prabhat on 27/01/2016.
 */
public class WordCount {

    Map<String, Integer> wordFrequency;
    List<Map.Entry<String, Integer>> sortedEntryList;
    String fileName;

    public WordCount(String fileName){
        this.fileName = fileName;
        wordFrequency = new HashMap<>();
    }

    public void readWords() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();


            while (line != null) {
                String[] words = line.split("\\s");

                for(String word : words){
                  if(wordFrequency.containsKey(word))
                      wordFrequency.put(word, wordFrequency.get(word) + 1);
                    else
                      wordFrequency.put(word,1);
                }
                line = br.readLine();
            }
        } finally {
            br.close();
        }

        // Sorted entry list
        sortedEntryList =  new LinkedList<>( wordFrequency.entrySet() );
        Collections.sort( sortedEntryList, new Comparator<Map.Entry<String, Integer>>()
        {
            @Override
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );

    }

    public SearchResult searchWords(List<String> words){
        SearchResult result = new SearchResult();
        Map<String, Integer> wordCount = new HashMap<>();

        for(String word : words){
            if(wordFrequency.containsKey(word))
                wordCount.put(word,wordFrequency.get(word));
        }

        result.setCounts(wordCount);
        return result;
    }

    public SearchResult topX(Integer n){

        SearchResult result = new SearchResult();
        Map<String, Integer> wordCount = new HashMap<>();

        for(int i=0; i <= n; i++){
            Map.Entry<String, Integer> entry = sortedEntryList.get(i);
            wordCount.put(entry.getKey(), entry.getValue());
        }

        result.setCounts(wordCount);
        return result;

    }
}
