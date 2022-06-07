package com.xiaomi.accountsdk.request;

import android.util.Base64;
import com.xiaomi.accountsdk.utils.IOUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import org.fourthline.cling.support.model.dlna.DLNAProfiles;

/* loaded from: classes2.dex */
public class UploadFileRequest {
    private static final String CRLF = "\r\n";

    public static String execute(String str, InputStream inputStream, String str2, String str3) throws InvalidResponseException, IOException {
        Throwable th;
        HttpURLConnection httpURLConnection;
        DataOutputStream dataOutputStream;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                byte[] bArr = new byte[16];
                new Random().nextBytes(bArr);
                String encodeToString = Base64.encodeToString(bArr, 2);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + encodeToString);
                httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                try {
                    dataOutputStream.writeBytes("--" + encodeToString + "\r\n");
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + str2 + "\"; filename=\"" + str3 + "\"\r\n");
                    StringBuilder sb = new StringBuilder();
                    sb.append("Content-Type: ");
                    sb.append(getMIMEType(str3));
                    sb.append("\r\n");
                    dataOutputStream.writeBytes(sb.toString());
                    dataOutputStream.writeBytes("\r\n");
                    byte[] bArr2 = new byte[8192];
                    while (true) {
                        int read = inputStream.read(bArr2);
                        if (read == -1) {
                            break;
                        }
                        dataOutputStream.write(bArr2, 0, read);
                    }
                    dataOutputStream.flush();
                    dataOutputStream.writeBytes("\r\n");
                    dataOutputStream.flush();
                    dataOutputStream.writeBytes("--" + encodeToString + "--\r\n");
                    dataOutputStream.flush();
                    inputStream.close();
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200) {
                        StringBuilder sb2 = new StringBuilder();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 1024);
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb2.append(readLine);
                        }
                        IOUtils.closeQuietly(bufferedReader);
                        String sb3 = sb2.toString();
                        try {
                            dataOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return sb3;
                    }
                    throw new InvalidResponseException(responseCode, "failed to upload file");
                } catch (Throwable th2) {
                    th = th2;
                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            httpURLConnection = null;
            dataOutputStream = null;
        }
    }

    private static String getMIMEType(String str) {
        return (str.endsWith("png") || str.endsWith("PNG")) ? DLNAProfiles.DLNAMimeTypes.MIME_IMAGE_PNG : "image/jpg";
    }
}
