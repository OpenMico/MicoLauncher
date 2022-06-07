package com.xiaomi.micolauncher.common.schema;

import android.content.Intent;
import android.os.Environment;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes3.dex */
public class TokenActionSchemaHandleHelper {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Intent intent) {
        File file = new File(Environment.getExternalStorageDirectory(), "micoServiceToken.txt");
        if (intent.getBooleanExtra("refresh", true)) {
            TokenManager.getInstance().refreshServiceToken(Constants.MICO_SID);
        }
        ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken(Constants.MICO_SID);
        if (blockGetServiceToken != null) {
            String str = blockGetServiceToken.serviceToken;
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(str);
                fileWriter.close();
            } catch (IOException e) {
                Logger logger = L.debug;
                logger.e("Failed to write service token to file " + file, e);
            }
        }
    }
}
