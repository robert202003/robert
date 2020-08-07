package com.github.cloud.user.client;

import com.github.cloud.core.constant.FeignConstant;
import com.github.cloud.core.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = FeignConstant.SMALL_GOODS)
public interface GoodsFeign {

    @PostMapping("/api/test/token")
    R tree();
}
