package com.hh.legou.page.service.impl;

import com.hh.legou.common.utils.JsonUtils;
import com.hh.legou.item.po.Category;
import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.Spu;
import com.hh.legou.item.po.SpuDetail;
import com.hh.legou.page.client.CategoryClient;
import com.hh.legou.page.client.SkuClient;
import com.hh.legou.page.client.SpuClient;
import com.hh.legou.page.client.SpuDetailClient;
import com.hh.legou.page.service.PageService;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hh
 * @version 1.0
 * @time 28/10/2023 15:24
 */
@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private SpuDetailClient spuDetailClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SkuClient skuClient;

    @Value("${pagePath}")
    private String pagePath;

    /**
     * thymeleaf模板引擎
     */
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 构建数据模型
     *
     * @return
     */
    private Map<String, Object> buildDataModel(Long spuId) {
        //这里的键值对的键和前端保持一致，前端取值
        Map<String, Object> dataMap = new HashMap<>();
        //获取spu
        Spu spu = spuClient.edit(spuId);
        Category c1 = categoryClient.edit(spu.getCid1());
        //获取分类信息
        dataMap.put("category1", categoryClient.edit(spu.getCid1()));
        dataMap.put("category2", categoryClient.edit(spu.getCid2()));
        dataMap.put("category3", categoryClient.edit(spu.getCid3()));
        //获取sku集合
        List<Sku> skus = skuClient.selectSkusBySpuId(spu.getId());
        //获取图片信息
        List<String> images = new ArrayList<>();
        for (Sku sku : skus) {
            images.add(sku.getImages());
        }
        dataMap.put("imageList", images);
        //spuDetail
        SpuDetail spuDetail = spuDetailClient.edit(spu.getId());
        //specialSpec
        Map<String, Object> specialSpecMap = JsonUtils.parseMap(spuDetail.getSpecialSpec(), String.class, Object.class);
        dataMap.put("specificationList", specialSpecMap);
        dataMap.put("spu", spu);
        dataMap.put("spuDetail", spuDetail);
        dataMap.put("skuList", skus);
        return dataMap;
    }

    /**
     * 手动渲染
     *
     * @param id
     */
    @Override
    public void createPageHtml(Long id) {
        //模版 + contextMap  = html
        Context context = new Context();
        Map<String, Object> dataModel = buildDataModel(id);
        context.setVariables(dataModel);//springBoot 自动渲染 组织数据model.addAttribute("key","value"); 使用数据${key}

        File dir = new File(pagePath);
        //目录不存在创建
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //写入目标文件路径和文件名，文件名： 商品id.html
        File dest = new File(dir, id + ".html");
        try {
            PrintWriter writer = new PrintWriter(dest, CharEncoding.UTF_8);
            //第一个参数为模版文件名称，第二个为渲染的数据（上下文），第三个参数为生成的静态页放置位置，即writer
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
