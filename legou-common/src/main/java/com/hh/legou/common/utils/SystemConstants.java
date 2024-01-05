package com.hh.legou.common.utils;

/**
 * @author hh
 * @version 1.0
 * @time 05/01/2024 10:20
 */
public class SystemConstants {

    /**
     * 秒杀商品存储到redis的前缀
     */
    public static final String SEC_KILL_GOODS_PREFIX = "SecKillGoods_";

    /**
     * 存储域订单的hash的大key
     */
    public static final String SEC_KILL_ORDER_KEY = "SecKillOrder";

    /**
     * 用户排队的队列key
     */
    public static final String SEC_KILL_USER_QUEUE_KEY = "SecKillOrderQueue";

    /**
     * 用户排队的标识key
     * 用于存储 谁 买了什么商品 以及抢单的状态
     */
    public static final String SEC_KILL_USER_STATUS_KEY = "UserQueueStatus";

    /**
     * 用于防止重复排队的hash的key
     */
    public static final String SEC_KILL_QUEUE_REPEAT_KEY = "UserQueueCount";

    /**
     * 防止超卖问题的队列key
     */
    public static final String SEC_KILL_OVERSELL_LIST_KEY_PREFIX = "SecKillGoodsCountList_";

    /**
     * 所有商品计数的大key
     * 用于存储所有的商品对应的库存数据
     * bigkey  field(商品id 1) value(库存数2)
     *           field(商品id 2) value(库存数5)
     */
    public static final String SEC_KILL_GOODS_COUNT_KEY = "SecKillGoodsCount";

}
