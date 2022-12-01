import java.io.FileWriter;
import java.io.IOException;
/**
 * Клас Вартовий (танк), має великий запас броні та здоров'я,
 * непоганий damage, але дуже низький шанс на увернутись від атаки
 * основне уміння - оглушити на 1 хід противника,
 * при низькому здоров'ї отримує менше шкоди
 */
public class Guardian extends DefaultDroid{
    /**
     * При створенні Вартовий отримує свої навички та характеристики
     */
    Guardian(){
        profession = "Guardian";
        health = 250;
        damage = 45;
        armor = 500;
        dodgeChance = 12;
        coef = 0.8;
    }
    /**
     * Визначення шансу оглушити ворога
     * @param file файл, в яикй записується бій
     */
    void StunChance(FileWriter file) {
        if (chance.nextInt(100) < 30 && !stun) {
            try {
                file.write("\n " + name + " оглушив ворога.\n");
            }catch(IOException e) {
                e.printStackTrace();
            }
            stunD = true;
        }
        else {
            stunD = false;
        }
    }
    /**
     * Проста атака, єдина зміна - це визначення шансу на оглушення противника
     * @param file файл в який записується бій
     * @return стандартну к-сть одиниць шкоди для Вартового
     */
    @Override
    int Attack(FileWriter file) {
        StunChance(file);
        return super.Attack(file);
    }
    /**
     * Зміна лише в тому, що коли у Вартового мало здоров'я він приймає менше шкоди
     * @param damage к-сть шкоди, яку отримав дроїд
     * @param stunD чи спрацював шанс оглушити цього дроїда
     * @param file файл, в який записується весь бій
     */
    @Override
    void GetDamage(int damage, boolean stunD, FileWriter file) {
        double decrease = health < 230 * 0.4 ? 0.3 : 1;
        super.GetDamage((int)(damage * decrease), stunD, file);
    }
    boolean getStunChance () { return stunD; }
}
