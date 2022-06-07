package com.xiaomi.micolauncher.module.recommend.holder;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.common.widget.CenterAlignImageSpan;

/* loaded from: classes3.dex */
public class MusicPageVH {
    TextView a;
    TextView b;

    public MusicPageVH(View view, MainScreen.MusicPage musicPage) {
        this.a = (TextView) view.findViewById(R.id.title);
        this.b = (TextView) view.findViewById(R.id.tips);
        if (musicPage.tips.size() > 0) {
            this.b.setText((CharSequence) musicPage.tips.get(0));
        }
        if (TextUtils.isEmpty(musicPage.tag)) {
            this.a.setText(musicPage.title);
            return;
        }
        SpannableString spannableString = new SpannableString(String.format("%s   %s", musicPage.tag, musicPage.title));
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), R.drawable.icon_title_divider);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableString.setSpan(new CenterAlignImageSpan(drawable), musicPage.tag.length() + 1, musicPage.tag.length() + 2, 17);
        this.a.setText(spannableString);
    }
}
