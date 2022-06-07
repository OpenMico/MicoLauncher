package com.xiaomi.micolauncher.common.media;

import android.graphics.Bitmap;
import java.util.Objects;

/* loaded from: classes3.dex */
public class MicoMediaEvent {
    private final CharSequence a;
    private final CharSequence b;
    private final String c;
    private final Bitmap d;
    private final String e;
    private final String f;

    public MicoMediaEvent(String str, CharSequence charSequence, CharSequence charSequence2, String str2, Bitmap bitmap, String str3) {
        this.a = charSequence;
        this.b = charSequence2;
        this.c = str2;
        this.d = bitmap;
        this.e = str;
        this.f = str3;
    }

    public CharSequence getTitle() {
        return this.a;
    }

    public CharSequence getArtist() {
        return this.b;
    }

    public String getCover() {
        return this.c;
    }

    public Bitmap getBitmap() {
        return this.d;
    }

    public String getMediaType() {
        return this.e;
    }

    public String getSource() {
        return this.f;
    }

    public String toString() {
        return "MicoMediaEvent{title=" + ((Object) this.a) + ", artist=" + ((Object) this.b) + ", cover='" + this.c + "', bitmap=" + this.d + ", mediaType='" + this.e + "', source='" + this.f + "'}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MicoMediaEvent micoMediaEvent = (MicoMediaEvent) obj;
        return Objects.equals(this.a, micoMediaEvent.a) && Objects.equals(this.b, micoMediaEvent.b) && Objects.equals(this.c, micoMediaEvent.c) && Objects.equals(this.e, micoMediaEvent.e) && Objects.equals(this.f, micoMediaEvent.f) && !bitmapChange(micoMediaEvent);
    }

    public boolean bitmapChange(MicoMediaEvent micoMediaEvent) {
        Bitmap bitmap;
        Bitmap bitmap2 = this.d;
        return bitmap2 == null && (bitmap = micoMediaEvent.d) != null && bitmap == null && bitmap2 != null;
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d, this.e, this.f);
    }
}
