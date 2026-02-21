package net.voidgroup.postProcessExample.client;

import net.minecraft.client.renderer.PostPass;

import java.util.function.Consumer;

public interface PostChainAccessor {
    default void post_process_example$forEachPass(Consumer<PostPass> func) {

    }
}
