package mixac1.dangerrpg.client.model;

import mixac1.dangerrpg.client.ModelBipedColor;
import mixac1.dangerrpg.client.ModelBipedE;
import mixac1.dangerrpg.client.ModelRendererColor;
import mixac1.dangerrpg.client.RPGRenderHelper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ModelMageArmor extends ModelBipedColor
{
    public static final ModelMageArmor INSTANCE_ARMOR = new ModelMageArmor(0.9F);
    public static final ModelMageArmor INSTANCE_LEGGINGS = new ModelMageArmor(0.4F);

    public ModelRendererColor cape;

    public ModelRenderer armorLeftArm;
    public ModelRenderer armorRightArm;
    public ModelRenderer armorBody;
    public ModelRenderer armorLeftLeg;
    public ModelRenderer armorRightLeg;
    public ModelRenderer armorHead;

    public ModelMageArmor(float scale)
    {
        super(scale, 0.0F, 80, 64);

        this.cape = new ModelRendererColor(this, 50, 32);
        this.cape.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cape.addBox(-5.0F, 11.0F, -2.1F, 10, 7, 5, scale);

        this.armorHead = new ModelRenderer(this, 0, 32);
        this.armorHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.armorHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.1F);

        this.armorLeftLeg = new ModelRenderer(this, 0, 48);
        this.armorLeftLeg.mirror = true;
        this.armorLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
        this.armorLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale + 0.1F);

        this.armorRightLeg = new ModelRenderer(this, 0, 48);
        this.armorRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
        this.armorRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale + 0.1F);

        this.armorBody = new ModelRenderer(this, 16, 48);
        this.armorBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.armorBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale + 0.1F);

        this.armorLeftArm = new ModelRenderer(this, 40, 48);
        this.armorLeftArm.mirror = true;
        this.armorLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.armorLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale + 0.1F);

        this.armorRightArm = new ModelRenderer(this, 40, 48);
        this.armorRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.armorRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale + 0.1F);
    }

    @Override
    public ModelBipedE init(EntityLivingBase entity, ItemStack stack, int slot)
    {
        super.init(entity, stack, slot);

        this.cape.showModel = this.bipedBody.showModel;

        this.armorHead.showModel = this.bipedHead.showModel;
        this.armorLeftLeg.showModel = this.bipedLeftLeg.showModel;
        this.armorLeftArm.showModel = this.bipedLeftArm.showModel;
        this.armorRightLeg.showModel = this.bipedRightLeg.showModel;
        this.armorBody.showModel = this.bipedBody.showModel;
        this.armorRightArm.showModel = this.bipedRightArm.showModel;

        return this;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);

        this.cape.render(f5);

        this.armorHead.render(f5);
        this.armorLeftLeg.render(f5);
        this.armorLeftArm.render(f5);
        this.armorRightLeg.render(f5);
        this.armorBody.render(f5);
        this.armorRightArm.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity f6)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, f6);
        RPGRenderHelper.copyModelRenderer(this.bipedBody, this.cape);

        RPGRenderHelper.copyModelRenderer(this.bipedBody, this.armorBody);
        RPGRenderHelper.copyModelRenderer(this.bipedLeftArm, this.armorLeftArm);
        RPGRenderHelper.copyModelRenderer(this.bipedRightArm, this.armorRightArm);
        RPGRenderHelper.copyModelRenderer(this.bipedLeftLeg, this.armorLeftLeg);
        RPGRenderHelper.copyModelRenderer(this.bipedRightLeg, this.armorRightLeg);
        RPGRenderHelper.copyModelRenderer(this.bipedHead, this.armorHead);
    }

    @Override
    public void setColor(int color)
    {
        super.setColor(color);
        cape.setColor(color);
    }
}
