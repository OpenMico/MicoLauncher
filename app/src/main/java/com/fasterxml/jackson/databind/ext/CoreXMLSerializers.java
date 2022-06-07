package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.util.Calendar;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/* loaded from: classes.dex */
public class CoreXMLSerializers extends Serializers.Base {
    @Override // com.fasterxml.jackson.databind.ser.Serializers.Base, com.fasterxml.jackson.databind.ser.Serializers
    public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) {
        Class<?> rawClass = javaType.getRawClass();
        if (Duration.class.isAssignableFrom(rawClass) || QName.class.isAssignableFrom(rawClass)) {
            return ToStringSerializer.instance;
        }
        if (XMLGregorianCalendar.class.isAssignableFrom(rawClass)) {
            return XMLGregorianCalendarSerializer.instance;
        }
        return null;
    }

    /* loaded from: classes.dex */
    public static class XMLGregorianCalendarSerializer extends StdSerializer<XMLGregorianCalendar> implements ContextualSerializer {
        static final XMLGregorianCalendarSerializer instance = new XMLGregorianCalendarSerializer();
        final JsonSerializer<Object> _delegate;

        public XMLGregorianCalendarSerializer() {
            this(CalendarSerializer.instance);
        }

        protected XMLGregorianCalendarSerializer(JsonSerializer<?> jsonSerializer) {
            super(XMLGregorianCalendar.class);
            this._delegate = jsonSerializer;
        }

        @Override // com.fasterxml.jackson.databind.JsonSerializer
        public JsonSerializer<?> getDelegatee() {
            return this._delegate;
        }

        public boolean isEmpty(SerializerProvider serializerProvider, XMLGregorianCalendar xMLGregorianCalendar) {
            return this._delegate.isEmpty(serializerProvider, _convert(xMLGregorianCalendar));
        }

        public void serialize(XMLGregorianCalendar xMLGregorianCalendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            this._delegate.serialize(_convert(xMLGregorianCalendar), jsonGenerator, serializerProvider);
        }

        public void serializeWithType(XMLGregorianCalendar xMLGregorianCalendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
            this._delegate.serializeWithType(_convert(xMLGregorianCalendar), jsonGenerator, serializerProvider, typeSerializer);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
        public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
            this._delegate.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, null);
        }

        @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
        public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
            JsonSerializer<?> handlePrimaryContextualization = serializerProvider.handlePrimaryContextualization(this._delegate, beanProperty);
            return handlePrimaryContextualization != this._delegate ? new XMLGregorianCalendarSerializer(handlePrimaryContextualization) : this;
        }

        protected Calendar _convert(XMLGregorianCalendar xMLGregorianCalendar) {
            if (xMLGregorianCalendar == null) {
                return null;
            }
            return xMLGregorianCalendar.toGregorianCalendar();
        }
    }
}
