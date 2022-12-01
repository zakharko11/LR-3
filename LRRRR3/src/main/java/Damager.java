import java.io.FileWriter;
import java.io.IOException;
/**
 * Клас Дамагер, дроїд, який наносить велику к-сть шкод,
 * має достатній запас здоров'я та броні
 * має шанс нанести критичний удар (+35% до шкоди)
 * а також при низькому рівні здоров'я має збільшений damage на 20%
 */
public class Damager extends DefaultDroid{
    private boolean crit;
    private double CritDamage = 1.35;
    /**
     * При створенні задаються характеристики притаманні лише Дамагеру
     */
    Damager(){
        profession = "Damager";
        health = 200;
        damage = 100;
        armor = 170;
        dodgeChance = 20;
        coef = 0.6;
    }
    /**
     * Визначення чи спрацював шанс на критичну шкоду
     */
    void CritChance() {
        if (chance.nextInt(100) < 35) {
            crit = true;
        }
        else {
            crit = false;
        }
    }
    /**
     * Основоною відмінністю від атаки стандартного дрона є те,
     * що дамагер може нанести критичний удар та при низькому здоров'ї має збільшений damage
     * @param file файл в який записується бій
     * @return Нанесену шкоду, залежно від шансу та к-сті здоров'я
     */
    @Override
    int Attack(FileWriter file) {
        double addition = health < 210 * 0.35 ? 1.2 : 1;
        this.CritChance();
        if (crit && !stun) {
            crit = false;
            try {
                file.write("\n У " + name + " спрацював шанс на критичний удар (+35% до шкоди) \n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (int) (CritDamage * super.Attack(file) * addition);
        }
        else {
            return (int) (super.Attack(file) * addition);
        }
    }
}