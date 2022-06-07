package org.fourthline.cling.model;

import com.xiaomi.mipush.sdk.Constants;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes5.dex */
public class ModelUtil {
    public static final boolean ANDROID_EMULATOR = false;
    public static final boolean ANDROID_RUNTIME;

    static {
        boolean z = false;
        try {
            if (Thread.currentThread().getContextClassLoader().loadClass("android.os.Build").getField("ID").get(null) != null) {
                z = true;
            }
        } catch (Exception unused) {
        }
        ANDROID_RUNTIME = z;
    }

    public static boolean isStringConvertibleType(Set<Class> set, Class cls) {
        if (cls.isEnum()) {
            return true;
        }
        for (Class cls2 : set) {
            if (cls2.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidUDAName(String str) {
        return ANDROID_RUNTIME ? (str == null || str.length() == 0) ? false : true : str != null && str.length() != 0 && !str.toLowerCase(Locale.ROOT).startsWith("xml") && str.matches(Constants.REGEX_UDA_NAME);
    }

    public static InetAddress getInetAddressByName(String str) {
        try {
            return InetAddress.getByName(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toCommaSeparatedList(Object[] objArr) {
        return toCommaSeparatedList(objArr, true, false);
    }

    public static String toCommaSeparatedList(Object[] objArr, boolean z, boolean z2) {
        if (objArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : objArr) {
            String replaceAll = obj.toString().replaceAll("\\\\", "\\\\\\\\");
            if (z) {
                replaceAll = replaceAll.replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SP, "\\\\,");
            }
            if (z2) {
                replaceAll = replaceAll.replaceAll("\"", "\\\"");
            }
            sb.append(replaceAll);
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String[] fromCommaSeparatedList(String str) {
        return fromCommaSeparatedList(str, true);
    }

    public static String[] fromCommaSeparatedList(String str, boolean z) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (z) {
            str = str.replaceAll("\\\\,", "XXX1122334455XXX");
        }
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].replaceAll("XXX1122334455XXX", Constants.ACCEPT_TIME_SEPARATOR_SP);
            split[i] = split[i].replaceAll("\\\\\\\\", "\\\\");
        }
        return split;
    }

    public static String toTimeString(long j) {
        long j2 = j / 3600;
        long j3 = j % 3600;
        long j4 = j3 / 60;
        long j5 = j3 % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(j2 < 10 ? "0" : "");
        sb.append(j2);
        sb.append(Constants.COLON_SEPARATOR);
        sb.append(j4 < 10 ? "0" : "");
        sb.append(j4);
        sb.append(Constants.COLON_SEPARATOR);
        sb.append(j5 < 10 ? "0" : "");
        sb.append(j5);
        return sb.toString();
    }

    public static long fromTimeString(String str) {
        if (str.lastIndexOf(".") != -1) {
            str = str.substring(0, str.lastIndexOf("."));
        }
        String[] split = str.split(Constants.COLON_SEPARATOR);
        if (split.length == 3) {
            return (Long.parseLong(split[0]) * 3600) + (Long.parseLong(split[1]) * 60) + Long.parseLong(split[2]);
        }
        throw new IllegalArgumentException("Can't parse time string: " + str);
    }

    public static String commaToNewline(String str) {
        StringBuilder sb = new StringBuilder();
        for (String str2 : str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
            sb.append(str2);
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            sb.append("\n");
        }
        if (sb.length() > 2) {
            sb.deleteCharAt(sb.length() - 2);
        }
        return sb.toString();
    }

    public static String getLocalHostName(boolean z) {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            return (!z && hostName.indexOf(".") != -1) ? hostName.substring(0, hostName.indexOf(".")) : hostName;
        } catch (Exception unused) {
            return "UNKNOWN HOST";
        }
    }

    public static byte[] getFirstNetworkInterfaceHardwareAddress() {
        try {
            Iterator it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (it.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface) it.next();
                if (!networkInterface.isLoopback() && networkInterface.isUp() && networkInterface.getHardwareAddress() != null) {
                    return networkInterface.getHardwareAddress();
                }
            }
            throw new RuntimeException("Could not discover first network interface hardware address");
        } catch (Exception unused) {
            throw new RuntimeException("Could not discover first network interface hardware address");
        }
    }
}
