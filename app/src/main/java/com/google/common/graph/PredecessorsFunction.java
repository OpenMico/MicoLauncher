package com.google.common.graph;

import com.google.common.annotations.Beta;

@Beta
/* loaded from: classes2.dex */
public interface PredecessorsFunction<N> {
    Iterable<? extends N> predecessors(N n);
}
