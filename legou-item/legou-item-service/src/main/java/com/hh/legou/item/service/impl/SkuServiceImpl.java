package com.hh.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.dao.SkuDao;
import com.hh.legou.item.po.Sku;
import com.hh.legou.item.service.ISkuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 15:42
 */
@Service
public class SkuServiceImpl extends CrudServiceImpl<Sku> implements ISkuService {

    /**
     * 搜索微服务工程要用
     *
     * @param entity
     * @return
     */
    @Override
    public List<Sku> list(Sku entity) {
        QueryWrapper<Sku> queryWrapper = Wrappers.<Sku>query();
        if (entity.getSpuId() != null) {
            queryWrapper.eq("spu_id_", entity.getSpuId());
        }
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public void decrCount(Integer num, Long skuId) {
        /**
         * 如果使用如下java代码实现，会出现超卖问题
         * 解决方案：
         * 1.数据库单行修改有行级锁不会出现这个问题
         * 2.使用分布式锁，这里普通锁只能控制当前节点，分布式当中不行
         */
        /*Sku sku = getBaseMapper().selectById(skuId);
        if (sku.getStock() >= num) {
            sku.setStock(sku.getStock() - num);
            getBaseMapper().updateById(sku);
        }*/

        //数据实现
        ((SkuDao) getBaseMapper()).decrCount(num, skuId);
    }
}
