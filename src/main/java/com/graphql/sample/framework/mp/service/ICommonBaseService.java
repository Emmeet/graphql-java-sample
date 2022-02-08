package com.graphql.sample.framework.mp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.graphql.sample.framework.entity.JsonParamDto;
import com.graphql.sample.framework.entity.JsonResult;
import com.graphql.sample.framework.mp.pagination.CommonPage;
import org.springframework.validation.FieldError;

import java.util.List;


public interface ICommonBaseService<T> extends IService<T> {


    /**
     * 自定义查询功能。
     *
     * @param queryWrapper 查询条件
     * @param tableParam   ui组件的过滤、排序功能、分页信息
     * @return 查询结果数据
     */
    CommonPage<T> table(QueryWrapper<T> queryWrapper, JsonParamDto tableParam);

    /**
     * Obtain prompt information according to i18n key
     *
     * @param i18nKey
     * @return
     */
    String getMessageByI18nKey(String i18nKey);

    /**
     * Filed Error Message
     *
     * @param errors
     * @return
     */
    String filedErrorMessage(List<FieldError> errors);

    /**
     * Operation Success
     *
     * @param i18nKey
     * @return
     */
    JsonResult operationSuccess(String i18nKey);

    /**
     * Operation Fail
     *
     * @param i18nKey
     * @return
     */
    JsonResult operationFail(String i18nKey);

    /**
     * @param column
     * @param direction    DESC or ASC
     * @param queryWrapper
     */
    void createSort(String column, String direction, QueryWrapper queryWrapper);
}