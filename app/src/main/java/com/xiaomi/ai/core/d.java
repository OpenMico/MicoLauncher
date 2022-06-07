package com.xiaomi.ai.core;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class d {
    private a a;
    private com.xiaomi.ai.transport.b b;
    private int c;
    private int d;
    private int e;

    /* loaded from: classes3.dex */
    class a implements Callable<c> {
        c a;
        CountDownLatch b;

        a(c cVar, CountDownLatch countDownLatch) {
            this.a = cVar;
            this.b = countDownLatch;
        }

        /* renamed from: a */
        public c call() {
            String str;
            StringBuilder sb;
            Throwable e;
            String str2;
            try {
                long currentTimeMillis = System.currentTimeMillis();
                InetSocketAddress inetSocketAddress = new InetSocketAddress(this.a.a, this.a.b);
                Socket socket = new Socket();
                socket.connect(inetSocketAddress, d.this.d);
                socket.close();
                this.a.c = (int) (System.currentTimeMillis() - currentTimeMillis);
            } catch (IOException e2) {
                e = e2;
                this.a.c = Integer.MAX_VALUE;
                str2 = "HorseRace";
                sb = new StringBuilder();
                str = "connect timeout at ";
                sb.append(str);
                sb.append(this.a);
                sb.append(", ");
                sb.append(Logger.throwableToString(e));
                Logger.d(str2, sb.toString());
                this.b.countDown();
                return this.a;
            } catch (Exception e3) {
                e = e3;
                this.a.c = Integer.MAX_VALUE;
                str2 = "HorseRace";
                sb = new StringBuilder();
                str = "connect failed at ";
                sb.append(str);
                sb.append(this.a);
                sb.append(", ");
                sb.append(Logger.throwableToString(e));
                Logger.d(str2, sb.toString());
                this.b.countDown();
                return this.a;
            }
            this.b.countDown();
            return this.a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class b implements Runnable {
        String a;

        b(String str) {
            this.a = str;
        }

        private void a(List<c> list, ArrayNode arrayNode) {
            StringBuilder sb;
            String str;
            Collections.sort(list, new Comparator<c>() { // from class: com.xiaomi.ai.core.d.b.1
                /* renamed from: a */
                public int compare(c cVar, c cVar2) {
                    return cVar.c - cVar2.c;
                }
            });
            ArrayNode createArrayNode = APIUtils.getObjectMapper().createArrayNode();
            for (int i = 0; i < arrayNode.size(); i++) {
                if (i < list.size()) {
                    c cVar = list.get(i);
                    if (cVar.c == Integer.MAX_VALUE) {
                        str = "HorseRace";
                        sb = new StringBuilder();
                        sb.append("connect timeout at ");
                        sb.append(cVar);
                        sb.append(" , remove it from dns list");
                    } else {
                        createArrayNode.add(cVar.a + Constants.COLON_SEPARATOR + cVar.b);
                        str = "HorseRace";
                        sb = new StringBuilder();
                        sb.append("race result: ");
                        sb.append(cVar.a);
                        sb.append(Constants.COLON_SEPARATOR);
                        sb.append(cVar.b);
                        sb.append(" cost:");
                        sb.append(cVar.c);
                    }
                    Logger.a(str, sb.toString());
                } else {
                    createArrayNode.add(arrayNode.get(i).asText());
                    Logger.a("HorseRace", "original address: " + arrayNode.get(i).asText());
                }
            }
            Logger.b("HorseRace", "newDnsList: " + createArrayNode);
            if (createArrayNode.size() > 0) {
                d.this.b.a(createArrayNode, true, this.a, "http_dns_cache");
            } else {
                d.this.b.b(this.a, "http_dns_cache");
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            ArrayList<Future> arrayList = new ArrayList();
            ArrayNode a = d.this.b.a("http_dns_cache", this.a);
            if (a == null || a.size() == 0) {
                Logger.b("HorseRace", "local dns is empty!");
                return;
            }
            if (d.this.c > a.size()) {
                d.this.c = a.size();
                Logger.c("HorseRace", "add just horseNum to " + d.this.c);
            }
            Logger.a("HorseRace", "original dns:" + a);
            CountDownLatch countDownLatch = new CountDownLatch(d.this.c);
            int i = 0;
            for (int i2 = 0; i2 < a.size(); i2++) {
                String asText = a.get(i2).asText();
                if (f.a(asText)) {
                    d.this.b.b(this.a, "http_dns_cache");
                    Logger.c("HorseRace", "empty item in http dns cache! stop horse race");
                    return;
                }
                String[] split = asText.split(Constants.COLON_SEPARATOR);
                if (split.length != 2) {
                    d.this.b.b(this.a, "http_dns_cache");
                    Logger.c("HorseRace", "invalid item in http dns cache! stop horse race");
                    return;
                }
                c cVar = new c(split[0], Integer.parseInt(split[1]));
                Logger.a("HorseRace", "start race " + asText);
                arrayList.add(com.xiaomi.ai.b.c.a.submit(new a(cVar, countDownLatch)));
                i++;
                if (i < d.this.c) {
                }
            }
            try {
                countDownLatch.await(d.this.d, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
                Logger.c("HorseRace", "latch interrupted");
            }
            ArrayList arrayList2 = new ArrayList();
            for (Future future : arrayList) {
                try {
                    arrayList2.add((c) future.get());
                } catch (InterruptedException | ExecutionException | Exception e) {
                    Logger.d("HorseRace", Logger.throwableToString(e));
                }
            }
            a(arrayList2, a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class c {
        String a;
        int b;
        int c;

        c(String str, int i) {
            this.a = str;
            this.b = i;
        }

        public String toString() {
            return this.a + Constants.COLON_SEPARATOR + this.b;
        }
    }

    public d(a aVar, com.xiaomi.ai.transport.b bVar) {
        this.a = aVar;
        this.b = bVar;
        this.c = this.a.getAivsConfig().getInt(AivsConfig.Connection.TCP_HORSE_NUM, 3);
        this.d = this.a.getAivsConfig().getInt(AivsConfig.Connection.HORSE_RACE_TIMEOUT, 5000);
        this.e = this.a.getAivsConfig().getInt(AivsConfig.Connection.HORSE_RACE_INTERVAL, 300);
    }

    public void a(String str) {
        synchronized (d.class) {
            String a2 = this.a.getListener().a(this.a, "last_horse_race_at");
            if (!f.a(a2)) {
                long parseLong = Long.parseLong(a2) + (this.e * 1000);
                if (System.currentTimeMillis() < parseLong) {
                    Logger.b("HorseRace", "frequency limited, wait until " + parseLong);
                    return;
                }
            }
            this.a.getListener().a(this.a, "last_horse_race_at", String.valueOf(System.currentTimeMillis()));
            com.xiaomi.ai.b.c.a.execute(new b(str));
        }
    }
}
