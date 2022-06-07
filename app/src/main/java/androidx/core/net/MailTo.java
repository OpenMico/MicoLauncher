package androidx.core.net;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public final class MailTo {
    public static final String MAILTO_SCHEME = "mailto:";
    private HashMap<String, String> a = new HashMap<>();

    private MailTo() {
    }

    public static boolean isMailTo(@Nullable String str) {
        return str != null && str.startsWith(MAILTO_SCHEME);
    }

    public static boolean isMailTo(@Nullable Uri uri) {
        return uri != null && "mailto".equals(uri.getScheme());
    }

    @NonNull
    public static MailTo parse(@NonNull String str) throws ParseException {
        String str2;
        String str3;
        Preconditions.checkNotNull(str);
        if (isMailTo(str)) {
            int indexOf = str.indexOf(35);
            if (indexOf != -1) {
                str = str.substring(0, indexOf);
            }
            int indexOf2 = str.indexOf(63);
            if (indexOf2 == -1) {
                str3 = Uri.decode(str.substring(7));
                str2 = null;
            } else {
                str3 = Uri.decode(str.substring(7, indexOf2));
                str2 = str.substring(indexOf2 + 1);
            }
            MailTo mailTo = new MailTo();
            if (str2 != null) {
                for (String str4 : str2.split(MusicGroupListActivity.SPECIAL_SYMBOL)) {
                    String[] split = str4.split("=", 2);
                    if (split.length != 0) {
                        mailTo.a.put(Uri.decode(split[0]).toLowerCase(Locale.ROOT), split.length > 1 ? Uri.decode(split[1]) : null);
                    }
                }
            }
            String to = mailTo.getTo();
            if (to != null) {
                str3 = str3 + ", " + to;
            }
            mailTo.a.put("to", str3);
            return mailTo;
        }
        throw new ParseException("Not a mailto scheme");
    }

    @NonNull
    public static MailTo parse(@NonNull Uri uri) throws ParseException {
        return parse(uri.toString());
    }

    @Nullable
    public String getTo() {
        return this.a.get("to");
    }

    @Nullable
    public String getCc() {
        return this.a.get("cc");
    }

    @Nullable
    public String getBcc() {
        return this.a.get("bcc");
    }

    @Nullable
    public String getSubject() {
        return this.a.get("subject");
    }

    @Nullable
    public String getBody() {
        return this.a.get("body");
    }

    @Nullable
    public Map<String, String> getHeaders() {
        return this.a;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(MAILTO_SCHEME);
        sb.append('?');
        for (Map.Entry<String, String> entry : this.a.entrySet()) {
            sb.append(Uri.encode(entry.getKey()));
            sb.append('=');
            sb.append(Uri.encode(entry.getValue()));
            sb.append(Typography.amp);
        }
        return sb.toString();
    }
}
