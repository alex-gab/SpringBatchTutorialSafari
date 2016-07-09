package io.spring.batch.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;

public final class ChunkListener {

    @BeforeChunk
    public final void beforeChunk() {
        System.out.println(">> Before the chunk");
    }

    @AfterChunk
    public final void afterChunk() {
        System.out.println("<< After the chunk");
    }
}
