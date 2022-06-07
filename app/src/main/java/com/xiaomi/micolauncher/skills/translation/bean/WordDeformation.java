package com.xiaomi.micolauncher.skills.translation.bean;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.api.FullScreenTemplate;
import com.xiaomi.micolauncher.R;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class WordDeformation implements Serializable {
    @SerializedName("part_name")
    private String partName;
    @SerializedName("word")
    private List<String> words;

    public WordDeformation(FullScreenTemplate.WordDeformation wordDeformation) {
        this.partName = wordDeformation.getPartName().isPresent() ? wordDeformation.getPartName().get() : "";
        if (wordDeformation.getWord().isPresent()) {
            this.words = wordDeformation.getWord().get();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public String getPartName(Context context) {
        char c;
        String str = this.partName;
        switch (str.hashCode()) {
            case 28695026:
                if (str.equals("word_adj")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 28695038:
                if (str.equals("word_adv")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 28699345:
                if (str.equals("word_est")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 28703021:
                if (str.equals("word_ing")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 889616193:
                if (str.equals("word_conn")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 889645975:
                if (str.equals("word_done")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 889944111:
                if (str.equals("word_noun")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 889990183:
                if (str.equals("word_past")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 890006082:
                if (str.equals("word_prep")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 890172724:
                if (str.equals("word_verb")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1524946434:
                if (str.equals("word_er")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1524946769:
                if (str.equals("word_pl")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1823784946:
                if (str.equals("word_third")) {
                    c = 0;
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
                return context.getString(R.string.word_third);
            case 1:
                return context.getString(R.string.word_past);
            case 2:
                return context.getString(R.string.word_adj);
            case 3:
                return context.getString(R.string.word_adv);
            case 4:
                return context.getString(R.string.word_conn);
            case 5:
                return context.getString(R.string.word_done);
            case 6:
                return context.getString(R.string.word_er);
            case 7:
                return context.getString(R.string.word_est);
            case '\b':
                return context.getString(R.string.word_ing);
            case '\t':
                return context.getString(R.string.word_noun);
            case '\n':
                return context.getString(R.string.word_pl);
            case 11:
                return context.getString(R.string.word_prep);
            case '\f':
                return context.getString(R.string.word_verb);
            default:
                return "";
        }
    }

    public boolean isNoun() {
        return "word_pl".equals(this.partName);
    }

    public boolean isAdj() {
        return "word_er".equals(this.partName) || "word_est".equals(this.partName);
    }

    public String getSpeechPart(Context context) {
        if ("word_pl".equals(this.partName)) {
            return context.getString(R.string.word_noun);
        }
        if ("word_er".equals(this.partName) || "word_est".equals(this.partName)) {
            return context.getString(R.string.word_adj);
        }
        return context.getString(R.string.word_verb);
    }

    public List<String> getWords() {
        return this.words;
    }
}
