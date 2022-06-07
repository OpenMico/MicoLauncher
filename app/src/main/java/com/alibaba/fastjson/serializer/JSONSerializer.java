package com.alibaba.fastjson.serializer;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class JSONSerializer extends SerializeFilterable {
    protected final SerializeConfig config;
    protected SerialContext context;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private String indent;
    private int indentCount;
    protected Locale locale;
    public final SerializeWriter out;
    protected IdentityHashMap<Object, SerialContext> references;
    protected TimeZone timeZone;

    public JSONSerializer() {
        this(new SerializeWriter(), SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeWriter serializeWriter) {
        this(serializeWriter, SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeConfig serializeConfig) {
        this(new SerializeWriter(), serializeConfig);
    }

    public JSONSerializer(SerializeWriter serializeWriter, SerializeConfig serializeConfig) {
        this.indentCount = 0;
        this.indent = "\t";
        this.references = null;
        this.timeZone = JSON.defaultTimeZone;
        this.locale = JSON.defaultLocale;
        this.out = serializeWriter;
        this.config = serializeConfig;
    }

    public String getDateFormatPattern() {
        DateFormat dateFormat = this.dateFormat;
        if (dateFormat instanceof SimpleDateFormat) {
            return ((SimpleDateFormat) dateFormat).toPattern();
        }
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        String str;
        if (this.dateFormat == null && (str = this.dateFormatPattern) != null) {
            this.dateFormat = new SimpleDateFormat(str, this.locale);
            this.dateFormat.setTimeZone(this.timeZone);
        }
        return this.dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        if (this.dateFormatPattern != null) {
            this.dateFormatPattern = null;
        }
    }

    public void setDateFormat(String str) {
        this.dateFormatPattern = str;
        if (this.dateFormat != null) {
            this.dateFormat = null;
        }
    }

    public SerialContext getContext() {
        return this.context;
    }

    public void setContext(SerialContext serialContext) {
        this.context = serialContext;
    }

    public void setContext(SerialContext serialContext, Object obj, Object obj2, int i) {
        setContext(serialContext, obj, obj2, i, 0);
    }

    public void setContext(SerialContext serialContext, Object obj, Object obj2, int i, int i2) {
        if (!this.out.disableCircularReferenceDetect) {
            this.context = new SerialContext(serialContext, obj, obj2, i, i2);
            if (this.references == null) {
                this.references = new IdentityHashMap<>();
            }
            this.references.put(obj, this.context);
        }
    }

    public void setContext(Object obj, Object obj2) {
        setContext(this.context, obj, obj2, 0);
    }

    public void popContext() {
        SerialContext serialContext = this.context;
        if (serialContext != null) {
            this.context = serialContext.parent;
        }
    }

    public final boolean isWriteClassName(Type type, Object obj) {
        SerialContext serialContext;
        return this.out.isEnabled(SerializerFeature.WriteClassName) && !(type == null && this.out.isEnabled(SerializerFeature.NotWriteRootClassName) && ((serialContext = this.context) == null || serialContext.parent == null));
    }

    public boolean containsReference(Object obj) {
        SerialContext serialContext;
        IdentityHashMap<Object, SerialContext> identityHashMap = this.references;
        if (identityHashMap == null || (serialContext = identityHashMap.get(obj)) == null || obj == Collections.emptyMap()) {
            return false;
        }
        Object obj2 = serialContext.fieldName;
        return obj2 == null || (obj2 instanceof Integer) || (obj2 instanceof String);
    }

    public void writeReference(Object obj) {
        SerialContext serialContext = this.context;
        if (obj == serialContext.object) {
            this.out.write("{\"$ref\":\"@\"}");
            return;
        }
        SerialContext serialContext2 = serialContext.parent;
        if (serialContext2 == null || obj != serialContext2.object) {
            while (serialContext.parent != null) {
                serialContext = serialContext.parent;
            }
            if (obj == serialContext.object) {
                this.out.write("{\"$ref\":\"$\"}");
                return;
            }
            this.out.write("{\"$ref\":\"");
            this.out.write(this.references.get(obj).toString());
            this.out.write("\"}");
            return;
        }
        this.out.write("{\"$ref\":\"..\"}");
    }

    public boolean checkValue(SerializeFilterable serializeFilterable) {
        return (this.valueFilters != null && this.valueFilters.size() > 0) || (this.contextValueFilters != null && this.contextValueFilters.size() > 0) || ((serializeFilterable.valueFilters != null && serializeFilterable.valueFilters.size() > 0) || ((serializeFilterable.contextValueFilters != null && serializeFilterable.contextValueFilters.size() > 0) || this.out.writeNonStringValueAsString));
    }

    public boolean hasNameFilters(SerializeFilterable serializeFilterable) {
        return (this.nameFilters != null && this.nameFilters.size() > 0) || (serializeFilterable.nameFilters != null && serializeFilterable.nameFilters.size() > 0);
    }

    public boolean hasPropertyFilters(SerializeFilterable serializeFilterable) {
        return (this.propertyFilters != null && this.propertyFilters.size() > 0) || (serializeFilterable.propertyFilters != null && serializeFilterable.propertyFilters.size() > 0);
    }

    public int getIndentCount() {
        return this.indentCount;
    }

    public void incrementIndent() {
        this.indentCount++;
    }

    public void decrementIdent() {
        this.indentCount--;
    }

    public void println() {
        this.out.write(10);
        for (int i = 0; i < this.indentCount; i++) {
            this.out.write(this.indent);
        }
    }

    public SerializeWriter getWriter() {
        return this.out;
    }

    public String toString() {
        return this.out.toString();
    }

    public void config(SerializerFeature serializerFeature, boolean z) {
        this.out.config(serializerFeature, z);
    }

    public boolean isEnabled(SerializerFeature serializerFeature) {
        return this.out.isEnabled(serializerFeature);
    }

    public void writeNull() {
        this.out.writeNull();
    }

    public SerializeConfig getMapping() {
        return this.config;
    }

    public static void write(Writer writer, Object obj) {
        SerializeWriter serializeWriter;
        try {
            serializeWriter = new SerializeWriter();
            try {
                new JSONSerializer(serializeWriter).write(obj);
                serializeWriter.writeTo(writer);
            } catch (IOException e) {
                throw new JSONException(e.getMessage(), e);
            }
        } finally {
            serializeWriter.close();
        }
    }

    public static void write(SerializeWriter serializeWriter, Object obj) {
        new JSONSerializer(serializeWriter).write(obj);
    }

    public final void write(Object obj) {
        if (obj == null) {
            this.out.writeNull();
            return;
        }
        try {
            getObjectWriter(obj.getClass()).write(this, obj, null, null, 0);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeAs(Object obj, Class cls) {
        if (obj == null) {
            this.out.writeNull();
            return;
        }
        try {
            getObjectWriter(cls).write(this, obj, null, null, 0);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeWithFieldName(Object obj, Object obj2) {
        writeWithFieldName(obj, obj2, null, 0);
    }

    public final void writeKeyValue(char c, String str, Object obj) {
        if (c != 0) {
            this.out.write(c);
        }
        this.out.writeFieldName(str);
        write(obj);
    }

    public final void writeWithFieldName(Object obj, Object obj2, Type type, int i) {
        try {
            if (obj == null) {
                this.out.writeNull();
            } else {
                getObjectWriter(obj.getClass()).write(this, obj, obj2, type, i);
            }
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeWithFormat(Object obj, String str) {
        if (obj instanceof Date) {
            if ("unixtime".equals(str)) {
                this.out.writeInt((int) (((Date) obj).getTime() / 1000));
            } else if ("millis".equals(str)) {
                this.out.writeLong(((Date) obj).getTime());
            } else {
                DateFormat dateFormat = getDateFormat();
                if (dateFormat == null) {
                    try {
                        dateFormat = new SimpleDateFormat(str, this.locale);
                    } catch (IllegalArgumentException unused) {
                        dateFormat = new SimpleDateFormat(str.replaceAll(ExifInterface.GPS_DIRECTION_TRUE, "'T'"), this.locale);
                    }
                    dateFormat.setTimeZone(this.timeZone);
                }
                this.out.writeString(dateFormat.format((Date) obj));
            }
        } else if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            if ("gzip".equals(str) || "gzip,base64".equals(str)) {
                GZIPOutputStream gZIPOutputStream = null;
                try {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        if (bArr.length < 512) {
                            gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream, bArr.length);
                        } else {
                            gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                        }
                        gZIPOutputStream.write(bArr);
                        gZIPOutputStream.finish();
                        this.out.writeByteArray(byteArrayOutputStream.toByteArray());
                    } catch (IOException e) {
                        throw new JSONException("write gzipBytes error", e);
                    }
                } finally {
                    IOUtils.close(gZIPOutputStream);
                }
            } else if ("hex".equals(str)) {
                this.out.writeHex(bArr);
            } else {
                this.out.writeByteArray(bArr);
            }
        } else if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            Iterator it = collection.iterator();
            this.out.write(91);
            for (int i = 0; i < collection.size(); i++) {
                Object next = it.next();
                if (i != 0) {
                    this.out.write(44);
                }
                writeWithFormat(next, str);
            }
            this.out.write(93);
        } else {
            write(obj);
        }
    }

    public final void write(String str) {
        StringCodec.instance.write(this, str);
    }

    public ObjectSerializer getObjectWriter(Class<?> cls) {
        return this.config.getObjectWriter(cls);
    }

    public void close() {
        this.out.close();
    }
}
