package org.robert.user.api.feign;

import org.robert.core.base.R;
import org.robert.core.constant.FeignConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = FeignConstant.SMALL_GOODS)
public interface GoodsFeignClient {

    @PostMapping("/api/test/token")
    R tree();
}
