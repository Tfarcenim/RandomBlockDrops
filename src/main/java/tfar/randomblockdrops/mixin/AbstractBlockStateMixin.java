package tfar.randomblockdrops.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.randomblockdrops.RandomBlockDrops;

import java.util.List;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
	@Shadow public abstract Block getBlock();

	@Shadow public abstract List<ItemStack> getDrops(LootContext.Builder builder);

	@Inject(at = @At("HEAD"), method = "getDrops",cancellable = true)
	private void init(LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
		if (RandomBlockDrops.ServerConfig.randomToggle.get()) {
			Block randomBlock = RandomBlockDrops.map.get(getBlock());
			BlockState state = randomBlock.getDefaultState();
			cir.setReturnValue(randomBlock.getDrops(state, builder));
		}
	}
}
