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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import er.seven.skills.Main;

public class LostSoul extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.5f; }
	
	@Override public String 		getName() 	{ return "Lost Soul"; }
	@Override public EntityType 	getType() 	{ return EntityType.VEX; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.SKELETON; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 1; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 1.0f; }
	@Override public float 	getSpeed() 				{ return 0.3f; }
	@Override public float 	getDamage() 			{ return 3; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, true); }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.SOUL_LANTERN); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.getEquipment().setItemInMainHandDropChance(1.0f);
	}
	
	public List<Biome> validBiomes = Arrays.asList(
			Biome.SOUL_SAND_VALLEY
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
	
	public LostSoul()
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
					if (world.getEnvironment() != Environment.NETHER) continue;
					
					List<LivingEntity> entities = world.getLivingEntities();
					
					for (LivingEntity entity : entities)
					{
						if (isMatching(entity))
						{
							Particle.DustOptions dust = new Particle.DustOptions(org.bukkit.Color.AQUA, 1.0f);
							world.spawnParticle(Particle.REDSTONE, entity.getLocation(), 2, 0.1f, 0.1f, 0.1f, 0, dust);
							
							dust = new Particle.DustOptions(org.bukkit.Color.GRAY, 1.0f);
							world.spawnParticle(Particle.REDSTONE, entity.getLocation(), 1, 0.2f, 0.2f, 0.2f, 0, dust);
							dust = new Particle.DustOptions(org.bukkit.Color.BLACK, 1.0f);
							world.spawnParticle(Particle.REDSTONE, entity.getLocation(), 1, 0.2f, 0.2f, 0.2f, 0, dust);
							
							world.spawnParticle(Particle.SOUL, entity.getLocation(), 1, 0.2f, 0.2f, 0.2f, 0);
						}
					}
				}
			}
		}, 0, 1);
	}
}
