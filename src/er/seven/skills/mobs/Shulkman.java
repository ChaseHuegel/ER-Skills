package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;

import er.seven.skills.Main;

public class Shulkman extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.25f; }
	
	@Override public String 		getName() 			{ return "Shulkman"; }
	@Override public EntityType 	getType() 			{ return EntityType.WITHER_SKELETON; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ENDERMAN; }
	
	@Override public int 	getHealth() 			{ return 30; }
	@Override public int 	getArmor() 				{ return 6; }
	@Override public int 	getToughness()			{ return 4; }
	@Override public float 	getKnockbackResist() 	{ return 1.0f; }
	@Override public float 	getSpeed() 				{ return 0.25f; }
	@Override public float 	getDamage() 			{ return 1; }
	@Override public float 	getKnockback() 			{ return 0.15f; }
	
	@Override public PotionEffect getPotionEffect() { return null; }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.NETHERITE_SWORD); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.ELYTRA); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.END_BARRENS, Biome.END_HIGHLANDS, Biome.END_MIDLANDS, Biome.SMALL_END_ISLANDS, Biome.THE_END
			);
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.setLootTable(LootTables.SHULKER.getLootTable());
	}
	
	@Override
	public boolean checkSpawnCondition(Location loc)
	{
		if (validBiomes.contains( loc.getWorld().getBiome(loc.getBlockX(), 0, loc.getBlockZ()) ) == true)
		{
			return true;
		}
		
		return false;
	}
	
	public Shulkman()
	{
		//	Run per tick
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(Main.Instance().plugin, new Runnable() 
		{
			@Override
			public void run()
			{
				List<World> worlds = Bukkit.getWorlds();
				
				for (World world : worlds)
				{
					if (world.getEnvironment() != Environment.THE_END) continue;
					
					List<LivingEntity> entities = world.getLivingEntities();
					
					for (LivingEntity entity : entities)
					{
						if (isMatching(entity))
						{
							world.spawnParticle(Particle.SQUID_INK, entity.getLocation(), 4, 0.5f, 1.5f, 0.5f, 0);
						}
					}
				}
			}
		}, 0, 5);
	}
}
