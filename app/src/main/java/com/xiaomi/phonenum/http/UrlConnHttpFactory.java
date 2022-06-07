package com.xiaomi.phonenum.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.SystemClock;
import androidx.annotation.RequiresApi;
import com.xiaomi.phonenum.bean.HttpError;
import com.xiaomi.phonenum.http.Response;
import com.xiaomi.phonenum.phone.PhoneInfoManager;
import com.xiaomi.phonenum.phone.PhoneUtil;
import com.xiaomi.phonenum.utils.Logger;
import com.xiaomi.phonenum.utils.LoggerManager;
import com.xiaomi.phonenum.utils.MapUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class UrlConnHttpFactory extends HttpFactory {
    private static CookieManager a = new CookieManager();
    private PhoneUtil b;
    private Logger c = LoggerManager.getLogger();
    private Context d;

    static {
        a.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    }

    public UrlConnHttpFactory(Context context) {
        this.d = context;
        this.b = PhoneInfoManager.getDefaultPhoneUtil(context);
        CookieHandler.setDefault(a);
    }

    @Override // com.xiaomi.phonenum.http.HttpFactory
    public HttpClient createHttpClient(HttpClientConfig httpClientConfig) {
        return new a(httpClientConfig);
    }

    /* loaded from: classes4.dex */
    public class a implements HttpClient {
        private HttpClientConfig b;

        private a(HttpClientConfig httpClientConfig) {
            UrlConnHttpFactory.this = r1;
            this.b = httpClientConfig;
        }

        @Override // com.xiaomi.phonenum.http.HttpClient
        public Response excute(Request request) throws IOException {
            Network network;
            long uptimeMillis = SystemClock.uptimeMillis();
            if (this.b.netWorkSubId >= 0) {
                if (!UrlConnHttpFactory.this.b.getDataEnabledForSubId(this.b.netWorkSubId)) {
                    return HttpError.DATA_NOT_ENABLED.result();
                }
                if (!UrlConnHttpFactory.this.b.isNetWorkTypeMobile()) {
                    if (!UrlConnHttpFactory.this.b.checkPermission("android.permission.CHANGE_NETWORK_STATE")) {
                        return HttpError.NO_CHANGE_NETWORK_STATE_PERMISSION.result();
                    }
                    network = a();
                    if (network == null) {
                        return HttpError.CELLULAR_NETWORK_NOT_AVAILABLE.result();
                    }
                    return a(request, a(request.url, network)).time(SystemClock.uptimeMillis() - uptimeMillis).build();
                }
            }
            network = null;
            return a(request, a(request.url, network)).time(SystemClock.uptimeMillis() - uptimeMillis).build();
        }

        private Response.Builder a(Request request, HttpURLConnection httpURLConnection) throws IOException {
            Throwable th;
            try {
                httpURLConnection.setConnectTimeout((int) this.b.connectTimeoutMs);
                httpURLConnection.setReadTimeout((int) this.b.readTimeoutMs);
                if (request.formBody != null) {
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                } else {
                    httpURLConnection.setRequestMethod("GET");
                }
                httpURLConnection.setInstanceFollowRedirects(request.followRedirects);
                if (request.headers != null) {
                    for (Map.Entry<String, String> entry : request.headers.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.connect();
                if (request.formBody != null) {
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(MapUtil.joinToQuery(request.formBody));
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                }
                int responseCode = httpURLConnection.getResponseCode();
                Response.Builder headers = new Response.Builder().code(responseCode).location(httpURLConnection.getHeaderField("Location")).setCookie(httpURLConnection.getHeaderField("Set-Cookie")).headers(httpURLConnection.getHeaderFields());
                if (responseCode != 200) {
                    return headers;
                }
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 1024);
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                sb.append(readLine);
                            } else {
                                Response.Builder body = headers.body(sb.toString());
                                bufferedReader.close();
                                return body;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } finally {
                httpURLConnection.disconnect();
            }
        }

        private HttpURLConnection a(String str, Network network) throws IOException {
            if (network == null || Build.VERSION.SDK_INT < 21) {
                return (HttpURLConnection) new URL(str).openConnection();
            }
            return (HttpURLConnection) network.openConnection(new URL(str));
        }

        private Network a() {
            try {
                if (Build.VERSION.SDK_INT >= 21) {
                    return a(UrlConnHttpFactory.this.d, this.b.waitCellularTimeoutMs);
                }
                return null;
            } catch (InterruptedException e) {
                UrlConnHttpFactory.this.c.e("HttpUrlConnClient", "waitForCellular", e);
                return null;
            } catch (TimeoutException e2) {
                Logger logger = UrlConnHttpFactory.this.c;
                logger.e("HttpUrlConnClient", "waitForCellular Timeout " + this.b.waitCellularTimeoutMs, e2);
                return null;
            }
        }

        @RequiresApi(api = 21)
        private Network a(Context context, long j) throws InterruptedException, TimeoutException {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            final AtomicReference atomicReference = new AtomicReference(null);
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            connectivityManager.requestNetwork(new NetworkRequest.Builder().addCapability(12).addTransportType(0).build(), new ConnectivityManager.NetworkCallback() { // from class: com.xiaomi.phonenum.http.UrlConnHttpFactory.a.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                    if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        atomicReference.set(network);
                        countDownLatch.countDown();
                    }
                }
            });
            if (countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                return (Network) atomicReference.get();
            }
            throw new TimeoutException();
        }
    }
}
