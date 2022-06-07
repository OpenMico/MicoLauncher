package com.efs.sdk.base.http;

import androidx.annotation.NonNull;
import java.io.File;
import java.util.Map;

/* loaded from: classes.dex */
public interface IHttpUtil {
    @NonNull
    HttpResponse get(String str, Map<String, String> map);

    @NonNull
    HttpResponse post(String str, Map<String, String> map, File file);

    @NonNull
    HttpResponse post(String str, Map<String, String> map, byte[] bArr);

    @NonNull
    HttpResponse postAsFile(String str, Map<String, String> map, byte[] bArr);
}
