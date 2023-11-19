package com.hh.legou.item.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.item.po.Spu;

/**
 * @author hh
 * @version 1.0
 * @time 10/09/2023 15:39
 */
public interface ISpuService extends ICrudService<Spu> {
    /**
     * 保存spu
     * <p>1.spu</p>
     * <p>2.spuDetail</p>
     * <p>3.skus</p>
     *
     * @param spu
     */
    void saveSpu(Spu spu);
}
