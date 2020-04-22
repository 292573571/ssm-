package com.zw.admin.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zw.admin.server.page.table.PageTableRequest;
import com.zw.admin.server.page.table.PageTableHandler;
import com.zw.admin.server.page.table.PageTableResponse;
import com.zw.admin.server.page.table.PageTableHandler.CountHandler;
import com.zw.admin.server.page.table.PageTableHandler.ListHandler;
import com.zw.admin.server.dao.NewsDao;
import com.zw.admin.server.model.News;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/newss")
public class NewsController {

    @Autowired
    private NewsDao newsDao;

    @PostMapping
    @ApiOperation(value = "保存")
    public News save(@RequestBody News news) {
        newsDao.save(news);

        return news;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public News get(@PathVariable Long id) {
        return newsDao.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "修改")
    public News update(@RequestBody News news) {
        newsDao.update(news);
        return news;
    }

    @GetMapping
    @ApiOperation(value = "列表")
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return newsDao.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<News> list(PageTableRequest request) {
                return newsDao.list(request.getParams(), request.getOffset(), request.getLimit());
            }
        }).handle(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable Long id) {
        newsDao.delete(id);
    }
}
