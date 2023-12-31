package com.hh.legou.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hh.legou.core.po.BaseEntity;

import java.util.List;

public interface ICrudService<T extends BaseEntity> extends IService<T> {

    public PageInfo<T> listPage(T entity, int pageNum, int pageSize);

    public List<T> list(T entity);

}
