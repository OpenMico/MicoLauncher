package com.xiaomi.micolauncher.skills.music.controller;

import android.text.TextUtils;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PlaylistHelper {
    public static List<Directive.DirectiveItem> extractPlayList(Directive directive) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(directive);
        return extractPlayList(arrayList);
    }

    public static List<Directive.DirectiveItem> extractPlayList(List<Directive> list) {
        ArrayList arrayList = new ArrayList();
        for (Directive directive : list) {
            if ("media".equals(directive.type)) {
                for (Directive.DirectiveItem directiveItem : directive.items) {
                    if (directiveItem instanceof Directive.Resource) {
                        Directive.Resource resource = (Directive.Resource) directiveItem;
                        if (resource.isLegal() && resource.extend != null) {
                            arrayList.add(directiveItem);
                        }
                    } else if (directiveItem instanceof Directive.Audio) {
                        arrayList.add(directiveItem);
                    } else if (!(directiveItem instanceof Directive.Text)) {
                        boolean z = directiveItem instanceof Directive.Message;
                    } else if (!TextUtils.isEmpty(((Directive.Text) directiveItem).text)) {
                        arrayList.add(directiveItem);
                    }
                }
            }
        }
        return arrayList;
    }

    public static List<Directive.DirectiveItem> extractPersonalizePlayList(List<Directive> list) {
        ArrayList arrayList = new ArrayList();
        for (Directive directive : list) {
            if ("media".equals(directive.type)) {
                for (Directive.DirectiveItem directiveItem : directive.items) {
                    if ((directiveItem instanceof Directive.Resource) && ((Directive.Resource) directiveItem).extend != null) {
                        arrayList.add(directiveItem);
                    }
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<VideoItem> extractMvPlayList(List<Directive> list) {
        Directive.Resource resource;
        Directive.ResourceExtend resourceExtend;
        ArrayList<VideoItem> arrayList = new ArrayList<>();
        for (Directive directive : list) {
            if ("media".equals(directive.type)) {
                for (int i = 0; i < directive.items.size(); i++) {
                    Directive.DirectiveItem directiveItem = directive.items.get(i);
                    if ((directiveItem instanceof Directive.Resource) && (resourceExtend = (resource = (Directive.Resource) directiveItem).extend) != null && !TextUtils.isEmpty(resourceExtend.mvId)) {
                        arrayList.add(new VideoItem(i + 1, resource.cp, resourceExtend.mvId, TextUtils.isEmpty(resourceExtend.originSong) ? resource.title : resourceExtend.originSong, resource.cover));
                    }
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<VideoItem> extractVideoPlayList(List<Directive> list) {
        ArrayList<VideoItem> arrayList = new ArrayList<>();
        for (Directive directive : list) {
            if ("media".equals(directive.type)) {
                for (int i = 0; i < directive.items.size(); i++) {
                    Directive.DirectiveItem directiveItem = directive.items.get(i);
                    if (directiveItem instanceof Directive.Resource) {
                        Directive.Resource resource = (Directive.Resource) directiveItem;
                        arrayList.add(new VideoItem(i + 1, resource.cp, resource.id, resource.title, resource.cover));
                    }
                }
            }
        }
        return arrayList;
    }
}
