package com.hh.legou.search.controller;

import com.hh.legou.search.po.SearchRequest;
import com.hh.legou.search.po.SearchResult;
import com.hh.legou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hh
 * @version 1.0
 * @time 24/09/2023 16:56
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/query")
    public ResponseEntity<SearchResult> queryGoodsByPage(@RequestBody SearchRequest searchRequest) {
        SearchResult result = searchService.search(searchRequest);
        if (null == result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(result);
    }
}
