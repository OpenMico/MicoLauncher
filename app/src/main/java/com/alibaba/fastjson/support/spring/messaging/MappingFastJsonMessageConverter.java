package com.alibaba.fastjson.support.spring.messaging;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.google.android.exoplayer2.util.MimeTypes;
import java.nio.charset.Charset;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

/* loaded from: classes.dex */
public class MappingFastJsonMessageConverter extends AbstractMessageConverter {
    private FastJsonConfig fastJsonConfig = new FastJsonConfig();

    protected boolean supports(Class<?> cls) {
        return true;
    }

    public FastJsonConfig getFastJsonConfig() {
        return this.fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

    public MappingFastJsonMessageConverter() {
        super(new MimeType(MimeTypes.BASE_TYPE_APPLICATION, "json", Charset.forName("UTF-8")));
    }

    protected boolean canConvertFrom(Message<?> message, Class<?> cls) {
        return supports(cls);
    }

    protected boolean canConvertTo(Object obj, MessageHeaders messageHeaders) {
        return supports(obj.getClass());
    }

    protected Object convertFromInternal(Message<?> message, Class<?> cls, Object obj) {
        Object payload = message.getPayload();
        if (payload instanceof byte[]) {
            return JSON.parseObject((byte[]) payload, this.fastJsonConfig.getCharset(), cls, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
        }
        if (payload instanceof String) {
            return JSON.parseObject((String) payload, cls, this.fastJsonConfig.getParserConfig(), this.fastJsonConfig.getParseProcess(), JSON.DEFAULT_PARSER_FEATURE, this.fastJsonConfig.getFeatures());
        }
        return null;
    }

    protected Object convertToInternal(Object obj, MessageHeaders messageHeaders, Object obj2) {
        if (byte[].class != getSerializedPayloadClass()) {
            return (!(obj instanceof String) || !JSON.isValid((String) obj)) ? JSON.toJSONString(obj, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures()) : obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (JSON.isValid(str)) {
                return str.getBytes(this.fastJsonConfig.getCharset());
            }
        }
        return JSON.toJSONBytes(this.fastJsonConfig.getCharset(), obj, this.fastJsonConfig.getSerializeConfig(), this.fastJsonConfig.getSerializeFilters(), this.fastJsonConfig.getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.fastJsonConfig.getSerializerFeatures());
    }
}
