package Blockcreator;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Writer {

    public String writeToFile(String input) {
        try (FileWriter fileWriter = new FileWriter("block.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter output = new PrintWriter(bufferedWriter)) {
            output.println(input);
        } catch (IOException e) {
            System.out.println("Error! This file doesn't exist: Block.txt");
            return "An Error has occured. Data was not saved."
        }
        return "Data added to block.";
    }
}
