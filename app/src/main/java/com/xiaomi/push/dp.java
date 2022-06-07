package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class dp implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ el e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public dp(el elVar, String str, Context context, String str2, String str3) {
        this.e = elVar;
        this.a = str;
        this.b = context;
        this.c = str2;
        this.d = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        Context context;
        String str;
        String str2;
        el elVar;
        en enVar;
        Context context2;
        if (!TextUtils.isEmpty(this.a)) {
            try {
                eh.a(this.b, this.a, 1001, "get message");
                JSONObject jSONObject = new JSONObject(this.a);
                String optString = jSONObject.optString("action");
                String optString2 = jSONObject.optString("awakened_app_packagename");
                String optString3 = jSONObject.optString("awake_app_packagename");
                String optString4 = jSONObject.optString("awake_app");
                String optString5 = jSONObject.optString("awake_type");
                if (this.c.equals(optString3) && this.d.equals(optString4)) {
                    if (TextUtils.isEmpty(optString5) || TextUtils.isEmpty(optString3) || TextUtils.isEmpty(optString4) || TextUtils.isEmpty(optString2)) {
                        eh.a(this.b, this.a, 1008, "A receive a incorrect message with empty type");
                        return;
                    }
                    this.e.b(optString3);
                    this.e.a(optString4);
                    ek ekVar = new ek();
                    ekVar.b(optString);
                    ekVar.a(optString2);
                    ekVar.d(this.a);
                    if ("service".equals(optString5)) {
                        if (!TextUtils.isEmpty(optString)) {
                            elVar = this.e;
                            enVar = en.SERVICE_ACTION;
                            context2 = this.b;
                        } else {
                            ekVar.c("com.xiaomi.mipush.sdk.PushMessageHandler");
                            elVar = this.e;
                            enVar = en.SERVICE_COMPONENT;
                            context2 = this.b;
                        }
                    } else if (en.ACTIVITY.f25a.equals(optString5)) {
                        elVar = this.e;
                        enVar = en.ACTIVITY;
                        context2 = this.b;
                    } else if (en.PROVIDER.f25a.equals(optString5)) {
                        elVar = this.e;
                        enVar = en.PROVIDER;
                        context2 = this.b;
                    } else {
                        Context context3 = this.b;
                        String str3 = this.a;
                        eh.a(context3, str3, 1008, "A receive a incorrect message with unknown type " + optString5);
                        return;
                    }
                    elVar.a(enVar, context2, ekVar);
                    return;
                }
                Context context4 = this.b;
                String str4 = this.a;
                eh.a(context4, str4, 1008, "A receive a incorrect message with incorrect package info" + optString3);
                return;
            } catch (JSONException e) {
                b.a(e);
                context = this.b;
                str = this.a;
                str2 = "A meet a exception when receive the message";
            }
        } else {
            context = this.b;
            str = "null";
            str2 = "A receive a incorrect message with empty info";
        }
        eh.a(context, str, 1008, str2);
    }
}
