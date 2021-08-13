package er.seven.skills;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Lectern;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.aim.coltonjgriswold.sga.api.SimpleGui;
import com.codingforcookies.armorequip.ArmorEquipEvent;

import er.seven.skills.Magic.ScrollType;
import er.seven.skills.abilities.*;
import er.seven.skills.abilities.Digging.*;
import er.seven.skills.abilities.Farming.*;
import er.seven.skills.abilities.Herbalism.*;
import er.seven.skills.abilities.Mining.*;
import er.seven.skills.abilities.Unarmed.*;
import er.seven.skills.abilities.Unarmored.*;
import er.seven.skills.abilities.Vitality.*;
import er.seven.skills.abilities.Woodcutting.*;
import er.seven.skills.spells.Spell;
import er.seven.skills.abilities.OneHanded.*;
import er.seven.skills.abilities.Archery.*;
import er.seven.skills.abilities.LightWeapons.*;
import er.seven.skills.abilities.HeavyWeapons.*;
import er.seven.skills.abilities.Wizardry.*;
//import er.seven.extras.PlayerBrewEvent;
//import er.seven.extras.PotionInfo;
import net.md_5.bungee.api.ChatColor;

public class SkillsListener  implements Listener
{
	public List<Location> placedBlocks = new ArrayList<Location>();

	public Ability[] abilities = new Ability[] 
    		{
    				//	COMBAT
    				new ArcheryProficiency(),
    				new HeavyWeaponsProficiency(),
    				new LightWeaponsProficiency(),
    				new OneHandedHeavyBlows(),
    				new UnarmedOldOneTwo(),
    				new UnarmedIronFists(),
    				new UnarmedShillelagh(),
    				new UnarmedMartialArts(),
    				new UnarmedDisarm(),
    				new UnarmedDeflect(),
    				new WizardrySpellcasting(),
    				
    				//	DEFENSE
    				new UnarmoredDefense(),
    				new UnarmoredLightFooted(),
    				new VitalityConstitution(),
    				
    				//	TRADE
    				new DiggingExcavator(),
    				new DiggingDualPurpose(),
    				new DiggingEfficiency(),
    				new DiggingArchaeology(),
    				new FarmingDualPurpose(),
    				new FarmingEfficiency(),
    				new MiningDoubleTime(),
    				new MiningDualPurpose(),
    				new MiningEfficiency(),
    				new MiningProspector(),
    				new WoodcuttingLumberjack(),
    				new WoodcuttingDualPurpose(),
    				new WoodcuttingEfficiency(),
    				new HerbalismScytheSweep(),
    				new HerbalismDualPurpose(),
    				new HerbalismEfficiency(),
    				new HerbalismGroveTender(),
    				new HerbalismBountifulHarvest()
    		};
	
	public SkillsListener()
	{
		RunScheduler();
	}
	
	public void RunScheduler()
	{
		// Clear block tracking
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(Main.Instance(), new Runnable() 
		{
			@Override
			public void run()
			{
				if (placedBlocks.size() > 0)
				{
					Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Cleared block tracking.");
					placedBlocks.clear();
				}
			}
		}, 8000, 8000);
	}
	
	@EventHandler
    public void onJoin(PlayerJoinEvent event) 
	{
		Player player = event.getPlayer();
		
		BossBar bar = Bukkit.createBossBar(player.getName() + "Target", BarColor.RED, BarStyle.SEGMENTED_10);
        bar.addPlayer(player);
        bar.setVisible(false);
        Main.Instance().healthBar.put(player, bar);
        Main.Instance().healthBarLifetime.put(player, 0);
        Main.Instance().healthBarTarget.put(player, null);
        
        SimpleGui gui = new SimpleGui();
		gui.addPages( gui.createPage(ChatColor.GOLD + "Skills", 54) );
        Main.Instance().skillGUI.put(player, gui);
		
		for (Skill skill : Skill.values())
        {
			SkillPair skillPair = new SkillPair(player, skill);
	
	        BossBar bar2 = Bukkit.createBossBar(skill.toString(), BarColor.GREEN, BarStyle.SEGMENTED_10);
	        bar2.addPlayer(player);
	        bar2.setVisible(false);

	        Main.Instance().skillBar.put(skillPair, bar2);
	        Main.Instance().skillBarLifetime.put(skillPair, 0);
	        
	        if (Main.Instance().skillCooldown.get(skillPair) == null)
	        {
	        	Main.Instance().skillCooldown.put(skillPair, 0);
	        	Main.Instance().skillTimer.put(skillPair, 0);
	        }
        }
        
        Main.loadPlayerData(player);
        
        new BukkitRunnable()
        {
            public void run()
            {
            	Util.updateCharacter(player);
            }
        }.runTaskLater(Main.Instance(), 1);
    }
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		Util.removeAbilityBuffs(player);
		
		new BukkitRunnable()
        {
            public void run()
            {
            	Util.updateCharacter(player);
            }
        }.runTaskLater(Main.Instance(), 1);
	}
	
	@EventHandler
    public void onQuit(PlayerQuitEvent event) 
	{
		Player player = event.getPlayer();
		Main.savePlayerData(player);
		Util.removeAbilityBuffs(player);
		
		//	Cleanup player data
		for (Skill skill : Skill.values())
        {
			Main.Instance().skillBar.remove(new SkillPair(player, skill));
			Main.Instance().skillBarLifetime.remove(new SkillPair(player, skill));
        }
		
		Main.Instance().healthBar.remove(player);
		Main.Instance().healthBarLifetime.remove(player);
		Main.Instance().healthBarTarget.remove(player);
		
		Main.Instance().skillGUI.remove(player);
		
		Main.Instance().combatLevel.remove(player);
		Main.Instance().totalLevel.remove(player);
		Main.Instance().totalXP.remove(player);
		Main.Instance().miningLevel.remove(player);
		Main.Instance().miningXP.remove(player);
		Main.Instance().diggingLevel.remove(player);
		Main.Instance().diggingXP.remove(player);
		Main.Instance().woodcuttingLevel.remove(player);
		Main.Instance().woodcuttingXP.remove(player);
		Main.Instance().herbalismLevel.remove(player);
		Main.Instance().herbalismXP.remove(player);
		Main.Instance().farmingLevel.remove(player);
		Main.Instance().farmingXP.remove(player);
		Main.Instance().husbandryLevel.remove(player);
		Main.Instance().husbandryXP.remove(player);
		Main.Instance().fishingLevel.remove(player);
		Main.Instance().fishingXP.remove(player);
		Main.Instance().buildingLevel.remove(player);
		Main.Instance().buildingXP.remove(player);
		Main.Instance().vitalityLevel.remove(player);
		Main.Instance().vitalityXP.remove(player);
		Main.Instance().unarmoredLevel.remove(player);
		Main.Instance().unarmoredXP.remove(player);
		Main.Instance().acrobaticsLevel.remove(player);
		Main.Instance().acrobaticsXP.remove(player);
		Main.Instance().cartographyLevel.remove(player);
		Main.Instance().cartographyXP.remove(player);
		Main.Instance().alchemyLevel.remove(player);
		Main.Instance().alchemyXP.remove(player);
		Main.Instance().cookingLevel.remove(player);
		Main.Instance().cookingXP.remove(player);
		Main.Instance().archeryLevel.remove(player);
		Main.Instance().archeryXP.remove(player);
		Main.Instance().lightWeaponsLevel.remove(player);
		Main.Instance().lightWeaponsXP.remove(player);
		Main.Instance().heavyWeaponsLevel.remove(player);
		Main.Instance().heavyWeaponsXP.remove(player);
		Main.Instance().bardLevel.remove(player);
		Main.Instance().bardXP.remove(player);
		Main.Instance().dualWieldLevel.remove(player);
		Main.Instance().dualWieldXP.remove(player);
		Main.Instance().oneHandedLevel.remove(player);
		Main.Instance().oneHandedXP.remove(player);
		Main.Instance().shieldsLevel.remove(player);
		Main.Instance().shieldsXP.remove(player);
		Main.Instance().enchantingLevel.remove(player);
		Main.Instance().enchantingXP.remove(player);
		Main.Instance().heavyArmorLevel.remove(player);
		Main.Instance().heavyArmorXP.remove(player);
		Main.Instance().mediumArmorLevel.remove(player);
		Main.Instance().mediumArmorXP.remove(player);
		Main.Instance().lightArmorLevel.remove(player);
		Main.Instance().lightArmorXP.remove(player);
		Main.Instance().smithingLevel.remove(player);
		Main.Instance().smithingXP.remove(player);
		Main.Instance().inventionLevel.remove(player);
		Main.Instance().inventionXP.remove(player);
		Main.Instance().stealthLevel.remove(player);
		Main.Instance().stealthXP.remove(player);
		Main.Instance().tailoringLevel.remove(player);
		Main.Instance().tailoringXP.remove(player);
		Main.Instance().unarmedLevel.remove(player);
		Main.Instance().unarmedXP.remove(player);
		Main.Instance().pietyLevel.remove(player);
		Main.Instance().pietyXP.remove(player);
		Main.Instance().witchcraftLevel.remove(player);
		Main.Instance().witchcraftXP.remove(player);
		Main.Instance().wizardryLevel.remove(player);
		Main.Instance().wizardryXP.remove(player);
		Main.Instance().woodworkingLevel.remove(player);
		Main.Instance().woodworkingXP.remove(player);
    }
	
	@EventHandler
	public void onEquip(ArmorEquipEvent event)
	{
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(Main.Instance(), new Runnable() 
		{
			@Override
			public void run()
			{
				Util.updateCharacter(event.getPlayer());
			}
		}, 2);
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event)
	{
		ItemStack item = event.getItemDrop().getItemStack();
		
		if (Util.isAnyAxe(item.getType()) || Util.isAnyPickaxe(item.getType()) || Util.isAnyShovel(item.getType()))
		{
			Util.removeAbilityBuffs(item);
		}
	}
	
	@EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event)
    {
		Entity attacker = event.getDamager();
		Entity victim = event.getEntity();
		double damage = event.getDamage();
		
		if (attacker instanceof Projectile)
		{
			Projectile projectile = (Projectile)attacker;
			if (projectile.getShooter() instanceof Player)
			{
				Player player = (Player)projectile.getShooter();
				
				if (Main.Instance().spellProjectiles.get(projectile) != null && victim instanceof LivingEntity)
				{
					Magic.TriggerProjectileHit(player, (LivingEntity)victim, projectile, Main.Instance().spellProjectiles.get(projectile));
					Main.Instance().spellProjectiles.remove(projectile);
					projectile.remove();
					event.setCancelled(true);
				}
				else if (attacker.getType() == EntityType.ARROW || attacker.getType() == EntityType.SPECTRAL_ARROW || attacker.getType() == EntityType.TRIDENT)
				{
					ItemStack mainHand = player.getEquipment().getItemInMainHand();
					
					//	Set the weapon type
					Material weapon;
					if (mainHand != null) weapon = mainHand.getType(); else weapon = Material.AIR;
					if (attacker.getType() == EntityType.TRIDENT) { weapon = Material.TRIDENT; }
					
					//////////////////////////////////
					//	TRIGGER ABLITIES
					for (int n = 0; n < abilities.length; n++) { abilities[n].onAttack(player, victim, weapon, event); }
					//////////////////////////////////
					
					//////////////////////////////////
					//	TRIGGER ABLITIES
					for (int n = 0; n < abilities.length; n++) { abilities[n].onAttackFinal(player, victim, weapon, event); }
					//////////////////////////////////
					
					//	Retrieve damage and baseline XP reward
					damage = event.getDamage();
					float reward = (float)(damage);
					
					reward += (player.getLocation().distance(victim.getLocation()) / 6);
					
					//	Perform critical hits
					if (Math.random() <= Main.mainConfig.getDouble("Combat.CritChance"))
					{
						damage *= 2.0f;
						Util.sendActionbar(player, ChatColor.RED + "- CRITICAL HIT -");
						
						Location location = player.getLocation();
						double size = victim.getHeight();
						World world = player.getWorld();
						
						PotionEffect critEffect = null;
						if (mainHand.getType() == Material.BOW)
						{
							critEffect = new PotionEffect(PotionEffectType.SLOW, 120, 0);
						}
						else if (mainHand.getType() == Material.CROSSBOW)
						{
							critEffect = new PotionEffect(PotionEffectType.SLOW, 120, 1);
						}
						else if (mainHand.getType() == Material.TRIDENT)
						{
							critEffect = new PotionEffect(PotionEffectType.SLOW, 120, 2);
						}
						
						if (critEffect != null) { ((LivingEntity)victim).addPotionEffect(critEffect); }
						
						world.playSound(location, Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 1);
						world.spawnParticle(Particle.CRIT, location.getX(), location.getY(), location.getZ(), 20, size * 0.3, size * 0.3, size * 0.3);
					}
					
					//	Reward XP
					if (weapon == Material.TRIDENT)
					{
						Util.giveXP(player, Skill.HEAVY_WEAPONS, reward * 0.5f);
						Util.giveXP(player, Skill.ARCHERY, reward * 0.5f);
					}
					else
					{
						Util.giveXP(player, Skill.ARCHERY, reward);
					}
				}
				
				//	Damage feedback
//				player.sendTitle( "", ChatColor.RED + "-" + Math.round(damage), 10, 10, 10);
			}
		}
		else if (attacker instanceof Player)
		{
			Player player = (Player)attacker;
			PlayerInventory equipment = player.getInventory();
			
			Material weapon;
			if (equipment.getItemInMainHand() != null) weapon = equipment.getItemInMainHand().getType(); else weapon = Material.AIR;
			
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onAttack(player, victim, weapon, event); }
			//////////////////////////////////
			
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onAttackFinal(player, victim, weapon, event); }
			//////////////////////////////////
			
			//	Retrieve damage
			damage = event.getDamage();
			
			//	Handle critical hits
			if (Math.random() <= Main.mainConfig.getDouble("Combat.CritChance"))
			{
				damage *= 2.0f;
				Util.sendActionbar(player, ChatColor.RED + "- CRITICAL HIT -");
				
				Location location = player.getLocation();
				double size = victim.getHeight();
				World world = player.getWorld();
				
				PotionEffect critEffect = null;
				ItemStack mainHand = equipment.getItemInMainHand();
				if (mainHand == null || mainHand.getType() == Material.AIR || mainHand.getType() == Material.STICK)
				{
					critEffect = new PotionEffect(PotionEffectType.CONFUSION, 120, 0);
				}
				else if (Util.isAnyPickaxe(mainHand.getType()) == true || Util.isAnyAxe(mainHand.getType()) == true)
				{
					critEffect = new PotionEffect(PotionEffectType.WEAKNESS, 120, 0);
				}
				else if (Util.isAnySword(mainHand.getType()) == true)
				{
					critEffect = new PotionEffect(PotionEffectType.WITHER, 120, 0);
				}
				else if (Util.isAnyShovel(mainHand.getType()) == true)
				{
					critEffect = new PotionEffect(PotionEffectType.CONFUSION, 120, 0);
				}
				else if (Util.isAnyHoe(mainHand.getType()) == true)
				{
					critEffect = new PotionEffect(PotionEffectType.BLINDNESS, 120, 0);
				}
				
				if (critEffect != null) { ((LivingEntity)victim).addPotionEffect(critEffect); }
				
				world.playSound(location, Sound.ENTITY_PLAYER_ATTACK_CRIT, 6, 1);
				world.spawnParticle(Particle.CRIT, location.getX(), location.getY(), location.getZ(), 20, size * 0.3, size * 0.3, size * 0.3);
			}
			
			//	Grant XP
			Skill wieldSkill = Util.getWieldSkill(player);			
			if (wieldSkill != Skill.ARCHERY)
			{
				Skill mainSkill = Util.getMainHandSkill(player);
				Skill offSkill = Util.getOffHandSkill(player);
				
				Util.giveXP(player, wieldSkill, damage);
				
				//	Anything except one handed should split XP.
				float xpMod = 0.5f;
				if (wieldSkill == Skill.ONE_HANDED) xpMod = 1.0f;
					
				Util.giveXP(player, mainSkill, damage * xpMod);
				// Because unarmed is both main and wield, it should not also give offhand XP
				if (offSkill != Skill.UNARMED) Util.giveXP(player, offSkill, damage * xpMod);
			}
			
			//	Defensive combat skills leech XP
			Util.giveXP(player, Skill.VITALITY, damage * 0.5f, true);
			
			if (equipment.getHelmet() == null) 															{ Util.giveXP(player, Skill.UNARMORED, damage * 0.25f, true); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getHelmet().getType().toString()))			{ Util.giveXP(player, Skill.LIGHT_ARMOR, damage * 0.25f, true); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getHelmet().getType().toString()))		{ Util.giveXP(player, Skill.MEDIUM_ARMOR, damage * 0.25f, true); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getHelmet().getType().toString()))			{ Util.giveXP(player, Skill.HEAVY_ARMOR, damage * 0.25f, true); }
			else { Util.giveXP(player, Skill.UNARMORED, damage * 0.25f); }	//	 Default to being unarmored
			
			if (equipment.getChestplate() == null)														{ Util.giveXP(player, Skill.UNARMORED, damage * 0.25f, true); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getChestplate().getType().toString()))		{ Util.giveXP(player, Skill.LIGHT_ARMOR, damage * 0.25f, true); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getChestplate().getType().toString()))	{ Util.giveXP(player, Skill.MEDIUM_ARMOR, damage * 0.25f, true); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getChestplate().getType().toString()))		{ Util.giveXP(player, Skill.HEAVY_ARMOR, damage * 0.25f, true); }
			else { Util.giveXP(player, Skill.UNARMORED, damage * 0.25f); }	//	 Default to being unarmored
			
			if (equipment.getLeggings() == null) 														{ Util.giveXP(player, Skill.UNARMORED, damage * 0.25f, true); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getLeggings().getType().toString()))		{ Util.giveXP(player, Skill.LIGHT_ARMOR, damage * 0.25f, true); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getLeggings().getType().toString()))		{ Util.giveXP(player, Skill.MEDIUM_ARMOR, damage * 0.25f, true); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getLeggings().getType().toString()))		{ Util.giveXP(player, Skill.HEAVY_ARMOR, damage * 0.25f, true); }
			else { Util.giveXP(player, Skill.UNARMORED, damage * 0.25f); }	//	 Default to being unarmored
			
			if (equipment.getBoots() == null) 															{ Util.giveXP(player, Skill.UNARMORED, damage * 0.25f, true); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getBoots().getType().toString()))			{ Util.giveXP(player, Skill.LIGHT_ARMOR, damage * 0.25f, true); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getBoots().getType().toString()))			{ Util.giveXP(player, Skill.MEDIUM_ARMOR, damage * 0.25f, true); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getBoots().getType().toString()))			{ Util.giveXP(player, Skill.HEAVY_ARMOR, damage * 0.25f, true); }
			else { Util.giveXP(player, Skill.UNARMORED, damage * 0.25f); }	//	 Default to being unarmored
			
			//	Damage feedback
//			player.sendTitle( "", ChatColor.RED + "-" + Math.round(damage), 10, 10, 10);
		}
		
		if (victim instanceof Player)
		{
			Player player = (Player)victim;
			PlayerInventory equipment = player.getInventory();
			
			float reward = (float)(event.getDamage());
			
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onAttacked(player, attacker, equipment, event); }
			//////////////////////////////////
			
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onAttackedFinal(player, attacker, equipment, event); }
			//////////////////////////////////
			
			//	Retrieve damage and reward
			damage = event.getDamage();
			
			Util.giveXP(player, Skill.VITALITY, reward);
			
			if (equipment.getHelmet() == null) 															{ Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getHelmet().getType().toString()))			{ Util.giveXP(player, Skill.LIGHT_ARMOR, reward * 0.25f); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getHelmet().getType().toString()))		{ Util.giveXP(player, Skill.MEDIUM_ARMOR, reward * 0.25f); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getHelmet().getType().toString()))			{ Util.giveXP(player, Skill.HEAVY_ARMOR, reward * 0.25f); }
			else { Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }	//	 Default to being unarmored
			
			if (equipment.getChestplate() == null)														{ Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getChestplate().getType().toString()))		{ Util.giveXP(player, Skill.LIGHT_ARMOR, reward * 0.25f); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getChestplate().getType().toString()))	{ Util.giveXP(player, Skill.MEDIUM_ARMOR, reward * 0.25f); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getChestplate().getType().toString()))		{ Util.giveXP(player, Skill.HEAVY_ARMOR, reward * 0.25f); }
			else { Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }	//	 Default to being unarmored
			
			if (equipment.getLeggings() == null) 														{ Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getLeggings().getType().toString()))		{ Util.giveXP(player, Skill.LIGHT_ARMOR, reward * 0.25f); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getLeggings().getType().toString()))		{ Util.giveXP(player, Skill.MEDIUM_ARMOR, reward * 0.25f); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getLeggings().getType().toString()))		{ Util.giveXP(player, Skill.HEAVY_ARMOR, reward * 0.25f); }
			else { Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }	//	 Default to being unarmored
			
			if (equipment.getBoots() == null) 															{ Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }
			else if (Skill.LIGHT_ARMOR.checkSource(equipment.getBoots().getType().toString()))			{ Util.giveXP(player, Skill.LIGHT_ARMOR, reward * 0.25f); }
			else if (Skill.MEDIUM_ARMOR.checkSource(equipment.getBoots().getType().toString()))			{ Util.giveXP(player, Skill.MEDIUM_ARMOR, reward * 0.25f); }
			else if (Skill.HEAVY_ARMOR.checkSource(equipment.getBoots().getType().toString()))			{ Util.giveXP(player, Skill.HEAVY_ARMOR, reward * 0.25f); }
			else { Util.giveXP(player, Skill.UNARMORED, reward * 0.25f); }	//	 Default to being unarmored
			
			if (player.isBlocking() == true) 
			{ 
				Util.giveXP(player, Skill.SHIELDS, reward * 1.5f);
			}
		}
		
		event.setDamage(damage);
		
		if ((attacker instanceof LivingEntity) && (victim instanceof LivingEntity))
		{
			final Entity atker = attacker;
			final Entity vict = victim;
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.Instance(), new Runnable() 
			{
				@Override
				public void run()
				{
					if (atker instanceof Player)
					{
						Main.Instance().healthBarTarget.put((Player)atker, Util.getTargetOf((Player)atker));
						Util.sendHealthBar((Player)atker, (LivingEntity)vict);
					}
					else if (vict instanceof Player)
					{
						Util.sendHealthBar((Player)vict, (LivingEntity)atker);
					}
				}
			}, 1);
		}
    }
	
	@EventHandler
    public void onDamage(EntityDamageEvent event)
    {
		DamageCause cause = event.getCause();
		Entity victim = event.getEntity();
		
		if (victim instanceof Player)
		{
			Player player = (Player)victim;
			
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onDamaged(player, event); }
			//////////////////////////////////
			
			if (cause != DamageCause.ENTITY_ATTACK && cause != DamageCause.ENTITY_EXPLOSION && cause != DamageCause.ENTITY_SWEEP_ATTACK)
			{
				float reward = (float)(event.getDamage());
				Util.giveXP(player, Skill.VITALITY, reward);
			}
			
			if (cause == DamageCause.FALL)
			{
				float reward = (float)(event.getDamage() * 3);
				Util.giveXP(player, Skill.ACROBATICS, reward);
			}
		}
    }
	
	@EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        //Cast spells
//  		if (Magic.hasSpellComponentsReady(player))
//  		{
//  			Magic.TryCast(player);
//			event.setCancelled(true);
//			return;
//  		}
        
        if (placedBlocks.contains(block.getLocation()) == false)
        {
	        for (Skill skill : Skill.values())
	        {
	        	if (skill != Skill.BUILDING && skill != Skill.WOODWORKING && skill != Skill.SMITHING && skill.checkSource(block.getType().toString()) == true)
	        	{	        		
	    			//////////////////////////////////
	    			//	TRIGGER ABLITIES
	    			for (int n = 0; n < abilities.length; n++) { abilities[n].onBreak(player, skill, event); }
	    			//////////////////////////////////
	    			
        			BlockData blockData = block.getBlockData();
        			
        			//	Don't give XP if a plant isn't grown
	        		if (block.getType() != Material.SUGAR_CANE && block.getType() != Material.CACTUS &&
	        				blockData.getAsString().contains("age=") == true &&
	        				((Ageable)blockData).getAge() != ((Ageable)blockData).getMaximumAge())
	        		{
	        			return;
	        		}
	        		
	        		Integer reward = skill.getSourceXP(block.getType().toString());
	        		
	        		//	Bonus XP when using a tool to harvest
	        		if (Util.isAnyHoe(player.getEquipment().getItemInMainHand().getType()) == true)
	        		{
	        			reward += skill.getSourceXP("HARVEST_TOOL");
						if (Math.random() <= 0.5f) { Util.damageTool(new Random(), player, player.getEquipment().getItemInMainHand(), 1); }
	        		}
	        		
	            	Util.giveXP(player, skill, reward);
	            }
	        }
        }
        else
        {
        	placedBlocks.remove(block.getLocation());
        }
    }
	
	@EventHandler
	public void onShoot(EntityShootBowEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			Player player = (Player)event.getEntity();
			
			if (Magic.isCastingTool(player.getInventory().getItemInMainHand()) || Magic.isCastingTool(player.getInventory().getItemInOffHand()))
			{
				event.setCancelled(true);
			}
			
			if (Magic.hasSpellComponentsReady(player))
			{
				Magic.TryCast(player, event.getForce());
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
		Player player = event.getPlayer();
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemStack offHand = player.getInventory().getItemInOffHand();
		Block block = event.getClickedBlock();
		
		//Cast spells
//		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
//		{
//			Magic.TryCast(player);
//			if (Magic.hasSpellComponentsReady(player))
//			{
//				event.setCancelled(true);
//				return;
//			}
//		}
		
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			boolean canUseSkill = true;
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && Main.Instance().getConfig().getStringList("blocked").contains(block.getType().toString()))
			{
				canUseSkill = false;
			}
			
			if (canUseSkill)
			{
				if (player.isSneaking() == false)
				{
					if (Util.isAnyPickaxe(mainHand.getType()) == true) { if (Skill.MINING.readyAbility(player)) {event.setCancelled(true); return;} }
					else if (Util.isAnyAxe(mainHand.getType()) == true) { if (Skill.WOODCUTTING.readyAbility(player)) {event.setCancelled(true); return;} }
					else if (Util.isAnyShovel(mainHand.getType()) == true) { if (Skill.DIGGING.readyAbility(player)) {event.setCancelled(true); return;} }
					else { if (Util.getWieldSkill(player).readyAbility(player)) { event.setCancelled(true); return; } }
				}
				else
				{
					if (Util.getWieldSkill(player).readyAbility(player)) { event.setCancelled(true); return; }
				}
			}
			
			//	Teleport Scrolls
			if (mainHand.getType() == Material.ENCHANTED_BOOK)
			{
				boolean didTeleport = false;
				
				if (ChatColor.stripColor(mainHand.getItemMeta().getLore().get(0)).equalsIgnoreCase("Teleport Home I"))
				{
					if (player.getBedSpawnLocation() != null)
					{
						Main.Instance().lastTeleportLocation.put(player, player.getLocation());
						player.getWorld().spawnParticle(Particle.REVERSE_PORTAL, player.getLocation(), 60, 1f, 1f, 1f);
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
						player.teleport(player.getBedSpawnLocation());
						didTeleport = true;
					}
					else
					{
						player.sendActionBar(ChatColor.RED + "You do not have a valid bed spawn.");
					}
				}
				else if (ChatColor.stripColor(mainHand.getItemMeta().getLore().get(0)).equalsIgnoreCase("Teleport Spawn I"))
				{
					Main.Instance().lastTeleportLocation.put(player, player.getLocation());
					player.getWorld().spawnParticle(Particle.REVERSE_PORTAL, player.getLocation(), 60, 1f, 1f, 1f);
					player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
					player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
					didTeleport = true;
				}
				else if (ChatColor.stripColor(mainHand.getItemMeta().getLore().get(0)).equalsIgnoreCase("Teleport Back I"))
				{
					if (Main.Instance().lastTeleportLocation.get(player) != null)
					{
						player.getWorld().spawnParticle(Particle.REVERSE_PORTAL, player.getLocation(), 60, 1f, 1f, 1f);
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
						player.teleport(Main.Instance().lastTeleportLocation.get(player));
						didTeleport = true;
					}
					else
					{
						player.sendActionBar(ChatColor.RED + "You have not recently teleported anywhere.");
					}
				}
				
				if (didTeleport)
				{
					player.getInventory().setItemInMainHand(null);
					player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 60, 1f, 1f, 1f);
					player.getWorld().playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1f, 1f);
				}
			}
		}
				
		if (block != null && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK))
		{
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onBlockInteract(player, block.getType(), event.getAction(), event); }
			//////////////////////////////////
			
			//	Spell crafting
			if (block.getType() == Material.LECTERN)
			{				
				Lectern lectern = (Lectern)block.getState();
				
				if (lectern.getInventory().getItem(0) != null && lectern.getInventory().getItem(0).getType() != Material.AIR)
				{
					Spell spell = Main.Instance().magic.TryCraftGetSpell(mainHand.getType());
					
					if (!player.getInventory().contains(Material.WRITABLE_BOOK))
						player.sendActionBar(ChatColor.RED + "You must have a book and quill to craft spells.");
					else if (spell != null && player.getTotalExperience() - spell.getCastingCost() <= 0)
						player.sendActionBar(ChatColor.RED + "You do not have enough experience to craft this spell.");
					else
					{
						if (spell != null)
						{
							player.getWorld().dropItemNaturally(lectern.getLocation().add(0f, 0.5f, 0f), Magic.CreateSpellTome(spell));
							mainHand.setAmount( mainHand.getAmount() - 1 );
							int bookSlot = player.getInventory().first(Material.WRITABLE_BOOK);
							player.getInventory().getContents()[bookSlot].setAmount( player.getInventory().getContents()[bookSlot].getAmount() - 1 );
							player.getWorld().playSound(lectern.getLocation(), Sound.ENTITY_EVOKER_PREPARE_WOLOLO, 1f, 1f);
							player.getWorld().playSound(lectern.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1f, 0.25f);
							
							player.giveExp( -spell.getCastingCost() );

							event.setCancelled(true);
						}
						//	Try scroll crafting
						else if (mainHand.getType() == Material.ENDER_PEARL || mainHand.getType() == Material.COMPASS || mainHand.getType() == Material.CHORUS_FRUIT)
						{
							if (mainHand.getType() == Material.ENDER_PEARL)
								player.getWorld().dropItemNaturally(lectern.getLocation().add(0f, 0.5f, 0f), Magic.CreateScroll(ScrollType.TELEPORT_HOME));
							else if (mainHand.getType() == Material.CHORUS_FRUIT)
								player.getWorld().dropItemNaturally(lectern.getLocation().add(0f, 0.5f, 0f), Magic.CreateScroll(ScrollType.TELEPORT_BACK));
							else if (mainHand.getType() == Material.COMPASS)
								player.getWorld().dropItemNaturally(lectern.getLocation().add(0f, 0.5f, 0f), Magic.CreateScroll(ScrollType.TELEPORT_SPAWN));
							
							mainHand.setAmount( mainHand.getAmount() - 1 );
							int bookSlot = player.getInventory().first(Material.WRITABLE_BOOK);
							player.getInventory().getContents()[bookSlot].setAmount( player.getInventory().getContents()[bookSlot].getAmount() - 1 );
							player.getWorld().playSound(lectern.getLocation(), Sound.ENTITY_EVOKER_PREPARE_WOLOLO, 1f, 1f);
							player.getWorld().playSound(lectern.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1f, 0.25f);
							
							event.setCancelled(true);
						}
					}
				}
			}
			
			if (mainHand.getType() == Material.BOOK && mainHand.getItemMeta().getDisplayName().equalsIgnoreCase("Holy Book")) 
			{
	        	boolean purified = false;
	        	Block origin = player.getWorld().getBlockAt(player.getLocation());
	        	Block tempBlock;
	        	
	        	for (int x = -4; x < 4; x++) {
	        		for (int y = -4; y < 4; y++) {
	        			for (int z = -4; z < 4; z++) {
	        				tempBlock = origin.getRelative(x, y, z);
	        				
	        				if (tempBlock.getType() == Material.NETHERRACK)
	        				{
	        					tempBlock.setType(Material.DIRT, false);
	        					purified = true;
	        				}
	        				else if (tempBlock.getType() == Material.SOUL_SAND)
	        				{
	        					tempBlock.setType(Material.SAND, false);
	        					purified = true;
	        				}
	        				else if (tempBlock.getType() == Material.LAVA)
	        				{
	        					tempBlock.setType(Material.WATER, true);
	        					purified = true;
	        				}
	        			}
	        		}
	        	}
	        	
	            if (purified)
	        	{	        		
	            	player.getWorld().spawnParticle(Particle.COMPOSTER, player.getLocation(), 60, 1f, 1f, 1f);
	        		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 0.75f);
	        	}
				
	            mainHand.setAmount( mainHand.getAmount() - 1 );
				Integer reward = Skill.PIETY.getSourceXP("PURIFY");
				Util.giveXP(player, Skill.PIETY, reward);
			}
			else if (block.getType() == Material.SWEET_BERRY_BUSH)
			{
				if ( ((Ageable)block.getBlockData()).getAge() >= 2)
				{					
					for (Skill skill : Skill.values())
			        {
						if (skill == Skill.HERBALISM || skill == Skill.FARMING)
		        		{
	        				if (skill.checkSource(block.getType().toString()) == true)
	        	            {
	        	        		Integer reward = skill.getSourceXP(block.getType().toString());	        		
	        	        		
	    		        		//	Bonus XP when using a tool to harvest
	    		        		if (Util.isAnyHoe(mainHand.getType()) == true)
	    		        		{
	    		        			reward += skill.getSourceXP("HARVEST_TOOL");
	    		        			if ((new Random()).nextFloat() <= 0.5f)
	    							{
	    		        				Util.damageTool(new Random(), player, player.getInventory().getItemInMainHand(), 1);
	    							}
	    		        		}
	        	        		
	        	        		Util.giveXP(player, skill, reward);
	        	            }
		        		}
			        }
				}
			}
			else if (mainHand == null || mainHand.getType() == Material.AIR || Util.isAnyHoe(mainHand.getType()) == true)
			{
				//	Harvesting grape vines
				if (block.getType() == Material.VINE)
        		{		        			
        			if (Util.isGrapeVine(block) == true)
        			{
        				for (Skill skill : Skill.values())
        		        {
        					if (skill == Skill.HERBALISM || skill == Skill.FARMING)
        	        		{
		        				if (skill.checkSource(block.getType().toString()) == true)
		        	            {
		        	        		Integer reward = skill.getSourceXP(block.getType().toString());	        		
		        	        		
	        		        		//	Bonus XP when using a tool to harvest
	        		        		if (Util.isAnyHoe(mainHand.getType()) == true)
	        		        		{
	        		        			reward += skill.getSourceXP("HARVEST_TOOL");
	        		        			if ((new Random()).nextFloat() <= 0.5f)
	        							{
	        		        				Util.damageTool(new Random(), player, player.getInventory().getItemInMainHand(), 1);
	        							}
	        							
	        		        		}
		        	        		
		        	        		Util.giveXP(player, skill, reward);
		        	            }
        	        		}
        		        }
        				
        				block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.SWEET_BERRIES, (new Random()).nextInt(4) + 2));
        				block.setType(Material.AIR, true);
        			}
        		}
			}
			else if (Util.isAnyAxe(mainHand.getType()) == true && Util.isLog(block.getType()) == true)
			{
				if (Skill.WOODCUTTING.checkSource("STRIP") == true)
		        {
					Integer reward = Skill.WOODCUTTING.getSourceXP("STRIP");	 
					
					Util.giveXP(player, Skill.WOODCUTTING, reward);
		        }
			}
			else if (Util.isAnyShovel(mainHand.getType()) == true || Util.isAnyShovel(offHand.getType()) == true)
			{
				if (block.getType() == Material.GRASS_BLOCK)
				{
					if (Skill.DIGGING.checkSource("DIGPATH") == true)
			        {
						Integer reward = Skill.DIGGING.getSourceXP("DIGPATH");	 
						
						Util.giveXP(player, Skill.DIGGING, reward);
			        }
				}
				else if (block.getType() == Material.FARMLAND && block.getRelative(BlockFace.UP).getType() == Material.AIR)
				{
					block.setType(Material.DIRT);
					
					if ((new Random()).nextFloat() <= 0.5f)
					{
						if (Util.isAnyShovel(mainHand.getType()) == true)
						{
							Util.damageTool(new Random(), player, player.getInventory().getItemInMainHand(), 1);
						}
						else if (Util.isAnyShovel(offHand.getType()) == true)
						{
							Util.damageTool(new Random(), player, player.getInventory().getItemInOffHand(), 1);
						}
					}
					
					if (Skill.DIGGING.checkSource("DIGPATH") == true)
			        {
						Integer reward = Skill.DIGGING.getSourceXP("DIGPATH");	 
						
						Util.giveXP(player, Skill.DIGGING, reward);
			        }
				}
			}
			else if (Util.isAnyHoe(mainHand.getType()) == true || Util.isAnyHoe(offHand.getType()) == true)
			{
				if (block.getType() == Material.GRASS_BLOCK || block.getType() == Material.DIRT)
				{
					if (Skill.FARMING.checkSource("TILLING") == true)
			        {
						Integer reward = Skill.FARMING.getSourceXP("TILLING");	 
						
						Util.giveXP(player, Skill.FARMING, reward);
			        }
				}
			}
			else if (mainHand.getType() == Material.BONE && player.isSneaking() == true)
			{
				if (block.getType() == Material.DIRT || block.getType() == Material.GRASS_BLOCK)
				{
					if (Skill.PIETY.checkSource("BURY_BONE") == true)
			        {
						mainHand.setAmount(mainHand.getAmount() - 1);
						Integer reward = Skill.PIETY.getSourceXP("BURY_BONE");	 
						
						Util.giveXP(player, Skill.PIETY, reward);
			        }
				}
			}
			else if (player.isSneaking() == true && 
					mainHand.getType() == Material.SKELETON_SKULL ||
					mainHand.getType() == Material.SKELETON_WALL_SKULL ||
					mainHand.getType() == Material.WITHER_SKELETON_SKULL || 
					mainHand.getType() == Material.WITHER_SKELETON_WALL_SKULL)
			{
				if (block.getType() == Material.DIRT || block.getType() == Material.GRASS_BLOCK)
				{
					if (Skill.PIETY.checkSource("BURY_SKULL") == true)
			        {
						mainHand.setAmount(mainHand.getAmount() - 1);
						Integer reward = Skill.PIETY.getSourceXP("BURY_SKULL");	 
						
						Util.giveXP(player, Skill.PIETY, reward);
			        }
				}
			}
			else if (player.isSneaking() == true && 
					mainHand.getType() == Material.PLAYER_HEAD || 
							mainHand.getType() == Material.PLAYER_WALL_HEAD)
			{
				if (block.getType() == Material.DIRT || block.getType() == Material.GRASS_BLOCK)
				{
					if (Skill.PIETY.checkSource("BURY_SKULL") == true)
			        {
						mainHand.setAmount(mainHand.getAmount() - 1);
						Integer reward = Skill.PIETY.getSourceXP("BURY_HEAD");	 
						
						Util.giveXP(player, Skill.PIETY, reward);
			        }
				}
			}	 
		}
    }
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
		Player player = (Player)event.getWhoClicked();
		PlayerInventory playerInventory = player.getInventory();
		Inventory inventory = event.getInventory();
		InventoryView view = event.getView();
		ItemStack clickedItem = event.getCurrentItem();
		
		if (inventory.getType() == InventoryType.ANVIL)
		{
			if (event.getRawSlot() == 2 && clickedItem != null && clickedItem.getType() != Material.AIR)
			{
				Integer repairCost = ((AnvilInventory) inventory).getRepairCost();
				Integer reward = repairCost;
				ItemStack baseItem = view.getItem(0);
				ItemStack workItem = view.getItem(1);
				boolean isMaterialRepair = true;
				
				if (reward <= 0 || player.getLevel() < repairCost) { event.setCancelled(true); return; }
				
				for (Skill skill : Skill.values())
				{
					if (skill.checkSource(workItem.getType().toString()) == true)
					{
						isMaterialRepair = false;
						break;
					}
				}
				
//				if (clickedItem.getEnchantments().isEmpty() == false)
//				{
//					if (Util.removeItemFromInventory(player.getInventory(), Material.LAPIS_LAZULI, repairCost) == false)
//					{
//						Util.sendActionbar(player, ChatColor.RED + "Working with enchantments costs lapis lazuli.");
//						((AnvilInventory) inventory).setRepairCost(0);
//						event.setCancelled(true); return;
//					}
//				}
				
//				if (player.getLevel() >= repairCost && isMaterialRepair == false)
//				{
//					if (event.isLeftClick() == true && event.isShiftClick() == false)
//					{
//						event.setCursor(clickedItem);
//						inventory.setItem(0, new ItemStack(Material.AIR));
//						inventory.setItem(1, new ItemStack(Material.AIR));
//						inventory.setItem(2, new ItemStack(Material.AIR));
//						player.setLevel(player.getLevel() - repairCost);
//						event.setCancelled(true);
//					}
//					else if (event.isLeftClick() == true && event.isShiftClick() == true)
//					{
//						Integer emptySlot = playerInventory.firstEmpty();
//						if (emptySlot >= 0)
//						{
//							playerInventory.setItem(emptySlot, clickedItem);
//							inventory.setItem(0, new ItemStack(Material.AIR));
//							inventory.setItem(1, new ItemStack(Material.AIR));
//							inventory.setItem(2, new ItemStack(Material.AIR));
//							player.setLevel(player.getLevel() - repairCost);
//							event.setCancelled(true);
//						}
//					}
//					else if (event.getHotbarButton() >= 0)
//					{
//						if (playerInventory.getItem(event.getHotbarButton()) == null)
//						{
//							playerInventory.setItem(event.getHotbarButton(), clickedItem);
//							inventory.setItem(0, new ItemStack(Material.AIR));
//							inventory.setItem(1, new ItemStack(Material.AIR));
//							inventory.setItem(2, new ItemStack(Material.AIR));
//							player.setLevel(player.getLevel() - repairCost);
//							event.setCancelled(true);
//						}
//					}
//					else
//					{
//						event.setCursor(new ItemStack(Material.AIR));
//						player.setLevel(player.getLevel() - repairCost);
//						event.setCancelled(true);
//					}
//				}
				
				if (baseItem.getType() == Material.ENCHANTED_BOOK || workItem.getType() == Material.ENCHANTED_BOOK)
				{
					Util.giveXP(player, Skill.ENCHANTING, reward);
				}
				else
				{
					reward *= 2;
					
					for (Skill skill : Skill.values())
			        {
			        	if (skill != Skill.BUILDING && skill != Skill.LIGHT_ARMOR && skill != Skill.LIGHT_WEAPONS && skill != Skill.HEAVY_WEAPONS && 
			        			skill != Skill.HEAVY_ARMOR && skill != Skill.MEDIUM_ARMOR && skill != Skill.SHIELDS && skill != Skill.UNARMED && skill != Skill.ARCHERY &&
			        			skill.checkSource(baseItem.getType().toString()) == true)
			            {
			        		Util.giveXP(player, skill, reward);
			            }
			        }
				}
			}
		}
		else if (inventory.getType() == InventoryType.FURNACE ||
				inventory.getType() == InventoryType.BLAST_FURNACE ||
				inventory.getType() == InventoryType.SMOKER)
		{
			if (event.getRawSlot() == 2)
			{
				for (Skill skill : Skill.values())
		        {
		        	if (skill != Skill.BUILDING && skill != Skill.LIGHT_ARMOR && skill != Skill.LIGHT_WEAPONS && skill != Skill.HEAVY_WEAPONS && 
		        			skill != Skill.HEAVY_ARMOR && skill != Skill.MEDIUM_ARMOR && skill != Skill.SHIELDS && skill != Skill.UNARMED && skill != Skill.ARCHERY &&
		        			skill != Skill.MINING && skill != Skill.DIGGING && skill != Skill.WOODCUTTING &&
		        			skill.checkSource(clickedItem.getType().toString()) == true)
		            {
		    			//////////////////////////////////
		    			//	TRIGGER ABLITIES
		    			for (int n = 0; n < abilities.length; n++) { abilities[n].onCraft(player, skill, clickedItem.getType(), event); }
		    			//////////////////////////////////
		    			
		        		Integer reward = skill.getSourceXP(clickedItem.getType().toString());
		        		reward *= clickedItem.getAmount();
		        		Util.giveXP(player, skill, reward);
		            }
		        }
			}
		}
    }
	
	@EventHandler
    public void onCraft(CraftItemEvent event)
    {
		Player player = (Player)event.getView().getPlayer();
		ItemStack result = event.getRecipe().getResult();
		Integer amount = Util.getCraftItemAmount(result.getType(), event);
		
		player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);
		
		for (Skill skill : Skill.values())
        {
			if (skill == Skill.SMITHING && (result.getType() == Material.GOLD_INGOT || result.getType() == Material.IRON_INGOT))
			{
				continue;
				
			}
        	if (skill != Skill.BUILDING && skill != Skill.MINING && skill != Skill.DIGGING && skill != Skill.WOODCUTTING && skill != Skill.FARMING && skill != Skill.LIGHT_ARMOR && skill != Skill.LIGHT_WEAPONS && skill != Skill.HEAVY_WEAPONS && skill != Skill.HEAVY_ARMOR && skill != Skill.MEDIUM_ARMOR && skill != Skill.SHIELDS && skill != Skill.UNARMED && skill != Skill.ARCHERY && skill.checkSource(result.getType().toString()) == true)
            {
    			//////////////////////////////////
    			//	TRIGGER ABLITIES
    			for (int n = 0; n < abilities.length; n++) { abilities[n].onCraft(player, skill, result.getType(), event); }
    			//////////////////////////////////
        		
        		Integer reward = skill.getSourceXP(result.getType().toString());
        		reward *= amount;
        		
            	Util.giveXP(player, skill, reward);
            }
        }
    }
	
//	@EventHandler
//	public void onAnvilPrepare(PrepareAnvilEvent event)
//	{
//		Player player = (Player)event.getView().getPlayer();
//		
//		String renameText = event.getInventory().getRenameText();
//		double cost = event.getInventory().getRepairCost();
//		
//		ItemStack baseItem = event.getView().getItem(0);
//		ItemStack workItem = event.getView().getItem(1);
//		
//		ItemMeta baseItemMeta = baseItem.getItemMeta();
//		ItemMeta workItemMeta = workItem.getItemMeta();
//		
//		ItemStack result = baseItem.clone();
//		ItemMeta resultMeta = result.getItemMeta();
//		
//		org.bukkit.inventory.meta.Damageable baseItemDmg = (org.bukkit.inventory.meta.Damageable)baseItemMeta;
//		
//		//	Display skill bar
//		if (baseItem.getType() != Material.AIR)
//		{
//			if (baseItem.getType() == Material.ENCHANTED_BOOK || workItem.getType() == Material.ENCHANTED_BOOK)
//			{
//				Util.sendSkillBar(player, Skill.ENCHANTING);
//			}
//			else
//			{
//				for (Skill skill : Skill.values())
//		        {
//		        	if (skill != Skill.BUILDING && skill != Skill.LIGHT_ARMOR && skill != Skill.LIGHT_WEAPONS && skill != Skill.HEAVY_WEAPONS && 
//		        			skill != Skill.HEAVY_ARMOR && skill != Skill.MEDIUM_ARMOR && skill != Skill.SHIELDS && skill != Skill.UNARMED && skill != Skill.ARCHERY &&
//		        			skill.checkSource(baseItem.getType().toString()) == true)
//		            {
//		        		Util.sendSkillBar(player, skill);
//		            }
//		        }
//			}
//			
//			if (workItem.getType() != Material.AIR)
//			{
//				if (baseItem.getType() != workItem.getType() && 
//					baseItem.getType() != Material.ENCHANTED_BOOK && 
//					workItem.getType() != Material.ENCHANTED_BOOK) { return; }
//				else if (baseItem.getType() == Material.ENCHANTED_BOOK && 
//						workItem.getType() == Material.ENCHANTED_BOOK) { return; }
//				
//				cost = 1;
//				
//				if (baseItem.getType() != Material.ENCHANTED_BOOK && baseItem.getType().getMaxDurability() == 0)
//				{
//					event.setResult(new ItemStack(Material.AIR));
//					return;
//				}
//				else
//				{
//					//	Clear enchants from the result
//					Map<Enchantment, Integer> baseItemEnchantMap;
//					if (baseItemMeta instanceof EnchantmentStorageMeta) { baseItemEnchantMap = ((EnchantmentStorageMeta)baseItemMeta).getStoredEnchants(); }
//					else { baseItemEnchantMap = baseItemMeta.getEnchants(); }
//					
//					if (baseItemEnchantMap.isEmpty() == false)
//					{
//						for (Map.Entry<Enchantment, Integer> baseEnchantment : baseItemEnchantMap.entrySet())
//						{
//							Enchantment enchant = baseEnchantment.getKey();	
//							if (baseItemMeta instanceof EnchantmentStorageMeta) { ((EnchantmentStorageMeta)resultMeta).removeStoredEnchant(enchant); }
//							else { resultMeta.removeEnchant(enchant); }
//						}
//					}
//				}
//				
//				//	Add repair cost nbt to anvil cost?
//				
//				//	Do repairs
//				if (baseItemDmg.getDamage() > 0)
//				{
//					cost += 2;
////					double damageReduction = workItem.getType().getMaxDurability() - workItemDmg.getDamage();
////					double damage = baseItemDmg.getDamage() - damageReduction;
////					if (damage < 0) { damage = 0; }
////					Util.setItemDamage(result, (int) damage);
//				}
//				
//				//	Do renaming
//				if (renameText != "")
//				{
//					cost += 1;
//					resultMeta.setDisplayName(renameText);
//					result.setItemMeta(resultMeta);
//				}
//				
//				//	Do enchanting
//				Map<Enchantment, Integer> enchantMap;
//				if (workItemMeta instanceof EnchantmentStorageMeta) { enchantMap = ((EnchantmentStorageMeta)workItemMeta).getStoredEnchants(); }
//				else { enchantMap = workItemMeta.getEnchants(); }
//				
//				if (enchantMap.isEmpty() == false)
//				{
//					Map<Enchantment, Integer> workItemEnchants;
//					if (workItemMeta instanceof EnchantmentStorageMeta) { workItemEnchants = ((EnchantmentStorageMeta)workItemMeta).getStoredEnchants(); }
//					else { workItemEnchants = workItemMeta.getEnchants(); }					
//					
//					for (Map.Entry<Enchantment, Integer> workEnchantment : workItemEnchants.entrySet())
//					{
//						Enchantment workEnchant = workEnchantment.getKey();
//						int workLevel = workEnchantment.getValue();
//						
//						Map<Enchantment, Integer> baseItemEnchants;
//						if (baseItemMeta instanceof EnchantmentStorageMeta) { baseItemEnchants = ((EnchantmentStorageMeta)baseItemMeta).getStoredEnchants(); }
//						else { baseItemEnchants = baseItemMeta.getEnchants(); }	
//						
//						if (baseItemEnchants.isEmpty() == false)
//						{							
//							for (Map.Entry<Enchantment, Integer> baseEnchantment : baseItemEnchants.entrySet())
//							{
//								Enchantment baseEnchant = baseEnchantment.getKey();
//								int baseLevel = baseEnchantment.getValue();
//								
//								if (workEnchant == baseEnchant)
//								{
//									if (workLevel == baseLevel)
//									{
//										if (workLevel < workEnchant.getMaxLevel())
//										{
//											workLevel += 1;
//											//break;
//										}
//									}
//									else if (workLevel < baseLevel)
//									{
//										workLevel = baseLevel;
//										//break;
//									}
//								}
//								else
//								{
//									result.addUnsafeEnchantment(baseEnchant, baseLevel);
//								}
//							}
//						}
//						
//						double multiplier = 1.5f;
//						//	Assign multiplier based on the enchantment
//						cost += workLevel * multiplier;
//						
//						result.addUnsafeEnchantment(workEnchant, workLevel);
//					}			
//				}
//				
//				event.setResult(result);
//			}
//			else
//			{
////				cost = Math.pow(cost, 1.355);
//				cost *= 2;
//			}
//		}
//		
//		cost = Math.round(cost);
//		if (cost <= 0) { cost = 1; }
//		event.getInventory().setRepairCost( (int)cost );
//		final int anvilCost = (int)cost;
//		
//		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
//		scheduler.scheduleSyncDelayedTask(Main.Instance(), new Runnable() 
//		{
//			@Override
//			public void run()
//			{
//				event.getInventory().setRepairCost( anvilCost );
//			}
//		}, 3);
//	}
	
	@EventHandler
    public void onEnchant(EnchantItemEvent event)
    {
		Player player = (Player)event.getEnchanter();
		
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < abilities.length; n++) { abilities[n].onEnchant(player, event); }
		//////////////////////////////////
		
		Integer reward = (int) (Math.pow(event.getExpLevelCost(), 1.355) * 2); 
		Util.giveXP(player, Skill.ENCHANTING, reward);
    }
	
//	@EventHandler
//    public void onPrepareEnchant(PrepareItemEnchantEvent event)
//    {
//		EnchantmentOffer[] offers = event.getOffers();
//		
//		for (EnchantmentOffer offer : offers)
//		{
////			offer.setCost( (int) Math.round( Math.pow(offer.getCost(), 1.355) ) );
//			offer.setCost( offer.getCost() * 2 );
//		}
//    }
	
	@EventHandler
    public void onClickEntity(PlayerInteractEntityEvent event)
    {
		Entity clickedEntity = event.getRightClicked();
		Player player = event.getPlayer();
		Material mainHand = player.getInventory().getItemInMainHand().getType();
		
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < abilities.length; n++) { abilities[n].onEntityInteract(player, clickedEntity.getType(), event); }
		//////////////////////////////////
		
		if (mainHand == Material.SHEARS)
		{
			if (clickedEntity.getType() == EntityType.SHEEP && ((Sheep)clickedEntity).isSheared() == false)	//	Shearing
			{
				for (Skill skill : Skill.values())
		        {
					if (skill.checkSource("SHEARING") == true)
			        {
						Integer reward = skill.getSourceXP("SHEARING");	 
						
						Util.giveXP(player, skill, reward);
			        }
		        }
			}
			else if (clickedEntity.getType() == EntityType.CHICKEN)	// Plucking
			{
				clickedEntity.getWorld().dropItem(clickedEntity.getLocation(), new ItemStack(Material.FEATHER, 1));
				((Damageable)clickedEntity).damage(1);
				Util.damageTool(new Random(), player, player.getInventory().getItemInMainHand(), 1);
				
				for (Skill skill : Skill.values())
		        {
					if (skill.checkSource("PLUCKING") == true)
			        {
						Integer reward = skill.getSourceXP("PLUCKING");	 
						
						Util.giveXP(player, skill, reward);
			        }
		        }
			}
		}
		else if (mainHand == Material.BUCKET)	//	 Milking
		{
			if (clickedEntity.getType() == EntityType.COW)
			{
				for (Skill skill : Skill.values())
		        {
					if (skill.checkSource("MILKING") == true)
			        {
						Integer reward = skill.getSourceXP("MILKING");	 
						
						Util.giveXP(player, skill, reward);
			        }
		        }
			}
		}
    }
	
	@EventHandler
    public void onDeath(EntityDeathEvent event)
    {
		Entity victim = event.getEntity();
		
		if (victim instanceof LivingEntity)
		{
			if (victim instanceof Player)
			{
				Util.removeAbilityBuffs((Player)victim);
			}
			
			if (((LivingEntity) victim).getKiller() instanceof Player)
			{
				Player player = ((LivingEntity) victim).getKiller();
				
				//////////////////////////////////
				//	TRIGGER ABLITIES
				for (int n = 0; n < abilities.length; n++) { abilities[n].onKill(player, victim, event); }
				//////////////////////////////////
				
				if (Skill.PIETY.checkSource(victim.getType().toString()) == true)
		        {
					Integer reward = Skill.PIETY.getSourceXP(victim.getType().toString());	 
					
					Util.giveXP(player, Skill.PIETY, reward);
		        }
			}
		}
    }
	
	@EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
		Projectile projectile = event.getEntity();
		ProjectileSource shooter = projectile.getShooter();
		Block block = event.getHitBlock();
		
		if (block != null)
		{			
			if (block.getType() == Material.HAY_BLOCK && projectile.getType() == EntityType.ARROW)
			{
				if (shooter instanceof Player)
				{
					Player player = (Player)shooter;
					
					//////////////////////////////////
					//	TRIGGER ABLITIES
					for (int n = 0; n < abilities.length; n++) { abilities[n].onProjectileHit((Player)shooter, projectile.getType(), block.getType(), event); }
					//////////////////////////////////
					
					Integer reward = (int)(projectile.getLocation().distance(player.getLocation()) / 6);	 
					
					Util.giveXP(player, Skill.ARCHERY, reward);
				}
			}
		}
    }
	
	@EventHandler
    public void onHeal(EntityRegainHealthEvent event)
    {
		Entity entity = event.getEntity();
		
		if (entity instanceof Player)
		{
			Player player = (Player)entity;
			
			if (event.getRegainReason() == RegainReason.SATIATED && player.getHealth() >= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()/2d)
				event.setAmount(0d);
			
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onHeal(player, event); }
			//////////////////////////////////
		}
    }
	
	@EventHandler
    public void onFishing(PlayerFishEvent event)
    {
		Player player = event.getPlayer();
		PlayerFishEvent.State state = event.getState();
		
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < abilities.length; n++) { abilities[n].onFishing(player, state, event); }
		//////////////////////////////////
		
		if (Skill.FISHING.checkSource(state.toString()) == true)
        {
			Integer reward = Skill.FISHING.getSourceXP(state.toString());	 
			
			reward += event.getExpToDrop();
			
			Util.giveXP(player, Skill.FISHING, reward);
        }
    }
	
	@EventHandler
    public void onBreed(EntityBreedEvent event)
    {
		if (event.getBreeder() != null)
		{
			Player player = (Player)event.getBreeder();
			
			//////////////////////////////////
			//	TRIGGER ABLITIES
			for (int n = 0; n < abilities.length; n++) { abilities[n].onBreed(player, event.getEntity(), event); }
			//////////////////////////////////
			
			if (Skill.HUSBANDRY.checkSource(event.getEntity().getType().toString()) == true)
	        {
				Integer reward = Skill.HUSBANDRY.getSourceXP(event.getEntity().getType().toString());	 
				
				Util.giveXP(player, Skill.HUSBANDRY, reward);
	        }
		}
    }
	
	@EventHandler
    public void onTame(EntityTameEvent event)
    {
		Player player = (Player)event.getOwner();
		
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < abilities.length; n++) { abilities[n].onTame(player, event.getEntity(), event); }
		//////////////////////////////////
		
		if (Skill.HUSBANDRY.checkSource(event.getEntity().getType().toString()) == true)
        {
			Integer reward = Skill.HUSBANDRY.getSourceXP(event.getEntity().getType().toString());	 
			
			Util.giveXP(player, Skill.HUSBANDRY, reward);
        }
    }
	
	@EventHandler
    public void onPlace(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        //	Don't allow players to place skulls when sneaking for the purposes of burying them
        if (block.getType() == Material.SKELETON_SKULL ||
        	block.getType() == Material.SKELETON_WALL_SKULL ||
        	block.getType() == Material.PLAYER_HEAD || 
        	block.getType() == Material.PLAYER_WALL_HEAD ||
        	block.getType() == Material.WITHER_SKELETON_SKULL || 
        	block.getType() == Material.WITHER_SKELETON_WALL_SKULL)
        {
        	if (player.isSneaking() == true)
        	{			
        		event.setCancelled(true);
        		return;
        	}
        }
        
        //	If the block isn't a crop
        if (block.getBlockData().getAsString().contains("age=") == false)
        {
        	placedBlocks.add(block.getLocation());
        	
    		//////////////////////////////////
    		//	TRIGGER ABLITIES
    		for (int n = 0; n < abilities.length; n++) { abilities[n].onPlace(player, block.getType(), event); }
    		//////////////////////////////////
        	
        	if (Skill.BUILDING.checkSource(block.getType().toString()) == true)
            {
            	Util.giveXP(player, Skill.BUILDING, Skill.BUILDING.getSourceXP(block.getType().toString()));
            }
        }
        else
        {
    		//////////////////////////////////
    		//	TRIGGER ABLITIES
    		for (int n = 0; n < abilities.length; n++) { abilities[n].onPlant(player, block.getType(), event); }
    		//////////////////////////////////
        }
    }
	
	@EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event)
    {
        Entity entity = event.getEntity();
        Block block = event.getBlock();
        Material toMaterial = event.getTo();
        
        if (toMaterial == Material.AIR)
        {
        	if (placedBlocks.contains(block.getLocation()) == true)
        	{
        		entity.addScoreboardTag("PLACED");
        	}
        }
        else if (entity.getScoreboardTags().contains("PLACED") == true)
        {
        	placedBlocks.add(block.getLocation());
        }
    }
	
	@EventHandler
	public void onSpread(BlockSpreadEvent event)
	{
		if (event.getSource().getType() == Material.GRASS_BLOCK && Math.random() < 0.5f)
		{
			event.setCancelled(true);
		}
	}
}
