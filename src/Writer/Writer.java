package Writer;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Writer {

    public static void Writer() {
        try (FileWriter fileWriter = new FileWriter("block.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter output = new PrintWriter(bufferedWriter)) {
            output.println("SUT DEN!");
        } catch (IOException e) {
            System.out.println("Error! This file doesn't exist: Block.txt");
        }
    }
}
