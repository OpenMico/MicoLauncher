package okhttp3;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Cookie;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaNetCookieJar.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokhttp3/JavaNetCookieJar;", "Lokhttp3/CookieJar;", "cookieHandler", "Ljava/net/CookieHandler;", "(Ljava/net/CookieHandler;)V", "decodeHeaderAsJavaNetCookies", "", "Lokhttp3/Cookie;", "url", "Lokhttp3/HttpUrl;", "header", "", "loadForRequest", "saveFromResponse", "", "cookies", "okhttp-urlconnection"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class JavaNetCookieJar implements CookieJar {
    private final CookieHandler cookieHandler;

    public JavaNetCookieJar(@NotNull CookieHandler cookieHandler) {
        Intrinsics.checkParameterIsNotNull(cookieHandler, "cookieHandler");
        this.cookieHandler = cookieHandler;
    }

    @Override // okhttp3.CookieJar
    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        Intrinsics.checkParameterIsNotNull(cookies, "cookies");
        ArrayList arrayList = new ArrayList();
        for (Cookie cookie : cookies) {
            arrayList.add(Internal.cookieToString(cookie, true));
        }
        try {
            this.cookieHandler.put(url.uri(), MapsKt.mapOf(TuplesKt.to("Set-Cookie", arrayList)));
        } catch (IOException e) {
            Platform platform = Platform.Companion.get();
            StringBuilder sb = new StringBuilder();
            sb.append("Saving cookies failed for ");
            HttpUrl resolve = url.resolve("/...");
            if (resolve == null) {
                Intrinsics.throwNpe();
            }
            sb.append(resolve);
            platform.log(sb.toString(), 5, e);
        }
    }

    @Override // okhttp3.CookieJar
    @NotNull
    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        try {
            Map<String, List<String>> cookieHeaders = this.cookieHandler.get(url.uri(), MapsKt.emptyMap());
            ArrayList arrayList = null;
            Intrinsics.checkExpressionValueIsNotNull(cookieHeaders, "cookieHeaders");
            for (Map.Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (StringsKt.equals("Cookie", key, true) || StringsKt.equals("Cookie2", key, true)) {
                    Intrinsics.checkExpressionValueIsNotNull(value, "value");
                    if (!value.isEmpty()) {
                        for (String header : value) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            Intrinsics.checkExpressionValueIsNotNull(header, "header");
                            arrayList.addAll(decodeHeaderAsJavaNetCookies(url, header));
                        }
                    }
                }
            }
            if (arrayList == null) {
                return CollectionsKt.emptyList();
            }
            List<Cookie> unmodifiableList = Collections.unmodifiableList(arrayList);
            Intrinsics.checkExpressionValueIsNotNull(unmodifiableList, "Collections.unmodifiableList(cookies)");
            return unmodifiableList;
        } catch (IOException e) {
            Platform platform = Platform.Companion.get();
            StringBuilder sb = new StringBuilder();
            sb.append("Loading cookies failed for ");
            HttpUrl resolve = url.resolve("/...");
            if (resolve == null) {
                Intrinsics.throwNpe();
            }
            sb.append(resolve);
            platform.log(sb.toString(), 5, e);
            return CollectionsKt.emptyList();
        }
    }

    private final List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl httpUrl, String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        int i = 0;
        while (i < length) {
            int delimiterOffset = Util.delimiterOffset(str, ";,", i, length);
            int delimiterOffset2 = Util.delimiterOffset(str, '=', i, delimiterOffset);
            String trimSubstring = Util.trimSubstring(str, i, delimiterOffset2);
            if (StringsKt.startsWith$default(trimSubstring, "$", false, 2, (Object) null)) {
                i = delimiterOffset + 1;
            } else {
                String trimSubstring2 = delimiterOffset2 < delimiterOffset ? Util.trimSubstring(str, delimiterOffset2 + 1, delimiterOffset) : "";
                if (StringsKt.startsWith$default(trimSubstring2, "\"", false, 2, (Object) null) && StringsKt.endsWith$default(trimSubstring2, "\"", false, 2, (Object) null)) {
                    int length2 = trimSubstring2.length() - 1;
                    if (trimSubstring2 != null) {
                        trimSubstring2 = trimSubstring2.substring(1, length2);
                        Intrinsics.checkExpressionValueIsNotNull(trimSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                }
                arrayList.add(new Cookie.Builder().name(trimSubstring).value(trimSubstring2).domain(httpUrl.host()).build());
                i = delimiterOffset + 1;
            }
        }
        return arrayList;
    }
}
