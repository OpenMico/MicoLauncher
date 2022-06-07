package com.blankj.utilcode.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class UiMessageUtils implements Handler.Callback {
    private static final boolean a = b.h();
    private final Handler b;
    private final UiMessage c;
    private final SparseArray<List<UiMessageCallback>> d;
    private final List<UiMessageCallback> e;
    private final List<UiMessageCallback> f;

    /* loaded from: classes.dex */
    public interface UiMessageCallback {
        void handleMessage(@NonNull UiMessage uiMessage);
    }

    public static UiMessageUtils getInstance() {
        return a.a;
    }

    private UiMessageUtils() {
        this.b = new Handler(Looper.getMainLooper(), this);
        this.c = new UiMessage(null);
        this.d = new SparseArray<>();
        this.e = new ArrayList();
        this.f = new ArrayList();
    }

    public final void send(int i) {
        this.b.sendEmptyMessage(i);
    }

    public final void send(int i, @NonNull Object obj) {
        if (obj != null) {
            Handler handler = this.b;
            handler.sendMessage(handler.obtainMessage(i, obj));
            return;
        }
        throw new NullPointerException("Argument 'obj' of type Object (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void addListener(int i, @NonNull UiMessageCallback uiMessageCallback) {
        if (uiMessageCallback != null) {
            synchronized (this.d) {
                List<UiMessageCallback> list = this.d.get(i);
                if (list == null) {
                    list = new ArrayList<>();
                    this.d.put(i, list);
                }
                if (!list.contains(uiMessageCallback)) {
                    list.add(uiMessageCallback);
                }
            }
            return;
        }
        throw new NullPointerException("Argument 'listener' of type UiMessageCallback (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void addListener(@NonNull UiMessageCallback uiMessageCallback) {
        if (uiMessageCallback != null) {
            synchronized (this.e) {
                if (!this.e.contains(uiMessageCallback)) {
                    this.e.add(uiMessageCallback);
                } else if (a) {
                    Log.w("UiMessageUtils", "Listener is already added. " + uiMessageCallback.toString());
                }
            }
            return;
        }
        throw new NullPointerException("Argument 'listener' of type UiMessageCallback (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void removeListener(@NonNull UiMessageCallback uiMessageCallback) {
        if (uiMessageCallback != null) {
            synchronized (this.e) {
                if (a && !this.e.contains(uiMessageCallback)) {
                    Log.w("UiMessageUtils", "Trying to remove a listener that is not registered. " + uiMessageCallback.toString());
                }
                this.e.remove(uiMessageCallback);
            }
            return;
        }
        throw new NullPointerException("Argument 'listener' of type UiMessageCallback (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public void removeListeners(int i) {
        List<UiMessageCallback> list;
        if (a && ((list = this.d.get(i)) == null || list.size() == 0)) {
            Log.w("UiMessageUtils", "Trying to remove specific listeners that are not registered. ID " + i);
        }
        synchronized (this.d) {
            this.d.delete(i);
        }
    }

    public void removeListener(int i, @NonNull UiMessageCallback uiMessageCallback) {
        if (uiMessageCallback != null) {
            synchronized (this.d) {
                List<UiMessageCallback> list = this.d.get(i);
                if (list == null || list.isEmpty()) {
                    if (a) {
                        Log.w("UiMessageUtils", "Trying to remove specific listener that is not registered. ID " + i + ", " + uiMessageCallback);
                    }
                } else if (!a || list.contains(uiMessageCallback)) {
                    list.remove(uiMessageCallback);
                } else {
                    Log.w("UiMessageUtils", "Trying to remove specific listener that is not registered. ID " + i + ", " + uiMessageCallback);
                    return;
                }
                return;
            }
        }
        throw new NullPointerException("Argument 'listener' of type UiMessageCallback (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        this.c.a(message);
        if (a) {
            a(this.c);
        }
        synchronized (this.d) {
            List<UiMessageCallback> list = this.d.get(message.what);
            if (list != null) {
                if (list.size() == 0) {
                    this.d.remove(message.what);
                } else {
                    this.f.addAll(list);
                    for (UiMessageCallback uiMessageCallback : this.f) {
                        uiMessageCallback.handleMessage(this.c);
                    }
                    this.f.clear();
                }
            }
        }
        synchronized (this.e) {
            if (this.e.size() > 0) {
                this.f.addAll(this.e);
                for (UiMessageCallback uiMessageCallback2 : this.f) {
                    uiMessageCallback2.handleMessage(this.c);
                }
                this.f.clear();
            }
        }
        this.c.a(null);
        return true;
    }

    private void a(@NonNull UiMessage uiMessage) {
        if (uiMessage != null) {
            List<UiMessageCallback> list = this.d.get(uiMessage.getId());
            if ((list == null || list.size() == 0) && this.e.size() == 0) {
                Log.w("UiMessageUtils", "Delivering FAILED for message ID " + uiMessage.getId() + ". No listeners. " + uiMessage.toString());
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Delivering message ID ");
            sb.append(uiMessage.getId());
            sb.append(", Specific listeners: ");
            if (list == null || list.size() == 0) {
                sb.append(0);
            } else {
                sb.append(list.size());
                sb.append(" [");
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).getClass().getSimpleName());
                    if (i < list.size() - 1) {
                        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    }
                }
                sb.append("]");
            }
            sb.append(", Universal listeners: ");
            synchronized (this.e) {
                if (this.e.size() == 0) {
                    sb.append(0);
                } else {
                    sb.append(this.e.size());
                    sb.append(" [");
                    for (int i2 = 0; i2 < this.e.size(); i2++) {
                        sb.append(this.e.get(i2).getClass().getSimpleName());
                        if (i2 < this.e.size() - 1) {
                            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                        }
                    }
                    sb.append("], Message: ");
                }
            }
            sb.append(uiMessage.toString());
            Log.v("UiMessageUtils", sb.toString());
            return;
        }
        throw new NullPointerException("Argument 'msg' of type UiMessage (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    /* loaded from: classes.dex */
    public static final class UiMessage {
        private Message a;

        private UiMessage(Message message) {
            this.a = message;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Message message) {
            this.a = message;
        }

        public int getId() {
            return this.a.what;
        }

        public Object getObject() {
            return this.a.obj;
        }

        public String toString() {
            return "{ id=" + getId() + ", obj=" + getObject() + " }";
        }
    }

    /* loaded from: classes.dex */
    private static final class a {
        private static final UiMessageUtils a = new UiMessageUtils();
    }
}
