package com.hh.legou.admin.controller;

import com.github.pagehelper.PageInfo;
import com.hh.legou.admin.po.Post;
import com.hh.legou.admin.service.IPostService;
import com.hh.legou.core.controller.BaseController;
import com.hh.legou.core.json.JSON;
import io.swagger.annotations.ApiOperation;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title:
 * @Description: 
 *
 * @Copyright 2019 hh - Powered By 雪松
 * @Author: hh
 * @Date:  2019/10/3 
 * @Version V1.0
 */
@RestController
@RequestMapping("/post")
public class PostController extends BaseController<IPostService, Post> {

    /**
     * 演示使用JSON注解过滤属性
     * @return
     */
    @ApiOperation(value="查询", notes="查询所有")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @JSON(type = Post.class ,filter = "desc")
    public List<Post> list(Post post) {
        List<Post> list = service.list(post);
        return list;
    }

    /**
     * 分页查询
     * @param entity
     * @param page
     * @param rows
     * @return
     */
    @ApiOperation(value="分页查询", notes="分页查询")
    @PostMapping("/list-page")
//    @PreAuthorize("hasRole('ADMIN')")
    public PageInfo<Post> listPage(Post entity,
                                   @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                   @RequestParam(name = "rows", defaultValue = "10", required = false) int rows) {
        PageInfo<Post> result = service.listPage(entity, page, rows);
        return result;
    }



}
