package com.xiaomi.micolauncher.module.appstore;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.alibaba.fastjson.asm.Opcodes;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class NotifyMessageDialog extends Dialog {
    View a;

    public NotifyMessageDialog(Context context) {
        super(context, R.style.DialogAlbum);
        this.a = LayoutInflater.from(context).inflate(R.layout.dialog_notify_message, (ViewGroup) null);
        setContentView(this.a);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = 478;
        attributes.height = Opcodes.INVOKEINTERFACE;
        window.setAttributes(attributes);
    }

    public NotifyMessageDialog setMessage(int i) {
        ((TextView) this.a.findViewById(R.id.custom_dialog_message)).setText(i);
        return this;
    }

    public NotifyMessageDialog setWidth(int i) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = i;
        window.setAttributes(attributes);
        return this;
    }
}
