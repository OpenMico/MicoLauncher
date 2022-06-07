package com.xiaomi.micolauncher.module.recommend.holder;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.XMStringUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.common.widget.CenterAlignImageSpan;
import com.xiaomi.micolauncher.module.recommend.PageFactory;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class SkillPageVH implements PageFactory.PageVH {
    TextView a;
    TextView b;
    ImageView c;
    TextView d;

    @Override // com.xiaomi.micolauncher.module.recommend.PageFactory.PageVH
    public void setMainColor(int i) {
    }

    public SkillPageVH(View view, MainScreen.SkillPage skillPage) {
        this.a = (TextView) view.findViewById(R.id.subtitle1);
        this.b = (TextView) view.findViewById(R.id.subtitle2);
        this.c = (ImageView) view.findViewById(R.id.icon);
        this.d = (TextView) view.findViewById(R.id.title);
        if (TextUtils.isEmpty(skillPage.tag)) {
            this.d.setText(skillPage.title);
        } else {
            SpannableString spannableString = new SpannableString(String.format("%s   %s", skillPage.tag, skillPage.title));
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), R.drawable.icon_title_divider);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            spannableString.setSpan(new CenterAlignImageSpan(drawable), skillPage.tag.length() + 1, skillPage.tag.length() + 2, 17);
            this.d.setText(spannableString);
        }
        this.a.setText(skillPage.subTitle1);
        this.b.setText(skillPage.subTitle2);
        if (XMStringUtils.isUrl(skillPage.extend.skillIcon)) {
            Glide.with(view).load(skillPage.extend.skillIcon).into(this.c);
        }
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.recommend.holder.-$$Lambda$SkillPageVH$PnAq65-MAOEIujrzYWc0Kk6En3k
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillPageVH.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        this.a.setText("");
        this.b.setText("");
    }
}
