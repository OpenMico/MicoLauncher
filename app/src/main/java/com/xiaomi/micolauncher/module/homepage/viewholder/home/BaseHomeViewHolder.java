package com.xiaomi.micolauncher.module.homepage.viewholder.home;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public abstract class BaseHomeViewHolder extends BaseViewHolder {
    protected static final int MSG_REFRESH_MEDIA = 1001;
    protected static final int MSG_REFRESH_NEWS = 1000;
    private static final long a = TimeUnit.SECONDS.toMillis(8);
    private static final long b = TimeUnit.SECONDS.toMillis(4);
    private static final long c = TimeUnit.SECONDS.toMillis(30);
    protected Context context;
    protected int currentMediaIndex;
    private WeakRefHandler e;
    private boolean d = true;
    private Handler.Callback f = new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.home.BaseHomeViewHolder.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1000:
                    BaseHomeViewHolder.this.refreshNews();
                    return false;
                case 1001:
                    BaseHomeViewHolder.this.refreshMedia();
                    return false;
                case 1002:
                    BaseHomeViewHolder.this.sendRefreshMsg();
                    return false;
                default:
                    return false;
            }
        }
    };
    protected String tag = getClass().getName();

    protected void defaultFillData() {
    }

    protected abstract boolean empty();

    protected abstract void fillData();

    protected void refreshNews() {
    }

    public BaseHomeViewHolder(View view) {
        super(view);
        this.context = view.getContext();
    }

    private void a() {
        if (this.e == null) {
            this.e = new WeakRefHandler(this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initViewInMain() {
        a();
    }

    public void setData() {
        this.currentMediaIndex = 0;
        defaultFillData();
        if (!empty()) {
            fillData();
            removeRefreshMsg();
            this.e.sendEmptyMessageDelayed(1002, c);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void refreshMedia() {
        b();
    }

    public void sendRefreshMsg() {
        removeRefreshMsg();
        if (this.d) {
            this.e.sendEmptyMessageDelayed(1002, c);
            this.d = false;
        } else if (!empty() && ActivityLifeCycleManager.getInstance().isHomeInForeground()) {
            sendNewsRefreshMsg();
            b();
        }
    }

    private void b() {
        L.homepage.i("%s send message %d", getClass().getName(), 1001);
        this.e.sendEmptyMessageDelayed(1001, a);
    }

    protected void sendNewsRefreshMsg() {
        L.homepage.i("%s send message %d", getClass().getName(), 1000);
        this.e.sendEmptyMessageDelayed(1000, b);
    }

    public void removeRefreshMsg() {
        this.e.removeMessages(1000);
        this.e.removeMessages(1001);
    }

    protected int getIndex(int i, int i2) {
        return i % i2;
    }
}
