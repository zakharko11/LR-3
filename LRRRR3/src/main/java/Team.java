import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Team {
    public Scanner inpt = new Scanner(System.in);
    public Random chance = new Random();
    DefaultDroid[] team;
    String teamname;
    Team(){
        CreateTeam();
    }
    /**
     * Метод створення та заповнення команди
     */
    void CreateTeam (){
        String name;
        int num;
        while (true){
            System.out.print("\n Введіть назву команди: ");
            teamname = inpt.nextLine();
            System.out.print("\n Введіть к-сть учасників команди \"" + teamname + "\": ");
            num = inpt.nextInt();
            if (num > 0){
                break;
            }
            System.out.print(" К-сть дроїдів має бути більшою ніж 0. \n");
        }
        team = new DefaultDroid[num];
        /*Цикл створення учасників*/
        for (int n = 0; n < num; n++) {
            ChoseMode(n);
            inpt.nextLine();
            System.out.print("\n Введіть ім\'я учасника №" + (n + 1) + ": ");
            name = inpt.nextLine();
            team[n].setName(name);
        }
    }
    /**
     * Метод вибору, яким із бійців буде даний дроїд
     * @param n номер учасника в масиві
     */
    void ChoseMode (int n) {
        int mode;
        while (true) {
            System.out.print("\n Виберіть вид учасника " + (n + 1) + " (Damager - 1, Guardian - 2, Scout - 3, Marauder - 4): ");
            mode = inpt.nextInt();
            switch (mode) {
                case 1:
                    team[n] = new Damager();
                    return;
                case 2:
                    team[n] = new Guardian();
                    return;
                case 3:
                    team[n] = new Scout();
                    return;
                case 4:
                    team[n] = new Marauder();
                    return;
                default:
                    System.out.print("\n Введіть значення 1, 2, 3 або 4 для вибору класу дроїда.\n");
                    break;
            }
        }
    }
    /**
     * Метод, в якому кожен учасник даної команди наносить шкоду випадклвлму дефендеру (противнику)
     * Визначається пошук живого учасника дефендерів, та атакують живі атакери
     * @param defender команда захисту
     * @param file файл з результатами битви
     */
    void TeamAttack (Team defender, FileWriter file){
        try {
            for (DefaultDroid defaultDroid : team) {
                int enemynum;
                while (true) {
                    enemynum = chance.nextInt(defender.team.length);
                    if (defender.team[enemynum].IsAlive()) {
                        break;
                    }
                    if (!defender.AliveTeam(file)) {
                        return;
                    }
                }
                if (defaultDroid.IsAlive() && !defaultDroid.IsStuned()) {
                    defender.team[enemynum].GetDamage(defaultDroid.Attack(file), defaultDroid.isStunD(), file);
                    file.write("\n " + defaultDroid.getName() + " атакує " + defender.team[enemynum].getName() + ".\n");
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Визначає чи є ще хтось живий у команді дронів
     * @param file файл, в який записується бій
     * @return true якщо ще є живий учасник, false, якщо всі мертві
     */
    boolean AliveTeam (FileWriter file){
        int fallen = 0;
        for (DefaultDroid defaultDroid : team) {
            if (!defaultDroid.IsAlive()) {
                fallen++;
            }
        }
        if(fallen == team.length){
            try {
                file.write("\n Команда \"" + teamname + "\" програла битву.\n");
            }catch(IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}