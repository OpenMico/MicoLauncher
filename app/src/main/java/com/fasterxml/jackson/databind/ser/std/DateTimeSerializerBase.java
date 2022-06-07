package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class DateTimeSerializerBase<T> extends StdScalarSerializer<T> implements ContextualSerializer {
    protected final DateFormat _customFormat;
    protected final AtomicReference<DateFormat> _reusedCustomFormat;
    protected final Boolean _useTimestamp;

    protected abstract long _timestamp(T t);

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(SerializerProvider serializerProvider, T t) {
        return false;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public abstract void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    public abstract DateTimeSerializerBase<T> withFormat(Boolean bool, DateFormat dateFormat);

    public DateTimeSerializerBase(Class<T> cls, Boolean bool, DateFormat dateFormat) {
        super(cls);
        this._useTimestamp = bool;
        this._customFormat = dateFormat;
        this._reusedCustomFormat = dateFormat == null ? null : new AtomicReference<>();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        SimpleDateFormat simpleDateFormat;
        Locale locale;
        JsonFormat.Value findFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, handledType());
        if (findFormatOverrides == null) {
            return this;
        }
        JsonFormat.Shape shape = findFormatOverrides.getShape();
        if (shape.isNumeric()) {
            return withFormat(Boolean.TRUE, null);
        }
        if (findFormatOverrides.hasPattern()) {
            if (findFormatOverrides.hasLocale()) {
                locale = findFormatOverrides.getLocale();
            } else {
                locale = serializerProvider.getLocale();
            }
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(findFormatOverrides.getPattern(), locale);
            simpleDateFormat2.setTimeZone(findFormatOverrides.hasTimeZone() ? findFormatOverrides.getTimeZone() : serializerProvider.getTimeZone());
            return withFormat(Boolean.FALSE, simpleDateFormat2);
        }
        boolean hasLocale = findFormatOverrides.hasLocale();
        boolean hasTimeZone = findFormatOverrides.hasTimeZone();
        boolean z = true;
        boolean z2 = shape == JsonFormat.Shape.STRING;
        if (!hasLocale && !hasTimeZone && !z2) {
            return this;
        }
        DateFormat dateFormat = serializerProvider.getConfig().getDateFormat();
        if (dateFormat instanceof StdDateFormat) {
            StdDateFormat stdDateFormat = (StdDateFormat) dateFormat;
            if (findFormatOverrides.hasLocale()) {
                stdDateFormat = stdDateFormat.withLocale(findFormatOverrides.getLocale());
            }
            if (findFormatOverrides.hasTimeZone()) {
                stdDateFormat = stdDateFormat.withTimeZone(findFormatOverrides.getTimeZone());
            }
            return withFormat(Boolean.FALSE, stdDateFormat);
        }
        if (!(dateFormat instanceof SimpleDateFormat)) {
            serializerProvider.reportBadDefinition(handledType(), String.format("Configured `DateFormat` (%s) not a `SimpleDateFormat`; cannot configure `Locale` or `TimeZone`", dateFormat.getClass().getName()));
        }
        SimpleDateFormat simpleDateFormat3 = (SimpleDateFormat) dateFormat;
        if (hasLocale) {
            simpleDateFormat = new SimpleDateFormat(simpleDateFormat3.toPattern(), findFormatOverrides.getLocale());
        } else {
            simpleDateFormat = (SimpleDateFormat) simpleDateFormat3.clone();
        }
        TimeZone timeZone = findFormatOverrides.getTimeZone();
        if (timeZone == null || timeZone.equals(simpleDateFormat.getTimeZone())) {
            z = false;
        }
        if (z) {
            simpleDateFormat.setTimeZone(timeZone);
        }
        return withFormat(Boolean.FALSE, simpleDateFormat);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return createSchemaNode(_asTimestamp(serializerProvider) ? "number" : "string", true);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        _acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType, _asTimestamp(jsonFormatVisitorWrapper.getProvider()));
    }

    protected boolean _asTimestamp(SerializerProvider serializerProvider) {
        Boolean bool = this._useTimestamp;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (this._customFormat != null) {
            return false;
        }
        if (serializerProvider != null) {
            return serializerProvider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        throw new IllegalArgumentException("Null SerializerProvider passed for " + handledType().getName());
    }

    protected void _acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType, boolean z) throws JsonMappingException {
        if (z) {
            visitIntFormat(jsonFormatVisitorWrapper, javaType, JsonParser.NumberType.LONG, JsonValueFormat.UTC_MILLISEC);
        } else {
            visitStringFormat(jsonFormatVisitorWrapper, javaType, JsonValueFormat.DATE_TIME);
        }
    }

    protected void _serializeAsString(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (this._customFormat == null) {
            serializerProvider.defaultSerializeDateValue(date, jsonGenerator);
            return;
        }
        DateFormat andSet = this._reusedCustomFormat.getAndSet(null);
        if (andSet == null) {
            andSet = (DateFormat) this._customFormat.clone();
        }
        jsonGenerator.writeString(andSet.format(date));
        this._reusedCustomFormat.compareAndSet(null, andSet);
    }
}
