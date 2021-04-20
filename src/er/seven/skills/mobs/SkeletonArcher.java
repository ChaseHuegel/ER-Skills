package er.seven.skills.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import er.seven.skills.Util;

public class SkeletonArcher extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.6f; }
	
	@Override public String 		getName() 	{ return "Skeleton Archer"; }
	@Override public EntityType 	getType() 	{ return EntityType.SKELETON; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 20; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.0f; }
	@Override public float 	getSpeed() 				{ return 0.25f; }
	@Override public float 	getDamage() 			{ return 1; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.BOW); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return Math.random() > 0.5f ? new ItemStack(Material.AIR) : new ItemStack(Util.randMaterial(Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET)); }
	@Override public ItemStack getChest() 		{ return Math.random() > 0.5f ? new ItemStack(Material.AIR) : new ItemStack(Util.randMaterial(Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE)); }
	@Override public ItemStack getLegs() 		{ return Math.random() > 0.5f ? new ItemStack(Material.AIR) : new ItemStack(Material.LEATHER_LEGGINGS); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
}
