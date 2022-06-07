package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.PushMessageHandler;
import com.xiaomi.push.aj;
import com.xiaomi.push.ew;
import com.xiaomi.push.fb;
import com.xiaomi.push.m;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class MessageHandleService extends BaseService {
    private static ConcurrentLinkedQueue<a> a = new ConcurrentLinkedQueue<>();
    private static ExecutorService b = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, new LinkedBlockingQueue(), new m("mhs"));

    /* loaded from: classes4.dex */
    public static class a {
        private PushMessageReceiver a;
        private Intent b;

        public a(Intent intent, PushMessageReceiver pushMessageReceiver) {
            this.a = pushMessageReceiver;
            this.b = intent;
        }

        public Intent a() {
            return this.b;
        }

        /* renamed from: a */
        public PushMessageReceiver m719a() {
            return this.a;
        }
    }

    public static void a(Context context, Intent intent) {
        if (intent != null) {
            b(context);
        }
    }

    public static void addJob(Context context, a aVar) {
        if (aVar != null) {
            a.add(aVar);
            b(context);
            startService(context);
        }
    }

    private static void b(Context context) {
        if (!b.isShutdown()) {
            b.execute(new k(context));
        }
    }

    public static void c(Context context) {
        ew a2;
        String packageName;
        int i;
        String str;
        String[] stringArrayExtra;
        try {
            a poll = a.poll();
            if (poll != null) {
                PushMessageReceiver a3 = poll.m719a();
                Intent a4 = poll.a();
                int intExtra = a4.getIntExtra(PushMessageHelper.MESSAGE_TYPE, 1);
                if (intExtra != 1) {
                    switch (intExtra) {
                        case 3:
                            MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) a4.getSerializableExtra(PushMessageHelper.KEY_COMMAND);
                            a3.onCommandResult(context, miPushCommandMessage);
                            if (TextUtils.equals(miPushCommandMessage.getCommand(), fb.COMMAND_REGISTER.f26a)) {
                                a3.onReceiveRegisterResult(context, miPushCommandMessage);
                                if (miPushCommandMessage.getResultCode() != 0) {
                                    return;
                                }
                            } else {
                                return;
                            }
                            break;
                        case 4:
                            return;
                        case 5:
                            if (PushMessageHelper.ERROR_TYPE_NEED_PERMISSION.equals(a4.getStringExtra(PushMessageHelper.ERROR_TYPE)) && (stringArrayExtra = a4.getStringArrayExtra(PushMessageHelper.ERROR_MESSAGE)) != null) {
                                a3.onRequirePermissions(context, stringArrayExtra);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                } else {
                    PushMessageHandler.a a5 = av.a(context).a(a4);
                    int intExtra2 = a4.getIntExtra("eventMessageType", -1);
                    if (a5 == null) {
                        return;
                    }
                    if (a5 instanceof MiPushMessage) {
                        MiPushMessage miPushMessage = (MiPushMessage) a5;
                        if (!miPushMessage.isArrivedMessage()) {
                            a3.onReceiveMessage(context, miPushMessage);
                        }
                        if (miPushMessage.getPassThrough() == 1) {
                            ew.a(context.getApplicationContext()).a(context.getPackageName(), a4, 2004, "call passThrough callBack");
                            a3.onReceivePassThroughMessage(context, miPushMessage);
                            return;
                        } else if (miPushMessage.isNotified()) {
                            if (intExtra2 == 1000) {
                                a2 = ew.a(context.getApplicationContext());
                                packageName = context.getPackageName();
                                i = 1007;
                                str = "call notification callBack";
                            } else {
                                a2 = ew.a(context.getApplicationContext());
                                packageName = context.getPackageName();
                                i = 3007;
                                str = "call business callBack";
                            }
                            a2.a(packageName, a4, i, str);
                            b.m149a("begin execute onNotificationMessageClicked from\u3000" + miPushMessage.getMessageId());
                            a3.onNotificationMessageClicked(context, miPushMessage);
                            return;
                        } else {
                            a3.onNotificationMessageArrived(context, miPushMessage);
                            return;
                        }
                    } else if (a5 instanceof MiPushCommandMessage) {
                        MiPushCommandMessage miPushCommandMessage2 = (MiPushCommandMessage) a5;
                        a3.onCommandResult(context, miPushCommandMessage2);
                        if (TextUtils.equals(miPushCommandMessage2.getCommand(), fb.COMMAND_REGISTER.f26a)) {
                            a3.onReceiveRegisterResult(context, miPushCommandMessage2);
                            if (miPushCommandMessage2.getResultCode() != 0) {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                j.b(context);
            }
        } catch (RuntimeException e) {
            b.a(e);
        }
    }

    public static void startService(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, MessageHandleService.class));
        aj.a(context).a(new i(context, intent));
    }

    @Override // com.xiaomi.mipush.sdk.BaseService
    /* renamed from: a */
    protected boolean mo721a() {
        ConcurrentLinkedQueue<a> concurrentLinkedQueue = a;
        return concurrentLinkedQueue != null && concurrentLinkedQueue.size() > 0;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // com.xiaomi.mipush.sdk.BaseService, android.app.Service
    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
    }
}
