package com.xiaomi.micolauncher.common.speech;

import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.api.model.Directive;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class DomainHelper {
    public static List<Directive> parseDirective(String str) {
        return (List) Gsons.getGson().fromJson(str, new TypeToken<List<Directive>>() { // from class: com.xiaomi.micolauncher.common.speech.DomainHelper.1
        }.getType());
    }

    /* loaded from: classes3.dex */
    public class DirectiveItemJsonAdapter extends TypeAdapter<Directive.DirectiveItem> {
        public void write(JsonWriter jsonWriter, Directive.DirectiveItem directiveItem) throws IOException {
        }

        public DirectiveItemJsonAdapter() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        public Directive.DirectiveItem read(JsonReader jsonReader) throws IOException {
            char c;
            JsonElement parse = Streams.parse(jsonReader);
            String asString = parse.getAsJsonObject().get("type").getAsString();
            switch (asString.hashCode()) {
                case -830132088:
                    if (asString.equals(Directive.DIRECTIVE_TYPE_RICH_MEDIA)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -341064690:
                    if (asString.equals(Directive.DIRECTIVE_TYPE_RESOURCE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3556653:
                    if (asString.equals("text")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 93166550:
                    if (asString.equals("audio")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 112202875:
                    if (asString.equals("video")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 954925063:
                    if (asString.equals("message")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return (Directive.DirectiveItem) Gsons.getGson().fromJson(parse, (Class<Object>) Directive.Resource.class);
                case 1:
                    return (Directive.DirectiveItem) Gsons.getGson().fromJson(parse, (Class<Object>) Directive.Audio.class);
                case 2:
                    return (Directive.DirectiveItem) Gsons.getGson().fromJson(parse, (Class<Object>) Directive.Text.class);
                case 3:
                    return (Directive.DirectiveItem) Gsons.getGson().fromJson(parse, (Class<Object>) Directive.Message.class);
                case 4:
                    return (Directive.DirectiveItem) Gsons.getGson().fromJson(parse, (Class<Object>) Directive.RichMedia.class);
                case 5:
                    return (Directive.DirectiveItem) Gsons.getGson().fromJson(parse, (Class<Object>) Directive.Video.class);
                default:
                    return null;
            }
        }
    }
}
