package com.xiaomi.micolauncher.common.player.opengl;

import androidx.annotation.NonNull;
import com.umeng.analytics.pro.ai;

/* loaded from: classes3.dex */
public class BackBufferParameters extends TextureParameters {
    private String a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(int i, int i2) {
        return false;
    }

    public BackBufferParameters() {
        super(9728, 9728, 33071, 33071);
    }

    public void setPreset(String str) {
        this.a = str;
    }

    @Override // com.xiaomi.micolauncher.common.player.opengl.TextureParameters
    @NonNull
    public String toString() {
        String textureParameters = super.toString();
        if (this.a == null) {
            return textureParameters;
        }
        if (textureParameters.length() < 1) {
            textureParameters = "///";
        }
        return textureParameters + "p:" + this.a + ";";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        a(9728, 9728, 33071, 33071);
        this.a = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.player.opengl.TextureParameters
    public void parseParameter(String str, String str2) {
        if (ai.av.equals(str)) {
            this.a = str2;
        } else {
            super.parseParameter(str, str2);
        }
    }
}
