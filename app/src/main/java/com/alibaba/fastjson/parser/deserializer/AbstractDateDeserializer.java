package com.alibaba.fastjson.parser.deserializer;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes.dex */
public abstract class AbstractDateDeserializer extends ContextObjectDeserializer implements ObjectDeserializer {
    protected abstract <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2);

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        SimpleDateFormat simpleDateFormat;
        Date date;
        Date date2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        Object obj2 = null;
        if (jSONLexer.token() == 2) {
            long longValue = jSONLexer.longValue();
            jSONLexer.nextToken(16);
            if ("unixtime".equals(str)) {
                longValue *= 1000;
            }
            obj2 = Long.valueOf(longValue);
        } else if (jSONLexer.token() == 4) {
            String stringVal = jSONLexer.stringVal();
            if (str != null) {
                if ("yyyy-MM-dd HH:mm:ss.SSSSSSSSS".equals(str) && (type instanceof Class) && ((Class) type).getName().equals("java.sql.Timestamp")) {
                    return (T) TypeUtils.castToTimestamp(stringVal);
                }
                try {
                    simpleDateFormat = new SimpleDateFormat(str, defaultJSONParser.lexer.getLocale());
                } catch (IllegalArgumentException e) {
                    if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                        try {
                            simpleDateFormat = new SimpleDateFormat(str.replaceAll(ExifInterface.GPS_DIRECTION_TRUE, "'T'"), defaultJSONParser.lexer.getLocale());
                        } catch (IllegalArgumentException unused) {
                            throw e;
                        }
                    } else {
                        simpleDateFormat = null;
                    }
                }
                if (JSON.defaultTimeZone != null) {
                    simpleDateFormat.setTimeZone(defaultJSONParser.lexer.getTimeZone());
                }
                try {
                    date = simpleDateFormat.parse(stringVal);
                } catch (ParseException unused2) {
                    date = null;
                }
                if (date == null && JSON.defaultLocale == Locale.CHINA) {
                    try {
                        simpleDateFormat = new SimpleDateFormat(str, Locale.US);
                    } catch (IllegalArgumentException e2) {
                        if (str.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                            try {
                                simpleDateFormat = new SimpleDateFormat(str.replaceAll(ExifInterface.GPS_DIRECTION_TRUE, "'T'"), defaultJSONParser.lexer.getLocale());
                            } catch (IllegalArgumentException unused3) {
                                throw e2;
                            }
                        }
                    }
                    simpleDateFormat.setTimeZone(defaultJSONParser.lexer.getTimeZone());
                    try {
                        date2 = simpleDateFormat.parse(stringVal);
                    } catch (ParseException unused4) {
                        date2 = null;
                    }
                } else {
                    date2 = date;
                }
                if (date2 != null) {
                    obj2 = date2;
                } else if (str.equals("yyyy-MM-dd'T'HH:mm:ss.SSS") && stringVal.length() == 19) {
                    try {
                        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", JSON.defaultLocale);
                        simpleDateFormat2.setTimeZone(JSON.defaultTimeZone);
                        obj2 = simpleDateFormat2.parse(stringVal);
                    } catch (ParseException unused5) {
                    }
                }
            }
            if (obj2 == null) {
                jSONLexer.nextToken(16);
                if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner jSONScanner = new JSONScanner(stringVal);
                    obj2 = stringVal;
                    if (jSONScanner.scanISO8601DateIfMatch()) {
                        obj2 = jSONScanner.getCalendar().getTime();
                    }
                    jSONScanner.close();
                } else {
                    obj2 = stringVal;
                }
            }
        } else if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
        } else if (jSONLexer.token() == 12) {
            jSONLexer.nextToken();
            if (jSONLexer.token() == 4) {
                if (JSON.DEFAULT_TYPE_KEY.equals(jSONLexer.stringVal())) {
                    jSONLexer.nextToken();
                    defaultJSONParser.accept(17);
                    Class<?> checkAutoType = defaultJSONParser.getConfig().checkAutoType(jSONLexer.stringVal(), null, jSONLexer.getFeatures());
                    if (checkAutoType != null) {
                        type = checkAutoType;
                    }
                    defaultJSONParser.accept(4);
                    defaultJSONParser.accept(16);
                }
                jSONLexer.nextTokenWithColon(2);
                if (jSONLexer.token() == 2) {
                    long longValue2 = jSONLexer.longValue();
                    jSONLexer.nextToken();
                    obj2 = Long.valueOf(longValue2);
                    defaultJSONParser.accept(13);
                } else {
                    throw new JSONException("syntax error : " + jSONLexer.tokenName());
                }
            } else {
                throw new JSONException("syntax error");
            }
        } else if (defaultJSONParser.getResolveStatus() == 2) {
            defaultJSONParser.setResolveStatus(0);
            defaultJSONParser.accept(16);
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            } else if ("val".equals(jSONLexer.stringVal())) {
                jSONLexer.nextToken();
                defaultJSONParser.accept(17);
                obj2 = defaultJSONParser.parse();
                defaultJSONParser.accept(13);
            } else {
                throw new JSONException("syntax error");
            }
        } else {
            obj2 = defaultJSONParser.parse();
        }
        return (T) cast(defaultJSONParser, type, obj, obj2);
    }
}
