package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class BlackBear extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.3f; }
	
	@Override public String 		getName() 			{ return "Black Bear"; }
	@Override public EntityType 	getType() 			{ return EntityType.POLAR_BEAR; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ZOMBIE; }
	@Override public boolean 		isBaby() 			{ return false; }
	
	@Override public int 	getHealth() 			{ return 25; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.4f; }
	@Override public float 	getSpeed() 				{ return 0.4f; }
	@Override public float 	getDamage() 			{ return 3; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.BIRCH_FOREST, Biome.DARK_FOREST, Biome.FOREST, Biome.TALL_BIRCH_FOREST, 
			Biome.WOODED_MOUNTAINS, Biome.TAIGA, Biome.GIANT_TREE_TAIGA, Biome.GIANT_SPRUCE_TAIGA
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
