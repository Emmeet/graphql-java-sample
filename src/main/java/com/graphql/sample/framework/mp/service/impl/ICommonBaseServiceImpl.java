package com.graphql.sample.framework.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.graphql.sample.framework.entity.JsonParamDto;
import com.graphql.sample.framework.entity.JsonResult;
import com.graphql.sample.framework.mp.pagination.CommonPage;
import com.graphql.sample.framework.mp.pagination.UiepPage;
import com.graphql.sample.framework.mp.service.ICommonBaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义Mybatis Plus的ServiceImpl基类
 *
 * @param <M> 对应数据层Mapper
 * @param <T> 对应业务实体
 * @author wanghaoqiao
 */
@Slf4j
public class ICommonBaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements ICommonBaseService<T> {

    @Autowired
    MessageSource messageSource;

    @Override
    public CommonPage<T> table(QueryWrapper<T> queryWrapper, JsonParamDto tableParam) {
        UiepPage<T> queryPage = new UiepPage<>();
        //分页信息
        if (tableParam.getCurrentPage() <= 0) {
            queryPage.setCurrent(1);
        } else {
            queryPage.setCurrent(tableParam.getCurrentPage());
        }
        queryPage.setSize(tableParam.getPageSize());
        //分页模式查询
        queryPage.setPageInfo(baseMapper.selectPage(queryPage, queryWrapper));
        //查询结果转换
        CommonPage<T> retData = new CommonPage<>();
        retData.setPageInfo(queryPage);
        return retData;
    }

    @Override
    public String getMessageByI18nKey(String i18nKey) {
        try {
            return messageSource.getMessage(i18nKey, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return i18nKey;
        }
    }

    @Override
    public String filedErrorMessage(List<FieldError> errors) {
        return errors.stream().map(field -> getMessageByI18nKey(field.getDefaultMessage())).collect(Collectors.joining(", "));
    }

    @Override
    public JsonResult operationSuccess(String i18nKey) {
        return JsonResult.success(getMessageByI18nKey(i18nKey));
    }

    @Override
    public JsonResult operationFail(String i18nKey) {
        return JsonResult.fail(getMessageByI18nKey(i18nKey));
    }

    @Override
    public void createSort(String column, String direction, QueryWrapper queryWrapper) {
        if (!StringUtils.isBlank(column) && !StringUtils.isBlank(direction)) {
            if ("asc".equals(direction)) {
                queryWrapper.orderByAsc(column);
            } else if ("desc".equals(direction)) {
                queryWrapper.orderByDesc(column);
            }
        } else {
            queryWrapper.orderByDesc("create_date");
        }
    }


}
