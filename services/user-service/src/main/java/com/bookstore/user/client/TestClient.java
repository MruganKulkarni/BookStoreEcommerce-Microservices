package com.bookstore.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "httpbin", url = "http://httpbin.org")
public interface TestClient {

    @GetMapping("/get")
    String testCall();
}