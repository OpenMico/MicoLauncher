package com.xiaomi.micolauncher.skills.openplatform.view;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.FarewellEvent;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.OpenPlatformChatListUpdateEvent;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.SkillIconDownloadFinishEvent;
import com.xiaomi.micolauncher.skills.openplatform.model.OpenPlatformModel;
import com.xiaomi.micolauncher.skills.openplatform.model.SkillChatItem;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public final class OpenPlatformChatListActivity extends BaseEventActivity {
    private RecyclerView a;
    private OpenPlatformAdapter b;
    private boolean c;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_openplatform_chat_list);
        b();
        scheduleToClose(DEFAULT_CLOSE_DURATION);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        L.openplatform.i("OpenPlatformChatListActivity onBackPressed");
        a();
    }

    private void a() {
        L.openplatform.i("OpenPlatformChatListActivity onUserQuitOperation");
        this.c = true;
        OpenPlatformModel.getInstance().setQuitByVoice(true);
        OpenPlatformModel.getInstance().quit(OpenPlatformModel.QuitType.NLP_AND_TTS_RESPONSE, true ^ this.c);
    }

    private void b() {
        this.a = (RecyclerView) findViewById(R.id.listviewOpenPlatformChatList);
        this.a.setLayoutManager(new LinearLayoutManager(this, 1, false));
        List<SkillChatItem> chatList = OpenPlatformModel.getInstance().getChatList();
        if (chatList != null) {
            this.b = new OpenPlatformAdapter(chatList);
            this.a.setAdapter(this.b);
            this.a.smoothScrollToPosition(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenPlatformChatListUpdateEvent(OpenPlatformChatListUpdateEvent openPlatformChatListUpdateEvent) {
        if (this.b != null) {
            List<SkillChatItem> chatList = OpenPlatformModel.getInstance().getChatList();
            this.b.a(chatList);
            this.b.notifyDataSetChanged();
            int size = chatList.size();
            L.openplatform.v("onOpenPlatformChatListUpdateEvent size=%d", Integer.valueOf(size));
            if (size > 1) {
                this.a.smoothScrollToPosition(size - 1);
            }
            if (SkillChatItem.Type.RECV == chatList.get(size - 1).getType()) {
                scheduleToClose(DEFAULT_CLOSE_DURATION * 5);
            } else {
                scheduleToClose(DEFAULT_CLOSE_DURATION * 2);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSkillIconDownloadFinishEvent(SkillIconDownloadFinishEvent skillIconDownloadFinishEvent) {
        OpenPlatformAdapter openPlatformAdapter = this.b;
        if (openPlatformAdapter != null) {
            openPlatformAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFarewellEvent(FarewellEvent farewellEvent) {
        this.c = false;
        L.openplatform.i("set isQuitByUserGesture to false, isQuitByVoice %s", Boolean.valueOf(OpenPlatformModel.getInstance().isQuitByVoice()));
        finish();
    }
}
