package er.seven.skills.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class EnchantedKnight extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.15f; }
	
	@Override public String 		getName() 	{ return "Enchanted Knight"; }
	@Override public EntityType 	getType() 	{ return EntityType.SKELETON; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 30; }
	@Override public int 	getArmor() 				{ return 4; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.0f; }
	@Override public float 	getSpeed() 				{ return 0.25f; }
	@Override public float 	getDamage() 			{ return 1; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.GOLDEN_SWORD); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.SHIELD); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.GOLDEN_HELMET); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.GOLDEN_CHESTPLATE); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.GOLDEN_LEGGINGS); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.GOLDEN_BOOTS); }
}
