import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;

public class Main {
    /**
     * Метод, який зчитує всю інформацію про бій із файла та виводить її
     */
    static void FightResults(){
        try{
            System.setIn(new FileInputStream("C:\\Files\\Lab3-PP.txt"));
            String content = new String(System.in.readAllBytes());
            System.out.println(content);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Team team1 = new Team(), team2 = new Team();
        try {
            FileWriter writer = new FileWriter("C:\\Files\\Lab3-PP.txt");
            Arena battle = new Arena();
            battle.Fight(team1, team2, writer);
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
        FightResults();
    }
}

