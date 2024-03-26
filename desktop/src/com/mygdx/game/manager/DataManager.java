package com.mygdx.game.manager;

import com.mygdx.game.screen.PlayerScore;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataManager {

        private static DataManager instance = null;


        private DataManager()
        {

        }

        public static DataManager get() // singleton class
        {
            if (instance == null)
                instance = new DataManager();
            return instance;
        }

        public void saveScore(String name,int score){
            try {
                Writer wr = new FileWriter("score.txt", true);
                wr.write( name + "," + String.valueOf(score) + "\n");
                wr.close();
            }catch (Exception e){

            }
        }

        public List<PlayerScore> readScores(){
            List<PlayerScore> scores = new ArrayList<>();
            File file = new File("score.txt");

            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String name = line.split(",")[0];
                    int score = Integer.valueOf(line.split(",")[1]);

                    scores.add(new PlayerScore(name,score));
                }
                sc.close();
            }
            catch (Exception e) {

            }
            return scores;
        }

}
