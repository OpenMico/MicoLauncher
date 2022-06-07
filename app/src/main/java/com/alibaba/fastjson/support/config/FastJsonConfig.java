package com.alibaba.fastjson.support.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import java.nio.charset.Charset;
import java.util.Map;

/* loaded from: classes.dex */
public class FastJsonConfig {
    private Map<Class<?>, SerializeFilter> classSerializeFilters;
    private String dateFormat;
    private ParseProcess parseProcess;
    private Charset charset = IOUtils.UTF8;
    private SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
    private ParserConfig parserConfig = ParserConfig.getGlobalInstance();
    private SerializerFeature[] serializerFeatures = {SerializerFeature.BrowserSecure};
    private SerializeFilter[] serializeFilters = new SerializeFilter[0];
    private Feature[] features = new Feature[0];
    private boolean writeContentLength = true;

    public SerializeConfig getSerializeConfig() {
        return this.serializeConfig;
    }

    public void setSerializeConfig(SerializeConfig serializeConfig) {
        this.serializeConfig = serializeConfig;
    }

    public ParserConfig getParserConfig() {
        return this.parserConfig;
    }

    public void setParserConfig(ParserConfig parserConfig) {
        this.parserConfig = parserConfig;
    }

    public SerializerFeature[] getSerializerFeatures() {
        return this.serializerFeatures;
    }

    public void setSerializerFeatures(SerializerFeature... serializerFeatureArr) {
        this.serializerFeatures = serializerFeatureArr;
    }

    public SerializeFilter[] getSerializeFilters() {
        return this.serializeFilters;
    }

    public void setSerializeFilters(SerializeFilter... serializeFilterArr) {
        this.serializeFilters = serializeFilterArr;
    }

    public Feature[] getFeatures() {
        return this.features;
    }

    public void setFeatures(Feature... featureArr) {
        this.features = featureArr;
    }

    public Map<Class<?>, SerializeFilter> getClassSerializeFilters() {
        return this.classSerializeFilters;
    }

    public void setClassSerializeFilters(Map<Class<?>, SerializeFilter> map) {
        if (map != null) {
            for (Map.Entry<Class<?>, SerializeFilter> entry : map.entrySet()) {
                this.serializeConfig.addFilter(entry.getKey(), entry.getValue());
            }
            this.classSerializeFilters = map;
        }
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String str) {
        this.dateFormat = str;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public boolean isWriteContentLength() {
        return this.writeContentLength;
    }

    public void setWriteContentLength(boolean z) {
        this.writeContentLength = z;
    }

    public ParseProcess getParseProcess() {
        return this.parseProcess;
    }

    public void setParseProcess(ParseProcess parseProcess) {
        this.parseProcess = parseProcess;
    }
}
