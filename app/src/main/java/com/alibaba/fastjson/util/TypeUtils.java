package com.alibaba.fastjson.util;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.umeng.analytics.pro.ai;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class TypeUtils {
    public static boolean compatibleWithFieldName;
    public static boolean compatibleWithJavaBean;
    private static volatile Map<Class, String[]> kotlinIgnores;
    private static volatile boolean kotlinIgnores_error;
    private static volatile boolean kotlin_class_klass_error;
    private static volatile boolean kotlin_error;
    private static volatile Constructor kotlin_kclass_constructor;
    private static volatile Method kotlin_kclass_getConstructors;
    private static volatile Method kotlin_kfunction_getParameters;
    private static volatile Method kotlin_kparameter_getName;
    private static volatile Class kotlin_metadata;
    private static volatile boolean kotlin_metadata_error;
    private static Class<?> optionalClass;
    private static Method oracleDateMethod;
    private static Method oracleTimestampMethod;
    private static Class<?> pathClass;
    private static Class<? extends Annotation> transientClass;
    private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");
    private static boolean setAccessibleEnable = true;
    private static boolean oracleTimestampMethodInited = false;
    private static boolean oracleDateMethodInited = false;
    private static boolean optionalClassInited = false;
    private static boolean transientClassInited = false;
    private static Class<? extends Annotation> class_OneToMany = null;
    private static boolean class_OneToMany_error = false;
    private static Class<? extends Annotation> class_ManyToMany = null;
    private static boolean class_ManyToMany_error = false;
    private static Method method_HibernateIsInitialized = null;
    private static boolean method_HibernateIsInitialized_error = false;
    private static ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap(256, 0.75f, 1);
    private static boolean pathClass_error = false;
    private static Class<? extends Annotation> class_JacksonCreator = null;
    private static boolean class_JacksonCreator_error = false;
    private static volatile Class class_Clob = null;
    private static volatile boolean class_Clob_error = false;
    private static volatile Class class_XmlAccessType = null;
    private static volatile Class class_XmlAccessorType = null;
    private static volatile boolean classXmlAccessorType_error = false;
    private static volatile Method method_XmlAccessorType_value = null;
    private static volatile Field field_XmlAccessType_FIELD = null;
    private static volatile Object field_XmlAccessType_FIELD_VALUE = null;

    static int num(char c, char c2) {
        if (c < '0' || c > '9' || c2 < '0' || c2 > '9') {
            return -1;
        }
        return ((c - '0') * 10) + (c2 - '0');
    }

    static int num(char c, char c2, char c3, char c4) {
        if (c < '0' || c > '9' || c2 < '0' || c2 > '9' || c3 < '0' || c3 > '9' || c4 < '0' || c4 > '9') {
            return -1;
        }
        return ((c - '0') * 1000) + ((c2 - '0') * 100) + ((c3 - '0') * 10) + (c4 - '0');
    }

    static int num(char c, char c2, char c3, char c4, char c5, char c6, char c7, char c8, char c9) {
        if (c < '0' || c > '9' || c2 < '0' || c2 > '9' || c3 < '0' || c3 > '9' || c4 < '0' || c4 > '9' || c5 < '0' || c5 > '9' || c6 < '0' || c6 > '9' || c7 < '0' || c7 > '9' || c8 < '0' || c8 > '9' || c9 < '0' || c9 > '9') {
            return -1;
        }
        return ((c - '0') * 100000000) + ((c2 - '0') * 10000000) + ((c3 - '0') * 1000000) + ((c4 - '0') * 100000) + ((c5 - '0') * 10000) + ((c6 - '0') * 1000) + ((c7 - '0') * 100) + ((c8 - '0') * 10) + (c9 - '0');
    }

    static {
        compatibleWithJavaBean = false;
        compatibleWithFieldName = false;
        try {
            compatibleWithJavaBean = "true".equals(IOUtils.getStringProperty(IOUtils.FASTJSON_COMPATIBLEWITHJAVABEAN));
            compatibleWithFieldName = "true".equals(IOUtils.getStringProperty(IOUtils.FASTJSON_COMPATIBLEWITHFIELDNAME));
        } catch (Throwable unused) {
        }
        addBaseClassMappings();
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0052 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isXmlField(java.lang.Class r5) {
        /*
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.class_XmlAccessorType
            r1 = 1
            if (r0 != 0) goto L_0x0014
            boolean r0 = com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error
            if (r0 != 0) goto L_0x0014
            java.lang.String r0 = "javax.xml.bind.annotation.XmlAccessorType"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch: Throwable -> 0x0012
            com.alibaba.fastjson.util.TypeUtils.class_XmlAccessorType = r0     // Catch: Throwable -> 0x0012
            goto L_0x0014
        L_0x0012:
            com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error = r1
        L_0x0014:
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.class_XmlAccessorType
            r2 = 0
            if (r0 != 0) goto L_0x001a
            return r2
        L_0x001a:
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.class_XmlAccessorType
            java.lang.annotation.Annotation r5 = getAnnotation(r5, r0)
            if (r5 != 0) goto L_0x0023
            return r2
        L_0x0023:
            java.lang.reflect.Method r0 = com.alibaba.fastjson.util.TypeUtils.method_XmlAccessorType_value
            if (r0 != 0) goto L_0x003a
            boolean r0 = com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error
            if (r0 != 0) goto L_0x003a
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.class_XmlAccessorType     // Catch: Throwable -> 0x0038
            java.lang.String r3 = "value"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch: Throwable -> 0x0038
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch: Throwable -> 0x0038
            com.alibaba.fastjson.util.TypeUtils.method_XmlAccessorType_value = r0     // Catch: Throwable -> 0x0038
            goto L_0x003a
        L_0x0038:
            com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error = r1
        L_0x003a:
            java.lang.reflect.Method r0 = com.alibaba.fastjson.util.TypeUtils.method_XmlAccessorType_value
            if (r0 != 0) goto L_0x003f
            return r2
        L_0x003f:
            boolean r0 = com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error
            r3 = 0
            if (r0 != 0) goto L_0x004f
            java.lang.reflect.Method r0 = com.alibaba.fastjson.util.TypeUtils.method_XmlAccessorType_value     // Catch: Throwable -> 0x004d
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: Throwable -> 0x004d
            java.lang.Object r5 = r0.invoke(r5, r4)     // Catch: Throwable -> 0x004d
            goto L_0x0050
        L_0x004d:
            com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error = r1
        L_0x004f:
            r5 = r3
        L_0x0050:
            if (r5 != 0) goto L_0x0053
            return r2
        L_0x0053:
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.class_XmlAccessType
            if (r0 != 0) goto L_0x0078
            boolean r0 = com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error
            if (r0 != 0) goto L_0x0078
            java.lang.String r0 = "javax.xml.bind.annotation.XmlAccessType"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch: Throwable -> 0x0076
            com.alibaba.fastjson.util.TypeUtils.class_XmlAccessType = r0     // Catch: Throwable -> 0x0076
            java.lang.Class r0 = com.alibaba.fastjson.util.TypeUtils.class_XmlAccessType     // Catch: Throwable -> 0x0076
            java.lang.String r4 = "FIELD"
            java.lang.reflect.Field r0 = r0.getField(r4)     // Catch: Throwable -> 0x0076
            com.alibaba.fastjson.util.TypeUtils.field_XmlAccessType_FIELD = r0     // Catch: Throwable -> 0x0076
            java.lang.reflect.Field r0 = com.alibaba.fastjson.util.TypeUtils.field_XmlAccessType_FIELD     // Catch: Throwable -> 0x0076
            java.lang.Object r0 = r0.get(r3)     // Catch: Throwable -> 0x0076
            com.alibaba.fastjson.util.TypeUtils.field_XmlAccessType_FIELD_VALUE = r0     // Catch: Throwable -> 0x0076
            goto L_0x0078
        L_0x0076:
            com.alibaba.fastjson.util.TypeUtils.classXmlAccessorType_error = r1
        L_0x0078:
            java.lang.Object r0 = com.alibaba.fastjson.util.TypeUtils.field_XmlAccessType_FIELD_VALUE
            if (r5 != r0) goto L_0x007d
            goto L_0x007e
        L_0x007d:
            r1 = r2
        L_0x007e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.isXmlField(java.lang.Class):boolean");
    }

    public static Annotation getXmlAccessorType(Class cls) {
        if (class_XmlAccessorType == null && !classXmlAccessorType_error) {
            try {
                class_XmlAccessorType = Class.forName("javax.xml.bind.annotation.XmlAccessorType");
            } catch (Throwable unused) {
                classXmlAccessorType_error = true;
            }
        }
        if (class_XmlAccessorType == null) {
            return null;
        }
        return getAnnotation(cls, class_XmlAccessorType);
    }

    public static boolean isClob(Class cls) {
        if (class_Clob == null && !class_Clob_error) {
            try {
                class_Clob = Class.forName("java.sql.Clob");
            } catch (Throwable unused) {
                class_Clob_error = true;
            }
        }
        if (class_Clob == null) {
            return false;
        }
        return class_Clob.isAssignableFrom(cls);
    }

    public static String castToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static Byte castToByte(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return Byte.valueOf(byteValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            return Byte.valueOf(((Number) obj).byteValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            return Byte.valueOf(Byte.parseByte(str));
        }
        throw new JSONException("can not cast to byte, value : " + obj);
    }

    public static Character castToChar(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Character) {
            return (Character) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0) {
                return null;
            }
            if (str.length() == 1) {
                return Character.valueOf(str.charAt(0));
            }
            throw new JSONException("can not cast to char, value : " + obj);
        }
        throw new JSONException("can not cast to char, value : " + obj);
    }

    public static Short castToShort(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return Short.valueOf(shortValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            return Short.valueOf(((Number) obj).shortValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            return Short.valueOf(Short.parseShort(str));
        }
        throw new JSONException("can not cast to short, value : " + obj);
    }

    public static BigDecimal castToBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof BigInteger) {
            return new BigDecimal((BigInteger) obj);
        }
        String obj2 = obj.toString();
        if (obj2.length() == 0) {
            return null;
        }
        if (!(obj instanceof Map) || ((Map) obj).size() != 0) {
            return new BigDecimal(obj2);
        }
        return null;
    }

    public static BigInteger castToBigInteger(Object obj) {
        BigDecimal bigDecimal;
        int scale;
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigInteger) {
            return (BigInteger) obj;
        }
        if ((obj instanceof Float) || (obj instanceof Double)) {
            return BigInteger.valueOf(((Number) obj).longValue());
        }
        if ((obj instanceof BigDecimal) && (scale = (bigDecimal = (BigDecimal) obj).scale()) > -1000 && scale < 1000) {
            return bigDecimal.toBigInteger();
        }
        String obj2 = obj.toString();
        if (obj2.length() == 0 || "null".equals(obj2) || "NULL".equals(obj2)) {
            return null;
        }
        return new BigInteger(obj2);
    }

    public static Float castToFloat(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return Float.valueOf(((Number) obj).floatValue());
        }
        if (obj instanceof String) {
            String obj2 = obj.toString();
            if (obj2.length() == 0 || "null".equals(obj2) || "NULL".equals(obj2)) {
                return null;
            }
            if (obj2.indexOf(44) != -1) {
                obj2 = obj2.replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SP, "");
            }
            return Float.valueOf(Float.parseFloat(obj2));
        }
        throw new JSONException("can not cast to float, value : " + obj);
    }

    public static Double castToDouble(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return Double.valueOf(((Number) obj).doubleValue());
        }
        if (obj instanceof String) {
            String obj2 = obj.toString();
            if (obj2.length() == 0 || "null".equals(obj2) || "NULL".equals(obj2)) {
                return null;
            }
            if (obj2.indexOf(44) != -1) {
                obj2 = obj2.replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SP, "");
            }
            return Double.valueOf(Double.parseDouble(obj2));
        }
        throw new JSONException("can not cast to double, value : " + obj);
    }

    public static Date castToDate(Object obj) {
        return castToDate(obj, null);
    }

    public static Date castToDate(Object obj, String str) {
        long j;
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Calendar) {
            return ((Calendar) obj).getTime();
        }
        if (obj instanceof BigDecimal) {
            return new Date(longValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            long longValue = ((Number) obj).longValue();
            if ("unixtime".equals(str)) {
                longValue *= 1000;
            }
            return new Date(longValue);
        }
        if (obj instanceof String) {
            String str2 = (String) obj;
            JSONScanner jSONScanner = new JSONScanner(str2);
            try {
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    return jSONScanner.getCalendar().getTime();
                }
                jSONScanner.close();
                if (str2.startsWith("/Date(") && str2.endsWith(")/")) {
                    str2 = str2.substring(6, str2.length() - 2);
                }
                if (str2.indexOf(45) > 0 || str2.indexOf(43) > 0) {
                    if (str == null) {
                        if (str2.length() == JSON.DEFFAULT_DATE_FORMAT.length() || (str2.length() == 22 && JSON.DEFFAULT_DATE_FORMAT.equals("yyyyMMddHHmmssSSSZ"))) {
                            str = JSON.DEFFAULT_DATE_FORMAT;
                        } else if (str2.length() == 10) {
                            str = XMPassport.SIMPLE_DATE_FORMAT;
                        } else if (str2.length() == 19) {
                            str = "yyyy-MM-dd HH:mm:ss";
                        } else if (str2.length() == 29 && str2.charAt(26) == ':' && str2.charAt(28) == '0') {
                            str = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                        } else {
                            str = (str2.length() == 23 && str2.charAt(19) == ',') ? "yyyy-MM-dd HH:mm:ss,SSS" : "yyyy-MM-dd HH:mm:ss.SSS";
                        }
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, JSON.defaultLocale);
                    simpleDateFormat.setTimeZone(JSON.defaultTimeZone);
                    try {
                        return simpleDateFormat.parse(str2);
                    } catch (ParseException unused) {
                        throw new JSONException("can not cast to Date, value : " + str2);
                    }
                } else if (str2.length() == 0) {
                    return null;
                } else {
                    j = Long.parseLong(str2);
                }
            } finally {
                jSONScanner.close();
            }
        } else {
            j = -1;
        }
        if (j != -1) {
            return new Date(j);
        }
        Class<?> cls = obj.getClass();
        if ("oracle.sql.TIMESTAMP".equals(cls.getName())) {
            if (oracleTimestampMethod == null && !oracleTimestampMethodInited) {
                try {
                    oracleTimestampMethod = cls.getMethod("toJdbc", new Class[0]);
                } catch (NoSuchMethodException unused2) {
                } catch (Throwable th) {
                    oracleTimestampMethodInited = true;
                    throw th;
                }
                oracleTimestampMethodInited = true;
            }
            try {
                return (Date) oracleTimestampMethod.invoke(obj, new Object[0]);
            } catch (Exception e) {
                throw new JSONException("can not cast oracle.sql.TIMESTAMP to Date", e);
            }
        } else if ("oracle.sql.DATE".equals(cls.getName())) {
            if (oracleDateMethod == null && !oracleDateMethodInited) {
                try {
                    oracleDateMethod = cls.getMethod("toJdbc", new Class[0]);
                } catch (NoSuchMethodException unused3) {
                } catch (Throwable th2) {
                    oracleDateMethodInited = true;
                    throw th2;
                }
                oracleDateMethodInited = true;
            }
            try {
                return (Date) oracleDateMethod.invoke(obj, new Object[0]);
            } catch (Exception e2) {
                throw new JSONException("can not cast oracle.sql.DATE to Date", e2);
            }
        } else {
            throw new JSONException("can not cast to Date, value : " + obj);
        }
    }

    public static java.sql.Date castToSqlDate(Object obj) {
        long j;
        if (obj == null) {
            return null;
        }
        if (obj instanceof java.sql.Date) {
            return (java.sql.Date) obj;
        }
        if (obj instanceof Date) {
            return new java.sql.Date(((Date) obj).getTime());
        }
        if (obj instanceof Calendar) {
            return new java.sql.Date(((Calendar) obj).getTimeInMillis());
        }
        if (obj instanceof BigDecimal) {
            j = longValue((BigDecimal) obj);
        } else {
            j = obj instanceof Number ? ((Number) obj).longValue() : 0L;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            if (isNumber(str)) {
                j = Long.parseLong(str);
            } else {
                JSONScanner jSONScanner = new JSONScanner(str);
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    j = jSONScanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + str);
                }
            }
        }
        if (j > 0) {
            return new java.sql.Date(j);
        }
        throw new JSONException("can not cast to Date, value : " + obj);
    }

    public static long longExtractValue(Number number) {
        if (number instanceof BigDecimal) {
            return ((BigDecimal) number).longValueExact();
        }
        return number.longValue();
    }

    public static Time castToSqlTime(Object obj) {
        long j;
        if (obj == null) {
            return null;
        }
        if (obj instanceof Time) {
            return (Time) obj;
        }
        if (obj instanceof Date) {
            return new Time(((Date) obj).getTime());
        }
        if (obj instanceof Calendar) {
            return new Time(((Calendar) obj).getTimeInMillis());
        }
        if (obj instanceof BigDecimal) {
            j = longValue((BigDecimal) obj);
        } else {
            j = obj instanceof Number ? ((Number) obj).longValue() : 0L;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equalsIgnoreCase(str)) {
                return null;
            }
            if (isNumber(str)) {
                j = Long.parseLong(str);
            } else {
                JSONScanner jSONScanner = new JSONScanner(str);
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    j = jSONScanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + str);
                }
            }
        }
        if (j > 0) {
            return new Time(j);
        }
        throw new JSONException("can not cast to Date, value : " + obj);
    }

    public static Timestamp castToTimestamp(Object obj) {
        long j;
        if (obj == null) {
            return null;
        }
        if (obj instanceof Calendar) {
            return new Timestamp(((Calendar) obj).getTimeInMillis());
        }
        if (obj instanceof Timestamp) {
            return (Timestamp) obj;
        }
        if (obj instanceof Date) {
            return new Timestamp(((Date) obj).getTime());
        }
        if (obj instanceof BigDecimal) {
            j = longValue((BigDecimal) obj);
        } else {
            j = obj instanceof Number ? ((Number) obj).longValue() : 0L;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            if (str.endsWith(".000000000")) {
                str = str.substring(0, str.length() - 10);
            } else if (str.endsWith(".000000")) {
                str = str.substring(0, str.length() - 7);
            }
            if (str.length() == 29 && str.charAt(4) == '-' && str.charAt(7) == '-' && str.charAt(10) == ' ' && str.charAt(13) == ':' && str.charAt(16) == ':' && str.charAt(19) == '.') {
                return new Timestamp(num(str.charAt(0), str.charAt(1), str.charAt(2), str.charAt(3)) - 1900, num(str.charAt(5), str.charAt(6)) - 1, num(str.charAt(8), str.charAt(9)), num(str.charAt(11), str.charAt(12)), num(str.charAt(14), str.charAt(15)), num(str.charAt(17), str.charAt(18)), num(str.charAt(20), str.charAt(21), str.charAt(22), str.charAt(23), str.charAt(24), str.charAt(25), str.charAt(26), str.charAt(27), str.charAt(28)));
            } else if (isNumber(str)) {
                j = Long.parseLong(str);
            } else {
                JSONScanner jSONScanner = new JSONScanner(str);
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    j = jSONScanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + str);
                }
            }
        }
        if (j > 0) {
            return new Timestamp(j);
        }
        throw new JSONException("can not cast to Timestamp, value : " + obj);
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '+' || charAt == '-') {
                if (i != 0) {
                    return false;
                }
            } else if (charAt < '0' || charAt > '9') {
                return false;
            }
        }
        return true;
    }

    public static Long castToLong(Object obj) {
        Calendar calendar = null;
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return Long.valueOf(longValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            return Long.valueOf(((Number) obj).longValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            if (str.indexOf(44) != -1) {
                str = str.replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SP, "");
            }
            try {
                return Long.valueOf(Long.parseLong(str));
            } catch (NumberFormatException unused) {
                JSONScanner jSONScanner = new JSONScanner(str);
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    calendar = jSONScanner.getCalendar();
                }
                jSONScanner.close();
                if (calendar != null) {
                    return Long.valueOf(calendar.getTimeInMillis());
                }
            }
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                Iterator it = map.values().iterator();
                it.next();
                return castToLong(it.next());
            }
        }
        throw new JSONException("can not cast to long, value : " + obj);
    }

    public static byte byteValue(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return (byte) 0;
        }
        int scale = bigDecimal.scale();
        if (scale < -100 || scale > 100) {
            return bigDecimal.byteValueExact();
        }
        return bigDecimal.byteValue();
    }

    public static short shortValue(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return (short) 0;
        }
        int scale = bigDecimal.scale();
        if (scale < -100 || scale > 100) {
            return bigDecimal.shortValueExact();
        }
        return bigDecimal.shortValue();
    }

    public static int intValue(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return 0;
        }
        int scale = bigDecimal.scale();
        if (scale < -100 || scale > 100) {
            return bigDecimal.intValueExact();
        }
        return bigDecimal.intValue();
    }

    public static long longValue(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return 0L;
        }
        int scale = bigDecimal.scale();
        if (scale < -100 || scale > 100) {
            return bigDecimal.longValueExact();
        }
        return bigDecimal.longValue();
    }

    public static Integer castToInt(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof BigDecimal) {
            return Integer.valueOf(intValue((BigDecimal) obj));
        }
        if (obj instanceof Number) {
            return Integer.valueOf(((Number) obj).intValue());
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
            if (str.indexOf(44) != -1) {
                str = str.replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SP, "");
            }
            Matcher matcher = NUMBER_WITH_TRAILING_ZEROS_PATTERN.matcher(str);
            if (matcher.find()) {
                str = matcher.replaceAll("");
            }
            return Integer.valueOf(Integer.parseInt(str));
        } else if (obj instanceof Boolean) {
            return Integer.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
        } else {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                    Iterator it = map.values().iterator();
                    it.next();
                    return castToInt(it.next());
                }
            }
            throw new JSONException("can not cast to int, value : " + obj);
        }
    }

    public static byte[] castToBytes(Object obj) {
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        if (obj instanceof String) {
            return IOUtils.decodeBase64((String) obj);
        }
        throw new JSONException("can not cast to byte[], value : " + obj);
    }

    public static Boolean castToBoolean(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        boolean z = false;
        if (obj instanceof BigDecimal) {
            if (intValue((BigDecimal) obj) == 1) {
                z = true;
            }
            return Boolean.valueOf(z);
        } else if (obj instanceof Number) {
            if (((Number) obj).intValue() == 1) {
                z = true;
            }
            return Boolean.valueOf(z);
        } else {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                    return null;
                }
                if ("true".equalsIgnoreCase(str) || "1".equals(str)) {
                    return Boolean.TRUE;
                }
                if ("false".equalsIgnoreCase(str) || "0".equals(str)) {
                    return Boolean.FALSE;
                }
                if ("Y".equalsIgnoreCase(str) || ExifInterface.GPS_DIRECTION_TRUE.equals(str)) {
                    return Boolean.TRUE;
                }
                if (HomePageRecordHelper.AREA_F.equalsIgnoreCase(str) || "N".equals(str)) {
                    return Boolean.FALSE;
                }
            }
            throw new JSONException("can not cast to boolean, value : " + obj);
        }
    }

    public static <T> T castToJavaBean(Object obj, Class<T> cls) {
        return (T) cast(obj, (Class<Object>) cls, ParserConfig.getGlobalInstance());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj, Class<T> cls, ParserConfig parserConfig) {
        T t;
        int i = 0;
        if (obj == 0) {
            if (cls == Integer.TYPE) {
                return (T) 0;
            }
            if (cls == Long.TYPE) {
                return (T) 0L;
            }
            if (cls == Short.TYPE) {
                return (T) (short) 0;
            }
            if (cls == Byte.TYPE) {
                return (T) (byte) 0;
            }
            if (cls == Float.TYPE) {
                return (T) Float.valueOf(0.0f);
            }
            if (cls == Double.TYPE) {
                return (T) Double.valueOf(0.0d);
            }
            if (cls == Boolean.TYPE) {
                return (T) Boolean.FALSE;
            }
            return null;
        } else if (cls == null) {
            throw new IllegalArgumentException("clazz is null");
        } else if (cls == obj.getClass()) {
            return obj;
        } else {
            if (!(obj instanceof Map)) {
                if (cls.isArray()) {
                    if (obj instanceof Collection) {
                        Collection<Object> collection = (Collection) obj;
                        T t2 = (T) Array.newInstance(cls.getComponentType(), collection.size());
                        for (Object obj2 : collection) {
                            Array.set(t2, i, cast(obj2, (Class<Object>) cls.getComponentType(), parserConfig));
                            i++;
                        }
                        return t2;
                    } else if (cls == byte[].class) {
                        return (T) castToBytes(obj);
                    }
                }
                if (cls.isAssignableFrom(obj.getClass())) {
                    return obj;
                }
                if (cls == Boolean.TYPE || cls == Boolean.class) {
                    return (T) castToBoolean(obj);
                }
                if (cls == Byte.TYPE || cls == Byte.class) {
                    return (T) castToByte(obj);
                }
                if (cls == Character.TYPE || cls == Character.class) {
                    return (T) castToChar(obj);
                }
                if (cls == Short.TYPE || cls == Short.class) {
                    return (T) castToShort(obj);
                }
                if (cls == Integer.TYPE || cls == Integer.class) {
                    return (T) castToInt(obj);
                }
                if (cls == Long.TYPE || cls == Long.class) {
                    return (T) castToLong(obj);
                }
                if (cls == Float.TYPE || cls == Float.class) {
                    return (T) castToFloat(obj);
                }
                if (cls == Double.TYPE || cls == Double.class) {
                    return (T) castToDouble(obj);
                }
                if (cls == String.class) {
                    return (T) castToString(obj);
                }
                if (cls == BigDecimal.class) {
                    return (T) castToBigDecimal(obj);
                }
                if (cls == BigInteger.class) {
                    return (T) castToBigInteger(obj);
                }
                if (cls == Date.class) {
                    return (T) castToDate(obj);
                }
                if (cls == java.sql.Date.class) {
                    return (T) castToSqlDate(obj);
                }
                if (cls == Time.class) {
                    return (T) castToSqlTime(obj);
                }
                if (cls == Timestamp.class) {
                    return (T) castToTimestamp(obj);
                }
                if (cls.isEnum()) {
                    return (T) castToEnum(obj, cls, parserConfig);
                }
                if (Calendar.class.isAssignableFrom(cls)) {
                    Date castToDate = castToDate(obj);
                    if (cls == Calendar.class) {
                        t = (T) Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    } else {
                        try {
                            t = (T) ((Calendar) cls.newInstance());
                        } catch (Exception e) {
                            throw new JSONException("can not cast to : " + cls.getName(), e);
                        }
                    }
                    ((Calendar) t).setTime(castToDate);
                    return t;
                }
                String name = cls.getName();
                if (name.equals("javax.xml.datatype.XMLGregorianCalendar")) {
                    Date castToDate2 = castToDate(obj);
                    Calendar instance = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    instance.setTime(castToDate2);
                    return (T) CalendarCodec.instance.createXMLGregorianCalendar(instance);
                }
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                        return null;
                    }
                    if (cls == Currency.class) {
                        return (T) Currency.getInstance(str);
                    }
                    if (cls == Locale.class) {
                        return (T) toLocale(str);
                    }
                    if (name.startsWith("java.time.")) {
                        return (T) JSON.parseObject(JSON.toJSONString(str), cls);
                    }
                }
                if (parserConfig.get(cls) != null) {
                    return (T) JSON.parseObject(JSON.toJSONString(obj), cls);
                }
                throw new JSONException("can not cast to : " + cls.getName());
            } else if (cls == Map.class) {
                return obj;
            } else {
                Map map = (Map) obj;
                return (cls != Object.class || map.containsKey(JSON.DEFAULT_TYPE_KEY)) ? (T) castToJavaBean(map, cls, parserConfig) : obj;
            }
        }
    }

    public static Locale toLocale(String str) {
        String[] split = str.split("_");
        if (split.length == 1) {
            return new Locale(split[0]);
        }
        if (split.length == 2) {
            return new Locale(split[0], split[1]);
        }
        return new Locale(split[0], split[1], split[2]);
    }

    public static <T> T castToEnum(Object obj, Class<T> cls, ParserConfig parserConfig) {
        try {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0) {
                    return null;
                }
                if (parserConfig == null) {
                    parserConfig = ParserConfig.getGlobalInstance();
                }
                ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
                if (deserializer instanceof EnumDeserializer) {
                    return (T) ((EnumDeserializer) deserializer).getEnumByHashCode(fnv1a_64(str));
                }
                return (T) Enum.valueOf(cls, str);
            }
            if (obj instanceof BigDecimal) {
                int intValue = intValue((BigDecimal) obj);
                T[] enumConstants = cls.getEnumConstants();
                if (intValue < enumConstants.length) {
                    return enumConstants[intValue];
                }
            }
            if (obj instanceof Number) {
                int intValue2 = ((Number) obj).intValue();
                T[] enumConstants2 = cls.getEnumConstants();
                if (intValue2 < enumConstants2.length) {
                    return enumConstants2[intValue2];
                }
            }
            throw new JSONException("can not cast to : " + cls.getName());
        } catch (Exception e) {
            throw new JSONException("can not cast to : " + cls.getName(), e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj, Type type, ParserConfig parserConfig) {
        if (obj == 0) {
            return null;
        }
        if (type instanceof Class) {
            return (T) cast(obj, (Class<Object>) type, parserConfig);
        }
        if (type instanceof ParameterizedType) {
            return (T) cast(obj, (ParameterizedType) type, parserConfig);
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
        }
        if (type instanceof TypeVariable) {
            return obj;
        }
        throw new JSONException("can not cast to : " + type);
    }

    /* JADX WARN: Type inference failed for: r6v7, types: [java.util.Map$Entry, T] */
    /* JADX WARN: Type inference failed for: r7v12, types: [java.util.List, T, java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r7v4, types: [T, java.util.Map, java.util.HashMap] */
    /* JADX WARN: Unknown variable types count: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T> T cast(java.lang.Object r6, java.lang.reflect.ParameterizedType r7, com.alibaba.fastjson.parser.ParserConfig r8) {
        /*
            Method dump skipped, instructions count: 423
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.cast(java.lang.Object, java.lang.reflect.ParameterizedType, com.alibaba.fastjson.parser.ParserConfig):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T castToJavaBean(Map<String, Object> map, Class<T> cls, ParserConfig parserConfig) {
        JSONObject jSONObject;
        int i = 0;
        try {
            if (cls == StackTraceElement.class) {
                String str = (String) map.get("className");
                String str2 = (String) map.get("methodName");
                String str3 = (String) map.get("fileName");
                Number number = (Number) map.get("lineNumber");
                if (number != null) {
                    if (number instanceof BigDecimal) {
                        i = ((BigDecimal) number).intValueExact();
                    } else {
                        i = number.intValue();
                    }
                }
                return (T) new StackTraceElement(str, str2, str3, i);
            }
            Object obj = map.get(JSON.DEFAULT_TYPE_KEY);
            JavaBeanDeserializer javaBeanDeserializer = null;
            if (obj instanceof String) {
                String str4 = (String) obj;
                if (parserConfig == null) {
                    parserConfig = ParserConfig.global;
                }
                Class<?> checkAutoType = parserConfig.checkAutoType(str4, null);
                if (checkAutoType == null) {
                    throw new ClassNotFoundException(str4 + " not found");
                } else if (!checkAutoType.equals(cls)) {
                    return (T) castToJavaBean(map, checkAutoType, parserConfig);
                }
            }
            if (cls.isInterface()) {
                if (map instanceof JSONObject) {
                    jSONObject = (JSONObject) map;
                } else {
                    jSONObject = new JSONObject(map);
                }
                if (parserConfig == null) {
                    parserConfig = ParserConfig.getGlobalInstance();
                }
                return parserConfig.get(cls) != null ? (T) JSON.parseObject(JSON.toJSONString(jSONObject), cls) : (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cls}, jSONObject);
            }
            if (cls == Locale.class) {
                Object obj2 = map.get(ai.N);
                Object obj3 = map.get(ai.O);
                if (obj2 instanceof String) {
                    String str5 = (String) obj2;
                    if (obj3 instanceof String) {
                        return (T) new Locale(str5, (String) obj3);
                    }
                    if (obj3 == null) {
                        return (T) new Locale(str5);
                    }
                }
            }
            if (cls == String.class && (map instanceof JSONObject)) {
                return (T) map.toString();
            }
            if (cls == JSON.class && (map instanceof JSONObject)) {
                return map;
            }
            if (cls == LinkedHashMap.class && (map instanceof JSONObject)) {
                T t = (T) ((JSONObject) map).getInnerMap();
                if (t instanceof LinkedHashMap) {
                    return t;
                }
                new LinkedHashMap().putAll(t);
            }
            if (cls.isInstance(map)) {
                return map;
            }
            if (cls == JSONObject.class) {
                return (T) new JSONObject(map);
            }
            if (parserConfig == null) {
                parserConfig = ParserConfig.getGlobalInstance();
            }
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
            }
            if (javaBeanDeserializer != null) {
                return (T) javaBeanDeserializer.createInstance(map, parserConfig);
            }
            throw new JSONException("can not get javaBeanDeserializer. " + cls.getName());
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    private static void addBaseClassMappings() {
        mappings.put("byte", Byte.TYPE);
        mappings.put("short", Short.TYPE);
        mappings.put("int", Integer.TYPE);
        mappings.put("long", Long.TYPE);
        mappings.put("float", Float.TYPE);
        mappings.put("double", Double.TYPE);
        mappings.put("boolean", Boolean.TYPE);
        mappings.put("char", Character.TYPE);
        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);
        mappings.put("[B", byte[].class);
        mappings.put("[S", short[].class);
        mappings.put("[I", int[].class);
        mappings.put("[J", long[].class);
        mappings.put("[F", float[].class);
        mappings.put("[D", double[].class);
        mappings.put("[C", char[].class);
        mappings.put("[Z", boolean[].class);
        Class<?>[] clsArr = {Object.class, Cloneable.class, loadClass("java.lang.AutoCloseable"), Exception.class, RuntimeException.class, IllegalAccessError.class, IllegalAccessException.class, IllegalArgumentException.class, IllegalMonitorStateException.class, IllegalStateException.class, IllegalThreadStateException.class, IndexOutOfBoundsException.class, InstantiationError.class, InstantiationException.class, InternalError.class, InterruptedException.class, LinkageError.class, NegativeArraySizeException.class, NoClassDefFoundError.class, NoSuchFieldError.class, NoSuchFieldException.class, NoSuchMethodError.class, NoSuchMethodException.class, NullPointerException.class, NumberFormatException.class, OutOfMemoryError.class, SecurityException.class, StackOverflowError.class, StringIndexOutOfBoundsException.class, TypeNotPresentException.class, VerifyError.class, StackTraceElement.class, HashMap.class, Hashtable.class, TreeMap.class, IdentityHashMap.class, WeakHashMap.class, LinkedHashMap.class, HashSet.class, LinkedHashSet.class, TreeSet.class, ArrayList.class, TimeUnit.class, ConcurrentHashMap.class, AtomicInteger.class, AtomicLong.class, Collections.EMPTY_MAP.getClass(), Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Number.class, String.class, BigDecimal.class, BigInteger.class, BitSet.class, Calendar.class, Date.class, Locale.class, UUID.class, Time.class, java.sql.Date.class, Timestamp.class, SimpleDateFormat.class, JSONObject.class, JSONPObject.class, JSONArray.class};
        for (Class<?> cls : clsArr) {
            if (cls != null) {
                mappings.put(cls.getName(), cls);
            }
        }
    }

    public static void clearClassMapping() {
        mappings.clear();
        addBaseClassMappings();
    }

    public static void addMapping(String str, Class<?> cls) {
        mappings.put(str, cls);
    }

    public static Class<?> loadClass(String str) {
        return loadClass(str, null);
    }

    public static boolean isPath(Class<?> cls) {
        if (pathClass == null && !pathClass_error) {
            try {
                pathClass = Class.forName("java.nio.file.Path");
            } catch (Throwable unused) {
                pathClass_error = true;
            }
        }
        Class<?> cls2 = pathClass;
        if (cls2 != null) {
            return cls2.isAssignableFrom(cls);
        }
        return false;
    }

    public static Class<?> getClassFromMapping(String str) {
        return mappings.get(str);
    }

    public static Class<?> loadClass(String str, ClassLoader classLoader) {
        return loadClass(str, classLoader, false);
    }

    public static Class<?> loadClass(String str, ClassLoader classLoader, boolean z) {
        if (str == null || str.length() == 0 || str.length() > 128) {
            return null;
        }
        Class<?> cls = mappings.get(str);
        if (cls != null) {
            return cls;
        }
        if (str.charAt(0) == '[') {
            return Array.newInstance(loadClass(str.substring(1), classLoader), 0).getClass();
        }
        if (str.startsWith("L") && str.endsWith(";")) {
            return loadClass(str.substring(1, str.length() - 1), classLoader);
        }
        if (classLoader != null) {
            try {
                cls = classLoader.loadClass(str);
                if (z) {
                    mappings.put(str, cls);
                }
                return cls;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        try {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (!(contextClassLoader == null || contextClassLoader == classLoader)) {
                Class<?> loadClass = contextClassLoader.loadClass(str);
                if (z) {
                    try {
                        mappings.put(str, loadClass);
                    } catch (Throwable unused) {
                        cls = loadClass;
                    }
                }
                return loadClass;
            }
        } catch (Throwable unused2) {
        }
        try {
            cls = Class.forName(str);
            if (z) {
                mappings.put(str, cls);
            }
            return cls;
        } catch (Throwable unused3) {
            return cls;
        }
    }

    public static SerializeBeanInfo buildBeanInfo(Class<?> cls, Map<String, String> map, PropertyNamingStrategy propertyNamingStrategy) {
        return buildBeanInfo(cls, map, propertyNamingStrategy, false);
    }

    public static SerializeBeanInfo buildBeanInfo(Class<?> cls, Map<String, String> map, PropertyNamingStrategy propertyNamingStrategy, boolean z) {
        String str;
        String str2;
        int i;
        String[] strArr;
        PropertyNamingStrategy propertyNamingStrategy2;
        List<FieldInfo> list;
        List<FieldInfo> list2;
        JSONType jSONType = (JSONType) getAnnotation(cls, JSONType.class);
        if (jSONType != null) {
            String[] orders = jSONType.orders();
            str2 = jSONType.typeName();
            if (str2.length() == 0) {
                str2 = null;
            }
            propertyNamingStrategy2 = jSONType.naming();
            if (propertyNamingStrategy2 == PropertyNamingStrategy.CamelCase) {
                propertyNamingStrategy2 = propertyNamingStrategy;
            }
            i = SerializerFeature.of(jSONType.serialzeFeatures());
            str = null;
            for (Class<? super Object> superclass = cls.getSuperclass(); superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                JSONType jSONType2 = (JSONType) getAnnotation(superclass, JSONType.class);
                if (jSONType2 == null) {
                    break;
                }
                str = jSONType2.typeKey();
                if (str.length() != 0) {
                    break;
                }
            }
            for (Class<?> cls2 : cls.getInterfaces()) {
                JSONType jSONType3 = (JSONType) getAnnotation(cls2, JSONType.class);
                if (jSONType3 != null) {
                    str = jSONType3.typeKey();
                    if (str.length() != 0) {
                        break;
                    }
                }
            }
            if (str != null && str.length() == 0) {
                str = null;
            }
            strArr = orders;
        } else {
            propertyNamingStrategy2 = propertyNamingStrategy;
            i = 0;
            strArr = null;
            str2 = null;
            str = null;
        }
        HashMap hashMap = new HashMap();
        ParserConfig.parserAllFieldToCache(cls, hashMap);
        if (z) {
            list = computeGettersWithFieldBase(cls, map, false, propertyNamingStrategy2);
        } else {
            list = computeGetters(cls, jSONType, map, hashMap, false, propertyNamingStrategy2);
        }
        FieldInfo[] fieldInfoArr = new FieldInfo[list.size()];
        list.toArray(fieldInfoArr);
        if (strArr == null || strArr.length == 0) {
            ArrayList arrayList = new ArrayList(list);
            Collections.sort(arrayList);
            list2 = arrayList;
        } else if (z) {
            list2 = computeGettersWithFieldBase(cls, map, true, propertyNamingStrategy2);
        } else {
            list2 = computeGetters(cls, jSONType, map, hashMap, true, propertyNamingStrategy2);
        }
        FieldInfo[] fieldInfoArr2 = new FieldInfo[list2.size()];
        list2.toArray(fieldInfoArr2);
        return new SerializeBeanInfo(cls, jSONType, str2, str, i, fieldInfoArr, Arrays.equals(fieldInfoArr2, fieldInfoArr) ? fieldInfoArr : fieldInfoArr2);
    }

    public static List<FieldInfo> computeGettersWithFieldBase(Class<?> cls, Map<String, String> map, boolean z, PropertyNamingStrategy propertyNamingStrategy) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Class<?> cls2 = cls; cls2 != null; cls2 = cls2.getSuperclass()) {
            computeFields(cls2, map, propertyNamingStrategy, linkedHashMap, cls2.getDeclaredFields());
        }
        return getFieldInfos(cls, z, linkedHashMap);
    }

    public static List<FieldInfo> computeGetters(Class<?> cls, Map<String, String> map) {
        return computeGetters(cls, map, true);
    }

    public static List<FieldInfo> computeGetters(Class<?> cls, Map<String, String> map, boolean z) {
        HashMap hashMap = new HashMap();
        ParserConfig.parserAllFieldToCache(cls, hashMap);
        return computeGetters(cls, (JSONType) getAnnotation(cls, JSONType.class), map, hashMap, z, PropertyNamingStrategy.CamelCase);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00f7, code lost:
        if (r2 == null) goto L_0x0177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ff, code lost:
        if (r5.startsWith(com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants.GET) == false) goto L_0x0177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0101, code lost:
        r11 = decapitalize(r5.substring(3));
        r21 = java.util.Arrays.binarySearch(r1, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x010e, code lost:
        if (r21 >= 0) goto L_0x0124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0110, code lost:
        r22 = r0;
        r23 = r4;
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0116, code lost:
        if (r0 >= r1.length) goto L_0x0128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x011e, code lost:
        if (r11.equalsIgnoreCase(r1[r0]) == false) goto L_0x0121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0121, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0124, code lost:
        r22 = r0;
        r23 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0128, code lost:
        r0 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012a, code lost:
        if (r0 < 0) goto L_0x0172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012c, code lost:
        r0 = r3[r2[r0]];
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0130, code lost:
        if (r0 == null) goto L_0x0150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0132, code lost:
        r4 = r0.length;
        r21 = r1;
        r1 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0136, code lost:
        if (r1 >= r4) goto L_0x014d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0138, code lost:
        r24 = r2;
        r2 = r0[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0140, code lost:
        if ((r2 instanceof com.alibaba.fastjson.annotation.JSONField) == false) goto L_0x0146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0142, code lost:
        r4 = (com.alibaba.fastjson.annotation.JSONField) r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0146, code lost:
        r1 = r1 + 1;
        r2 = r24;
        r0 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x014d, code lost:
        r24 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0150, code lost:
        r21 = r1;
        r24 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0154, code lost:
        r4 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0156, code lost:
        if (r4 != null) goto L_0x016f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0158, code lost:
        r0 = com.alibaba.fastjson.parser.ParserConfig.getFieldFromCache(r11, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x015c, code lost:
        if (r0 == null) goto L_0x016f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x015e, code lost:
        r23 = (com.alibaba.fastjson.annotation.JSONField) getAnnotation(r0, com.alibaba.fastjson.annotation.JSONField.class);
        r25 = r24;
        r24 = r21;
        r21 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x016f, code lost:
        r23 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0172, code lost:
        r21 = r1;
        r24 = r2;
     */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x03eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<com.alibaba.fastjson.util.FieldInfo> computeGetters(java.lang.Class<?> r39, com.alibaba.fastjson.annotation.JSONType r40, java.util.Map<java.lang.String, java.lang.String> r41, java.util.Map<java.lang.String, java.lang.reflect.Field> r42, boolean r43, com.alibaba.fastjson.PropertyNamingStrategy r44) {
        /*
            Method dump skipped, instructions count: 1513
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.TypeUtils.computeGetters(java.lang.Class, com.alibaba.fastjson.annotation.JSONType, java.util.Map, java.util.Map, boolean, com.alibaba.fastjson.PropertyNamingStrategy):java.util.List");
    }

    private static List<FieldInfo> getFieldInfos(Class<?> cls, boolean z, Map<String, FieldInfo> map) {
        ArrayList arrayList = new ArrayList();
        JSONType jSONType = (JSONType) getAnnotation(cls, JSONType.class);
        String[] orders = jSONType != null ? jSONType.orders() : null;
        if (orders == null || orders.length <= 0) {
            for (FieldInfo fieldInfo : map.values()) {
                arrayList.add(fieldInfo);
            }
            if (z) {
                Collections.sort(arrayList);
            }
        } else {
            LinkedHashMap linkedHashMap = new LinkedHashMap(arrayList.size());
            for (FieldInfo fieldInfo2 : map.values()) {
                linkedHashMap.put(fieldInfo2.name, fieldInfo2);
            }
            for (String str : orders) {
                FieldInfo fieldInfo3 = (FieldInfo) linkedHashMap.get(str);
                if (fieldInfo3 != null) {
                    arrayList.add(fieldInfo3);
                    linkedHashMap.remove(str);
                }
            }
            for (FieldInfo fieldInfo4 : linkedHashMap.values()) {
                arrayList.add(fieldInfo4);
            }
        }
        return arrayList;
    }

    private static void computeFields(Class<?> cls, Map<String, String> map, PropertyNamingStrategy propertyNamingStrategy, Map<String, FieldInfo> map2, Field[] fieldArr) {
        String str;
        int i;
        int i2;
        int i3;
        for (Field field : fieldArr) {
            if (!Modifier.isStatic(field.getModifiers())) {
                JSONField jSONField = (JSONField) getAnnotation(field, JSONField.class);
                String name = field.getName();
                if (jSONField == null) {
                    str = null;
                    i3 = 0;
                    i2 = 0;
                    i = 0;
                } else if (jSONField.serialize()) {
                    int ordinal = jSONField.ordinal();
                    int of = SerializerFeature.of(jSONField.serialzeFeatures());
                    int of2 = Feature.of(jSONField.parseFeatures());
                    if (jSONField.name().length() != 0) {
                        name = jSONField.name();
                    }
                    if (jSONField.label().length() != 0) {
                        str = jSONField.label();
                        i3 = ordinal;
                        i2 = of;
                        i = of2;
                    } else {
                        str = null;
                        i3 = ordinal;
                        i2 = of;
                        i = of2;
                    }
                }
                if (map == null || (name = map.get(name)) != null) {
                    String translate = propertyNamingStrategy != null ? propertyNamingStrategy.translate(name) : name;
                    if (!map2.containsKey(translate)) {
                        map2.put(translate, new FieldInfo(translate, null, field, cls, null, i3, i2, i, null, jSONField, str));
                    }
                }
            }
        }
    }

    private static String getPropertyNameByCompatibleFieldName(Map<String, Field> map, String str, String str2, int i) {
        if (!compatibleWithFieldName || map.containsKey(str2)) {
            return str2;
        }
        String substring = str.substring(i);
        return map.containsKey(substring) ? substring : str2;
    }

    public static JSONField getSuperMethodAnnotation(Class<?> cls, Method method) {
        boolean z;
        JSONField jSONField;
        boolean z2;
        JSONField jSONField2;
        Class<?>[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> cls2 : interfaces) {
                Method[] methods = cls2.getMethods();
                for (Method method2 : methods) {
                    Class<?>[] parameterTypes2 = method2.getParameterTypes();
                    if (parameterTypes2.length == parameterTypes.length && method2.getName().equals(method.getName())) {
                        int i = 0;
                        while (true) {
                            if (i >= parameterTypes.length) {
                                z2 = true;
                                break;
                            } else if (!parameterTypes2[i].equals(parameterTypes[i])) {
                                z2 = false;
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (z2 && (jSONField2 = (JSONField) getAnnotation(method2, JSONField.class)) != null) {
                            return jSONField2;
                        }
                    }
                }
            }
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass != null && Modifier.isAbstract(superclass.getModifiers())) {
            Class<?>[] parameterTypes3 = method.getParameterTypes();
            Method[] methods2 = superclass.getMethods();
            for (Method method3 : methods2) {
                Class<?>[] parameterTypes4 = method3.getParameterTypes();
                if (parameterTypes4.length == parameterTypes3.length && method3.getName().equals(method.getName())) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= parameterTypes3.length) {
                            z = true;
                            break;
                        } else if (!parameterTypes4[i2].equals(parameterTypes3[i2])) {
                            z = false;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (z && (jSONField = (JSONField) getAnnotation(method3, JSONField.class)) != null) {
                        return jSONField;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isJSONTypeIgnore(Class<?> cls, String str) {
        JSONType jSONType = (JSONType) getAnnotation(cls, JSONType.class);
        if (jSONType != null) {
            String[] includes = jSONType.includes();
            if (includes.length > 0) {
                for (String str2 : includes) {
                    if (str.equals(str2)) {
                        return false;
                    }
                }
                return true;
            }
            for (String str3 : jSONType.ignores()) {
                if (str.equals(str3)) {
                    return true;
                }
            }
        }
        if (cls.getSuperclass() == Object.class || cls.getSuperclass() == null) {
            return false;
        }
        return isJSONTypeIgnore(cls.getSuperclass(), str);
    }

    public static boolean isGenericParamType(Type type) {
        if (type instanceof ParameterizedType) {
            return true;
        }
        if (!(type instanceof Class)) {
            return false;
        }
        Type genericSuperclass = ((Class) type).getGenericSuperclass();
        return genericSuperclass != Object.class && isGenericParamType(genericSuperclass);
    }

    public static Type getGenericParamType(Type type) {
        return (!(type instanceof ParameterizedType) && (type instanceof Class)) ? getGenericParamType(((Class) type).getGenericSuperclass()) : type;
    }

    public static Type unwrapOptional(Type type) {
        if (!optionalClassInited) {
            try {
                optionalClass = Class.forName("java.util.Optional");
            } catch (Exception unused) {
            } catch (Throwable th) {
                optionalClassInited = true;
                throw th;
            }
            optionalClassInited = true;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() == optionalClass) {
                return parameterizedType.getActualTypeArguments()[0];
            }
        }
        return type;
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            Type type2 = ((TypeVariable) type).getBounds()[0];
            if (type2 instanceof Class) {
                return (Class) type2;
            }
            return getClass(type2);
        } else if (!(type instanceof WildcardType)) {
            return Object.class;
        } else {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getClass(upperBounds[0]);
            }
            return Object.class;
        }
    }

    public static Field getField(Class<?> cls, String str, Field[] fieldArr) {
        char charAt;
        char charAt2;
        for (Field field : fieldArr) {
            String name = field.getName();
            if (str.equals(name)) {
                return field;
            }
            if (str.length() > 2 && (charAt = str.charAt(0)) >= 'a' && charAt <= 'z' && (charAt2 = str.charAt(1)) >= 'A' && charAt2 <= 'Z' && str.equalsIgnoreCase(name)) {
                return field;
            }
        }
        Class<? super Object> superclass = cls.getSuperclass();
        if (superclass == null || superclass == Object.class) {
            return null;
        }
        return getField(superclass, str, superclass.getDeclaredFields());
    }

    public static int getSerializeFeatures(Class<?> cls) {
        JSONType jSONType = (JSONType) getAnnotation(cls, JSONType.class);
        if (jSONType == null) {
            return 0;
        }
        return SerializerFeature.of(jSONType.serialzeFeatures());
    }

    public static int getParserFeatures(Class<?> cls) {
        JSONType jSONType = (JSONType) getAnnotation(cls, JSONType.class);
        if (jSONType == null) {
            return 0;
        }
        return Feature.of(jSONType.parseFeatures());
    }

    public static String decapitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (str.length() > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0))) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }

    public static void setAccessible(AccessibleObject accessibleObject) {
        if (setAccessibleEnable && !accessibleObject.isAccessible()) {
            try {
                accessibleObject.setAccessible(true);
            } catch (AccessControlException unused) {
                setAccessibleEnable = false;
            }
        }
    }

    public static Type getCollectionItemType(Type type) {
        if (type instanceof ParameterizedType) {
            return getCollectionItemType((ParameterizedType) type);
        }
        if (type instanceof Class) {
            return getCollectionItemType((Class<?>) type);
        }
        return Object.class;
    }

    private static Type getCollectionItemType(Class<?> cls) {
        return cls.getName().startsWith("java.") ? Object.class : getCollectionItemType(getCollectionSuperType(cls));
    }

    private static Type getCollectionItemType(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (rawType == Collection.class) {
            return getWildcardTypeUpperBounds(actualTypeArguments[0]);
        }
        Class cls = (Class) rawType;
        Map<TypeVariable, Type> createActualTypeMap = createActualTypeMap(cls.getTypeParameters(), actualTypeArguments);
        Type collectionSuperType = getCollectionSuperType(cls);
        if (!(collectionSuperType instanceof ParameterizedType)) {
            return getCollectionItemType((Class<?>) collectionSuperType);
        }
        Class<?> rawClass = getRawClass(collectionSuperType);
        Type[] actualTypeArguments2 = ((ParameterizedType) collectionSuperType).getActualTypeArguments();
        if (actualTypeArguments2.length > 0) {
            return getCollectionItemType(makeParameterizedType(rawClass, actualTypeArguments2, createActualTypeMap));
        }
        return getCollectionItemType(rawClass);
    }

    private static Type getCollectionSuperType(Class<?> cls) {
        Type[] genericInterfaces = cls.getGenericInterfaces();
        Type type = null;
        for (Type type2 : genericInterfaces) {
            Class<?> rawClass = getRawClass(type2);
            if (rawClass == Collection.class) {
                return type2;
            }
            if (Collection.class.isAssignableFrom(rawClass)) {
                type = type2;
            }
        }
        return type == null ? cls.getGenericSuperclass() : type;
    }

    private static Map<TypeVariable, Type> createActualTypeMap(TypeVariable[] typeVariableArr, Type[] typeArr) {
        int length = typeVariableArr.length;
        HashMap hashMap = new HashMap(length);
        for (int i = 0; i < length; i++) {
            hashMap.put(typeVariableArr[i], typeArr[i]);
        }
        return hashMap;
    }

    private static ParameterizedType makeParameterizedType(Class<?> cls, Type[] typeArr, Map<TypeVariable, Type> map) {
        int length = typeArr.length;
        Type[] typeArr2 = new Type[length];
        for (int i = 0; i < length; i++) {
            typeArr2[i] = getActualType(typeArr[i], map);
        }
        return new ParameterizedTypeImpl(typeArr2, null, cls);
    }

    private static Type getActualType(Type type, Map<TypeVariable, Type> map) {
        if (type instanceof TypeVariable) {
            return map.get(type);
        }
        if (type instanceof ParameterizedType) {
            return makeParameterizedType(getRawClass(type), ((ParameterizedType) type).getActualTypeArguments(), map);
        }
        return type instanceof GenericArrayType ? new GenericArrayTypeImpl(getActualType(((GenericArrayType) type).getGenericComponentType(), map)) : type;
    }

    private static Type getWildcardTypeUpperBounds(Type type) {
        if (!(type instanceof WildcardType)) {
            return type;
        }
        Type[] upperBounds = ((WildcardType) type).getUpperBounds();
        return upperBounds.length > 0 ? upperBounds[0] : Object.class;
    }

    public static Class<?> getCollectionItemClass(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return Object.class;
        }
        Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        if (type2 instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type2).getUpperBounds();
            if (upperBounds.length == 1) {
                type2 = upperBounds[0];
            }
        }
        if (type2 instanceof Class) {
            Class<?> cls = (Class) type2;
            if (Modifier.isPublic(cls.getModifiers())) {
                return cls;
            }
            throw new JSONException("can not create ASMParser");
        }
        throw new JSONException("can not create ASMParser");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r3v9, types: [java.lang.Class] */
    public static Type checkPrimitiveArray(GenericArrayType genericArrayType) {
        Type genericComponentType = genericArrayType.getGenericComponentType();
        String str = "[";
        while (genericComponentType instanceof GenericArrayType) {
            genericComponentType = ((GenericArrayType) genericComponentType).getGenericComponentType();
            str = str + str;
        }
        if (genericComponentType instanceof Class) {
            Class cls = (Class) genericComponentType;
            if (cls.isPrimitive()) {
                try {
                    if (cls == Boolean.TYPE) {
                        genericArrayType = Class.forName(str + "Z");
                    } else if (cls == Character.TYPE) {
                        genericArrayType = Class.forName(str + HomePageRecordHelper.AREA_C);
                    } else if (cls == Byte.TYPE) {
                        genericArrayType = Class.forName(str + "B");
                    } else if (cls == Short.TYPE) {
                        genericArrayType = Class.forName(str + ExifInterface.LATITUDE_SOUTH);
                    } else if (cls == Integer.TYPE) {
                        genericArrayType = Class.forName(str + "I");
                    } else if (cls == Long.TYPE) {
                        genericArrayType = Class.forName(str + "J");
                    } else if (cls == Float.TYPE) {
                        genericArrayType = Class.forName(str + HomePageRecordHelper.AREA_F);
                    } else {
                        genericArrayType = genericArrayType;
                        if (cls == Double.TYPE) {
                            genericArrayType = Class.forName(str + HomePageRecordHelper.AREA_D);
                        }
                    }
                } catch (ClassNotFoundException unused) {
                }
            }
        }
        return genericArrayType;
    }

    public static Collection createCollection(Type type) {
        Type type2;
        Class<?> rawClass = getRawClass(type);
        if (rawClass == AbstractCollection.class || rawClass == Collection.class) {
            return new ArrayList();
        }
        if (rawClass.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (rawClass.isAssignableFrom(LinkedHashSet.class)) {
            return new LinkedHashSet();
        }
        if (rawClass.isAssignableFrom(TreeSet.class)) {
            return new TreeSet();
        }
        if (rawClass.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (rawClass.isAssignableFrom(EnumSet.class)) {
            if (type instanceof ParameterizedType) {
                type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                type2 = Object.class;
            }
            return EnumSet.noneOf(type2);
        } else if (rawClass.isAssignableFrom(Queue.class) || rawClass.isAssignableFrom(Deque.class)) {
            return new LinkedList();
        } else {
            try {
                return (Collection) rawClass.newInstance();
            } catch (Exception unused) {
                throw new JSONException("create instance error, class " + rawClass.getName());
            }
        }
    }

    public static Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getRawClass(upperBounds[0]);
            }
            throw new JSONException("TODO");
        }
        throw new JSONException("TODO");
    }

    public static boolean isProxy(Class<?> cls) {
        for (Class<?> cls2 : cls.getInterfaces()) {
            String name = cls2.getName();
            if (name.equals("net.sf.cglib.proxy.Factory") || name.equals("org.springframework.cglib.proxy.Factory") || name.equals("javassist.util.proxy.ProxyObject") || name.equals("org.apache.ibatis.javassist.util.proxy.ProxyObject") || name.equals("org.hibernate.proxy.HibernateProxy")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTransient(Method method) {
        if (method == null) {
            return false;
        }
        if (!transientClassInited) {
            try {
                transientClass = Class.forName("java.beans.Transient");
            } catch (Exception unused) {
            } catch (Throwable th) {
                transientClassInited = true;
                throw th;
            }
            transientClassInited = true;
        }
        Class<? extends Annotation> cls = transientClass;
        return (cls == null || getAnnotation(method, (Class<Annotation>) cls) == null) ? false : true;
    }

    public static boolean isAnnotationPresentOneToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_OneToMany == null && !class_OneToMany_error) {
            try {
                class_OneToMany = Class.forName("javax.persistence.OneToMany");
            } catch (Throwable unused) {
                class_OneToMany_error = true;
            }
        }
        Class<? extends Annotation> cls = class_OneToMany;
        return cls != null && method.isAnnotationPresent(cls);
    }

    public static boolean isAnnotationPresentManyToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_ManyToMany == null && !class_ManyToMany_error) {
            try {
                class_ManyToMany = Class.forName("javax.persistence.ManyToMany");
            } catch (Throwable unused) {
                class_ManyToMany_error = true;
            }
        }
        if (class_ManyToMany != null) {
            return method.isAnnotationPresent(class_OneToMany) || method.isAnnotationPresent(class_ManyToMany);
        }
        return false;
    }

    public static boolean isHibernateInitialized(Object obj) {
        if (obj == null) {
            return false;
        }
        if (method_HibernateIsInitialized == null && !method_HibernateIsInitialized_error) {
            try {
                method_HibernateIsInitialized = Class.forName("org.hibernate.Hibernate").getMethod("isInitialized", Object.class);
            } catch (Throwable unused) {
                method_HibernateIsInitialized_error = true;
            }
        }
        Method method = method_HibernateIsInitialized;
        if (method != null) {
            try {
                return ((Boolean) method.invoke(null, obj)).booleanValue();
            } catch (Throwable unused2) {
            }
        }
        return true;
    }

    public static double parseDouble(String str) {
        int length = str.length();
        if (length > 10) {
            return Double.parseDouble(str);
        }
        long j = 0;
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '-' && i2 == 0) {
                z = true;
            } else if (charAt == '.') {
                if (i != 0) {
                    return Double.parseDouble(str);
                }
                i = (length - i2) - 1;
            } else if (charAt < '0' || charAt > '9') {
                return Double.parseDouble(str);
            } else {
                j = (j * 10) + (charAt - '0');
            }
        }
        if (z) {
            j = -j;
        }
        switch (i) {
            case 0:
                return j;
            case 1:
                return j / 10.0d;
            case 2:
                return j / 100.0d;
            case 3:
                return j / 1000.0d;
            case 4:
                return j / 10000.0d;
            case 5:
                return j / 100000.0d;
            case 6:
                return j / 1000000.0d;
            case 7:
                return j / 1.0E7d;
            case 8:
                return j / 1.0E8d;
            case 9:
                return j / 1.0E9d;
            default:
                return Double.parseDouble(str);
        }
    }

    public static float parseFloat(String str) {
        int length = str.length();
        if (length >= 10) {
            return Float.parseFloat(str);
        }
        long j = 0;
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '-' && i2 == 0) {
                z = true;
            } else if (charAt == '.') {
                if (i != 0) {
                    return Float.parseFloat(str);
                }
                i = (length - i2) - 1;
            } else if (charAt < '0' || charAt > '9') {
                return Float.parseFloat(str);
            } else {
                j = (j * 10) + (charAt - '0');
            }
        }
        if (z) {
            j = -j;
        }
        switch (i) {
            case 0:
                return (float) j;
            case 1:
                return ((float) j) / 10.0f;
            case 2:
                return ((float) j) / 100.0f;
            case 3:
                return ((float) j) / 1000.0f;
            case 4:
                return ((float) j) / 10000.0f;
            case 5:
                return ((float) j) / 100000.0f;
            case 6:
                return ((float) j) / 1000000.0f;
            case 7:
                return ((float) j) / 1.0E7f;
            case 8:
                return ((float) j) / 1.0E8f;
            case 9:
                return ((float) j) / 1.0E9f;
            default:
                return Float.parseFloat(str);
        }
    }

    public static long fnv1a_64_lower(String str) {
        long j = -3750763034362895579L;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!(charAt == '_' || charAt == '-')) {
                if (charAt >= 'A' && charAt <= 'Z') {
                    charAt = (char) (charAt + ' ');
                }
                j = (j ^ charAt) * 1099511628211L;
            }
        }
        return j;
    }

    public static long fnv1a_64(String str) {
        long j = -3750763034362895579L;
        for (int i = 0; i < str.length(); i++) {
            j = (j ^ str.charAt(i)) * 1099511628211L;
        }
        return j;
    }

    public static boolean isKotlin(Class cls) {
        if (kotlin_metadata == null && !kotlin_metadata_error) {
            try {
                kotlin_metadata = Class.forName("kotlin.Metadata");
            } catch (Throwable unused) {
                kotlin_metadata_error = true;
            }
        }
        return kotlin_metadata != null && cls.isAnnotationPresent(kotlin_metadata);
    }

    public static Constructor getKoltinConstructor(Constructor[] constructorArr) {
        return getKoltinConstructor(constructorArr, null);
    }

    public static Constructor getKoltinConstructor(Constructor[] constructorArr, String[] strArr) {
        Constructor constructor = null;
        for (Constructor constructor2 : constructorArr) {
            Class<?>[] parameterTypes = constructor2.getParameterTypes();
            if ((strArr == null || parameterTypes.length == strArr.length) && ((parameterTypes.length <= 0 || !parameterTypes[parameterTypes.length - 1].getName().equals("kotlin.jvm.internal.DefaultConstructorMarker")) && (constructor == null || constructor.getParameterTypes().length < parameterTypes.length))) {
                constructor = constructor2;
            }
        }
        return constructor;
    }

    public static String[] getKoltinConstructorParameters(Class cls) {
        if (kotlin_kclass_constructor == null && !kotlin_class_klass_error) {
            try {
                kotlin_kclass_constructor = Class.forName("kotlin.reflect.jvm.internal.KClassImpl").getConstructor(Class.class);
            } catch (Throwable unused) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kclass_constructor == null) {
            return null;
        }
        if (kotlin_kclass_getConstructors == null && !kotlin_class_klass_error) {
            try {
                kotlin_kclass_getConstructors = Class.forName("kotlin.reflect.jvm.internal.KClassImpl").getMethod("getConstructors", new Class[0]);
            } catch (Throwable unused2) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kfunction_getParameters == null && !kotlin_class_klass_error) {
            try {
                kotlin_kfunction_getParameters = Class.forName("kotlin.reflect.KFunction").getMethod("getParameters", new Class[0]);
            } catch (Throwable unused3) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kparameter_getName == null && !kotlin_class_klass_error) {
            try {
                kotlin_kparameter_getName = Class.forName("kotlin.reflect.KParameter").getMethod("getName", new Class[0]);
            } catch (Throwable unused4) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_error) {
            return null;
        }
        try {
            Iterator it = ((Iterable) kotlin_kclass_getConstructors.invoke(kotlin_kclass_constructor.newInstance(cls), new Object[0])).iterator();
            Object obj = null;
            while (it.hasNext()) {
                Object next = it.next();
                List list = (List) kotlin_kfunction_getParameters.invoke(next, new Object[0]);
                if (obj == null || list.size() != 0) {
                    obj = next;
                }
                it.hasNext();
            }
            if (obj == null) {
                return null;
            }
            List list2 = (List) kotlin_kfunction_getParameters.invoke(obj, new Object[0]);
            String[] strArr = new String[list2.size()];
            for (int i = 0; i < list2.size(); i++) {
                strArr[i] = (String) kotlin_kparameter_getName.invoke(list2.get(i), new Object[0]);
            }
            return strArr;
        } catch (Throwable th) {
            th.printStackTrace();
            kotlin_error = true;
            return null;
        }
    }

    private static boolean isKotlinIgnore(Class cls, String str) {
        if (kotlinIgnores == null && !kotlinIgnores_error) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put(Class.forName("kotlin.ranges.CharRange"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.IntRange"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.LongRange"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.b"), new String[]{"getEndInclusive", "isEmpty"});
                hashMap.put(Class.forName("kotlin.ranges.a"), new String[]{"getEndInclusive", "isEmpty"});
                kotlinIgnores = hashMap;
            } catch (Throwable unused) {
                kotlinIgnores_error = true;
            }
        }
        if (kotlinIgnores == null) {
            return false;
        }
        String[] strArr = kotlinIgnores.get(cls);
        return strArr != null && Arrays.binarySearch(strArr, str) >= 0;
    }

    public static <A extends Annotation> A getAnnotation(Class<?> cls, Class<A> cls2) {
        A a;
        A a2 = (A) cls.getAnnotation(cls2);
        Type mixInAnnotations = JSON.getMixInAnnotations(cls);
        Class cls3 = mixInAnnotations instanceof Class ? (Class) mixInAnnotations : null;
        if (cls3 != null) {
            Annotation annotation = cls3.getAnnotation(cls2);
            if (annotation != null || cls3.getAnnotations().length <= 0) {
                a = (A) annotation;
            } else {
                a = annotation;
                for (Annotation annotation2 : cls3.getAnnotations()) {
                    a = (A) annotation2.annotationType().getAnnotation(cls2);
                    if (a != null) {
                        break;
                    }
                }
            }
            if (a != null) {
                return a;
            }
        }
        if (a2 == null && cls.getAnnotations().length > 0) {
            for (Annotation annotation3 : cls.getAnnotations()) {
                a2 = (A) annotation3.annotationType().getAnnotation(cls2);
                if (a2 != null) {
                    break;
                }
            }
        }
        return a2;
    }

    public static <A extends Annotation> A getAnnotation(Field field, Class<A> cls) {
        A a;
        A a2 = (A) field.getAnnotation(cls);
        Type mixInAnnotations = JSON.getMixInAnnotations(field.getDeclaringClass());
        Field field2 = null;
        Class cls2 = mixInAnnotations instanceof Class ? (Class) mixInAnnotations : null;
        if (cls2 != null) {
            String name = field.getName();
            while (cls2 != null && cls2 != Object.class) {
                try {
                    field2 = cls2.getDeclaredField(name);
                    break;
                } catch (NoSuchFieldException unused) {
                    cls2 = cls2.getSuperclass();
                }
            }
            if (!(field2 == null || (a = (A) field2.getAnnotation(cls)) == null)) {
                return a;
            }
        }
        return a2;
    }

    public static <A extends Annotation> A getAnnotation(Method method, Class<A> cls) {
        A a;
        A a2 = (A) method.getAnnotation(cls);
        Type mixInAnnotations = JSON.getMixInAnnotations(method.getDeclaringClass());
        Method method2 = null;
        Class cls2 = mixInAnnotations instanceof Class ? (Class) mixInAnnotations : null;
        if (cls2 != null) {
            String name = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            while (cls2 != null && cls2 != Object.class) {
                try {
                    method2 = cls2.getDeclaredMethod(name, parameterTypes);
                    break;
                } catch (NoSuchMethodException unused) {
                    cls2 = cls2.getSuperclass();
                }
            }
            if (!(method2 == null || (a = (A) method2.getAnnotation(cls)) == null)) {
                return a;
            }
        }
        return a2;
    }

    public static Annotation[][] getParameterAnnotations(Method method) {
        Annotation[][] parameterAnnotations;
        Annotation[][] parameterAnnotations2 = method.getParameterAnnotations();
        Type mixInAnnotations = JSON.getMixInAnnotations(method.getDeclaringClass());
        Method method2 = null;
        Class cls = mixInAnnotations instanceof Class ? (Class) mixInAnnotations : null;
        if (cls != null) {
            String name = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            while (cls != null && cls != Object.class) {
                try {
                    method2 = cls.getDeclaredMethod(name, parameterTypes);
                    break;
                } catch (NoSuchMethodException unused) {
                    cls = cls.getSuperclass();
                }
            }
            if (!(method2 == null || (parameterAnnotations = method2.getParameterAnnotations()) == null)) {
                return parameterAnnotations;
            }
        }
        return parameterAnnotations2;
    }

    public static Annotation[][] getParameterAnnotations(Constructor constructor) {
        Annotation[][] parameterAnnotations;
        Annotation[][] parameterAnnotations2 = constructor.getParameterAnnotations();
        Type mixInAnnotations = JSON.getMixInAnnotations(constructor.getDeclaringClass());
        Constructor constructor2 = null;
        Class cls = mixInAnnotations instanceof Class ? (Class) mixInAnnotations : null;
        if (cls != null) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            ArrayList arrayList = new ArrayList(2);
            for (Class<?> enclosingClass = cls.getEnclosingClass(); enclosingClass != null; enclosingClass = enclosingClass.getEnclosingClass()) {
                arrayList.add(enclosingClass);
            }
            int size = arrayList.size();
            Class cls2 = cls;
            while (true) {
                if (cls2 == null || cls2 == Object.class) {
                    break;
                }
                try {
                    if (size != 0) {
                        Class<?>[] clsArr = new Class[parameterTypes.length + size];
                        System.arraycopy(parameterTypes, 0, clsArr, size, parameterTypes.length);
                        for (int i = size; i > 0; i--) {
                            int i2 = i - 1;
                            clsArr[i2] = (Class) arrayList.get(i2);
                        }
                        constructor2 = cls.getDeclaredConstructor(clsArr);
                    } else {
                        constructor2 = cls.getDeclaredConstructor(parameterTypes);
                    }
                } catch (NoSuchMethodException unused) {
                    size--;
                    cls2 = cls2.getSuperclass();
                }
            }
            if (!(constructor2 == null || (parameterAnnotations = constructor2.getParameterAnnotations()) == null)) {
                return parameterAnnotations;
            }
        }
        return parameterAnnotations2;
    }

    public static boolean isJacksonCreator(Method method) {
        if (method == null) {
            return false;
        }
        if (class_JacksonCreator == null && !class_JacksonCreator_error) {
            try {
                class_JacksonCreator = Class.forName("com.fasterxml.jackson.annotation.JsonCreator");
            } catch (Throwable unused) {
                class_JacksonCreator_error = true;
            }
        }
        Class<? extends Annotation> cls = class_JacksonCreator;
        return cls != null && method.isAnnotationPresent(cls);
    }

    public static LocalDateTime castToLocalDateTime(Object obj, String str) {
        if (obj == null) {
            return null;
        }
        if (str == null) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        return LocalDateTime.parse(obj.toString(), DateTimeFormatter.ofPattern(str));
    }
}
