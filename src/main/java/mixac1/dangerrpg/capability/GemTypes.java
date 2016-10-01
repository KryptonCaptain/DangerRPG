package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.capability.gt.GTPassiveAttribute;
import mixac1.dangerrpg.capability.gt.GTPassiveAttribute.GTPAArmor;
import mixac1.dangerrpg.capability.gt.GTPassiveAttribute.GTPAWeapon;

public abstract class GemTypes
{
    public static final GTPassiveAttribute  PA  = new GTPAWeapon("pa");
    public static final GTPAWeapon          PAW = new GTPAWeapon("paw");
    public static final GTPAArmor           PAA = new GTPAArmor("paa");
}
