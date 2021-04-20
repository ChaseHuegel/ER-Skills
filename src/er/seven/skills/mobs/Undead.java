package er.seven.skills.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Undead extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.35f; }
	
	@Override public String 		getName() 	{ return "Forgotten"; }
	@Override public EntityType 	getType() 	{ return EntityType.ZOMBIE; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 20; }
	@Override public int 	getArmor() 				{ return 8; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.0f; }
	@Override public float 	getSpeed() 				{ return 0.23f; }
	@Override public float 	getDamage() 			{ return 3; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
}
