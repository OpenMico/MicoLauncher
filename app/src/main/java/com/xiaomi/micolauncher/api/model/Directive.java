package com.xiaomi.micolauncher.api.model;

import android.text.TextUtils;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.micolauncher.common.model.ImageSource;
import com.xiaomi.micolauncher.common.speech.DomainHelper;
import com.xiaomi.micolauncher.skills.common.DirectiveModelHelper;
import com.xiaomi.micolauncher.skills.video.model.StreamInfo;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class Directive {
    public static final String DIRECTIVE_MEDIA = "media";
    public static final String DIRECTIVE_TYPE_AUDIO = "audio";
    public static final String DIRECTIVE_TYPE_MESSAGE = "message";
    public static final String DIRECTIVE_TYPE_RESOURCE = "resource";
    public static final String DIRECTIVE_TYPE_RICH_MEDIA = "richmedia";
    public static final String DIRECTIVE_TYPE_TEXT = "text";
    public static final String DIRECTIVE_TYPE_VIDEO = "video";
    public List<DirectiveItem> items;
    public String type;

    /* loaded from: classes3.dex */
    public static class Audio extends DirectiveItem {
        public String cp;
        public AudioExtend extend;
        public String id;
        public AudioImage image;
        public String modelid;
        public String rawCp;
        public String refer;
        public Stream stream;
        public String text;
        public String title;
    }

    /* loaded from: classes3.dex */
    public static class AudioExtend implements Serializable {
        private static final long serialVersionUID = -7049114652784023305L;
        public String time;
    }

    /* loaded from: classes3.dex */
    public static class AudioImage implements Serializable {
        @SerializedName("url")
        public String url;
    }

    @JsonAdapter(DomainHelper.DirectiveItemJsonAdapter.class)
    /* loaded from: classes3.dex */
    public static class DirectiveItem implements Serializable {
        private static final long serialVersionUID = -759934638389319083L;
        public boolean cached;
        @SerializedName("type")
        public String type;
    }

    /* loaded from: classes3.dex */
    public static class Media extends DirectiveItem {
        @SerializedName(TraceConstants.Result.CP)
        public String cp;
        @SerializedName("isVisible")
        public boolean isVisible;
        @SerializedName(AivsConfig.Tts.AUDIO_TYPE_STREAM)
        public DirectiveModelHelper.ItemStream stream;
        @SerializedName("text")
        public String text;
        @SerializedName("title")
        public String title;
    }

    /* loaded from: classes3.dex */
    public static class Message extends DirectiveItem {
        public boolean isVisible;
        public String text;
        public String url;
    }

    /* loaded from: classes3.dex */
    public static class ResourceExtend implements Serializable {
        private static final long serialVersionUID = -5056643750811029307L;
        public String albumId;
        public String albumName;
        public String cpAlbumId;
        public int duration;
        public int episodes;
        public int episodesNum;
        public int extracEpisode;
        public boolean featured;
        @SerializedName(alternate = {"global_id"}, value = "globalId")
        public String globalId;
        public boolean is_vip;
        @SerializedName("mv_id")
        public String mvId;
        public String orderType;
        public String originSong;
        public int position;
        public double rank;
        public String resourceType;
        public String tags;
    }

    /* loaded from: classes3.dex */
    public static class RichMedia extends DirectiveItem {
        public ImageSource image;
        public StreamInfo stream;
        public String text;
    }

    /* loaded from: classes3.dex */
    public static class Stream implements Serializable {
        private boolean authen;
        @SerializedName("authentication_method")
        public String authentication_method;
        @SerializedName("offset_in_milliseconds")
        public long offsetInMillis;
        @SerializedName("token")
        public String token;
        @SerializedName("url")
        public String url;
    }

    /* loaded from: classes3.dex */
    public static class Text extends DirectiveItem {
        public String album;
        public String artist;
        public String cover;
        public String cp;
        public TextExtend extend;
        public String id;
        public boolean isVisible;
        public String refer;
        public String text;
        public String title;
    }

    /* loaded from: classes3.dex */
    public static class TextExtend implements Serializable {
        private static final long serialVersionUID = -7825015258980768485L;
        public String time;
    }

    /* loaded from: classes3.dex */
    public static class Video extends DirectiveItem {
        public Stream stream;
    }

    /* loaded from: classes3.dex */
    public static class PlayerSource extends DirectiveItem {
        public String content;

        public PlayerSource(String str, String str2) {
            this.type = str;
            this.content = str2;
        }
    }

    /* loaded from: classes3.dex */
    public static class Resource extends DirectiveItem {
        public String album;
        public String artist;
        public String cover;
        public String cp;
        public ResourceExtend extend;
        public String id;
        public boolean isVisible;
        public String refer;
        public String title;

        public boolean isLegal() {
            return !TextUtils.isEmpty(this.cp) && !TextUtils.isEmpty(this.id);
        }
    }
}
