package com.xiaomi.smarthome.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dinuscxj.itemdecoration.LinearOffsetsItemDecoration;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.media.MicoMediaEvent;
import com.xiaomi.micolauncher.common.utils.AnimationUtils;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.base.BaseActivity;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;
import com.xiaomi.smarthome.ui.adapter.MediaListAdapter;
import com.xiaomi.smarthome.ui.media.RelayMediaListener;
import com.xiaomi.smarthome.ui.media.RelayMediaManger;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes4.dex */
public class MediaListActivity extends BaseActivity implements RelayMediaListener {
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_STATUS = "extra_status";
    private RecyclerView a;
    private MicoMediaData b;
    private MediaListAdapter c;

    @Override // com.xiaomi.smarthome.ui.media.RelayMediaListener
    public void onDataSizeChange(int i) {
    }

    @Override // com.xiaomi.smarthome.ui.media.RelayMediaListener
    public void onFirstDataChanged(@Nullable MicoMediaData micoMediaData) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_media_list);
        this.b = RelayMediaManger.INSTANCE.getPlayMediaData();
        int intExtra = getIntent().getIntExtra(EXTRA_STATUS, 0);
        this.a = (RecyclerView) findViewById(R.id.mediaList);
        this.a.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.c = new MediaListAdapter(this, this.b);
        this.a.setAdapter(this.c);
        this.c.setPlayState(intExtra);
        this.c.setRelayMediaData(RelayMediaManger.INSTANCE.getRelayMediaDataList());
        LinearOffsetsItemDecoration linearOffsetsItemDecoration = new LinearOffsetsItemDecoration(0);
        linearOffsetsItemDecoration.setItemOffsets(getResources().getDimensionPixelOffset(R.dimen.smart_home_item_decoration));
        this.a.addItemDecoration(linearOffsetsItemDecoration);
        this.a.setItemAnimator(new DefaultItemAnimator());
        EventBusRegistry.getEventBus().register(this);
        RelayMediaManger.INSTANCE.registerListener(this);
        View findViewById = findViewById(R.id.ivBack);
        final ImageView imageView = (ImageView) findViewById(R.id.ivBackCircleBg);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.smarthome.ui.-$$Lambda$MediaListActivity$-2JfMuQ7Rza7a3Xt9KIEPnarDp0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaListActivity.this.a(view);
            }
        });
        findViewById.setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.smarthome.ui.MediaListActivity.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action != 3) {
                    switch (action) {
                        case 0:
                            AnimationUtils.titleIconTouchDown(imageView);
                            return false;
                        case 1:
                            break;
                        default:
                            return false;
                    }
                }
                AnimationUtils.titleIconTouchUp(imageView);
                return false;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.smarthome.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBusRegistry.getEventBus().unregister(this);
        RelayMediaManger.INSTANCE.unRegisterListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMicoMediaEvent(MicoMediaEvent micoMediaEvent) {
        this.b = new MicoMediaData(micoMediaEvent);
        this.c.setMicoMediaData(this.b);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayStateChanged(MediaControllerEvent.MusicState musicState) {
        this.c.setPlayState(musicState.getState());
    }

    @Override // com.xiaomi.smarthome.ui.media.RelayMediaListener
    public void onDataChanged(@NonNull List<MicoMediaData> list) {
        this.c.setRelayMediaData(list);
    }
}
