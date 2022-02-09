package com.ndourcodeur.userservice.feignClients;

import com.ndourcodeur.userservice.model.LapTop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "laptop-service", url = "http://localhost:8300", path = "/api/v1/lapTops")
public interface LapTopFeignClient {

    @GetMapping(path = "/byUserId/{userId}")
    public List<LapTop> fetchAllLapTopsByUserId(@PathVariable Long userId);
}
