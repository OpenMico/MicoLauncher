package com.milink.runtime.lock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.base.contract.LockContract;
import com.milink.base.contract.MiLinkKeys;
import com.milink.base.utils.AndroidContextUtil;
import com.milink.base.utils.Logger;
import com.milink.base.utils.TransactionLockProvider;
import com.milink.runtime.PrivilegedPackageManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public class LockContentProvider extends ContentProvider {
    private static final long a = TimeUnit.SECONDS.toMillis(6);
    private static final long b = TimeUnit.SECONDS.toMillis(30);
    private static final List<String> c = Arrays.asList(MiLinkKeys.PARAM_EVENT, MiLinkKeys.PARAM_REPLAY_ID);
    private LockRecordHelper g;
    private LockHelper h;
    private Handler i;
    private final a d = new a();
    private final ExecutorService e = Executors.newCachedThreadPool();
    private final TransactionLockProvider f = new TransactionLockProvider();
    private final Handler.Callback j = new Handler.Callback() { // from class: com.milink.runtime.lock.LockContentProvider.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 1:
                    LockContentProvider.this.a();
                    return true;
                case 2:
                    LockContentProvider.this.a(message);
                    return true;
                default:
                    return true;
            }
        }
    };

    @Override // android.content.ContentProvider
    @Nullable
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/vnd.com.milink.lock";
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        return null;
    }

    public static void prepare(@NonNull Context context) {
        final LockDatabase lockDatabase = LockDatabase.get(context);
        lockDatabase.runInTransaction(new Runnable() { // from class: com.milink.runtime.lock.-$$Lambda$LockContentProvider$G3Ek8Vq0bN6NnDos8wA6mlApQT4
            @Override // java.lang.Runnable
            public final void run() {
                LockContentProvider.a(LockDatabase.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(LockDatabase lockDatabase) {
        lockDatabase.getActiveLockDao().clear();
        lockDatabase.getLockRecordDao().clear();
        Logger.d("LockContentProvider", "clear lock cache done.", new Object[0]);
    }

    @Nullable
    private static LockInfo a(Uri uri) {
        if (uri != null && LockContract.Matcher.match(uri) == 2) {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments.size() == 3 && LockContract.Matcher.LOCK.equals(pathSegments.get(0))) {
                return new LockInfo(pathSegments.get(1), pathSegments.get(2));
            }
        }
        return null;
    }

    @NonNull
    private static Bundle a(@NonNull ContentValues contentValues) {
        Set<String> keySet = contentValues.keySet();
        Bundle bundle = new Bundle();
        for (String str : keySet) {
            if (!c.contains(str)) {
                bundle.putString(str, contentValues.getAsString(str));
            }
        }
        return bundle;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        HandlerThread handlerThread = new HandlerThread("lock_content_Provider");
        handlerThread.start();
        this.i = new Handler(handlerThread.getLooper(), this.j);
        this.h = new LockHelper(getContext(), LockContract.SCOPE_LOCAL_DEVICE);
        this.g = new LockRecordHelper(getContext());
        this.i.sendEmptyMessage(1);
        return true;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String callingPackage = getCallingPackage();
        a(callingPackage);
        if (contentValues == null) {
            return uri.buildUpon().clearQuery().appendQueryParameter("code", String.valueOf(-1)).build();
        }
        if (LockContract.Matcher.match(uri) != 2) {
            return uri.buildUpon().clearQuery().appendQueryParameter("code", String.valueOf(-1)).build();
        }
        LockInfo lockInfo = new LockInfo(contentValues.getAsString(LockContract.COL_LOCK_SCOPE), contentValues.getAsString(LockContract.COL_LOCK_NAME));
        lockInfo.identify = callingPackage;
        String asString = contentValues.getAsString(LockContract.COL_TAG);
        if (TextUtils.isEmpty(asString)) {
            Logger.w("LockContentProvider", "request lock fail, tag can't be empty.", new Object[0]);
            return uri.buildUpon().clearQuery().appendQueryParameter("code", String.valueOf(-1)).build();
        }
        lockInfo.tag = asString.trim();
        if (this.f.hasTransactionVisitor(lockInfo.lockName, "release")) {
            return uri.buildUpon().clearQuery().appendQueryParameter("code", String.valueOf(3)).build();
        }
        TransactionLockProvider.ITransactionLock require = this.f.require(lockInfo.lockName, "grant");
        try {
            require.startTransaction();
            try {
                return a(uri, lockInfo);
            } finally {
                require.endTransaction();
            }
        } catch (InterruptedException unused) {
            Logger.w("LockContentProvider", "Interrupted when require lock transaction.", new Object[0]);
            return uri.buildUpon().clearQuery().appendQueryParameter("code", String.valueOf(4)).build();
        }
    }

    @Override // android.content.ContentProvider
    public int delete(@NonNull final Uri uri, @Nullable String str, @Nullable String[] strArr) {
        a(getCallingPackage());
        if (LockContract.Matcher.match(uri) != 2) {
            return -1;
        }
        final String callingPackage = getCallingPackage();
        final String queryParameter = uri.getQueryParameter(MiLinkKeys.PARAM_TAG);
        String lastPathSegment = uri.getLastPathSegment();
        if (TextUtils.isEmpty(queryParameter) || TextUtils.isEmpty(lastPathSegment)) {
            return -1;
        }
        final TransactionLockProvider.ITransactionLock require = this.f.require(lastPathSegment, "release", 1);
        if (require == null) {
            return 0;
        }
        try {
            require.startTransaction();
            String queryParameter2 = uri.getQueryParameter(MiLinkKeys.PARAM_EVENT);
            String queryParameter3 = uri.getQueryParameter(MiLinkKeys.PARAM_TO_IDENTIFY);
            String queryParameter4 = uri.getQueryParameter(MiLinkKeys.PARAM_TO_TAG);
            if (LockContract.Event.LOCK_TRANSFER.equals(queryParameter2) && !TextUtils.isEmpty(queryParameter3) && !TextUtils.isEmpty(queryParameter4) && !this.g.hasLockRecord(LockContract.SCOPE_LOCAL_DEVICE, lastPathSegment, queryParameter3, queryParameter4)) {
                return 2;
            }
            LockInfo byLockName = this.h.getByLockName(lastPathSegment);
            if (byLockName == null || !Objects.equals(byLockName.identify, callingPackage) || !Objects.equals(byLockName.tag, queryParameter)) {
                require.endTransaction();
                return b(uri, callingPackage, queryParameter);
            }
            Logger.d("LockContentProvider", "start async revoke lock %s : %s", callingPackage, uri);
            this.e.execute(new Runnable() { // from class: com.milink.runtime.lock.-$$Lambda$LockContentProvider$GTlaXMeQIXBjcN9Ei5j7vbwfkO8
                @Override // java.lang.Runnable
                public final void run() {
                    LockContentProvider.this.a(callingPackage, uri, queryParameter, require);
                }
            });
            return 0;
        } catch (InterruptedException unused) {
            Logger.w("LockContentProvider", "Interrupted when release lock transaction.", new Object[0]);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, Uri uri, String str2, TransactionLockProvider.ITransactionLock iTransactionLock) {
        Logger.d("LockContentProvider", "async revoke lock %s : %s", str, uri);
        try {
            a(uri, str, str2, true);
        } finally {
            iTransactionLock.endTransaction();
        }
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Bundle call(@NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        char c2;
        a(getCallingPackage());
        int hashCode = str.hashCode();
        if (hashCode != -366612090) {
            if (hashCode == -76986864 && str.equals(LockContract.Action.TICK_INFO)) {
                c2 = 0;
            }
            c2 = 65535;
        } else {
            if (str.equals(LockContract.Action.LOCK_STATUS)) {
                c2 = 1;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                Bundle bundle2 = new Bundle();
                bundle2.putLong(MiLinkKeys.PARAM_INTERVAL, b);
                return bundle2;
            case 1:
                LockContract.LockUrn parse = LockContract.LockUrn.parse(str2);
                if (parse != null && LockContract.SCOPE_LOCAL_DEVICE.equals(parse.getLockScope())) {
                    LockInfo byLockName = this.h.getByLockName(parse.getLockName());
                    String str3 = (byLockName == null || byLockName.identify == null) ? "" : byLockName.identify;
                    String str4 = (byLockName == null || byLockName.tag == null) ? "" : byLockName.tag;
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("scope", parse.getLockScope());
                    bundle3.putString("name", parse.getLockName());
                    bundle3.putString(MiLinkKeys.PARAM_IDENTIFY, str3);
                    bundle3.putString(MiLinkKeys.PARAM_TAG, str4);
                    return bundle3;
                }
                break;
            default:
                Logger.w("LockContentProvider", "Unknown method %s", str);
                break;
        }
        return Bundle.EMPTY;
    }

    @Override // android.content.ContentProvider
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        String queryParameter;
        a(getCallingPackage());
        try {
            int match = LockContract.Matcher.match(uri);
            if (match != 1) {
                return (match != 2 || (queryParameter = uri.getQueryParameter(MiLinkKeys.PARAM_EVENT)) == null || !a(queryParameter, uri, contentValues)) ? -1 : 0;
            }
            String callingPackage = getCallingPackage();
            String queryParameter2 = uri.getQueryParameter(MiLinkKeys.PARAM_TAG);
            String queryParameter3 = uri.getQueryParameter(MiLinkKeys.PARAM_IDENTIFY);
            if (queryParameter2 != null && (queryParameter3 == null || Objects.equals(queryParameter3, callingPackage))) {
                String intern = b().appendQueryParameter(MiLinkKeys.PARAM_IDENTIFY, callingPackage).appendQueryParameter(MiLinkKeys.PARAM_TAG, queryParameter2).build().toString().intern();
                Logger.d("LockContentProvider", "rm auto release task, %s : %s", intern, uri);
                this.i.removeMessages(2, intern);
                return 0;
            }
            Logger.w("LockContentProvider", "request package is %s, but (idy)%s - (tag)%s", callingPackage, queryParameter3, queryParameter2);
            return -1;
        } catch (Exception e) {
            Logger.e("LockContentProvider", "handle %s, error %s", uri, e);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        TransactionLockProvider.ITransactionLock require;
        String str = (String) message.obj;
        if (str != null) {
            Uri parse = Uri.parse(str);
            String queryParameter = parse.getQueryParameter(MiLinkKeys.PARAM_IDENTIFY);
            String queryParameter2 = parse.getQueryParameter(MiLinkKeys.PARAM_TAG);
            if (queryParameter == null || queryParameter2 == null) {
                Logger.e("LockContentProvider", "handle auto release lock, but param is null. %s", str);
                return;
            }
            LockInfo[] byIdentifyAndTag = this.h.getByIdentifyAndTag(queryParameter, queryParameter2);
            if (byIdentifyAndTag == null || byIdentifyAndTag.length == 0) {
                Logger.w("LockContentProvider", "not found active lock, when auto release %s", str);
                return;
            }
            for (LockInfo lockInfo : byIdentifyAndTag) {
                if (!(lockInfo == null || this.f.hasTransactionVisitor(lockInfo.lockName, "release") || (require = this.f.require(lockInfo.lockName, "release_auto", 1)) == null)) {
                    try {
                        require.startTransaction();
                        try {
                            Uri lockUri = LockContract.Matcher.getLockUri(lockInfo.lockScope, lockInfo.lockName);
                            Logger.w("LockContentProvider", "!! Auto release %s-%s : %s", lockInfo.identify, lockInfo.tag, lockUri);
                            a(lockUri, lockInfo.identify, lockInfo.tag, false);
                        } finally {
                            require.endTransaction();
                        }
                    } catch (InterruptedException unused) {
                        Logger.w("LockContentProvider", "Interrupted when auto release lock transaction.", new Object[0]);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    private void a(Uri uri, final String str, final String str2, boolean z) {
        LockInfo a2 = a(uri);
        if (a2 == null) {
            Logger.e("LockContentProvider", "revoke lock fail, can't parse uri : %s", uri);
            return;
        }
        if (z) {
            String a3 = this.d.a();
            final Uri build = uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.LOCK_REVOKE_BEFORE).appendQueryParameter(MiLinkKeys.PARAM_REPLAY_ID, a3).build();
            this.e.execute(new Runnable() { // from class: com.milink.runtime.lock.-$$Lambda$LockContentProvider$1EwfP_2tUJbbilS6YWcL0a7z8Y0
                @Override // java.lang.Runnable
                public final void run() {
                    LockContentProvider.this.c(build, str, str2);
                }
            });
            try {
                this.d.a(a3, 60, TimeUnit.SECONDS);
            } catch (InterruptedException | TimeoutException e) {
                Logger.w("LockContentProvider", "wait revoke lock reply %s, %s", e, uri);
            }
        }
        if (!this.h.removeByLockName(a2.lockName, str, str2)) {
            Logger.w("LockContentProvider", "error!! revoke lock fail : %s", uri);
            return;
        }
        if (z) {
            c(uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.LOCK_REVOKED).build(), str, str2);
        }
        Logger.d("LockContentProvider", "redispatch local lock: %s", uri);
        c(uri);
    }

    private void b(@NonNull Uri uri) {
        Logger.d("LockContentProvider", "send broadcast: %s", uri);
        AndroidContextUtil.compatNotifyChange(getContext().getContentResolver(), (Uri) Objects.requireNonNull(uri), null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void c(@NonNull Uri uri, @NonNull String str, @NonNull String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("identify or tag is empty.");
        }
        Uri build = ((Uri) Objects.requireNonNull(uri)).buildUpon().appendPath(LockContract.genIdentifyString(str, str2)).build();
        Logger.d("LockContentProvider", "send event: %s", build);
        AndroidContextUtil.compatNotifyChange(getContext().getContentResolver(), build, null, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (this.h.size() != 0) {
            LockInfo[] all = this.h.getAll();
            for (LockInfo lockInfo : all) {
                String intern = b().appendQueryParameter(MiLinkKeys.PARAM_IDENTIFY, lockInfo.identify).appendQueryParameter(MiLinkKeys.PARAM_TAG, lockInfo.tag).build().toString().intern();
                Message obtain = Message.obtain(this.i, 2, intern);
                this.i.removeMessages(2, intern);
                this.i.sendMessageDelayed(obtain, a);
                b(LockContract.Matcher.getTickUri(lockInfo.lockScope, lockInfo.lockName));
            }
            this.i.removeMessages(1);
            this.i.sendEmptyMessageDelayed(1, b);
        }
    }

    private Uri a(@NonNull Uri uri, @NonNull LockInfo lockInfo) {
        if (!this.i.hasMessages(1)) {
            this.i.sendEmptyMessageDelayed(1, b);
        }
        LockInfo byLockName = this.h.getByLockName(lockInfo.lockName);
        if (byLockName == null) {
            this.h.addLock(lockInfo);
            Uri build = uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.LOCK_GRANTED).appendQueryParameter("code", String.valueOf(0)).build();
            c(build, lockInfo.identify, lockInfo.tag);
            a(lockInfo);
            return build;
        } else if (!Objects.equals(byLockName.identify, lockInfo.identify) || !Objects.equals(byLockName.tag, lockInfo.tag)) {
            this.g.addLockRecord(lockInfo);
            c(uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.ASK_FOR_LOCK).appendQueryParameter(MiLinkKeys.PARAM_TAG, lockInfo.tag).appendQueryParameter(MiLinkKeys.PARAM_IDENTIFY, lockInfo.identify).build(), byLockName.identify, byLockName.tag);
            return uri.buildUpon().clearQuery().appendQueryParameter("code", String.valueOf(0)).build();
        } else {
            Uri build2 = uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.LOCK_GRANTED).appendQueryParameter("code", String.valueOf(0)).build();
            c(build2, byLockName.identify, byLockName.tag);
            return build2;
        }
    }

    private void a(@NonNull LockInfo lockInfo) {
        Uri.Builder buildUpon = LockContract.Matcher.getLockStatusChangeUri(lockInfo.lockScope, lockInfo.lockName).buildUpon();
        if (!TextUtils.isEmpty(lockInfo.identify)) {
            buildUpon.appendQueryParameter(MiLinkKeys.PARAM_IDENTIFY, lockInfo.identify);
        }
        if (!TextUtils.isEmpty(lockInfo.tag)) {
            buildUpon.appendQueryParameter(MiLinkKeys.PARAM_TAG, lockInfo.tag);
        }
        b(buildUpon.build());
    }

    private void c(@NonNull Uri uri) {
        LockInfo lockInfo;
        LockInfo a2 = a(uri);
        if (a2 == null) {
            Logger.w("LockContentProvider", "illegal lock uri: %s", uri);
            return;
        }
        LockInfo filoPop = this.g.filoPop(a2.lockScope, a2.lockName);
        if (filoPop == null) {
            Logger.i("LockContentProvider", "no lock request for: %s", uri);
            a(a2);
            return;
        }
        String queryParameter = uri.getQueryParameter(MiLinkKeys.PARAM_EVENT);
        String queryParameter2 = uri.getQueryParameter(MiLinkKeys.PARAM_TO_IDENTIFY);
        String queryParameter3 = uri.getQueryParameter(MiLinkKeys.PARAM_TO_TAG);
        Uri build = uri.buildUpon().clearQuery().build();
        if (!Objects.equals(queryParameter, LockContract.Event.LOCK_TRANSFER) || TextUtils.isEmpty(queryParameter2) || TextUtils.isEmpty(queryParameter3) || (lockInfo = a(build)) == null) {
            lockInfo = filoPop;
        } else {
            lockInfo.identify = queryParameter2;
            lockInfo.tag = queryParameter3;
        }
        if (!this.g.remove(lockInfo)) {
            c(build);
        } else if (!Objects.equals(a(build, lockInfo).getQueryParameter("code"), String.valueOf(0))) {
            throw new IllegalStateException("transfer lock fail");
        }
    }

    private int b(@NonNull Uri uri, @NonNull String str, String str2) {
        LockInfo a2 = a(uri);
        if (a2 != null) {
            a2.identify = (String) Objects.requireNonNull(str);
            a2.tag = (String) Objects.requireNonNull(str2);
            boolean remove = this.g.remove(a2);
            Logger.d("LockContentProvider", "rm pending lock request %s:%s", str, str2);
            return remove ? 0 : -1;
        }
        Logger.w("LockContentProvider", "rm lock but uri invalidate: %s", uri);
        return -1;
    }

    private boolean a(@NonNull String str, @NonNull Uri uri, ContentValues contentValues) {
        char c2;
        int hashCode = str.hashCode();
        if (hashCode != -1091295882) {
            if (hashCode == 1555454199 && str.equals(LockContract.Event.ASK_FOR_LOCK_REJECT)) {
                c2 = 0;
            }
            c2 = 65535;
        } else {
            if (str.equals(LockContract.Event.CLIENT_REPLY)) {
                c2 = 1;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                return d(uri);
            case 1:
                return this.d.a(uri.getQueryParameter(MiLinkKeys.PARAM_REPLAY_ID), a(contentValues));
            default:
                Logger.w("LockContentProvider", "unknown reply: %s, %s", str, uri);
                return false;
        }
    }

    private boolean d(Uri uri) {
        String queryParameter = uri.getQueryParameter(MiLinkKeys.PARAM_TO_IDENTIFY);
        String queryParameter2 = uri.getQueryParameter(MiLinkKeys.PARAM_TO_TAG);
        if (TextUtils.isEmpty(queryParameter) || TextUtils.isEmpty(queryParameter2)) {
            return false;
        }
        c(uri.buildUpon().clearQuery().appendQueryParameter(MiLinkKeys.PARAM_EVENT, LockContract.Event.ASK_FOR_LOCK_REJECT).build(), queryParameter, queryParameter2);
        return true;
    }

    private Uri.Builder b() {
        return LockContract.Matcher.ROOT_URI.buildUpon();
    }

    private void a(String str) {
        if (!PrivilegedPackageManager.isPrivilegedPackage(getContext(), str)) {
            throw new SecurityException("Permission Denial For " + str);
        }
    }
}
