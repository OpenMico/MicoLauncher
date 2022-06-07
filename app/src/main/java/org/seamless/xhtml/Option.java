package org.seamless.xhtml;

import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class Option {
    private String key;
    private String[] values;

    public Option(String str, String[] strArr) {
        this.key = str;
        this.values = strArr;
    }

    public static Option[] fromString(String str) {
        if (str == null || str.length() == 0) {
            return new Option[0];
        }
        ArrayList arrayList = new ArrayList();
        try {
            for (String str2 : str.split(";")) {
                String trim = str2.trim();
                if (trim.contains(Constants.COLON_SEPARATOR)) {
                    String[] split = trim.split(Constants.COLON_SEPARATOR);
                    if (split.length == 2) {
                        String trim2 = split[0].trim();
                        String[] split2 = split[1].split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                        ArrayList arrayList2 = new ArrayList();
                        for (String str3 : split2) {
                            String trim3 = str3.trim();
                            if (trim3.length() > 0) {
                                arrayList2.add(trim3);
                            }
                        }
                        arrayList.add(new Option(trim2, (String[]) arrayList2.toArray(new String[arrayList2.size()])));
                    }
                }
            }
            return (Option[]) arrayList.toArray(new Option[arrayList.size()]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't parse options string: " + str, e);
        }
    }

    public String getKey() {
        return this.key;
    }

    public String[] getValues() {
        return this.values;
    }

    public boolean isTrue() {
        return getValues().length == 1 && getValues()[0].toLowerCase().equals("true");
    }

    public boolean isFalse() {
        return getValues().length == 1 && getValues()[0].toLowerCase().equals("false");
    }

    public String getFirstValue() {
        return getValues()[0];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getKey());
        sb.append(": ");
        Iterator it = Arrays.asList(getValues()).iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Option option = (Option) obj;
        return this.key.equals(option.key) && Arrays.equals(this.values, option.values);
    }

    public int hashCode() {
        return (this.key.hashCode() * 31) + Arrays.hashCode(this.values);
    }
}
