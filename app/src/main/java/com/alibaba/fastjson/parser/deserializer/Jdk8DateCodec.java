package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.TimeZone;
import okhttp3.internal.connection.RealConnection;

/* loaded from: classes.dex */
public class Jdk8DateCodec extends ContextObjectDeserializer implements ObjectDeserializer, ContextObjectSerializer, ObjectSerializer {
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final Jdk8DateCodec instance = new Jdk8DateCodec();
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(defaultPatttern);
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormatter.ofPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormatter.ofPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormatter.ofPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormatter.ofPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormatter.ofPattern(defaultPatttern).withZone(ZoneId.systemDefault());
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormatter.ofPattern(formatter_iso8601_pattern);

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        DateTimeFormatter dateTimeFormatter;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        } else if (jSONLexer.token() == 4) {
            String stringVal = jSONLexer.stringVal();
            jSONLexer.nextToken();
            if (str == null) {
                dateTimeFormatter = null;
            } else if (defaultPatttern.equals(str)) {
                dateTimeFormatter = defaultFormatter;
            } else {
                dateTimeFormatter = DateTimeFormatter.ofPattern(str);
            }
            if ("".equals(stringVal)) {
                return null;
            }
            if (type == LocalDateTime.class) {
                if (stringVal.length() == 10 || stringVal.length() == 8) {
                    return (T) LocalDateTime.of(parseLocalDate(stringVal, str, dateTimeFormatter), LocalTime.MIN);
                }
                return (T) parseDateTime(stringVal, dateTimeFormatter);
            } else if (type != LocalDate.class) {
                boolean z = true;
                if (type == LocalTime.class) {
                    if (stringVal.length() == 23) {
                        LocalDateTime parse = LocalDateTime.parse(stringVal);
                        return (T) LocalTime.of(parse.getHour(), parse.getMinute(), parse.getSecond(), parse.getNano());
                    }
                    for (int i2 = 0; i2 < stringVal.length(); i2++) {
                        char charAt = stringVal.charAt(i2);
                        if (charAt < '0' || charAt > '9') {
                            z = false;
                            break;
                        }
                    }
                    if (!z || stringVal.length() <= 8 || stringVal.length() >= 19) {
                        return (T) LocalTime.parse(stringVal);
                    }
                    return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(stringVal)), JSON.defaultTimeZone.toZoneId()).toLocalTime();
                } else if (type == ZonedDateTime.class) {
                    if (dateTimeFormatter == defaultFormatter) {
                        dateTimeFormatter = ISO_FIXED_FORMAT;
                    }
                    if (dateTimeFormatter == null && stringVal.length() <= 19) {
                        JSONScanner jSONScanner = new JSONScanner(stringVal);
                        TimeZone timeZone = defaultJSONParser.lexer.getTimeZone();
                        jSONScanner.setTimeZone(timeZone);
                        if (jSONScanner.scanISO8601DateIfMatch(false)) {
                            return (T) ZonedDateTime.ofInstant(jSONScanner.getCalendar().getTime().toInstant(), timeZone.toZoneId());
                        }
                    }
                    return (T) parseZonedDateTime(stringVal, dateTimeFormatter);
                } else if (type == OffsetDateTime.class) {
                    return (T) OffsetDateTime.parse(stringVal);
                } else {
                    if (type == OffsetTime.class) {
                        return (T) OffsetTime.parse(stringVal);
                    }
                    if (type == ZoneId.class) {
                        return (T) ZoneId.of(stringVal);
                    }
                    if (type == Period.class) {
                        return (T) Period.parse(stringVal);
                    }
                    if (type == Duration.class) {
                        return (T) Duration.parse(stringVal);
                    }
                    if (type != Instant.class) {
                        return null;
                    }
                    for (int i3 = 0; i3 < stringVal.length(); i3++) {
                        char charAt2 = stringVal.charAt(i3);
                        if (charAt2 < '0' || charAt2 > '9') {
                            z = false;
                            break;
                        }
                    }
                    if (!z || stringVal.length() <= 8 || stringVal.length() >= 19) {
                        return (T) Instant.parse(stringVal);
                    }
                    return (T) Instant.ofEpochMilli(Long.parseLong(stringVal));
                }
            } else if (stringVal.length() != 23) {
                return (T) parseLocalDate(stringVal, str, dateTimeFormatter);
            } else {
                LocalDateTime parse2 = LocalDateTime.parse(stringVal);
                return (T) LocalDate.of(parse2.getYear(), parse2.getMonthValue(), parse2.getDayOfMonth());
            }
        } else if (jSONLexer.token() == 2) {
            long longValue = jSONLexer.longValue();
            jSONLexer.nextToken();
            if ("unixtime".equals(str)) {
                longValue *= 1000;
            } else if ("yyyyMMddHHmmss".equals(str)) {
                int i4 = (int) (longValue / RealConnection.IDLE_CONNECTION_HEALTHY_NS);
                int i5 = (int) ((longValue / 100000000) % 100);
                int i6 = (int) ((longValue / 1000000) % 100);
                int i7 = (int) ((longValue / 10000) % 100);
                int i8 = (int) ((longValue / 100) % 100);
                int i9 = (int) (longValue % 100);
                if (type == LocalDateTime.class) {
                    return (T) LocalDateTime.of(i4, i5, i6, i7, i8, i9);
                }
            }
            if (type == LocalDateTime.class) {
                return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue), JSON.defaultTimeZone.toZoneId());
            }
            if (type == LocalDate.class) {
                return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue), JSON.defaultTimeZone.toZoneId()).toLocalDate();
            }
            if (type == LocalTime.class) {
                return (T) LocalDateTime.ofInstant(Instant.ofEpochMilli(longValue), JSON.defaultTimeZone.toZoneId()).toLocalTime();
            }
            if (type == ZonedDateTime.class) {
                return (T) ZonedDateTime.ofInstant(Instant.ofEpochMilli(longValue), JSON.defaultTimeZone.toZoneId());
            }
            if (type == Instant.class) {
                return (T) Instant.ofEpochMilli(longValue);
            }
            throw new UnsupportedOperationException();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected LocalDateTime parseDateTime(String str, DateTimeFormatter dateTimeFormatter) {
        DateTimeFormatter dateTimeFormatter2;
        boolean z = true;
        if (dateTimeFormatter == null) {
            if (str.length() == 19) {
                char charAt = str.charAt(4);
                char charAt2 = str.charAt(7);
                char charAt3 = str.charAt(10);
                char charAt4 = str.charAt(13);
                char charAt5 = str.charAt(16);
                if (charAt4 == ':' && charAt5 == ':') {
                    if (charAt == '-' && charAt2 == '-') {
                        if (charAt3 == 'T') {
                            dateTimeFormatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        } else if (charAt3 == ' ') {
                            dateTimeFormatter2 = defaultFormatter;
                        }
                    } else if (charAt == '/' && charAt2 == '/') {
                        dateTimeFormatter2 = formatter_dt19_tw;
                    } else {
                        char charAt6 = str.charAt(0);
                        char charAt7 = str.charAt(1);
                        char charAt8 = str.charAt(2);
                        char charAt9 = str.charAt(3);
                        char charAt10 = str.charAt(5);
                        if (charAt8 == '/' && charAt10 == '/') {
                            int i = ((charAt9 - '0') * 10) + (charAt - '0');
                            if (((charAt6 - '0') * 10) + (charAt7 - '0') > 12) {
                                dateTimeFormatter2 = formatter_dt19_eur;
                            } else if (i > 12) {
                                dateTimeFormatter2 = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    dateTimeFormatter2 = formatter_dt19_us;
                                } else {
                                    dateTimeFormatter2 = (country.equals("BR") || country.equals("AU")) ? formatter_dt19_eur : dateTimeFormatter;
                                }
                            }
                        } else if (charAt8 == '.' && charAt10 == '.') {
                            dateTimeFormatter2 = formatter_dt19_de;
                        } else if (charAt8 == '-' && charAt10 == '-') {
                            dateTimeFormatter2 = formatter_dt19_in;
                        }
                    }
                }
                dateTimeFormatter2 = dateTimeFormatter;
            } else {
                if (str.length() == 23) {
                    char charAt11 = str.charAt(4);
                    char charAt12 = str.charAt(7);
                    char charAt13 = str.charAt(10);
                    char charAt14 = str.charAt(13);
                    char charAt15 = str.charAt(16);
                    char charAt16 = str.charAt(19);
                    if (charAt14 == ':' && charAt15 == ':' && charAt11 == '-' && charAt12 == '-' && charAt13 == ' ' && charAt16 == '.') {
                        dateTimeFormatter2 = defaultFormatter_23;
                    }
                }
                dateTimeFormatter2 = dateTimeFormatter;
            }
            if (str.length() >= 17) {
                char charAt17 = str.charAt(4);
                if (charAt17 == 24180) {
                    if (str.charAt(str.length() - 1) == 31186) {
                        dateTimeFormatter2 = formatter_dt19_cn_1;
                    } else {
                        dateTimeFormatter2 = formatter_dt19_cn;
                    }
                } else if (charAt17 == 45380) {
                    dateTimeFormatter2 = formatter_dt19_kr;
                }
            }
        } else {
            dateTimeFormatter2 = dateTimeFormatter;
        }
        if (dateTimeFormatter2 == null) {
            JSONScanner jSONScanner = new JSONScanner(str);
            if (jSONScanner.scanISO8601DateIfMatch(false)) {
                return LocalDateTime.ofInstant(jSONScanner.getCalendar().toInstant(), ZoneId.systemDefault());
            }
            for (int i2 = 0; i2 < str.length(); i2++) {
                char charAt18 = str.charAt(i2);
                if (charAt18 < '0' || charAt18 > '9') {
                    z = false;
                    break;
                }
            }
            if (z && str.length() > 8 && str.length() < 19) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(str)), JSON.defaultTimeZone.toZoneId());
            }
        }
        if (dateTimeFormatter2 == null) {
            return LocalDateTime.parse(str);
        }
        return LocalDateTime.parse(str, dateTimeFormatter2);
    }

    protected LocalDate parseLocalDate(String str, String str2, DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter == null) {
            if (str.length() == 8) {
                dateTimeFormatter = formatter_d8;
            }
            boolean z = false;
            if (str.length() == 10) {
                char charAt = str.charAt(4);
                char charAt2 = str.charAt(7);
                if (charAt == '/' && charAt2 == '/') {
                    dateTimeFormatter = formatter_d10_tw;
                }
                char charAt3 = str.charAt(0);
                char charAt4 = str.charAt(1);
                char charAt5 = str.charAt(2);
                char charAt6 = str.charAt(3);
                char charAt7 = str.charAt(5);
                if (charAt5 == '/' && charAt7 == '/') {
                    int i = ((charAt6 - '0') * 10) + (charAt - '0');
                    if (((charAt3 - '0') * 10) + (charAt4 - '0') > 12) {
                        dateTimeFormatter = formatter_d10_eur;
                    } else if (i > 12) {
                        dateTimeFormatter = formatter_d10_us;
                    } else {
                        String country = Locale.getDefault().getCountry();
                        if (country.equals("US")) {
                            dateTimeFormatter = formatter_d10_us;
                        } else if (country.equals("BR") || country.equals("AU")) {
                            dateTimeFormatter = formatter_d10_eur;
                        }
                    }
                } else if (charAt5 == '.' && charAt7 == '.') {
                    dateTimeFormatter = formatter_d10_de;
                } else if (charAt5 == '-' && charAt7 == '-') {
                    dateTimeFormatter = formatter_d10_in;
                }
            }
            if (str.length() >= 9) {
                char charAt8 = str.charAt(4);
                if (charAt8 == 24180) {
                    dateTimeFormatter = formatter_d10_cn;
                } else if (charAt8 == 45380) {
                    dateTimeFormatter = formatter_d10_kr;
                }
            }
            int i2 = 0;
            while (true) {
                if (i2 >= str.length()) {
                    z = true;
                    break;
                }
                char charAt9 = str.charAt(i2);
                if (charAt9 < '0' || charAt9 > '9') {
                    break;
                }
                i2++;
            }
            if (z && str.length() > 8 && str.length() < 19) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(str)), JSON.defaultTimeZone.toZoneId()).toLocalDate();
            }
        }
        if (dateTimeFormatter == null) {
            return LocalDate.parse(str);
        }
        return LocalDate.parse(str, dateTimeFormatter);
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00fc A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.time.ZonedDateTime parseZonedDateTime(java.lang.String r16, java.time.format.DateTimeFormatter r17) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec.parseZonedDateTime(java.lang.String, java.time.format.DateTimeFormatter):java.time.ZonedDateTime");
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        if (type == null) {
            type = obj.getClass();
        }
        if (type == LocalDateTime.class) {
            int mask = SerializerFeature.UseISO8601DateFormat.getMask();
            LocalDateTime localDateTime = (LocalDateTime) obj;
            String dateFormatPattern = jSONSerializer.getDateFormatPattern();
            if (dateFormatPattern == null) {
                if ((mask & i) != 0 || jSONSerializer.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
                    dateFormatPattern = formatter_iso8601_pattern;
                } else {
                    int nano = localDateTime.getNano();
                    if (nano == 0) {
                        dateFormatPattern = formatter_iso8601_pattern;
                    } else {
                        dateFormatPattern = nano % 1000000 == 0 ? formatter_iso8601_pattern_23 : formatter_iso8601_pattern_29;
                    }
                }
            }
            if (dateFormatPattern != null) {
                write(serializeWriter, localDateTime, dateFormatPattern);
            } else if (serializeWriter.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
                write(serializeWriter, localDateTime, JSON.DEFFAULT_DATE_FORMAT);
            } else {
                serializeWriter.writeLong(localDateTime.atZone(JSON.defaultTimeZone.toZoneId()).toInstant().toEpochMilli());
            }
        } else {
            serializeWriter.writeString(obj.toString());
        }
    }

    @Override // com.alibaba.fastjson.serializer.ContextObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, BeanContext beanContext) throws IOException {
        write(jSONSerializer.out, (TemporalAccessor) obj, beanContext.getFormat());
    }

    private void write(SerializeWriter serializeWriter, TemporalAccessor temporalAccessor, String str) {
        DateTimeFormatter dateTimeFormatter;
        if ("unixtime".equals(str)) {
            if (temporalAccessor instanceof ChronoZonedDateTime) {
                serializeWriter.writeInt((int) ((ChronoZonedDateTime) temporalAccessor).toEpochSecond());
                return;
            } else if (temporalAccessor instanceof LocalDateTime) {
                serializeWriter.writeInt((int) ((LocalDateTime) temporalAccessor).atZone(JSON.defaultTimeZone.toZoneId()).toEpochSecond());
                return;
            }
        }
        if ("millis".equals(str)) {
            Instant instant = null;
            if (temporalAccessor instanceof ChronoZonedDateTime) {
                instant = ((ChronoZonedDateTime) temporalAccessor).toInstant();
            } else if (temporalAccessor instanceof LocalDateTime) {
                instant = ((LocalDateTime) temporalAccessor).atZone(JSON.defaultTimeZone.toZoneId()).toInstant();
            }
            if (instant != null) {
                serializeWriter.writeLong(instant.toEpochMilli());
                return;
            }
        }
        if (str == formatter_iso8601_pattern) {
            dateTimeFormatter = formatter_iso8601;
        } else {
            dateTimeFormatter = DateTimeFormatter.ofPattern(str);
        }
        serializeWriter.writeString(dateTimeFormatter.format(temporalAccessor));
    }
}
