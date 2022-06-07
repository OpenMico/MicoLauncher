package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.uicontroller.SimpleFutureTask;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/* loaded from: classes4.dex */
public class CaptchaView extends LinearLayout {
    private static final String TAG = "CaptchaView";
    private ImageView mCaptchaImageView;
    private ImageView mCaptchaSwitch;
    private EditText mCodeView;
    private volatile String mIck;
    private SimpleFutureTask<Pair<Bitmap, String>> mImageCaptchaTask;
    private String mImageCaptchaUrl;
    private boolean mIsTallBackAlive;
    private volatile boolean mIsVoiceCaptcha;
    private OnCaptchaSwitchChange mOnCaptchaSwitchChange;
    private SimpleFutureTask<Boolean> mVoiceCaptchaTask;
    private String mVoiceCaptchaUrl;

    /* loaded from: classes4.dex */
    public interface OnCaptchaSwitchChange {
        void update(boolean z);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        SimpleFutureTask<Pair<Bitmap, String>> simpleFutureTask = this.mImageCaptchaTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
        }
        SimpleFutureTask<Boolean> simpleFutureTask2 = this.mVoiceCaptchaTask;
        if (simpleFutureTask2 != null) {
            simpleFutureTask2.cancel(true);
        }
        super.onDetachedFromWindow();
    }

    public CaptchaView(Context context) {
        super(context);
        this.mIsVoiceCaptcha = false;
        init(context);
    }

    public CaptchaView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CaptchaView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsVoiceCaptcha = false;
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.passport_captcha, this);
        this.mCaptchaImageView = (ImageView) inflate.findViewById(R.id.et_captcha_image);
        this.mCaptchaSwitch = (ImageView) inflate.findViewById(R.id.et_switch);
        this.mCodeView = (EditText) inflate.findViewById(R.id.et_captcha_code);
        if (this.mCaptchaSwitch != null) {
            this.mIsTallBackAlive = AccessibilityUtil.isTallBackActive(context);
            this.mCaptchaSwitch.setVisibility(this.mIsTallBackAlive ? 0 : 8);
            this.mCaptchaSwitch.setContentDescription(getResources().getString(R.string.passport_talkback_switch_voice_captcha));
            this.mCaptchaSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Drawable drawable = null;
                    CaptchaView.this.mCodeView.setError(null, null);
                    CaptchaView.this.cancelCaptchaDownloadTask();
                    CaptchaView captchaView = CaptchaView.this;
                    captchaView.mIsVoiceCaptcha = !captchaView.mIsVoiceCaptcha;
                    ImageView imageView = CaptchaView.this.mCaptchaSwitch;
                    CaptchaView captchaView2 = CaptchaView.this;
                    imageView.setImageDrawable(captchaView2.getDrawable(captchaView2.mIsVoiceCaptcha ? R.drawable.passport_ic_captcha_switch_image : R.drawable.passport_ic_captcha_switch_speaker));
                    CaptchaView.this.mCaptchaSwitch.setContentDescription(CaptchaView.this.getResources().getString(CaptchaView.this.mIsVoiceCaptcha ? R.string.passport_talkback_switch_image_captcha : R.string.passport_talkback_switch_voice_captcha));
                    ImageView imageView2 = CaptchaView.this.mCaptchaImageView;
                    if (CaptchaView.this.mIsVoiceCaptcha) {
                        drawable = CaptchaView.this.getDrawable(R.drawable.passport_ic_sound_wave_retry);
                    }
                    imageView2.setImageDrawable(drawable);
                    CaptchaView.this.mCaptchaImageView.setContentDescription(CaptchaView.this.getResources().getString(CaptchaView.this.mIsVoiceCaptcha ? R.string.passport_talkback_voice_captcha : R.string.passport_talkback_image_captcha));
                    if (CaptchaView.this.mOnCaptchaSwitchChange != null) {
                        CaptchaView.this.mOnCaptchaSwitchChange.update(CaptchaView.this.mIsVoiceCaptcha);
                    }
                    CaptchaView.this.mCaptchaImageView.setImageDrawable(CaptchaView.this.mIsVoiceCaptcha ? CaptchaView.this.getDrawable(R.drawable.passport_ic_sound_wave_retry) : CaptchaView.this.getDrawable(R.drawable.passport_ic_captch_retry));
                    CaptchaView.this.mCodeView.setHint(CaptchaView.this.mIsVoiceCaptcha ? R.string.passport_input_voice_captcha_hint : R.string.passport_input_captcha_hint);
                    CaptchaView.this.startDownLoad();
                }
            });
        }
        this.mCaptchaImageView.setContentDescription(getResources().getString(R.string.passport_talkback_image_captcha));
        this.mCaptchaImageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CaptchaView.this.mCodeView.setText((CharSequence) null);
                CaptchaView.this.startDownLoad();
            }
        });
    }

    public String getCaptchaCode() {
        String str;
        String obj = this.mCodeView.getText().toString();
        if (!TextUtils.isEmpty(obj)) {
            return obj;
        }
        this.mCodeView.requestFocus();
        EditText editText = this.mCodeView;
        if (this.mIsVoiceCaptcha) {
            str = getResources().getString(R.string.passport_error_empty_voice_code);
        } else {
            str = getResources().getString(R.string.passport_error_empty_captcha_code);
        }
        editText.setError(str);
        return null;
    }

    public String getCaptchaIck() {
        return this.mIck;
    }

    public void onCaptchaError() {
        String str;
        this.mCodeView.requestFocus();
        EditText editText = this.mCodeView;
        if (this.mIsVoiceCaptcha) {
            str = getResources().getString(R.string.passport_wrong_voice);
        } else {
            str = getResources().getString(R.string.passport_wrong_captcha);
        }
        editText.setError(str);
    }

    public void downloadCaptcha(String str, String str2) {
        this.mVoiceCaptchaUrl = str2;
        this.mImageCaptchaUrl = str;
        this.mCodeView.setText((CharSequence) null);
        startDownLoad();
    }

    public void startDownLoad() {
        if (this.mIsVoiceCaptcha) {
            downloadAndPlayVoiceCaptcha(this.mVoiceCaptchaUrl);
        } else {
            downloadImageCaptcha(this.mImageCaptchaUrl);
        }
    }

    public Drawable getDrawable(int i) {
        return getResources().getDrawable(i);
    }

    public void cancelCaptchaDownloadTask() {
        SimpleFutureTask<Pair<Bitmap, String>> simpleFutureTask = this.mImageCaptchaTask;
        if (simpleFutureTask != null) {
            simpleFutureTask.cancel(true);
            this.mImageCaptchaTask = null;
        }
        SimpleFutureTask<Boolean> simpleFutureTask2 = this.mVoiceCaptchaTask;
        if (simpleFutureTask2 != null) {
            simpleFutureTask2.cancel(true);
            this.mVoiceCaptchaTask = null;
        }
    }

    private void downloadImageCaptcha(final String str) {
        SimpleFutureTask<Pair<Bitmap, String>> simpleFutureTask = this.mImageCaptchaTask;
        if (simpleFutureTask == null || simpleFutureTask.isDone()) {
            final Context applicationContext = getContext().getApplicationContext();
            final int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.passport_captcha_img_w);
            final int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.passport_captcha_img_h);
            this.mImageCaptchaTask = new SimpleFutureTask<>(new Callable<Pair<Bitmap, String>>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.4
                @Override // java.util.concurrent.Callable
                public Pair<Bitmap, String> call() throws Exception {
                    Pair blockingDownloadCaptcha = CaptchaView.this.blockingDownloadCaptcha(applicationContext, str);
                    if (blockingDownloadCaptcha != null) {
                        return Pair.create(CaptchaView.getFixedImageBitmap(((File) blockingDownloadCaptcha.first).getPath(), dimensionPixelSize, dimensionPixelSize2), blockingDownloadCaptcha.second);
                    }
                    AccountLog.e(CaptchaView.TAG, "image captcha result is null");
                    return null;
                }
            }, new SimpleFutureTask.Callback<Pair<Bitmap, String>>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.3
                @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
                public void call(SimpleFutureTask<Pair<Bitmap, String>> simpleFutureTask2) {
                    try {
                        Pair<Bitmap, String> pair = simpleFutureTask2.get();
                        if (pair == null) {
                            Toast.makeText(CaptchaView.this.getContext(), R.string.passport_input_captcha_hint, 1).show();
                            return;
                        }
                        CaptchaView.this.mIck = (String) pair.second;
                        CaptchaView.this.mCaptchaImageView.setImageBitmap((Bitmap) pair.first);
                    } catch (InterruptedException e) {
                        AccountLog.e(CaptchaView.TAG, "downloadCaptchaImage", e);
                    } catch (ExecutionException e2) {
                        AccountLog.e(CaptchaView.TAG, "downloadCaptchaImage", e2);
                    }
                }
            });
            XiaomiPassportExecutor.getSingleton().execute(this.mImageCaptchaTask);
            return;
        }
        AccountLog.w(TAG, "pre image task passport_input_speaker_capcha_hintis doing");
    }

    private void downloadAndPlayVoiceCaptcha(final String str) {
        SimpleFutureTask<Boolean> simpleFutureTask = this.mVoiceCaptchaTask;
        if (simpleFutureTask == null || simpleFutureTask.isDone()) {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.5
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(MediaPlayer mediaPlayer2) {
                    mediaPlayer2.release();
                    if (CaptchaView.this.mIsVoiceCaptcha) {
                        CaptchaView.this.mCaptchaImageView.setImageDrawable(CaptchaView.this.getDrawable(R.drawable.passport_ic_sound_wave_retry));
                    }
                }
            });
            this.mVoiceCaptchaTask = new SimpleFutureTask<>(new Callable<Boolean>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.7
                @Override // java.util.concurrent.Callable
                public Boolean call() throws Exception {
                    Context applicationContext = CaptchaView.this.getContext().getApplicationContext();
                    Pair blockingDownloadCaptcha = CaptchaView.this.blockingDownloadCaptcha(applicationContext, str);
                    if (blockingDownloadCaptcha == null) {
                        AccountLog.w(CaptchaView.TAG, "speaker captcha null");
                        return false;
                    }
                    mediaPlayer.setDataSource(applicationContext, Uri.fromFile((File) blockingDownloadCaptcha.first));
                    mediaPlayer.prepare();
                    CaptchaView.this.mIck = (String) blockingDownloadCaptcha.second;
                    return true;
                }
            }, new SimpleFutureTask.Callback<Boolean>() { // from class: com.xiaomi.passport.ui.settings.CaptchaView.6
                @Override // com.xiaomi.passport.uicontroller.SimpleFutureTask.Callback
                public void call(SimpleFutureTask<Boolean> simpleFutureTask2) {
                    try {
                        try {
                            boolean booleanValue = simpleFutureTask2.get().booleanValue();
                            if (booleanValue) {
                                CaptchaView.this.mCaptchaImageView.setImageDrawable(CaptchaView.this.getDrawable(R.drawable.passport_ic_sound_wave));
                                mediaPlayer.start();
                            } else {
                                Toast.makeText(CaptchaView.this.getContext(), R.string.passport_input_voice_captcha_hint, 1).show();
                            }
                            if (booleanValue) {
                                return;
                            }
                        } catch (InterruptedException e) {
                            AccountLog.e(CaptchaView.TAG, "downloadSpeakerCaptcha", e);
                            if (0 != 0) {
                                return;
                            }
                        } catch (ExecutionException e2) {
                            AccountLog.e(CaptchaView.TAG, "downloadSpeakerCaptcha", e2);
                            if (0 != 0) {
                                return;
                            }
                        }
                        mediaPlayer.release();
                    } catch (Throwable th) {
                        if (0 == 0) {
                            mediaPlayer.release();
                        }
                        throw th;
                    }
                }
            });
            XiaomiPassportExecutor.getSingleton().execute(this.mVoiceCaptchaTask);
            return;
        }
        AccountLog.w(TAG, "pre speaker task is doing");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0023 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0024 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.util.Pair<java.io.File, java.lang.String> blockingDownloadCaptcha(android.content.Context r4, java.lang.String r5) {
        /*
            r3 = this;
            r0 = 0
            com.xiaomi.accountsdk.request.SimpleRequest$StreamContent r5 = com.xiaomi.accountsdk.request.SimpleRequestForAccount.getAsStream(r5, r0, r0)     // Catch: IOException -> 0x0018, AccessDeniedException -> 0x000f, AuthenticationFailureException -> 0x0006
            goto L_0x0021
        L_0x0006:
            r5 = move-exception
            java.lang.String r1 = "CaptchaView"
            java.lang.String r2 = "getCaptcha"
            com.xiaomi.accountsdk.utils.AccountLog.w(r1, r2, r5)
            goto L_0x0020
        L_0x000f:
            r5 = move-exception
            java.lang.String r1 = "CaptchaView"
            java.lang.String r2 = "getCaptcha"
            com.xiaomi.accountsdk.utils.AccountLog.w(r1, r2, r5)
            goto L_0x0020
        L_0x0018:
            r5 = move-exception
            java.lang.String r1 = "CaptchaView"
            java.lang.String r2 = "getCaptcha"
            com.xiaomi.accountsdk.utils.AccountLog.w(r1, r2, r5)
        L_0x0020:
            r5 = r0
        L_0x0021:
            if (r5 != 0) goto L_0x0024
            return r0
        L_0x0024:
            java.io.InputStream r1 = r5.getStream()     // Catch: IOException -> 0x003e, all -> 0x003c
            java.lang.String r2 = "captcha"
            java.io.File r4 = com.xiaomi.passport.ui.internal.util.BitmapUtils.saveAsFile(r4, r1, r2)     // Catch: IOException -> 0x003e, all -> 0x003c
            java.lang.String r1 = "ick"
            java.lang.String r1 = r5.getHeader(r1)     // Catch: IOException -> 0x003e, all -> 0x003c
            android.util.Pair r4 = android.util.Pair.create(r4, r1)     // Catch: IOException -> 0x003e, all -> 0x003c
            r5.closeStream()
            return r4
        L_0x003c:
            r4 = move-exception
            goto L_0x004a
        L_0x003e:
            r4 = move-exception
            java.lang.String r1 = "CaptchaView"
            java.lang.String r2 = "getCaptcha"
            com.xiaomi.accountsdk.utils.AccountLog.w(r1, r2, r4)     // Catch: all -> 0x003c
            r5.closeStream()
            return r0
        L_0x004a:
            r5.closeStream()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.ui.settings.CaptchaView.blockingDownloadCaptcha(android.content.Context, java.lang.String):android.util.Pair");
    }

    public static Bitmap getFixedImageBitmap(String str, int i, int i2) {
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeFile, i, i2, true);
        if (decodeFile != createScaledBitmap) {
            decodeFile.recycle();
        }
        return createScaledBitmap;
    }

    public void setOnCaptchaSwitchChange(OnCaptchaSwitchChange onCaptchaSwitchChange) {
        this.mOnCaptchaSwitchChange = onCaptchaSwitchChange;
    }
}
