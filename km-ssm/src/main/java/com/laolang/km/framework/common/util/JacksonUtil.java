package com.laolang.km.framework.common.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.laolang.km.framework.common.consts.CommonStatusCode;
import com.laolang.km.framework.common.exception.BusinessException;
import com.laolang.km.framework.web.jackson.module.BigDecimalModule;
import com.laolang.km.framework.web.jackson.module.Jdk8TimeModule;
import com.laolang.km.framework.web.jackson.module.LongModule;

import cn.hutool.core.collection.CollUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JacksonUtil {

    /**
     * 默认的 objectMapper, 不带格式化
     */
    private static ObjectMapper objectMapper;
    /**
     * 带格式化的 objectMapper
     */
    private static ObjectMapper objectMapperPretty;

    /**
     * 格式化需要的 Feature
     */
    private static final List<SerializationFeature> DEFAULT_PRETTY_SERIALIZATIONFEATURE = Lists
            .newArrayList(SerializationFeature.INDENT_OUTPUT);

    /**
     * ****************************************************************************
     * 初始化相关
     * ****************************************************************************
     */
    static {
        initObjectMapper();
    }

    /**
     * 初始化 objectMapper 和 objectMapperPretty
     */
    private static void initObjectMapper() {
        objectMapper = new ObjectMapper();
        registerModule(objectMapper);

        objectMapperPretty = new ObjectMapper();
        registerModule(objectMapperPretty);
        enableFeatures(objectMapperPretty, DEFAULT_PRETTY_SERIALIZATIONFEATURE, null);
    }

    /**
     * 注册自定义 Module
     */
    private static void registerModule(ObjectMapper om) {
        om.registerModule(new BigDecimalModule());
        om.registerModule(new LongModule());
        om.registerModule(new Jdk8TimeModule());
    }

    /**
     * 开启 Feature
     */
    private static void enableFeatures(ObjectMapper om, List<SerializationFeature> serializationFeatures,
            List<DeserializationFeature> deserializationFeatures) {
        if (CollUtil.isNotEmpty(serializationFeatures)) {
            for (SerializationFeature feature : serializationFeatures) {
                om.enable(feature);
            }
        }
        if (CollUtil.isNotEmpty(deserializationFeatures)) {
            for (DeserializationFeature feature : deserializationFeatures) {
                om.enable(feature);
            }
        }
    }

    /**
     * ****************************************************************************
     * json 相关
     * ****************************************************************************
     */

    public static String toJson(Object o) {
        return toJson(o, false, true);
    }

    public static String toJson(Object o, boolean pretty) {
        return toJson(o, pretty, true);
    }

    public static String toJson(Object o, boolean pretty, boolean ignoreError) {
        try {
            if (pretty) {
                return objectMapperPretty.writeValueAsString(o);
            }
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            if (!ignoreError) {
                throw new BusinessException(CommonStatusCode.FAILED.getCode(), e.getMessage());
            }
            e.printStackTrace();
        }
        return null;
    }
}
