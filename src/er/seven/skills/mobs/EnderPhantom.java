package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitScheduler;

import er.seven.skills.Main;

public class EnderPhantom extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.25f; }
	
	@Override public String 		getName() 			{ return "Ender Phantom"; }
	@Override public EntityType 	getType() 			{ return EntityType.PHANTOM; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ENDERMAN; }
	
	@Override public int 	getHealth() 			{ return 10; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 0.0f; }
	@Override public float 	getSpeed() 				{ return 0.35f; }
	@Override public float 	getDamage() 			{ return 2; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.END_BARRENS, Biome.END_HIGHLANDS, Biome.END_MIDLANDS, Biome.SMALL_END_ISLANDS, Biome.THE_END
			);
	
	@Override
	public boolean checkSpawnCondition(Location loc)
	{
		if (validBiomes.contains( loc.getWorld().getBiome(loc.getBlockX(), 0, loc.getBlockZ()) ) == true)
		{
			return true;
		}
		
		return false;
	}
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.teleport(mob.getLocation().add( new Location(mob.getWorld(), 0, Math.random() * 24 + 12, 0) ));
	}
	
	public EnderPhantom()
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
							world.spawnParticle(Particle.REVERSE_PORTAL, entity.getLocation(), 2, 0.5f, 0.5f, 0.5f, 0);
						}
					}
				}
			}
		}, 0, 2);
	}
}
