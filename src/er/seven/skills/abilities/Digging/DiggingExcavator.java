package er.seven.skills.abilities.Digging;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class DiggingExcavator extends Ability
{
	@Override
	public Skill getSkill() { return Skill.DIGGING; }
	
	@Override
	public boolean isPassive() { return false; }
	
	@Override
	public String getName() { return "Excavator"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Digging speed increases greatly and shovel durability is halved.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Digging.ExcavatorStrength", 3);
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
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		if (skill != getSkill()) { return; }
		
		if (getSkill().isAbilityReady(player)) { getSkill().activateAbility(player); }
		
		if (getSkill().isAbilityActive(player))
		{
			if (Util.isAnyShovel(player.getEquipment().getItemInMainHand().getType()) == true)
			{
				Util.damageTool(new Random(), player, player.getEquipment().getItemInMainHand(), 1);
			}
		}
	}
}
