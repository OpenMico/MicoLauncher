package org.seamless.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class MimeType {
    public static final String WILDCARD = "*";
    private Map<String, String> parameters;
    private String subtype;
    private String type;

    public MimeType() {
        this("*", "*");
    }

    public MimeType(String str, String str2, Map<String, String> map) {
        this.type = str == null ? "*" : str;
        this.subtype = str2 == null ? "*" : str2;
        if (map == null) {
            this.parameters = Collections.EMPTY_MAP;
            return;
        }
        TreeMap treeMap = new TreeMap(new Comparator<String>() { // from class: org.seamless.util.MimeType.1
            public int compare(String str3, String str4) {
                return str3.compareToIgnoreCase(str4);
            }
        });
        for (Map.Entry<String, String> entry : map.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue());
        }
        this.parameters = Collections.unmodifiableMap(treeMap);
    }

    public MimeType(String str, String str2) {
        this(str, str2, Collections.EMPTY_MAP);
    }

    public String getType() {
        return this.type;
    }

    public boolean isWildcardType() {
        return getType().equals("*");
    }

    public String getSubtype() {
        return this.subtype;
    }

    public boolean isWildcardSubtype() {
        return getSubtype().equals("*");
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public boolean isCompatible(MimeType mimeType) {
        if (mimeType == null) {
            return false;
        }
        if (this.type.equals("*") || mimeType.type.equals("*")) {
            return true;
        }
        if (!this.type.equalsIgnoreCase(mimeType.type) || (!this.subtype.equals("*") && !mimeType.subtype.equals("*"))) {
            return this.type.equalsIgnoreCase(mimeType.type) && this.subtype.equalsIgnoreCase(mimeType.subtype);
        }
        return true;
    }

    public static MimeType valueOf(String str) throws IllegalArgumentException {
        String str2;
        String str3;
        if (str != null) {
            int indexOf = str.indexOf(";");
            String str4 = null;
            if (indexOf > -1) {
                str2 = str.substring(indexOf + 1).trim();
                str = str.substring(0, indexOf);
            } else {
                str2 = null;
            }
            String[] split = str.split("/");
            if (split.length < 2 && str.equals("*")) {
                str4 = "*";
                str3 = "*";
            } else if (split.length == 2) {
                str4 = split[0].trim();
                str3 = split[1].trim();
            } else if (split.length == 2) {
                str3 = null;
            } else {
                throw new IllegalArgumentException("Error parsing string: " + str);
            }
            if (str2 == null || str2.length() <= 0) {
                return new MimeType(str4, str3);
            }
            HashMap hashMap = new HashMap();
            for (int i = 0; i < str2.length(); i = readParamsIntoMap(hashMap, str2, i)) {
            }
            return new MimeType(str4, str3, hashMap);
        }
        throw new IllegalArgumentException("String value is null");
    }

    public static int readParamsIntoMap(Map<String, String> map, String str, int i) {
        int end = getEnd(str, i);
        String trim = str.substring(i, end).trim();
        if (end < str.length() && str.charAt(end) == '=') {
            end++;
        }
        StringBuilder sb = new StringBuilder(str.length() - end);
        boolean z = false;
        boolean z2 = false;
        while (end < str.length()) {
            char charAt = str.charAt(end);
            if (charAt != '\"') {
                if (charAt != ';') {
                    if (charAt != '\\') {
                        sb.append(charAt);
                    } else if (z) {
                        sb.append(charAt);
                        z = false;
                    } else {
                        z = true;
                    }
                } else if (!z2) {
                    map.put(trim, sb.toString().trim());
                    return end + 1;
                } else {
                    sb.append(charAt);
                }
            } else if (z) {
                sb.append(charAt);
                z = false;
            } else {
                z2 = !z2;
            }
            end++;
        }
        map.put(trim, sb.toString().trim());
        return end;
    }

    protected static int getEnd(String str, int i) {
        int indexOf = str.indexOf(61, i);
        int indexOf2 = str.indexOf(59, i);
        if (indexOf == -1 && indexOf2 == -1) {
            return str.length();
        }
        return indexOf == -1 ? indexOf2 : (indexOf2 != -1 && indexOf >= indexOf2) ? indexOf2 : indexOf;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MimeType mimeType = (MimeType) obj;
        Map<String, String> map = this.parameters;
        if (map == null ? mimeType.parameters == null : map.equals(mimeType.parameters)) {
            return this.subtype.equalsIgnoreCase(mimeType.subtype) && this.type.equalsIgnoreCase(mimeType.type);
        }
        return false;
    }

    public int hashCode() {
        int hashCode = ((this.type.toLowerCase().hashCode() * 31) + this.subtype.toLowerCase().hashCode()) * 31;
        Map<String, String> map = this.parameters;
        return hashCode + (map != null ? map.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toStringNoParameters());
        if (getParameters() != null || getParameters().size() > 0) {
            for (String str : getParameters().keySet()) {
                sb.append(";");
                sb.append(str);
                sb.append("=\"");
                sb.append(getParameters().get(str));
                sb.append("\"");
            }
        }
        return sb.toString();
    }

    public String toStringNoParameters() {
        return getType() + "/" + getSubtype();
    }
}
