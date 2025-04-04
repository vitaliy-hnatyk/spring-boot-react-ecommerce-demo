package com.ujjaval.ecommerce.searchsuggestionservice.controller;

import com.ujjaval.ecommerce.searchsuggestionservice.dto.InnerSearchSuggestionKeywordInfo;
import com.ujjaval.ecommerce.searchsuggestionservice.service.SearchSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SearchSuggestionController {

    @Autowired
    SearchSuggestionService searchSuggestionService;

    public void loadSearchSuggestions() {
        searchSuggestionService.loadSearchSuggestionToMap();
    }

    @GetMapping("/search-suggestion")
    public ResponseEntity<?> searchKeyword(@RequestParam String q) {
        return ResponseEntity.ok(searchSuggestionService.searchKeywordFromMap(q));
    }

    @GetMapping("/default-search-suggestion")
    public ResponseEntity<?> defaultSearchSuggestions() {
        List<InnerSearchSuggestionKeywordInfo> resultList
                = searchSuggestionService.getDefaultSearchSuggestions();
        return ResponseEntity.ok(resultList);
    }
}
