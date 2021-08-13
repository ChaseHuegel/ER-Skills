package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Vulture extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 1f; }
	
	@Override public String 		getName() 			{ return "Vulture"; }
	@Override public EntityType 	getType() 			{ return EntityType.PHANTOM; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.PHANTOM; }
	@Override public boolean 		isBaby() 			{ return false; }
	
	@Override public int 	getHealth() 			{ return 6; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0f; }
	@Override public float 	getSpeed() 				{ return 0.2f; }
	@Override public float 	getDamage() 			{ return 2; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_LAKES, Biome.BADLANDS, Biome.BADLANDS_PLATEAU, Biome.ERODED_BADLANDS,
			Biome.MODIFIED_BADLANDS_PLATEAU, Biome.MODIFIED_WOODED_BADLANDS_PLATEAU, Biome.WOODED_BADLANDS_PLATEAU
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
}
