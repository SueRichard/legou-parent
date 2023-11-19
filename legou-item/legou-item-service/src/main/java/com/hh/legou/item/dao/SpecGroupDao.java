package com.hh.legou.item.dao;

import com.hh.legou.core.dao.ICrudDao;
import com.hh.legou.item.po.SpecGroup;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 06/09/2023 11:00
 */
public interface SpecGroupDao extends ICrudDao<SpecGroup> {

    /**
     * 根据实体类的条件生成动态sql语句查询规格分组
     *
     * @param specGroup
     * @return
     */
    List<SpecGroup> selectList(SpecGroup specGroup);
}
