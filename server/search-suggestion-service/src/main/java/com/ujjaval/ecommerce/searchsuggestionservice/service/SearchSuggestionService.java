package com.ujjaval.ecommerce.searchsuggestionservice.service;

import com.ujjaval.ecommerce.searchsuggestionservice.dto.InnerSearchSuggestionKeywordInfo;
import java.util.List;

public interface SearchSuggestionService {
    void loadSearchSuggestionToMap();

    List<InnerSearchSuggestionKeywordInfo> searchKeywordFromMap(String q);

    List<InnerSearchSuggestionKeywordInfo> getDefaultSearchSuggestions();
}

