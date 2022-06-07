package miuix.arch.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes5.dex */
public @interface AppTime {
    public static final int AFTER_APP_CREATED = 4;
    public static final int AFTER_FIRST_ACT_CREATED = 7;
    public static final int APP_ATTACH_CONTEXT = 1;
    public static final int APP_CREATE = 3;
    public static final int BEFORE_APP_CREATED = 2;
    public static final int BEFORE_FIRST_ACT_CREATED = 5;
    public static final int FIRST_ACT_CREATE = 6;
    public static final int MAIN_HANDLER_IDLE = 8;
}
