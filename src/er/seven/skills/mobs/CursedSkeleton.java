package er.seven.skills.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CursedSkeleton extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.2f; }
	
	@Override public String 		getName() 			{ return "Cursed Skeleton"; }
	@Override public EntityType 	getType() 			{ return EntityType.SKELETON; }
	@Override public boolean 		isBaby() 			{ return false; }
	
	@Override public int 	getHealth() 			{ return 30; }
	@Override public int 	getArmor() 				{ return 2; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.0f; }
	@Override public float 	getSpeed() 				{ return 0.15f; }
	@Override public float 	getDamage() 			{ return 2; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false); }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.setFireTicks(Integer.MAX_VALUE);
	}
}
