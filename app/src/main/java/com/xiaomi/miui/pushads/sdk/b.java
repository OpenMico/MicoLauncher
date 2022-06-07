package com.xiaomi.miui.pushads.sdk;

import android.app.PendingIntent;
import android.content.Context;
import android.text.TextUtils;
import android.widget.RemoteViews;

/* loaded from: classes4.dex */
class b extends RemoteViews {
    protected Context a;

    public b(Context context) {
        super(context.getPackageName(), context.getResources().getIdentifier("notification_base_layout", "layout", context.getPackageName()));
        this.a = context;
    }

    public b(String str, int i) {
        super(str, i);
    }

    public void a(int i) {
        setImageViewResource(16908294, i);
    }

    public void a(String str, PendingIntent pendingIntent) {
        int i;
        if (str != null) {
            str = str.trim();
        }
        int identifier = this.a.getResources().getIdentifier("action_button", "id", this.a.getPackageName());
        if (!TextUtils.isEmpty(str)) {
            setTextViewText(identifier, str);
            if (pendingIntent != null) {
                setOnClickPendingIntent(identifier, pendingIntent);
            }
            i = 0;
        } else {
            i = 8;
        }
        setViewVisibility(identifier, i);
    }

    public void a(String str, String str2) {
        String str3;
        if (str != null) {
            str = str.trim();
        }
        if (str2 != null) {
            str2 = str2.trim();
        }
        if (!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str)) {
                str3 = "";
            } else {
                str2 = str;
                str3 = str2;
            }
            if (!TextUtils.isEmpty(str3)) {
                int identifier = this.a.getResources().getIdentifier("sub_title", "id", this.a.getPackageName());
                setTextViewText(identifier, str3);
                setViewVisibility(identifier, 0);
            }
            setTextViewText(this.a.getResources().getIdentifier("title", "id", this.a.getPackageName()), str2);
        }
    }
}
