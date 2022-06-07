package androidx.work.impl.model;

import android.os.Build;
import androidx.room.TypeConverter;
import androidx.work.BackoffPolicy;
import androidx.work.ContentUriTriggers;
import androidx.work.NetworkType;
import androidx.work.WorkInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

/* loaded from: classes.dex */
public class WorkTypeConverters {

    /* loaded from: classes.dex */
    public interface BackoffPolicyIds {
        public static final int EXPONENTIAL = 0;
        public static final int LINEAR = 1;
    }

    /* loaded from: classes.dex */
    public interface NetworkTypeIds {
        public static final int CONNECTED = 1;
        public static final int METERED = 4;
        public static final int NOT_REQUIRED = 0;
        public static final int NOT_ROAMING = 3;
        public static final int TEMPORARILY_UNMETERED = 5;
        public static final int UNMETERED = 2;
    }

    /* loaded from: classes.dex */
    public interface StateIds {
        public static final int BLOCKED = 4;
        public static final int CANCELLED = 5;
        public static final String COMPLETED_STATES = "(2, 3, 5)";
        public static final int ENQUEUED = 0;
        public static final int FAILED = 3;
        public static final int RUNNING = 1;
        public static final int SUCCEEDED = 2;
    }

    @TypeConverter
    public static int stateToInt(WorkInfo.State state) {
        switch (state) {
            case ENQUEUED:
                return 0;
            case RUNNING:
                return 1;
            case SUCCEEDED:
                return 2;
            case FAILED:
                return 3;
            case BLOCKED:
                return 4;
            case CANCELLED:
                return 5;
            default:
                throw new IllegalArgumentException("Could not convert " + state + " to int");
        }
    }

    @TypeConverter
    public static WorkInfo.State intToState(int i) {
        switch (i) {
            case 0:
                return WorkInfo.State.ENQUEUED;
            case 1:
                return WorkInfo.State.RUNNING;
            case 2:
                return WorkInfo.State.SUCCEEDED;
            case 3:
                return WorkInfo.State.FAILED;
            case 4:
                return WorkInfo.State.BLOCKED;
            case 5:
                return WorkInfo.State.CANCELLED;
            default:
                throw new IllegalArgumentException("Could not convert " + i + " to State");
        }
    }

    @TypeConverter
    public static int backoffPolicyToInt(BackoffPolicy backoffPolicy) {
        switch (backoffPolicy) {
            case EXPONENTIAL:
                return 0;
            case LINEAR:
                return 1;
            default:
                throw new IllegalArgumentException("Could not convert " + backoffPolicy + " to int");
        }
    }

    @TypeConverter
    public static BackoffPolicy intToBackoffPolicy(int i) {
        switch (i) {
            case 0:
                return BackoffPolicy.EXPONENTIAL;
            case 1:
                return BackoffPolicy.LINEAR;
            default:
                throw new IllegalArgumentException("Could not convert " + i + " to BackoffPolicy");
        }
    }

    @TypeConverter
    public static int networkTypeToInt(NetworkType networkType) {
        switch (networkType) {
            case NOT_REQUIRED:
                return 0;
            case CONNECTED:
                return 1;
            case UNMETERED:
                return 2;
            case NOT_ROAMING:
                return 3;
            case METERED:
                return 4;
            default:
                if (Build.VERSION.SDK_INT >= 30 && networkType == NetworkType.TEMPORARILY_UNMETERED) {
                    return 5;
                }
                throw new IllegalArgumentException("Could not convert " + networkType + " to int");
        }
    }

    @TypeConverter
    public static NetworkType intToNetworkType(int i) {
        switch (i) {
            case 0:
                return NetworkType.NOT_REQUIRED;
            case 1:
                return NetworkType.CONNECTED;
            case 2:
                return NetworkType.UNMETERED;
            case 3:
                return NetworkType.NOT_ROAMING;
            case 4:
                return NetworkType.METERED;
            default:
                if (Build.VERSION.SDK_INT >= 30 && i == 5) {
                    return NetworkType.TEMPORARILY_UNMETERED;
                }
                throw new IllegalArgumentException("Could not convert " + i + " to NetworkType");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [int] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    @TypeConverter
    public static byte[] contentUriTriggersToByteArray(ContentUriTriggers contentUriTriggers) {
        Throwable th;
        IOException e;
        boolean hasNext;
        ByteArrayOutputStream size = contentUriTriggers.size();
        ObjectOutputStream objectOutputStream = null;
        ObjectOutputStream objectOutputStream2 = null;
        objectOutputStream = null;
        if (size == 0) {
            return null;
        }
        try {
            try {
                size = new ByteArrayOutputStream();
                try {
                    objectOutputStream = new ObjectOutputStream(size);
                } catch (IOException e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            size = size;
            objectOutputStream = objectOutputStream;
        }
        try {
            objectOutputStream.writeInt(contentUriTriggers.size());
            Iterator<ContentUriTriggers.Trigger> it = contentUriTriggers.getTriggers().iterator();
            while (true) {
                hasNext = it.hasNext();
                if (hasNext != 0) {
                    ContentUriTriggers.Trigger next = it.next();
                    objectOutputStream.writeUTF(next.getUri().toString());
                    objectOutputStream.writeBoolean(next.shouldTriggerForDescendants());
                } else {
                    try {
                        break;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
            }
            objectOutputStream.close();
            size.close();
            size = size;
            objectOutputStream = hasNext;
        } catch (IOException e5) {
            e = e5;
            objectOutputStream2 = objectOutputStream;
            e.printStackTrace();
            if (objectOutputStream2 != null) {
                try {
                    objectOutputStream2.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            size.close();
            size = size;
            objectOutputStream = objectOutputStream2;
            return size.toByteArray();
        } catch (Throwable th3) {
            th = th3;
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            try {
                size.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
            throw th;
        }
        return size.toByteArray();
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x005a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @androidx.room.TypeConverter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.work.ContentUriTriggers byteArrayToContentUriTriggers(byte[] r6) {
        /*
            androidx.work.ContentUriTriggers r0 = new androidx.work.ContentUriTriggers
            r0.<init>()
            if (r6 != 0) goto L_0x0008
            return r0
        L_0x0008:
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream
            r1.<init>(r6)
            r6 = 0
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch: IOException -> 0x003d, all -> 0x0039
            r2.<init>(r1)     // Catch: IOException -> 0x003d, all -> 0x0039
            int r6 = r2.readInt()     // Catch: IOException -> 0x0037, all -> 0x0057
        L_0x0017:
            if (r6 <= 0) goto L_0x002b
            java.lang.String r3 = r2.readUTF()     // Catch: IOException -> 0x0037, all -> 0x0057
            android.net.Uri r3 = android.net.Uri.parse(r3)     // Catch: IOException -> 0x0037, all -> 0x0057
            boolean r4 = r2.readBoolean()     // Catch: IOException -> 0x0037, all -> 0x0057
            r0.add(r3, r4)     // Catch: IOException -> 0x0037, all -> 0x0057
            int r6 = r6 + (-1)
            goto L_0x0017
        L_0x002b:
            r2.close()     // Catch: IOException -> 0x002f
            goto L_0x0033
        L_0x002f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0033:
            r1.close()     // Catch: IOException -> 0x0052
            goto L_0x0056
        L_0x0037:
            r6 = move-exception
            goto L_0x0041
        L_0x0039:
            r0 = move-exception
            r2 = r6
            r6 = r0
            goto L_0x0058
        L_0x003d:
            r2 = move-exception
            r5 = r2
            r2 = r6
            r6 = r5
        L_0x0041:
            r6.printStackTrace()     // Catch: all -> 0x0057
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch: IOException -> 0x004a
            goto L_0x004e
        L_0x004a:
            r6 = move-exception
            r6.printStackTrace()
        L_0x004e:
            r1.close()     // Catch: IOException -> 0x0052
            goto L_0x0056
        L_0x0052:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0056:
            return r0
        L_0x0057:
            r6 = move-exception
        L_0x0058:
            if (r2 == 0) goto L_0x0062
            r2.close()     // Catch: IOException -> 0x005e
            goto L_0x0062
        L_0x005e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0062:
            r1.close()     // Catch: IOException -> 0x0066
            goto L_0x006a
        L_0x0066:
            r0 = move-exception
            r0.printStackTrace()
        L_0x006a:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.model.WorkTypeConverters.byteArrayToContentUriTriggers(byte[]):androidx.work.ContentUriTriggers");
    }

    private WorkTypeConverters() {
    }
}
