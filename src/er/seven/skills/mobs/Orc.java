package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import er.seven.skills.Util;

public class Orc extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.4f; }
	
	@Override public String 		getName() 			{ return "Orc"; }
	@Override public EntityType 	getType() 			{ return EntityType.PIGLIN; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ZOMBIE; }
	@Override public boolean 		isBaby() 			{ return false; }
	
	@Override public int 	getHealth() 			{ return 30; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 6; }
	@Override public float 	getKnockbackResist() 	{ return 0.2f; }
	@Override public float 	getSpeed() 				{ return 0.25f; }
	@Override public float 	getDamage() 			{ return 1; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack( Util.randMaterial(Material.IRON_AXE, Material.IRON_SWORD) ); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return Math.random() > 0.1f ? new ItemStack(Material.AIR) : new ItemStack( Material.IRON_HELMET ); }
	@Override public ItemStack getChest() 		{ return Math.random() > 0.5f ? new ItemStack(Material.AIR) : new ItemStack(Material.CHAINMAIL_CHESTPLATE); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.GRAVELLY_MOUNTAINS, Biome.MOUNTAINS, Biome.MOUNTAIN_EDGE, Biome.MODIFIED_GRAVELLY_MOUNTAINS, Biome.SNOWY_MOUNTAINS,
			Biome.SNOWY_TAIGA_MOUNTAINS, Biome.TAIGA_MOUNTAINS, Biome.WOODED_MOUNTAINS
			);
	
	@Override
	public boolean checkSpawnCondition(Location loc)
	{
		if (loc.getY() < 60 || validBiomes.contains( loc.getWorld().getBiome(loc.getBlockX(), 0, loc.getBlockZ()) ) == true)
		{
			return true;
		}
		
		return false;
	}
}
