package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;
import com.alibaba.fastjson.util.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Order(Integer.MIN_VALUE)
/* loaded from: classes.dex */
public class JSONPResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    public final Log logger = LogFactory.getLog(getClass());

    public void beforeBodyWriteInternal(JSONPObject jSONPObject, MediaType mediaType, MethodParameter methodParameter, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
    }

    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> cls) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(cls) && (methodParameter.getContainingClass().isAnnotationPresent(ResponseJSONP.class) || methodParameter.hasMethodAnnotation(ResponseJSONP.class));
    }

    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> cls, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ResponseJSONP responseJSONP = (ResponseJSONP) methodParameter.getMethodAnnotation(ResponseJSONP.class);
        if (responseJSONP == null) {
            responseJSONP = (ResponseJSONP) methodParameter.getContainingClass().getAnnotation(ResponseJSONP.class);
        }
        String parameter = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getParameter(responseJSONP.callback());
        if (!IOUtils.isValidJsonpQueryParam(parameter)) {
            if (this.logger.isDebugEnabled()) {
                Log log = this.logger;
                log.debug("Invalid jsonp parameter value:" + parameter);
            }
            parameter = null;
        }
        JSONPObject jSONPObject = new JSONPObject(parameter);
        jSONPObject.addParameter(obj);
        beforeBodyWriteInternal(jSONPObject, mediaType, methodParameter, serverHttpRequest, serverHttpResponse);
        return jSONPObject;
    }

    protected MediaType getContentType(MediaType mediaType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return FastJsonHttpMessageConverter.APPLICATION_JAVASCRIPT;
    }
}
