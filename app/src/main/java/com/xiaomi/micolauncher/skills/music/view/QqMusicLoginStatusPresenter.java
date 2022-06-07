package com.xiaomi.micolauncher.skills.music.view;

import android.view.View;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.skills.music.view.QqMusicLoginStatusPresenter;

/* loaded from: classes3.dex */
public class QqMusicLoginStatusPresenter {
    private final TextView a;
    private View b;
    private View c;

    /* loaded from: classes3.dex */
    public interface OnCancelBtnClickListener {
        void onCancelBtnClick();
    }

    public QqMusicLoginStatusPresenter(View view, View view2, final OnCancelBtnClickListener onCancelBtnClickListener) {
        this.c = view;
        this.b = view2;
        this.a = (TextView) view.findViewById(R.id.auth_status_desc);
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$QqMusicLoginStatusPresenter$8v5RxrDvkmAjCK9kByzXR1XUNTg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                QqMusicLoginStatusPresenter.OnCancelBtnClickListener.this.onCancelBtnClick();
            }
        });
    }

    public void showLoginStatus(Throwable th) {
        this.c.setVisibility(0);
        this.b.setVisibility(8);
        if (th instanceof MusicDataManager.QQExpiredException) {
            this.a.setText(R.string.qq_music_auth_expired);
        } else if (th instanceof MusicDataManager.QQNotBindedException) {
            this.a.setText(R.string.qq_music_auth_unbind);
        }
    }
}
