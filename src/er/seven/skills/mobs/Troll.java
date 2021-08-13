package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Troll extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.5f; }
	
	@Override public String 		getName() 			{ return "Troll"; }
	@Override public EntityType 	getType() 			{ return EntityType.IRON_GOLEM; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ENDERMAN; }
	@Override public boolean 		isBaby() 			{ return false; }
	
	@Override public int 	getHealth() 			{ return 80; }
	@Override public int 	getArmor() 				{ return 4; }
	@Override public int 	getToughness()			{ return 12; }
	@Override public float 	getKnockbackResist() 	{ return 1f; }
	@Override public float 	getSpeed() 				{ return 0.15f; }
	@Override public float 	getDamage() 			{ return 14; }
	@Override public float 	getKnockback() 			{ return 0.5f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.GRAVELLY_MOUNTAINS, Biome.MOUNTAINS, Biome.MOUNTAIN_EDGE, Biome.MODIFIED_GRAVELLY_MOUNTAINS, Biome.SNOWY_MOUNTAINS,
			Biome.SNOWY_TAIGA_MOUNTAINS, Biome.TAIGA_MOUNTAINS, Biome.WOODED_MOUNTAINS
			);
	
	@Override
	public boolean checkSpawnCondition(Location loc)
	{
		if (loc.getWorld().getEnvironment() != Environment.NORMAL)
			return false;
		
		if (loc.getY() < 60 || validBiomes.contains( loc.getWorld().getBiome(loc.getBlockX(), 0, loc.getBlockZ()) ) == true)
		{
			return true;
		}
		
		return false;
	}
}
