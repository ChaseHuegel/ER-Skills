package er.seven.skills.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haunt extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.35f; }
	
	@Override public String 		getName() 	{ return "Haunt"; }
	@Override public EntityType 	getType() 	{ return EntityType.SKELETON; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ENDERMAN; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 30; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.5f; }
	@Override public float 	getSpeed() 				{ return 0.25f; }
	@Override public float 	getDamage() 			{ return 3; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, true); }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.IRON_HOE); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.CARVED_PUMPKIN); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.setSilent(true);
	}
	
	@Override
	public boolean checkSpawnCondition(Location loc)
	{
		if (loc.getWorld().getEnvironment() != Environment.THE_END)
		{
			return true;
		}
		
		return false;
	}
}
