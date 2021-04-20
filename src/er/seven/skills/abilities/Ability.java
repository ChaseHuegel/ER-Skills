package er.seven.skills.abilities;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import er.seven.skills.Skill;
import er.seven.skills.Util;
import net.md_5.bungee.api.ChatColor;

public class Ability 
{
	//	METHODS DONT TOUCH
	public void SendFeedback(Player player, boolean reflectChat)
	{
		Util.sendActionbar(player, ChatColor.RED + "- " + getName().toUpperCase() + " -", reflectChat);
	}
	
	//	OVERRIDES
	public boolean isPassive() { return true; }
	
	public String getName() { return ""; }
	public String getDescription(Player player) { return ""; }
	public Skill getSkill() { return Skill.ACROBATICS; }
	
	public void registerConfigVars(YamlConfiguration yml)
	{
		
	}
	
	public void onActivate(Player player)
	{
		
	}
	
	public void onDeactivate(Player player)
	{
		
	}
	
	public void onReady(Player player)
	{
		
	}
	
	public void onUnready(Player player)
	{
		
	}
	
	public void onCharacterUpdate(Player player)
	{
		
	}
	
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		
	}
	
	public void onPlace(Player player, Material material, BlockPlaceEvent event) 
	{
		
	}
	
	public void onAttack(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		
	}
	
	public void onAttackFinal(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		
	}
	
	public void onAttacked(Player player, Entity attacker, PlayerInventory equipment, EntityDamageByEntityEvent event) 
	{
		
	}
	
	public void onAttackedFinal(Player player, Entity attacker, PlayerInventory equipment, EntityDamageByEntityEvent event) 
	{
		
	}
	
	public void onDamaged(Player player, EntityDamageEvent event) 
	{
		
	}
	
	public void onEntityInteract(Player player, EntityType entity, PlayerInteractEntityEvent event) 
	{
		
	}
	
	public void onBlockInteract(Player player, Material material, Action action, PlayerInteractEvent event) 
	{
		
	}
	
	public void onKill(Player player, Entity victim, EntityDeathEvent event) 
	{
		
	}
	
	public void onProjectileHit(Player shooter, EntityType projectile, Material material, ProjectileHitEvent event) 
	{
		
	}
	
	public void onEnchant(Player player, EnchantItemEvent event) 
	{
		
	}
	
	public void onHeal(Player player, EntityRegainHealthEvent event) 
	{
		
	}
	
	public void onBreed(Player player, LivingEntity entity, EntityBreedEvent event) 
	{
		
	}
	
	public void onTame(Player player, LivingEntity entity, EntityTameEvent event) 
	{
		
	}
	
	public void onFishing(Player player, State state, PlayerFishEvent event) 
	{
		
	}

	public void onCraft(Player player, Skill skill, Material result, InventoryClickEvent event) 
	{
		
	}

	public void onPlant(Player player, Material material, BlockPlaceEvent event) 
	{
		
	}
}
