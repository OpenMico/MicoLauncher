package com.xiaomi.micolauncher.skills.video.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.Education;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.Video;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.instruction.InstructionManager;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.impl.EductionOperation;
import com.xiaomi.micolauncher.module.child.util.ChildHelper;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.module.video.manager.VideoStatHelper;
import com.xiaomi.micolauncher.skills.personalize.model.PersonalizeExecution;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.NextPageEvent;
import com.xiaomi.micolauncher.skills.soundboxcontrol.controller.uievent.PrevPageEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoInvokeRecommendationEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPlayEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoSelectEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import com.xiaomi.micolauncher.skills.video.view.RecommendationVideoAdapter;
import com.xiaomi.micolauncher.skills.video.view.SearchTagAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public final class VideoRecommendationActivity extends BaseEventActivity {
    private TipsStatus a;
    private List<VideoItem> b;
    private RecyclerView c;
    private RecyclerView d;
    private LinearLayoutManager e;
    private SearchTagAdapter f;
    private Runnable g;
    private List<MainScreen.InstructionItem> h;
    private int i;
    private TextView j;
    private Disposable k;
    private List<Education.EduSearchTag> l;
    private TextView m;
    private Common.LoadmoreInfo n;
    private RecyclerView.ItemDecoration o;
    private RecyclerView.ItemDecoration p;
    private ImageView q;
    protected RecommendationVideoAdapter recommendationVideoAdapter;

    /* loaded from: classes3.dex */
    public enum TipsStatus {
        Close,
        Open
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return false;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_recommendation);
        a();
        b();
        c();
        ActivityLifeCycleManager.getInstance().finishVideoPlayerActivity();
        scheduleToClose(TimeUnit.MINUTES.toMillis(5L));
        if (!ChildModeManager.getManager().isChildMode()) {
            VideoStatHelper.recordVideoPageShow(VideoStatHelper.VideoKey.VIDEO_SEARCH_SHOW);
        }
        VideoModel.getInstance().setDialogOrigin("voice");
        SpeechManager.getInstance().uiShow(QueryLatency.VideoRecommend);
    }

    private void a() {
        this.c = (RecyclerView) findViewById(R.id.recommendation_recycler_view);
        this.d = (RecyclerView) findViewById(R.id.search_tag_recycler);
        this.q = (ImageView) findViewById(R.id.recommendation_back_iv);
        this.g = new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoRecommendationActivity$psQno8oGnXsFAdu4UWQuw7FmETk
            @Override // java.lang.Runnable
            public final void run() {
                VideoRecommendationActivity.this.f();
            }
        };
        if (Hardware.isLx04()) {
            this.j = (TextView) findViewById(R.id.textViewTips);
            this.i = -1;
            h();
        } else {
            this.m = (TextView) findViewById(R.id.recommendation_title);
        }
        this.b = VideoModel.getInstance().getRecommendationList();
        if (this.b == null) {
            this.b = new ArrayList();
            L.video.w("empty recommendation list");
        }
        this.q.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoRecommendationActivity$UTqCWhs8aGjdnRTkcKYZWyTgNVI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoRecommendationActivity.this.a(view);
            }
        });
    }

    public /* synthetic */ void a(View view) {
        finish();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        a();
        b();
        c();
        SpeechManager.getInstance().uiShow(QueryLatency.VideoRecommend);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.ON_VISIBLE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoPlayEvent(VideoPlayEvent videoPlayEvent) {
        if (this.b.size() > 0) {
            a(this.b.get(0));
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onVideoSelectEvent(VideoSelectEvent videoSelectEvent) {
        int findFirstVisibleItemPosition = (this.e.findFirstVisibleItemPosition() - 1) + videoSelectEvent.index;
        if (findFirstVisibleItemPosition >= 0 && findFirstVisibleItemPosition < this.b.size()) {
            VideoModel.getInstance().setCmdVideoDuration(PersonalizeExecution.getInstance().getCurrentCmdDuration());
            VideoItem videoItem = this.b.get(findFirstVisibleItemPosition);
            videoItem.loadSerialId();
            a(videoItem);
            VideoTrackHelper.postSearchResultTrack(EventType.CLICK, videoItem, findFirstVisibleItemPosition, "voice", this.recommendationVideoAdapter.getCurrentTab(), VideoTrackHelper.PAGE_COMMON, null, "play");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoPrevPageEvent(PrevPageEvent prevPageEvent) {
        int findFirstVisibleItemPosition = this.e.findFirstVisibleItemPosition() - 3;
        if (findFirstVisibleItemPosition < 0) {
            findFirstVisibleItemPosition = 0;
        }
        this.c.scrollToPosition(findFirstVisibleItemPosition);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoNextPageEvent(NextPageEvent nextPageEvent) {
        int findLastVisibleItemPosition = this.e.findLastVisibleItemPosition() + 3;
        if (findLastVisibleItemPosition >= this.c.getAdapter().getItemCount()) {
            findLastVisibleItemPosition = this.c.getAdapter().getItemCount();
        }
        this.c.scrollToPosition(findLastVisibleItemPosition);
    }

    private void a(VideoItem videoItem) {
        if (isMicoActivityResumed()) {
            VideoProcessHelper.playVideo(this, videoItem, videoItem.getEpisode());
        }
    }

    private void b() {
        this.e = new LinearLayoutManager(this, 0, false);
        this.c.setLayoutManager(this.e);
        this.recommendationVideoAdapter = new RecommendationVideoAdapter(this, this.b, new RecommendationVideoAdapter.OnItemClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoRecommendationActivity$xmf3i5j1Rou_XLTygrr3Oc6lYq0
            @Override // com.xiaomi.micolauncher.skills.video.view.RecommendationVideoAdapter.OnItemClickListener
            public final void onItemClick(VideoItem videoItem) {
                VideoRecommendationActivity.this.b(videoItem);
            }
        });
        this.c.setAdapter(this.recommendationVideoAdapter);
        if (this.p == null) {
            this.p = new RecommendationVideoAdapter.SpacesItemDecoration(this.b.size(), DisplayUtils.dip2px((Activity) this, 2.0f));
            this.c.addItemDecoration(this.p);
        }
        this.c.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && recyclerView.getAdapter() != null) {
                    int itemCount = recyclerView.getAdapter().getItemCount();
                    int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    int childCount = recyclerView.getChildCount();
                    if (i == 0 && findLastVisibleItemPosition == itemCount - 1 && childCount > 0 && VideoRecommendationActivity.this.n != null && VideoRecommendationActivity.this.n.isNeedsLoadmore().get().booleanValue()) {
                        VideoRecommendationActivity.this.d();
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        });
    }

    public /* synthetic */ void b(VideoItem videoItem) {
        if (videoItem.isMiTV()) {
            VideoModel.getInstance().setMiTvType(ChildHelper.parsePCode(videoItem.getpCode()));
        }
        VideoDetailActivity.playVideo(this, videoItem);
        if (!ChildModeManager.getManager().isChildMode()) {
            VideoStatHelper.recordVideoSearchClick();
        }
    }

    private void c() {
        this.d.setLayoutManager(new LinearLayoutManager(this, 0, false));
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.course_filter_list_divider);
        if (this.o == null) {
            this.o = new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                    rect.right = dimensionPixelOffset;
                }
            };
            this.d.addItemDecoration(this.o);
            if (this.d.getItemAnimator() != null) {
                ((SimpleItemAnimator) this.d.getItemAnimator()).setSupportsChangeAnimations(false);
            }
        }
        this.f = new SearchTagAdapter(this);
        this.d.setAdapter(this.f);
        this.l = VideoModel.getInstance().getSearchTagList();
        if (ContainerUtil.hasData(this.l)) {
            this.f.a(this.l);
            this.m.setVisibility(8);
            this.d.setVisibility(0);
            for (int i = 0; i < this.l.size(); i++) {
                Education.EduSearchTag eduSearchTag = this.l.get(i);
                if (eduSearchTag.isSelected()) {
                    this.recommendationVideoAdapter.setCurrentTab(eduSearchTag.getText());
                }
            }
        } else {
            this.m.setVisibility(0);
            this.d.setVisibility(8);
        }
        this.f.a(new SearchTagAdapter.OnFilterItemClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoRecommendationActivity$3_FRalzDkjmwq5ghhpwDm69dfdQ
            @Override // com.xiaomi.micolauncher.skills.video.view.SearchTagAdapter.OnFilterItemClickListener
            public final void onFilterClick(int i2) {
                VideoRecommendationActivity.this.a(i2);
            }
        });
    }

    public /* synthetic */ void a(int i) {
        if (!ContainerUtil.isOutOfBound(i, this.l)) {
            this.recommendationVideoAdapter.setCurrentTab(this.l.get(i).getText());
        }
        for (int i2 = 0; i2 < this.l.size(); i2++) {
            Education.EduSearchTag eduSearchTag = this.l.get(i2);
            if (i == i2) {
                eduSearchTag.setSelected(true);
            } else {
                eduSearchTag.setSelected(false);
            }
        }
        this.b.clear();
        this.recommendationVideoAdapter.setRecommendationVideoItems(this.b);
        a(this.l);
    }

    private void a(List<Education.EduSearchTag> list) {
        Education.EduSearchTagsReset eduSearchTagsReset = new Education.EduSearchTagsReset();
        eduSearchTagsReset.setTags(list);
        Event buildEvent = APIUtils.buildEvent(eduSearchTagsReset);
        buildEvent.addContext(SpeechContextHelper.educationControllerContext());
        a(buildEvent);
    }

    public void d() {
        String str = this.n.getOriginId().get();
        Nlp.LoadMore loadMore = new Nlp.LoadMore(str);
        loadMore.setOriginId(str);
        loadMore.setToken(this.n.getToken().get());
        loadMore.setDelta(10);
        Event buildEvent = APIUtils.buildEvent(loadMore);
        buildEvent.addContext(SpeechContextHelper.educationControllerContext());
        a(buildEvent);
    }

    private void a(Event event) {
        SpeechEventUtil.getInstance().eventRequest(event, new SpeechEventUtil.EventListener() { // from class: com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity.3
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventResult(List<Instruction> list, Event event2) {
                Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.Education.EduShowSearchPage);
                if (intention != null && (intention.getPayload() instanceof Education.EduShowSearchPage)) {
                    Education.EduShowSearchPage eduShowSearchPage = (Education.EduShowSearchPage) intention.getPayload();
                    List<Video.VideoSearchItem> items = eduShowSearchPage.getItems();
                    List<Education.EduSearchTag> list2 = eduShowSearchPage.getTags().get();
                    List<VideoItem> parseVideos = EductionOperation.parseVideos(items, eduShowSearchPage.getItemsMoreInfo().get());
                    Common.PageLoadType pageLoadType = eduShowSearchPage.getLoadType().get();
                    L.course.i("pageLoadType  %s", Integer.valueOf(pageLoadType.getId()));
                    if (ContainerUtil.hasData(VideoRecommendationActivity.this.l)) {
                        VideoRecommendationActivity.this.l = list2;
                    }
                    if (AnonymousClass6.a[pageLoadType.ordinal()] != 1) {
                        VideoRecommendationActivity.this.c(parseVideos);
                    } else {
                        VideoRecommendationActivity.this.b(parseVideos);
                    }
                }
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.EventListener
            public void onEventFail(Event event2, boolean z) {
                L.video.e("load tag query data error");
            }
        });
    }

    /* renamed from: com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity$6 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] a = new int[Common.PageLoadType.values().length];

        static {
            try {
                a[Common.PageLoadType.APPEND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[Common.PageLoadType.REFRESH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[Common.PageLoadType.PARTIAL_REFRESH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void b(final List<VideoItem> list) {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoRecommendationActivity$NsvmSsugIZnhH4Yqv3plEn56CII
            @Override // java.lang.Runnable
            public final void run() {
                VideoRecommendationActivity.this.f(list);
            }
        });
    }

    public /* synthetic */ void f(List list) {
        if (ContainerUtil.hasData(list)) {
            this.b.addAll(list);
            this.recommendationVideoAdapter.setRecommendationVideoItems(this.b);
        }
    }

    public void c(final List<VideoItem> list) {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoRecommendationActivity$XxAc4AbJBF8x4m2RSG9Dj_b7Apo
            @Override // java.lang.Runnable
            public final void run() {
                VideoRecommendationActivity.this.e(list);
            }
        });
    }

    public /* synthetic */ void e(List list) {
        if (ContainerUtil.hasData(list)) {
            this.b.clear();
            this.b.addAll(list);
            this.recommendationVideoAdapter.setRecommendationVideoItems(this.b);
            this.c.scrollToPosition(0);
        }
    }

    public void e() {
        g();
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim_in);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity.4
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (VideoRecommendationActivity.this.a == TipsStatus.Open) {
                    VideoRecommendationActivity.this.getHandler().postDelayed(VideoRecommendationActivity.this.g, 7000L);
                }
            }
        });
        this.a = TipsStatus.Open;
        TextView textView = this.j;
        if (textView != null) {
            textView.setVisibility(0);
            this.j.startAnimation(loadAnimation);
        }
    }

    public void f() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim_out);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.skills.video.view.VideoRecommendationActivity.5
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (VideoRecommendationActivity.this.a == TipsStatus.Open) {
                    VideoRecommendationActivity.this.e();
                }
            }
        });
        TextView textView = this.j;
        if (textView != null) {
            textView.startAnimation(loadAnimation);
        }
    }

    private void g() {
        if (!ContainerUtil.isEmpty(this.h) && this.j != null) {
            int i = this.i;
            do {
                this.i = new Random().nextInt(this.h.size());
                if (i != this.i) {
                    break;
                }
            } while (this.h.size() != 1);
            TextView textView = this.j;
            if (textView != null) {
                textView.setText(this.h.get(this.i).text);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        VideoModel.getInstance().setDialogOrigin("soundbox");
        Disposable disposable = this.k;
        if (disposable != null && !disposable.isDisposed()) {
            this.k.dispose();
            this.k = null;
        }
    }

    private void h() {
        this.k = InstructionManager.getInstance().getVideoInstructionList().subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoRecommendationActivity$kM8MODPyuO82eVR59ScSSOw4eds
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                VideoRecommendationActivity.this.d((List) obj);
            }
        });
    }

    public /* synthetic */ void d(List list) throws Exception {
        this.h = list;
        e();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showVideoInvokeRecommendationEvent(VideoInvokeRecommendationEvent videoInvokeRecommendationEvent) {
        this.n = videoInvokeRecommendationEvent.loadmoreInfo;
        Common.LoadmoreInfo loadmoreInfo = this.n;
        if (loadmoreInfo != null && loadmoreInfo.isNeedsLoadmore().get().booleanValue()) {
            L.course.i("need load more");
        }
    }
}
