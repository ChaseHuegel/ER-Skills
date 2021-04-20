package er.seven.skills.abilities.Farming;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class XFarmingHarvestSeason extends Ability
{
	@Override
	public Skill getSkill() { return Skill.FARMING; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Harvest Season"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Harvesting crops with a hoe has a " + ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Farming.HarvestSeasonChance")) + ChatColor.GRAY + "% chance to harvest nearby crops in a radius depending on your hoe.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Farming.HarvestSeasonChance", 1.0f);
		yml.addDefault("Farming.HarvestSeasonRangeWood", 0);
		yml.addDefault("Farming.HarvestSeasonRangeStone", 1);
		yml.addDefault("Farming.HarvestSeasonRangeIron", 1);
		yml.addDefault("Farming.HarvestSeasonRangeDiamond", 2);
		yml.addDefault("Farming.HarvestSeasonRangeGold", 3);
		yml.addDefault("Farming.HarvestSeasonRangeNetherite", 4);
	}
	
	@Override
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		if (skill != getSkill() || Util.isAnyHoe(player.getEquipment().getItemInMainHand().getType()) == false) { return; }
		
		if (Math.random() <= (skill.getLevel(player) * Main.mainConfig.getDouble("Farming.HarvestSeasonChance")) * 0.01)
		{
			player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, event.getBlock().getLocation(), 1, 0.1f, 0.0f, 0.1f);
			player.getWorld().playSound(event.getBlock().getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
			
			int range = 1;
			switch (player.getEquipment().getItemInMainHand().getType())
			{
			case WOODEN_HOE: range = Main.mainConfig.getInt("Farming.HarvestSeasonRangeWood"); break;
			case STONE_HOE: range = Main.mainConfig.getInt("Farming.HarvestSeasonRangeStone"); break;
			case IRON_HOE: range = Main.mainConfig.getInt("Farming.HarvestSeasonRangeIron"); break;
			case DIAMOND_HOE: range = Main.mainConfig.getInt("Farming.HarvestSeasonRangeDiamond"); break;
			case GOLDEN_HOE: range = Main.mainConfig.getInt("Farming.HarvestSeasonRangeGold"); break;
			case NETHERITE_HOE: range = Main.mainConfig.getInt("Farming.HarvestSeasonRangeNetherite"); break;
			default: break;
			}
			
			for (int x = -range; x < range; x++)
			{
				for (int z = -range; z < range; z++)
				{
					Block thisBlock = event.getBlock().getRelative(x, 0, z);
					
					if (Tag.CROPS.getValues().contains(thisBlock.getType()))
					{
						BlockBreakEvent newEvent = new BlockBreakEvent(thisBlock, player);
						Bukkit.getServer().getPluginManager().callEvent((Event)newEvent);
						thisBlock.breakNaturally();
					}
				}
			}
		}
	}
}
