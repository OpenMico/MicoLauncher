package com.xiaomi.micolauncher.module.station.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.module.music.base.BaseSquareCardView;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import com.xiaomi.micolauncher.skills.music.PlayerApi;

/* loaded from: classes3.dex */
public class StationSquareCardView extends BaseSquareCardView {
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

    public StationSquareCardView(Context context) {
        this(context, null);
    }

    public StationSquareCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StationSquareCardView(Context context, AttributeSet attributeSet, int i) {
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
        if (obj != null && (obj instanceof Station.Item)) {
            final Station.Item item = (Station.Item) obj;
            GlideUtils.bindImageViewWithDefaultAndRoundCorners(this.f, item.getCover(), this.a, this.e, (int) this.d);
            this.b.setText(item.getTitle());
            setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.station.ui.-$$Lambda$StationSquareCardView$mujpjJ9r-sdds7vYW-BUhKrV420
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StationSquareCardView.this.a(item, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Station.Item item, View view) {
        if (item != null && !ContainerUtil.isEmpty(item.getId())) {
            PlayerApi.playStation(getContext(), item.getId(), item.getOrigin(), item.getTypeId());
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
