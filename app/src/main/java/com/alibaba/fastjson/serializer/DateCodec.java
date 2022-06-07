package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class DateCodec extends AbstractDateDeserializer implements ObjectDeserializer, ObjectSerializer {
    public static final DateCodec instance = new DateCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        Date date;
        char[] cArr;
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        Class<?> cls = obj.getClass();
        if (cls == java.sql.Date.class) {
            long time = ((java.sql.Date) obj).getTime();
            if ((time + jSONSerializer.timeZone.getOffset(time)) % 86400000 == 0 && !SerializerFeature.isEnabled(serializeWriter.features, i, SerializerFeature.WriteClassName)) {
                serializeWriter.writeString(obj.toString());
                return;
            }
        }
        if (cls == Time.class) {
            long time2 = ((Time) obj).getTime();
            if ("unixtime".equals(jSONSerializer.getDateFormatPattern())) {
                serializeWriter.writeLong(time2 / 1000);
                return;
            } else if ("millis".equals(jSONSerializer.getDateFormatPattern())) {
                serializeWriter.writeLong(time2);
                return;
            } else if (time2 < 86400000) {
                serializeWriter.writeString(obj.toString());
                return;
            }
        }
        int nanos = cls == Timestamp.class ? ((Timestamp) obj).getNanos() : 0;
        if (obj instanceof Date) {
            date = (Date) obj;
        } else {
            date = TypeUtils.castToDate(obj);
        }
        if ("unixtime".equals(jSONSerializer.getDateFormatPattern())) {
            serializeWriter.writeLong(date.getTime() / 1000);
        } else if ("millis".equals(jSONSerializer.getDateFormatPattern())) {
            serializeWriter.writeLong(date.getTime());
        } else if (serializeWriter.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            DateFormat dateFormat = jSONSerializer.getDateFormat();
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT, jSONSerializer.locale);
                dateFormat.setTimeZone(jSONSerializer.timeZone);
            }
            serializeWriter.writeString(dateFormat.format(date));
        } else if (!serializeWriter.isEnabled(SerializerFeature.WriteClassName) || cls == type) {
            long time3 = date.getTime();
            if (serializeWriter.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
                int i2 = serializeWriter.isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
                serializeWriter.write(i2);
                Calendar instance2 = Calendar.getInstance(jSONSerializer.timeZone, jSONSerializer.locale);
                instance2.setTimeInMillis(time3);
                int i3 = instance2.get(1);
                int i4 = instance2.get(2) + 1;
                int i5 = instance2.get(5);
                int i6 = instance2.get(11);
                int i7 = instance2.get(12);
                int i8 = instance2.get(13);
                int i9 = instance2.get(14);
                if (nanos > 0) {
                    cArr = "0000-00-00 00:00:00.000000000".toCharArray();
                    IOUtils.getChars(nanos, 29 - (9 - IOUtils.stringSize(nanos)), cArr);
                    IOUtils.getChars(i8, 19, cArr);
                    IOUtils.getChars(i7, 16, cArr);
                    IOUtils.getChars(i6, 13, cArr);
                    IOUtils.getChars(i5, 10, cArr);
                    IOUtils.getChars(i4, 7, cArr);
                    IOUtils.getChars(i3, 4, cArr);
                } else if (i9 != 0) {
                    char[] charArray = "0000-00-00T00:00:00.000".toCharArray();
                    IOUtils.getChars(i9, 23, charArray);
                    IOUtils.getChars(i8, 19, charArray);
                    IOUtils.getChars(i7, 16, charArray);
                    IOUtils.getChars(i6, 13, charArray);
                    IOUtils.getChars(i5, 10, charArray);
                    IOUtils.getChars(i4, 7, charArray);
                    IOUtils.getChars(i3, 4, charArray);
                    cArr = charArray;
                } else if (i8 == 0 && i7 == 0 && i6 == 0) {
                    char[] charArray2 = "0000-00-00".toCharArray();
                    IOUtils.getChars(i5, 10, charArray2);
                    IOUtils.getChars(i4, 7, charArray2);
                    IOUtils.getChars(i3, 4, charArray2);
                    cArr = charArray2;
                } else {
                    cArr = "0000-00-00T00:00:00".toCharArray();
                    IOUtils.getChars(i8, 19, cArr);
                    IOUtils.getChars(i7, 16, cArr);
                    IOUtils.getChars(i6, 13, cArr);
                    IOUtils.getChars(i5, 10, cArr);
                    IOUtils.getChars(i4, 7, cArr);
                    IOUtils.getChars(i3, 4, cArr);
                }
                serializeWriter.write(cArr);
                if (nanos > 0) {
                    serializeWriter.write(i2);
                    return;
                }
                float offset = instance2.getTimeZone().getOffset(instance2.getTimeInMillis()) / 3600000.0f;
                int i10 = (int) offset;
                if (i10 == 0.0d) {
                    serializeWriter.write(90);
                } else {
                    if (i10 > 9) {
                        serializeWriter.write(43);
                        serializeWriter.writeInt(i10);
                    } else if (i10 > 0) {
                        serializeWriter.write(43);
                        serializeWriter.write(48);
                        serializeWriter.writeInt(i10);
                    } else if (i10 < -9) {
                        serializeWriter.write(45);
                        serializeWriter.writeInt(-i10);
                    } else if (i10 < 0) {
                        serializeWriter.write(45);
                        serializeWriter.write(48);
                        serializeWriter.writeInt(-i10);
                    }
                    serializeWriter.write(58);
                    serializeWriter.append((CharSequence) String.format("%02d", Integer.valueOf((int) (Math.abs(offset - i10) * 60.0f))));
                }
                serializeWriter.write(i2);
                return;
            }
            serializeWriter.writeLong(time3);
        } else if (cls == Date.class) {
            serializeWriter.write("new Date(");
            serializeWriter.writeLong(((Date) obj).getTime());
            serializeWriter.write(41);
        } else {
            serializeWriter.write(123);
            serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
            jSONSerializer.write(cls.getName());
            serializeWriter.writeFieldValue(StringUtil.COMMA, "val", ((Date) obj).getTime());
            serializeWriter.write(125);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v17, types: [java.util.Calendar, T] */
    /* JADX WARN: Type inference failed for: r4v24, types: [java.util.Calendar, T] */
    /* JADX WARN: Unknown variable types count: 2 */
    @Override // com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <T> T cast(com.alibaba.fastjson.parser.DefaultJSONParser r4, java.lang.reflect.Type r5, java.lang.Object r6, java.lang.Object r7) {
        /*
            Method dump skipped, instructions count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.DateCodec.cast(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object):java.lang.Object");
    }
}
