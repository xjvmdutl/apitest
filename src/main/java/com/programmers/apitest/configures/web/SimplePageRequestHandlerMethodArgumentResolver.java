package com.programmers.apitest.configures.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SimplePageRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_OFFSET_PARAMETER = "offset";

    private static final String DEFAULT_SIZE_PARAMETER = "size";

    private static final long DEFAULT_OFFSET = 0;
    private static final long MIN_OFFSET = 0;
    private static final long MAX_OFFSET = Long.MAX_VALUE;

    private static final int DEFAULT_SIZE = 5;
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 5;

    private String offsetParameterName = DEFAULT_OFFSET_PARAMETER;

    private String sizeParameterName = DEFAULT_SIZE_PARAMETER;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public SimplePageRequest resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        return new SimplePageRequest(validOffset(webRequest.getParameter(offsetParameterName)), validSize(webRequest.getParameter(sizeParameterName)));
    }

    private int validSize(String sizeStr) {
        Integer size;
        try {
            size = Integer.parseInt(sizeStr);
        } catch (NumberFormatException ne) {
            size = null;
        }
        if (size == null || size < MIN_SIZE || size > MAX_SIZE) return DEFAULT_SIZE;
        return size;
    }

    private long validOffset(String offsetString) {
        Long offset;
        try {
            offset = Long.parseLong(offsetString);
        } catch (NumberFormatException ne) {
            offset = null;
        }
        if (offset == null || offset < MIN_OFFSET || offset > MAX_OFFSET) return DEFAULT_OFFSET;
        return offset;
    }

    public void setOffsetParameterName(String offsetParameterName) {
        this.offsetParameterName = offsetParameterName;
    }

    public void setSizeParameterName(String sizeParameterName) {
        this.sizeParameterName = sizeParameterName;
    }

}