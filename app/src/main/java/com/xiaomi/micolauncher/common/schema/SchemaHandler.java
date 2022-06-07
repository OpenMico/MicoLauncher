package com.xiaomi.micolauncher.common.schema;

import android.content.Context;
import android.net.Uri;

/* loaded from: classes3.dex */
public interface SchemaHandler {
    public static final String SCHEME_MICO = "mico";

    boolean canHandle(Uri uri);

    boolean needLogin();

    void process(Context context, Uri uri, Object obj);
}
