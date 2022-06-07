package com.xiaomi.micolauncher.skills.video.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.skill.VideoMediaSession;
import com.xiaomi.micolauncher.common.transformation.BlurTransformation;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPlayEvent;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoSelectEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import com.xiaomi.micolauncher.skills.video.model.VideoProcessHelper;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public final class VideoDetailActivity extends BaseEventActivity {
    public static final String PLAY_FROM_ID = "play_from_id";
    public static final String PLAY_HISTORY_FROM_ID = "play_history_from_id";
    private VideoItem a;
    private ImageView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private Button h;
    private Button i;
    private TextView j;
    private RelativeLayout k;
    private ImageView l;
    private ImageView m;
    protected SerialVideoAdapter serialVideoAdapter;

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    protected boolean delayPauseStat() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_detail);
        a();
        b();
        ActivityLifeCycleManager.getInstance().finishVideoPlayerActivity();
        scheduleToClose(TimeUnit.MINUTES.toMillis(5L));
        int intExtra = getIntent().getIntExtra(VideoModel.KEY_VIDEO_INDEX, 0);
        if (intExtra > 0) {
            playVideo(this, this.a, intExtra);
        }
    }

    private void a() {
        this.b = (ImageView) findViewById(R.id.imageViewBackground);
        this.c = (TextView) findViewById(R.id.textViewTitle);
        this.d = (TextView) findViewById(R.id.textViewHotIndex);
        this.e = (TextView) findViewById(R.id.textViewIntroduce);
        this.f = (TextView) findViewById(R.id.textViewDirector);
        this.g = (TextView) findViewById(R.id.textViewMajor);
        this.h = (Button) findViewById(R.id.buttonPlayVideo);
        this.l = (ImageView) findViewById(R.id.imageViewCp);
        this.h.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoDetailActivity$xPxbBk8zxNOdcPKeGvTl80eCfUQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoDetailActivity.this.d(view);
            }
        });
        this.i = (Button) findViewById(R.id.buttonChooseSerial);
        this.i.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoDetailActivity$Gg5SOwGo8BAz4D1LZ0p8PrT5cRw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoDetailActivity.this.c(view);
            }
        });
        this.j = (TextView) findViewById(R.id.textViewSummary);
        this.a = VideoModel.getInstance().getRecommendationVideoItem(getIntent().getStringExtra(VideoModel.KEY_VIDEO_ITEM_ID));
        a((ImageView) findViewById(R.id.imageViewSnapshot));
    }

    public /* synthetic */ void d(View view) {
        playVideo(this, this.a);
    }

    public /* synthetic */ void c(View view) {
        RelativeLayout relativeLayout = this.k;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
        } else {
            L.video.e("serial layout is null, logic error");
        }
    }

    private void a(ImageView imageView) {
        VideoItem videoItem = this.a;
        if (videoItem != null) {
            videoItem.loadSerialId();
            if (this.a.isSerial()) {
                if (this.a.getEpisodeCount() > 1) {
                    d();
                    this.i.setVisibility(0);
                } else {
                    this.i.setVisibility(8);
                }
                c();
            } else {
                this.i.setVisibility(8);
            }
            if (this.a.getImageUrl() != null) {
                GlideUtils.bindImageView(imageView, this.a.getImageUrl(), 0, (Target<Bitmap>) null);
            }
            String title = this.a.getTitle();
            if (title != null) {
                this.c.setText(title);
            }
            this.d.setText(this.a.getRating());
            String str = "" + this.a.getYear();
            String category = this.a.getCategory();
            if (category != null) {
                str = str + StringUtils.SPACE + category;
            }
            int playLength = this.a.getPlayLength();
            if (playLength > 0) {
                str = str + StringUtils.SPACE + playLength + getString(R.string.video_play_length_minute);
            }
            StringBuilder sb = new StringBuilder();
            LinkedList<String> directors = this.a.getDirectors();
            if (directors != null && directors.size() > 0) {
                sb.append(getString(R.string.video_directors));
                Iterator<String> it = directors.iterator();
                while (it.hasNext()) {
                    sb.append(StringUtils.SPACE);
                    sb.append(it.next());
                }
            }
            StringBuilder sb2 = new StringBuilder();
            LinkedList<String> actors = this.a.getActors();
            if (actors != null && actors.size() > 0) {
                sb2.append(getString(R.string.video_actors));
                Iterator<String> it2 = actors.iterator();
                while (it2.hasNext()) {
                    sb2.append(StringUtils.SPACE);
                    sb2.append(it2.next());
                }
            }
            if (str.length() > 0) {
                this.e.setText(str);
            } else {
                this.e.setVisibility(8);
            }
            if (sb.length() > 0) {
                this.f.setText(sb.toString());
            } else {
                this.f.setVisibility(8);
            }
            if (sb2.length() > 0) {
                this.g.setText(sb2.toString());
            } else {
                this.g.setVisibility(8);
            }
            this.j.setText(this.a.getSummary());
            if (this.a.isIqiyi()) {
                GlideUtils.bindImageView((Activity) this, (int) R.drawable.aiqiyilogo, this.l);
            } else if (this.a.isBili()) {
                GlideUtils.bindImageView((Activity) this, (int) R.drawable.bilibili_logo, this.l);
            } else if (this.a.isVipKid()) {
                this.l.setVisibility(4);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        d();
        VideoMediaSession.getInstance().setStopped();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoPlayEvent(VideoPlayEvent videoPlayEvent) {
        if (isResumed()) {
            playVideo(this, this.a);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoSelectEvent(VideoSelectEvent videoSelectEvent) {
        if (isResumed()) {
            playVideo(this, this.a, videoSelectEvent.index);
        }
    }

    public static void playVideo(Context context, VideoItem videoItem) {
        VideoProcessHelper.playVideo(context, videoItem);
    }

    public static void playVideo(Context context, VideoItem videoItem, int i) {
        VideoProcessHelper.playVideo(context, videoItem, i);
    }

    private void b() {
        VideoItem videoItem = this.a;
        if (videoItem != null) {
            GlideUtils.bindImageView(this.b, videoItem.getImageUrl(), 0, new BlurTransformation(this, 3), new ImageViewTarget<Bitmap>(this.b) { // from class: com.xiaomi.micolauncher.skills.video.view.VideoDetailActivity.1
                /* renamed from: a */
                public void setResource(@Nullable Bitmap bitmap) {
                    VideoDetailActivity.this.b.setImageBitmap(bitmap);
                    if (VideoDetailActivity.this.m != null) {
                        VideoDetailActivity.this.m.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }

    private void c() {
        this.k = (RelativeLayout) findViewById(R.id.serial_Layout);
        this.m = (ImageView) findViewById(R.id.imageViewBackgroundSerialLayout);
        findViewById(R.id.close_serial).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoDetailActivity$2O3IOEsaXAK7ApHSsq9m0Y9q8dU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoDetailActivity.this.b(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.serial_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(8, 1));
        this.serialVideoAdapter = new SerialVideoAdapter(this.a.getEpisodeCount(), new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.video.view.-$$Lambda$VideoDetailActivity$dt0ydW-YCNFDxoWTKgfakp14qj8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoDetailActivity.this.a(view);
            }
        });
        recyclerView.setAdapter(this.serialVideoAdapter);
    }

    public /* synthetic */ void b(View view) {
        this.k.setVisibility(4);
    }

    public /* synthetic */ void a(View view) {
        Integer num = (Integer) view.getTag();
        if (num != null) {
            playVideo(this, this.a, num.intValue() + 1);
        }
        this.k.setVisibility(4);
    }

    private void d() {
        VideoItem videoItem = this.a;
        if (videoItem != null) {
            if (videoItem.getSerialId() >= 1) {
                this.h.setText(R.string.video_continue_play);
            } else {
                this.h.setText(R.string.video_play);
            }
        }
    }
}
