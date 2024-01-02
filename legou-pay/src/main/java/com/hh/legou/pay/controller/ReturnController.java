package com.hh.legou.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hh.legou.pay.config.AppUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class ReturnController {
    @RequestMapping("/getreturn")
    public void getreturn(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        System.out.println("同步通知");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        Iterator<String> iter = requestParams.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        /**
         * rsaCheckV1、rsaCheckV2 两种验签方法用于不同的接口的返回参数验签 ：
         * 1、rsaCheckV1：主要用于支付接口发送到notify_url地址的异步通知参的验签，比如：当面付、APP支付、手机网站支付、电脑网站支付等接口；
         * 2、rsaCheckV2：主要是用于生活号相关的事件消息等发送到应用网关地址的异步信息的验签。
         */
        //RSA2验证，支付接口验签请使用v1，使用v2校验会失败
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AppUtil.alipay_public_key, AppUtil.charset, AppUtil.sign_type); //调用SDK验证签名
//        boolean signVerified = AlipaySignature.rsaCheckV2(params, AppUtil.alipay_public_key, AppUtil.charset, AppUtil.sign_type); //调用SDK验证签名
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            //支付宝交易号
            String trade_no = request.getParameter("trade_no");
            //付款金额
            String total_amount = request.getParameter("total_amount");
            out.println("trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);
        } else {
            out.println("验签失败");
        }
    }
}
