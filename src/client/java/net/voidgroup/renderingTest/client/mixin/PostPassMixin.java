package net.voidgroup.renderingTest.client.mixin;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostPass;
import net.voidgroup.renderingTest.client.PostPassAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PostPass.class)
public class PostPassMixin implements PostPassAccessor {
    @Shadow
    @Final
    private EffectInstance effect;

    @Override
    public void rendering_test$setUniform(String name, float value) {
        effect.safeGetUniform(name).set(value);
    }
}
