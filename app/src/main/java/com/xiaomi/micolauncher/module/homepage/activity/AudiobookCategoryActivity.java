package com.xiaomi.micolauncher.module.homepage.activity;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.module.homepage.adapter.SubAudiobookContentAdapter;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.event.LoadAllCategoryDatasEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadCategoryDatasEvent;
import com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class AudiobookCategoryActivity extends BaseAudiobookActivity implements AudiobookRefreshViewHolder.OnFooterRefreshCallback {
    RecyclerView a;
    ConstraintLayout b;
    ImageView c;
    private Station.CategoryItem d;
    private AllCategoryAdapter f;
    private List<AudiobookContent> g;
    private final HashMap<String, List<AudiobookContent>> h = new HashMap<>();
    private String i;
    private boolean j;

    @Override // com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    protected int layoutId() {
        return R.layout.audiobook_category_layout;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    public void initView() {
        super.initView();
        this.a = (RecyclerView) findViewById(R.id.category_recycler);
        this.b = (ConstraintLayout) findViewById(R.id.loading_layout);
        this.c = (ImageView) findViewById(R.id.category_back_iv);
        this.a.setLayoutManager(new LinearLayoutManager(this, 0, false) { // from class: com.xiaomi.micolauncher.module.homepage.activity.AudiobookCategoryActivity.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                    L.audiobook.e("onLayoutChildren exception : %s", e);
                }
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.activity.-$$Lambda$AudiobookCategoryActivity$VH__MB_brilnpSU2GmVjOYkgvwE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AudiobookCategoryActivity.this.a(view);
            }
        });
        this.e.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.homepage.activity.AudiobookCategoryActivity.2
            final int a;

            {
                this.a = AudiobookCategoryActivity.this.getResources().getDimensionPixelOffset(R.dimen.audiobook_category_content_view_margin_top);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                rect.top = this.a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadAllCategoryDatas(LoadAllCategoryDatasEvent loadAllCategoryDatasEvent) {
        if (this.f == null && ContainerUtil.hasData(loadAllCategoryDatasEvent.categorys)) {
            this.f = new AllCategoryAdapter(loadAllCategoryDatasEvent.categorys);
            this.a.setAdapter(this.f);
        } else if (!ContainerUtil.isEmpty(loadAllCategoryDatasEvent.datas)) {
            a(loadAllCategoryDatasEvent.datas);
        }
    }

    private void a() {
        if (this.b.getVisibility() == 0) {
            this.b.setVisibility(8);
        }
        if (this.e.getVisibility() == 8) {
            this.e.setVisibility(0);
        }
    }

    private void a(List<AudiobookContent> list) {
        a();
        this.g = list;
        this.h.put(this.i, list);
        this.adapter = new SubAudiobookContentAdapter();
        this.adapter.addFooterHolder(new AudiobookRefreshViewHolder(LayoutInflater.from(this).inflate(R.layout.audiobook_footer_layout, (ViewGroup) this.e, false)).setCallback(this));
        this.adapter.setContents(list, TrackWidget.STATION_DISCOVER_CATEGORY_RECOMMEND);
        this.e.setAdapter(this.adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loadSingleCategoryData(LoadCategoryDatasEvent loadCategoryDatasEvent) {
        if (this.j && !ContainerUtil.isEmpty(loadCategoryDatasEvent.datas)) {
            a(loadCategoryDatasEvent.datas);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.j = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.j = false;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.activity.BaseAudiobookActivity
    protected void loadData() {
        this.b.setVisibility(0);
        AudiobookDataManager.getManager().loadCategoryDatas();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.audiobook.AudiobookRefreshViewHolder.OnFooterRefreshCallback
    public void onFooterRefresh() {
        AudiobookDataManager.getManager().loadAudiobookAll(this.d.name);
    }

    /* loaded from: classes3.dex */
    public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryViewHolder> {
        Typeface a = Typeface.create(TypefaceUtil.FONT_WEIGHT_W400, 0);
        Typeface b = Typeface.create(TypefaceUtil.FONT_WEIGHT_W600, 0);
        private final List<Station.CategoryItem> d;
        private TextView e;
        private int f;

        public AllCategoryAdapter(List<Station.CategoryItem> list) {
            this.d = list;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            L.homepage.d("audiobook category onCreateViewHolder : %d", Integer.valueOf(i));
            return new AllCategoryViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.audiobook_all_category_item, viewGroup, false));
        }

        public void onBindViewHolder(@NonNull AllCategoryViewHolder allCategoryViewHolder, int i) {
            L.homepage.d("audiobook category onBindViewHolder : %d", Integer.valueOf(i));
            allCategoryViewHolder.bindData(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ContainerUtil.getSize(this.d);
        }

        /* loaded from: classes3.dex */
        public class AllCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView a;
            int b;

            public AllCategoryViewHolder(View view) {
                super(view);
                this.a = (TextView) view.findViewById(R.id.category_title);
                this.a.setOnClickListener(this);
            }

            public void bindData(int i) {
                this.b = i;
                Station.CategoryItem categoryItem = (Station.CategoryItem) AllCategoryAdapter.this.d.get(i);
                this.a.setText(categoryItem.name);
                TextView textView = AllCategoryAdapter.this.e;
                int i2 = R.color.category_title_select_color;
                if (textView == null) {
                    AllCategoryAdapter.this.e = this.a;
                    AudiobookCategoryActivity.this.i = categoryItem.name;
                    AudiobookCategoryActivity.this.d = categoryItem;
                    this.a.setTypeface(AllCategoryAdapter.this.b);
                    this.a.setTextColor(AudiobookCategoryActivity.this.getColor(R.color.category_title_select_color));
                    return;
                }
                boolean z = AllCategoryAdapter.this.f == i;
                this.a.setTypeface(z ? AllCategoryAdapter.this.b : AllCategoryAdapter.this.a);
                TextView textView2 = this.a;
                AudiobookCategoryActivity audiobookCategoryActivity = AudiobookCategoryActivity.this;
                if (!z) {
                    i2 = R.color.category_title_normal_color;
                }
                textView2.setTextColor(audiobookCategoryActivity.getColor(i2));
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AllCategoryAdapter.this.f != this.b) {
                    AudiobookCategoryActivity.this.d = (Station.CategoryItem) AllCategoryAdapter.this.d.get(this.b);
                    AllCategoryAdapter.this.f = this.b;
                    AudiobookCategoryActivity.this.i = AudiobookCategoryActivity.this.d.name;
                    if (AllCategoryAdapter.this.e != null) {
                        AllCategoryAdapter.this.e.setTypeface(AllCategoryAdapter.this.a);
                        AllCategoryAdapter.this.e.setTextColor(AudiobookCategoryActivity.this.getColor(R.color.category_title_normal_color));
                    }
                    AllCategoryAdapter.this.e = (TextView) view;
                    AllCategoryAdapter.this.e.setTypeface(AllCategoryAdapter.this.b);
                    AllCategoryAdapter.this.e.setTextColor(AudiobookCategoryActivity.this.getColor(R.color.category_title_select_color));
                    if (ContainerUtil.hasData((Collection) AudiobookCategoryActivity.this.h.get(AudiobookCategoryActivity.this.i))) {
                        AudiobookCategoryActivity.this.e.smoothScrollToPosition(0);
                        AudiobookCategoryActivity.this.g = (List) AudiobookCategoryActivity.this.h.get(AudiobookCategoryActivity.this.i);
                        AudiobookCategoryActivity.this.adapter.setContents(AudiobookCategoryActivity.this.g, TrackWidget.STATION_DISCOVER_CATEGORY_RECOMMEND);
                        AudiobookCategoryActivity.this.adapter.notifyDataSetChanged();
                        return;
                    }
                    AudiobookCategoryActivity.this.e.setVisibility(8);
                    AudiobookCategoryActivity.this.b.setVisibility(0);
                    AudiobookDataManager.getManager().loadAudiobookAll(AudiobookCategoryActivity.this.d.name);
                }
            }
        }
    }
}
