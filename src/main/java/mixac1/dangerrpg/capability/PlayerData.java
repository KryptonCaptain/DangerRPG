package mixac1.dangerrpg.capability;

import java.util.ArrayList;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.init.RPGConfig;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerData extends CommonEntityData
{
    public final EntityPlayer player;

    public static ArrayList<LvlEAProvider>   lvlProviders     = new ArrayList<LvlEAProvider>(CommonEntityData.lvlProviders);
    public static ArrayList<EntityAttribute> entityAttributes = new ArrayList<EntityAttribute>(CommonEntityData.entityAttributes);

    public PlayerData(EntityPlayer player)
    {
        super(player);
        this.player = player;
    }

    public EntityPlayer getPlayer()
    {
        return (EntityPlayer) entity;
    }

    public static void register(EntityPlayer player)
    {
        player.registerExtendedProperties(ID, new PlayerData(player));
    }

    public static PlayerData get(EntityPlayer player)
    {
        return (PlayerData) player.getExtendedProperties(ID);
    }

    public void rebuildOnDeath()
    {
        int count = 0;
        int lvl;

        ArrayList<LvlEAProvider> pas = new ArrayList<LvlEAProvider>();
        for (LvlEAProvider it : getLvlProviders()) {
            if ((lvl = it.getLvl(player)) > 1) {
                pas.add(it);
                count += lvl - 1;
            }
        }

        if (count > RPGConfig.playerLoseLvlCount) {
            count = RPGConfig.playerLoseLvlCount;
        }

        for (int i = 0; i < count; ++i) {
            int rand = DangerRPG.rand.nextInt(pas.size());
            pas.get(rand).up(player, false);
            if (pas.get(rand).getLvl(player) <= 1) {
                pas.remove(rand);
            }
        }
    }

    @Override
    public ArrayList<EntityAttribute> getEntityAttributes()
    {
        return entityAttributes;
    }

    @Override
    public ArrayList<LvlEAProvider> getLvlProviders()
    {
        return lvlProviders;
    }
}
