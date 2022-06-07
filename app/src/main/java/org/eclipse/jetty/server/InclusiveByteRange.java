package org.eclipse.jetty.server;

import com.xiaomi.mipush.sdk.Constants;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.fourthline.cling.model.message.header.ContentRangeHeader;

/* loaded from: classes5.dex */
public class InclusiveByteRange {
    private static final Logger LOG = Log.getLogger(InclusiveByteRange.class);
    long first;
    long last;

    public InclusiveByteRange(long j, long j2) {
        this.first = 0L;
        this.last = 0L;
        this.first = j;
        this.last = j2;
    }

    public long getFirst() {
        return this.first;
    }

    public long getLast() {
        return this.last;
    }

    public static List satisfiableRanges(Enumeration enumeration, long j) {
        int indexOf;
        long parseLong;
        long j2;
        Object obj = null;
        while (enumeration.hasMoreElements()) {
            StringTokenizer stringTokenizer = new StringTokenizer((String) enumeration.nextElement(), "=,", false);
            Object obj2 = obj;
            String str = null;
            while (true) {
                try {
                    if (stringTokenizer.hasMoreTokens()) {
                        try {
                            str = stringTokenizer.nextToken().trim();
                            indexOf = str.indexOf(45);
                        } catch (NumberFormatException e) {
                            LOG.warn("Bad range format: {}", str);
                            LOG.ignore(e);
                        }
                        if (indexOf >= 0) {
                            int i = indexOf + 1;
                            if (str.indexOf(Constants.ACCEPT_TIME_SEPARATOR_SERVER, i) < 0) {
                                if (indexOf == 0) {
                                    if (i < str.length()) {
                                        j2 = Long.parseLong(str.substring(i).trim());
                                        parseLong = -1;
                                    } else {
                                        LOG.warn("Bad range format: {}", str);
                                    }
                                } else if (i < str.length()) {
                                    parseLong = Long.parseLong(str.substring(0, indexOf).trim());
                                    j2 = Long.parseLong(str.substring(i).trim());
                                } else {
                                    parseLong = Long.parseLong(str.substring(0, indexOf).trim());
                                    j2 = -1;
                                }
                                int i2 = (parseLong > (-1L) ? 1 : (parseLong == (-1L) ? 0 : -1));
                                if (!(i2 == 0 && j2 == -1) && (i2 == 0 || j2 == -1 || parseLong <= j2)) {
                                    if (parseLong < j) {
                                        obj2 = LazyList.add(obj2, new InclusiveByteRange(parseLong, j2));
                                    }
                                }
                            }
                        }
                        if (!"bytes".equals(str)) {
                            LOG.warn("Bad range format: {}", str);
                            break;
                        }
                    }
                } catch (Exception e2) {
                    LOG.warn("Bad range format: {}", str);
                    LOG.ignore(e2);
                }
            }
            obj = obj2;
        }
        return LazyList.getList(obj, true);
    }

    public long getFirst(long j) {
        long j2 = this.first;
        if (j2 >= 0) {
            return j2;
        }
        long j3 = j - this.last;
        if (j3 < 0) {
            return 0L;
        }
        return j3;
    }

    public long getLast(long j) {
        if (this.first < 0) {
            return j - 1;
        }
        long j2 = this.last;
        return (j2 < 0 || j2 >= j) ? j - 1 : j2;
    }

    public long getSize(long j) {
        return (getLast(j) - getFirst(j)) + 1;
    }

    public String toHeaderRangeString(long j) {
        StringBuilder sb = new StringBuilder(40);
        sb.append(ContentRangeHeader.PREFIX);
        sb.append(getFirst(j));
        sb.append('-');
        sb.append(getLast(j));
        sb.append("/");
        sb.append(j);
        return sb.toString();
    }

    public static String to416HeaderRangeString(long j) {
        StringBuilder sb = new StringBuilder(40);
        sb.append("bytes */");
        sb.append(j);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append(Long.toString(this.first));
        sb.append(Constants.COLON_SEPARATOR);
        sb.append(Long.toString(this.last));
        return sb.toString();
    }
}
