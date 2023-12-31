package com.hh.legou.item.dao;

import com.hh.legou.core.dao.ICrudDao;
import com.hh.legou.item.po.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 14:45
 */
public interface SkuDao extends ICrudDao<Sku> {
    @Select("select * from legou.sku_ where spu_id_ = #{spuId}")
    List<Sku> findBySpuId(Integer spuId);

    /**
     * 减库存判断，能减再减
     * @param num
     * @param skuId
     * @return
     */
    @Update(value = "update sku_ set stock_ = stock_ - #{num} where id_ = #{skuId} and stock_ >= #{num}")
    int decrCount(@Param("num") Integer num, @Param("skuId") Long skuId);
}
