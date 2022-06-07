package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.xiaomi.push.hu;
import com.xiaomi.push.ik;
import java.util.List;

/* loaded from: classes4.dex */
public class PushMessageHelper {
    public static final String ERROR_MESSAGE = "error_message";
    public static final String ERROR_TYPE = "error_type";
    public static final String ERROR_TYPE_NEED_PERMISSION = "error_lack_of_permission";
    public static final String KEY_COMMAND = "key_command";
    public static final String KEY_MESSAGE = "key_message";
    public static final int MESSAGE_COMMAND = 3;
    public static final int MESSAGE_ERROR = 5;
    public static final int MESSAGE_QUIT = 4;
    public static final int MESSAGE_RAW = 1;
    public static final int MESSAGE_SENDMESSAGE = 2;
    public static final String MESSAGE_TYPE = "message_type";
    public static final int PUSH_MODE_BROADCAST = 2;
    public static final int PUSH_MODE_CALLBACK = 1;
    private static int a;

    private static void a(int i) {
        a = i;
    }

    private static boolean a(Context context, Intent intent) {
        try {
            List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            if (queryBroadcastReceivers != null) {
                if (!queryBroadcastReceivers.isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    public static MiPushCommandMessage generateCommandMessage(String str, List<String> list, long j, String str2, String str3) {
        MiPushCommandMessage miPushCommandMessage = new MiPushCommandMessage();
        miPushCommandMessage.setCommand(str);
        miPushCommandMessage.setCommandArguments(list);
        miPushCommandMessage.setResultCode(j);
        miPushCommandMessage.setReason(str2);
        miPushCommandMessage.setCategory(str3);
        return miPushCommandMessage;
    }

    public static MiPushMessage generateMessage(ik ikVar, hu huVar, boolean z) {
        MiPushMessage miPushMessage = new MiPushMessage();
        miPushMessage.setMessageId(ikVar.m1056a());
        if (!TextUtils.isEmpty(ikVar.d())) {
            miPushMessage.setMessageType(1);
            miPushMessage.setAlias(ikVar.d());
        } else if (!TextUtils.isEmpty(ikVar.c())) {
            miPushMessage.setMessageType(2);
            miPushMessage.setTopic(ikVar.c());
        } else if (!TextUtils.isEmpty(ikVar.f())) {
            miPushMessage.setMessageType(3);
            miPushMessage.setUserAccount(ikVar.f());
        } else {
            miPushMessage.setMessageType(0);
        }
        miPushMessage.setCategory(ikVar.e());
        if (ikVar.a() != null) {
            miPushMessage.setContent(ikVar.a().c());
        }
        if (huVar != null) {
            if (TextUtils.isEmpty(miPushMessage.getMessageId())) {
                miPushMessage.setMessageId(huVar.m988a());
            }
            if (TextUtils.isEmpty(miPushMessage.getTopic())) {
                miPushMessage.setTopic(huVar.m993b());
            }
            miPushMessage.setDescription(huVar.d());
            miPushMessage.setTitle(huVar.m996c());
            miPushMessage.setNotifyType(huVar.a());
            miPushMessage.setNotifyId(huVar.c());
            miPushMessage.setPassThrough(huVar.b());
            miPushMessage.setExtra(huVar.m989a());
        }
        miPushMessage.setNotified(z);
        return miPushMessage;
    }

    public static hu generateMessage(MiPushMessage miPushMessage) {
        hu huVar = new hu();
        huVar.a(miPushMessage.getMessageId());
        huVar.b(miPushMessage.getTopic());
        huVar.d(miPushMessage.getDescription());
        huVar.c(miPushMessage.getTitle());
        huVar.c(miPushMessage.getNotifyId());
        huVar.a(miPushMessage.getNotifyType());
        huVar.b(miPushMessage.getPassThrough());
        huVar.a(miPushMessage.getExtra());
        return huVar;
    }

    public static int getPushMode(Context context) {
        if (a == 0) {
            a(isUseCallbackPushMode(context) ? 1 : 2);
        }
        return a;
    }

    public static boolean isUseCallbackPushMode(Context context) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setClassName(context.getPackageName(), "com.xiaomi.mipush.sdk.PushServiceReceiver");
        return a(context, intent);
    }

    public static void sendCommandMessageBroadcast(Context context, MiPushCommandMessage miPushCommandMessage) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setPackage(context.getPackageName());
        intent.putExtra(MESSAGE_TYPE, 3);
        intent.putExtra(KEY_COMMAND, miPushCommandMessage);
        new PushServiceReceiver().onReceive(context, intent);
    }

    public static void sendQuitMessageBroadcast(Context context) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setPackage(context.getPackageName());
        intent.putExtra(MESSAGE_TYPE, 4);
        new PushServiceReceiver().onReceive(context, intent);
    }
}
