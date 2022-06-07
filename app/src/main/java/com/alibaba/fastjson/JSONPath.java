package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class JSONPath implements JSONAware {
    static final long LENGTH = -1580386065683472715L;
    static final long SIZE = 5614464919154503228L;
    private static ConcurrentMap<String, JSONPath> pathCache = new ConcurrentHashMap(128, 0.75f, 1);
    private boolean hasRefSegment;
    private ParserConfig parserConfig;
    private final String path;
    private Segment[] segments;
    private SerializeConfig serializeConfig;

    /* loaded from: classes.dex */
    public interface Filter {
        boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3);
    }

    /* loaded from: classes.dex */
    public enum Operator {
        EQ,
        NE,
        GT,
        GE,
        LT,
        LE,
        LIKE,
        NOT_LIKE,
        RLIKE,
        NOT_RLIKE,
        IN,
        NOT_IN,
        BETWEEN,
        NOT_BETWEEN,
        And,
        Or,
        REG_MATCH
    }

    /* loaded from: classes.dex */
    public interface Segment {
        Object eval(JSONPath jSONPath, Object obj, Object obj2);

        void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context);
    }

    public JSONPath(String str) {
        this(str, SerializeConfig.getGlobalInstance(), ParserConfig.getGlobalInstance());
    }

    public JSONPath(String str, SerializeConfig serializeConfig, ParserConfig parserConfig) {
        if (str == null || str.length() == 0) {
            throw new JSONPathException("json-path can not be null or empty");
        }
        this.path = str;
        this.serializeConfig = serializeConfig;
        this.parserConfig = parserConfig;
    }

    protected void init() {
        if (this.segments == null) {
            if ("*".equals(this.path)) {
                this.segments = new Segment[]{WildCardSegment.instance};
                return;
            }
            JSONPathParser jSONPathParser = new JSONPathParser(this.path);
            this.segments = jSONPathParser.explain();
            this.hasRefSegment = jSONPathParser.hasRefSegment;
        }
    }

    public boolean isRef() {
        init();
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return true;
            }
            Class<?> cls = segmentArr[i].getClass();
            if (cls != ArrayAccessSegment.class && cls != PropertySegment.class) {
                return false;
            }
            i++;
        }
    }

    public Object eval(Object obj) {
        if (obj == null) {
            return null;
        }
        init();
        int i = 0;
        Object obj2 = obj;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return obj2;
            }
            obj2 = segmentArr[i].eval(this, obj, obj2);
            i++;
        }
    }

    public Object extract(DefaultJSONParser defaultJSONParser) {
        if (defaultJSONParser == null) {
            return null;
        }
        init();
        if (this.hasRefSegment) {
            return eval(defaultJSONParser.parse());
        }
        if (this.segments.length == 0) {
            return defaultJSONParser.parse();
        }
        Context context = null;
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return context.object;
            }
            Segment segment = segmentArr[i];
            boolean z = true;
            boolean z2 = i == segmentArr.length - 1;
            if (context == null || context.object == null) {
                if (!z2) {
                    Segment segment2 = this.segments[i + 1];
                    if ((!(segment instanceof PropertySegment) || !((PropertySegment) segment).deep || (!(segment2 instanceof ArrayAccessSegment) && !(segment2 instanceof MultiIndexSegment) && !(segment2 instanceof MultiPropertySegment) && !(segment2 instanceof SizeSegment) && !(segment2 instanceof PropertySegment) && !(segment2 instanceof FilterSegment))) && ((!(segment2 instanceof ArrayAccessSegment) || ((ArrayAccessSegment) segment2).index >= 0) && !(segment2 instanceof FilterSegment) && !(segment instanceof WildCardSegment))) {
                        z = false;
                    }
                }
                Context context2 = new Context(context, z);
                segment.extract(this, defaultJSONParser, context2);
                context = context2;
            } else {
                context.object = segment.eval(this, null, context.object);
            }
            i++;
        }
    }

    /* loaded from: classes.dex */
    public static class Context {
        final boolean eval;
        Object object;
        final Context parent;

        public Context(Context context, boolean z) {
            this.parent = context;
            this.eval = z;
        }
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        init();
        Object obj2 = obj;
        int i = 0;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return true;
            }
            Object eval = segmentArr[i].eval(this, obj, obj2);
            if (eval == null) {
                return false;
            }
            if (eval == Collections.EMPTY_LIST && (obj2 instanceof List)) {
                return ((List) obj2).contains(eval);
            }
            i++;
            obj2 = eval;
        }
    }

    public boolean containsValue(Object obj, Object obj2) {
        Object eval = eval(obj);
        if (eval == obj2) {
            return true;
        }
        if (eval == null) {
            return false;
        }
        if (!(eval instanceof Iterable)) {
            return eq(eval, obj2);
        }
        for (Object obj3 : (Iterable) eval) {
            if (eq(obj3, obj2)) {
                return true;
            }
        }
        return false;
    }

    public int size(Object obj) {
        if (obj == null) {
            return -1;
        }
        init();
        int i = 0;
        Object obj2 = obj;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return evalSize(obj2);
            }
            obj2 = segmentArr[i].eval(this, obj, obj2);
            i++;
        }
    }

    public Set<?> keySet(Object obj) {
        if (obj == null) {
            return null;
        }
        init();
        int i = 0;
        Object obj2 = obj;
        while (true) {
            Segment[] segmentArr = this.segments;
            if (i >= segmentArr.length) {
                return evalKeySet(obj2);
            }
            obj2 = segmentArr[i].eval(this, obj, obj2);
            i++;
        }
    }

    public void arrayAdd(Object obj, Object... objArr) {
        if (objArr != null && objArr.length != 0 && obj != null) {
            init();
            Object obj2 = null;
            int i = 0;
            Object obj3 = obj;
            int i2 = 0;
            while (true) {
                Segment[] segmentArr = this.segments;
                if (i2 >= segmentArr.length) {
                    break;
                }
                if (i2 == segmentArr.length - 1) {
                    obj2 = obj3;
                }
                obj3 = this.segments[i2].eval(this, obj, obj3);
                i2++;
            }
            if (obj3 == null) {
                throw new JSONPathException("value not found in path " + this.path);
            } else if (obj3 instanceof Collection) {
                Collection collection = (Collection) obj3;
                int length = objArr.length;
                while (i < length) {
                    collection.add(objArr[i]);
                    i++;
                }
            } else {
                Class<?> cls = obj3.getClass();
                if (cls.isArray()) {
                    int length2 = Array.getLength(obj3);
                    Object newInstance = Array.newInstance(cls.getComponentType(), objArr.length + length2);
                    System.arraycopy(obj3, 0, newInstance, 0, length2);
                    while (i < objArr.length) {
                        Array.set(newInstance, length2 + i, objArr[i]);
                        i++;
                    }
                    Segment[] segmentArr2 = this.segments;
                    Segment segment = segmentArr2[segmentArr2.length - 1];
                    if (segment instanceof PropertySegment) {
                        ((PropertySegment) segment).setValue(this, obj2, newInstance);
                    } else if (segment instanceof ArrayAccessSegment) {
                        ((ArrayAccessSegment) segment).setValue(this, obj2, newInstance);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    throw new JSONException("unsupported array put operation. " + cls);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x008f, code lost:
        if (r1 != null) goto L_0x0092;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0091, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0094, code lost:
        if ((r2 instanceof com.alibaba.fastjson.JSONPath.PropertySegment) == false) goto L_0x00cc;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0096, code lost:
        r2 = (com.alibaba.fastjson.JSONPath.PropertySegment) r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x009a, code lost:
        if ((r1 instanceof java.util.Collection) == false) goto L_0x00c7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x009c, code lost:
        r12 = r11.segments;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x009f, code lost:
        if (r12.length <= 1) goto L_0x00c7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a1, code lost:
        r12 = r12[r12.length - 2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00a8, code lost:
        if ((r12 instanceof com.alibaba.fastjson.JSONPath.RangeSegment) != false) goto L_0x00ae;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ac, code lost:
        if ((r12 instanceof com.alibaba.fastjson.JSONPath.MultiIndexSegment) == false) goto L_0x00c7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00ae, code lost:
        r12 = r1.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b8, code lost:
        if (r12.hasNext() == false) goto L_0x00c6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00c2, code lost:
        if (r2.remove(r11, r12.next()) == false) goto L_0x00b4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c4, code lost:
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c6, code lost:
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00cb, code lost:
        return r2.remove(r11, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ce, code lost:
        if ((r2 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment) == false) goto L_0x00d7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00d6, code lost:
        return ((com.alibaba.fastjson.JSONPath.ArrayAccessSegment) r2).remove(r11, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00d9, code lost:
        if ((r2 instanceof com.alibaba.fastjson.JSONPath.FilterSegment) == false) goto L_0x00e2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00e1, code lost:
        return ((com.alibaba.fastjson.JSONPath.FilterSegment) r2).remove(r11, r12, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00e7, code lost:
        throw new java.lang.UnsupportedOperationException();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean remove(java.lang.Object r12) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.remove(java.lang.Object):boolean");
    }

    public boolean set(Object obj, Object obj2) {
        return set(obj, obj2, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean set(java.lang.Object r9, java.lang.Object r10, boolean r11) {
        /*
            r8 = this;
            r11 = 0
            if (r9 != 0) goto L_0x0004
            return r11
        L_0x0004:
            r8.init()
            r0 = 0
            r2 = r9
            r1 = r11
            r3 = r0
        L_0x000b:
            com.alibaba.fastjson.JSONPath$Segment[] r4 = r8.segments
            int r5 = r4.length
            r6 = 1
            if (r1 >= r5) goto L_0x0086
            r3 = r4[r1]
            java.lang.Object r4 = r3.eval(r8, r9, r2)
            if (r4 != 0) goto L_0x0081
            com.alibaba.fastjson.JSONPath$Segment[] r4 = r8.segments
            int r5 = r4.length
            int r5 = r5 - r6
            if (r1 >= r5) goto L_0x0024
            int r5 = r1 + 1
            r4 = r4[r5]
            goto L_0x0025
        L_0x0024:
            r4 = r0
        L_0x0025:
            boolean r5 = r4 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r5 == 0) goto L_0x0061
            boolean r4 = r3 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r4 == 0) goto L_0x004b
            r4 = r3
            com.alibaba.fastjson.JSONPath$PropertySegment r4 = (com.alibaba.fastjson.JSONPath.PropertySegment) r4
            java.lang.String r4 = com.alibaba.fastjson.JSONPath.PropertySegment.access$400(r4)
            java.lang.Class r5 = r2.getClass()
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r5 = r8.getJavaBeanDeserializer(r5)
            if (r5 == 0) goto L_0x004b
            com.alibaba.fastjson.parser.deserializer.FieldDeserializer r4 = r5.getFieldDeserializer(r4)
            com.alibaba.fastjson.util.FieldInfo r4 = r4.fieldInfo
            java.lang.Class<?> r4 = r4.fieldClass
            com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer r5 = r8.getJavaBeanDeserializer(r4)
            goto L_0x004d
        L_0x004b:
            r4 = r0
            r5 = r4
        L_0x004d:
            if (r5 == 0) goto L_0x005b
            com.alibaba.fastjson.util.JavaBeanInfo r7 = r5.beanInfo
            java.lang.reflect.Constructor<?> r7 = r7.defaultConstructor
            if (r7 == 0) goto L_0x005a
            java.lang.Object r4 = r5.createInstance(r0, r4)
            goto L_0x006c
        L_0x005a:
            return r11
        L_0x005b:
            com.alibaba.fastjson.JSONObject r4 = new com.alibaba.fastjson.JSONObject
            r4.<init>()
            goto L_0x006c
        L_0x0061:
            boolean r4 = r4 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment
            if (r4 == 0) goto L_0x006b
            com.alibaba.fastjson.JSONArray r4 = new com.alibaba.fastjson.JSONArray
            r4.<init>()
            goto L_0x006c
        L_0x006b:
            r4 = r0
        L_0x006c:
            if (r4 == 0) goto L_0x0087
            boolean r5 = r3 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r5 == 0) goto L_0x0078
            com.alibaba.fastjson.JSONPath$PropertySegment r3 = (com.alibaba.fastjson.JSONPath.PropertySegment) r3
            r3.setValue(r8, r2, r4)
            goto L_0x0081
        L_0x0078:
            boolean r5 = r3 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment
            if (r5 == 0) goto L_0x0087
            com.alibaba.fastjson.JSONPath$ArrayAccessSegment r3 = (com.alibaba.fastjson.JSONPath.ArrayAccessSegment) r3
            r3.setValue(r8, r2, r4)
        L_0x0081:
            int r1 = r1 + 1
            r3 = r2
            r2 = r4
            goto L_0x000b
        L_0x0086:
            r2 = r3
        L_0x0087:
            if (r2 != 0) goto L_0x008a
            return r11
        L_0x008a:
            com.alibaba.fastjson.JSONPath$Segment[] r9 = r8.segments
            int r11 = r9.length
            int r11 = r11 - r6
            r9 = r9[r11]
            boolean r11 = r9 instanceof com.alibaba.fastjson.JSONPath.PropertySegment
            if (r11 == 0) goto L_0x009a
            com.alibaba.fastjson.JSONPath$PropertySegment r9 = (com.alibaba.fastjson.JSONPath.PropertySegment) r9
            r9.setValue(r8, r2, r10)
            return r6
        L_0x009a:
            boolean r11 = r9 instanceof com.alibaba.fastjson.JSONPath.ArrayAccessSegment
            if (r11 == 0) goto L_0x00a5
            com.alibaba.fastjson.JSONPath$ArrayAccessSegment r9 = (com.alibaba.fastjson.JSONPath.ArrayAccessSegment) r9
            boolean r9 = r9.setValue(r8, r2, r10)
            return r9
        L_0x00a5:
            java.lang.UnsupportedOperationException r9 = new java.lang.UnsupportedOperationException
            r9.<init>()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.set(java.lang.Object, java.lang.Object, boolean):boolean");
    }

    public static Object eval(Object obj, String str) {
        return compile(str).eval(obj);
    }

    public static int size(Object obj, String str) {
        JSONPath compile = compile(str);
        return compile.evalSize(compile.eval(obj));
    }

    public static Set<?> keySet(Object obj, String str) {
        JSONPath compile = compile(str);
        return compile.evalKeySet(compile.eval(obj));
    }

    public static boolean contains(Object obj, String str) {
        if (obj == null) {
            return false;
        }
        return compile(str).contains(obj);
    }

    public static boolean containsValue(Object obj, String str, Object obj2) {
        return compile(str).containsValue(obj, obj2);
    }

    public static void arrayAdd(Object obj, String str, Object... objArr) {
        compile(str).arrayAdd(obj, objArr);
    }

    public static boolean set(Object obj, String str, Object obj2) {
        return compile(str).set(obj, obj2);
    }

    public static boolean remove(Object obj, String str) {
        return compile(str).remove(obj);
    }

    public static JSONPath compile(String str) {
        if (str != null) {
            JSONPath jSONPath = pathCache.get(str);
            if (jSONPath != null) {
                return jSONPath;
            }
            JSONPath jSONPath2 = new JSONPath(str);
            if (pathCache.size() >= 1024) {
                return jSONPath2;
            }
            pathCache.putIfAbsent(str, jSONPath2);
            return pathCache.get(str);
        }
        throw new JSONPathException("jsonpath can not be null");
    }

    public static Object read(String str, String str2) {
        return compile(str2).eval(JSON.parse(str));
    }

    public static Object extract(String str, String str2, ParserConfig parserConfig, int i, Feature... featureArr) {
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser(str, parserConfig, i | Feature.OrderedField.mask);
        Object extract = compile(str2).extract(defaultJSONParser);
        defaultJSONParser.lexer.close();
        return extract;
    }

    public static Object extract(String str, String str2) {
        return extract(str, str2, ParserConfig.global, JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
    }

    public static Map<String, Object> paths(Object obj) {
        return paths(obj, SerializeConfig.globalInstance);
    }

    public static Map<String, Object> paths(Object obj, SerializeConfig serializeConfig) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        HashMap hashMap = new HashMap();
        paths(identityHashMap, hashMap, "/", obj, serializeConfig);
        return hashMap;
    }

    private static void paths(Map<Object, String> map, Map<String, Object> map2, String str, Object obj, SerializeConfig serializeConfig) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        if (obj != null) {
            int i = 0;
            if (map.put(obj, str) != null) {
                if (!((obj instanceof String) || (obj instanceof Number) || (obj instanceof Date) || (obj instanceof UUID))) {
                    return;
                }
            }
            map2.put(str, obj);
            if (obj instanceof Map) {
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    Object key = entry.getKey();
                    if (key instanceof String) {
                        if (str.equals("/")) {
                            sb4 = new StringBuilder();
                        } else {
                            sb4 = new StringBuilder();
                            sb4.append(str);
                        }
                        sb4.append("/");
                        sb4.append(key);
                        paths(map, map2, sb4.toString(), entry.getValue(), serializeConfig);
                    }
                }
            } else if (obj instanceof Collection) {
                for (Object obj2 : (Collection) obj) {
                    if (str.equals("/")) {
                        sb3 = new StringBuilder();
                    } else {
                        sb3 = new StringBuilder();
                        sb3.append(str);
                    }
                    sb3.append("/");
                    sb3.append(i);
                    paths(map, map2, sb3.toString(), obj2, serializeConfig);
                    i++;
                }
            } else {
                Class<?> cls = obj.getClass();
                if (cls.isArray()) {
                    int length = Array.getLength(obj);
                    while (i < length) {
                        Object obj3 = Array.get(obj, i);
                        if (str.equals("/")) {
                            sb2 = new StringBuilder();
                        } else {
                            sb2 = new StringBuilder();
                            sb2.append(str);
                        }
                        sb2.append("/");
                        sb2.append(i);
                        paths(map, map2, sb2.toString(), obj3, serializeConfig);
                        i++;
                    }
                } else if (!ParserConfig.isPrimitive2(cls) && !cls.isEnum()) {
                    ObjectSerializer objectWriter = serializeConfig.getObjectWriter(cls);
                    if (objectWriter instanceof JavaBeanSerializer) {
                        try {
                            for (Map.Entry<String, Object> entry2 : ((JavaBeanSerializer) objectWriter).getFieldValuesMap(obj).entrySet()) {
                                String key2 = entry2.getKey();
                                if (key2 instanceof String) {
                                    if (str.equals("/")) {
                                        sb = new StringBuilder();
                                        sb.append("/");
                                        sb.append(key2);
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append(str);
                                        sb.append("/");
                                        sb.append(key2);
                                    }
                                    paths(map, map2, sb.toString(), entry2.getValue(), serializeConfig);
                                }
                            }
                        } catch (Exception e) {
                            throw new JSONException("toJSON error", e);
                        }
                    }
                }
            }
        }
    }

    public String getPath() {
        return this.path;
    }

    /* loaded from: classes.dex */
    public static class JSONPathParser {
        private char ch;
        private boolean hasRefSegment;
        private int level;
        private final String path;
        private int pos;
        private static final String strArrayRegex = "'\\s*,\\s*'";
        private static final Pattern strArrayPatternx = Pattern.compile(strArrayRegex);

        static boolean isDigitFirst(char c) {
            return c == '-' || c == '+' || (c >= '0' && c <= '9');
        }

        public JSONPathParser(String str) {
            this.path = str;
            next();
        }

        void next() {
            String str = this.path;
            int i = this.pos;
            this.pos = i + 1;
            this.ch = str.charAt(i);
        }

        char getNextChar() {
            return this.path.charAt(this.pos);
        }

        boolean isEOF() {
            return this.pos >= this.path.length();
        }

        Segment readSegement() {
            char c;
            boolean z = true;
            if (this.level == 0 && this.path.length() == 1) {
                if (isDigitFirst(this.ch)) {
                    return new ArrayAccessSegment(this.ch - '0');
                }
                char c2 = this.ch;
                if ((c2 >= 'a' && c2 <= 'z') || ((c = this.ch) >= 'A' && c <= 'Z')) {
                    return new PropertySegment(Character.toString(this.ch), false);
                }
            }
            while (!isEOF()) {
                skipWhitespace();
                char c3 = this.ch;
                if (c3 == '$') {
                    next();
                } else if (c3 == '.' || c3 == '/') {
                    char c4 = this.ch;
                    next();
                    if (c4 == '.' && this.ch == '.') {
                        next();
                        int length = this.path.length();
                        int i = this.pos;
                        if (length > i + 3 && this.ch == '[' && this.path.charAt(i) == '*' && this.path.charAt(this.pos + 1) == ']' && this.path.charAt(this.pos + 2) == '.') {
                            next();
                            next();
                            next();
                            next();
                        }
                    } else {
                        z = false;
                    }
                    char c5 = this.ch;
                    if (c5 == '*') {
                        if (!isEOF()) {
                            next();
                        }
                        return z ? WildCardSegment.instance_deep : WildCardSegment.instance;
                    } else if (isDigitFirst(c5)) {
                        return parseArrayAccess(false);
                    } else {
                        String readName = readName();
                        if (this.ch != '(') {
                            return new PropertySegment(readName, z);
                        }
                        next();
                        if (this.ch == ')') {
                            if (!isEOF()) {
                                next();
                            }
                            if ("size".equals(readName) || "length".equals(readName)) {
                                return SizeSegment.instance;
                            }
                            if ("max".equals(readName)) {
                                return MaxSegment.instance;
                            }
                            if ("min".equals(readName)) {
                                return MinSegment.instance;
                            }
                            if ("keySet".equals(readName)) {
                                return KeySetSegment.instance;
                            }
                            throw new JSONPathException("not support jsonpath : " + this.path);
                        }
                        throw new JSONPathException("not support jsonpath : " + this.path);
                    }
                } else if (c3 == '[') {
                    return parseArrayAccess(true);
                } else {
                    if (this.level == 0) {
                        return new PropertySegment(readName(), false);
                    }
                    throw new JSONPathException("not support jsonpath : " + this.path);
                }
            }
            return null;
        }

        public final void skipWhitespace() {
            while (true) {
                char c = this.ch;
                if (c > ' ') {
                    return;
                }
                if (c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '\f' || c == '\b') {
                    next();
                } else {
                    return;
                }
            }
        }

        Segment parseArrayAccess(boolean z) {
            Object parseArrayAccessFilter = parseArrayAccessFilter(z);
            if (parseArrayAccessFilter instanceof Segment) {
                return (Segment) parseArrayAccessFilter;
            }
            return new FilterSegment((Filter) parseArrayAccessFilter);
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x0072, code lost:
            r3 = r23.pos - 1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        java.lang.Object parseArrayAccessFilter(boolean r24) {
            /*
                Method dump skipped, instructions count: 1635
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.JSONPathParser.parseArrayAccessFilter(boolean):java.lang.Object");
        }

        Filter filterRest(Filter filter) {
            boolean z = true;
            boolean z2 = this.ch == '&';
            if ((this.ch != '&' || getNextChar() != '&') && (this.ch != '|' || getNextChar() != '|')) {
                return filter;
            }
            next();
            next();
            if (this.ch == '(') {
                next();
            } else {
                z = false;
            }
            while (this.ch == ' ') {
                next();
            }
            FilterGroup filterGroup = new FilterGroup(filter, (Filter) parseArrayAccessFilter(false), z2);
            if (z && this.ch == ')') {
                next();
            }
            return filterGroup;
        }

        protected long readLongValue() {
            int i = this.pos - 1;
            char c = this.ch;
            if (c == '+' || c == '-') {
                next();
            }
            while (true) {
                char c2 = this.ch;
                if (c2 < '0' || c2 > '9') {
                    break;
                }
                next();
            }
            return Long.parseLong(this.path.substring(i, this.pos - 1));
        }

        protected double readDoubleValue(long j) {
            int i = this.pos - 1;
            next();
            while (true) {
                char c = this.ch;
                if (c < '0' || c > '9') {
                    break;
                }
                next();
            }
            return Double.parseDouble(this.path.substring(i, this.pos - 1)) + j;
        }

        protected Object readValue() {
            skipWhitespace();
            if (isDigitFirst(this.ch)) {
                return Long.valueOf(readLongValue());
            }
            char c = this.ch;
            if (c == '\"' || c == '\'') {
                return readString();
            }
            if (c != 'n') {
                throw new UnsupportedOperationException();
            } else if ("null".equals(readName())) {
                return null;
            } else {
                throw new JSONPathException(this.path);
            }
        }

        protected Operator readOp() {
            Operator operator;
            char c = this.ch;
            if (c == '=') {
                next();
                char c2 = this.ch;
                if (c2 == '~') {
                    next();
                    operator = Operator.REG_MATCH;
                } else if (c2 == '=') {
                    next();
                    operator = Operator.EQ;
                } else {
                    operator = Operator.EQ;
                }
            } else if (c == '!') {
                next();
                accept('=');
                operator = Operator.NE;
            } else if (c == '<') {
                next();
                if (this.ch == '=') {
                    next();
                    operator = Operator.LE;
                } else {
                    operator = Operator.LT;
                }
            } else if (c == '>') {
                next();
                if (this.ch == '=') {
                    next();
                    operator = Operator.GE;
                } else {
                    operator = Operator.GT;
                }
            } else {
                operator = null;
            }
            if (operator != null) {
                return operator;
            }
            String readName = readName();
            if ("not".equalsIgnoreCase(readName)) {
                skipWhitespace();
                String readName2 = readName();
                if ("like".equalsIgnoreCase(readName2)) {
                    return Operator.NOT_LIKE;
                }
                if ("rlike".equalsIgnoreCase(readName2)) {
                    return Operator.NOT_RLIKE;
                }
                if (ServerSetting.SERVER_IN.equalsIgnoreCase(readName2)) {
                    return Operator.NOT_IN;
                }
                if ("between".equalsIgnoreCase(readName2)) {
                    return Operator.NOT_BETWEEN;
                }
                throw new UnsupportedOperationException();
            } else if ("nin".equalsIgnoreCase(readName)) {
                return Operator.NOT_IN;
            } else {
                if ("like".equalsIgnoreCase(readName)) {
                    return Operator.LIKE;
                }
                if ("rlike".equalsIgnoreCase(readName)) {
                    return Operator.RLIKE;
                }
                if (ServerSetting.SERVER_IN.equalsIgnoreCase(readName)) {
                    return Operator.IN;
                }
                if ("between".equalsIgnoreCase(readName)) {
                    return Operator.BETWEEN;
                }
                throw new UnsupportedOperationException();
            }
        }

        String readName() {
            skipWhitespace();
            char c = this.ch;
            if (c == '\\' || Character.isJavaIdentifierStart(c)) {
                StringBuilder sb = new StringBuilder();
                while (!isEOF()) {
                    char c2 = this.ch;
                    if (c2 == '\\') {
                        next();
                        sb.append(this.ch);
                        if (isEOF()) {
                            return sb.toString();
                        }
                        next();
                    } else if (!Character.isJavaIdentifierPart(c2)) {
                        break;
                    } else {
                        sb.append(this.ch);
                        next();
                    }
                }
                if (isEOF() && Character.isJavaIdentifierPart(this.ch)) {
                    sb.append(this.ch);
                }
                return sb.toString();
            }
            throw new JSONPathException("illeal jsonpath syntax. " + this.path);
        }

        String readString() {
            char c = this.ch;
            next();
            int i = this.pos - 1;
            while (this.ch != c && !isEOF()) {
                next();
            }
            String substring = this.path.substring(i, isEOF() ? this.pos : this.pos - 1);
            accept(c);
            return substring;
        }

        void accept(char c) {
            if (this.ch != c) {
                throw new JSONPathException("expect '" + c + ", but '" + this.ch + LrcRow.SINGLE_QUOTE);
            } else if (!isEOF()) {
                next();
            }
        }

        public Segment[] explain() {
            String str = this.path;
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException();
            }
            Segment[] segmentArr = new Segment[8];
            while (true) {
                Segment readSegement = readSegement();
                if (readSegement == null) {
                    break;
                }
                if (readSegement instanceof PropertySegment) {
                    PropertySegment propertySegment = (PropertySegment) readSegement;
                    if (!propertySegment.deep && propertySegment.propertyName.equals("*")) {
                    }
                }
                int i = this.level;
                if (i == segmentArr.length) {
                    Segment[] segmentArr2 = new Segment[(i * 3) / 2];
                    System.arraycopy(segmentArr, 0, segmentArr2, 0, i);
                    segmentArr = segmentArr2;
                }
                int i2 = this.level;
                this.level = i2 + 1;
                segmentArr[i2] = readSegement;
            }
            int i3 = this.level;
            if (i3 == segmentArr.length) {
                return segmentArr;
            }
            Segment[] segmentArr3 = new Segment[i3];
            System.arraycopy(segmentArr, 0, segmentArr3, 0, i3);
            return segmentArr3;
        }

        Segment buildArraySegement(String str) {
            int length = str.length();
            char charAt = str.charAt(0);
            int i = 1;
            int i2 = length - 1;
            char charAt2 = str.charAt(i2);
            int indexOf = str.indexOf(44);
            int i3 = -1;
            if (str.length() > 2 && charAt == '\'' && charAt2 == '\'') {
                String substring = str.substring(1, i2);
                if (indexOf == -1 || !strArrayPatternx.matcher(str).find()) {
                    return new PropertySegment(substring, false);
                }
                return new MultiPropertySegment(substring.split(strArrayRegex));
            }
            int indexOf2 = str.indexOf(58);
            if (indexOf == -1 && indexOf2 == -1) {
                if (TypeUtils.isNumber(str)) {
                    try {
                        return new ArrayAccessSegment(Integer.parseInt(str));
                    } catch (NumberFormatException unused) {
                        return new PropertySegment(str, false);
                    }
                } else {
                    if (str.charAt(0) == '\"' && str.charAt(str.length() - 1) == '\"') {
                        str = str.substring(1, str.length() - 1);
                    }
                    return new PropertySegment(str, false);
                }
            } else if (indexOf != -1) {
                String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                int[] iArr = new int[split.length];
                for (int i4 = 0; i4 < split.length; i4++) {
                    iArr[i4] = Integer.parseInt(split[i4]);
                }
                return new MultiIndexSegment(iArr);
            } else if (indexOf2 != -1) {
                String[] split2 = str.split(Constants.COLON_SEPARATOR);
                int[] iArr2 = new int[split2.length];
                for (int i5 = 0; i5 < split2.length; i5++) {
                    String str2 = split2[i5];
                    if (str2.length() != 0) {
                        iArr2[i5] = Integer.parseInt(str2);
                    } else if (i5 == 0) {
                        iArr2[i5] = 0;
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
                int i6 = iArr2[0];
                if (iArr2.length > 1) {
                    i3 = iArr2[1];
                }
                if (iArr2.length == 3) {
                    i = iArr2[2];
                }
                if (i3 >= 0 && i3 < i6) {
                    throw new UnsupportedOperationException("end must greater than or equals start. start " + i6 + ",  end " + i3);
                } else if (i > 0) {
                    return new RangeSegment(i6, i3, i);
                } else {
                    throw new UnsupportedOperationException("step must greater than zero : " + i);
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    /* loaded from: classes.dex */
    public static class SizeSegment implements Segment {
        public static final SizeSegment instance = new SizeSegment();

        SizeSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Integer eval(JSONPath jSONPath, Object obj, Object obj2) {
            return Integer.valueOf(jSONPath.evalSize(obj2));
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes.dex */
    public static class MaxSegment implements Segment {
        public static final MaxSegment instance = new MaxSegment();

        MaxSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (obj2 instanceof Collection) {
                Object obj3 = null;
                for (Object obj4 : (Collection) obj2) {
                    if (obj4 != null && (obj3 == null || JSONPath.compare(obj3, obj4) < 0)) {
                        obj3 = obj4;
                    }
                }
                return obj3;
            }
            throw new UnsupportedOperationException();
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes.dex */
    public static class MinSegment implements Segment {
        public static final MinSegment instance = new MinSegment();

        MinSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (obj2 instanceof Collection) {
                Object obj3 = null;
                for (Object obj4 : (Collection) obj2) {
                    if (obj4 != null && (obj3 == null || JSONPath.compare(obj3, obj4) > 0)) {
                        obj3 = obj4;
                    }
                }
                return obj3;
            }
            throw new UnsupportedOperationException();
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    static int compare(Object obj, Object obj2) {
        Object obj3;
        if (obj.getClass() == obj2.getClass()) {
            return ((Comparable) obj).compareTo(obj2);
        }
        Class<?> cls = obj.getClass();
        Class<?> cls2 = obj2.getClass();
        if (cls == BigDecimal.class) {
            if (cls2 == Integer.class) {
                obj3 = new BigDecimal(((Integer) obj2).intValue());
            } else if (cls2 == Long.class) {
                obj3 = new BigDecimal(((Long) obj2).longValue());
            } else if (cls2 == Float.class) {
                obj3 = new BigDecimal(((Float) obj2).floatValue());
            } else {
                if (cls2 == Double.class) {
                    obj3 = new BigDecimal(((Double) obj2).doubleValue());
                }
                obj3 = obj2;
            }
            return ((Comparable) obj).compareTo(obj3);
        } else if (cls == Long.class) {
            if (cls2 == Integer.class) {
                obj3 = new Long(((Integer) obj2).intValue());
            } else if (cls2 == BigDecimal.class) {
                obj = new BigDecimal(((Long) obj).longValue());
                obj3 = obj2;
            } else if (cls2 == Float.class) {
                obj = new Float((float) ((Long) obj).longValue());
                obj3 = obj2;
            } else {
                if (cls2 == Double.class) {
                    obj = new Double(((Long) obj).longValue());
                    obj3 = obj2;
                }
                obj3 = obj2;
            }
            return ((Comparable) obj).compareTo(obj3);
        } else if (cls == Integer.class) {
            if (cls2 == Long.class) {
                obj = new Long(((Integer) obj).intValue());
                obj3 = obj2;
            } else if (cls2 == BigDecimal.class) {
                obj = new BigDecimal(((Integer) obj).intValue());
                obj3 = obj2;
            } else if (cls2 == Float.class) {
                obj = new Float(((Integer) obj).intValue());
                obj3 = obj2;
            } else {
                if (cls2 == Double.class) {
                    obj = new Double(((Integer) obj).intValue());
                    obj3 = obj2;
                }
                obj3 = obj2;
            }
            return ((Comparable) obj).compareTo(obj3);
        } else if (cls == Double.class) {
            if (cls2 == Integer.class) {
                obj3 = new Double(((Integer) obj2).intValue());
            } else if (cls2 == Long.class) {
                obj3 = new Double(((Long) obj2).longValue());
            } else {
                if (cls2 == Float.class) {
                    obj3 = new Double(((Float) obj2).floatValue());
                }
                obj3 = obj2;
            }
            return ((Comparable) obj).compareTo(obj3);
        } else {
            if (cls == Float.class) {
                if (cls2 == Integer.class) {
                    obj3 = new Float(((Integer) obj2).intValue());
                } else if (cls2 == Long.class) {
                    obj3 = new Float((float) ((Long) obj2).longValue());
                } else if (cls2 == Double.class) {
                    obj = new Double(((Float) obj).floatValue());
                    obj3 = obj2;
                }
                return ((Comparable) obj).compareTo(obj3);
            }
            obj3 = obj2;
            return ((Comparable) obj).compareTo(obj3);
        }
    }

    /* loaded from: classes.dex */
    public static class KeySetSegment implements Segment {
        public static final KeySetSegment instance = new KeySetSegment();

        KeySetSegment() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            return jSONPath.evalKeySet(obj2);
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes.dex */
    public static class PropertySegment implements Segment {
        private final boolean deep;
        private final String propertyName;
        private final long propertyNameHash;

        public PropertySegment(String str, boolean z) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.deep = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (!this.deep) {
                return jSONPath.getPropertyValue(obj2, this.propertyName, this.propertyNameHash);
            }
            ArrayList arrayList = new ArrayList();
            jSONPath.deepScan(obj2, this.propertyName, arrayList);
            return arrayList;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            Object obj;
            Object obj2;
            JSONArray jSONArray;
            Object obj3;
            JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
            if (this.deep && context.object == null) {
                context.object = new JSONArray();
            }
            if (jSONLexerBase.token() != 14) {
                boolean z = this.deep;
                if (z) {
                    while (true) {
                        int seekObjectToField = jSONLexerBase.seekObjectToField(this.propertyNameHash, this.deep);
                        if (seekObjectToField != -1) {
                            if (seekObjectToField == 3) {
                                if (context.eval) {
                                    switch (jSONLexerBase.token()) {
                                        case 2:
                                            obj = jSONLexerBase.integerValue();
                                            jSONLexerBase.nextToken(16);
                                            break;
                                        case 3:
                                            obj = jSONLexerBase.decimalValue();
                                            jSONLexerBase.nextToken(16);
                                            break;
                                        case 4:
                                            obj = jSONLexerBase.stringVal();
                                            jSONLexerBase.nextToken(16);
                                            break;
                                        default:
                                            obj = defaultJSONParser.parse();
                                            break;
                                    }
                                    if (context.eval) {
                                        if (context.object instanceof List) {
                                            List list = (List) context.object;
                                            if (list.size() != 0 || !(obj instanceof List)) {
                                                list.add(obj);
                                            } else {
                                                context.object = obj;
                                            }
                                        } else {
                                            context.object = obj;
                                        }
                                    }
                                }
                            } else if (seekObjectToField == 1 || seekObjectToField == 2) {
                                extract(jSONPath, defaultJSONParser, context);
                            }
                        } else {
                            return;
                        }
                    }
                } else if (jSONLexerBase.seekObjectToField(this.propertyNameHash, z) == 3 && context.eval) {
                    switch (jSONLexerBase.token()) {
                        case 2:
                            obj2 = jSONLexerBase.integerValue();
                            jSONLexerBase.nextToken(16);
                            break;
                        case 3:
                            obj2 = jSONLexerBase.decimalValue();
                            jSONLexerBase.nextToken(16);
                            break;
                        case 4:
                            obj2 = jSONLexerBase.stringVal();
                            jSONLexerBase.nextToken(16);
                            break;
                        default:
                            obj2 = defaultJSONParser.parse();
                            break;
                    }
                    if (context.eval) {
                        context.object = obj2;
                    }
                }
            } else if (!"*".equals(this.propertyName)) {
                jSONLexerBase.nextToken();
                if (this.deep) {
                    jSONArray = (JSONArray) context.object;
                } else {
                    jSONArray = new JSONArray();
                }
                while (true) {
                    int i = jSONLexerBase.token();
                    if (i == 12) {
                        boolean z2 = this.deep;
                        if (z2) {
                            extract(jSONPath, defaultJSONParser, context);
                        } else {
                            int seekObjectToField2 = jSONLexerBase.seekObjectToField(this.propertyNameHash, z2);
                            if (seekObjectToField2 == 3) {
                                int i2 = jSONLexerBase.token();
                                if (i2 == 2) {
                                    obj3 = jSONLexerBase.integerValue();
                                    jSONLexerBase.nextToken();
                                } else if (i2 != 4) {
                                    obj3 = defaultJSONParser.parse();
                                } else {
                                    obj3 = jSONLexerBase.stringVal();
                                    jSONLexerBase.nextToken();
                                }
                                jSONArray.add(obj3);
                                if (jSONLexerBase.token() == 13) {
                                    jSONLexerBase.nextToken();
                                } else {
                                    jSONLexerBase.skipObject(false);
                                }
                            } else if (seekObjectToField2 == -1) {
                                continue;
                            } else if (!this.deep) {
                                jSONLexerBase.skipObject(false);
                            } else {
                                throw new UnsupportedOperationException(jSONLexerBase.info());
                            }
                        }
                    } else if (i != 14) {
                        switch (i) {
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                                jSONLexerBase.nextToken();
                                break;
                        }
                    } else if (this.deep) {
                        extract(jSONPath, defaultJSONParser, context);
                    } else {
                        jSONLexerBase.skipObject(false);
                    }
                    if (jSONLexerBase.token() == 15) {
                        jSONLexerBase.nextToken();
                        if (!this.deep && jSONArray.size() > 0) {
                            context.object = jSONArray;
                            return;
                        }
                        return;
                    } else if (jSONLexerBase.token() == 16) {
                        jSONLexerBase.nextToken();
                    } else {
                        throw new JSONException("illegal json : " + jSONLexerBase.info());
                    }
                }
            }
        }

        public void setValue(JSONPath jSONPath, Object obj, Object obj2) {
            if (this.deep) {
                jSONPath.deepSet(obj, this.propertyName, this.propertyNameHash, obj2);
            } else {
                jSONPath.setPropertyValue(obj, this.propertyName, this.propertyNameHash, obj2);
            }
        }

        public boolean remove(JSONPath jSONPath, Object obj) {
            return jSONPath.removePropertyValue(obj, this.propertyName, this.deep);
        }
    }

    /* loaded from: classes.dex */
    public static class MultiPropertySegment implements Segment {
        private final String[] propertyNames;
        private final long[] propertyNamesHash;

        public MultiPropertySegment(String[] strArr) {
            this.propertyNames = strArr;
            this.propertyNamesHash = new long[strArr.length];
            int i = 0;
            while (true) {
                long[] jArr = this.propertyNamesHash;
                if (i < jArr.length) {
                    jArr[i] = TypeUtils.fnv1a_64(strArr[i]);
                    i++;
                } else {
                    return;
                }
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            ArrayList arrayList = new ArrayList(this.propertyNames.length);
            int i = 0;
            while (true) {
                String[] strArr = this.propertyNames;
                if (i >= strArr.length) {
                    return arrayList;
                }
                arrayList.add(jSONPath.getPropertyValue(obj2, strArr[i], this.propertyNamesHash[i]));
                i++;
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            JSONArray jSONArray;
            Object obj;
            JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
            if (context.object == null) {
                jSONArray = new JSONArray();
                context.object = jSONArray;
            } else {
                jSONArray = (JSONArray) context.object;
            }
            for (int size = jSONArray.size(); size < this.propertyNamesHash.length; size++) {
                jSONArray.add(null);
            }
            do {
                int seekObjectToField = jSONLexerBase.seekObjectToField(this.propertyNamesHash);
                if (jSONLexerBase.matchStat == 3) {
                    switch (jSONLexerBase.token()) {
                        case 2:
                            obj = jSONLexerBase.integerValue();
                            jSONLexerBase.nextToken(16);
                            break;
                        case 3:
                            obj = jSONLexerBase.decimalValue();
                            jSONLexerBase.nextToken(16);
                            break;
                        case 4:
                            obj = jSONLexerBase.stringVal();
                            jSONLexerBase.nextToken(16);
                            break;
                        default:
                            obj = defaultJSONParser.parse();
                            break;
                    }
                    jSONArray.set(seekObjectToField, obj);
                } else {
                    return;
                }
            } while (jSONLexerBase.token() == 16);
        }
    }

    /* loaded from: classes.dex */
    public static class WildCardSegment implements Segment {
        public static final WildCardSegment instance = new WildCardSegment(false);
        public static final WildCardSegment instance_deep = new WildCardSegment(true);
        private boolean deep;

        private WildCardSegment(boolean z) {
            this.deep = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (!this.deep) {
                return jSONPath.getPropertyValues(obj2);
            }
            ArrayList arrayList = new ArrayList();
            jSONPath.deepGetPropertyValues(obj2, arrayList);
            return arrayList;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            if (context.eval) {
                Object parse = defaultJSONParser.parse();
                if (this.deep) {
                    ArrayList arrayList = new ArrayList();
                    jSONPath.deepGetPropertyValues(parse, arrayList);
                    context.object = arrayList;
                    return;
                } else if (parse instanceof JSONObject) {
                    Collection<Object> values = ((JSONObject) parse).values();
                    JSONArray jSONArray = new JSONArray(values.size());
                    for (Object obj : values) {
                        jSONArray.add(obj);
                    }
                    context.object = jSONArray;
                    return;
                } else if (parse instanceof JSONArray) {
                    context.object = parse;
                    return;
                }
            }
            throw new JSONException("TODO");
        }
    }

    /* loaded from: classes.dex */
    public static class ArrayAccessSegment implements Segment {
        private final int index;

        public ArrayAccessSegment(int i) {
            this.index = i;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            return jSONPath.getArrayItem(obj2, this.index);
        }

        public boolean setValue(JSONPath jSONPath, Object obj, Object obj2) {
            return jSONPath.setArrayItem(jSONPath, obj, this.index, obj2);
        }

        public boolean remove(JSONPath jSONPath, Object obj) {
            return jSONPath.removeArrayItem(jSONPath, obj, this.index);
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            if (((JSONLexerBase) defaultJSONParser.lexer).seekArrayToItem(this.index) && context.eval) {
                context.object = defaultJSONParser.parse();
            }
        }
    }

    /* loaded from: classes.dex */
    public static class MultiIndexSegment implements Segment {
        private final int[] indexes;

        public MultiIndexSegment(int[] iArr) {
            this.indexes = iArr;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            JSONArray jSONArray = new JSONArray(this.indexes.length);
            int i = 0;
            while (true) {
                int[] iArr = this.indexes;
                if (i >= iArr.length) {
                    return jSONArray;
                }
                jSONArray.add(jSONPath.getArrayItem(obj2, iArr[i]));
                i++;
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            if (context.eval) {
                Object parse = defaultJSONParser.parse();
                if (parse instanceof List) {
                    int[] iArr = this.indexes;
                    int[] iArr2 = new int[iArr.length];
                    boolean z = false;
                    System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
                    if (iArr2[0] >= 0) {
                        z = true;
                    }
                    List list = (List) parse;
                    if (z) {
                        for (int size = list.size() - 1; size >= 0; size--) {
                            if (Arrays.binarySearch(iArr2, size) < 0) {
                                list.remove(size);
                            }
                        }
                        context.object = list;
                        return;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes.dex */
    public static class RangeSegment implements Segment {
        private final int end;
        private final int start;
        private final int step;

        public RangeSegment(int i, int i2, int i3) {
            this.start = i;
            this.end = i2;
            this.step = i3;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            int intValue = SizeSegment.instance.eval(jSONPath, obj, obj2).intValue();
            int i = this.start;
            if (i < 0) {
                i += intValue;
            }
            int i2 = this.end;
            if (i2 < 0) {
                i2 += intValue;
            }
            int i3 = ((i2 - i) / this.step) + 1;
            if (i3 == -1) {
                return null;
            }
            ArrayList arrayList = new ArrayList(i3);
            while (i <= i2 && i < intValue) {
                arrayList.add(jSONPath.getArrayItem(obj2, i));
                i += this.step;
            }
            return arrayList;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes.dex */
    public static class NotNullSegement implements Filter {
        private final String propertyName;
        private final long propertyNameHash;

        public NotNullSegement(String str) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            return jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash) != null;
        }
    }

    /* loaded from: classes.dex */
    public static class NullSegement implements Filter {
        private final String propertyName;
        private final long propertyNameHash;

        public NullSegement(String str) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            return jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash) == null;
        }
    }

    /* loaded from: classes.dex */
    public static class ValueSegment implements Filter {
        private boolean eq;
        private final String propertyName;
        private final long propertyNameHash;
        private final Object value;

        public ValueSegment(String str, Object obj, boolean z) {
            this.eq = true;
            if (obj != null) {
                this.propertyName = str;
                this.propertyNameHash = TypeUtils.fnv1a_64(str);
                this.value = obj;
                this.eq = z;
                return;
            }
            throw new IllegalArgumentException("value is null");
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            boolean equals = this.value.equals(jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash));
            return !this.eq ? !equals : equals;
        }
    }

    /* loaded from: classes.dex */
    public static class IntInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final long[] values;

        public IntInSegement(String str, long[] jArr, boolean z) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.values = jArr;
            this.not = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                for (long j : this.values) {
                    if (j == longExtractValue) {
                        return !this.not;
                    }
                }
            }
            return this.not;
        }
    }

    /* loaded from: classes.dex */
    public static class IntBetweenSegement implements Filter {
        private final long endValue;
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final long startValue;

        public IntBetweenSegement(String str, long j, long j2, boolean z) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.startValue = j;
            this.endValue = j2;
            this.not = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                if (longExtractValue >= this.startValue && longExtractValue <= this.endValue) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    /* loaded from: classes.dex */
    public static class IntObjInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final Long[] values;

        public IntObjInSegement(String str, Long[] lArr, boolean z) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.values = lArr;
            this.not = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            int i = 0;
            if (propertyValue == null) {
                Long[] lArr = this.values;
                int length = lArr.length;
                while (i < length) {
                    if (lArr[i] == null) {
                        return !this.not;
                    }
                    i++;
                }
                return this.not;
            }
            if (propertyValue instanceof Number) {
                long longExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                Long[] lArr2 = this.values;
                int length2 = lArr2.length;
                while (i < length2) {
                    Long l = lArr2[i];
                    if (l != null && l.longValue() == longExtractValue) {
                        return !this.not;
                    }
                    i++;
                }
            }
            return this.not;
        }
    }

    /* loaded from: classes.dex */
    public static class StringInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final String[] values;

        public StringInSegement(String str, String[] strArr, boolean z) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.values = strArr;
            this.not = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            String[] strArr = this.values;
            for (String str : strArr) {
                if (str == propertyValue) {
                    return !this.not;
                }
                if (str != null && str.equals(propertyValue)) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    /* loaded from: classes.dex */
    public static class IntOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final long value;
        private BigDecimal valueDecimal;
        private Double valueDouble;
        private Float valueFloat;

        public IntOpSegement(String str, long j, Operator operator) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.value = j;
            this.op = operator;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            if (propertyValue instanceof BigDecimal) {
                if (this.valueDecimal == null) {
                    this.valueDecimal = BigDecimal.valueOf(this.value);
                }
                int compareTo = this.valueDecimal.compareTo((BigDecimal) propertyValue);
                switch (this.op) {
                    case EQ:
                        return compareTo == 0;
                    case NE:
                        return compareTo != 0;
                    case GE:
                        return compareTo <= 0;
                    case GT:
                        return compareTo < 0;
                    case LE:
                        return compareTo >= 0;
                    case LT:
                        return compareTo > 0;
                    default:
                        return false;
                }
            } else if (propertyValue instanceof Float) {
                if (this.valueFloat == null) {
                    this.valueFloat = Float.valueOf((float) this.value);
                }
                int compareTo2 = this.valueFloat.compareTo((Float) propertyValue);
                switch (this.op) {
                    case EQ:
                        return compareTo2 == 0;
                    case NE:
                        return compareTo2 != 0;
                    case GE:
                        return compareTo2 <= 0;
                    case GT:
                        return compareTo2 < 0;
                    case LE:
                        return compareTo2 >= 0;
                    case LT:
                        return compareTo2 > 0;
                    default:
                        return false;
                }
            } else if (propertyValue instanceof Double) {
                if (this.valueDouble == null) {
                    this.valueDouble = Double.valueOf(this.value);
                }
                int compareTo3 = this.valueDouble.compareTo((Double) propertyValue);
                switch (this.op) {
                    case EQ:
                        return compareTo3 == 0;
                    case NE:
                        return compareTo3 != 0;
                    case GE:
                        return compareTo3 <= 0;
                    case GT:
                        return compareTo3 < 0;
                    case LE:
                        return compareTo3 >= 0;
                    case LT:
                        return compareTo3 > 0;
                    default:
                        return false;
                }
            } else {
                long longExtractValue = TypeUtils.longExtractValue((Number) propertyValue);
                switch (this.op) {
                    case EQ:
                        return longExtractValue == this.value;
                    case NE:
                        return longExtractValue != this.value;
                    case GE:
                        return longExtractValue >= this.value;
                    case GT:
                        return longExtractValue > this.value;
                    case LE:
                        return longExtractValue <= this.value;
                    case LT:
                        return longExtractValue < this.value;
                    default:
                        return false;
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static class DoubleOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final double value;

        public DoubleOpSegement(String str, double d, Operator operator) {
            this.propertyName = str;
            this.value = d;
            this.op = operator;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            double doubleValue = ((Number) propertyValue).doubleValue();
            switch (this.op) {
                case EQ:
                    return doubleValue == this.value;
                case NE:
                    return doubleValue != this.value;
                case GE:
                    return doubleValue >= this.value;
                case GT:
                    return doubleValue > this.value;
                case LE:
                    return doubleValue <= this.value;
                case LT:
                    return doubleValue < this.value;
                default:
                    return false;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class RefOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final Segment refSgement;

        public RefOpSegement(String str, Segment segment, Operator operator) {
            this.propertyName = str;
            this.refSgement = segment;
            this.op = operator;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            Object eval = this.refSgement.eval(jSONPath, obj, obj);
            if ((eval instanceof Integer) || (eval instanceof Long) || (eval instanceof Short) || (eval instanceof Byte)) {
                long longExtractValue = TypeUtils.longExtractValue((Number) eval);
                if ((propertyValue instanceof Integer) || (propertyValue instanceof Long) || (propertyValue instanceof Short) || (propertyValue instanceof Byte)) {
                    long longExtractValue2 = TypeUtils.longExtractValue((Number) propertyValue);
                    switch (this.op) {
                        case EQ:
                            return longExtractValue2 == longExtractValue;
                        case NE:
                            return longExtractValue2 != longExtractValue;
                        case GE:
                            return longExtractValue2 >= longExtractValue;
                        case GT:
                            return longExtractValue2 > longExtractValue;
                        case LE:
                            return longExtractValue2 <= longExtractValue;
                        case LT:
                            return longExtractValue2 < longExtractValue;
                    }
                } else if (propertyValue instanceof BigDecimal) {
                    int compareTo = BigDecimal.valueOf(longExtractValue).compareTo((BigDecimal) propertyValue);
                    switch (this.op) {
                        case EQ:
                            return compareTo == 0;
                        case NE:
                            return compareTo != 0;
                        case GE:
                            return compareTo <= 0;
                        case GT:
                            return compareTo < 0;
                        case LE:
                            return compareTo >= 0;
                        case LT:
                            return compareTo > 0;
                        default:
                            return false;
                    }
                }
            }
            throw new UnsupportedOperationException();
        }
    }

    /* loaded from: classes.dex */
    public static class MatchSegement implements Filter {
        private final String[] containsValues;
        private final String endsWithValue;
        private final int minLength;
        private final boolean not;
        private final String propertyName;
        private final long propertyNameHash;
        private final String startsWithValue;

        public MatchSegement(String str, String str2, String str3, String[] strArr, boolean z) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.startsWithValue = str2;
            this.endsWithValue = str3;
            this.containsValues = strArr;
            this.not = z;
            int length = str2 != null ? str2.length() + 0 : 0;
            length = str3 != null ? length + str3.length() : length;
            if (strArr != null) {
                for (String str4 : strArr) {
                    length += str4.length();
                }
            }
            this.minLength = length;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            int i;
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            String obj4 = propertyValue.toString();
            if (obj4.length() < this.minLength) {
                return this.not;
            }
            String str = this.startsWithValue;
            if (str == null) {
                i = 0;
            } else if (!obj4.startsWith(str)) {
                return this.not;
            } else {
                i = this.startsWithValue.length() + 0;
            }
            String[] strArr = this.containsValues;
            if (strArr != null) {
                for (String str2 : strArr) {
                    int indexOf = obj4.indexOf(str2, i);
                    if (indexOf == -1) {
                        return this.not;
                    }
                    i = indexOf + str2.length();
                }
            }
            String str3 = this.endsWithValue;
            if (str3 == null || obj4.endsWith(str3)) {
                return !this.not;
            }
            return this.not;
        }
    }

    /* loaded from: classes.dex */
    public static class RlikeSegement implements Filter {
        private final boolean not;
        private final Pattern pattern;
        private final String propertyName;
        private final long propertyNameHash;

        public RlikeSegement(String str, String str2, boolean z) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.pattern = Pattern.compile(str2);
            this.not = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            boolean matches = this.pattern.matcher(propertyValue.toString()).matches();
            return this.not ? !matches : matches;
        }
    }

    /* loaded from: classes.dex */
    public static class StringOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long propertyNameHash;
        private final String value;

        public StringOpSegement(String str, String str2, Operator operator) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.value = str2;
            this.op = operator;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (this.op == Operator.EQ) {
                return this.value.equals(propertyValue);
            }
            if (this.op == Operator.NE) {
                return !this.value.equals(propertyValue);
            }
            if (propertyValue == null) {
                return false;
            }
            int compareTo = this.value.compareTo(propertyValue.toString());
            return this.op == Operator.GE ? compareTo <= 0 : this.op == Operator.GT ? compareTo < 0 : this.op == Operator.LE ? compareTo >= 0 : this.op == Operator.LT && compareTo > 0;
        }
    }

    /* loaded from: classes.dex */
    public static class RegMatchSegement implements Filter {
        private final Operator op;
        private final Pattern pattern;
        private final String propertyName;
        private final long propertyNameHash;

        public RegMatchSegement(String str, Pattern pattern, Operator operator) {
            this.propertyName = str;
            this.propertyNameHash = TypeUtils.fnv1a_64(str);
            this.pattern = pattern;
            this.op = operator;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            Object propertyValue = jSONPath.getPropertyValue(obj3, this.propertyName, this.propertyNameHash);
            if (propertyValue == null) {
                return false;
            }
            return this.pattern.matcher(propertyValue.toString()).matches();
        }
    }

    /* loaded from: classes.dex */
    public static class FilterSegment implements Segment {
        private final Filter filter;

        public FilterSegment(Filter filter) {
            this.filter = filter;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public Object eval(JSONPath jSONPath, Object obj, Object obj2) {
            if (obj2 == null) {
                return null;
            }
            JSONArray jSONArray = new JSONArray();
            if (obj2 instanceof Iterable) {
                for (Object obj3 : (Iterable) obj2) {
                    if (this.filter.apply(jSONPath, obj, obj2, obj3)) {
                        jSONArray.add(obj3);
                    }
                }
                return jSONArray;
            } else if (this.filter.apply(jSONPath, obj, obj2, obj2)) {
                return obj2;
            } else {
                return null;
            }
        }

        @Override // com.alibaba.fastjson.JSONPath.Segment
        public void extract(JSONPath jSONPath, DefaultJSONParser defaultJSONParser, Context context) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(JSONPath jSONPath, Object obj, Object obj2) {
            if (obj2 == null || !(obj2 instanceof Iterable)) {
                return false;
            }
            Iterator it = ((Iterable) obj2).iterator();
            while (it.hasNext()) {
                if (this.filter.apply(jSONPath, obj, obj2, it.next())) {
                    it.remove();
                }
            }
            return true;
        }
    }

    /* loaded from: classes.dex */
    public static class FilterGroup implements Filter {
        private boolean and;
        private List<Filter> fitlers = new ArrayList(2);

        public FilterGroup(Filter filter, Filter filter2, boolean z) {
            this.fitlers.add(filter);
            this.fitlers.add(filter2);
            this.and = z;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3) {
            if (this.and) {
                for (Filter filter : this.fitlers) {
                    if (!filter.apply(jSONPath, obj, obj2, obj3)) {
                        return false;
                    }
                }
                return true;
            }
            for (Filter filter2 : this.fitlers) {
                if (filter2.apply(jSONPath, obj, obj2, obj3)) {
                    return true;
                }
            }
            return false;
        }
    }

    protected Object getArrayItem(Object obj, int i) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            if (i >= 0) {
                if (i < list.size()) {
                    return list.get(i);
                }
                return null;
            } else if (Math.abs(i) <= list.size()) {
                return list.get(list.size() + i);
            } else {
                return null;
            }
        } else if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            if (i >= 0) {
                if (i < length) {
                    return Array.get(obj, i);
                }
                return null;
            } else if (Math.abs(i) <= length) {
                return Array.get(obj, length + i);
            } else {
                return null;
            }
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            Object obj2 = map.get(Integer.valueOf(i));
            return obj2 == null ? map.get(Integer.toString(i)) : obj2;
        } else if (obj instanceof Collection) {
            int i2 = 0;
            for (Object obj3 : (Collection) obj) {
                if (i2 == i) {
                    return obj3;
                }
                i2++;
            }
            return null;
        } else if (i == 0) {
            return obj;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean setArrayItem(JSONPath jSONPath, Object obj, int i, Object obj2) {
        if (obj instanceof List) {
            List list = (List) obj;
            if (i >= 0) {
                list.set(i, obj2);
            } else {
                list.set(list.size() + i, obj2);
            }
            return true;
        }
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            int length = Array.getLength(obj);
            if (i >= 0) {
                if (i < length) {
                    Array.set(obj, i, obj2);
                }
            } else if (Math.abs(i) <= length) {
                Array.set(obj, length + i, obj2);
            }
            return true;
        }
        throw new JSONPathException("unsupported set operation." + cls);
    }

    public boolean removeArrayItem(JSONPath jSONPath, Object obj, int i) {
        if (obj instanceof List) {
            List list = (List) obj;
            if (i < 0) {
                int size = list.size() + i;
                if (size < 0) {
                    return false;
                }
                list.remove(size);
                return true;
            } else if (i >= list.size()) {
                return false;
            } else {
                list.remove(i);
                return true;
            }
        } else {
            Class<?> cls = obj.getClass();
            throw new JSONPathException("unsupported set operation." + cls);
        }
    }

    protected Collection<Object> getPropertyValues(Object obj) {
        if (obj == null) {
            return null;
        }
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(obj.getClass());
        if (javaBeanSerializer != null) {
            try {
                return javaBeanSerializer.getFieldValues(obj);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        } else if (obj instanceof Map) {
            return ((Map) obj).values();
        } else {
            if (obj instanceof Collection) {
                return (Collection) obj;
            }
            throw new UnsupportedOperationException();
        }
    }

    protected void deepGetPropertyValues(Object obj, List<Object> list) {
        Collection fieldValues;
        Class<?> cls = obj.getClass();
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(cls);
        if (javaBeanSerializer != null) {
            try {
                fieldValues = javaBeanSerializer.getFieldValues(obj);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        } else if (obj instanceof Map) {
            fieldValues = ((Map) obj).values();
        } else {
            fieldValues = obj instanceof Collection ? (Collection) obj : null;
        }
        if (fieldValues != null) {
            for (Object obj2 : fieldValues) {
                if (obj2 == null || ParserConfig.isPrimitive2(obj2.getClass())) {
                    list.add(obj2);
                } else {
                    deepGetPropertyValues(obj2, list);
                }
            }
            return;
        }
        throw new UnsupportedOperationException(cls.getName());
    }

    static boolean eq(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        if (obj.getClass() == obj2.getClass()) {
            return obj.equals(obj2);
        }
        if (!(obj instanceof Number)) {
            return obj.equals(obj2);
        }
        if (obj2 instanceof Number) {
            return eqNotNull((Number) obj, (Number) obj2);
        }
        return false;
    }

    static boolean eqNotNull(Number number, Number number2) {
        Class<?> cls = number.getClass();
        boolean isInt = isInt(cls);
        Class<?> cls2 = number2.getClass();
        boolean isInt2 = isInt(cls2);
        if (number instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) number;
            if (isInt2) {
                return bigDecimal.equals(BigDecimal.valueOf(TypeUtils.longExtractValue(number2)));
            }
        }
        if (isInt) {
            if (isInt2) {
                return number.longValue() == number2.longValue();
            }
            if (number2 instanceof BigInteger) {
                return BigInteger.valueOf(number.longValue()).equals((BigInteger) number);
            }
        }
        if (isInt2 && (number instanceof BigInteger)) {
            return ((BigInteger) number).equals(BigInteger.valueOf(TypeUtils.longExtractValue(number2)));
        }
        boolean isDouble = isDouble(cls);
        boolean isDouble2 = isDouble(cls2);
        return ((isDouble && isDouble2) || ((isDouble && isInt2) || (isDouble2 && isInt))) && number.doubleValue() == number2.doubleValue();
    }

    protected static boolean isDouble(Class<?> cls) {
        return cls == Float.class || cls == Double.class;
    }

    protected static boolean isInt(Class<?> cls) {
        return cls == Byte.class || cls == Short.class || cls == Integer.class || cls == Long.class;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.lang.Object getPropertyValue(java.lang.Object r8, java.lang.String r9, long r10) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.JSONPath.getPropertyValue(java.lang.Object, java.lang.String, long):java.lang.Object");
    }

    protected void deepScan(Object obj, String str, List<Object> list) {
        if (obj != null) {
            if (obj instanceof Map) {
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    Object value = entry.getValue();
                    if (str.equals(entry.getKey())) {
                        if (value instanceof Collection) {
                            list.addAll((Collection) value);
                        } else {
                            list.add(value);
                        }
                    } else if (value != null && !ParserConfig.isPrimitive2(value.getClass())) {
                        deepScan(value, str, list);
                    }
                }
            } else if (obj instanceof Collection) {
                for (Object obj2 : (Collection) obj) {
                    if (!ParserConfig.isPrimitive2(obj2.getClass())) {
                        deepScan(obj2, str, list);
                    }
                }
            } else {
                JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(obj.getClass());
                if (javaBeanSerializer != null) {
                    try {
                        FieldSerializer fieldSerializer = javaBeanSerializer.getFieldSerializer(str);
                        if (fieldSerializer != null) {
                            try {
                                try {
                                    list.add(fieldSerializer.getPropertyValueDirect(obj));
                                } catch (InvocationTargetException e) {
                                    throw new JSONException("getFieldValue error." + str, e);
                                }
                            } catch (IllegalAccessException e2) {
                                throw new JSONException("getFieldValue error." + str, e2);
                            }
                        } else {
                            for (Object obj3 : javaBeanSerializer.getFieldValues(obj)) {
                                deepScan(obj3, str, list);
                            }
                        }
                    } catch (Exception e3) {
                        throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + str, e3);
                    }
                } else if (obj instanceof List) {
                    List list2 = (List) obj;
                    for (int i = 0; i < list2.size(); i++) {
                        deepScan(list2.get(i), str, list);
                    }
                }
            }
        }
    }

    protected void deepSet(Object obj, String str, long j, Object obj2) {
        if (obj != null) {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.containsKey(str)) {
                    map.get(str);
                    map.put(str, obj2);
                    return;
                }
                for (Object obj3 : map.values()) {
                    deepSet(obj3, str, j, obj2);
                }
                return;
            }
            Class<?> cls = obj.getClass();
            JavaBeanDeserializer javaBeanDeserializer = getJavaBeanDeserializer(cls);
            if (javaBeanDeserializer != null) {
                try {
                    FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(str);
                    if (fieldDeserializer != null) {
                        fieldDeserializer.setValue(obj, obj2);
                        return;
                    }
                    for (Object obj4 : getJavaBeanSerializer(cls).getObjectFieldValues(obj)) {
                        deepSet(obj4, str, j, obj2);
                    }
                } catch (Exception e) {
                    throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + str, e);
                }
            } else if (obj instanceof List) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    deepSet(list.get(i), str, j, obj2);
                }
            }
        }
    }

    protected boolean setPropertyValue(Object obj, String str, long j, Object obj2) {
        if (obj instanceof Map) {
            ((Map) obj).put(str, obj2);
            return true;
        } else if (obj instanceof List) {
            for (Object obj3 : (List) obj) {
                if (obj3 != null) {
                    setPropertyValue(obj3, str, j, obj2);
                }
            }
            return true;
        } else {
            ObjectDeserializer deserializer = this.parserConfig.getDeserializer(obj.getClass());
            JavaBeanDeserializer javaBeanDeserializer = null;
            if (deserializer instanceof JavaBeanDeserializer) {
                javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
            }
            if (javaBeanDeserializer != null) {
                FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(j);
                if (fieldDeserializer == null) {
                    return false;
                }
                fieldDeserializer.setValue(obj, obj2);
                return true;
            }
            throw new UnsupportedOperationException();
        }
    }

    protected boolean removePropertyValue(Object obj, String str, boolean z) {
        boolean z2 = true;
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.remove(str) == null) {
                z2 = false;
            }
            if (z) {
                for (Object obj2 : map.values()) {
                    removePropertyValue(obj2, str, z);
                }
            }
            return z2;
        }
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(obj.getClass());
        JavaBeanDeserializer javaBeanDeserializer = deserializer instanceof JavaBeanDeserializer ? (JavaBeanDeserializer) deserializer : null;
        if (javaBeanDeserializer != null) {
            FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(str);
            if (fieldDeserializer != null) {
                fieldDeserializer.setValue(obj, (String) null);
            } else {
                z2 = false;
            }
            if (z) {
                for (Object obj3 : getPropertyValues(obj)) {
                    if (obj3 != null) {
                        removePropertyValue(obj3, str, z);
                    }
                }
            }
            return z2;
        } else if (z) {
            return false;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected JavaBeanSerializer getJavaBeanSerializer(Class<?> cls) {
        ObjectSerializer objectWriter = this.serializeConfig.getObjectWriter(cls);
        if (objectWriter instanceof JavaBeanSerializer) {
            return (JavaBeanSerializer) objectWriter;
        }
        return null;
    }

    protected JavaBeanDeserializer getJavaBeanDeserializer(Class<?> cls) {
        ObjectDeserializer deserializer = this.parserConfig.getDeserializer(cls);
        if (deserializer instanceof JavaBeanDeserializer) {
            return (JavaBeanDeserializer) deserializer;
        }
        return null;
    }

    int evalSize(Object obj) {
        if (obj == null) {
            return -1;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size();
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        }
        if (obj instanceof Map) {
            int i = 0;
            for (Object obj2 : ((Map) obj).values()) {
                if (obj2 != null) {
                    i++;
                }
            }
            return i;
        }
        JavaBeanSerializer javaBeanSerializer = getJavaBeanSerializer(obj.getClass());
        if (javaBeanSerializer == null) {
            return -1;
        }
        try {
            return javaBeanSerializer.getSize(obj);
        } catch (Exception e) {
            throw new JSONPathException("evalSize error : " + this.path, e);
        }
    }

    Set<?> evalKeySet(Object obj) {
        JavaBeanSerializer javaBeanSerializer;
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            return ((Map) obj).keySet();
        }
        if ((obj instanceof Collection) || (obj instanceof Object[]) || obj.getClass().isArray() || (javaBeanSerializer = getJavaBeanSerializer(obj.getClass())) == null) {
            return null;
        }
        try {
            return javaBeanSerializer.getFieldNames(obj);
        } catch (Exception e) {
            throw new JSONPathException("evalKeySet error : " + this.path, e);
        }
    }

    @Override // com.alibaba.fastjson.JSONAware
    public String toJSONString() {
        return JSON.toJSONString(this.path);
    }

    public static Object reserveToArray(Object obj, String... strArr) {
        JSONArray jSONArray = new JSONArray();
        if (strArr == null || strArr.length == 0) {
            return jSONArray;
        }
        for (String str : strArr) {
            JSONPath compile = compile(str);
            compile.init();
            jSONArray.add(compile.eval(obj));
        }
        return jSONArray;
    }

    public static Object reserveToObject(Object obj, String... strArr) {
        Object eval;
        if (strArr == null || strArr.length == 0) {
            return obj;
        }
        JSONObject jSONObject = new JSONObject(true);
        for (String str : strArr) {
            JSONPath compile = compile(str);
            compile.init();
            Segment[] segmentArr = compile.segments;
            if ((segmentArr[segmentArr.length - 1] instanceof PropertySegment) && (eval = compile.eval(obj)) != null) {
                compile.set(jSONObject, eval);
            }
        }
        return jSONObject;
    }
}
