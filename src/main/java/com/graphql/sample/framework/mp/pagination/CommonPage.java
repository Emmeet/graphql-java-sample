package com.graphql.sample.framework.mp.pagination;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class CommonPage<T> {

    private List<T> items;

    private T item;

    private long total;

    private long currentPage;

    private long pageSize;

    private long totalPages;

    /**
     * 将MybatisPlus标准的分页信息转换为前台表格组件能识别的分页信息
     *
     * @param pageInfo MybatisPlus标准的分页信息
     * @return 分页信息对象
     */
    public static <T> CommonPage<T> convertFromMpPage(IPage<T> pageInfo) {
        CommonPage<T> xyPageInfo = new CommonPage<>();
        xyPageInfo.setPageInfo(pageInfo);
        return xyPageInfo;
    }

    /**
     * 设置分页信息
     *
     * @param pageInfo 分页信息
     * @return 分页信息对象
     */
    public CommonPage<T> setPageInfo(IPage<T> pageInfo) {
        setCurrentPage(pageInfo.getCurrent());
        setTotalPages(pageInfo.getPages());
        setItems(pageInfo.getRecords());
        setPageSize(pageInfo.getSize());
        setTotal(pageInfo.getTotal());
        return this;
    }
}
