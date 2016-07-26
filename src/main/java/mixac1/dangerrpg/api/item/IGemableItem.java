package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.capability.GemType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

/**
 * Implements this interface for creating GemableItem
 */
public interface IGemableItem
{
    public static final IGemableItem DEFAULT = new IGemableItem()
    {
        @Override
        public GemType[] getGemTypes(ItemStack stack)
        {
            if (stack.getItem() instanceof ItemSword) {
                return new GemType[] {
                    GemType.GEM_MODIFY_ATTACK_1,
                    GemType.GEM_MODIFY_ATTACK_2,
                    GemType.GEM_SPECIAL_ATTACK,
                    GemType.GEM_DEFENCE
                };
            }
            return new GemType[] {};
        }
    };
    
    public GemType[] getGemTypes(ItemStack stack);
}
