package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class HotMusicItem extends FrameLayout implements View.OnClickListener {
    TextView a;
    TextView b;
    TextView c;
    TextView d;
    ImageView e;
    private PatchWall.Item f;
    private PatchWall.Block g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;

    public HotMusicItem(Context context) {
        this(context, null);
    }

    public HotMusicItem(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HotMusicItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        X2CWrapper.inflate(context, (int) R.layout.hot_music_item, this);
        this.a = (TextView) findViewById(R.id.first_music);
        this.b = (TextView) findViewById(R.id.first_music_artist);
        this.c = (TextView) findViewById(R.id.second_music);
        this.d = (TextView) findViewById(R.id.third_music);
        this.e = (ImageView) findViewById(R.id.markIv);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HotMusicItem);
        this.h = obtainStyledAttributes.getResourceId(1, R.drawable.music_rank_hottag);
        this.i = obtainStyledAttributes.getColor(4, ContextCompat.getColor(context, R.color.white));
        this.j = obtainStyledAttributes.getColor(2, ContextCompat.getColor(context, R.color.white));
        this.k = obtainStyledAttributes.getColor(3, ContextCompat.getColor(context, R.color.white));
        this.l = obtainStyledAttributes.getResourceId(0, R.drawable.music_rank_hotfirst);
        this.m = obtainStyledAttributes.getResourceId(5, R.drawable.music_rank_hotsecond);
        this.n = obtainStyledAttributes.getResourceId(6, R.drawable.music_rank_hotthird);
        obtainStyledAttributes.recycle();
    }

    public void initInMain() {
        this.e.setImageResource(this.h);
        this.a.setTextColor(this.i);
        this.b.setTextColor(this.i);
        this.c.setTextColor(this.j);
        this.d.setTextColor(this.j);
        AnimLifecycleManager.repeatOnAttach(this, MicoAnimConfigurator4EntertainmentAndApps.get());
        setOnClickListener(this);
    }

    public void setDataList(PatchWall.Block block, int i) {
        if (block != null && !ContainerUtil.isEmpty(block.items)) {
            this.g = block;
            this.f = block.items.get(i);
            List<PatchWall.Song> list = this.f.songs;
            if (!ContainerUtil.isEmpty(list)) {
                a(this.a, this.b, list.get(0));
                a(this.c, this.m, list.get(1));
                a(this.d, this.n, list.get(2));
            }
        }
    }

    private void a(TextView textView, TextView textView2, PatchWall.Song song) {
        if (song != null) {
            textView.setText(song.name);
            textView2.setText(getResources().getString(R.string.hot_music_first, song.artist.name));
        }
    }

    private void a(TextView textView, int i, PatchWall.Song song) {
        if (song != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(i), (Drawable) null, (Drawable) null, (Drawable) null);
            String string = getResources().getString(R.string.hot_music, song.name, song.artist.name);
            SpannableString spannableString = new SpannableString(string);
            spannableString.setSpan(new ForegroundColorSpan(this.j), 0, song.name.length(), 33);
            spannableString.setSpan(new ForegroundColorSpan(this.k), song.name.length(), string.length(), 33);
            textView.setText(spannableString);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        PatchWall.Item item = this.f;
        if (item != null && !TextUtils.isEmpty(item.target)) {
            SchemaManager.handleSchema(getContext(), this.f.target);
            MusicStatHelper.recordMusicClick(this.g.title, this.f.title);
        }
    }

    public void setItemTag(int i) {
        this.h = i;
    }

    public void setItemTitleTextColor(int i) {
        this.i = i;
    }

    public void setTipTextColor(int i) {
        this.j = i;
    }

    public void setFirstIndexSrc(int i) {
        this.l = i;
    }

    public void setSecondIndexSrc(int i) {
        this.m = i;
    }

    public void setThirdIndexSrc(int i) {
        this.n = i;
    }
}
