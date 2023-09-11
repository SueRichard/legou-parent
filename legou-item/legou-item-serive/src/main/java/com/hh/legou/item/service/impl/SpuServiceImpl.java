package com.hh.legou.item.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hh.legou.core.service.impl.CrudServiceImpl;
import com.hh.legou.item.po.Sku;
import com.hh.legou.item.po.Spu;
import com.hh.legou.item.service.ISkuService;
import com.hh.legou.item.service.ISpuDetailService;
import com.hh.legou.item.service.ISpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 15:41
 */
@Service
public class SpuServiceImpl extends CrudServiceImpl<Spu> implements ISpuService {

    @Autowired
    private ISpuDetailService spuDetailService;

    @Autowired
    private ISkuService skuService;

    /**
     * 保存spu
     * <p>1.spu</p>
     * <p>2.spuDetail</p>
     * <p>3.skus</p>
     *
     * @param spu
     */
    @Override
    public void saveSpu(Spu spu) {
        //1.保存spu -> spu持久化产生主键属性
        this.saveOrUpdate(spu);

        //2.spuDetail
        if (spu.getSpuDetail().getId() == null) {
            //新增
            spu.getSpuDetail().setId(spu.getId());
            spuDetailService.save(spu.getSpuDetail());
        } else {
            //修改
            spuDetailService.updateById(spu.getSpuDetail());
        }

//        3.skus
//        3.1. 先删除对应的sku
        skuService.remove(Wrappers.<Sku>query().eq("spu_id_", spu.getId()));
//        3.2. 循环添加sku
        for (Sku sku : spu.getSkus()) {
            sku.setSpuId(spu.getId());
            skuService.save(sku);
        }
    }
}
