package com.github.zly2006.reden.mixin.debugger.events;

import com.github.zly2006.reden.carpet.RedenCarpetSettings;
import com.github.zly2006.reden.debugger.events.BlockChangedEvent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldChunk.class)
public class MixinChunk {
    @Shadow @Final World world;

    @Inject(method = "setBlockState", at = @At("HEAD"))
    private void beforeSetBlockState(BlockPos pos, BlockState state, boolean moved, CallbackInfoReturnable<BlockState> cir) {
        if (!world.isClient && RedenCarpetSettings.Options.redenDebuggerEnabled) {
            var event = new BlockChangedEvent(pos, state, state);
            event.fire();
        }
    }
}
