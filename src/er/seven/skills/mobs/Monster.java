package er.seven.skills.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import org.bukkit.entity.Hoglin;
import org.bukkit.entity.LivingEntity;

public class Monster 
{
	public boolean isMatching(LivingEntity entity)
	{
		return (entity.getType() == getType());
	}
	
	public boolean doesDespawn() { return true; }
	public float getSpawnChance() { return 1.0f; }
	
	public String 		getName() 			{ return "Zombie"; }
	public EntityType 	getType() 			{ return EntityType.ZOMBIE; }
	public EntityType 	getReplaceType() 	{ return getType(); }
	public boolean 		isBaby() 			{ return false; }
	
	public int 	getHealth() 			{ return 20; }
	public int 	getArmor() 				{ return 2; }
	public int 	getToughness()			{ return 0; }
	public float getKnockbackResist() 	{ return 0.0f; }
	public float getSpeed() 			{ return 0.23f; }
	public float 	getDamage() 			{ return 3; }
	public float getKnockback() 		{ return 0.0f; }
	
	public PotionEffect getPotionEffect() { return null; }
	
	public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	public ItemStack getHead() 		{ return new ItemStack(Material.AIR); }
	public ItemStack getChest() 	{ return new ItemStack(Material.AIR); }
	public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	public void PostSpawn(Mob mob) {}
	
	public boolean checkSpawnCondition(Location loc) { return true; }
	
	public Mob spawn(Location loc) 
	{
		World world = loc.getWorld();
		Mob mob = (Mob)world.spawnEntity(loc, getType());
		
		//	Set isBaby
		if (getType() == EntityType.PIGLIN) { ((Piglin)mob).setBaby( isBaby() ); }
		else if (getType() == EntityType.ZOMBIE) { ((Zombie)mob).setBaby( isBaby() ); }
		
		//	Immune nether mobs that spawn in overworld
		if (world.getEnvironment() == Environment.NORMAL && getType() == EntityType.PIGLIN) { ((Piglin)mob).setImmuneToZombification(true); }
		else if (world.getEnvironment() == Environment.NORMAL && getType() == EntityType.HOGLIN) { ((Hoglin)mob).setImmuneToZombification(true); }
		
		//	Set attributes
		mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue( getHealth() ); mob.setHealth( mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() );
		mob.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue( getArmor() );
		mob.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue( getToughness() );
		mob.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue( getKnockbackResist() );
		mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue( getSpeed() );
		mob.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue( getDamage() );
		mob.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue( getKnockback() );
		
		//	Apply potion effect
		if (getPotionEffect() != null) mob.addPotionEffect(getPotionEffect());
		
		//	Set equipment
		EntityEquipment equipment = mob.getEquipment();
		equipment.setItemInMainHand( getMainHand() );
		equipment.setItemInOffHand( getOffHand() );
		equipment.setHelmet( getHead() );
		equipment.setChestplate( getChest() );
		equipment.setLeggings( getLegs() );
		equipment.setBoots( getFeet() );
		
		//	Set name
		mob.setCustomName(getName());
		mob.setRemoveWhenFarAway(doesDespawn());
		
		//	Call PostSpawn to perform any custom operations
		PostSpawn(mob);
		
		return mob;
	}
}
