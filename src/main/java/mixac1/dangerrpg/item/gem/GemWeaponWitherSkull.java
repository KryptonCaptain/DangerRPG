package mixac1.dangerrpg.item.gem;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.GemType;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.util.CopyPastedWorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class GemWeaponWitherSkull extends GemWeaponSpecialAttack
{
    private static final int NEED_MANA = 5;

    public GemWeaponWitherSkull(String name)
    {
        super(name);

        this.setMaxStackSize(1);
    }

    @Override
    public void upgrade(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote) {
            float need;
            if (!player.capabilities.isCreativeMode) {
                if (PlayerAttributes.CURR_MANA.getValue(player) >= (need = (player.isSneaking()) ? NEED_MANA * 2 : NEED_MANA)) {
                    PlayerAttributes.CURR_MANA.addValue(-need, player);
                }
                else {
                    return;
                }
            }

            Vec3 vector = CopyPastedWorldHelper.getVecFromEntity(player, 6.0F);
            double motionX = vector.xCoord - player.posX;
            double motionY = vector.yCoord - (player.posY + player.getEyeHeight());
            double motionZ = vector.zCoord - player.posZ;

            EntityWitherSkull skull = new EntityWitherSkull(world, player, motionX, motionY, motionZ);
            skull.posX += motionX / 6;
            skull.posY += motionY / 6;
            skull.posZ += motionZ / 6;
            skull.posY += player.getEyeHeight();

            if (player.isSneaking()) {
                skull.setInvulnerable(true);
            }

            world.spawnEntityInWorld(skull);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag)
    {
        list.add(EnumChatFormatting.GOLD + DangerRPG.trans("rpgstr.gem_type") + ":");
        list.add(EnumChatFormatting.GRAY + GemType.GEM_SPECIAL_ATTACK.getDispayName() + " (" + DangerRPG.trans("rpgstr.weapon") + ")");
        list.add("");
        list.add(EnumChatFormatting.DARK_AQUA + DangerRPG.trans("rpgstr.description") + ":");
        list.add(EnumChatFormatting.GRAY + DangerRPG.trans("rpgstr.item.gem_wither_skull"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player, ItemStack gem)
    {
        return super.getInformationToInfoBook(item, player, gem).concat(DangerRPG.trans("rpgstr.no_info_yet"));
    }
}
