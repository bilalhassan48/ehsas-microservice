package com.zingpay.ehsas.feign;

import com.zingpay.ehsas.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

@FeignClient(value = "${feign.auth.name}", url = "${feign.auth.url:#{null}}",  configuration = FeignClientConfiguration.class)
public interface AuthServiceClient {
    @GetMapping("/fetch-email-against-token")
    String fetchEmail(@RequestHeader(name = "Authorization") String token);
    /*@GetMapping("/fetch-authorities-against-token")
    List<Authority> fetchAuthorities(@RequestHeader(name = "Authorization") String token);*/

    @GetMapping("/fetch-accountId-against-token")
    int fetchAccountId(@RequestHeader(name = "Authorization") String token);

    @PostMapping("/oauth/token")
    String login(@RequestParam("grant_type") String grantType);
}
