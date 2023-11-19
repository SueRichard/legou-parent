package com.hh.legou.item.dao;

import com.hh.legou.core.dao.ICrudDao;
import com.hh.legou.item.po.SpecParam;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 06/09/2023 11:04
 */
public interface SpecParamDao extends ICrudDao<SpecParam> {

    @Select("select * from legou.spec_param_ where group_id_ = #{groupId}")
    List<SpecParam> findByGroupId(Integer groupId);
}
