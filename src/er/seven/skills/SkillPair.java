package er.seven.skills;

import org.bukkit.entity.Player;

public class SkillPair 
{
    private final Player x;
    private final Skill y;

    public SkillPair(Player x, Skill y) 
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (!(o instanceof SkillPair)) return false;
        SkillPair pair = (SkillPair) o;
        return x == pair.x && y == pair.y;
    }
    
    @Override
    public int hashCode() 
    {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}
