package er.seven.skills.mobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class RPGMobs  implements Listener
{
	List<Monster> customMobs = Arrays.asList(
			//	End mobs
			new EnderPhantom(),
			new Shulkman(),
			
			//	Ghosts
			new LostSoul(),
			new PossessedObject(),
			new Haunt(),
			new Spirit(),
			
			//	Goblins
			new Goblin(),
			
			//	Pigmen
			new Pigman(),
			new PigmanWarrior(),
			new PigmanHunter(),
			
			//	Animals
			new Rat(),
			new WildWolf(),
			new BrownBear(),
			new BlackBear(),
			new Warthog(),
			
			//	Skeletons
			new Barebones(),
			new Rattlebones(),
			new SkeletonArcher(),
			new SkeletonWarrior(),
			new SkeletonKnight(),
			new CursedSkeleton(),
			
			//	Zombies
			new Undead(),
			new UndeadShambler(),
			new UndeadMiner(),
			new UndeadKnight()
			);
	
	List<SpawnReason> validSpawnReasons = Arrays.asList( 
			SpawnReason.NATURAL,
			SpawnReason.SPAWNER,
			SpawnReason.SPAWNER_EGG,
			SpawnReason.NETHER_PORTAL,
			SpawnReason.PATROL,
			SpawnReason.RAID,
			SpawnReason.REINFORCEMENTS,
			SpawnReason.SILVERFISH_BLOCK );
	
	List<EntityType> validMobs = Arrays.asList( 
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
			EntityType.PILLAGER,
			EntityType.VINDICATOR,
			EntityType.VEX,
			EntityType.PHANTOM,
			EntityType.SLIME,
			EntityType.WITCH,
			EntityType.WITHER_SKELETON,
			EntityType.RAVAGER,
			EntityType.GHAST,
			EntityType.GUARDIAN,
			EntityType.EVOKER,
			EntityType.ZOMBIE_VILLAGER,
			EntityType.STRAY,
			EntityType.MAGMA_CUBE,
			EntityType.ENDERMITE,
			EntityType.SILVERFISH,
			EntityType.WITHER,
			EntityType.ENDER_DRAGON );
	
	public Integer GetMobCount(World world)
	{
//		List<World> worlds = Main.Instance().getServer().getWorlds();
		Integer count = 0;
		
//		for (World world : worlds)
//		{
//			for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class))
//			{
//				if (validMobs.contains(entity.getType()))
//				{
//					count++;
//				}
//			}
//		}
		
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
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onMobSpawn(CreatureSpawnEvent event)
	{		
		if (validMobs.contains(event.getEntity().getType()) == false) return;
		
		LivingEntity entity = event.getEntity();
		if ( !(entity instanceof Mob) ) return;
		Mob mob = null;
		
		if (validSpawnReasons.contains(event.getSpawnReason()) == false) { return; }
		boolean canSpawn = false;
		
		if (GetMobCount(event.getLocation().getWorld()) >= Main.Instance().maxMobCount) { event.setCancelled(true); return; }
		
		for (int i = 0; i < customMobs.size(); i++)
		{
			if (customMobs.get(i).getReplaceType() == event.getEntityType() && 
				Math.random() <= customMobs.get(i).getSpawnChance() &&
				customMobs.get(i).checkSpawnCondition(event.getLocation()) == true)
			{
				entity.remove();
				mob = customMobs.get(i).spawn(event.getLocation());
				canSpawn = true;
				break;
			}
		}
		
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
