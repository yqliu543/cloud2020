package com.towery.springcloud.controller;

import com.towery.springcloud.entity.CommentResult;
import com.towery.springcloud.entity.Payment;
import com.towery.springcloud.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderFeignController {
    @Autowired
    private PaymentFeignService paymentFeignService;
    @GetMapping("/consumer/payment/get/{id}")
    public CommentResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.get(id);
    }
}
