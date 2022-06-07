package org.eclipse.jetty.util.ajax;

import com.fasterxml.jackson.core.JsonPointer;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class JSON {
    private Map<String, Convertor> _convertors = new ConcurrentHashMap();
    private int _stringBufferSize = 1024;
    static final Logger LOG = Log.getLogger(JSON.class);
    public static final JSON DEFAULT = new JSON();

    /* loaded from: classes5.dex */
    public interface Convertible {
        void fromJSON(Map map);

        void toJSON(Output output);
    }

    /* loaded from: classes5.dex */
    public interface Convertor {
        Object fromJSON(Map map);

        void toJSON(Object obj, Output output);
    }

    /* loaded from: classes5.dex */
    public interface Generator {
        void addJSON(Appendable appendable);
    }

    /* loaded from: classes5.dex */
    public interface Output {
        void add(Object obj);

        void add(String str, double d);

        void add(String str, long j);

        void add(String str, Object obj);

        void add(String str, boolean z);

        void addClass(Class cls);
    }

    /* loaded from: classes5.dex */
    public interface Source {
        boolean hasNext();

        char next();

        char peek();

        char[] scratchBuffer();
    }

    @Deprecated
    public static void setDefault(JSON json) {
    }

    protected JSON contextFor(String str) {
        return this;
    }

    protected JSON contextForArray() {
        return this;
    }

    public int getStringBufferSize() {
        return this._stringBufferSize;
    }

    public void setStringBufferSize(int i) {
        this._stringBufferSize = i;
    }

    public static void registerConvertor(Class cls, Convertor convertor) {
        DEFAULT.addConvertor(cls, convertor);
    }

    public static JSON getDefault() {
        return DEFAULT;
    }

    public static String toString(Object obj) {
        StringBuilder sb = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.append(sb, obj);
        return sb.toString();
    }

    public static String toString(Map map) {
        StringBuilder sb = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.appendMap(sb, map);
        return sb.toString();
    }

    public static String toString(Object[] objArr) {
        StringBuilder sb = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.appendArray(sb, objArr);
        return sb.toString();
    }

    public static Object parse(String str) {
        return DEFAULT.parse((Source) new StringSource(str), false);
    }

    public static Object parse(String str, boolean z) {
        return DEFAULT.parse(new StringSource(str), z);
    }

    public static Object parse(Reader reader) throws IOException {
        return DEFAULT.parse((Source) new ReaderSource(reader), false);
    }

    public static Object parse(Reader reader, boolean z) throws IOException {
        return DEFAULT.parse(new ReaderSource(reader), z);
    }

    @Deprecated
    public static Object parse(InputStream inputStream) throws IOException {
        return DEFAULT.parse((Source) new StringSource(IO.toString(inputStream)), false);
    }

    @Deprecated
    public static Object parse(InputStream inputStream, boolean z) throws IOException {
        return DEFAULT.parse(new StringSource(IO.toString(inputStream)), z);
    }

    public String toJSON(Object obj) {
        StringBuilder sb = new StringBuilder(getStringBufferSize());
        append(sb, obj);
        return sb.toString();
    }

    public Object fromJSON(String str) {
        return parse(new StringSource(str));
    }

    @Deprecated
    public void append(StringBuffer stringBuffer, Object obj) {
        append((Appendable) stringBuffer, obj);
    }

    public void append(Appendable appendable, Object obj) {
        try {
            if (obj == null) {
                appendable.append("null");
            } else if (obj instanceof Convertible) {
                appendJSON(appendable, (Convertible) obj);
            } else if (obj instanceof Generator) {
                appendJSON(appendable, (Generator) obj);
            } else if (obj instanceof Map) {
                appendMap(appendable, (Map) obj);
            } else if (obj instanceof Collection) {
                appendArray(appendable, (Collection) obj);
            } else if (obj.getClass().isArray()) {
                appendArray(appendable, obj);
            } else if (obj instanceof Number) {
                appendNumber(appendable, (Number) obj);
            } else if (obj instanceof Boolean) {
                appendBoolean(appendable, (Boolean) obj);
            } else if (obj instanceof Character) {
                appendString(appendable, obj.toString());
            } else if (obj instanceof String) {
                appendString(appendable, (String) obj);
            } else {
                Convertor convertor = getConvertor(obj.getClass());
                if (convertor != null) {
                    appendJSON(appendable, convertor, obj);
                } else {
                    appendString(appendable, obj.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendNull(StringBuffer stringBuffer) {
        appendNull((Appendable) stringBuffer);
    }

    public void appendNull(Appendable appendable) {
        try {
            appendable.append("null");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Convertor convertor, Object obj) {
        appendJSON((Appendable) stringBuffer, convertor, obj);
    }

    public void appendJSON(Appendable appendable, final Convertor convertor, final Object obj) {
        appendJSON(appendable, new Convertible() { // from class: org.eclipse.jetty.util.ajax.JSON.1
            @Override // org.eclipse.jetty.util.ajax.JSON.Convertible
            public void fromJSON(Map map) {
            }

            @Override // org.eclipse.jetty.util.ajax.JSON.Convertible
            public void toJSON(Output output) {
                convertor.toJSON(obj, output);
            }
        });
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Convertible convertible) {
        appendJSON((Appendable) stringBuffer, convertible);
    }

    public void appendJSON(Appendable appendable, Convertible convertible) {
        ConvertableOutput convertableOutput = new ConvertableOutput(appendable);
        convertible.toJSON(convertableOutput);
        convertableOutput.complete();
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Generator generator) {
        generator.addJSON(stringBuffer);
    }

    public void appendJSON(Appendable appendable, Generator generator) {
        generator.addJSON(appendable);
    }

    @Deprecated
    public void appendMap(StringBuffer stringBuffer, Map<?, ?> map) {
        appendMap((Appendable) stringBuffer, map);
    }

    public void appendMap(Appendable appendable, Map<?, ?> map) {
        try {
            if (map == null) {
                appendNull(appendable);
                return;
            }
            appendable.append('{');
            Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> next = it.next();
                QuotedStringTokenizer.quote(appendable, next.getKey().toString());
                appendable.append(':');
                append(appendable, next.getValue());
                if (it.hasNext()) {
                    appendable.append(StringUtil.COMMA);
                }
            }
            appendable.append('}');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendArray(StringBuffer stringBuffer, Collection collection) {
        appendArray((Appendable) stringBuffer, collection);
    }

    public void appendArray(Appendable appendable, Collection collection) {
        try {
            if (collection == null) {
                appendNull(appendable);
                return;
            }
            appendable.append('[');
            boolean z = true;
            for (Object obj : collection) {
                if (!z) {
                    appendable.append(StringUtil.COMMA);
                }
                z = false;
                append(appendable, obj);
            }
            appendable.append(']');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendArray(StringBuffer stringBuffer, Object obj) {
        appendArray((Appendable) stringBuffer, obj);
    }

    public void appendArray(Appendable appendable, Object obj) {
        try {
            if (obj == null) {
                appendNull(appendable);
                return;
            }
            appendable.append('[');
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                if (i != 0) {
                    appendable.append(StringUtil.COMMA);
                }
                append(appendable, Array.get(obj, i));
            }
            appendable.append(']');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendBoolean(StringBuffer stringBuffer, Boolean bool) {
        appendBoolean((Appendable) stringBuffer, bool);
    }

    public void appendBoolean(Appendable appendable, Boolean bool) {
        try {
            if (bool == null) {
                appendNull(appendable);
            } else {
                appendable.append(bool.booleanValue() ? "true" : "false");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendNumber(StringBuffer stringBuffer, Number number) {
        appendNumber((Appendable) stringBuffer, number);
    }

    public void appendNumber(Appendable appendable, Number number) {
        try {
            if (number == null) {
                appendNull(appendable);
            } else {
                appendable.append(String.valueOf(number));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendString(StringBuffer stringBuffer, String str) {
        appendString((Appendable) stringBuffer, str);
    }

    public void appendString(Appendable appendable, String str) {
        if (str == null) {
            appendNull(appendable);
        } else {
            QuotedStringTokenizer.quote(appendable, str);
        }
    }

    protected String toString(char[] cArr, int i, int i2) {
        return new String(cArr, i, i2);
    }

    protected Map<String, Object> newMap() {
        return new HashMap();
    }

    protected Object[] newArray(int i) {
        return new Object[i];
    }

    protected Object convertTo(Class cls, Map map) {
        if (cls == null || !Convertible.class.isAssignableFrom(cls)) {
            Convertor convertor = getConvertor(cls);
            return convertor != null ? convertor.fromJSON(map) : map;
        }
        try {
            Convertible convertible = (Convertible) cls.newInstance();
            convertible.fromJSON(map);
            return convertible;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addConvertor(Class cls, Convertor convertor) {
        this._convertors.put(cls.getName(), convertor);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
        r5 = r5.getSuperclass();
        r0 = r4._convertors.get(r5.getName());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected org.eclipse.jetty.util.ajax.JSON.Convertor getConvertor(java.lang.Class r5) {
        /*
            r4 = this;
            java.util.Map<java.lang.String, org.eclipse.jetty.util.ajax.JSON$Convertor> r0 = r4._convertors
            java.lang.String r1 = r5.getName()
            java.lang.Object r0 = r0.get(r1)
            org.eclipse.jetty.util.ajax.JSON$Convertor r0 = (org.eclipse.jetty.util.ajax.JSON.Convertor) r0
            if (r0 != 0) goto L_0x0016
            org.eclipse.jetty.util.ajax.JSON r1 = org.eclipse.jetty.util.ajax.JSON.DEFAULT
            if (r4 == r1) goto L_0x0016
            org.eclipse.jetty.util.ajax.JSON$Convertor r0 = r1.getConvertor(r5)
        L_0x0016:
            if (r0 != 0) goto L_0x004f
            if (r5 == 0) goto L_0x004f
            java.lang.Class<java.lang.Object> r1 = java.lang.Object.class
            if (r5 == r1) goto L_0x004f
            java.lang.Class[] r1 = r5.getInterfaces()
            r2 = 0
        L_0x0023:
            if (r0 != 0) goto L_0x003c
            if (r1 == 0) goto L_0x003c
            int r3 = r1.length
            if (r2 >= r3) goto L_0x003c
            java.util.Map<java.lang.String, org.eclipse.jetty.util.ajax.JSON$Convertor> r0 = r4._convertors
            int r3 = r2 + 1
            r2 = r1[r2]
            java.lang.String r2 = r2.getName()
            java.lang.Object r0 = r0.get(r2)
            org.eclipse.jetty.util.ajax.JSON$Convertor r0 = (org.eclipse.jetty.util.ajax.JSON.Convertor) r0
            r2 = r3
            goto L_0x0023
        L_0x003c:
            if (r0 != 0) goto L_0x0016
            java.lang.Class r5 = r5.getSuperclass()
            java.util.Map<java.lang.String, org.eclipse.jetty.util.ajax.JSON$Convertor> r0 = r4._convertors
            java.lang.String r1 = r5.getName()
            java.lang.Object r0 = r0.get(r1)
            org.eclipse.jetty.util.ajax.JSON$Convertor r0 = (org.eclipse.jetty.util.ajax.JSON.Convertor) r0
            goto L_0x0016
        L_0x004f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.getConvertor(java.lang.Class):org.eclipse.jetty.util.ajax.JSON$Convertor");
    }

    public void addConvertorFor(String str, Convertor convertor) {
        this._convertors.put(str, convertor);
    }

    public Convertor getConvertorFor(String str) {
        JSON json;
        Convertor convertor = this._convertors.get(str);
        return (convertor != null || this == (json = DEFAULT)) ? convertor : json.getConvertorFor(str);
    }

    public Object parse(Source source, boolean z) {
        if (!z) {
            return parse(source);
        }
        Object obj = null;
        char c = 0;
        boolean z2 = true;
        while (source.hasNext()) {
            char peek = source.peek();
            if (c == 1) {
                if (peek != '*') {
                    if (peek == '/') {
                        c = 65535;
                    }
                } else if (z2) {
                    c = 0;
                    z2 = true;
                } else {
                    c = 2;
                }
            } else if (c > 1) {
                if (peek == '*') {
                    c = 3;
                } else if (peek != '/') {
                    c = 2;
                } else if (c != 3) {
                    c = 2;
                } else if (z2) {
                    return obj;
                } else {
                    c = 0;
                }
            } else if (c < 0) {
                if (peek == '\n' || peek == '\r') {
                    c = 0;
                }
            } else if (!Character.isWhitespace(peek)) {
                if (peek == '/') {
                    c = 1;
                } else if (peek == '*') {
                    c = 3;
                } else if (obj == null) {
                    obj = parse(source);
                }
            }
            source.next();
        }
        return obj;
    }

    public Object parse(Source source) {
        char c = 0;
        while (source.hasNext()) {
            char peek = source.peek();
            if (c == 1) {
                if (peek == '*') {
                    c = 2;
                } else if (peek == '/') {
                    c = 65535;
                }
            } else if (c > 1) {
                c = peek != '*' ? peek != '/' ? (char) 2 : c == 3 ? (char) 0 : (char) 2 : (char) 3;
            } else if (c < 0) {
                if (peek == '\n' || peek == '\r') {
                    c = 0;
                }
            } else if (peek == '\"') {
                return parseString(source);
            } else {
                if (peek == '-') {
                    return parseNumber(source);
                }
                if (peek == '/') {
                    c = 1;
                } else if (peek == 'N') {
                    complete("NaN", source);
                    return null;
                } else if (peek == '[') {
                    return parseArray(source);
                } else {
                    if (peek == 'f') {
                        complete("false", source);
                        return Boolean.FALSE;
                    } else if (peek == 'n') {
                        complete("null", source);
                        return null;
                    } else if (peek == '{') {
                        return parseObject(source);
                    } else {
                        switch (peek) {
                            case 't':
                                complete("true", source);
                                return Boolean.TRUE;
                            case 'u':
                                complete("undefined", source);
                                return null;
                            default:
                                if (Character.isDigit(peek)) {
                                    return parseNumber(source);
                                }
                                if (Character.isWhitespace(peek)) {
                                    break;
                                } else {
                                    return handleUnknown(source, peek);
                                }
                        }
                    }
                }
            }
            source.next();
        }
        return null;
    }

    protected Object handleUnknown(Source source, char c) {
        throw new IllegalStateException("unknown char '" + c + "'(" + ((int) c) + ") in " + source);
    }

    protected Object parseObject(Source source) {
        if (source.next() == '{') {
            Map<String, Object> newMap = newMap();
            char seekTo = seekTo("\"}", source);
            while (true) {
                if (!source.hasNext()) {
                    break;
                } else if (seekTo == '}') {
                    source.next();
                    break;
                } else {
                    String parseString = parseString(source);
                    seekTo(':', source);
                    source.next();
                    newMap.put(parseString, contextFor(parseString).parse(source));
                    seekTo(",}", source);
                    if (source.next() == '}') {
                        break;
                    }
                    seekTo = seekTo("\"}", source);
                }
            }
            String str = (String) newMap.get("class");
            if (str != null) {
                try {
                    return convertTo(Loader.loadClass(JSON.class, str), newMap);
                } catch (ClassNotFoundException e) {
                    LOG.warn(e);
                }
            }
            return newMap;
        }
        throw new IllegalStateException();
    }

    protected Object parseArray(Source source) {
        if (source.next() == '[') {
            boolean z = true;
            ArrayList arrayList = null;
            Object obj = null;
            int i = 0;
            while (source.hasNext()) {
                char peek = source.peek();
                if (peek != ',') {
                    if (peek == ']') {
                        source.next();
                        switch (i) {
                            case 0:
                                return newArray(0);
                            case 1:
                                Object[] newArray = newArray(1);
                                Array.set(newArray, 0, obj);
                                return newArray;
                            default:
                                return arrayList.toArray(newArray(arrayList.size()));
                        }
                    } else if (Character.isWhitespace(peek)) {
                        source.next();
                    } else {
                        int i2 = i + 1;
                        if (i == 0) {
                            obj = contextForArray().parse(source);
                            i = i2;
                            z = false;
                        } else if (arrayList == null) {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(obj);
                            arrayList2.add(contextForArray().parse(source));
                            obj = null;
                            arrayList = arrayList2;
                            i = i2;
                            z = false;
                        } else {
                            arrayList.add(contextForArray().parse(source));
                            obj = null;
                            i = i2;
                            z = false;
                        }
                    }
                } else if (!z) {
                    source.next();
                    z = true;
                } else {
                    throw new IllegalStateException();
                }
            }
            throw new IllegalStateException("unexpected end of array");
        }
        throw new IllegalStateException();
    }

    protected String parseString(Source source) {
        boolean z;
        StringBuilder sb;
        StringBuilder sb2;
        if (source.next() == '\"') {
            char[] scratchBuffer = source.scratchBuffer();
            if (scratchBuffer != null) {
                int i = 0;
                z = false;
                while (true) {
                    if (!source.hasNext()) {
                        sb2 = null;
                    } else if (i >= scratchBuffer.length) {
                        StringBuilder sb3 = new StringBuilder(scratchBuffer.length * 2);
                        sb3.append(scratchBuffer, 0, i);
                        sb2 = sb3;
                    } else {
                        char next = source.next();
                        if (z) {
                            if (next == '\"') {
                                i++;
                                scratchBuffer[i] = '\"';
                            } else if (next == '/') {
                                i++;
                                scratchBuffer[i] = JsonPointer.SEPARATOR;
                            } else if (next == '\\') {
                                i++;
                                scratchBuffer[i] = '\\';
                            } else if (next == 'b') {
                                i++;
                                scratchBuffer[i] = '\b';
                            } else if (next == 'f') {
                                i++;
                                scratchBuffer[i] = '\f';
                            } else if (next == 'n') {
                                i++;
                                scratchBuffer[i] = '\n';
                            } else if (next != 'r') {
                                switch (next) {
                                    case 't':
                                        i++;
                                        scratchBuffer[i] = '\t';
                                        break;
                                    case 'u':
                                        i++;
                                        scratchBuffer[i] = (char) ((TypeUtil.convertHexDigit((byte) source.next()) << 12) + (TypeUtil.convertHexDigit((byte) source.next()) << 8) + (TypeUtil.convertHexDigit((byte) source.next()) << 4) + TypeUtil.convertHexDigit((byte) source.next()));
                                        break;
                                    default:
                                        i++;
                                        scratchBuffer[i] = next;
                                        break;
                                }
                            } else {
                                i++;
                                scratchBuffer[i] = '\r';
                            }
                            z = false;
                        } else if (next == '\\') {
                            z = true;
                        } else if (next == '\"') {
                            return toString(scratchBuffer, 0, i);
                        } else {
                            i++;
                            scratchBuffer[i] = next;
                        }
                    }
                }
                if (sb2 == null) {
                    return toString(scratchBuffer, 0, i);
                }
                sb = sb2;
            } else {
                sb = new StringBuilder(getStringBufferSize());
                z = false;
            }
            while (source.hasNext()) {
                char next2 = source.next();
                if (z) {
                    if (next2 == '\"') {
                        sb.append('\"');
                    } else if (next2 == '/') {
                        sb.append(JsonPointer.SEPARATOR);
                    } else if (next2 == '\\') {
                        sb.append('\\');
                    } else if (next2 == 'b') {
                        sb.append('\b');
                    } else if (next2 == 'f') {
                        sb.append('\f');
                    } else if (next2 == 'n') {
                        sb.append('\n');
                    } else if (next2 != 'r') {
                        switch (next2) {
                            case 't':
                                sb.append('\t');
                                break;
                            case 'u':
                                sb.append((char) ((TypeUtil.convertHexDigit((byte) source.next()) << 12) + (TypeUtil.convertHexDigit((byte) source.next()) << 8) + (TypeUtil.convertHexDigit((byte) source.next()) << 4) + TypeUtil.convertHexDigit((byte) source.next())));
                                break;
                            default:
                                sb.append(next2);
                                break;
                        }
                    } else {
                        sb.append('\r');
                    }
                    z = false;
                } else if (next2 == '\\') {
                    z = true;
                } else if (next2 == '\"') {
                    return sb.toString();
                } else {
                    sb.append(next2);
                }
            }
            return sb.toString();
        }
        throw new IllegalStateException();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0063 A[LOOP:1: B:27:0x0063->B:35:0x007a, LOOP_START] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Number parseNumber(org.eclipse.jetty.util.ajax.JSON.Source r10) {
        /*
            r9 = this;
            r0 = 0
            r2 = 0
            r4 = r2
            r2 = r0
        L_0x0005:
            boolean r5 = r10.hasNext()
            r6 = 101(0x65, float:1.42E-43)
            r7 = 69
            r8 = 43
            if (r5 == 0) goto L_0x0056
            char r5 = r10.peek()
            if (r5 == r8) goto L_0x0045
            if (r5 == r7) goto L_0x002d
            if (r5 == r6) goto L_0x002d
            switch(r5) {
                case 45: goto L_0x0045;
                case 46: goto L_0x002d;
                default: goto L_0x001e;
            }
        L_0x001e:
            switch(r5) {
                case 48: goto L_0x0022;
                case 49: goto L_0x0022;
                case 50: goto L_0x0022;
                case 51: goto L_0x0022;
                case 52: goto L_0x0022;
                case 53: goto L_0x0022;
                case 54: goto L_0x0022;
                case 55: goto L_0x0022;
                case 56: goto L_0x0022;
                case 57: goto L_0x0022;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x0056
        L_0x0022:
            r6 = 10
            long r2 = r2 * r6
            int r5 = r5 + (-48)
            long r5 = (long) r5
            long r2 = r2 + r5
            r10.next()
            goto L_0x0005
        L_0x002d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 16
            r0.<init>(r1)
            if (r4 == 0) goto L_0x003b
            r1 = 45
            r0.append(r1)
        L_0x003b:
            r0.append(r2)
            r0.append(r5)
            r10.next()
            goto L_0x0057
        L_0x0045:
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 != 0) goto L_0x004e
            r4 = 1
            r10.next()
            goto L_0x0005
        L_0x004e:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "bad number"
            r10.<init>(r0)
            throw r10
        L_0x0056:
            r0 = 0
        L_0x0057:
            if (r0 != 0) goto L_0x0063
            if (r4 == 0) goto L_0x005e
            r0 = -1
            long r2 = r2 * r0
        L_0x005e:
            java.lang.Long r10 = java.lang.Long.valueOf(r2)
            return r10
        L_0x0063:
            boolean r1 = r10.hasNext()
            if (r1 == 0) goto L_0x0081
            char r1 = r10.peek()
            if (r1 == r8) goto L_0x007a
            if (r1 == r7) goto L_0x007a
            if (r1 == r6) goto L_0x007a
            switch(r1) {
                case 45: goto L_0x007a;
                case 46: goto L_0x007a;
                default: goto L_0x0076;
            }
        L_0x0076:
            switch(r1) {
                case 48: goto L_0x007a;
                case 49: goto L_0x007a;
                case 50: goto L_0x007a;
                case 51: goto L_0x007a;
                case 52: goto L_0x007a;
                case 53: goto L_0x007a;
                case 54: goto L_0x007a;
                case 55: goto L_0x007a;
                case 56: goto L_0x007a;
                case 57: goto L_0x007a;
                default: goto L_0x0079;
            }
        L_0x0079:
            goto L_0x0081
        L_0x007a:
            r0.append(r1)
            r10.next()
            goto L_0x0063
        L_0x0081:
            java.lang.Double r10 = new java.lang.Double
            java.lang.String r0 = r0.toString()
            r10.<init>(r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parseNumber(org.eclipse.jetty.util.ajax.JSON$Source):java.lang.Number");
    }

    protected void seekTo(char c, Source source) {
        while (source.hasNext()) {
            char peek = source.peek();
            if (peek != c) {
                if (Character.isWhitespace(peek)) {
                    source.next();
                } else {
                    throw new IllegalStateException("Unexpected '" + peek + " while seeking '" + c + LrcRow.SINGLE_QUOTE);
                }
            } else {
                return;
            }
        }
        throw new IllegalStateException("Expected '" + c + LrcRow.SINGLE_QUOTE);
    }

    protected char seekTo(String str, Source source) {
        while (source.hasNext()) {
            char peek = source.peek();
            if (str.indexOf(peek) >= 0) {
                return peek;
            }
            if (Character.isWhitespace(peek)) {
                source.next();
            } else {
                throw new IllegalStateException("Unexpected '" + peek + "' while seeking one of '" + str + LrcRow.SINGLE_QUOTE);
            }
        }
        throw new IllegalStateException("Expected one of '" + str + LrcRow.SINGLE_QUOTE);
    }

    protected static void complete(String str, Source source) {
        int i = 0;
        while (source.hasNext() && i < str.length()) {
            char next = source.next();
            i++;
            if (next != str.charAt(i)) {
                throw new IllegalStateException("Unexpected '" + next + " while seeking  \"" + str + "\"");
            }
        }
        if (i < str.length()) {
            throw new IllegalStateException("Expected \"" + str + "\"");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public final class ConvertableOutput implements Output {
        private final Appendable _buffer;
        char c;

        private ConvertableOutput(Appendable appendable) {
            this.c = '{';
            this._buffer = appendable;
        }

        public void complete() {
            try {
                if (this.c == '{') {
                    this._buffer.append("{}");
                } else if (this.c != 0) {
                    this._buffer.append("}");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(Object obj) {
            if (this.c != 0) {
                JSON.this.append(this._buffer, obj);
                this.c = (char) 0;
                return;
            }
            throw new IllegalStateException();
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void addClass(Class cls) {
            try {
                if (this.c != 0) {
                    this._buffer.append(this.c);
                    this._buffer.append("\"class\":");
                    JSON.this.append(this._buffer, cls.getName());
                    this.c = StringUtil.COMMA;
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, Object obj) {
            try {
                if (this.c != 0) {
                    this._buffer.append(this.c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.append(this._buffer, obj);
                    this.c = StringUtil.COMMA;
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, double d) {
            try {
                if (this.c != 0) {
                    this._buffer.append(this.c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendNumber(this._buffer, new Double(d));
                    this.c = StringUtil.COMMA;
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, long j) {
            try {
                if (this.c != 0) {
                    this._buffer.append(this.c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendNumber(this._buffer, Long.valueOf(j));
                    this.c = StringUtil.COMMA;
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Output
        public void add(String str, boolean z) {
            try {
                if (this.c != 0) {
                    this._buffer.append(this.c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendBoolean(this._buffer, z ? Boolean.TRUE : Boolean.FALSE);
                    this.c = StringUtil.COMMA;
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class StringSource implements Source {
        private int index;
        private char[] scratch;
        private final String string;

        public StringSource(String str) {
            this.string = str;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public boolean hasNext() {
            if (this.index < this.string.length()) {
                return true;
            }
            this.scratch = null;
            return false;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char next() {
            String str = this.string;
            int i = this.index;
            this.index = i + 1;
            return str.charAt(i);
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char peek() {
            return this.string.charAt(this.index);
        }

        public String toString() {
            return this.string.substring(0, this.index) + "|||" + this.string.substring(this.index);
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[this.string.length()];
            }
            return this.scratch;
        }
    }

    /* loaded from: classes5.dex */
    public static class ReaderSource implements Source {
        private int _next = -1;
        private Reader _reader;
        private char[] scratch;

        public ReaderSource(Reader reader) {
            this._reader = reader;
        }

        public void setReader(Reader reader) {
            this._reader = reader;
            this._next = -1;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public boolean hasNext() {
            getNext();
            if (this._next >= 0) {
                return true;
            }
            this.scratch = null;
            return false;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char next() {
            getNext();
            char c = (char) this._next;
            this._next = -1;
            return c;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char peek() {
            getNext();
            return (char) this._next;
        }

        private void getNext() {
            if (this._next < 0) {
                try {
                    this._next = this._reader.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Source
        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[1024];
            }
            return this.scratch;
        }
    }

    /* loaded from: classes5.dex */
    public static class Literal implements Generator {
        private String _json;

        public Literal(String str) {
            if (JSON.LOG.isDebugEnabled()) {
                JSON.parse(str);
            }
            this._json = str;
        }

        public String toString() {
            return this._json;
        }

        @Override // org.eclipse.jetty.util.ajax.JSON.Generator
        public void addJSON(Appendable appendable) {
            try {
                appendable.append(this._json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
