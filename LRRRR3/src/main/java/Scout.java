import java.io.FileWriter;
import java.io.IOException;
/**
 * Клас Скаут, має відносно малий запас здоров'я та броні
 * при атаці наносить комбо з від 1 до 5 ударів (у кожної к-сті своя ймовірність)
 * має великий шанс увернутися від атаки, при зниженні здоров'я до половини
 * цей шанс стає ще більшим
 */
public class Scout extends DefaultDroid{
    int combos;
    /**
     * Скаут отримує свої властивості
     */
    Scout(){
        profession = "Scout";
        health = 180;
        damage = 25;
        armor = 100;
        dodgeChance = 40;
        coef = 0.4;
    }
    /**
     * Визначення к-сті комбо ударів з їх певною ймовірністю
     */
    void ComboChance (){
        int interval = chance.nextInt(100);
        if (interval < 10){
            combos = 1;
        }
        else if (interval < 40){
            combos = 2;
        }
        else if (interval < 80){
            combos = 3;
        }
        else if (interval < 95){
            combos = 4;
        }
        else {
            combos = 5;
        }
    }
    /**
     * Атака змінена під к-сть комбо ударів
     * @param file файл в який записується бій
     * @return шкоду нанесену, залежно від к-сті ударів
     */
    @Override
    int Attack(FileWriter file) {
        ComboChance();
        int res = combos * super.Attack(file);
        try {
            if (!stun) {
                file.write("\n " + name + " провів серію з " + combos + " ударів. Нанесено " + res + " од. шкоди.\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        combos = 0;
        return res;
    }
    /**
     * Зі змін лише призначення шансу доджу при здоров'ї нижче 50 од. як 60% а не 40%
     * @param damage к-сть шкоди, яку отримав дроїд
     * @param stunD чи спрацював шанс оглушити цього дроїда
     * @param file файл, в який записується весь бій
     */
    @Override
    void GetDamage(int damage, boolean stunD, FileWriter file) {
        if(health < 50){
            dodgeChance = 60;
        }
        super.GetDamage(damage, stunD, file);
    }
}