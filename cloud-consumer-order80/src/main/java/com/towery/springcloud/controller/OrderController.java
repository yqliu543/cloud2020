package com.towery.springcloud.controller;

import com.towery.springcloud.lb.LoadBalancer;
import com.towery.springcloud.entity.CommentResult;
import com.towery.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
//    public static final String PAYMENT_URL="http://localhost:8001";
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/payment/create")
    public CommentResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommentResult.class);
    }
    @GetMapping("/consumer/payment/get/{id}")
    public CommentResult<Payment> getPayment(@PathVariable("id")Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommentResult.class);
    }
    @GetMapping("/consumer/payment/get2/{id}")
    public CommentResult<Payment> getPayment2(@PathVariable("id")Long id){
        ResponseEntity<CommentResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommentResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }else {
            return new CommentResult<Payment>(444,"操作失败");
        }
    }
    @GetMapping("/consumer/payment/lb")
    public String getpaymentlb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null||instances.size()<=0) {
            return null;
        }else {
            ServiceInstance instances1 = loadBalancer.instances(instances);
            URI uri = instances1.getUri();
            return restTemplate.getForObject(uri+"/payment/lb",String.class);
        }
    }
}
