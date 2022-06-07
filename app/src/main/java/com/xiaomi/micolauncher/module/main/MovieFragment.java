package com.xiaomi.micolauncher.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.main.util.MainStatHelper;
import com.xiaomi.micolauncher.module.main.view.FadeInVideoView;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoMode;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.view.VideoDetailActivity;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MovieFragment extends BaseFragment {
    protected static final int MSG_REFRESH_PAGE = 1;
    protected static final long REFRESH_PAGE_TIME_PERIOD = TimeUnit.SECONDS.toMillis(8);
    FadeInVideoView a;
    private List<? extends MainPage.MainPageVideo> b;
    private int c;
    private MainPage.MainPageVideo d;
    private Handler e;
    private Handler.Callback f = new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.main.MovieFragment.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            if (MovieFragment.this.isActivated()) {
                L.launcher.i(" refresh video page");
                MovieFragment.this.b();
                return false;
            }
            L.launcher.i(" stop refresh video, fragment not activated");
            return false;
        }
    };

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_movie, viewGroup, false);
        this.a = (FadeInVideoView) inflate.findViewById(R.id.movie_card);
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        this.e = new WeakRefHandler(this.f);
        a();
        refreshData();
        return inflate;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
        FadeInVideoView fadeInVideoView = this.a;
        if (fadeInVideoView != null) {
            fadeInVideoView.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        b();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onDeactivate() {
        super.onDeactivate();
        this.e.removeMessages(1);
    }

    private void a() {
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$MovieFragment$2opTTOXlniMBos1DWt7MSzbGJDM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MovieFragment.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        L.launcher.i("on click movie card");
        if (CommonUtil.checkHasNetworkAndShowToast(getContext()) && ChildModeManager.getManager().allowPlayingVideoOrShowActivity(getContext())) {
            MainPage.MainPageVideo mainPageVideo = this.d;
            if (mainPageVideo == null) {
                ToastUtil.showToast((int) R.string.error_get_data_failed);
            } else if (mainPageVideo.isShortVideo()) {
                VideoPlayerApi.play(getContext(), RecommendDataManager.getManager().getRecommendShortVideos(), RecommendDataManager.getManager().getRecommendShortVideoIndex(this.d.transform()), VideoMode.SHORT_VIDEO_RECOMMEND);
                MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_SHORT_VIDEO_RECOMMEND);
            } else {
                VideoItem transform = this.d.transform();
                VideoModel.getInstance().setRecommendationList(Arrays.asList(transform), null);
                Intent intent = new Intent(getContext(), VideoDetailActivity.class);
                intent.putExtra(VideoModel.KEY_VIDEO_ITEM_ID, transform.getId());
                getContext().startActivity(intent);
                MainStatHelper.recordCardClick(MainStatHelper.RecommendVal.LAUNCHER_VIDEO_RECOMMEND);
            }
        }
    }

    protected void refreshData() {
        this.b = RecommendDataManager.getManager().getRecommendVideos();
        if (!ContainerUtil.isEmpty(this.b)) {
            this.c = 0;
            b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (!ContainerUtil.isEmpty(this.b)) {
            L.launcher.i(" start refresh video page");
            this.e.removeMessages(1);
            List<? extends MainPage.MainPageVideo> list = this.b;
            int size = this.c % list.size();
            this.c = size;
            this.d = (MainPage.MainPageVideo) list.get(size);
            this.c++;
            MainPage.MainPageVideo mainPageVideo = this.d;
            if (mainPageVideo != null) {
                this.a.refreshPage(mainPageVideo.coverImageUrl(), this.d.isShortVideo(), this.d.videoTitle(), this.d.videoCategory());
                this.e.sendEmptyMessageDelayed(1, REFRESH_PAGE_TIME_PERIOD);
                return;
            }
            this.e.sendEmptyMessage(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecommendMovieDataChanged(RecommendEvent.VideoDataUpdated videoDataUpdated) {
        if (videoDataUpdated != null) {
            L.launcher.i(" on recommend movie data changed");
            refreshData();
        }
    }
}
