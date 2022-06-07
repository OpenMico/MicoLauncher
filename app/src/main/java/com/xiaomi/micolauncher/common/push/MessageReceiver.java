package com.xiaomi.micolauncher.common.push;

import android.content.Context;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import java.util.List;

/* loaded from: classes3.dex */
public class MessageReceiver extends PushMessageReceiver {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        this.b = miPushMessage.getContent();
        if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
            this.c = miPushMessage.getTopic();
        } else if (!TextUtils.isEmpty(miPushMessage.getAlias())) {
            this.d = miPushMessage.getAlias();
        } else if (!TextUtils.isEmpty(miPushMessage.getUserAccount())) {
            this.e = miPushMessage.getUserAccount();
        }
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        this.b = miPushMessage.getContent();
        if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
            this.c = miPushMessage.getTopic();
        } else if (!TextUtils.isEmpty(miPushMessage.getAlias())) {
            this.d = miPushMessage.getAlias();
        } else if (!TextUtils.isEmpty(miPushMessage.getUserAccount())) {
            this.e = miPushMessage.getUserAccount();
        }
        Logger logger = L.push;
        logger.i("message:" + this.b);
        SchemaManager.handleSchema(context, this.b);
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        this.b = miPushMessage.getContent();
        if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
            this.c = miPushMessage.getTopic();
        } else if (!TextUtils.isEmpty(miPushMessage.getAlias())) {
            this.d = miPushMessage.getAlias();
        } else if (!TextUtils.isEmpty(miPushMessage.getUserAccount())) {
            this.e = miPushMessage.getUserAccount();
        }
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        String command = miPushCommandMessage.getCommand();
        List<String> commandArguments = miPushCommandMessage.getCommandArguments();
        String str = null;
        String str2 = (commandArguments == null || commandArguments.size() <= 0) ? null : commandArguments.get(0);
        if (commandArguments != null && commandArguments.size() > 1) {
            str = commandArguments.get(1);
        }
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == 0) {
                this.a = str2;
                Logger logger = L.push;
                logger.i("pushId: " + this.a);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (miPushCommandMessage.getResultCode() == 0) {
                this.d = str2;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (miPushCommandMessage.getResultCode() == 0) {
                this.d = str2;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (miPushCommandMessage.getResultCode() == 0) {
                this.c = str2;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (miPushCommandMessage.getResultCode() == 0) {
                this.c = str2;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command) && miPushCommandMessage.getResultCode() == 0) {
            this.f = str2;
            this.g = str;
        }
    }

    @Override // com.xiaomi.mipush.sdk.PushMessageReceiver
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        String command = miPushCommandMessage.getCommand();
        List<String> commandArguments = miPushCommandMessage.getCommandArguments();
        String str = (commandArguments == null || commandArguments.size() <= 0) ? null : commandArguments.get(0);
        if (commandArguments != null && commandArguments.size() > 1) {
            commandArguments.get(1);
        }
        if (MiPushClient.COMMAND_REGISTER.equals(command) && miPushCommandMessage.getResultCode() == 0) {
            this.a = str;
            Logger logger = L.push;
            logger.i("pushId: " + this.a);
        }
    }
}
