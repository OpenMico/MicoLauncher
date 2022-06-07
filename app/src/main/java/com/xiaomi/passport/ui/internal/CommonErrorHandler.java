package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.umeng.analytics.pro.c;
import com.xiaomi.account.diagnosis.task.CollectAndUploadDiagnosisTask;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.R;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Utils.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\f\u0018\u0000 02\u00020\u0001:\u00010B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u0016\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0002JV\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000b26\u0010\u001e\u001a2\u0012\u0013\u0012\u00110\r¢\u0006\f\b \u0012\b\b!\u0012\u0004\b\b(\"\u0012\u0013\u0012\u00110\r¢\u0006\f\b \u0012\b\b!\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u000f0\u001fJ \u0010$\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020%2\u0006\u0010&\u001a\u00020\rH\u0002J \u0010'\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010(\u001a\u00020%H\u0002J\u0018\u0010)\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\rH\u0002J\u0018\u0010*\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010+\u001a\u00020%H\u0002J\u0018\u0010,\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u001a\u0010-\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u0018\u0010.\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0018\u0010/\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u00061"}, d2 = {"Lcom/xiaomi/passport/ui/internal/CommonErrorHandler;", "", "()V", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "getPassportRepo", "()Lcom/xiaomi/passport/ui/internal/PassportRepo;", "setPassportRepo", "(Lcom/xiaomi/passport/ui/internal/PassportRepo;)V", "getCaptcha", "Lcom/xiaomi/passport/ui/internal/Source;", "Lcom/xiaomi/passport/ui/internal/Captcha;", "url", "", "onIOError", "", "e", "Ljava/io/IOException;", c.R, "Landroid/content/Context;", OneTrack.Event.VIEW, "Landroid/view/View;", "onUnKnowError", ServerSetting.SERVER_TK, "", "showBigTh", "showCaptcha", "layoutInflater", "Landroid/view/LayoutInflater;", "captcha", "callback", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "code", "ick", "showError", "", "msg", "showErrorWithLog", "msgId", "showLittleTh", "showMidTh", "resId", "showUnknownError", "showUnknownHostException", "showUnknownIOExceptionWithLog", "uploadLog", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class CommonErrorHandler {
    public static final Companion Companion = new Companion(null);
    @NotNull
    private PassportRepo passportRepo = new PassportRepoImpl();

    @NotNull
    public final PassportRepo getPassportRepo() {
        return this.passportRepo;
    }

    public final void setPassportRepo(@NotNull PassportRepo passportRepo) {
        Intrinsics.checkParameterIsNotNull(passportRepo, "<set-?>");
        this.passportRepo = passportRepo;
    }

    public final void onIOError(IOException iOException, Context context) {
        onIOError(iOException, context, null);
    }

    public final void onIOError(@NotNull IOException e, @NotNull Context context, @Nullable View view) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (e instanceof UnknownHostException) {
            showUnknownHostException(context, view);
        } else if (e instanceof SocketTimeoutException) {
            showErrorWithLog(context, e, R.string.passport_timeout_network_error);
        } else {
            showUnknownIOExceptionWithLog(context, e);
        }
    }

    public final void onUnKnowError(@NotNull Throwable tr, @NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(tr, "tr");
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (tr instanceof RuntimeException) {
            throw tr;
        } else if (!(tr instanceof Error)) {
            showUnknownError(context, tr);
        } else {
            throw tr;
        }
    }

    private final void showUnknownIOExceptionWithLog(Context context, IOException iOException) {
        showErrorWithLog(context, iOException, R.string.passport_unknow_network_error);
    }

    private final void showUnknownHostException(Context context, View view) {
        if (view != null) {
            String string = context.getString(R.string.passport_unknow_host_network_error);
            Intrinsics.checkExpressionValueIsNotNull(string, "context.getString(R.stri…nknow_host_network_error)");
            showLittleTh(view, string);
            return;
        }
        showMidTh(context, R.string.passport_unknow_host_network_error);
    }

    private final void showUnknownError(Context context, Throwable th) {
        if (th instanceof InvalidResponseException) {
            InvalidResponseException invalidResponseException = (InvalidResponseException) th;
            if (invalidResponseException.code == 10031) {
                int i = invalidResponseException.code;
                String str = invalidResponseException.codeDesc;
                Intrinsics.checkExpressionValueIsNotNull(str, "tr.codeDesc");
                showError(context, i, str);
                return;
            }
            ServerError serverError = invalidResponseException.getServerError();
            if (serverError != null) {
                Companion.showErrorWithTips(context, serverError);
                return;
            }
        }
        showErrorWithLog(context, th, R.string.passport_unknow_error);
    }

    private final void showErrorWithLog(final Context context, final Throwable th, int i) {
        AlertDialog create = new AlertDialog.Builder(context).setTitle(i).setMessage(th.toString()).setNegativeButton(R.string.upload_error_log, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.CommonErrorHandler$showErrorWithLog$dialog$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CommonErrorHandler.this.uploadLog(context, th);
            }
        }).setPositiveButton(R.string.passport_log_detail, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.CommonErrorHandler$showErrorWithLog$dialog$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CommonErrorHandler.this.showBigTh(context, th);
            }
        }).create();
        boolean z = false;
        create.setCanceledOnTouchOutside(false);
        create.setCancelable(false);
        if ((context instanceof Activity) && ((Activity) context).isFinishing()) {
            z = true;
        }
        if (!z) {
            create.show();
            View findViewById = create.findViewById(16908299);
            if (findViewById != null) {
                TextView textView = (TextView) findViewById;
                textView.setTextSize(2, 10.0f);
                textView.setTextIsSelectable(true);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        }
    }

    /* compiled from: Utils.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/xiaomi/passport/ui/internal/CommonErrorHandler$Companion;", "", "()V", "showErrorWithTips", "", c.R, "Landroid/content/Context;", "serverError", "Lcom/xiaomi/accountsdk/account/ServerError;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void showErrorWithTips(@NotNull Context context, @NotNull ServerError serverError) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(serverError, "serverError");
            if (serverError.getTips() != null) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.server_error_with_tips_dialog, (ViewGroup) null);
                View findViewById = inflate.findViewById(R.id.msg);
                if (findViewById != null) {
                    TextView textView = (TextView) findViewById;
                    textView.setLinksClickable(true);
                    textView.setText(Html.fromHtml(serverError.getTips()));
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    new AlertDialog.Builder(context).setTitle(serverError.getTitle()).setView(inflate).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create().show();
                    return;
                }
                throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
            }
        }
    }

    private final void showError(Context context, int i, String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("" + str + " (" + i + ')').setPositiveButton(17039370, CommonErrorHandler$showError$dialog$1.INSTANCE).create().show();
    }

    private final void showLittleTh(View view, String str) {
        Snackbar.make(view, str, 0).show();
    }

    private final void showMidTh(Context context, int i) {
        if ((context instanceof Activity) && !((Activity) context).isFinishing()) {
            new AlertDialog.Builder(context).setMessage(i).create().show();
        }
    }

    public final void showBigTh(final Context context, final Throwable th) {
        AlertDialog create = new AlertDialog.Builder(context).setMessage(Log.getStackTraceString(th)).setPositiveButton(R.string.passport_close, (DialogInterface.OnClickListener) null).setNegativeButton(R.string.upload_error_log, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.CommonErrorHandler$showBigTh$dialog$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CommonErrorHandler.this.uploadLog(context, th);
            }
        }).setCancelable(false).create();
        create.setCanceledOnTouchOutside(false);
        create.show();
        View findViewById = create.findViewById(16908299);
        if (findViewById != null) {
            TextView textView = (TextView) findViewById;
            textView.setTextSize(2, 9.0f);
            textView.setTextIsSelectable(true);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
    }

    public final void uploadLog(final Context context, Throwable th) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.sending));
        progressDialog.setCancelable(false);
        progressDialog.show();
        new CollectAndUploadDiagnosisTask(new CollectAndUploadDiagnosisTask.Callback() { // from class: com.xiaomi.passport.ui.internal.CommonErrorHandler$uploadLog$task$1
            @Override // com.xiaomi.account.diagnosis.task.CollectAndUploadDiagnosisTask.Callback
            public final void onFinished(boolean z, String str) {
                Context context2 = context;
                if ((context2 instanceof Activity) && !((Activity) context2).isFinishing()) {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    if (!z || TextUtils.isEmpty(str)) {
                        builder.setMessage(context.getString(R.string.diagnosis_log_send_failed));
                    } else {
                        builder.setMessage(context.getString(R.string.diagnosis_log_sent_format, str));
                    }
                    builder.setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        }, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.lang.String] */
    public final void showCaptcha(@NotNull final Context context, @NotNull LayoutInflater layoutInflater, @NotNull final Captcha captcha, @NotNull final Function2<? super String, ? super String, Unit> callback) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(layoutInflater, "layoutInflater");
        Intrinsics.checkParameterIsNotNull(captcha, "captcha");
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        View inflate = layoutInflater.inflate(R.layout.dg_captcha_layout, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.captcha_input);
        if (findViewById != null) {
            final EditText editText = (EditText) findViewById;
            View findViewById2 = inflate.findViewById(R.id.captcha_image);
            if (findViewById2 != null) {
                final ImageView imageView = (ImageView) findViewById2;
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                objectRef.element = captcha.getIck();
                imageView.setImageBitmap(captcha.getBitmap());
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.CommonErrorHandler$showCaptcha$1

                    /* compiled from: Utils.kt */
                    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/passport/ui/internal/Captcha;", "invoke"}, k = 3, mv = {1, 1, 10})
                    /* renamed from: com.xiaomi.passport.ui.internal.CommonErrorHandler$showCaptcha$1$1  reason: invalid class name */
                    /* loaded from: classes4.dex */
                    static final class AnonymousClass1 extends Lambda implements Function1<Captcha, Unit> {
                        AnonymousClass1() {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Captcha captcha) {
                            invoke2(captcha);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Type inference failed for: r3v1, types: [T, java.lang.String] */
                        /* renamed from: invoke  reason: avoid collision after fix types in other method */
                        public final void invoke2(@NotNull Captcha it) {
                            Intrinsics.checkParameterIsNotNull(it, "it");
                            imageView.setImageBitmap(it.getBitmap());
                            objectRef.element = it.getIck();
                        }
                    }

                    /* compiled from: Utils.kt */
                    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 10})
                    /* renamed from: com.xiaomi.passport.ui.internal.CommonErrorHandler$showCaptcha$1$2  reason: invalid class name */
                    /* loaded from: classes4.dex */
                    static final class AnonymousClass2 extends Lambda implements Function1<Throwable, Unit> {
                        AnonymousClass2() {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                            invoke2(th);
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke  reason: avoid collision after fix types in other method */
                        public final void invoke2(@NotNull Throwable it) {
                            Intrinsics.checkParameterIsNotNull(it, "it");
                            AccountLog.e("Passport", "captcha", it);
                            if (it instanceof IOException) {
                                CommonErrorHandler.this.onIOError((IOException) it, context);
                            } else {
                                CommonErrorHandler.this.onUnKnowError(it, context);
                            }
                        }
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        Source captcha2;
                        captcha2 = CommonErrorHandler.this.getCaptcha(captcha.getCaptchaUrl());
                        captcha2.get(new AnonymousClass1(), new AnonymousClass2());
                    }
                });
                new AlertDialog.Builder(context).setTitle(R.string.passport_captcha_title).setView(inflate).setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.CommonErrorHandler$showCaptcha$dialog$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(@NotNull DialogInterface dialogInterface, int i) {
                        Intrinsics.checkParameterIsNotNull(dialogInterface, "<anonymous parameter 0>");
                        Function2.this.invoke(editText.getText().toString(), (String) objectRef.element);
                    }
                }).create().show();
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.widget.ImageView");
        }
        throw new TypeCastException("null cannot be cast to non-null type android.widget.EditText");
    }

    public final Source<Captcha> getCaptcha(String str) {
        return this.passportRepo.getCaptchaImage(str);
    }
}
