package org.json;

import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/* loaded from: classes3.dex */
public class JSONObject {
    public static final Object NULL = new a();
    private Map a;

    /* loaded from: classes3.dex */
    private static final class a {
        protected final Object clone() {
            return this;
        }

        public boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public String toString() {
            return "null";
        }

        private a() {
        }
    }

    public JSONObject() {
        this.a = new HashMap();
    }

    public JSONObject(JSONObject jSONObject, String[] strArr) throws JSONException {
        this();
        for (int i = 0; i < strArr.length; i++) {
            putOnce(strArr[i], jSONObject.opt(strArr[i]));
        }
    }

    public JSONObject(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() == '{') {
            while (true) {
                char nextClean = jSONTokener.nextClean();
                if (nextClean == 0) {
                    throw jSONTokener.syntaxError("A JSONObject text must end with '}'");
                } else if (nextClean != '}') {
                    jSONTokener.back();
                    String obj = jSONTokener.nextValue().toString();
                    char nextClean2 = jSONTokener.nextClean();
                    if (nextClean2 == '=') {
                        if (jSONTokener.next() != '>') {
                            jSONTokener.back();
                        }
                    } else if (nextClean2 != ':') {
                        throw jSONTokener.syntaxError("Expected a ':' after a key");
                    }
                    putOnce(obj, jSONTokener.nextValue());
                    char nextClean3 = jSONTokener.nextClean();
                    if (nextClean3 == ',' || nextClean3 == ';') {
                        if (jSONTokener.nextClean() != '}') {
                            jSONTokener.back();
                        } else {
                            return;
                        }
                    } else if (nextClean3 != '}') {
                        throw jSONTokener.syntaxError("Expected a ',' or '}'");
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        } else {
            throw jSONTokener.syntaxError("A JSONObject text must begin with '{'");
        }
    }

    public JSONObject(Map map) {
        this.a = map == null ? new HashMap() : map;
    }

    public JSONObject(Map map, boolean z) {
        this.a = new HashMap();
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                this.a.put(entry.getKey(), new JSONObject(entry.getValue(), z));
            }
        }
    }

    public JSONObject(Object obj) {
        this();
        a(obj, false);
    }

    public JSONObject(Object obj, boolean z) {
        this();
        a(obj, z);
    }

    private void a(Object obj, boolean z) {
        Class<?> cls = obj.getClass();
        if (cls.getClassLoader() == null) {
            z = false;
        }
        Method[] methods = z ? cls.getMethods() : cls.getDeclaredMethods();
        for (Method method : methods) {
            try {
                String name = method.getName();
                String str = "";
                if (name.startsWith(BluetoothConstants.GET)) {
                    str = name.substring(3);
                } else if (name.startsWith(ai.ae)) {
                    str = name.substring(2);
                }
                if (str.length() > 0 && Character.isUpperCase(str.charAt(0)) && method.getParameterTypes().length == 0) {
                    if (str.length() == 1) {
                        str = str.toLowerCase();
                    } else if (!Character.isUpperCase(str.charAt(1))) {
                        str = str.substring(0, 1).toLowerCase() + str.substring(1);
                    }
                    Object invoke = method.invoke(obj, null);
                    if (invoke == null) {
                        this.a.put(str, NULL);
                    } else if (invoke.getClass().isArray()) {
                        this.a.put(str, new JSONArray(invoke, z));
                    } else if (invoke instanceof Collection) {
                        this.a.put(str, new JSONArray((Collection) invoke, z));
                    } else if (invoke instanceof Map) {
                        this.a.put(str, new JSONObject((Map) invoke, z));
                    } else if (a((Class) invoke.getClass())) {
                        this.a.put(str, invoke);
                    } else {
                        if (!invoke.getClass().getPackage().getName().startsWith("java") && invoke.getClass().getClassLoader() != null) {
                            this.a.put(str, new JSONObject(invoke, z));
                        }
                        this.a.put(str, invoke.toString());
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean a(Class cls) {
        return cls.isPrimitive() || cls.isAssignableFrom(Byte.class) || cls.isAssignableFrom(Short.class) || cls.isAssignableFrom(Integer.class) || cls.isAssignableFrom(Long.class) || cls.isAssignableFrom(Float.class) || cls.isAssignableFrom(Double.class) || cls.isAssignableFrom(Character.class) || cls.isAssignableFrom(String.class) || cls.isAssignableFrom(Boolean.class);
    }

    public JSONObject(Object obj, String[] strArr) {
        this();
        Class<?> cls = obj.getClass();
        for (String str : strArr) {
            try {
                putOpt(str, cls.getField(str).get(obj));
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject(String str) throws JSONException {
        this(new JSONTokener(str));
    }

    public JSONObject accumulate(String str, Object obj) throws JSONException {
        a(obj);
        Object opt = opt(str);
        if (opt == null) {
            if (obj instanceof JSONArray) {
                obj = new JSONArray().put(obj);
            }
            put(str, obj);
        } else if (opt instanceof JSONArray) {
            ((JSONArray) opt).put(obj);
        } else {
            put(str, new JSONArray().put(opt).put(obj));
        }
        return this;
    }

    public JSONObject append(String str, Object obj) throws JSONException {
        a(obj);
        Object opt = opt(str);
        if (opt == null) {
            put(str, new JSONArray().put(obj));
        } else if (opt instanceof JSONArray) {
            put(str, ((JSONArray) opt).put(obj));
        } else {
            throw new JSONException("JSONObject[" + str + "] is not a JSONArray.");
        }
        return this;
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return "null";
        }
        String d2 = Double.toString(d);
        if (d2.indexOf(46) <= 0 || d2.indexOf(101) >= 0 || d2.indexOf(69) >= 0) {
            return d2;
        }
        while (d2.endsWith("0")) {
            d2 = d2.substring(0, d2.length() - 1);
        }
        return d2.endsWith(".") ? d2.substring(0, d2.length() - 1) : d2;
    }

    public Object get(String str) throws JSONException {
        Object opt = opt(str);
        if (opt != null) {
            return opt;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] not found.");
    }

    public boolean getBoolean(String str) throws JSONException {
        Object obj = get(str);
        if (obj.equals(Boolean.FALSE)) {
            return false;
        }
        boolean z = obj instanceof String;
        if (z && ((String) obj).equalsIgnoreCase("false")) {
            return false;
        }
        if (obj.equals(Boolean.TRUE)) {
            return true;
        }
        if (z && ((String) obj).equalsIgnoreCase("true")) {
            return true;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a Boolean.");
    }

    public double getDouble(String str) throws JSONException {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).doubleValue() : Double.valueOf((String) obj).doubleValue();
        } catch (Exception unused) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not a number.");
        }
    }

    public int getInt(String str) throws JSONException {
        Object obj = get(str);
        return obj instanceof Number ? ((Number) obj).intValue() : (int) getDouble(str);
    }

    public JSONArray getJSONArray(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONObject.");
    }

    public long getLong(String str) throws JSONException {
        Object obj = get(str);
        return obj instanceof Number ? ((Number) obj).longValue() : (long) getDouble(str);
    }

    public static String[] getNames(JSONObject jSONObject) {
        int length = jSONObject.length();
        if (length == 0) {
            return null;
        }
        Iterator keys = jSONObject.keys();
        String[] strArr = new String[length];
        int i = 0;
        while (keys.hasNext()) {
            strArr[i] = (String) keys.next();
            i++;
        }
        return strArr;
    }

    public static String[] getNames(Object obj) {
        Field[] fields;
        int length;
        if (obj == null || (length = (fields = obj.getClass().getFields()).length) == 0) {
            return null;
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = fields[i].getName();
        }
        return strArr;
    }

    public String getString(String str) throws JSONException {
        return get(str).toString();
    }

    public boolean has(String str) {
        return this.a.containsKey(str);
    }

    public boolean isNull(String str) {
        return NULL.equals(opt(str));
    }

    public Iterator keys() {
        return this.a.keySet().iterator();
    }

    public int length() {
        return this.a.size();
    }

    public JSONArray names() {
        JSONArray jSONArray = new JSONArray();
        Iterator keys = keys();
        while (keys.hasNext()) {
            jSONArray.put(keys.next());
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        return jSONArray;
    }

    public static String numberToString(Number number) throws JSONException {
        if (number != null) {
            a(number);
            String obj = number.toString();
            if (obj.indexOf(46) <= 0 || obj.indexOf(101) >= 0 || obj.indexOf(69) >= 0) {
                return obj;
            }
            while (obj.endsWith("0")) {
                obj = obj.substring(0, obj.length() - 1);
            }
            return obj.endsWith(".") ? obj.substring(0, obj.length() - 1) : obj;
        }
        throw new JSONException("Null pointer");
    }

    public Object opt(String str) {
        if (str == null) {
            return null;
        }
        return this.a.get(str);
    }

    public boolean optBoolean(String str) {
        return optBoolean(str, false);
    }

    public boolean optBoolean(String str, boolean z) {
        try {
            return getBoolean(str);
        } catch (Exception unused) {
            return z;
        }
    }

    public JSONObject put(String str, Collection collection) throws JSONException {
        put(str, new JSONArray(collection));
        return this;
    }

    public double optDouble(String str) {
        return optDouble(str, Double.NaN);
    }

    public double optDouble(String str, double d) {
        try {
            Object opt = opt(str);
            return opt instanceof Number ? ((Number) opt).doubleValue() : new Double((String) opt).doubleValue();
        } catch (Exception unused) {
            return d;
        }
    }

    public int optInt(String str) {
        return optInt(str, 0);
    }

    public int optInt(String str, int i) {
        try {
            return getInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public JSONArray optJSONArray(String str) {
        Object opt = opt(str);
        if (opt instanceof JSONArray) {
            return (JSONArray) opt;
        }
        return null;
    }

    public JSONObject optJSONObject(String str) {
        Object opt = opt(str);
        if (opt instanceof JSONObject) {
            return (JSONObject) opt;
        }
        return null;
    }

    public long optLong(String str) {
        return optLong(str, 0L);
    }

    public long optLong(String str, long j) {
        try {
            return getLong(str);
        } catch (Exception unused) {
            return j;
        }
    }

    public String optString(String str) {
        return optString(str, "");
    }

    public String optString(String str, String str2) {
        Object opt = opt(str);
        return opt != null ? opt.toString() : str2;
    }

    public JSONObject put(String str, boolean z) throws JSONException {
        put(str, z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject put(String str, double d) throws JSONException {
        put(str, new Double(d));
        return this;
    }

    public JSONObject put(String str, int i) throws JSONException {
        put(str, new Integer(i));
        return this;
    }

    public JSONObject put(String str, long j) throws JSONException {
        put(str, new Long(j));
        return this;
    }

    public JSONObject put(String str, Map map) throws JSONException {
        put(str, new JSONObject(map));
        return this;
    }

    public JSONObject put(String str, Object obj) throws JSONException {
        if (str != null) {
            if (obj != null) {
                a(obj);
                this.a.put(str, obj);
            } else {
                remove(str);
            }
            return this;
        }
        throw new JSONException("Null key.");
    }

    public JSONObject putOnce(String str, Object obj) throws JSONException {
        if (!(str == null || obj == null)) {
            if (opt(str) == null) {
                put(str, obj);
            } else {
                throw new JSONException("Duplicate key \"" + str + "\"");
            }
        }
        return this;
    }

    public JSONObject putOpt(String str, Object obj) throws JSONException {
        if (!(str == null || obj == null)) {
            put(str, obj);
        }
        return this;
    }

    public static String quote(String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 4);
        stringBuffer.append('\"');
        int i = 0;
        char c = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt != '\"') {
                if (charAt != '/') {
                    if (charAt != '\\') {
                        switch (charAt) {
                            case '\b':
                                stringBuffer.append("\\b");
                                continue;
                            case '\t':
                                stringBuffer.append("\\t");
                                continue;
                            case '\n':
                                stringBuffer.append("\\n");
                                continue;
                            default:
                                switch (charAt) {
                                    case '\f':
                                        stringBuffer.append("\\f");
                                        continue;
                                    case '\r':
                                        stringBuffer.append("\\r");
                                        continue;
                                    default:
                                        if (charAt < ' ' || ((charAt >= 128 && charAt < 160) || (charAt >= 8192 && charAt < 8448))) {
                                            String str2 = "000" + Integer.toHexString(charAt);
                                            stringBuffer.append("\\u" + str2.substring(str2.length() - 4));
                                            break;
                                        } else {
                                            stringBuffer.append(charAt);
                                            continue;
                                            continue;
                                        }
                                        break;
                                }
                        }
                    }
                } else {
                    if (c == '<') {
                        stringBuffer.append('\\');
                    }
                    stringBuffer.append(charAt);
                }
                i++;
                c = charAt;
            }
            stringBuffer.append('\\');
            stringBuffer.append(charAt);
            i++;
            c = charAt;
        }
        stringBuffer.append('\"');
        return stringBuffer.toString();
    }

    public Object remove(String str) {
        return this.a.remove(str);
    }

    public Iterator sortedKeys() {
        return new TreeSet(this.a.keySet()).iterator();
    }

    public static Object stringToValue(String str) {
        if (str.equals("")) {
            return str;
        }
        if (str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase("null")) {
            return NULL;
        }
        char charAt = str.charAt(0);
        if ((charAt >= '0' && charAt <= '9') || charAt == '.' || charAt == '-' || charAt == '+') {
            if (charAt == '0') {
                try {
                    if (str.length() <= 2 || (str.charAt(1) != 'x' && str.charAt(1) != 'X')) {
                        return new Integer(Integer.parseInt(str, 8));
                    }
                    return new Integer(Integer.parseInt(str.substring(2), 16));
                } catch (Exception unused) {
                }
            }
            try {
                try {
                    try {
                        return new Integer(str);
                    } catch (Exception unused2) {
                        return new Long(str);
                    }
                } catch (Exception unused3) {
                }
            } catch (Exception unused4) {
                return new Double(str);
            }
        }
        return str;
    }

    public static void a(Object obj) throws JSONException {
        if (obj == null) {
            return;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.isInfinite() || d.isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        } else if (obj instanceof Float) {
            Float f = (Float) obj;
            if (f.isInfinite() || f.isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }
    }

    public JSONArray toJSONArray(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONArray2.put(opt(jSONArray.getString(i)));
        }
        return jSONArray2;
    }

    public String toString() {
        try {
            Iterator keys = keys();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (keys.hasNext()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(StringUtil.COMMA);
                }
                Object next = keys.next();
                stringBuffer.append(quote(next.toString()));
                stringBuffer.append(':');
                stringBuffer.append(b(this.a.get(next)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString(int i) throws JSONException {
        return a(i, 0);
    }

    String a(int i, int i2) throws JSONException {
        int i3;
        int length = length();
        if (length == 0) {
            return "{}";
        }
        Iterator sortedKeys = sortedKeys();
        StringBuffer stringBuffer = new StringBuffer("{");
        int i4 = i2 + i;
        if (length == 1) {
            Object next = sortedKeys.next();
            stringBuffer.append(quote(next.toString()));
            stringBuffer.append(": ");
            stringBuffer.append(a(this.a.get(next), i, i2));
        } else {
            while (true) {
                i3 = 0;
                if (!sortedKeys.hasNext()) {
                    break;
                }
                Object next2 = sortedKeys.next();
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(",\n");
                } else {
                    stringBuffer.append('\n');
                }
                while (i3 < i4) {
                    stringBuffer.append(' ');
                    i3++;
                }
                stringBuffer.append(quote(next2.toString()));
                stringBuffer.append(": ");
                stringBuffer.append(a(this.a.get(next2), i, i4));
            }
            if (stringBuffer.length() > 1) {
                stringBuffer.append('\n');
                while (i3 < i2) {
                    stringBuffer.append(' ');
                    i3++;
                }
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    public static String b(Object obj) throws JSONException {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        if (obj instanceof JSONString) {
            try {
                String jSONString = ((JSONString) obj).toJSONString();
                if (jSONString instanceof String) {
                    return jSONString;
                }
                throw new JSONException("Bad value from toJSONString: " + ((Object) jSONString));
            } catch (Exception e) {
                throw new JSONException(e);
            }
        } else if (obj instanceof Number) {
            return numberToString((Number) obj);
        } else {
            if ((obj instanceof Boolean) || (obj instanceof JSONObject) || (obj instanceof JSONArray)) {
                return obj.toString();
            }
            if (obj instanceof Map) {
                return new JSONObject((Map) obj).toString();
            }
            if (obj instanceof Collection) {
                return new JSONArray((Collection) obj).toString();
            }
            if (obj.getClass().isArray()) {
                return new JSONArray(obj).toString();
            }
            return quote(obj.toString());
        }
    }

    public static String a(Object obj, int i, int i2) throws JSONException {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        try {
            if (obj instanceof JSONString) {
                String jSONString = ((JSONString) obj).toJSONString();
                if (jSONString instanceof String) {
                    return jSONString;
                }
            }
        } catch (Exception unused) {
        }
        if (obj instanceof Number) {
            return numberToString((Number) obj);
        }
        if (obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).a(i, i2);
        }
        if (obj instanceof JSONArray) {
            return ((JSONArray) obj).a(i, i2);
        }
        if (obj instanceof Map) {
            return new JSONObject((Map) obj).a(i, i2);
        }
        if (obj instanceof Collection) {
            return new JSONArray((Collection) obj).a(i, i2);
        }
        if (obj.getClass().isArray()) {
            return new JSONArray(obj).a(i, i2);
        }
        return quote(obj.toString());
    }

    public Writer write(Writer writer) throws JSONException {
        boolean z = false;
        try {
            Iterator keys = keys();
            writer.write(123);
            while (keys.hasNext()) {
                if (z) {
                    writer.write(44);
                }
                Object next = keys.next();
                writer.write(quote(next.toString()));
                writer.write(58);
                Object obj = this.a.get(next);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(b(obj));
                }
                z = true;
            }
            writer.write(125);
            return writer;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }
}
