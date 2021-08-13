package er.seven.skills.mobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Strider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.StriderTemperatureChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.aim.coltonjgriswold.sga.api.SimpleGui;
import com.codingforcookies.armorequip.DispenserArmorListener;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.mobs.extras.*;
import net.md_5.bungee.api.ChatColor;

public class RPGMobs implements Listener
{
	Random random = new Random();
	
	List<Monster> customMobs = Arrays.asList(
			//	End mobs
			new EnderPhantom(),
			new Shulkman(),
//			new Crawler(),
			
			//	Ghosts
			new LostSoul(),
			new PossessedObject(),
			new Haunt(),
			new Spirit(),
			new Wraith(),
			new Fury(),
//			new Remnant(),
			
			//	Giantkind
			new Troll(),
			new Yeti(),
			new EarthGolem(),
			
			//	Human
			new Bandit(),
			new BanditArcher(),
			
			//	Goblins
			new Goblin(),
			
			//	Orcs
			new Orc(),
			
			//	Pigmen
			new Pigman(),
			new PigmanWarrior(),
			new PigmanHunter(),
			
			//	Beastmen
			new Beastman(),
			new BeastmanWarrior(),
			
			//	Animals
			new Rat(),
			new Termite(),
//			new Scarab(),
//			new WildWolf(),
			new BrownBear(),
			new BlackBear(),
			new GrizzlyBear(),
			new Warthog(),
//			new Scorpion(),
//			new Crab(),
			new Taurus(),
			new Tick(),
//			new Vulture(),
//			new Eagle(),
//			new Owl(),
//			new Ape(),
			
			//	Critters
			new Raccoon(),
//			new Crow(),
//			new Pigeon(),
//			new Hawk(),
			new Monkey(),
			new Frog(),
//			new GlowFly(),
//			new UmberFly(),
//			new Giraffe(),
			new OwlBeast(),
			
			//	Skeletons
			new Barebones(),
			new Rattlebones(),
//			new Forgotten(),
//			new Sunken(),
//			new Unearthed(),
			new SkeletonArcher(),
			new SkeletonWarrior(),
			new SkeletonKnight(),
			new SkeletonMage(),
			new CursedSkeleton(),
//			new EnchantedArcher(),
//			new EnchantedKnight(),
//			new EnchantedWarrior(),
			
			//	Horrors
			new Skinbag(),
			new Watcher(),
			new Eyeball(),
//			new BrainBlob(),
			new Mimic(),
//			new Lurker(),
			
			//	Zombies
			new Undead(),
			new UndeadShambler(),
			new UndeadMiner(),
			new UndeadKnight()
			);
	
	public static List<SpawnReason> validSpawnReasons = Arrays.asList( 
			SpawnReason.NATURAL
			);
//			SpawnReason.SPAWNER,
//			SpawnReason.SPAWNER_EGG,
//			SpawnReason.NETHER_PORTAL,
//			SpawnReason.PATROL,
//			SpawnReason.RAID,
//			SpawnReason.REINFORCEMENTS,
//			SpawnReason.SILVERFISH_BLOCK );
	
	public static List<EntityType> validMobs = Arrays.asList( 
			EntityType.SKELETON,
			EntityType.ZOMBIE,
			EntityType.PIGLIN,
			EntityType.ZOMBIFIED_PIGLIN,
			EntityType.CREEPER,
			EntityType.ENDERMAN,
			EntityType.SPIDER,
			EntityType.CAVE_SPIDER,
			EntityType.POLAR_BEAR,
			EntityType.WOLF,
			EntityType.BLAZE,
			EntityType.HUSK,
			EntityType.DROWNED,
			EntityType.VEX,
			EntityType.PHANTOM,
			EntityType.WITHER_SKELETON,
			EntityType.GHAST,
			EntityType.GUARDIAN,
			EntityType.EVOKER,
			EntityType.ZOMBIE_VILLAGER,
			EntityType.STRAY,
			EntityType.MAGMA_CUBE,
			EntityType.ENDERMITE,
			EntityType.SILVERFISH,
			EntityType.WITHER,
			EntityType.ENDER_DRAGON,
			EntityType.BAT );
	
	public HashMap<EntityType, List<Monster>> mobMapping = new HashMap<>();
	
	public RPGMobs()
	{
		for (int i = 0; i < customMobs.size(); i++)
		{
			if (mobMapping.get(customMobs.get(i).getReplaceType()) == null)
				mobMapping.put(customMobs.get(i).getReplaceType(), new ArrayList<Monster>());
			
			mobMapping.get(customMobs.get(i).getReplaceType()).add(customMobs.get(i));
		}
		
		//	Register mob enhancements
		Main.Instance().getServer().getPluginManager().registerEvents(new Striders(), Main.Instance());
		Main.Instance().getServer().getPluginManager().registerEvents(new Pigs(), Main.Instance());
	}
	
	public static Integer GetMobCap(World world)
	{
		if (world.getEnvironment() == Environment.NORMAL)
			return (int) (Main.Instance().maxMobCount + (Main.Instance().maxMobCount * 0.25f * world.getPlayerCount()));
		else if (world.getEnvironment() == Environment.NETHER)
			return (int) (Main.Instance().maxMobCountNether + (Main.Instance().maxMobCountNether * 0.25f * world.getPlayerCount()));
		if (world.getEnvironment() == Environment.THE_END)
			return (int) (Main.Instance().maxMobCountEnd + (Main.Instance().maxMobCountEnd * 0.25f * world.getPlayerCount()));
		
		return 0;
	}
	
	public static Integer GetMobCount(World world)
	{
		Integer count = 0;
		
		for (Mob entity : world.getEntitiesByClass(Mob.class))
		{
			if (validMobs.contains(entity.getType()))
			{
				count++;
			}
		}
		
		Main.Instance().mobCount = count;
		return count;
	}
	
	public static void ProcessTag(LivingEntity entity, String tag)
	{
		String[] set = tag.split(":");
		
		if (set.length > 1)
		{
			//	Bleeding
			if (set[0].equals("BLEED"))
			{
				Integer remainingTime = Integer.parseInt(set[1]);
				remainingTime -= 1;
				
				entity.removeScoreboardTag(tag);
				
				if (remainingTime >= 0)
				{
					entity.addScoreboardTag("BLEED:" + remainingTime.toString());
				}
				
				entity.damage(1);
			}
		}
	}
	
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event)
	{
		if ((event.getEntityType() == EntityType.PIGLIN || event.getEntityType() == EntityType.HOGLIN) && 
				event.getEntity().getWorld().getEnvironment() == Environment.NORMAL)
		{
			if (event.getEntity().getLastDamageCause() == null ||
				(event.getEntity().getLastDamageCause().getCause() != DamageCause.ENTITY_ATTACK && 
				event.getEntity().getLastDamageCause().getCause() != DamageCause.ENTITY_SWEEP_ATTACK))
			{
				if (event.getEntityType() == EntityType.PIGLIN && event.getEntity().getCustomName() != null && event.getEntity().getCustomName().contains("Pigman"))
					event.setCancelled(true);
				else if (event.getEntityType() == EntityType.HOGLIN)
					event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		if (event.getEntity().getPassengers().size() > 0)
		{
			for (int i = 0; i < event.getEntity().getPassengers().size(); i++)
			{
				if (event.getEntity().getPassengers().get(i).getType() == EntityType.SILVERFISH)
				{
					event.getEntity().getPassengers().get(i).remove();
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event)
	{
		if (event.getEntityType() == EntityType.CREEPER)
		{
			AreaEffectCloud cloud = (AreaEffectCloud)event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.AREA_EFFECT_CLOUD);
			PotionEffect effect = new PotionEffect(PotionEffectType.POISON, 220, 0);
			cloud.addCustomEffect(effect, false);
			cloud.setDuration(600);
			cloud.setRadius(3f);
			cloud.setRadiusPerTick(0.005f);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onMobSpawn(CreatureSpawnEvent event)
	{		
		if (validMobs.contains(event.getEntity().getType()) == false) return;
		
		if (event.getLocation().getWorld().getEnvironment() == Environment.NORMAL && event.getLocation().getBlock().getRelative(0, -1, 0).getLightFromBlocks() > 0)
		{
			event.setCancelled(true);
			return;
		}
		
		if (event.getLocation().getWorld().getEnvironment() == Environment.NORMAL && GetMobCount(event.getLocation().getWorld()) >= Main.Instance().maxMobCount * event.getLocation().getWorld().getPlayerCount())
		{ event.setCancelled(true); return; }
		else if (event.getLocation().getWorld().getEnvironment() == Environment.NETHER && GetMobCount(event.getLocation().getWorld()) >= Main.Instance().maxMobCountNether * event.getLocation().getWorld().getPlayerCount())
			{ event.setCancelled(true); return; }
		else if (event.getLocation().getWorld().getEnvironment() == Environment.THE_END && GetMobCount(event.getLocation().getWorld()) >= Main.Instance().maxMobCountEnd * event.getLocation().getWorld().getPlayerCount())
			{ event.setCancelled(true); return; }
		
		LivingEntity entity = event.getEntity();
		if ( !(entity instanceof Mob) ) return;
		Mob mob = null;
		
		if (validSpawnReasons.contains(event.getSpawnReason()) == false) { return; }
		boolean canSpawn = false;
		
		if (mobMapping.get(event.getEntity().getType()) != null)
		{
			List<Monster> validMonsters = new ArrayList<Monster>();
			Monster monster = null;
			
			for (int n = 0; n < mobMapping.get(event.getEntity().getType()).size(); n++)
			{
				monster = mobMapping.get(event.getEntity().getType()).get(n);
				
				if (Math.random() <= monster.getSpawnChance() && monster.checkSpawnCondition(event.getLocation()) == true)
					validMonsters.add(monster);
			}
			
			if (validMonsters.size() > 1)
			{
				monster = Util.randomMonster(random, validMonsters.toArray(new Monster[0]));
	
				entity.remove();
				mob = monster.spawn(event.getLocation());
				canSpawn = true;
			}
		}
		
//		for (int i = 0; i < customMobs.size(); i++)
//		{
//			if (customMobs.get(i).getReplaceType() == event.getEntityType() && 
//				Math.random() <= customMobs.get(i).getSpawnChance() &&
//				customMobs.get(i).checkSpawnCondition(event.getLocation()) == true)
//			{
//				entity.remove();
//				mob = customMobs.get(i).spawn(event.getLocation());
//				canSpawn = true;
//				break;
//			}
//		}
//		
		if (canSpawn == false) canSpawn = validMobs.contains(entity.getType());
		
		if (canSpawn)
		{			
			if (mob == null) { mob = (Mob)entity; }
			
			int playerCount = 0;
			int levelTotal = 0;
			int level = 0;
			int levelVariance = 0;
			int lowestLevel = 100;
			int highestLevel = 1;
			double nearestDistance = 1000.0f;
			double distance = 0.0f;
			
			Player nearestPlayer = null;
			List<Player> players = mob.getWorld().getPlayers();
			for (int i = 0; i < players.size(); i++)
			{
				int thisLevel = Skill.VITALITY.getLevel( players.get(i) );
				levelTotal += thisLevel;
				playerCount++;
				
				if (thisLevel > highestLevel) highestLevel = thisLevel;
				if (thisLevel < lowestLevel) lowestLevel = thisLevel;
				
				distance = mob.getLocation().distance(players.get(i).getLocation());
				if (distance  <= nearestDistance )
				{
					nearestDistance = distance;
					nearestPlayer = players.get(i);
				}
			}
			
			levelVariance = highestLevel - lowestLevel;
			
			if (playerCount < 1) playerCount = 1;
			if (levelTotal < 1) levelTotal = 1;
			if (levelVariance < 8) levelVariance = 8;
			
//			level = (int) ( Math.round(levelTotal / playerCount) + Math.round( (Math.random() * levelVariance) - (levelVariance * 0.5f) ) );
			level = (int) ( Skill.VITALITY.getLevel(nearestPlayer) + Math.round( (Math.random() * levelVariance) - (levelVariance * 0.5f) ) );
			if (level < 1) level = 1;
			
			double scale = 1 + (level * 0.01f);
			
//			mob.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue( mob.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue() * scale );
//			
//			mob.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue( mob.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue() * scale );
//			
//			mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue( mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * scale );
//			mob.setHealth( mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() );
			
//			mob.setCustomName(ChatColor.GOLD + "" + level + " " + ChatColor.WHITE + mob.getName());
			mob.setCustomName(ChatColor.WHITE + mob.getName());
			mob.setCustomNameVisible(false);
			mob.setRemoveWhenFarAway(true);
		}
	}
}
