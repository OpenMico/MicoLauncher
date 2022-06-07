package com.xiaomi.passport.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.settings.utils.PermissionRequestHistory;
import com.xiaomi.passport.ui.settings.utils.PermissionUtils;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.io.File;

/* loaded from: classes4.dex */
public class UserAvatarUpdateFragment extends Fragment {
    private static final String AVATAR_FILE_NAME = "xiaomi_user_avatar_file";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final String FILE_PROVIDER_AUTHORITY_SUFFIX = ".passport.fileprovider";
    private static final int PERMISSION_SETTINGS_REQUEST_CODE = 2000;
    private static final int REQUEST_CROP_PHOTO = 1004;
    private static final int REQUEST_PICK_PHOTO_FROM_GALLERY = 1003;
    private static final int REQUEST_TAKE_PHOTO = 1002;
    private static final String TAG = "UserAvatarUpdateFragment";
    private File mAvatarCachedFile;
    private Uri mFileProviderUri;
    private UploadUserAvatarTask mUploadAvatarTask;

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getActivity().getPackageManager().hasSystemFeature("android.hardware.camera")) {
            AccountLog.i(TAG, "has camera");
        } else {
            AccountLog.i(TAG, "no camera");
        }
        String string = getArguments().getString(UserAvatarUpdateActivity.EXTRA_UPDATE_AVATAR_TYPE);
        if (UserAvatarUpdateActivity.CAMERA.equals(string)) {
            checkCameraPermissionAndTakePhoto();
        } else if (UserAvatarUpdateActivity.GALLERY.equals(string)) {
            startPickPhotoFromGallery();
        } else {
            finishActivity();
        }
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (MiAccountManager.get(getActivity()).getXiaomiAccount() == null) {
            AccountLog.w(TAG, "no xiaomi account");
            finishActivity();
        }
        View findViewById = getActivity().findViewById(16908290);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.UserAvatarUpdateFragment.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    UserAvatarUpdateFragment.this.finishActivity();
                }
            });
        }
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100) {
            PermissionRequestHistory.setPermissionRequested(getActivity(), "android.permission.CAMERA");
            if (iArr[0] == 0) {
                startTakePhoto();
            } else {
                finishActivity();
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Uri uri = null;
        boolean z = false;
        switch (i) {
            case 1002:
            case 1003:
                if (i2 == -1) {
                    if (intent != null) {
                        uri = intent.getData();
                    }
                    if (uri == null) {
                        uri = getFileProviderUri();
                    }
                    doCropPhoto(uri);
                    z = true;
                    break;
                }
                break;
            case 1004:
                if (intent != null && intent.getExtras() != null) {
                    Object obj = intent.getExtras().get("data");
                    if (obj instanceof Bitmap) {
                        startUploadAvatar((Bitmap) obj);
                    }
                } else if (i2 == -1) {
                    startUploadAvatar(null);
                }
                z = true;
                break;
            default:
                z = true;
                break;
        }
        if (!z) {
            finishActivity();
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        UploadUserAvatarTask uploadUserAvatarTask = this.mUploadAvatarTask;
        if (uploadUserAvatarTask != null) {
            uploadUserAvatarTask.cancel(true);
            this.mUploadAvatarTask = null;
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRequestCameraPermission() {
        Activity activity = getActivity();
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 100);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.CAMERA"}, 100);
        }
    }

    private void checkCameraPermissionAndTakePhoto() {
        final Activity activity = getActivity();
        if (ContextCompat.checkSelfPermission(activity, "android.permission.CAMERA") != 0) {
            int[] iArr = {R.string.request_camera_permission_message, R.string.i_know, R.string.open_settings};
            if (canShowPermissionPrompt(iArr)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(Html.fromHtml(getString(iArr[0])));
                builder.setPositiveButton(iArr[1], (DialogInterface.OnClickListener) null);
                if (PermissionRequestHistory.isPermissionPermanentlyDenied(activity, "android.permission.CAMERA")) {
                    builder.setNegativeButton(iArr[2], new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.UserAvatarUpdateFragment.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PermissionUtils.openPermissionSetting(activity, 2000);
                        }
                    });
                }
                builder.setCancelable(false);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaomi.passport.ui.settings.UserAvatarUpdateFragment.3
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        UserAvatarUpdateFragment.this.startRequestCameraPermission();
                    }
                });
                builder.show();
                return;
            }
            startRequestCameraPermission();
            return;
        }
        startTakePhoto();
    }

    private boolean canShowPermissionPrompt(int[] iArr) {
        for (int i : iArr) {
            try {
                getString(i);
            } catch (Resources.NotFoundException unused) {
                return false;
            }
        }
        return true;
    }

    private void startTakePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", getFileProviderUri());
        intent.addFlags(2);
        startActivityForResult(intent, 1002);
    }

    private void startPickPhotoFromGallery() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, 1003);
    }

    private void doCropPhoto(Uri uri) {
        if (uri == null) {
            AccountLog.i(TAG, "inputUri is null");
            return;
        }
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri fileProviderUri = getFileProviderUri();
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("output", fileProviderUri);
            intent.addFlags(1);
            intent.putExtra("return-data", true);
            Activity activity = getActivity();
            for (ResolveInfo resolveInfo : activity.getPackageManager().queryIntentActivities(intent, 0)) {
                String str = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(str, uri, 1);
                activity.grantUriPermission(str, fileProviderUri, 2);
            }
            intent.putExtra(SkillStore.SkillDetailSection.TYPE_TIPS, getString(R.string.account_crop_user_avatar));
            addCropExtras(intent, getPhotoPickSize());
            startActivityForResult(intent, 1004);
        } catch (Exception e) {
            AccountLog.e(TAG, "Cannot crop image", e);
            Toast.makeText(getActivity(), R.string.photoPickerNotFoundText, 1).show();
        }
    }

    private static void addCropExtras(Intent intent, int i) {
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", i);
        intent.putExtra("outputY", i);
    }

    private int getPhotoPickSize() {
        return getResources().getDimensionPixelSize(R.dimen.upload_user_avatar_size);
    }

    private Uri getFileProviderUri() {
        if (this.mFileProviderUri == null) {
            this.mFileProviderUri = FileProvider.getUriForFile(getActivity(), getCurrentPackageAuthority(), getFileProviderFile());
        }
        return this.mFileProviderUri;
    }

    private String getCurrentPackageAuthority() {
        return getActivity().getPackageName() + FILE_PROVIDER_AUTHORITY_SUFFIX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getFileProviderFile() {
        if (this.mAvatarCachedFile == null) {
            this.mAvatarCachedFile = new File(getActivity().getCacheDir(), AVATAR_FILE_NAME);
        }
        return this.mAvatarCachedFile;
    }

    private void startUploadAvatar(Bitmap bitmap) {
        UploadUserAvatarTask uploadUserAvatarTask = this.mUploadAvatarTask;
        if (uploadUserAvatarTask != null) {
            uploadUserAvatarTask.cancel(true);
            this.mUploadAvatarTask = null;
        }
        this.mUploadAvatarTask = new UploadUserAvatarTask(getActivity(), bitmap);
        this.mUploadAvatarTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class UploadUserAvatarTask extends AsyncTask<Void, Void, UploadAvatarTaskResult> {
        private final Bitmap mBitmap;
        private Context mContext;
        private ProgressDialog mProgressDialog;

        UploadUserAvatarTask(Context context, Bitmap bitmap) {
            this.mBitmap = bitmap;
            this.mContext = context.getApplicationContext();
            this.mProgressDialog = new ProgressDialog(context);
            this.mProgressDialog.setMessage(UserAvatarUpdateFragment.this.getString(R.string.user_avatar_uploading));
            this.mProgressDialog.setCanceledOnTouchOutside(false);
            this.mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaomi.passport.ui.settings.UserAvatarUpdateFragment.UploadUserAvatarTask.1
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    UploadUserAvatarTask.this.cancel(true);
                }
            });
            this.mProgressDialog.show();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Removed duplicated region for block: B:94:0x0160 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public com.xiaomi.passport.ui.settings.UserAvatarUpdateFragment.UploadAvatarTaskResult doInBackground(java.lang.Void... r12) {
            /*
                Method dump skipped, instructions count: 381
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.ui.settings.UserAvatarUpdateFragment.UploadUserAvatarTask.doInBackground(java.lang.Void[]):com.xiaomi.passport.ui.settings.UserAvatarUpdateFragment$UploadAvatarTaskResult");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(UploadAvatarTaskResult uploadAvatarTaskResult) {
            super.onPostExecute((UploadUserAvatarTask) uploadAvatarTaskResult);
            UserAvatarUpdateFragment.this.finishActivity();
            this.mProgressDialog.dismiss();
            if (uploadAvatarTaskResult != null && uploadAvatarTaskResult.bitmap == null) {
                Toast.makeText(this.mContext, uploadAvatarTaskResult.errorMsgResId == -1 ? R.string.passport_error_unknown : uploadAvatarTaskResult.errorMsgResId, 0).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onCancelled(UploadAvatarTaskResult uploadAvatarTaskResult) {
            if (!(uploadAvatarTaskResult == null || uploadAvatarTaskResult.bitmap == null)) {
                uploadAvatarTaskResult.bitmap.recycle();
            }
            super.onCancelled((UploadUserAvatarTask) uploadAvatarTaskResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishActivity() {
        getActivity().overridePendingTransition(0, 0);
        getActivity().finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class UploadAvatarTaskResult {
        public Bitmap bitmap;
        int errorMsgResId;

        UploadAvatarTaskResult(int i, Bitmap bitmap) {
            this.errorMsgResId = i;
            this.bitmap = bitmap;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFile(File file) {
        if (file != null && file.exists() && file.isFile()) {
            file.delete();
        }
    }
}
