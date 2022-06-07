package com.xiaomi.push;

import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes4.dex */
public class fj {
    private XmlPullParser a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public fj() {
        try {
            this.a = XmlPullParserFactory.newInstance().newPullParser();
            this.a.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        } catch (XmlPullParserException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ge a(byte[] bArr, fn fnVar) {
        this.a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
        this.a.next();
        int eventType = this.a.getEventType();
        String name = this.a.getName();
        if (eventType != 2) {
            return null;
        }
        if (name.equals("message")) {
            return gm.a(this.a);
        }
        if (name.equals("iq")) {
            return gm.a(this.a, fnVar);
        }
        if (name.equals("presence")) {
            return gm.m945a(this.a);
        }
        if (this.a.getName().equals(AivsConfig.Tts.AUDIO_TYPE_STREAM)) {
            return null;
        }
        if (this.a.getName().equals("error")) {
            throw new fy(gm.m946a(this.a));
        } else if (this.a.getName().equals("warning")) {
            this.a.next();
            this.a.getName().equals("multi-login");
            return null;
        } else {
            this.a.getName().equals(SchemaActivity.VALUE_REGISTER_BIND);
            return null;
        }
    }
}
