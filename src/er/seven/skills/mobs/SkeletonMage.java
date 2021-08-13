package er.seven.skills.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import er.seven.skills.Magic;
import er.seven.skills.Util;

public class SkeletonMage extends Monster
{
	public String[] staffNames = new String[] {
			"Wooden Staff",
			"Emerald Staff",
			"Amethyst Staff",
			"Quartz Staff",
			"Staff of Ender",
			"Staff of Fire",
			"Diamond Staff",
			"Presto Wand",
			"Soul Staff",
			"Prismarine Staff",
			"Staff of the Sea",
			"Star Staff",
			"Netherite Staff",
			"Staff of Time"
	};
	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.1f; }
	
	@Override public String 		getName() 	{ return "Skeleton Mage"; }
	@Override public EntityType 	getType() 	{ return EntityType.SKELETON; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 20; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.0f; }
	@Override public float 	getSpeed() 				{ return 0.2f; }
	@Override public float 	getDamage() 			{ return 4; }
	@Override public float 	getKnockback() 			{ return 1.0f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return Magic.CreateStaff(Util.randomString(staffNames)); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.getEquipment().setItemInMainHandDropChance(0.5f);
	}
}
