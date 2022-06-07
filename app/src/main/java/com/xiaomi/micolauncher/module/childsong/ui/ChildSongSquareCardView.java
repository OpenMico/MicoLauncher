package com.xiaomi.micolauncher.module.childsong.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.music.base.BaseSquareCardView;
import com.xiaomi.micolauncher.module.music.bean.TitleType;

/* loaded from: classes3.dex */
public class ChildSongSquareCardView extends BaseSquareCardView {
    ImageView a;
    TextView b;
    private String c;
    private float d;
    private int e;
    private Context f;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setTitleType(TitleType titleType) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void showTagView() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void showTimeView() {
    }

    public ChildSongSquareCardView(Context context) {
        this(context, null);
    }

    public ChildSongSquareCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSongSquareCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f = context;
        a();
    }

    private void a() {
        View.inflate(this.f, R.layout.view_station_square_card, this);
        this.a = (ImageView) findViewById(R.id.image_iv);
        this.b = (TextView) findViewById(R.id.title_tv);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void refreshPage(Object obj) {
        if (obj != null && (obj instanceof PatchWall.Item)) {
            final PatchWall.Item item = (PatchWall.Item) obj;
            GlideUtils.bindImageViewWithDefaultAndRoundCorners(this.f, item.images.poster.url, this.a, this.e, (int) this.d);
            this.b.setText(item.title);
            setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.childsong.ui.-$$Lambda$ChildSongSquareCardView$sO4hvPoU_Y2KqwsU-Kf8UQazGqU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChildSongSquareCardView.this.a(item, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(PatchWall.Item item, View view) {
        if (!TextUtils.isEmpty(item.target)) {
            SchemaManager.handleSchema(this.f, item.target);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setTitleName(String str) {
        this.c = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setRadius(float f) {
        this.d = f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.music.base.BaseSquareCardView
    public void setDefaultResId(int i) {
        this.e = i;
    }
}
