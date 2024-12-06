package app;

import console.Computer;
import constants.HelpingFunctions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainApp {
    public static void main(String[] args) {
        HelpingFunctions.clearLogFileIfNotEmpty("algorithm_output.log");
        try (FileWriter fileWriter = new FileWriter("algorithm_output.log", true);
             PrintWriter logWriter = new PrintWriter(fileWriter)) {
            logWriter.println("////////////////////////////////////////////////////// LEVEL 10 //////////////////////////////////////////////////////");
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions during file writing
        }
//        Computer.play("bfs", 5);
//        Computer.play("dfs", 5);
//        Computer.play("ucs", 5);
//        Computer.play("dfs-r", 5);
//        Computer.play("hill-climbing-simple", 5);
//        Computer.play("hill-climbing-steepest", 5);
        Computer.play("a_star", 5);
        Computer.play("a_star_advanced", 5);
        try (FileWriter fileWriter = new FileWriter("algorithm_output.log", true);
             PrintWriter logWriter = new PrintWriter(fileWriter)) {
            logWriter.println("////////////////////////////////////////////////////// LEVEL 20 //////////////////////////////////////////////////////");
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions during file writing
        }
        //  Computer.play("bfs", 10);
//        Computer.play("dfs", 10);
//        Computer.play("ucs", 10);
//        Computer.play("dfs-r", 10);
//        Computer.play("hill-climbing-simple", 10);
//        Computer.play("hill-climbing-steepest", 10);
        Computer.play("a_star", 10);
        Computer.play("a_star_advanced", 10);
        try (FileWriter fileWriter = new FileWriter("algorithm_output.log", true);
             PrintWriter logWriter = new PrintWriter(fileWriter)) {
            logWriter.println("////////////////////////////////////////////////////// LEVEL 30 //////////////////////////////////////////////////////");
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions during file writing
        }
//        Computer.play("bfs", 15);
//        Computer.play("dfs", 15);
//        Computer.play("ucs", 15);
//        Computer.play("dfs-r", 15);
//        Computer.play("hill-climbing-simple", 15);
//        Computer.play("hill-climbing-steepest", 15);
        Computer.play("a_star", 15);
        Computer.play("a_star_advanced", 15);
    }
}


//    @Override
//    public void start(Stage primaryStage) {
////        LevelSelectionView levelSelectionView = new LevelSelectionView(primaryStage);
////        primaryStage.setTitle("Zero Squares Game");
////        Scene scene = new Scene(levelSelectionView.getView(), 1200, 700);
////        primaryStage.setScene(scene);
////        primaryStage.show();
////        // Request focus on the scene to capture keyboard input
////        scene.getRoot().requestFocus();
//
//
////        Computer.play("a_star", 5);
////        Computer.play("a_star", 5);
//    }
