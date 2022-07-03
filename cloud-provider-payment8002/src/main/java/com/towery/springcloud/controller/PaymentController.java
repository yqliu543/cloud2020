package com.towery.springcloud.controller;

import com.towery.springcloud.entity.CommentResult;
import com.towery.springcloud.entity.Payment;
import com.towery.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @PostMapping(value = "/payment/create")
    public CommentResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if (result>0){
            return new CommentResult(200,"插入数据库成功,serverPort:"+serverPort,result);
        }else {
            return new CommentResult(404,"插入数据库失败,serverPort:"+serverPort,null);
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommentResult get(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        if (result!=null){
            return new CommentResult(200,"查询成功,serverPort:"+serverPort,result);
        }else {
            return new CommentResult(404,"查询失败,serverPort:"+serverPort,null);
        }
    }
    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
