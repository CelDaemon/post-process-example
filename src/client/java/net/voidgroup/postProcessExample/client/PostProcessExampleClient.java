package net.voidgroup.postProcessExample.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.voidgroup.postProcessExample.PostProcessExample;
import org.apache.commons.lang3.mutable.MutableObject;

import java.io.IOException;

public class PostProcessExampleClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        final var client = new MutableObject<Minecraft>();
        final var chain = new MutableObject<PostChain>();
        ClientLifecycleEvents.CLIENT_STARTED.register(minecraft -> {
            client.setValue(minecraft);
            try {
                chain.setValue(new PostChain(minecraft.getTextureManager(), minecraft.getResourceManager(), minecraft.getMainRenderTarget(), ResourceLocation.fromNamespaceAndPath(PostProcessExample.MOD_ID, "shaders/post/inverse_red.json")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            final var target = client.getValue().getMainRenderTarget();
            final var currentChain = chain.getValue();
            @SuppressWarnings("resource")
            final var strength = context.world().getGameTime() % 200 / 200.f;
            currentChain.setUniform("EffectStrength", strength);
            currentChain.resize(target.width, target.height);
            currentChain.process(0);
            target.bindWrite(true);
        });
    }
}
