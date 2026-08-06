package objects;

import java.util.Random;

public class Unit {
    String name;
    String originalName;

    int level;

    int hpGrowth;
    int atkGrowth;
    int defGrowth;
    int spdGrowth;

    int currentHP;
    int maximumHP;
    int atk;
    int def;
    int spd;

    String selectedMagic;
    int sunChance;
    int starChance;
    int moonChance;

    String status;
    Unit target;

    public Unit(String name, int level, int hpGrowth, int atkGrowth, int defGrowth, int spdGrowth) {
        this.name = name;
        this.originalName = name;

        this.level = level;

        this.hpGrowth = hpGrowth;
        this.atkGrowth = atkGrowth;
        this.defGrowth = defGrowth;
        this.spdGrowth = spdGrowth;

        this.currentHP = this.hpGrowth * this.level;
        this.maximumHP = currentHP;
        this.atk = this.atkGrowth * this.level;
        this.def = this.defGrowth * this.level;
        this.spd = this.spdGrowth * this.level;

        this.status = "Okay";
        this.target = null;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public int getCurrentHP() {
        return this.currentHP;
    }

    public int getMaximumHP() {
        return this.maximumHP;
    }

    public int getLevel() {
        return this.level;
    }

    public int getAtk() {
        return this.atk;
    }

    public int getDef() {
        return this.def;
    }

    public int getSpd() {
        return this.spd;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }

    public void setSelectedMagic(String selectedMagic) {
        this.selectedMagic = selectedMagic;
    }

    public Unit getTarget() {
        return this.target;
    }

    public String getSelectedMagic() {
        return this.selectedMagic;
    }

    public String attackTarget() {
        if (this.target.getStatus().equals("Defeated")) {
            return String.format("%s tried to attack %s, but %s is already defeated.", this.originalName, this.target.getOriginalName(), this.target.getOriginalName());
        }

        Random random = new Random();
        int evasionRNG = random.nextInt(101);

        if (evasionRNG <= this.target.getSpd()/150*0.2*100) {
            return String.format("%s tried to attack %s, but %s evaded the attack.", this.originalName, this.target.getOriginalName(), this.target.getOriginalName());
        }

        int critBonus = 1;
        double effectiveElementBonus = 1;
        double defendDamageReduction = 1;

        int critRNG = random.nextInt(101);
        if (critRNG <= this.spd/150*0.2*100) {
            critBonus = 2;
        }

        if (this.target.getStatus().equals("Defending")) {
            defendDamageReduction = 0.25;
        }
        else {
            if ((this.selectedMagic.equals("Sun") && this.target.selectedMagic.equals("Star")) ||
               (this.selectedMagic.equals("Star") && this.target.selectedMagic.equals("Moon")) ||
               (this.selectedMagic.equals("Moon") && this.target.selectedMagic.equals("Sun"))) {
                   effectiveElementBonus = 2;
            }
            else if (this.selectedMagic.equals(this.target.getSelectedMagic())) {
                effectiveElementBonus = 1;
            }
            else {
                effectiveElementBonus = 0.5;
            }
        }

        int totalDamage = (int) (this.atk*critBonus*effectiveElementBonus*defendDamageReduction - random.nextInt(this.target.getDef()+1));

        if (totalDamage < 1) {
            totalDamage = 1;
        }

        this.target.setCurrentHP(this.target.getCurrentHP() - totalDamage);

        if (this.target.getCurrentHP() < 1) {
            this.target.setCurrentHP(0);
            this.target.setStatus("Defeated");
            this.target.setName("[DEFEATED]");
        }

        if (critBonus == 2 && effectiveElementBonus == 0.5) {
            return String.format("%s ineffectively, but CRITICALLY attacks %s with %s magic for %d damage.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage);
        }
        else if (critBonus == 2 && defendDamageReduction == 0.25) {
            return String.format("%s CRITICALLY attacks %s with %s magic for %d damage, while %s is defending.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage, this.target.getOriginalName());
        }
        else if (critBonus == 2 && effectiveElementBonus == 2) {
            return String.format("%s EFFECTIVELY and CRITICALLY attacks %s with %s magic for %d damage.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage);
        }
        else if (critBonus == 1 && effectiveElementBonus == 2) {
            return String.format("%s EFFECTIVELY attacks %s with %s magic for %d damage.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage);
        }
        else if (critBonus == 1 && effectiveElementBonus == 0.5) {
            return String.format("%s ineffectively attacks %s with %s magic for %d damage.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage);
        }
        else if (critBonus == 1 && defendDamageReduction == 0.25) {
            return String.format("%s attacks %s with %s magic for %d damage, while %s is defending.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage, this.target.getOriginalName());
        }
        else if (critBonus == 2 && effectiveElementBonus == 1) {
            return String.format("%s CRITICALLY attacks %s with %s magic for %d damage.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage);
        }
        else {
            return String.format("%s attacks %s with %s magic for %d damage.", this.originalName, this.target.getOriginalName(), this.selectedMagic, totalDamage);
        }
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSunChance() {
        return this.sunChance;
    }

    public int getStarChance() {
        return this.starChance;
    }

    public int getMoonChance() {
        return this.moonChance;
    }

    public void setSunChance(int sunChance) {
        this.sunChance = sunChance;
    }

    public void setStarChance(int starChance) {
        this.starChance = starChance;
    }

    public void setMoonChance(int moonChance) {
        this.moonChance = moonChance;
    }

    public String getName() {
        return this.name;
    }

    public void levelUp() {
        this.level++;

        this.maximumHP += this.hpGrowth;
        this.currentHP = this.maximumHP;
        this.atk += this.atkGrowth;
        this.def += this.defGrowth;
        this.spd += this.spdGrowth;
    }
}
