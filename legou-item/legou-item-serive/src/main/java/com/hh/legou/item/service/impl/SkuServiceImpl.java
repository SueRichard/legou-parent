package com.hh.legou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.core.service.impl.CrudServiceImpl;
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
}
