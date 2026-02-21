package net.voidgroup.renderingTest.client;

import net.minecraft.client.renderer.PostPass;

import java.util.function.Consumer;

public interface PostChainAccessor {
    default void rendering_test$forEachPass(Consumer<PostPass> func) {

    }
}
