package org.seamless.http;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class Query {
    protected final Map<String, List<String>> parameters;

    public static Query newInstance(Map<String, List<String>> map) {
        Query query = new Query();
        query.parameters.putAll(map);
        return query;
    }

    public Query() {
        this.parameters = new LinkedHashMap();
    }

    public Query(Map<String, String[]> map) {
        this.parameters = new LinkedHashMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            this.parameters.put(entry.getKey(), Arrays.asList(entry.getValue() != null ? entry.getValue() : new String[0]));
        }
    }

    public Query(URL url) {
        this(url.getQuery());
    }

    public Query(String str) {
        String decode;
        this.parameters = new LinkedHashMap();
        if (str != null) {
            String[] split = str.split(MusicGroupListActivity.SPECIAL_SYMBOL);
            for (String str2 : split) {
                int indexOf = str2.indexOf(61);
                if (indexOf == -1) {
                    decode = null;
                } else {
                    try {
                        str2 = URLDecoder.decode(str2.substring(0, indexOf), "UTF-8");
                        decode = URLDecoder.decode(str2.substring(indexOf + 1, str2.length()), "UTF-8");
                    } catch (UnsupportedEncodingException unused) {
                        throw new IllegalStateException("Query string is not UTF-8");
                    }
                }
                List<String> list = this.parameters.get(str2);
                if (list == null) {
                    list = new ArrayList<>();
                    this.parameters.put(str2, list);
                }
                list.add(decode);
            }
        }
    }

    public String get(String str) {
        List<String> list = this.parameters.get(str);
        return (list == null || list.size() == 0) ? "" : list.get(0);
    }

    public String[] getValues(String str) {
        List<String> list = this.parameters.get(str);
        if (list == null) {
            return null;
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public List<String> getValuesAsList(String str) {
        if (this.parameters.containsKey(str)) {
            return Collections.unmodifiableList(this.parameters.get(str));
        }
        return null;
    }

    public Enumeration<String> getNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    public Map<String, String[]> getMap() {
        TreeMap treeMap = new TreeMap();
        for (Map.Entry<String, List<String>> entry : this.parameters.entrySet()) {
            List<String> value = entry.getValue();
            treeMap.put(entry.getKey(), value == null ? null : (String[]) value.toArray(new String[value.size()]));
        }
        return treeMap;
    }

    public Map<String, List<String>> getMapWithLists() {
        return Collections.unmodifiableMap(this.parameters);
    }

    public boolean isEmpty() {
        return this.parameters.size() == 0;
    }

    public Query cloneAndAdd(String str, String... strArr) {
        HashMap hashMap = new HashMap(getMapWithLists());
        List list = (List) hashMap.get(str);
        if (list == null) {
            list = new ArrayList();
            hashMap.put(str, list);
        }
        list.addAll(Arrays.asList(strArr));
        return newInstance(hashMap);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : this.parameters.entrySet()) {
            for (String str : entry.getValue()) {
                if (!(str == null || str.length() == 0)) {
                    if (sb.length() > 0) {
                        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                    }
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(str);
                }
            }
        }
        return sb.toString();
    }
}
