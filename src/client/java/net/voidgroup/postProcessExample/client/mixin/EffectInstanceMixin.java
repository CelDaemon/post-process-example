package net.voidgroup.postProcessExample.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.shaders.Program;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.voidgroup.postProcessExample.PostProcessExample;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(EffectInstance.class)
public class EffectInstanceMixin {
    @WrapOperation(method = "<init>", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation init(String path, Operation<ResourceLocation> original, ResourceManager resourceManager, String name) {
        return mapLocation(path, original, name, ".json");
    }

    @WrapOperation(method = "getOrCreate", at = @At(value = "NEW", target = "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;"))
    private static ResourceLocation getOrCreate(String path, Operation<ResourceLocation> original, ResourceManager resourceManager, Program.Type type, String name) {
        return mapLocation(path, original, name, type.getExtension());
    }

    @Unique
    private static ResourceLocation mapLocation(String path, Operation<ResourceLocation> original, String name, String suffix) {
        final var location = ResourceLocation.tryParse(name);

        if(location == null || !location.getNamespace().equals(PostProcessExample.MOD_ID))
            return original.call(path);

        return location.withPath(pathName -> "shaders/program/" + pathName + suffix);
    }
}
