package androidx.core.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.core.os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
/* loaded from: classes.dex */
public class FingerprintManagerCompat {
    private final Context a;

    /* loaded from: classes.dex */
    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    @NonNull
    public static FingerprintManagerCompat from(@NonNull Context context) {
        return new FingerprintManagerCompat(context);
    }

    private FingerprintManagerCompat(Context context) {
        this.a = context;
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public boolean hasEnrolledFingerprints() {
        FingerprintManager a;
        return Build.VERSION.SDK_INT >= 23 && (a = a(this.a)) != null && a.hasEnrolledFingerprints();
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public boolean isHardwareDetected() {
        FingerprintManager a;
        return Build.VERSION.SDK_INT >= 23 && (a = a(this.a)) != null && a.isHardwareDetected();
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public void authenticate(@Nullable CryptoObject cryptoObject, int i, @Nullable CancellationSignal cancellationSignal, @NonNull AuthenticationCallback authenticationCallback, @Nullable Handler handler) {
        FingerprintManager a;
        if (Build.VERSION.SDK_INT >= 23 && (a = a(this.a)) != null) {
            a.authenticate(a(cryptoObject), cancellationSignal != null ? (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject() : null, i, a(authenticationCallback), handler);
        }
    }

    @Nullable
    @RequiresApi(23)
    private static FingerprintManager a(@NonNull Context context) {
        if (Build.VERSION.SDK_INT == 23) {
            return (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }
        if (Build.VERSION.SDK_INT <= 23 || !context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return null;
        }
        return (FingerprintManager) context.getSystemService(FingerprintManager.class);
    }

    @RequiresApi(23)
    private static FingerprintManager.CryptoObject a(CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new FingerprintManager.CryptoObject(cryptoObject.getMac());
        }
        return null;
    }

    @RequiresApi(23)
    static CryptoObject a(FingerprintManager.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new CryptoObject(cryptoObject.getMac());
        }
        return null;
    }

    @RequiresApi(23)
    private static FingerprintManager.AuthenticationCallback a(final AuthenticationCallback authenticationCallback) {
        return new FingerprintManager.AuthenticationCallback() { // from class: androidx.core.hardware.fingerprint.FingerprintManagerCompat.1
            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationError(int i, CharSequence charSequence) {
                AuthenticationCallback.this.onAuthenticationError(i, charSequence);
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationHelp(int i, CharSequence charSequence) {
                AuthenticationCallback.this.onAuthenticationHelp(i, charSequence);
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
                AuthenticationCallback.this.onAuthenticationSucceeded(new AuthenticationResult(FingerprintManagerCompat.a(authenticationResult.getCryptoObject())));
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationFailed() {
                AuthenticationCallback.this.onAuthenticationFailed();
            }
        };
    }

    /* loaded from: classes.dex */
    public static class CryptoObject {
        private final Signature a;
        private final Cipher b;
        private final Mac c;

        public CryptoObject(@NonNull Signature signature) {
            this.a = signature;
            this.b = null;
            this.c = null;
        }

        public CryptoObject(@NonNull Cipher cipher) {
            this.b = cipher;
            this.a = null;
            this.c = null;
        }

        public CryptoObject(@NonNull Mac mac) {
            this.c = mac;
            this.b = null;
            this.a = null;
        }

        @Nullable
        public Signature getSignature() {
            return this.a;
        }

        @Nullable
        public Cipher getCipher() {
            return this.b;
        }

        @Nullable
        public Mac getMac() {
            return this.c;
        }
    }

    /* loaded from: classes.dex */
    public static final class AuthenticationResult {
        private final CryptoObject a;

        public AuthenticationResult(CryptoObject cryptoObject) {
            this.a = cryptoObject;
        }

        public CryptoObject getCryptoObject() {
            return this.a;
        }
    }
}
