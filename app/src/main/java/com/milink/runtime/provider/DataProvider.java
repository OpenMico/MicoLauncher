package com.milink.runtime.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.milink.base.utils.Logger;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public abstract class DataProvider {
    public static final Class[] CALL_METHOD_PARAMS = {String.class, String.class, Bundle.class};
    public static final int OPT_DELETE = 8;
    public static final int OPT_INSERT = 2;
    public static final int OPT_QUERY = 1;
    public static final int OPT_UPDATE = 4;
    public static final int ROOT = 1;
    private final Map<String, Integer> a = new ArrayMap();
    private final UriMatcher b = new UriMatcher(-1);
    private final Object c = new Object();
    private Context d;
    private String e;
    private List<a> f;

    /* loaded from: classes2.dex */
    public interface PathMapper {
        void addPath(String str, int i);
    }

    protected Bundle call(@NonNull String str, @Nullable String str2, @Nullable Bundle bundle) throws DataProviderException {
        return null;
    }

    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) throws DataProviderException {
        return 0;
    }

    @Nullable
    public abstract String getType(@NonNull Uri uri);

    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) throws DataProviderException {
        return null;
    }

    public boolean onCreate() {
        return true;
    }

    protected void onRegisterPath(@NonNull PathMapper pathMapper) {
    }

    @Nullable
    protected Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2, @NonNull String str3) throws DataProviderException {
        return null;
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) throws DataProviderException {
        return 0;
    }

    public static Cursor requireValidCursor(Context context, @NonNull Uri uri, Future<Cursor> future) throws DataProviderException {
        Cursor cursor;
        try {
            cursor = future.get(20L, TimeUnit.SECONDS);
        } catch (TimeoutException unused) {
            throw new DataProviderException(BaseCode.TIMEOUT_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    public static Cursor requireValidCursor(Context context, @NonNull Uri uri, Cursor cursor) throws DataProviderException {
        if (cursor != null) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        }
        throw new DataProviderException(BaseCode.TIMEOUT_ERROR);
    }

    public void a(@NonNull String str) {
        if (this.e == null) {
            this.e = (String) Objects.requireNonNull(str);
            this.b.addURI("*", str, 1);
            onRegisterPath(new PathMapper() { // from class: com.milink.runtime.provider.-$$Lambda$DataProvider$3C0X43Axl5XaqV12z-CK9bq41Xg
                @Override // com.milink.runtime.provider.DataProvider.PathMapper
                public final void addPath(String str2, int i) {
                    DataProvider.this.a(str2, i);
                }
            });
            return;
        }
        throw new IllegalStateException("root path already attached!");
    }

    public void a(@NonNull String str, int i) {
        String str2;
        if (i != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.e);
            if (str.startsWith("/")) {
                str2 = str;
            } else {
                str2 = "/" + str;
            }
            sb.append(str2);
            String sb2 = sb.toString();
            Integer put = this.a.put(sb2, Integer.valueOf(i));
            if (put == null) {
                this.b.addURI("*", sb2, i);
                return;
            }
            throw new IllegalStateException(String.format("sub path %s has already map to %s", str, put));
        }
        throw new IllegalStateException(String.format("path %s, id = %d, is already use by root", str, Integer.valueOf(i)));
    }

    protected int matchPath(@NonNull Uri uri) {
        return this.b.match(uri);
    }

    public void a(@NonNull Context context) {
        this.d = (Context) Objects.requireNonNull(context);
    }

    @Nullable
    public String a(@NonNull Uri uri) {
        return this.b.match(uri) == 1 ? "vnd.android.cursor.item/path_list" : getType(uri);
    }

    public Cursor a(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2, @NonNull String str3) {
        if (this.b.match(uri) == 1) {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{"path"}, this.a.size());
            Iterator<String> it = this.a.keySet().iterator();
            while (it.hasNext()) {
                matrixCursor.addRow(new Object[]{it.next()});
            }
            return matrixCursor;
        }
        try {
            return query(uri, strArr, str, strArr2, str2, str3);
        } catch (DataProviderException e) {
            int code = e.getCode();
            if (code != 0) {
                return DataUtils.obtainEmptyCursor(code, e.getExtra());
            }
            return DataUtils.obtainEmptyCursor(BaseCode.UNKNOWN_ERROR, null);
        } catch (IllegalStateException e2) {
            Logger.e("DataProvider", e2, "handleQuery illegal state", new Object[0]);
            return DataUtils.obtainEmptyCursor(BaseCode.UNKNOWN_ERROR, null);
        }
    }

    public Bundle a(@NonNull String str, String str2, Bundle bundle) {
        try {
            a(getClass().getDeclaredMethods());
            a b = b(str);
            if (b != null) {
                return b.a(this, str, str2, bundle);
            }
            return call(str, str2, bundle);
        } catch (DataProviderException e) {
            throw DataContentProvider.a(e.getCode());
        } catch (IllegalStateException e2) {
            Logger.e("DataProvider", e2, "handleCall illegal state", new Object[0]);
            throw DataContentProvider.a(BaseCode.UNKNOWN_ERROR, e2);
        }
    }

    @Nullable
    private a b(@NonNull String str) {
        List<a> list = this.f;
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (a aVar : this.f) {
            if (aVar.a(str)) {
                return aVar;
            }
        }
        return null;
    }

    private void a(Method[] methodArr) {
        if (this.f == null) {
            synchronized (this.c) {
                if (this.f == null) {
                    for (Method method : methodArr) {
                        CallMethod callMethod = (CallMethod) method.getAnnotation(CallMethod.class);
                        if (callMethod != null) {
                            if (callMethod.value().length <= 0 && TextUtils.isEmpty(callMethod.regex())) {
                                throw new IllegalStateException("Not found method name in " + method);
                            }
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            if (Arrays.equals(parameterTypes, CALL_METHOD_PARAMS)) {
                                if (this.f == null) {
                                    this.f = new LinkedList();
                                }
                                this.f.add(new a(method, callMethod.value(), callMethod.regex()));
                            } else {
                                throw new IllegalStateException("Not found method name in " + method + ", params : " + Arrays.toString(parameterTypes));
                            }
                        }
                    }
                    if (this.f == null) {
                        this.f = Collections.emptyList();
                    }
                }
            }
        }
    }

    public Uri a(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        try {
            return insert(uri, contentValues);
        } catch (DataProviderException e) {
            throw DataContentProvider.a(e.getCode());
        } catch (IllegalStateException e2) {
            Logger.e("DataProvider", e2, "handleInsert illegal state", new Object[0]);
            throw DataContentProvider.a(BaseCode.UNKNOWN_ERROR, e2);
        }
    }

    public int a(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        try {
            return delete(uri, str, strArr);
        } catch (DataProviderException e) {
            throw DataContentProvider.a(e.getCode());
        } catch (IllegalStateException e2) {
            Logger.e("DataProvider", e2, "handleDelete illegal state", new Object[0]);
            throw DataContentProvider.a(BaseCode.UNKNOWN_ERROR, e2);
        }
    }

    public int a(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        try {
            return update(uri, contentValues, str, strArr);
        } catch (DataProviderException e) {
            throw DataContentProvider.a(e.getCode());
        } catch (IllegalStateException e2) {
            Logger.e("DataProvider", e2, "handleUpdate illegal state", new Object[0]);
            throw DataContentProvider.a(BaseCode.UNKNOWN_ERROR, e2);
        }
    }

    @NonNull
    protected Context getContext() {
        return (Context) Objects.requireNonNull(this.d, "provider not attach context");
    }
}
