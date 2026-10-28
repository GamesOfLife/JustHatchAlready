package net.gamesoflife.justhatchalready.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnifferEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SnifferEggBlock.class)
public abstract class SnifferEggBlockMixin extends Block {
    public SnifferEggBlockMixin(Properties p) { super(p); }
    @Shadow
    public abstract void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom);
    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (!pLevel.isClientSide && pLevel.random.nextInt(3) == 0 && pState.is(Blocks.SNIFFER_EGG)) {
            this.tick(pState, (ServerLevel)pLevel, pPos, pLevel.getRandom());
        }
        pEntity.causeFallDamage(pFallDistance, 1.0F, pEntity.damageSources().fall());
    }
}
