package com.milink.runtime.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.base.utils.Logger;
import com.milink.runtime.PrivilegedPackageManager;

/* loaded from: classes2.dex */
public final class DataContentProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    public static IllegalStateException a(int i) {
        return a(i, (Exception) null);
    }

    public static IllegalStateException a(int i, Exception exc) {
        if (i != 0) {
            return new IllegalStateException(String.valueOf(i), exc);
        }
        throw new RuntimeException("code can't be 0");
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Cursor query(@NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        if (a(uri)) {
            return null;
        }
        b b = b(uri);
        String callingPackage = getCallingPackage();
        if (callingPackage == null) {
            return DataUtils.obtainEmptyCursor(BaseCode.UNKNOWN_CALLING_PACKAGE_ERROR, null);
        }
        a(uri, callingPackage, b);
        DataProvider a = b.a(a());
        if (a == null) {
            return DataUtils.obtainEmptyCursor(BaseCode.NOT_FOUND_PROVIDER, null);
        }
        int a2 = a(b, a, uri, 1);
        if (a2 != 0) {
            return DataUtils.obtainEmptyCursor(a2, null);
        }
        Logger.i("DataContentProvider", "Query %s from %s", uri, callingPackage);
        return a.a(uri, strArr, str, strArr2, str2, callingPackage);
    }

    @Override // android.content.ContentProvider
    @Nullable
    public String getType(@NonNull Uri uri) {
        if (a(uri)) {
            return "vnd.android.cursor.item/none";
        }
        DataProvider a = b(uri).a(a());
        if (a != null) {
            return a.a(uri);
        }
        return null;
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        b b = b(uri);
        b(uri, getCallingPackage(), b);
        DataProvider a = b.a(a());
        a(uri, b, a);
        Logger.i("DataContentProvider", "Insert %s from %s", uri, getCallingPackage());
        return a.a(uri, contentValues);
    }

    @Override // android.content.ContentProvider
    public int delete(@NonNull Uri uri, @Nullable String str, @Nullable String[] strArr) {
        b b = b(uri);
        b(uri, getCallingPackage(), b);
        DataProvider a = b.a(a());
        a(uri, b, a);
        Logger.i("DataContentProvider", "Delete %s from %s", uri, getCallingPackage());
        return a.a(uri, str, strArr);
    }

    @Override // android.content.ContentProvider
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String str, @Nullable String[] strArr) {
        b b = b(uri);
        b(uri, getCallingPackage(), b);
        DataProvider a = b.a(a());
        a(uri, b, a);
        Logger.i("DataContentProvider", "Update %s from %s", uri, getCallingPackage());
        return a.a(uri, contentValues, str, strArr);
    }

    @Override // android.content.ContentProvider
    @Nullable
    public Bundle call(@NonNull String str, @Nullable String str2, @Nullable Bundle bundle) {
        if (str.startsWith("content://")) {
            try {
                Uri parse = Uri.parse(str);
                String fragment = parse.getFragment();
                if (fragment == null || fragment.length() == 0) {
                    throw new IllegalArgumentException("can't found method : " + str);
                }
                b b = b(parse);
                b(parse, getCallingPackage(), b);
                DataProvider a = b.a(a());
                a(parse, b, a);
                Logger.i("DataContentProvider", "Call %s from %s, %s", str, getCallingPackage(), parse);
                return a.a(fragment, str2, bundle);
            } catch (Exception e) {
                throw new IllegalArgumentException("parse method uri failure: " + str, e);
            }
        } else {
            throw new IllegalArgumentException("illegal call method: " + str + ", method example: content://xxx.xxx/your/path#method_name");
        }
    }

    @NonNull
    private Context a() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("context is null");
    }

    private void a(@NonNull Uri uri, b bVar, DataProvider dataProvider) {
        if (dataProvider != null) {
            int a = a(bVar, dataProvider, uri, 2);
            if (a != 0) {
                throw a(a);
            }
            return;
        }
        throw a(BaseCode.NOT_FOUND_PROVIDER);
    }

    private int a(b bVar, DataProvider dataProvider, @NonNull Uri uri, int i) {
        int a = DataProviderManager.getInstance().a(a(), bVar, uri, i);
        return (a != 0 || !(dataProvider instanceof AccessFilter)) ? a : ((AccessFilter) dataProvider).onAcceptAccess(a(), uri, i);
    }

    private boolean a(@NonNull Uri uri) {
        return TextUtils.isEmpty(uri.getPath());
    }

    @NonNull
    private b b(@NonNull Uri uri) {
        b a = DataProviderManager.getInstance().a(uri);
        if (a != null) {
            return a;
        }
        throw new SecurityException("Permission Denial: " + getClass().getName() + " uri " + uri + " no provider deploy.");
    }

    private void a(Uri uri, String str, @NonNull b bVar) {
        Context a = a();
        if (!a(a, str)) {
            a(uri, a, bVar.a);
        }
    }

    private void b(Uri uri, String str, @NonNull b bVar) {
        Context a = a();
        if (!a(a, str)) {
            a(uri, a, bVar.b);
        }
    }

    private void a(Uri uri, Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            if (context.checkPermission(str, callingPid, callingUid) != 0) {
                throw new SecurityException("Permission Denial: " + getClass().getName() + " uri " + uri + " from pid=" + callingPid + ", uid=" + callingUid + " requires " + str);
            }
            return;
        }
        throw new SecurityException("Permission Denial: " + getClass().getName() + " uri " + uri + " permission is denial.");
    }

    private boolean a(Context context, String str) {
        return PrivilegedPackageManager.isPrivilegedPackage(context, str);
    }
}
