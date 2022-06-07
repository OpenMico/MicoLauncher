package org.eclipse.jetty.http;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.StringMap;

/* loaded from: classes5.dex */
public class PathMap extends HashMap implements Externalizable {
    private static String __pathSpecSeparators = ":,";
    Entry _default;
    List _defaultSingletonList;
    final Set _entrySet;
    final StringMap _exactMap;
    boolean _nodefault;
    Entry _prefixDefault;
    final StringMap _prefixMap;
    final StringMap _suffixMap;

    public static void setPathSpecSeparators(String str) {
        __pathSpecSeparators = str;
    }

    public PathMap() {
        super(11);
        this._prefixMap = new StringMap();
        this._suffixMap = new StringMap();
        this._exactMap = new StringMap();
        this._defaultSingletonList = null;
        this._prefixDefault = null;
        this._default = null;
        this._nodefault = false;
        this._entrySet = entrySet();
    }

    public PathMap(boolean z) {
        super(11);
        this._prefixMap = new StringMap();
        this._suffixMap = new StringMap();
        this._exactMap = new StringMap();
        this._defaultSingletonList = null;
        this._prefixDefault = null;
        this._default = null;
        this._nodefault = false;
        this._entrySet = entrySet();
        this._nodefault = z;
    }

    public PathMap(int i) {
        super(i);
        this._prefixMap = new StringMap();
        this._suffixMap = new StringMap();
        this._exactMap = new StringMap();
        this._defaultSingletonList = null;
        this._prefixDefault = null;
        this._default = null;
        this._nodefault = false;
        this._entrySet = entrySet();
    }

    public PathMap(Map map) {
        this._prefixMap = new StringMap();
        this._suffixMap = new StringMap();
        this._exactMap = new StringMap();
        this._defaultSingletonList = null;
        this._prefixDefault = null;
        this._default = null;
        this._nodefault = false;
        putAll(map);
        this._entrySet = entrySet();
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(new HashMap(this));
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        putAll((HashMap) objectInput.readObject());
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Object put(Object obj, Object obj2) {
        String obj3 = obj.toString();
        if ("".equals(obj3.trim())) {
            Entry entry = new Entry("", obj2);
            entry.setMapped("");
            this._exactMap.put("", (Object) entry);
            return super.put("", obj2);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(obj3, __pathSpecSeparators);
        Object obj4 = null;
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.startsWith("/") || nextToken.startsWith("*.")) {
                Object put = super.put(nextToken, obj2);
                Entry entry2 = new Entry(nextToken, obj2);
                if (entry2.getKey().equals(nextToken)) {
                    if (nextToken.equals("/*")) {
                        this._prefixDefault = entry2;
                    } else if (nextToken.endsWith("/*")) {
                        String substring = nextToken.substring(0, nextToken.length() - 2);
                        entry2.setMapped(substring);
                        this._prefixMap.put(substring, (Object) entry2);
                        this._exactMap.put(substring, (Object) entry2);
                        this._exactMap.put(nextToken.substring(0, nextToken.length() - 1), (Object) entry2);
                    } else if (nextToken.startsWith("*.")) {
                        this._suffixMap.put(nextToken.substring(2), (Object) entry2);
                    } else if (!nextToken.equals("/")) {
                        entry2.setMapped(nextToken);
                        this._exactMap.put(nextToken, (Object) entry2);
                    } else if (this._nodefault) {
                        this._exactMap.put(nextToken, (Object) entry2);
                    } else {
                        this._default = entry2;
                        this._defaultSingletonList = Collections.singletonList(this._default);
                    }
                }
                obj4 = put;
            } else {
                throw new IllegalArgumentException("PathSpec " + nextToken + ". must start with '/' or '*.'");
            }
        }
        return obj4;
    }

    public Object match(String str) {
        Entry match = getMatch(str);
        if (match != null) {
            return match.getValue();
        }
        return null;
    }

    public Entry getMatch(String str) {
        Map.Entry entry;
        Map.Entry entry2;
        Map.Entry entry3;
        if (str == null) {
            return null;
        }
        int length = str.length();
        int i = 0;
        if (length == 1 && str.charAt(0) == '/' && (entry3 = (Map.Entry) this._exactMap.get("")) != null) {
            return (Entry) entry3;
        }
        Map.Entry entry4 = this._exactMap.getEntry(str, 0, length);
        if (entry4 != null) {
            return (Entry) entry4.getValue();
        }
        int i2 = length;
        do {
            i2 = str.lastIndexOf(47, i2 - 1);
            if (i2 >= 0) {
                entry2 = this._prefixMap.getEntry(str, 0, i2);
            } else {
                Entry entry5 = this._prefixDefault;
                if (entry5 != null) {
                    return entry5;
                }
                do {
                    i = str.indexOf(46, i + 1);
                    if (i <= 0) {
                        return this._default;
                    }
                    entry = this._suffixMap.getEntry(str, i + 1, (length - i) - 1);
                } while (entry == null);
                return (Entry) entry.getValue();
            }
        } while (entry2 == null);
        return (Entry) entry2.getValue();
    }

    public Object getLazyMatches(String str) {
        Object obj = null;
        if (str == null) {
            return LazyList.getList(null);
        }
        int length = str.length();
        int i = 0;
        Map.Entry entry = this._exactMap.getEntry(str, 0, length);
        if (entry != null) {
            obj = LazyList.add(null, entry.getValue());
        }
        int i2 = length - 1;
        while (true) {
            i2 = str.lastIndexOf(47, i2 - 1);
            if (i2 < 0) {
                break;
            }
            Map.Entry entry2 = this._prefixMap.getEntry(str, 0, i2);
            if (entry2 != null) {
                obj = LazyList.add(obj, entry2.getValue());
            }
        }
        Entry entry3 = this._prefixDefault;
        if (entry3 != null) {
            obj = LazyList.add(obj, entry3);
        }
        while (true) {
            i = str.indexOf(46, i + 1);
            if (i <= 0) {
                break;
            }
            Map.Entry entry4 = this._suffixMap.getEntry(str, i + 1, (length - i) - 1);
            if (entry4 != null) {
                obj = LazyList.add(obj, entry4.getValue());
            }
        }
        Entry entry5 = this._default;
        if (entry5 == null) {
            return obj;
        }
        if (obj == null) {
            return this._defaultSingletonList;
        }
        return LazyList.add(obj, entry5);
    }

    public List getMatches(String str) {
        return LazyList.getList(getLazyMatches(str));
    }

    public boolean containsMatch(String str) {
        Entry match = getMatch(str);
        return match != null && !match.equals(this._default);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        if (obj != null) {
            String str = (String) obj;
            if (str.equals("/*")) {
                this._prefixDefault = null;
            } else if (str.endsWith("/*")) {
                this._prefixMap.remove(str.substring(0, str.length() - 2));
                this._exactMap.remove(str.substring(0, str.length() - 1));
                this._exactMap.remove(str.substring(0, str.length() - 2));
            } else if (str.startsWith("*.")) {
                this._suffixMap.remove(str.substring(2));
            } else if (str.equals("/")) {
                this._default = null;
                this._defaultSingletonList = null;
            } else {
                this._exactMap.remove(str);
            }
        }
        return super.remove(obj);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public void clear() {
        this._exactMap.clear();
        this._prefixMap.clear();
        this._suffixMap.clear();
        this._default = null;
        this._defaultSingletonList = null;
        super.clear();
    }

    public static boolean match(String str, String str2) throws IllegalArgumentException {
        return match(str, str2, false);
    }

    public static boolean match(String str, String str2, boolean z) throws IllegalArgumentException {
        char charAt = str.charAt(0);
        if (charAt == '/') {
            if ((!z && str.length() == 1) || str.equals(str2) || isPathWildcardMatch(str, str2)) {
                return true;
            }
        } else if (charAt == '*') {
            return str2.regionMatches((str2.length() - str.length()) + 1, str, 1, str.length() - 1);
        }
        return false;
    }

    private static boolean isPathWildcardMatch(String str, String str2) {
        int length = str.length() - 2;
        return str.endsWith("/*") && str2.regionMatches(0, str, 0, length) && (str2.length() == length || '/' == str2.charAt(length));
    }

    public static String pathMatch(String str, String str2) {
        char charAt = str.charAt(0);
        if (charAt == '/') {
            if (str.length() == 1 || str.equals(str2)) {
                return str2;
            }
            if (isPathWildcardMatch(str, str2)) {
                return str2.substring(0, str.length() - 2);
            }
            return null;
        } else if (charAt != '*' || !str2.regionMatches(str2.length() - (str.length() - 1), str, 1, str.length() - 1)) {
            return null;
        } else {
            return str2;
        }
    }

    public static String pathInfo(String str, String str2) {
        if ("".equals(str)) {
            return str2;
        }
        if (str.charAt(0) != '/' || str.length() == 1) {
            return null;
        }
        boolean isPathWildcardMatch = isPathWildcardMatch(str, str2);
        if ((!str.equals(str2) || isPathWildcardMatch) && isPathWildcardMatch && str2.length() != str.length() - 2) {
            return str2.substring(str.length() - 2);
        }
        return null;
    }

    public static String relativePath(String str, String str2, String str3) {
        String pathInfo = pathInfo(str2, str3);
        if (pathInfo == null) {
            pathInfo = str3;
        }
        if (pathInfo.startsWith("./")) {
            pathInfo = pathInfo.substring(2);
        }
        if (str.endsWith("/")) {
            if (pathInfo.startsWith("/")) {
                return str + pathInfo.substring(1);
            }
            return str + pathInfo;
        } else if (pathInfo.startsWith("/")) {
            return str + pathInfo;
        } else {
            return str + "/" + pathInfo;
        }
    }

    /* loaded from: classes5.dex */
    public static class Entry implements Map.Entry {
        private final Object key;
        private String mapped;
        private transient String string;
        private final Object value;

        Entry(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            if (this.string == null) {
                this.string = this.key + "=" + this.value;
            }
            return this.string;
        }

        public String getMapped() {
            return this.mapped;
        }

        void setMapped(String str) {
            this.mapped = str;
        }
    }
}
