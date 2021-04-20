package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Piglin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PigmanHunter extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.5f; }
	
	@Override public String 		getName() 			{ return "Pigman Hunter"; }
	@Override public EntityType 	getType() 			{ return EntityType.PIGLIN; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.SKELETON; }
	@Override public boolean 		isBaby() 			{ return false; }
	
	@Override public int 	getHealth() 			{ return 16; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.0f; }
	@Override public float 	getSpeed() 				{ return 0.3f; }
	@Override public float 	getDamage() 			{ return 1; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.CROSSBOW); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return Math.random() > 0.5f ? new ItemStack(Material.AIR) : new ItemStack(Material.LEATHER_HELMET); }
	@Override public ItemStack getChest() 		{ return Math.random() > 0.5f ? new ItemStack(Material.AIR) : new ItemStack(Material.LEATHER_CHESTPLATE); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.FOREST, Biome.SAVANNA, Biome.PLAINS
			);
	
	@Override
	public boolean checkSpawnCondition(Location loc)
	{
		if (loc.getY() > 60 && validBiomes.contains( loc.getWorld().getBiome(loc.getBlockX(), 0, loc.getBlockZ()) ) == true)
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public void PostSpawn(Mob mob)
	{
		( (Piglin)mob ).setIsAbleToHunt(true);
	}
}
