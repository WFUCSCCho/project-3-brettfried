//∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗
//        ∗ @file: DatasetGenerator.java
//        ∗ @description: This program generates the three datasets I needed, and puts them in txt files
//        ∗ @author: Brett Fried
//        ∗ @date: November 14, 2024
//∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DatasetGenerator {
    public static void main(String[] args) throws IOException {
        int numberOfElements = 100; // You can adjust the number of elements as needed
        String baseFilename = "dataset";

        // Generate Random Dataset
        generateRandomDataset(baseFilename + "_shuffled.txt", numberOfElements);

        // Load Dataset into ArrayList
        ArrayList<Integer> data = loadDataset(baseFilename + "_shuffled.txt", numberOfElements);

        // Create Sorted Version
        createSortedVersion(data, baseFilename + "_sorted.txt");

        // Create Reversed Version
        createReversedVersion(data, baseFilename + "_reversed.txt");
    }

    public static void generateRandomDataset(String filename, int numberOfElements) throws IOException {
        Random rand = new Random();
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < numberOfElements; i++) {
                writer.write(rand.nextInt(10000) + "\n"); // Random numbers between 0 and 9999
            }
        }
        System.out.println("Shuffled dataset generated: " + filename);
    }

    public static ArrayList<Integer> loadDataset(String filename, int numberOfLines) throws IOException {
        ArrayList<Integer> data = new ArrayList<>();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < numberOfLines) {
                data.add(Integer.parseInt(line.trim()));
                count++;
            }
        }
        return data;
    }

    public static void createSortedVersion(ArrayList<Integer> data, String filename) throws IOException {
        ArrayList<Integer> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData);
        try (FileWriter writer = new FileWriter(filename)) {
            for (int num : sortedData) {
                writer.write(num + "\n");
            }
        }
        System.out.println("Sorted version created: " + filename);
    }

    public static void createReversedVersion(ArrayList<Integer> data, String filename) throws IOException {
        ArrayList<Integer> reversedData = new ArrayList<>(data);
        Collections.sort(reversedData, Collections.reverseOrder());
        try (FileWriter writer = new FileWriter(filename)) {
            for (int num : reversedData) {
                writer.write(num + "\n");
            }
        }
        System.out.println("Reversed version created: " + filename);
    }
}
