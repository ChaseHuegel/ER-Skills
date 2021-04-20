package er.seven.skills.mobs;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PossessedObject extends Monster
{	
	@Override public boolean 	doesDespawn() 		{ return true; }
	@Override public float 		getSpawnChance() 	{ return 0.25f; }
	
	@Override public String 		getName() 	{ return "Possessed Object"; }
	@Override public EntityType 	getType() 	{ return EntityType.ZOMBIE; }
	@Override public EntityType 	getReplaceType() 	{ return EntityType.ENDERMAN; }
	@Override public boolean 		isBaby() 	{ return false; }
	
	@Override public int 	getHealth() 			{ return 5; }
	@Override public int 	getArmor() 				{ return 0; }
	@Override public int 	getToughness()			{ return 0; }
	@Override public float 	getKnockbackResist() 	{ return 1.0f; }
	@Override public float 	getSpeed() 				{ return 0.3f; }
	@Override public float 	getDamage() 			{ return 2; }
	@Override public float 	getKnockback() 			{ return 0.0f; }
	
	@Override public PotionEffect getPotionEffect() { return new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, true); }
	
	@Override public ItemStack getMainHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getOffHand() 	{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getHead() 		{ return new ItemStack( Material.values()[ (new Random()).nextInt(Material.values().length) ] ); }
	@Override public ItemStack getChest() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getLegs() 		{ return new ItemStack(Material.AIR); }
	@Override public ItemStack getFeet() 		{ return new ItemStack(Material.AIR); }
	
	@Override public void PostSpawn(Mob mob)
	{
		mob.getEquipment().setHelmetDropChance(1.0f);
		mob.setSilent(true);
	}
}
