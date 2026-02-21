package net.voidgroup.postProcessExample.client.mixin;

import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.voidgroup.postProcessExample.client.PostChainAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.function.Consumer;

@Mixin(PostChain.class)
public class PostChainMixin implements PostChainAccessor {
    @Shadow
    @Final
    private List<PostPass> passes;

    @Override
    public void post_process_example$forEachPass(Consumer<PostPass> func) {
        passes.forEach(func);
    }
}
