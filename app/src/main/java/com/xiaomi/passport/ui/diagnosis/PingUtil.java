package com.xiaomi.passport.ui.diagnosis;

import androidx.collection.ArrayMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class PingUtil {
    private static final String ipRegex = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";

    public static String getIPFromUrl(String str) {
        String domain = getDomain(str);
        if (domain == null) {
            return null;
        }
        if (isMatch(ipRegex, domain)) {
            return domain;
        }
        String ping = ping(createSimplePingCommand(1, 100, domain));
        if (ping != null) {
            try {
                String substring = ping.substring(ping.indexOf("from") + 5);
                return substring.substring(0, substring.indexOf("icmp_seq") - 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getMinRTT(String str) {
        return getMinRTT(str, 1, 100);
    }

    public static int getAvgRTT(String str) {
        return getAvgRTT(str, 1, 100);
    }

    public static int getMaxRTT(String str) {
        return getMaxRTT(str, 1, 100);
    }

    public static int getMdevRTT(String str) {
        return getMdevRTT(str, 1, 100);
    }

    public static int getMinRTT(String str, int i, int i2) {
        String ping;
        String domain = getDomain(str);
        if (!(domain == null || (ping = ping(createSimplePingCommand(i, i2, domain))) == null)) {
            try {
                return Math.round(Float.valueOf(ping.substring(ping.indexOf("min/avg/max/mdev") + 19).split("/")[0]).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static int getAvgRTT(String str, int i, int i2) {
        String ping;
        String domain = getDomain(str);
        if (!(domain == null || (ping = ping(createSimplePingCommand(i, i2, domain))) == null)) {
            try {
                return Math.round(Float.valueOf(ping.substring(ping.indexOf("min/avg/max/mdev") + 19).split("/")[1]).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static int getMaxRTT(String str, int i, int i2) {
        String ping;
        String domain = getDomain(str);
        if (!(domain == null || (ping = ping(createSimplePingCommand(i, i2, domain))) == null)) {
            try {
                return Math.round(Float.valueOf(ping.substring(ping.indexOf("min/avg/max/mdev") + 19).split("/")[2]).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static int getMdevRTT(String str, int i, int i2) {
        String ping;
        String domain = getDomain(str);
        if (!(domain == null || (ping = ping(createSimplePingCommand(i, i2, domain))) == null)) {
            try {
                return Math.round(Float.valueOf(ping.substring(ping.indexOf("min/avg/max/mdev") + 19).split("/")[3].replace(" ms", "")).floatValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static float getPacketLossFloat(String str) {
        String packetLoss = getPacketLoss(str);
        if (packetLoss == null) {
            return -1.0f;
        }
        try {
            return Float.valueOf(packetLoss.replace("%", "")).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0f;
        }
    }

    public static float getPacketLossFloat(String str, int i, int i2) {
        String packetLoss = getPacketLoss(str, i, i2);
        if (packetLoss == null) {
            return -1.0f;
        }
        try {
            return Float.valueOf(packetLoss.replace("%", "")).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return -1.0f;
        }
    }

    public static String getPacketLoss(String str) {
        return getPacketLoss(str, 1, 100);
    }

    public static String getPacketLoss(String str, int i, int i2) {
        String ping;
        String domain = getDomain(str);
        if (!(domain == null || (ping = ping(createSimplePingCommand(i, i2, domain))) == null)) {
            try {
                String substring = ping.substring(ping.indexOf("received,"));
                return substring.substring(9, substring.indexOf("packet"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getPingString(String str, int i, int i2) {
        return ping(createSimplePingCommand(i, i2, str));
    }

    private static String getDomain(String str) {
        try {
            return URI.create(str).getHost();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isMatch(String str, String str2) {
        return Pattern.matches(str, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v5 */
    private static String ping(String str) {
        Throwable th;
        Process process;
        IOException e;
        try {
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            process = Runtime.getRuntime().exec(str);
            try {
                InputStream inputStream = process.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                String sb2 = sb.toString();
                if (process != null) {
                    process.destroy();
                }
                return sb2;
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                if (process != null) {
                    process.destroy();
                }
                return null;
            }
        } catch (IOException e3) {
            e = e3;
            process = null;
        } catch (Throwable th3) {
            th = th3;
            str = 0;
            if (str != 0) {
                str.destroy();
            }
            throw th;
        }
    }

    private static String createSimplePingCommand(int i, int i2, String str) {
        return "/system/bin/ping -c " + i + " -w " + i2 + StringUtils.SPACE + str;
    }

    private static String createPingCommand(ArrayMap<String, String> arrayMap, String str) {
        String str2 = "/system/bin/ping";
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            str2 = str2.concat(StringUtils.SPACE + arrayMap.keyAt(i) + StringUtils.SPACE + arrayMap.get(arrayMap.keyAt(i)));
        }
        return str2.concat(StringUtils.SPACE + str);
    }
}
