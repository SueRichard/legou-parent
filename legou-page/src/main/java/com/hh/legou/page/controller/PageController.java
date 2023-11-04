package com.hh.legou.page.controller;

import com.hh.legou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hh
 * @version 1.0
 * @time 04/11/2023 10:05
 */
@RestController
@RequestMapping("/page")
@CrossOrigin
public class PageController {

    @Autowired
    private PageService pageService;

    @RequestMapping("/createHtml/{id}")
    public ResponseEntity<String> createHtml(@PathVariable(name = "id") Long id) {
        pageService.createPageHtml(id);
        return ResponseEntity.ok("生成成功");
    }
}
