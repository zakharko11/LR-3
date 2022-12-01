import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Клас арена, де я ініціалізую, проводжу битву та визначаю результати битви
 */
public class Arena {
    public Random chance = new Random();
    private String st = "===============================================================================", tab = "\n\t\t\t\t\t\t\t";
    /**
     * Ініціалізація битви, визначається, яка команда атакує першою
     * @param t1 команда 1
     * @param t2 команда 2 (парядок не важливий)
     * @param file файл, в який записується битва
     * @return 1, якщо команда 1 атакує першою, 2, якщо команда №2
     */
    int StartFight (Team t1, Team t2, FileWriter file){
        int num = 0;
        Team first;
        try {
            file.write("\n Буде вибрано команду, яка починає атаку: ");
            if (chance.nextInt(2) == 1){
                num = 1;
                first = t1;
            }
            else {
                num = 2;
                first = t2;
            }
            file.write("\n Команда \""+ first.teamname + "\" атакує першою.");
        }catch(IOException e) {
            e.printStackTrace();
        }
        return num;
    }
    /**
     * Все, що відбувається на битві, в тому ж числі виклик кожного раунду
     * виводяться відомості про учасників двох команд
     * @param t1 команда 1
     * @param t2 команда 2
     * @param file файл з результатами бою
     */
    void Fight (Team t1, Team t2, FileWriter file){
        int roundNum = 1;
        Team tm1, tm2;
        if(StartFight(t1, t2, file) == 1){
            tm1 = t1; tm2 = t2;
        }
        else {
            tm1 = t2; tm2 = t1;
        }
        while (true){
            if(!Round(tm1, tm2, roundNum, file)){
                return;
            }
            try {
                file.write("\n Відомості про учасників команди \""+ tm1.teamname +"\":\n");
                for (int n = 0; n < tm1.team.length; n++) {
                    file.write(tm1.team[n] + "\n");
                }
                file.write("\n Відомості про учасників команди \""+ tm2.teamname + "\":\n");
                for (int n = 0; n < tm2.team.length; n++) {
                    file.write(tm2.team[n] + "\n");
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
            roundNum++;
        }
    }
    /**
     * Усі дії, які відбуваються у раунді (атака 1 та 2 команди) визначення чи є живі учасники
     * @param t1 команда, яка атакує першою
     * @param t2 команда, яка атакує другою
     * @param num номер раунду
     * @param file файл, в який записується бій
     * @return true, якщо обидві команди мають ще живих учасників, false якщо одна з команд програла
     */
    boolean Round (Team t1, Team t2, int num, FileWriter file){
        try {
            file.write("\n\n"+ st + tab +"Почався раунд №" + num + "\n" + st + "\n");
            if (!t2.AliveTeam(file)) {
                return false;
            }
            t1.TeamAttack(t2, file);
            if (!t1.AliveTeam(file)) {
                return false;
            }
            t2.TeamAttack(t1, file);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}