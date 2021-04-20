package er.seven.skills.abilities.Herbalism;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class HerbalismGroveTender extends Ability
{
	@Override
	public Skill getSkill() { return Skill.HERBALISM; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Grove Tender"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Wheat seeds can be used to turn dirt into grass and have a " + ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Herbalism.GroveTenderChance")) + ChatColor.GRAY + "% chance to not be consumed on use.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Herbalism.GroveTenderChance", 0.5f);
	}
	
	@Override
	public void onBlockInteract(Player player, Material material, Action action, PlayerInteractEvent event)
	{
		if (action != Action.RIGHT_CLICK_BLOCK) return;
		
		ItemStack mainHand = player.getEquipment().getItemInMainHand();
		
		if (mainHand.getType() == Material.WHEAT_SEEDS)
		{
			Block block = event.getClickedBlock();
			
			if (block.getType() == Material.DIRT && block.getRelative(BlockFace.UP).isPassable() == true)
			{
				if (!(Math.random() <= (getSkill().getLevel(player) * Main.mainConfig.getDouble("Herbalism.GroveTenderChance")) * 0.01))
				{
					mainHand.setAmount(mainHand.getAmount() - 1);
				}
				
				block.setType(Material.GRASS_BLOCK);
				block.getWorld().spawnParticle(Particle.COMPOSTER, block.getRelative(BlockFace.UP).getLocation(), 10, 0.5f, 0.0f, 0.5f);
				
				if (Skill.HERBALISM.checkSource("SEED_GRASS") == true)
		        {
					Integer reward = Skill.HERBALISM.getSourceXP("SEED_GRASS");	 
					
					Util.giveXP(player, Skill.HERBALISM, reward);
		        }
			}
		}
	}
}
