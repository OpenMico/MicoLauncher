package com.xiaomi.micolauncher.skills.common;

import com.elvishew.xlog.Logger;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class DirectiveModelHelper {

    /* loaded from: classes3.dex */
    public static class ItemStream extends Directive.Stream {
    }

    /* loaded from: classes3.dex */
    public static class MediaItem extends Directive.Media {
    }

    /* loaded from: classes3.dex */
    public static class DirectiveElement {
        @SerializedName("items")
        public MediaItem[] items;
        @SerializedName("type")
        public String type;

        public String getFirstItemUrl() {
            if (!ContainerUtil.hasData(this.items) || this.items[0].stream == null) {
                return null;
            }
            return this.items[0].stream.url;
        }

        public String getFirstItemText() {
            if (ContainerUtil.hasData(this.items)) {
                return this.items[0].text;
            }
            return null;
        }
    }

    public static DirectiveElement[] fromJsonStr(String str) {
        try {
            return (DirectiveElement[]) Gsons.getGson().fromJson(str, (Class<Object>) DirectiveElement[].class);
        } catch (JsonSyntaxException e) {
            Logger logger = L.skill;
            logger.e("convert json failure " + str, e);
            return null;
        }
    }
}
