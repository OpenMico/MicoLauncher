package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.HashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class ah extends Handler {
    final /* synthetic */ ay a;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ah(ay ayVar, Looper looper) {
        super(looper);
        this.a = ayVar;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        Context context;
        Context context2;
        Context context3;
        Context context4;
        Context context5;
        ay ayVar;
        bd bdVar;
        Context context6;
        HashMap<String, String> a;
        Context context7;
        Context context8;
        Context context9;
        Context context10;
        Context context11;
        Context context12;
        Context context13;
        ay ayVar2;
        bd bdVar2;
        Context context14;
        if (message.what == 19) {
            String str = (String) message.obj;
            int i = message.arg1;
            synchronized (ao.class) {
                context = this.a.c;
                if (ao.a(context).m723a(str)) {
                    context2 = this.a.c;
                    if (ao.a(context2).a(str) < 10) {
                        if (bd.DISABLE_PUSH.ordinal() == i) {
                            context14 = this.a.c;
                            if ("syncing".equals(ao.a(context14).a(bd.DISABLE_PUSH))) {
                                ayVar2 = this.a;
                                bdVar2 = bd.DISABLE_PUSH;
                                ayVar2.a(str, bdVar2, true, (HashMap<String, String>) null);
                                context4 = this.a.c;
                                ao.a(context4).b(str);
                            }
                        }
                        if (bd.ENABLE_PUSH.ordinal() == i) {
                            context13 = this.a.c;
                            if ("syncing".equals(ao.a(context13).a(bd.ENABLE_PUSH))) {
                                ayVar2 = this.a;
                                bdVar2 = bd.ENABLE_PUSH;
                                ayVar2.a(str, bdVar2, true, (HashMap<String, String>) null);
                                context4 = this.a.c;
                                ao.a(context4).b(str);
                            }
                        }
                        if (bd.UPLOAD_HUAWEI_TOKEN.ordinal() == i) {
                            context11 = this.a.c;
                            if ("syncing".equals(ao.a(context11).a(bd.UPLOAD_HUAWEI_TOKEN))) {
                                ayVar = this.a;
                                bdVar = bd.UPLOAD_HUAWEI_TOKEN;
                                context12 = this.a.c;
                                a = j.a(context12, f.ASSEMBLE_PUSH_HUAWEI);
                                ayVar.a(str, bdVar, false, a);
                                context4 = this.a.c;
                                ao.a(context4).b(str);
                            }
                        }
                        if (bd.UPLOAD_FCM_TOKEN.ordinal() == i) {
                            context9 = this.a.c;
                            if ("syncing".equals(ao.a(context9).a(bd.UPLOAD_FCM_TOKEN))) {
                                ayVar = this.a;
                                bdVar = bd.UPLOAD_FCM_TOKEN;
                                context10 = this.a.c;
                                a = j.a(context10, f.ASSEMBLE_PUSH_FCM);
                                ayVar.a(str, bdVar, false, a);
                                context4 = this.a.c;
                                ao.a(context4).b(str);
                            }
                        }
                        if (bd.UPLOAD_COS_TOKEN.ordinal() == i) {
                            context7 = this.a.c;
                            if ("syncing".equals(ao.a(context7).a(bd.UPLOAD_COS_TOKEN))) {
                                ayVar = this.a;
                                bdVar = bd.UPLOAD_COS_TOKEN;
                                context8 = this.a.c;
                                a = j.a(context8, f.ASSEMBLE_PUSH_COS);
                                ayVar.a(str, bdVar, false, a);
                                context4 = this.a.c;
                                ao.a(context4).b(str);
                            }
                        }
                        if (bd.UPLOAD_FTOS_TOKEN.ordinal() == i) {
                            context5 = this.a.c;
                            if ("syncing".equals(ao.a(context5).a(bd.UPLOAD_FTOS_TOKEN))) {
                                ayVar = this.a;
                                bdVar = bd.UPLOAD_FTOS_TOKEN;
                                context6 = this.a.c;
                                a = j.a(context6, f.ASSEMBLE_PUSH_FTOS);
                                ayVar.a(str, bdVar, false, a);
                            }
                        }
                        context4 = this.a.c;
                        ao.a(context4).b(str);
                    } else {
                        context3 = this.a.c;
                        ao.a(context3).c(str);
                    }
                }
            }
        }
    }
}
