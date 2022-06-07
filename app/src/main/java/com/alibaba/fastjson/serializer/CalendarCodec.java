package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/* loaded from: classes.dex */
public class CalendarCodec extends ContextObjectDeserializer implements ObjectDeserializer, ContextObjectSerializer, ObjectSerializer {
    public static final CalendarCodec instance = new CalendarCodec();
    private DatatypeFactory dateFactory;

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, BeanContext beanContext) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        String format = beanContext.getFormat();
        Calendar calendar = (Calendar) obj;
        if (format.equals("unixtime")) {
            serializeWriter.writeInt((int) (calendar.getTimeInMillis() / 1000));
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(jSONSerializer.timeZone);
        serializeWriter.writeString(simpleDateFormat.format(calendar.getTime()));
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        Calendar calendar;
        char[] cArr;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        if (obj instanceof XMLGregorianCalendar) {
            calendar = ((XMLGregorianCalendar) obj).toGregorianCalendar();
        } else {
            calendar = (Calendar) obj;
        }
        if (serializeWriter.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
            char c = serializeWriter.isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : '\"';
            serializeWriter.append(c);
            int i2 = calendar.get(1);
            int i3 = calendar.get(2) + 1;
            int i4 = calendar.get(5);
            int i5 = calendar.get(11);
            int i6 = calendar.get(12);
            int i7 = calendar.get(13);
            int i8 = calendar.get(14);
            if (i8 != 0) {
                cArr = "0000-00-00T00:00:00.000".toCharArray();
                IOUtils.getChars(i8, 23, cArr);
                IOUtils.getChars(i7, 19, cArr);
                IOUtils.getChars(i6, 16, cArr);
                IOUtils.getChars(i5, 13, cArr);
                IOUtils.getChars(i4, 10, cArr);
                IOUtils.getChars(i3, 7, cArr);
                IOUtils.getChars(i2, 4, cArr);
            } else if (i7 == 0 && i6 == 0 && i5 == 0) {
                cArr = "0000-00-00".toCharArray();
                IOUtils.getChars(i4, 10, cArr);
                IOUtils.getChars(i3, 7, cArr);
                IOUtils.getChars(i2, 4, cArr);
            } else {
                cArr = "0000-00-00T00:00:00".toCharArray();
                IOUtils.getChars(i7, 19, cArr);
                IOUtils.getChars(i6, 16, cArr);
                IOUtils.getChars(i5, 13, cArr);
                IOUtils.getChars(i4, 10, cArr);
                IOUtils.getChars(i3, 7, cArr);
                IOUtils.getChars(i2, 4, cArr);
            }
            serializeWriter.write(cArr);
            float offset = calendar.getTimeZone().getOffset(calendar.getTimeInMillis()) / 3600000.0f;
            int i9 = (int) offset;
            if (i9 == 0.0d) {
                serializeWriter.write(90);
            } else {
                if (i9 > 9) {
                    serializeWriter.write(43);
                    serializeWriter.writeInt(i9);
                } else if (i9 > 0) {
                    serializeWriter.write(43);
                    serializeWriter.write(48);
                    serializeWriter.writeInt(i9);
                } else if (i9 < -9) {
                    serializeWriter.write(45);
                    serializeWriter.writeInt(i9);
                } else if (i9 < 0) {
                    serializeWriter.write(45);
                    serializeWriter.write(48);
                    serializeWriter.writeInt(-i9);
                }
                serializeWriter.write(58);
                serializeWriter.append((CharSequence) String.format("%02d", Integer.valueOf((int) ((offset - i9) * 60.0f))));
            }
            serializeWriter.append(c);
            return;
        }
        jSONSerializer.write(calendar.getTime());
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [java.util.Calendar, T] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r7, java.lang.reflect.Type r8, java.lang.Object r9, java.lang.String r10, int r11) {
        /*
            r6 = this;
            com.alibaba.fastjson.serializer.DateCodec r0 = com.alibaba.fastjson.serializer.DateCodec.instance
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            java.lang.Object r9 = r0.deserialze(r1, r2, r3, r4, r5)
            boolean r10 = r9 instanceof java.util.Calendar
            if (r10 == 0) goto L_0x0010
            return r9
        L_0x0010:
            java.util.Date r9 = (java.util.Date) r9
            if (r9 != 0) goto L_0x0016
            r7 = 0
            return r7
        L_0x0016:
            com.alibaba.fastjson.parser.JSONLexer r7 = r7.lexer
            java.util.TimeZone r10 = r7.getTimeZone()
            java.util.Locale r7 = r7.getLocale()
            java.util.Calendar r7 = java.util.Calendar.getInstance(r10, r7)
            r7.setTime(r9)
            java.lang.Class<javax.xml.datatype.XMLGregorianCalendar> r9 = javax.xml.datatype.XMLGregorianCalendar.class
            if (r8 != r9) goto L_0x0032
            java.util.GregorianCalendar r7 = (java.util.GregorianCalendar) r7
            javax.xml.datatype.XMLGregorianCalendar r7 = r6.createXMLGregorianCalendar(r7)
            return r7
        L_0x0032:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.CalendarCodec.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.String, int):java.lang.Object");
    }

    public XMLGregorianCalendar createXMLGregorianCalendar(Calendar calendar) {
        if (this.dateFactory == null) {
            try {
                this.dateFactory = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException e) {
                throw new IllegalStateException("Could not obtain an instance of DatatypeFactory.", e);
            }
        }
        return this.dateFactory.newXMLGregorianCalendar((GregorianCalendar) calendar);
    }
}
