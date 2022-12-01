import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Клас стандартного дрона, який ще по собі не є якимось воїном, але він потрібен,
 * для розпису усіх методів, що є притаманні для кожного воїна
 */
public class DefaultDroid {
    protected Random chance = new Random();
    protected int health, damage, armor, dodgeChance;
    protected String name, profession;
    protected boolean alive, stun, dodge, stunD = false;
    public boolean isStunD() { return stunD; }
    protected double coef; // Коефіцієнт поглинання шкоди бронею (якщо вона є)
    /**
     * При створенні дроїду надаються стандартні властивості
     */
    DefaultDroid(){
        alive = true;
        stun = dodge = false;
    }
    /**
     * Метод, який каже чи дроїд є живим, тобто чи не програв досі бій
     * @return повертає true, якщо дроїд живий, та false, якщо ні
     */
    protected boolean IsAlive(){
        if(health <= 0){
            alive = false;
        }
        return alive;
    }
    /**
     * Метод, який призначає отриманий ботом damage, та інші ефекти (як оглушення і тд.)
     * @param damage к-сть шкоди, яку отримав дроїд
     * @param stunD чи спрацював шанс оглушити цього дроїда
     * @param file файл, в який записується весь бій
     */
    void GetDamage (int damage, boolean stunD, FileWriter file){
        DodgeChance(file);
        if(stunD && !dodge){
            try {
                file.write("\n " + name + " був оглушений на 1 раунд.\n");
            }catch(IOException e) {
                e.printStackTrace();
            }
            stun = true;
        }
        if (!dodge) {
            if (armor > 0) {
                armor -= coef * damage;
                health -= (1.0 - coef) * damage;
                health = armor < 0? health - armor : health;
                armor = armor < 0? 0 : armor;
            } else {
                health -= damage;
            }
        }
        dodge = false;
    }
    boolean setDodge(){ return dodge;}
    /**
     * Метод атакувати противника
     * @return к-сть шкоди, яку має нанести дроїд
     */
    int Attack (FileWriter file) {
        if(!stun) {
            return damage;
        }
        else {
            stun = false;
            return 0;
        }
    }
    boolean IsStuned () { return stun; }
    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getArmor() {return armor;}
    public int getDamage() {return damage;}
    public int getHealth() {return health;}
    /**
     * Метод, який визначає чи спрацював шанс увернутися від атаки у дроїда
     * @param file файл, в який записується бій
     */
    void DodgeChance(FileWriter file){
        if (chance.nextInt(100) < dodgeChance && alive) {
            try {
                file.write("\n У " + name + " спрацював шанс увернутися від наступної атаки. \n");
            }catch(IOException e) {
                e.printStackTrace();
            }
            dodge = true;
        }
        else {
            dodge = false;
        }
    }
    @Override
    public String toString() {
        return "\n Характеристики " + name + " (" + profession  + "):"+
                "\n Здоров\'я: " + health +
                "\n Броня: " + armor;
    }
}