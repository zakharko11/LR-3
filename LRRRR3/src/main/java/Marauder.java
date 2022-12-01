import java.io.FileWriter;
import java.io.IOException;

public class Marauder extends DefaultDroid{
    /**
     * При створенні мародер має нормальний запас хп
     * середню шкоду за удар, трохи мало броні
     * шанс доджу в нього теж невисокий, коефіцієнт поглинання шкоди бронею - середній
     */
    Marauder(){
        profession = "Marauder";
        health = 145;
        damage = 60;
        armor = 100;
        dodgeChance = 25;
        coef = 0.6;
    }
    /**
     * Метод, що визначає чи спрацював шанс у мародера знайти та вкрасти трохи елементів броні з поля бою
     * @param file файл, в який записується битва
     */
    void StealArmorChance(FileWriter file){
        if (chance.nextInt(100) < 30 && !stun){
            int stolen = chance.nextInt(25) + 5;
            try {
                file.write("\n " + name + " зібрав уламки броні. Було отримано +"
                        + stolen +  " од. броні.\n");
                armor += stolen;
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Метод отримання шкоди, особливість: коли мародер має 0 броні і менше 65 хп він буде шукати
     * свосіб відновити собі хоча б трохи захисту та хп (за умови, що він не оглушений)
     * @param damage к-сть шкоди, яку отримав дроїд
     * @param stunD чи спрацював шанс оглушити цього дроїда
     * @param file файл, в який записується весь бій
     */
    @Override
    void GetDamage(int damage, boolean stunD, FileWriter file) {
        if (health < 65 && armor == 0 && !stun){
            int healthRegen = chance.nextInt(5) + 5, armoreRegen = chance.nextInt(15) + 5;
            health += healthRegen;
            armor += armoreRegen;
            try {
                file.write("\n " + name + " знайшов на полі бою елементи дроїдів. Отримано " + healthRegen +
                        " од. здоров\'я та " + armoreRegen + " од. броні.\n");
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
        super.GetDamage(damage, stunD, file);
    }
    /**
     * Перед своєю атакою мародер має шанс знайти частину броні, та поповнити запас броні
     * @param file файл, в який записується бій
     * @return к-сть одиниць шкоди, яку дроїд наніс ворогу
     */
    @Override
    int Attack(FileWriter file) {
        StealArmorChance(file);
        return super.Attack(file);
    }
}