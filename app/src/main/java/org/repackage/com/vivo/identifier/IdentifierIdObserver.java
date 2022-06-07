package org.repackage.com.vivo.identifier;

import android.database.ContentObserver;
import android.util.Log;

/* loaded from: classes3.dex */
public class IdentifierIdObserver extends ContentObserver {
    private String a;
    private int b;
    private IdentifierIdClient c;

    public IdentifierIdObserver(IdentifierIdClient identifierIdClient, int i, String str) {
        super(null);
        this.c = identifierIdClient;
        this.b = i;
        this.a = str;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        IdentifierIdClient identifierIdClient = this.c;
        if (identifierIdClient != null) {
            identifierIdClient.a(this.b, this.a);
        } else {
            Log.e("VMS_IDLG_SDK_Observer", "mIdentifierIdClient is null");
        }
    }
}
