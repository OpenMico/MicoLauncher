package com.xiaomi.micolauncher.common.event;

import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.skills.ancientpoem.AncientPoemEntity;
import com.xiaomi.micolauncher.skills.joke.JokeItem;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class UiEvent {

    /* loaded from: classes3.dex */
    public static class AncientPoemUiEvent {
        public ArrayList<AncientPoemEntity> ancientPoems;
        public String dialogId;

        public AncientPoemUiEvent(String str, ArrayList<AncientPoemEntity> arrayList) {
            this.dialogId = str;
            this.ancientPoems = arrayList;
        }
    }

    /* loaded from: classes3.dex */
    public static class JokeUiEvent {
        public String dialogId;
        public ArrayList<JokeItem> jokeItems;

        public JokeUiEvent(String str, ArrayList<JokeItem> arrayList) {
            this.dialogId = str;
            this.jokeItems = arrayList;
        }
    }

    /* loaded from: classes3.dex */
    public static class WhiteNoiseEvent {
        private String a;

        public WhiteNoiseEvent(String str) {
            this.a = str;
        }

        public String getDialogId() {
            return this.a;
        }
    }

    /* loaded from: classes3.dex */
    public static class RichPictureEvent {
        public String dialogId;
        public Directive.RichMedia richMedia;

        public RichPictureEvent(String str, Directive.RichMedia richMedia) {
            this.dialogId = str;
            this.richMedia = richMedia;
        }
    }
}
