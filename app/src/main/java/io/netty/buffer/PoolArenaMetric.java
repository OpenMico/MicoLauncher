package io.netty.buffer;

import java.util.List;

/* loaded from: classes4.dex */
public interface PoolArenaMetric {
    List<PoolChunkListMetric> chunkLists();

    long numActiveAllocations();

    long numActiveBytes();

    long numActiveHugeAllocations();

    long numActiveNormalAllocations();

    long numActiveSmallAllocations();

    long numActiveTinyAllocations();

    long numAllocations();

    int numChunkLists();

    long numDeallocations();

    long numHugeAllocations();

    long numHugeDeallocations();

    long numNormalAllocations();

    long numNormalDeallocations();

    long numSmallAllocations();

    long numSmallDeallocations();

    int numSmallSubpages();

    int numThreadCaches();

    long numTinyAllocations();

    long numTinyDeallocations();

    int numTinySubpages();

    List<PoolSubpageMetric> smallSubpages();

    List<PoolSubpageMetric> tinySubpages();
}
