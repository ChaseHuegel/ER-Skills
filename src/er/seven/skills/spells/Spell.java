package er.seven.skills.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import er.seven.skills.Skill;
import er.seven.skills.Util;
import net.md_5.bungee.api.ChatColor;

public class Spell 
{
	//	METHODS DONT TOUCH
	public void SendFeedback(Player player, boolean reflectChat)
	{
		Util.sendActionbar(player, ChatColor.RED + "- " + getName().toUpperCase() + " -", reflectChat);
	}
	
	public boolean hasPotionEffect()
	{
		return !(getPotionEffect() == null);
	}
	
	public boolean hasCastTool(Player player)
	{
		return Util.isAnyHoe(player.getInventory().getItemInMainHand().getType());
	}
	
	public boolean hasCastComponent(Player player)
	{
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if (offHand.getType() == Material.ENCHANTED_BOOK && 
			offHand.getItemMeta().getLore().get(0).contains(getName()))
		{
			return true;
		}
		
		return false;
	}
	
	public boolean isHoldingSpell(Player player)
	{
		return ChatColor.stripColor(player.getInventory().getItemInOffHand().getItemMeta().getLore().get(0)).equalsIgnoreCase(getName());
	}
	
	public boolean canCast(Player player)
	{
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemStack offHand = player.getInventory().getItemInOffHand();
		
		if (player.getCooldown(Material.WOODEN_HOE) != 0 ||
			player.getCooldown(Material.STONE_HOE) != 0 ||
			player.getCooldown(Material.IRON_HOE) != 0 ||
			player.getCooldown(Material.GOLDEN_HOE) != 0 ||
			player.getCooldown(Material.DIAMOND_HOE) != 0 ||
			player.getCooldown(Material.NETHERITE_HOE) != 0) 
		{
			return false;
		}
		
		if (offHand.getType() == Material.ENCHANTED_BOOK && 
			Util.isAnyHoe(mainHand.getType()) &&
			offHand.getItemMeta().getLore().get(0).contains(getName()))
		{
			return true;
		}
		
		return false;
	}
	
	//	OVERRIDES
	public boolean targeted = true;
	public Integer spellLevel = 1;
	public String name = "";
	public Skill skill = Skill.WIZARDRY;
	public Integer castingCost = 0;
	public boolean damageScales = true;
	public boolean damages = true;
	public double damage = 1.0;
	public boolean effectScales = true;
	public boolean effectOnSelf = false;
	public PotionEffectType effect = null;
	public Integer effectDuration = 120;
	public Integer effectLevel = 1;
	public Sound castSound = Sound.ENTITY_ILLUSIONER_CAST_SPELL;
	public Particle castParticle = Particle.SPELL;
	public Integer castParticleCount = 2;
	public Sound hitSound = Sound.ENTITY_ITEM_BREAK;
	public Particle hitParticle = Particle.SPELL;
	public Integer hitParticleCount = 20;
	
	public boolean isTargeted() 	{ return targeted; }
	public Integer getSpellLevel()	{ return spellLevel; }
	
	public String getName() { return name; }	
	public Skill getSkill() { return skill; }
	
	public Integer 	getCastingCost() 		{ return castingCost; }
	public boolean 	doesDamageScale() 		{ return damageScales; }
	public boolean 	doesDamage() 			{ return damages; }
	public double 	getDamageStrength()		{ return damage; }
	
	public boolean 			doesEffectScale()		{ return effectScales; }
	public boolean 			doesEffectOnSelf()		{ return effectOnSelf; }
	public PotionEffectType getPotionEffect() 		{ return effect; }
	public Integer			getEffectDuration()		{ return effectDuration; }
	public Integer			getEffectLevel()		{ return effectLevel; }
	
	public Sound 		getCastSound()			{ return castSound; }
	public Particle 	getCastParticle()		{ return castParticle; }
	public Integer		getCastParticleCount()	{ return castParticleCount; }
	
	public Sound 		getHitSound()			{ return hitSound; }
	public Particle 	getHitParticle()		{ return hitParticle; }
	public Integer 		getHitParticleCount()	{ return hitParticleCount; }
	
	public void onPrecast(Player player) 
	{
		
	}
	
	public void onCast(Player player, Location location) 
	{
		
	}
	
	public void onHit(Player player, LivingEntity target) 
	{
		
	}
}
