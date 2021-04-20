package er.seven.skills.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class SkeletonWarrior extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.6f; }
	
	@Override public String 		getName() 	{ return "Skeleton Warrior"; }
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
	
	@Override public ItemStack getMainHand() 	{ return Math.random() > 0.5f ? new ItemStack(Material.IRON_SWORD) : new ItemStack(Material.IRON_AXE); }
	@Override public ItemStack getOffHand() 	{ return Math.random() > 0.5f ? new ItemStack(Material.AIR) : new ItemStack(Material.SHIELD); }
	@Override public ItemStack getHead() 		{ return Math.random() > 0.5f ? new ItemStack(Material.CHAINMAIL_HELMET) : new ItemStack(Material.IRON_HELMET); }
	@Override public ItemStack getChest() 		{ return Math.random() > 0.6f ? new ItemStack(Material.CHAINMAIL_CHESTPLATE) : new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
}
