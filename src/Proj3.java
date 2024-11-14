//∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗
//        ∗ @file: Proj3.java
//        ∗ @description: This program houses the different sorting methods, and writing to sorted and analysis files.
//        ∗ @author: Brett Fried, Samuel Cho
//        ∗ @date: November 14, 2024
//∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid);
            mergeSort(a, mid + 1, right);
            merge(a, left, mid, right);
        }
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<>(a);
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (temp.get(i).compareTo(temp.get(j)) <= 0) {
                a.set(k++, temp.get(i++));
            } else {
                a.set(k++, temp.get(j++));
            }
        }

        while (i <= mid) {
            a.set(k++, temp.get(i++));
        }
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(a, left, right);
            quickSort(a, left, pivotIndex - 1);
            quickSort(a, pivotIndex + 1, right);
        }
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        T pivot = a.get(right);
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (a.get(j).compareTo(pivot) <= 0) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, right);
        return i + 1;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        int n = right - left + 1;  // Total number of elements to sort

        // Build a max heap within the range [left, right]
        for (int i = (right + left) / 2; i >= left; i--) {
            heapify(a, left, right, i);
        }

        // Extract elements from the heap one by one and place at the end
        for (int i = right; i > left; i--) {
            swap(a, left, i); // Move current root to the end
            heapify(a, left, i - 1, left); // Call heapify on the reduced heap
        }
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right, int root) {
        int largest = root;
        int leftChild = 2 * (root - left) + 1 + left;
        int rightChild = 2 * (root - left) + 2 + left;

        // Ensure that the left child is within the bounds defined by `right`
        if (leftChild <= right && a.get(leftChild).compareTo(a.get(largest)) > 0) {
            largest = leftChild;
        }

        // Ensure that the right child is within the bounds defined by `right`
        if (rightChild <= right && a.get(rightChild).compareTo(a.get(largest)) > 0) {
            largest = rightChild;
        }

        // If the largest is not the root, swap and continue heapifying
        if (largest != root) {
            swap(a, root, largest);
            heapify(a, left, right, largest);
        }
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        int comparisons = 0;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                comparisons++;
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                }
            }
        }
        return comparisons;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        boolean isSorted = false;
        int comparisons = 0;
        while (!isSorted) {
            isSorted = true;
            for (int i = 1; i <= size - 2; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            for (int i = 0; i <= size - 2; i += 2) {
                comparisons++;
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
        }
        return comparisons;
    }

    public static void main(String [] args)  throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java Proj3 <dataset-file> <sorting-algorithm-type> <number-of-lines>");
            return;
        }

        String datasetFile = args[0];
        String sortingAlgorithm = args[1].toLowerCase();
        int numberOfLines = Integer.parseInt(args[2]);

        ArrayList<Integer> data = new ArrayList<>();

        // Read data from file
        try (BufferedReader br = new BufferedReader(new FileReader(datasetFile))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < numberOfLines) {
                data.add(Integer.parseInt(line.trim()));
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        ArrayList<Integer> dataCopy = new ArrayList<>(data);
        long startTime, endTime;
        int comparisons = 0;

        // Choose sorting algorithm
        switch (sortingAlgorithm) {
            case "bubble":
                startTime = System.nanoTime();
                comparisons = bubbleSort(dataCopy, dataCopy.size());
                endTime = System.nanoTime();
                break;
            case "merge":
                startTime = System.nanoTime();
                mergeSort(dataCopy, 0, dataCopy.size() - 1);
                endTime = System.nanoTime();
                break;
            case "quick":
                startTime = System.nanoTime();
                quickSort(dataCopy, 0, dataCopy.size() - 1);
                endTime = System.nanoTime();
                break;
            case "heap":
                startTime = System.nanoTime();
                heapSort(dataCopy, 0, dataCopy.size() - 1);
                endTime = System.nanoTime();
                break;
            case "transposition":
                startTime = System.nanoTime();
                comparisons = transpositionSort(dataCopy, dataCopy.size());
                endTime = System.nanoTime();
                break;
            default:
                System.out.println("Invalid sorting algorithm type.");
                return;
        }

        long elapsedTime = endTime - startTime;
        System.out.println("Sorting completed in " + elapsedTime + " nanoseconds.");
        if (sortingAlgorithm.equals("bubble") || sortingAlgorithm.equals("transposition")) {
            System.out.println("Number of comparisons: " + comparisons);
        }

        // Write sorted data to file
        String outputFile = "sorted.txt";
        try (FileWriter sortedWriter = new FileWriter(outputFile, true)) {
            sortedWriter.write("\nSorted using " + sortingAlgorithm.toUpperCase() + " on dataset: " + datasetFile + "\n");
            for (int num : dataCopy) {
                sortedWriter.write(num + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing sorted data to file: " + e.getMessage());
        }

        // Append timing data to analysis file
        String analysisFile = "analysis.txt";
        try (FileWriter analysisWriter = new FileWriter(analysisFile, true)) {
            analysisWriter.write(datasetFile + "," + sortingAlgorithm + "," + numberOfLines + "," + elapsedTime);
            if (sortingAlgorithm.equals("bubble") || sortingAlgorithm.equals("transposition")) {
                analysisWriter.write("," + comparisons);
            }
            analysisWriter.write("\n");
        } catch (IOException e) {
            System.err.println("Error writing analysis data to file: " + e.getMessage());
        }
    }
}
