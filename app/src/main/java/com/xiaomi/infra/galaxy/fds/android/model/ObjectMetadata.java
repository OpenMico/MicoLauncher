package com.xiaomi.infra.galaxy.fds.android.model;

import com.xiaomi.infra.galaxy.fds.android.util.Util;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;

/* loaded from: classes3.dex */
public class ObjectMetadata {
    private static final Set<String> a = new HashSet();
    private final Map<String, String> b = new HashMap();
    private final Map<String, String> c = new HashMap();

    static {
        a.add("Last-Modified");
        a.add("Content-MD5");
        a.add("Content-Type");
        a.add("Content-Length");
        a.add("Content-Encoding");
        a.add("Cache-Control");
    }

    public static ObjectMetadata parseObjectMetadata(Header[] headerArr) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        for (Header header : headerArr) {
            String name = header.getName();
            String value = header.getValue();
            if (name.startsWith("x-xiaomi-meta-")) {
                objectMetadata.addUserMetadata(name, value);
            } else if (a.contains(name)) {
                objectMetadata.addPredefinedMetadata(name, value);
            }
        }
        return objectMetadata;
    }

    public Map<String, String> getUserMetadata() {
        return this.b;
    }

    public void addUserMetadata(String str, String str2) {
        a(str);
        this.b.put(str, str2);
    }

    public long getContentLength() {
        String str = this.c.get("Content-Length");
        if (str != null) {
            return Long.parseLong(str);
        }
        return -1L;
    }

    public void setContentLength(long j) {
        this.c.put("Content-Length", Long.toString(j));
    }

    public String getContentMD5() {
        return this.c.get("Content-MD5");
    }

    public void setContentMD5(String str) {
        this.c.put("Content-MD5", str);
    }

    public String getContentType() {
        return this.c.get("Content-Type");
    }

    public void setContentType(String str) {
        this.c.put("Content-Type", str);
    }

    public String getContentEncoding() {
        return this.c.get("Content-Encoding");
    }

    public void setContentEncoding(String str) {
        this.c.put("Content-Encoding", str);
    }

    public String getCacheControl() {
        return this.c.get("Cache-Control");
    }

    public void setCacheControl(String str) {
        this.c.put("Cache-Control", str);
    }

    public Date getLastModified() {
        String str = this.c.get("Last-Modified");
        if (str == null) {
            return null;
        }
        try {
            return Util.parseDate(str);
        } catch (ParseException unused) {
            return null;
        }
    }

    public void setLastModified(Date date) {
        this.c.put("Last-Modified", Util.formatDateString(date));
    }

    public void addPredefinedMetadata(String str, String str2) {
        a(str);
        this.c.put(str, str2);
    }

    public Map<String, String> getAllMetadata() {
        HashMap hashMap = new HashMap(this.c);
        hashMap.putAll(this.b);
        return hashMap;
    }

    private void a(String str) {
        boolean startsWith = str.startsWith("x-xiaomi-meta-");
        if (!startsWith) {
            Iterator<String> it = a.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (str.equals(it.next())) {
                        startsWith = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (!startsWith) {
            throw new RuntimeException("Invalid metadata: " + str, null);
        }
    }
}
