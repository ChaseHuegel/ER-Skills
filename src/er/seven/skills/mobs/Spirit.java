package er.seven.skills.mobs;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTables;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import er.seven.skills.Main;

public class Spirit extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.3f; }
	
	@Override public String 		getName() 	{ return "Spirit"; }
	@Override public EntityType 	getType() 	{ return EntityType.ENDERMAN; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ENDERMAN; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 20; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 1.0f; }
	@Override public float 	getSpeed() 				{ return 0.3f; }
	@Override public float 	getDamage() 			{ return 4; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, true); }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.setCollidable(false);
		mob.setLootTable(LootTables.EVOKER.getLootTable());
	}
	
	@Override
	public boolean checkSpawnCondition(Location loc)
	{
		if (loc.getWorld().getEnvironment() == Environment.NORMAL 
				&& loc.getWorld().getBlockAt(loc).getType() == Material.CAVE_AIR)
		{
			return true;
		}
		
		return false;
	}
	
	public Spirit()
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
					if (world.getEnvironment() != Environment.NORMAL) continue;
					
					List<LivingEntity> entities = world.getLivingEntities();
					
					for (LivingEntity entity : entities)
					{
						if (isMatching(entity))
						{
							if (world.getBlockAt(entity.getLocation()).getLightLevel() > 7)
							{
								world.spawnParticle(Particle.CLOUD, entity.getLocation(), 10, 1, 1, 1, 0);
								entity.damage(100);
							}
						}
					}
				}
			}
		}, 0, 20);
	}
}
