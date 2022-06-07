package com.xiaomi.micolauncher.module.music.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.module.childsong.ui.ChildSongSquareCardView;
import com.xiaomi.micolauncher.module.music.bean.SquareItem;
import com.xiaomi.micolauncher.module.music.bean.TitleType;
import com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.module.music.view.MusicSquareCardView;
import com.xiaomi.micolauncher.module.station.ui.StationSquareCardView;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.micolauncher.module.video.ui.view.VideoSquareCardView;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BasePatchWallBlockView extends FrameLayout implements BindLifecycleListener {
    private static final long a = TimeUnit.SECONDS.toMillis(5);
    private static final int b;
    private float A;
    private float B;
    private Context c;
    private List<Object> d;
    private List<BaseSquareCardView> e;
    private boolean f;
    private Map<BaseSquareCardView, Integer> g;
    private Set<Integer> h;
    private int i;
    private float j;
    private float k;
    private float l;
    private float m;
    private float n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private String t;
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private WeakRefHandler y;
    private Handler.Callback z;

    static {
        b = Hardware.isBigScreen() ? 6 : 8;
    }

    public void a() {
        int i;
        if (!this.w && this.d.size() > 0 && this.e.size() > 0) {
            this.r = a(this.h, this.d.size());
            if (this.r >= 0) {
                this.s %= this.e.size();
                if (this.v && (i = this.s) == 0) {
                    this.s = i + 1;
                }
                List<BaseSquareCardView> list = this.e;
                int i2 = this.s;
                this.s = i2 + 1;
                BaseSquareCardView baseSquareCardView = list.get(i2);
                Object obj = this.d.get(this.r);
                int intValue = this.g.get(baseSquareCardView).intValue();
                if (isAttachedToWindow()) {
                    baseSquareCardView.refreshPage(obj);
                    this.y.sendEmptyMessageDelayed(1, a);
                    this.h.remove(Integer.valueOf(intValue));
                    this.h.add(Integer.valueOf(this.r));
                    this.g.put(baseSquareCardView, Integer.valueOf(this.r));
                }
            }
        }
    }

    public BasePatchWallBlockView(Context context) {
        this(context, null);
    }

    public BasePatchWallBlockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.d = new ArrayList();
        this.e = new ArrayList();
        this.g = new HashMap();
        this.h = new HashSet();
        this.i = b;
        this.z = new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.music.base.BasePatchWallBlockView.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what != 1) {
                    return false;
                }
                BasePatchWallBlockView.this.a();
                return false;
            }
        };
        this.c = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PatchWallBlock);
        this.j = obtainStyledAttributes.getDimension(4, 167.0f);
        this.k = obtainStyledAttributes.getDimension(1, 139.0f);
        this.m = obtainStyledAttributes.getDimension(2, 4.0f);
        this.l = obtainStyledAttributes.getDimension(3, 4.0f);
        this.n = obtainStyledAttributes.getDimension(5, 10.0f);
        this.i = obtainStyledAttributes.getInteger(6, b);
        this.o = obtainStyledAttributes.getResourceId(0, R.drawable.music_card_view_default);
        this.y = new WeakRefHandler(this.z);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < this.e.size(); i5++) {
            int[] a2 = a(i5);
            float f = this.j;
            int i6 = (int) ((this.A + f) * a2[1]);
            float f2 = this.k;
            int i7 = (int) ((this.B + f2) * a2[0]);
            this.e.get(i5).layout(i6, i7, (int) (i6 + f), (int) (i7 + f2));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.y.removeMessages(1);
    }

    @Override // com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener
    public void onResume() {
        this.y.removeMessages(1);
        this.y.sendEmptyMessage(1);
    }

    @Override // com.xiaomi.micolauncher.module.music.listener.BindLifecycleListener
    public void onPause() {
        this.y.removeMessages(1);
    }

    public void setDisableRefresh(boolean z) {
        this.w = z;
    }

    public void setFixFirstLocation(boolean z) {
        this.x = z;
    }

    public void setDataList(List<Object> list, TitleType titleType) {
        setDataList(list, true, titleType);
    }

    public void setDataList(List<Object> list, boolean z, TitleType titleType) {
        setDataList(list, z, titleType, "");
    }

    public void setShowPlayIcon(boolean z) {
        this.f = z;
    }

    public void setDefaultResId(int i) {
        this.o = i;
    }

    public void setDataList(List<Object> list, boolean z, TitleType titleType, String str) {
        this.u = z;
        this.d.clear();
        this.d.addAll(list);
        this.t = str;
        a(titleType);
        if (list.size() > this.i && !this.w) {
            this.y.removeMessages(1);
            this.y.sendEmptyMessageDelayed(1, a);
        }
    }

    private int[] a(int i) {
        int[] iArr = new int[2];
        int i2 = 0;
        while (i2 < this.p) {
            int i3 = 0;
            while (true) {
                if (i3 >= this.q) {
                    break;
                }
                if (Hardware.isBigScreen()) {
                    float f = 0.0f;
                    this.B = i2 == 0 ? 0.0f : this.l;
                    if (i3 != 0) {
                        f = this.m;
                    }
                    this.A = f;
                } else {
                    this.B = this.l;
                    this.A = this.m;
                }
                if ((this.p * i3) + i2 == i) {
                    iArr[0] = i2;
                    iArr[1] = i3;
                    break;
                }
                i3++;
            }
            i2++;
        }
        return iArr;
    }

    private void b() {
        int size = this.d.size();
        this.p = 2;
        int i = this.i;
        if (size < i) {
            this.q = size / 2;
        } else {
            this.q = i / 2;
        }
        float f = this.j;
        int i2 = this.q;
        float f2 = this.k;
        int i3 = this.p;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) ((f * i2) + (this.m * (i2 - 1))), (int) ((f2 * i3) + (this.l * (i3 - 1))));
        layoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.card_margin_top), 0, getResources().getDimensionPixelSize(R.dimen.card_margin_bottom));
        setLayoutParams(layoutParams);
    }

    private List<Integer> a(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        if (i > i2) {
            if (this.x) {
                arrayList.add(0);
            }
            while (arrayList.size() < i2) {
                int random = (int) (Math.random() * i);
                if (random >= i) {
                    random %= i;
                }
                if (!arrayList.contains(Integer.valueOf(random))) {
                    arrayList.add(Integer.valueOf(random));
                }
            }
        } else {
            while (i3 < i) {
                i3++;
                arrayList.add(Integer.valueOf(i3));
            }
        }
        return arrayList;
    }

    private int a(Set<Integer> set, int i) {
        if (set.size() >= i) {
            return -1;
        }
        int i2 = -1;
        while (true) {
            if (i2 != -1 && !set.contains(Integer.valueOf(i2))) {
                return i2;
            }
            i2 = (int) (Math.random() * i);
            if (i2 >= i) {
                i2 %= i;
            }
        }
    }

    private void a(TitleType titleType) {
        removeAllViews();
        this.e.clear();
        this.g.clear();
        b();
        List<Integer> a2 = a(this.d.size(), this.i);
        for (int i = 0; i < a2.size(); i++) {
            int intValue = a2.get(i).intValue();
            BaseSquareCardView a3 = a(titleType, intValue);
            if (a3 != null) {
                this.h.add(Integer.valueOf(intValue));
                addView(a3, new FrameLayout.LayoutParams((int) this.j, (int) this.k));
                this.e.add(a3);
                this.g.put(a3, Integer.valueOf(intValue));
            }
        }
        requestLayout();
    }

    private BaseSquareCardView a(TitleType titleType, int i) {
        Object obj = this.d.get(i);
        if (obj instanceof SquareItem) {
            MusicSquareCardView musicSquareCardView = new MusicSquareCardView(this.c);
            musicSquareCardView.setShowPlayIcon(this.f);
            a(titleType, musicSquareCardView, (SquareItem) obj);
            if (this.u || i != 0 || !MusicDataManager.getManager().isQQMusicSource() || Hardware.isBigScreen()) {
                return musicSquareCardView;
            }
            musicSquareCardView.setTitleType(TitleType.NONE);
            musicSquareCardView.showTimeView();
            this.v = true;
            return musicSquareCardView;
        } else if (obj instanceof VideoItem) {
            VideoSquareCardView videoSquareCardView = new VideoSquareCardView(this.c);
            a(titleType, videoSquareCardView, (VideoItem) obj);
            if (this.u || VideoDataManager.getManager().isChildMode()) {
                return videoSquareCardView;
            }
            videoSquareCardView.showTagView();
            return videoSquareCardView;
        } else if (obj instanceof Station.Item) {
            StationSquareCardView stationSquareCardView = new StationSquareCardView(this.c);
            a(titleType, stationSquareCardView, (Station.Item) obj);
            return stationSquareCardView;
        } else if (!(obj instanceof PatchWall.Item)) {
            return null;
        } else {
            ChildSongSquareCardView childSongSquareCardView = new ChildSongSquareCardView(this.c);
            a(titleType, childSongSquareCardView, (PatchWall.Item) obj);
            return childSongSquareCardView;
        }
    }

    private void a(TitleType titleType, BaseSquareCardView baseSquareCardView, Object obj) {
        baseSquareCardView.setTitleType(titleType);
        baseSquareCardView.setRadius(this.n);
        baseSquareCardView.setDefaultResId(this.o);
        baseSquareCardView.refreshPage(obj);
        baseSquareCardView.setTitleName(this.t);
    }
}
