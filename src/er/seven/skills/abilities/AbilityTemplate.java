package er.seven.skills.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import er.seven.skills.Skill;

public class AbilityTemplate extends Ability
{
	@Override
	public Skill getSkill() { return Skill.MINING; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return ""; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "";
	}
	
	@Override
	public void onActivate(Player player)
	{
		
	}
	
	@Override
	public void onDeactivate(Player player)
	{
		
	}
	
	@Override
	public void onCharacterUpdate(Player player)
	{
		
	}
	
	@Override
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		
	}
}
