package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.capability.gt.GemTypes;

public class GemPassiveStat extends Gem
{
    public GemPassiveStat(String name)
    {
        super(name);
    }

    @Override
    public GemType getGemType()
    {
        return GemTypes.PA;
    }
}
