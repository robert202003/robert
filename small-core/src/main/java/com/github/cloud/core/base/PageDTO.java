package com.github.cloud.core.base;

import lombok.Data;

@Data
public class PageDTO<T> {

    private Long total = 0L;
    private int pageSize = 10;
    private int currentPage = 1;

    public PageDTO(Long total, int pageSize, int currentPage) {
        this.total = total;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
