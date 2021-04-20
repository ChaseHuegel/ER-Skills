package er.seven.skills.abilities.Woodcutting;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class WoodcuttingLumberjack extends Ability
{
	@Override
	public Skill getSkill() { return Skill.WOODCUTTING; }
	
	@Override
	public boolean isPassive() { return false; }
	
	@Override
	public String getName() { return "Lumberjack"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Chopping speed increases greatly and axe durability is halved. Additionally axes instantly destroy leaves without losing durability.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Woodcutting.LumberjackStrength", 5);
	}
	
	@Override
	public void onReady(Player player)
	{
		Util.sendActionbar(player, ChatColor.GOLD + "You ready " + ChatColor.BLUE + getName(), true);
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 3, 1);
	}
	
	@Override
	public void onUnready(Player player)
	{
		Util.sendActionbar(player, ChatColor.GRAY + "You steady yourself", true);
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 3, 0.6f);
	}
	
	@Override
	public void onActivate(Player player)
	{
		SendFeedback(player, true);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3, 0.6f);
		Util.addAbilityBuff(player, getSkill());
	}
	
	@Override
	public void onDeactivate(Player player)
	{
		Util.sendActionbar(player, ChatColor.GRAY + "You exhausted yourself", true);
		Util.removeAbilityBuff(player, getSkill());
	}
	
	@Override
	public void onBlockInteract(Player player, Material material, Action action, PlayerInteractEvent event)
	{
		if (getSkill().isAbilityActive(player) && action == Action.LEFT_CLICK_BLOCK && Util.isLeaves(material))
		{
			event.getClickedBlock().breakNaturally();
		}
	}
	
	@Override
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{		
		if (skill != getSkill()) { return; }
		
		if (getSkill().isAbilityReady(player)) { getSkill().activateAbility(player); }
		
		if (getSkill().isAbilityActive(player))
		{
			if (Util.isAnyAxe(player.getEquipment().getItemInMainHand().getType()) == true)
			{
				Util.damageTool(new Random(), player, player.getEquipment().getItemInMainHand(), 1);
			}
		}
	}
}
