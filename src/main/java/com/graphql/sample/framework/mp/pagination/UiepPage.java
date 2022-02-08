package com.graphql.sample.framework.mp.pagination;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class UiepPage<T> extends Page<T> {
    private long totalAll;

    public void setTotalAll(long totalAll) {
        this.totalAll = totalAll;
    }

    public long getTotalAll() {
        return totalAll;
    }

    public UiepPage<T> setPageInfo(IPage<T> pageInfo) {
        setCurrent(pageInfo.getCurrent());
        setPages(pageInfo.getPages());
        setRecords(pageInfo.getRecords());
        setSize(pageInfo.getSize());
        setTotal(pageInfo.getTotal());
        if (totalAll <= 0L) {
            totalAll = getTotal();
        }
        return this;
    }
}
