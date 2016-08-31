package mixac1.dangerrpg.client.gui;

import java.util.LinkedHashSet;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.GemType;
import mixac1.dangerrpg.capability.GemableItem;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.gem.Gem;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public class GuiInfoBookContentStack extends GuiInfoBookContent
{
    private ItemStack stack;
    private EntityPlayer player;

    public GuiInfoBookContentStack(Minecraft mc, int width, int height, int top, int size, int left, GuiInfoBook parent, ItemStack stack)
    {
        super(mc, width, height, top, size, left, mc.fontRenderer.FONT_HEIGHT + 2, parent);
        this.stack = stack;
        if (parent.target instanceof EntityPlayer) {
            player = (EntityPlayer) parent.target;
        }
    }

    @Override
    public void init()
    {
        super.init();

        if (stack == null) {
            addCenteredString(DangerRPG.trans("rpgstr.no_item"));
            return;
        }

        if (player == null) {
            return;
        }

        addCenteredString(stack.getDisplayName().toUpperCase());
        addString("");

        addCenteredString(DangerRPG.trans("rpgstr.item_description").toUpperCase());
        addString("");

        if (LvlableItem.isLvlable(stack)) {
            addString(String.format("%s: %d\n", ItemAttributes.LEVEL.getDispayName(),
                                                (int) ItemAttributes.LEVEL.get(stack)));
            addString(String.format("%s: %d/%d", ItemAttributes.CURR_EXP.getDispayName(),
                                                 (int) ItemAttributes.CURR_EXP.get(stack),
                                                 (int) ItemAttributes.MAX_EXP.get(stack)));
            if (ItemAttributes.MAX_DURABILITY.hasIt(stack)) {
                addString(String.format("%s: %s/%s", ItemAttributes.DURABILITY.getDispayName(),
                                                     ItemAttributes.DURABILITY.getDispayValue(stack, player),
                                                     ItemAttributes.MAX_DURABILITY.getDispayValue(stack, player)));
            }
            addString("");
        }

        if (stack.getItem() instanceof IHasBooksInfo) {
            String s = ((IHasBooksInfo) stack.getItem()).getInformationToInfoBook(stack, player);
            if (s != null) {
                addString(s);
                addString("");
            }
        }

        if (LvlableItem.isLvlable(stack)) {
            Set<ItemAttribute> itemAttributes = new LinkedHashSet<ItemAttribute>(LvlableItem.getAttributeValues(stack));
            itemAttributes.remove(ItemAttributes.MAX_EXP);
            itemAttributes.remove(ItemAttributes.DURABILITY);
            itemAttributes.remove(ItemAttributes.MAX_DURABILITY);
            if (itemAttributes.size() != 0) {
                addCenteredString(DangerRPG.trans("rpgstr.parametres").toUpperCase());
                addString("");
                boolean flag = false;

                flag |= addAttribute(ItemAttributes.PHISIC_ARMOR, itemAttributes);
                flag |= addAttribute(ItemAttributes.MAGIC_ARMOR, itemAttributes);
                flag |= addAttribute(ItemAttributes.MELEE_DAMAGE, itemAttributes);
                flag |= addAttribute(ItemAttributes.SHOT_DAMAGE, itemAttributes);
                flag |= addAttribute(ItemAttributes.SHOT_POWER, itemAttributes);
                flag |= addAttribute(ItemAttributes.MELEE_SPEED, itemAttributes);
                flag |= addAttribute(ItemAttributes.SHOT_SPEED, itemAttributes);
                flag |= addAttribute(ItemAttributes.MANA_COST, itemAttributes);
                flag |= addAttribute(ItemAttributes.REACH, itemAttributes);
                flag |= addAttribute(ItemAttributes.KNOCKBACK, itemAttributes);

                if (flag) {
                    addString("");
                    flag = false;
                }

                flag |= addAttribute(ItemAttributes.STR_MUL, itemAttributes);
                flag |= addAttribute(ItemAttributes.AGI_MUL, itemAttributes);
                flag |= addAttribute(ItemAttributes.INT_MUL, itemAttributes);
                flag |= addAttribute(ItemAttributes.KNBACK_MUL, itemAttributes);

                if (flag) {
                    addString("");
                    flag = false;
                }

                flag |= addAttribute(ItemAttributes.EFFICIENCY, itemAttributes);
                flag |= addAttribute(ItemAttributes.ENCHANTABILITY, itemAttributes);
                for(ItemAttribute iter : itemAttributes) {
                    if (iter.isVisibleInInfoBook(stack)) {
                        addString(String.format("%s : %s", iter.getDispayName(), iter.getDispayValue(stack, player)));
                    }
                }
                addString("");
            }
        }

        if (GemableItem.isGemable(stack)) {
            GemType[] gems = GemableItem.getGemTypes(stack);
            boolean empty = true;
            if (gems.length != 0) {
                for (GemType gemType : gems) {
                    ItemStack gem = gemType.get(stack);
                    if (gem != null && gem.getItem() instanceof Gem) {
                        if (empty) {
                            empty = false;
                            addCenteredString(DangerRPG.trans("rpgstr.gems").toUpperCase());
                            addString("");
                        }
                        addString(Utils.toString(DangerRPG.trans("rpgstr.name"), ": ", gem.getDisplayName()));
                        addString(Utils.toString(DangerRPG.trans("rpgstr.type"), ": ", ((Gem) gem.getItem()).getGemType().getDispayName()));
                        addString("");
                    }
                }
            }
        }
    }

    private void addString(String str)
    {
        list.addAll(mc.fontRenderer.listFormattedStringToWidth(str, listWidth - 15));
    }

    private void addCenteredString(String str)
    {
        list.add(new CenteredString(str));
    }

    private boolean addAttribute(ItemAttribute attr, Set<ItemAttribute> set)
    {
        if (attr.hasIt(stack) && attr.isVisibleInInfoBook(stack)) {
            addString(String.format("%s : %s", attr.getDispayName(), attr.getDispayValue(stack, player)));
            set.remove(attr);

            return true;
        }
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);

        String s = DangerRPG.trans("rpgstr.item_info");
        mc.fontRenderer.drawStringWithShadow(s, left + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2, top - mc.fontRenderer.FONT_HEIGHT - 4, 0xffffff);
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {}

    @Override
    protected boolean isSelected(int index)
    {
        return false;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5)
    {
        Object obj = list.get(var1);
        if (obj instanceof CenteredString) {
            ((CenteredString) obj).draw(left, var3, 0xffffff);
        }
        else {
            mc.fontRenderer.drawString(obj.toString(), left + 5, var3, 0xffffff);
        }
    }

    public class CenteredString
    {
        String str;

        public CenteredString(String str)
        {
            this.str = str;
        }

        public void draw(int x, int y, int color)
        {
            String s = mc.fontRenderer.trimStringToWidth(str, listWidth);
            mc.fontRenderer.drawString(s, x + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2, y, color);
        }
    }
}
