package org.fourthline.cling.support.model.dlna.types;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fourthline.cling.model.types.InvalidValueException;

/* loaded from: classes5.dex */
public class NormalPlayTime {
    static final Pattern pattern = Pattern.compile("^(\\d+):(\\d{1,2}):(\\d{1,2})(\\.(\\d{1,3}))?|(\\d+)(\\.(\\d{1,3}))?$", 2);
    private long milliseconds;

    /* loaded from: classes5.dex */
    public enum Format {
        SECONDS,
        TIME
    }

    public NormalPlayTime(long j) {
        if (j >= 0) {
            this.milliseconds = j;
            return;
        }
        throw new InvalidValueException("Invalid parameter milliseconds: " + j);
    }

    public NormalPlayTime(long j, long j2, long j3, long j4) throws InvalidValueException {
        if (j < 0) {
            throw new InvalidValueException("Invalid parameter hours: " + j);
        } else if (j2 < 0 || j2 > 59) {
            throw new InvalidValueException("Invalid parameter minutes: " + j);
        } else if (j3 < 0 || j3 > 59) {
            throw new InvalidValueException("Invalid parameter seconds: " + j);
        } else if (j4 < 0 || j4 > 999) {
            throw new InvalidValueException("Invalid parameter milliseconds: " + j4);
        } else {
            this.milliseconds = (((j * 60 * 60) + (j2 * 60) + j3) * 1000) + j4;
        }
    }

    public long getMilliseconds() {
        return this.milliseconds;
    }

    public void setMilliseconds(long j) {
        if (j >= 0) {
            this.milliseconds = j;
            return;
        }
        throw new InvalidValueException("Invalid parameter milliseconds: " + j);
    }

    public String getString() {
        return getString(Format.SECONDS);
    }

    public String getString(Format format) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(this.milliseconds);
        long j = this.milliseconds % 1000;
        if (AnonymousClass1.$SwitchMap$org$fourthline$cling$support$model$dlna$types$NormalPlayTime$Format[format.ordinal()] != 1) {
            return String.format(Locale.ROOT, "%d.%03d", Long.valueOf(seconds), Long.valueOf(j));
        }
        long seconds2 = TimeUnit.MILLISECONDS.toSeconds(this.milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.milliseconds));
        return String.format(Locale.ROOT, "%d:%02d:%02d.%03d", Long.valueOf(TimeUnit.MILLISECONDS.toHours(this.milliseconds)), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(this.milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.milliseconds))), Long.valueOf(seconds2), Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: org.fourthline.cling.support.model.dlna.types.NormalPlayTime$1  reason: invalid class name */
    /* loaded from: classes5.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$fourthline$cling$support$model$dlna$types$NormalPlayTime$Format = new int[Format.values().length];

        static {
            try {
                $SwitchMap$org$fourthline$cling$support$model$dlna$types$NormalPlayTime$Format[Format.TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static NormalPlayTime valueOf(String str) throws InvalidValueException {
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            try {
                if (matcher.group(1) != null) {
                    return new NormalPlayTime(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3)), Long.parseLong(matcher.group(5)) * ((int) Math.pow(10.0d, 3 - matcher.group(5).length())));
                }
                return new NormalPlayTime((Long.parseLong(matcher.group(6)) * 1000) + (Long.parseLong(matcher.group(8)) * ((int) Math.pow(10.0d, 3 - matcher.group(8).length()))));
            } catch (NumberFormatException unused) {
            }
        }
        throw new InvalidValueException("Can't parse NormalPlayTime: " + str);
    }
}
