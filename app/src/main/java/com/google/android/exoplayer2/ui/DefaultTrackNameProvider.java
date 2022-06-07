package com.google.android.exoplayer2.ui;

import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Locale;

/* loaded from: classes2.dex */
public class DefaultTrackNameProvider implements TrackNameProvider {
    private final Resources a;

    public DefaultTrackNameProvider(Resources resources) {
        this.a = (Resources) Assertions.checkNotNull(resources);
    }

    @Override // com.google.android.exoplayer2.ui.TrackNameProvider
    public String getTrackName(Format format) {
        String str;
        int h = h(format);
        if (h == 2) {
            str = a(g(format), a(format), b(format));
        } else if (h == 1) {
            str = a(d(format), c(format), b(format));
        } else {
            str = d(format);
        }
        return str.length() == 0 ? this.a.getString(R.string.exo_track_unknown) : str;
    }

    private String a(Format format) {
        int i = format.width;
        int i2 = format.height;
        return (i == -1 || i2 == -1) ? "" : this.a.getString(R.string.exo_track_resolution, Integer.valueOf(i), Integer.valueOf(i2));
    }

    private String b(Format format) {
        int i = format.bitrate;
        return i == -1 ? "" : this.a.getString(R.string.exo_track_bitrate, Float.valueOf(i / 1000000.0f));
    }

    private String c(Format format) {
        int i = format.channelCount;
        if (i == -1 || i < 1) {
            return "";
        }
        switch (i) {
            case 1:
                return this.a.getString(R.string.exo_track_mono);
            case 2:
                return this.a.getString(R.string.exo_track_stereo);
            case 3:
            case 4:
            case 5:
            default:
                return this.a.getString(R.string.exo_track_surround);
            case 6:
            case 7:
                return this.a.getString(R.string.exo_track_surround_5_point_1);
            case 8:
                return this.a.getString(R.string.exo_track_surround_7_point_1);
        }
    }

    private String d(Format format) {
        String a = a(f(format), g(format));
        return TextUtils.isEmpty(a) ? e(format) : a;
    }

    private String e(Format format) {
        return TextUtils.isEmpty(format.label) ? "" : format.label;
    }

    private String f(Format format) {
        String str = format.language;
        if (TextUtils.isEmpty(str) || C.LANGUAGE_UNDETERMINED.equals(str)) {
            return "";
        }
        return (Util.SDK_INT >= 21 ? Locale.forLanguageTag(str) : new Locale(str)).getDisplayName();
    }

    private String g(Format format) {
        String str = "";
        if ((format.roleFlags & 2) != 0) {
            str = this.a.getString(R.string.exo_track_role_alternate);
        }
        if ((format.roleFlags & 4) != 0) {
            str = a(str, this.a.getString(R.string.exo_track_role_supplementary));
        }
        if ((format.roleFlags & 8) != 0) {
            str = a(str, this.a.getString(R.string.exo_track_role_commentary));
        }
        return (format.roleFlags & 1088) != 0 ? a(str, this.a.getString(R.string.exo_track_role_closed_captions)) : str;
    }

    private String a(String... strArr) {
        String str = "";
        for (String str2 : strArr) {
            if (str2.length() > 0) {
                str = TextUtils.isEmpty(str) ? str2 : this.a.getString(R.string.exo_item_list, str, str2);
            }
        }
        return str;
    }

    private static int h(Format format) {
        int trackType = MimeTypes.getTrackType(format.sampleMimeType);
        if (trackType != -1) {
            return trackType;
        }
        if (MimeTypes.getVideoMediaMimeType(format.codecs) != null) {
            return 2;
        }
        if (MimeTypes.getAudioMediaMimeType(format.codecs) != null) {
            return 1;
        }
        if (format.width == -1 && format.height == -1) {
            return (format.channelCount == -1 && format.sampleRate == -1) ? -1 : 1;
        }
        return 2;
    }
}
