package miuix.animation.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes5.dex */
public class LogUtils {
    private static volatile boolean d;
    private static final HandlerThread a = new HandlerThread("LogThread");
    private static final Map<Integer, String> c = new ConcurrentHashMap();
    private static final Handler b = new Handler(a.getLooper()) { // from class: miuix.animation.utils.LogUtils.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            if (message.what == 0) {
                Log.d((String) LogUtils.c.get(Integer.valueOf(message.arg1)), "thread log, " + ((String) message.obj));
            }
            message.obj = null;
        }
    };

    static {
        a.start();
    }

    public static void logThread(String str, String str2) {
        Message obtainMessage = b.obtainMessage(0);
        obtainMessage.obj = str2;
        obtainMessage.arg1 = str.hashCode();
        c.put(Integer.valueOf(obtainMessage.arg1), str);
        obtainMessage.sendToTarget();
    }

    private LogUtils() {
    }

    public static void getLogEnableInfo() {
        String str = "";
        try {
            str = CommonUtils.readProp("log.tag.folme.level");
            if (str == null) {
                str = "";
            }
        } catch (Exception e) {
            Log.i(CommonUtils.TAG, "can not access property log.tag.folme.level, no log", e);
        }
        Log.d(CommonUtils.TAG, "logLevel = " + str);
        d = str.equals(HomePageRecordHelper.AREA_D);
    }

    public static boolean isLogEnabled() {
        return d;
    }

    public static void debug(String str, Object... objArr) {
        if (d) {
            if (objArr.length > 0) {
                StringBuilder sb = new StringBuilder(", ");
                int length = sb.length();
                for (Object obj : objArr) {
                    if (sb.length() > length) {
                        sb.append(", ");
                    }
                    sb.append(obj);
                }
                Log.i(CommonUtils.TAG, str + sb.toString());
                return;
            }
            Log.i(CommonUtils.TAG, str);
        }
    }

    public static String getStackTrace(int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return Arrays.toString(Arrays.asList(stackTrace).subList(3, Math.min(stackTrace.length, i + 4)).toArray());
    }
}
