package mixac1.dangerrpg.item;

public class RPGItemComponent
{
    public static final RPGItemComponent  NULL      = new RPGItemComponent();

    public static final RPGToolComponent  TRAINING  = new RPGToolComponent("training");

    public static final RPGToolComponent  SWORD     = new RPGToolComponent("sword");
    public static final RPGToolComponent  NAGINATA  = new RPGToolComponent("naginata");
    public static final RPGToolComponent  KATANA    = new RPGToolComponent("katana");
    public static final RPGToolComponent  SCYTHE    = new RPGToolComponent("scythe");
    public static final RPGToolComponent  HAMMER    = new RPGToolComponent("hammer");
    public static final RPGToolComponent  TOMAHAWK  = new RPGToolComponent("tomahawk");
    public static final RPGToolComponent  KNIFE     = new RPGToolComponent("knife");

    public static final RPGToolComponent  AXE       = new RPGToolComponent("axe");
    public static final RPGToolComponent  PICKAXE   = new RPGToolComponent("pickaxe");
    public static final RPGToolComponent  SHOVEL    = new RPGToolComponent("shovel");
    public static final RPGToolComponent  HOE       = new RPGToolComponent("hoe");
    public static final RPGToolComponent  MULTITOOL = new RPGToolComponent("multitool");

    public static final RPGBowComponent   BOW        = new RPGBowComponent("bow");
    public static final RPGBowComponent   SHADOW_BOW = new RPGBowComponent("shadow_bow");
    public static final RPGBowComponent   SNIPER_BOW = new RPGBowComponent("sniper_bow");

    public static final RPGArmorComponent ARMOR      = new RPGArmorComponent("armor");
    public static final RPGArmorComponent MAGE_ARMOR = new RPGArmorComponent("mage_armor");

    static
    {
     /* TOOLS             mDmg    mSpeed  magic   strMul  agiMul  intMul   knBack  knbMul    reach */

        TRAINING.init    (0.0F,   10.0F,  0.0F,   0.5F,   1.0F,   0.25F,   10.0F,  0.00F,    2.5F);

        SWORD.init       (4.0F,   10.0F,  0.0F,   0.5F,   1.0F,   0.25F,   0.0F,   0.10F,    0.0F);
        NAGINATA.init    (4.0F,   12.0F,  0.0F,   0.5F,   0.8F,   0.25F,   0.5F,   0.15F,    1.5F);
        KATANA.init      (3.5F,   9.0F,   0.0F,   0.5F,   1.1F,   0.25F,   0.0F,   0.05F,    0.0F);
        SCYTHE.init      (5.0F,   13.0F,  0.0F,   0.65F,  0.7F,   0.25F,   0.5F,   0.15F,    1.0F);
        HAMMER.init      (6.0F,   17.0F,  0.0F,   0.8F,   0.3F,   0.25F,   1.0F,   0.25F,    0.0F);
        TOMAHAWK.init    (3.5F,   9.5F,   0.0F,   0.4F,   1.1F,   0.25F,   0.0F,   0.00F,    0.0F);
        KNIFE.init       (1.0F,   7.5F,   0.0F,   0.25F,  1.25F,  0.25F,   0.0F,   0.00F,    0.0F);

        AXE.init         (3.0F,   11.0F,  0.0F,   0.55F,  0.8F,   0.25F,   0.0F,   0.10F,    0.0F);
        PICKAXE.init     (2.0F,   10.0F,  0.0F,   0.3F,   0.8F,   0.25F,   0.0F,   0.10F,    0.0F);
        SHOVEL.init      (1.0F,   10.0F,  0.0F,   0.3F,   0.8F,   0.25F,   0.0F,   0.10F,    0.0F);
        HOE.init         (1.0F,   10.0F,  0.0F,   0.4F,   0.8F,   0.25F,   0.0F,   0.10F,    0.0F);
        MULTITOOL.init   (1.0F,   10.0F,  0.0F,   0.4F,   0.8F,   0.25F,   0.0F,   0.10F,    0.0F);

     /* BOWS              mDmg    mSpeed  magic   strMul  agiMul  intMul   knBack  knbMul    reach   rDmg    rSpeed  rPow    durab   ench */

        BOW.init         (1.0F,   10.0F,  0.0F,   0.16F,  1.0F,   0.25F,   0.0F,   0.10F,    0.0F,   2.0F,   20.0F,  3.0F,   -0F,    3F);
        SHADOW_BOW.init  (4.0F,   10.0F,  0.0F,   0.16F,  1.0F,   0.25F,   0.0F,   0.10F,    0.0F,   2.5F,   16.0F,  3.5F,   500F,   5F);
        SNIPER_BOW.init  (1.0F,   10.0F,  0.0F,   0.16F,  1.0F,   0.25F,   1.0F,   0.20F,    0.0F,   4.0F,   40.0F,  5.5F,   1000F,  10F);

     /* ARMORS            mRes */

        ARMOR.init       (1.0f, 0.5f);
        MAGE_ARMOR.init  (0.5f, 1.0f);
    }

    public String name;

    public static class RPGToolComponent extends RPGItemComponent
    {
        public float meleeDamage;
        public float meleeSpeed;
        public float magic;
        public float strMul;
        public float agiMul;
        public float intMul;
        public float knBack;
        public float knbMul;
        public float reach;

        public RPGToolComponent(String name)
        {
            this.name = name;
        }

        protected void init(float meleeDamage, float meleeSpeed, float magic, float strMul,
                            float agiMul, float intMul, float knBack, float knbMul, float reach)
        {
            this.meleeDamage = meleeDamage;
            this.meleeSpeed  = meleeSpeed;
            this.magic       = magic;
            this.strMul      = strMul;
            this.agiMul      = agiMul;
            this.intMul      = intMul;
            this.knBack      = knBack;
            this.knbMul      = knbMul;
            this.reach       = reach;
        }
    }

    public static class RPGGunComponent extends RPGToolComponent
    {
        public float shotDamage;
        public float shotSpeed;
        public float shotPower;

        public RPGGunComponent(String name)
        {
            super(name);
        }

        protected void init(float meleeDamage, float meleeSpeed, float magic, float strMul,
                            float agiMul, float intMul, float knBack, float knbMul, float reach,
                            float shotDamage, float shotSpeed, float shotPower)
        {
            super.init(meleeDamage, meleeSpeed, magic, strMul, agiMul, intMul, knBack, knbMul, reach);
            this.shotDamage = shotDamage;
            this.shotSpeed  = shotSpeed;
            this.shotPower  = shotPower;
        }
    }

    public static class RPGBowComponent extends RPGGunComponent implements IWithoutToolMaterial
    {
        private RPGICWithoutTM itemComponent = new RPGICWithoutTM();

        public RPGBowComponent(String name)
        {
            super(name);
        }

        protected void init(float meleeDamage, float meleeSpeed, float magic, float strMul,
                            float agiMul, float intMul, float knBack, float knbMul, float reach,
                            float shotDamage, float shotSpeed, float shotPower,
                            float durab, float ench)
        {
            super.init(meleeDamage, meleeSpeed, magic, strMul, agiMul, intMul,
                       knBack, knbMul, reach, shotDamage, shotSpeed, shotPower);
            itemComponent.init(durab, ench);
        }

        @Override
        public float getMaxDurability()
        {
            return itemComponent.durab;
        }

        @Override
        public float getEnchantability()
        {
            return itemComponent.ench;
        }
    }

    public static class RPGArmorComponent extends RPGItemComponent
    {
        public float phisicalResMul;
        public float magicResMul;

        public RPGArmorComponent(String name)
        {
            this.name = name;
        }

        protected void init(float phisicalResMul, float magicResMul)
        {
            this.phisicalResMul = phisicalResMul;
            this.magicResMul = magicResMul;
        }
    }

    public static class RPGICWithoutTM extends RPGItemComponent implements IWithoutToolMaterial
    {
        public float durab;
        public float ench;

        protected void init(float durab, float ench)
        {
            this.durab = durab;
            this.ench  = ench;
        }

        @Override
        public float getMaxDurability()
        {
            return durab;
        }

        @Override
        public float getEnchantability()
        {
            return ench;
        }
    }

    public static interface IWithoutToolMaterial
    {
        public float getMaxDurability();

        public float getEnchantability();
    }
}
