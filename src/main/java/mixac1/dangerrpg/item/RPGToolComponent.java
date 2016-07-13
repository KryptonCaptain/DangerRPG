package mixac1.dangerrpg.item;

public enum RPGToolComponent
{
	          /* damage	speed	magic	strMul	agiMul	intMul	knBack	reach */
	TRAINING     (0.0F,	10.0F,	0.0F,	0.5F,	1.0F,	0.25F,	10.0F,	2.5F),
	
	SWORD        (4.0F,	10.0F,	0.0F,	0.5F,	1.0F,	0.25F,	1.0F,	0.0F),
	
	NAGINATA     (4.0F,	8.0F,	0.0F,	0.5F,	0.8F,	0.25F,	1.1F,	1.5F),
	
	KATANA       (3.5F,	11.0F,	0.0F,	0.5F,	1.1F,	0.25F,	0.5F,	0.0F),
	
	SCYTHE       (5.0F,	7.0F,	0.0F,	0.65F,	0.7F,	0.25F,	1.2F,	1.0F),
	
	HAMMER       (6.0F,	3.0F,	0.0F,	0.8F,	0.3F,	0.25F,	1.5F,	0.0F),
	
	TOMAHAWK     (3.5F,	10.5F,	0.0F,	0.4F,	1.1F,	0.25F,	0.4F,	0.0F),
	
	KNIFE        (1.0F,	12.5F,	0.0F,	0.25F,	1.25F,	0.25F,	0.1F,	0.0F),
	
	AXE          (3.0F,	9.0F,	0.0F,	0.55F,	0.8F,	0.25F,	1.0F,	0.0F),
	
	PICKAXE      (2.0F,	10.0F,	0.0F,	0.3F,	0.8F,	0.25F,	1.0F,	0.0F),
	
	SHOVEL       (1.0F,	10.0F,	0.0F,	0.3F,	0.8F,	0.25F,	1.0F,	0.0F),
	
	HOE          (1.0F,	10.0F,	0.0F,	0.4F,	0.8F,	0.25F,	1.0F,	0.0F),
	
	MULTITOOL    (3.0F,	10.0F,	0.0F,	0.55F,	0.8F,	0.25F,	1.0F,	0.0F),
	
	BOW          (2.0F, 20.0F,  0.0F,   0.16F,  1.0F,   0.25F,  1.0F,   0.0F),
    
    SHADOW_BOW   (2.5F, 16.0F,  0.0F,   0.16F,  1.0F,   0.25F,  1.0F,   0.0F),
	
	SNIPER_BOW   (4F,   40.0F,  0.0F,   0.16F,  1.0F,   0.25F,  1.0F,   0.0F);
	
	public float damage;
	public float speed;
	public float magic;
	public float strMul;
	public float agiMul;
	public float intMul;
	public float knBack;
	public float reach;
	
	RPGToolComponent(float damage, float speed, float magic, float strMul, float agiMul, float intMul, float knBack, float reach)
	{
		this.damage = damage;
		this.speed = speed;
		this.magic = magic;
		this.strMul = strMul;
		this.agiMul = agiMul;
		this.intMul = intMul;
		this.knBack = knBack;
		this.reach = reach;
	}
}
