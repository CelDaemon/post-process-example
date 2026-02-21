package net.voidgroup.renderingTest.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.shaders.Program;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.voidgroup.renderingTest.RenderingTest;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.UnaryOperator;

@Debug(export = true)
@Mixin(EffectInstance.class)
public class EffectInstanceMixin {
    @WrapOperation(method = "<init>", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation init(String path, Operation<ResourceLocation> original, ResourceManager resourceManager, String name) {
        return mapLocation(path, original, name, location -> location.withSuffix(".json"));
    }

    @WrapOperation(method = "getOrCreate", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation getOrCreate(String path, Operation<ResourceLocation> original, ResourceManager resourceManager, Program.Type type, String name) {
        return mapLocation(path, original, name, location -> location.withSuffix(type.getExtension()));
    }

    @Unique
    private static ResourceLocation mapLocation(String path, Operation<ResourceLocation> original, String name, UnaryOperator<ResourceLocation> operator) {
        final var location = ResourceLocation.tryParse(name);

        if(location == null || !location.getNamespace().equals(RenderingTest.MOD_ID))
            return original.call(path);

        return operator.apply(location.withPrefix("shaders/program/"));
    }
}
