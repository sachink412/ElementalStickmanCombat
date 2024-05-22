package game;

public class Skill {
    public String name;
    public double cd;
    public double damage;
    public double currentCd = 0;

    public Skill(String name, double cd, double damage) {
        this.name = name;
        this.cd = cd;
        this.damage = damage;
    }

    public void use() {
        if (currentCd <= 0) {
            currentCd = cd;
        }
    }
}
