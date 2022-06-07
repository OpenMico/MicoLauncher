package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/* loaded from: classes.dex */
public class SqlDateDeserializer extends AbstractDateDeserializer implements ObjectDeserializer {
    public static final SqlDateDeserializer instance = new SqlDateDeserializer();
    public static final SqlDateDeserializer instance_timestamp = new SqlDateDeserializer(true);
    private boolean timestamp;

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }

    public SqlDateDeserializer() {
        this.timestamp = false;
    }

    public SqlDateDeserializer(boolean z) {
        this.timestamp = false;
        this.timestamp = true;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
    protected <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        long j;
        if (this.timestamp) {
            return (T) castTimestamp(defaultJSONParser, type, obj, obj2);
        }
        if (obj2 == null) {
            return null;
        }
        if (obj2 instanceof Date) {
            return (T) new java.sql.Date(((Date) obj2).getTime());
        }
        if (obj2 instanceof BigDecimal) {
            return (T) new java.sql.Date(TypeUtils.longValue((BigDecimal) obj2));
        }
        if (obj2 instanceof Number) {
            return (T) new java.sql.Date(((Number) obj2).longValue());
        }
        if (obj2 instanceof String) {
            String str = (String) obj2;
            if (str.length() == 0) {
                return null;
            }
            JSONScanner jSONScanner = new JSONScanner(str);
            try {
                if (jSONScanner.scanISO8601DateIfMatch()) {
                    j = jSONScanner.getCalendar().getTimeInMillis();
                } else {
                    try {
                        return (T) new java.sql.Date(defaultJSONParser.getDateFormat().parse(str).getTime());
                    } catch (ParseException unused) {
                        j = Long.parseLong(str);
                    }
                }
                jSONScanner.close();
                return (T) new java.sql.Date(j);
            } finally {
                jSONScanner.close();
            }
        } else {
            throw new JSONException("parse error : " + obj2);
        }
    }

    protected <T> T castTimestamp(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        long j;
        if (obj2 == null) {
            return null;
        }
        if (obj2 instanceof Date) {
            return (T) new Timestamp(((Date) obj2).getTime());
        }
        if (obj2 instanceof BigDecimal) {
            return (T) new Timestamp(TypeUtils.longValue((BigDecimal) obj2));
        }
        if (obj2 instanceof Number) {
            return (T) new Timestamp(((Number) obj2).longValue());
        }
        if (obj2 instanceof String) {
            String str = (String) obj2;
            if (str.length() == 0) {
                return null;
            }
            JSONScanner jSONScanner = new JSONScanner(str);
            try {
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    j = jSONScanner.getCalendar().getTimeInMillis();
                } else {
                    if (str.length() == 29) {
                        String dateFomartPattern = defaultJSONParser.getDateFomartPattern();
                        if (dateFomartPattern.length() != 29 && dateFomartPattern == JSON.DEFFAULT_DATE_FORMAT) {
                            return (T) Timestamp.valueOf(str);
                        }
                    }
                    try {
                        return (T) new Timestamp(defaultJSONParser.getDateFormat().parse(str).getTime());
                    } catch (ParseException unused) {
                        j = Long.parseLong(str);
                    }
                }
                jSONScanner.close();
                return (T) new Timestamp(j);
            } finally {
                jSONScanner.close();
            }
        } else {
            throw new JSONException("parse error");
        }
    }
}
