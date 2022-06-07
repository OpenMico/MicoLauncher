package com.blankj.utilcode.util;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.core.content.FileProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public final class IntentUtils {
    private IntentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isIntentAvailable(Intent intent) {
        return Utils.getApp().getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static Intent getInstallAppIntent(String str) {
        return getInstallAppIntent(b.f(str));
    }

    public static Intent getInstallAppIntent(File file) {
        Uri uri;
        if (!b.b(file)) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(file);
        } else {
            uri = FileProvider.getUriForFile(Utils.getApp(), Utils.getApp().getPackageName() + ".utilcode.fileprovider", file);
        }
        return getInstallAppIntent(uri);
    }

    public static Intent getInstallAppIntent(Uri uri) {
        if (uri == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
        }
        return intent.addFlags(268435456);
    }

    public static Intent getUninstallAppIntent(String str) {
        Intent intent = new Intent("android.intent.action.DELETE");
        intent.setData(Uri.parse("package:" + str));
        return intent.addFlags(268435456);
    }

    public static Intent getLaunchAppIntent(String str) {
        String a = b.a(str);
        if (b.o(a)) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setClassName(str, a);
        return intent.addFlags(268435456);
    }

    public static Intent getLaunchAppDetailsSettingsIntent(String str) {
        return getLaunchAppDetailsSettingsIntent(str, false);
    }

    public static Intent getLaunchAppDetailsSettingsIntent(String str, boolean z) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + str));
        return a(intent, z);
    }

    public static Intent getShareTextIntent(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", str);
        return a(Intent.createChooser(intent, ""), true);
    }

    public static Intent getShareImageIntent(String str) {
        return getShareTextImageIntent("", str);
    }

    public static Intent getShareImageIntent(File file) {
        return getShareTextImageIntent("", file);
    }

    public static Intent getShareImageIntent(Uri uri) {
        return getShareTextImageIntent("", uri);
    }

    public static Intent getShareTextImageIntent(@Nullable String str, String str2) {
        return getShareTextImageIntent(str, b.f(str2));
    }

    public static Intent getShareTextImageIntent(@Nullable String str, File file) {
        return getShareTextImageIntent(str, b.i(file));
    }

    public static Intent getShareTextImageIntent(@Nullable String str, Uri uri) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setType("image/*");
        return a(Intent.createChooser(intent, ""), true);
    }

    public static Intent getShareImageIntent(LinkedList<String> linkedList) {
        return getShareTextImageIntent("", linkedList);
    }

    public static Intent getShareImageIntent(List<File> list) {
        return getShareTextImageIntent("", list);
    }

    public static Intent getShareImageIntent(ArrayList<Uri> arrayList) {
        return getShareTextImageIntent("", arrayList);
    }

    public static Intent getShareTextImageIntent(@Nullable String str, LinkedList<String> linkedList) {
        ArrayList arrayList = new ArrayList();
        if (linkedList != null) {
            Iterator<String> it = linkedList.iterator();
            while (it.hasNext()) {
                File f = b.f(it.next());
                if (f != null) {
                    arrayList.add(f);
                }
            }
        }
        return getShareTextImageIntent(str, (List<File>) arrayList);
    }

    public static Intent getShareTextImageIntent(@Nullable String str, List<File> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (File file : list) {
                Uri i = b.i(file);
                if (i != null) {
                    arrayList.add(i);
                }
            }
        }
        return getShareTextImageIntent(str, (ArrayList<Uri>) arrayList);
    }

    public static Intent getShareTextImageIntent(@Nullable String str, ArrayList<Uri> arrayList) {
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.putExtra("android.intent.extra.TEXT", str);
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        intent.setType("image/*");
        return a(Intent.createChooser(intent, ""), true);
    }

    public static Intent getComponentIntent(String str, String str2) {
        return getComponentIntent(str, str2, null, false);
    }

    public static Intent getComponentIntent(String str, String str2, boolean z) {
        return getComponentIntent(str, str2, null, z);
    }

    public static Intent getComponentIntent(String str, String str2, Bundle bundle) {
        return getComponentIntent(str, str2, bundle, false);
    }

    public static Intent getComponentIntent(String str, String str2, Bundle bundle, boolean z) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        return a(intent, z);
    }

    public static Intent getShutdownIntent() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= 26) {
            intent = new Intent("com.android.internal.intent.action.REQUEST_SHUTDOWN");
        } else {
            intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        }
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        return intent.addFlags(268435456);
    }

    public static Intent getDialIntent(@NonNull String str) {
        if (str != null) {
            return a(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + Uri.encode(str))), true);
        }
        throw new NullPointerException("Argument 'phoneNumber' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresPermission("android.permission.CALL_PHONE")
    public static Intent getCallIntent(@NonNull String str) {
        if (str != null) {
            return a(new Intent("android.intent.action.CALL", Uri.parse("tel:" + Uri.encode(str))), true);
        }
        throw new NullPointerException("Argument 'phoneNumber' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Intent getSendSmsIntent(@NonNull String str, String str2) {
        if (str != null) {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + Uri.encode(str)));
            intent.putExtra("sms_body", str2);
            return a(intent, true);
        }
        throw new NullPointerException("Argument 'phoneNumber' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Intent getCaptureIntent(Uri uri) {
        return getCaptureIntent(uri, false);
    }

    public static Intent getCaptureIntent(Uri uri, boolean z) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        intent.addFlags(1);
        return a(intent, z);
    }

    private static Intent a(Intent intent, boolean z) {
        return z ? intent.addFlags(268435456) : intent;
    }
}
