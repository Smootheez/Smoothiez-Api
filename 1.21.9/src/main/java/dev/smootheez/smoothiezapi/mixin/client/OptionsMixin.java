package dev.smootheez.smoothiezapi.mixin.client;

import dev.smootheez.smoothiezapi.keymapping.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Options.class)
public abstract class OptionsMixin {
    @Mutable
    @Shadow @Final public KeyMapping[] keyMappings;

    @Inject(method = "load", at = @At("HEAD"))
    public void loadHook(CallbackInfo info) {
        keyMappings = KeyMappingRegistryImpl.process(keyMappings);
    }
}
