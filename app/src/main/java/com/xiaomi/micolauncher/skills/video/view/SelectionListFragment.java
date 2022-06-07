package com.xiaomi.micolauncher.skills.video.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.adapter.GridSpacingItemDecoration;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.databinding.FragmentSelectionListBinding;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.vm.SelectionViewModel;

/* loaded from: classes3.dex */
public class SelectionListFragment extends DialogFragment implements VideoModel.OnDataChangeListener {
    private FragmentSelectionListBinding a;
    private SelectionListAdapter b;
    private SelectionViewModel c;
    private Activity d;

    public static SelectionListFragment newInstance() {
        return new SelectionListFragment();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.c = (SelectionViewModel) new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(MicoApplication.getApp())).get(SelectionViewModel.class);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.d = (Activity) context;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.a = FragmentSelectionListBinding.inflate(layoutInflater);
        b();
        this.c.loadMore();
        VideoModel.getInstance().addOnDataChangeListener(this);
        return this.a.getRoot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        dismiss();
    }

    private void b() {
        this.a.vBgLeft.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$SelectionListFragment$pq-JeOvlUmEnQBbJsEXeUkId2cA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SelectionListFragment.this.a(view);
            }
        });
        this.a.rvSelections.addItemDecoration(new GridSpacingItemDecoration(DisplayUtils.dip2px(MicoApplication.getApp(), getResources().getDimensionPixelSize(R.dimen.mc20dp)), false));
        this.a.rvSelections.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.skills.video.view.SelectionListFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0 && !recyclerView.canScrollVertically(1)) {
                    if (VideoModel.getInstance().getMiTvHasNext()) {
                        SelectionListFragment.this.c.loadMore();
                    } else {
                        ToastUtil.showToast("已经到底了...");
                    }
                }
            }
        });
        this.b = new SelectionListAdapter(VideoModel.getInstance().getPlayList());
        this.b.setOnItemClickListener($$Lambda$SelectionListFragment$toEl3xJsc5A07UGW2Tvq9U7DXjk.INSTANCE);
        this.a.rvSelections.setAdapter(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(int i, VideoItem videoItem) {
        VideoPlayerApi.playByIndex(i);
        VideoTrackHelper.postFloatingTrack(videoItem, EventType.CLICK, i);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        if (!(getDialog() == null || getDialog().getWindow() == null)) {
            WindowManager.LayoutParams attributes = getDialog().getWindow().getAttributes();
            attributes.width = -1;
            attributes.height = -1;
            getDialog().getWindow().setAttributes(attributes);
        }
        super.onResume();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.a.rvSelections.clearOnScrollListeners();
        VideoModel.getInstance().removeOnDataChangeListener(this);
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.VideoModel.OnDataChangeListener
    public void onIndexChanged() {
        dismiss();
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.VideoModel.OnDataChangeListener
    public void onDataSetChanged() {
        final int oldSize = VideoModel.getInstance().getOldSize();
        final int size = VideoModel.getInstance().getPlayList().size();
        this.d.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$SelectionListFragment$ML7YL9O_I0I82e9AjqlYobtEmgk
            @Override // java.lang.Runnable
            public final void run() {
                SelectionListFragment.this.a(oldSize, size);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(int i, int i2) {
        this.b.notifyItemRangeInserted(i, i2);
    }
}
