import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


class MaxHeap {
  private ArrayList<Long> heap;


  public MaxHeap() {
    heap = new ArrayList<Long>();
  }


  public MaxHeap(ArrayList<Long> arr) {
    heap = arr;
    for (int i = (heap.size() - 1) / 2; i >= 0; i--) {
      heapify(i);
    }
  }


  private int parent(int node) {
    return (node - 1) / 2;
  }


  private int left(int node) {
    return 2 * node + 1;
  }


  private int right(int node) {
    return 2 * node + 2;
  }


  private void swap(int i, int j) {
    long temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }


  private void heapify(int root) {
    int left = left(root);
    int right = right(root);

    int largest = root;

    if (left < heap.size() && heap.get(left) > heap.get(root)) largest = left;
    if (right < heap.size() && heap.get(right) > heap.get(largest)) largest = right;

    if (largest != root) {
      swap(root, largest);
      heapify(largest);
    }
  }


  public int getSize() {
    return heap.size();
  }


  public long deleteMax() {
    long max = heap.get(0);
    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);
    heapify(0);
    return max;
  }


  public void insert(long val) {
    heap.add(val);
    int currentNode = heap.size() - 1;
    while (currentNode != 0 && heap.get(parent(currentNode)) < heap.get(currentNode)) {
        swap(parent(currentNode), currentNode);
        currentNode = parent(currentNode);
    }
  }


  public String toString() {
    return Arrays.toString(heap.toArray());
  }


  public ArrayList<Long> getHeap() {
    return heap;
  }
}



class PartitionAlgs {
  private static int MAX_ITER = 25000;
  private PartitionAlgs(){};


  public static long[] copyTo(long[] arr1, long[] arr2) {
    if (arr1.length != arr2.length) throw
      new IllegalArgumentException("Size exception.");
    for (int i = 0; i < arr1.length; i++) {
      arr2[i] = arr1[i];
    }
    return arr2;
  }


  public static long residue(long[] arr, long[] signs) {
    long res = 0;
    for (int i = 0; i < arr.length; i++) {
      res += arr[i] * signs[i];
    }
    return Math.abs(res);
  }


  public static long residuePP(long[] arr, long[] prepartition) {
    long[] arr2 = new long[arr.length];
    for (int j = 0; j < arr.length;j++) {
      arr2[Math.toIntExact(prepartition[j])] += arr[j];
    }
    ArrayList<Long> arrlist = new ArrayList<Long>();
    for (int j = 0; j < arr.length; j++) {
      arrlist.add(arr2[j]);
    }
    return kk(arrlist);
  }


  public static long kk(ArrayList<Long> arr) {
    MaxHeap heap = new MaxHeap(arr);
    while(heap.getSize() > 1) {
      long largest = heap.deleteMax();
      long second = heap.deleteMax();
      heap.insert(largest - second);
    }
    return heap.getHeap().get(0);
  }


  public static long repeatedRandom(long[] arr) {
    Random r = new Random();
    long[] rand = {-1, 1};

    long[] solution = new long[arr.length];
    for (int j = 0; j < arr.length; j++) {
      solution[j] = rand[r.nextInt(2)];
    }

    long[] currSolution = new long[arr.length];

    for (int i = 0; i < MAX_ITER; i++) {
      for (int j = 0; j < arr.length; j++)
        currSolution[j] = rand[r.nextInt(2)];
      if (residue(arr, currSolution) < residue(arr, solution))
        solution = copyTo(currSolution, solution);
    }

    return residue(arr, solution);
  }


  public static long repeatedRandomPP(long[] arr) {
    Random r = new Random();

    long[] solution = new long[arr.length];
    for (int j = 0; j < arr.length; j++) {
      Integer z = new Integer(r.nextInt(arr.length));
      solution[j] = z.longValue();
    }

    long[] currPrePartition = new long[arr.length];

    for (int i = 0; i < MAX_ITER; i++) {
      for (int j = 0; j < arr.length; j++) {
        Integer z = new Integer(r.nextInt(arr.length));
        currPrePartition[j] = z.longValue();
      }
      if (residuePP(arr, currPrePartition) < residuePP(arr, solution))
        solution = copyTo(currPrePartition, solution);
    }

    return residuePP(arr, solution);
  }


  public static long hillClimb(long[] arr) {
    Random r = new Random();
    long[] rand = {-1, 1};

    long[] solution = new long[arr.length];
    for (int j = 0; j < arr.length; j++)
      solution[j] = rand[r.nextInt(2)];

    long[] currSolution = new long[arr.length];
    currSolution = copyTo(solution, currSolution);

    for (int i = 0; i < MAX_ITER; i++) {
      int ind1 = r.nextInt(arr.length);
      int ind2;
      do {
        ind2 = r.nextInt(arr.length);
      } while(ind1 == ind2);
      currSolution[ind1] *= -1;
      if (r.nextInt(2) == 1) currSolution[ind2] *= -1;
      if (residue(arr, currSolution) < residue(arr, solution)) {
        solution = copyTo(currSolution, solution);
      }
    }
    return residue(arr, solution);
  }


  public static long hillClimbPP(long[] arr) {
    Random r = new Random();

    long[] solution = new long[arr.length];
    for (int j = 0; j < arr.length; j++) {
      Integer z = new Integer(r.nextInt(arr.length));
      solution[j] = z.longValue();
    }

    long residue = residuePP(arr, solution);

    long[] currPrePartition = new long[arr.length];
    currPrePartition = copyTo(solution, currPrePartition);

    for (int i = 0; i < MAX_ITER; i++) {
      int ind1 = r.nextInt(arr.length);
      int ind2;
      do {
        ind2 = r.nextInt(arr.length);
      } while(solution[ind1] == ind2);
      currPrePartition[ind1] = ind2;
      if (residuePP(arr, currPrePartition) < residuePP(arr, solution)) {
        residue = residuePP(arr, currPrePartition);
        solution = copyTo(currPrePartition, solution);
      }
    }
    return residue;
  }


  public static double cooling(int iter) {
    return Math.pow(10,10) * Math.pow(0.8, iter/300);
  }


  public static long simAnneal(long[] arr) {
    Random r = new Random();
    long[] rand = {-1,1};

    long[] solution = new long[arr.length];
    for (int j = 0; j < arr.length; j++)
        solution[j] = rand[r.nextInt(2)];

    long[] globalSol = new long[arr.length];
    globalSol = copyTo(solution, globalSol);

    long[] currSolution = new long[arr.length];
    currSolution = copyTo(solution, currSolution);

    for(int i = 0; i < MAX_ITER; i++) {
      int ind1 = r.nextInt(arr.length);
      int ind2;
      do {
        ind2 = r.nextInt(arr.length);
      } while(ind1 == ind2);
      currSolution[ind1] *= -1;
      if (r.nextInt(2) == 1) currSolution[ind2] *= -1;
      if (residue(arr, currSolution) < residue(arr, solution))
        solution = copyTo(currSolution, solution);
      else if (r.nextDouble() < Math.exp(-(residue(arr, currSolution) -
        residue(arr, solution)) / cooling(i))) {
        solution = copyTo(currSolution, solution);
      }
      if (residue(arr, solution) < residue(arr, globalSol)) {
          globalSol = copyTo(solution, globalSol);
      }
    }

    return residue(arr, globalSol);
  }


  public static long simAnnealPP(long[] arr) {
    Random r = new Random();

    long[] solution = new long[arr.length];
    for (int j = 0; j < arr.length; j++) {
      Integer z = new Integer(r.nextInt(arr.length));
      solution[j] = z.longValue();
    }

    long[] globalSolution = new long[arr.length];
    globalSolution = copyTo(solution, globalSolution);

    for (int i = 0; i < MAX_ITER; i++) {
      long[] currentSolution = new long[arr.length];
      currentSolution = copyTo(solution, currentSolution);

      int j = r.nextInt(arr.length);
      int k;

      do {
        k = r.nextInt(arr.length);
      } while (currentSolution[j] == k);

      Integer z = new Integer(k);
      currentSolution[j] = z.longValue();

      if (residuePP(arr, currentSolution) < residuePP(arr, solution))
        solution = copyTo(currentSolution, solution);
      else if (r.nextDouble() < Math.exp(-(residuePP(arr, currentSolution) -
        residuePP(arr, solution)) / cooling(i))) {
        solution = copyTo(currentSolution, solution);
      }
      if (residuePP(arr, solution) < residuePP(arr, globalSolution))
        globalSolution = copyTo(solution, globalSolution);
    }

    return residuePP(arr, globalSolution);
  }
}


public class Partition {
  private static int ITERS = 100;


  public static void tests() {
    for (int i = 0; i < ITERS; i++) {
      long[] arr = new long[ITERS];
      ArrayList<Long> arrlist = new ArrayList<Long>(ITERS);

      for (int j = 0; j < ITERS; j++) {
        long r = ThreadLocalRandom.current().nextLong((long)Math.pow(10,12));
        arr[j] = r;
        arrlist.add(r);
      }

      System.out.println("KK: " + PartitionAlgs.kk(arrlist));
      System.out.println("Repeated random: " + PartitionAlgs.repeatedRandom(arr));
      System.out.println("Repeated random (PP): " + PartitionAlgs.repeatedRandomPP(arr));
      System.out.println("Hill climb: " + PartitionAlgs.hillClimb(arr));
      System.out.println("Hill climb (PP): " + PartitionAlgs.hillClimbPP(arr));
      System.out.println("Simulated annealing: " + PartitionAlgs.simAnneal(arr));
      System.out.println("Simulated annealing (PP): " + PartitionAlgs.simAnnealPP(arr));
      System.out.println();
    }
  }


  public static void timeTests() {
    for (int i = 0; i < ITERS; i++) {
      long[] arr = new long[ITERS];
      ArrayList<Long> arrlist = new ArrayList<Long>(ITERS);

      for (int j = 0; j < ITERS; j++) {
        long r = ThreadLocalRandom.current().nextLong((long)Math.pow(10,12));
        arr[j] = r;
        arrlist.add(r);
      }

      long start = System.nanoTime();
      long x = PartitionAlgs.kk(arrlist);
      long kkTime = (System.nanoTime() - start) / 1000000;

      start = System.nanoTime();
      x = PartitionAlgs.repeatedRandom(arr);
      long rrTime = (System.nanoTime() - start) / 1000000;

      start = System.nanoTime();
      x = PartitionAlgs.repeatedRandomPP(arr);
      long rrppTime = (System.nanoTime() - start) / 1000000;

      start = System.nanoTime();
      x = PartitionAlgs.hillClimb(arr);
      long hcTime = (System.nanoTime() - start) / 1000000;

      start = System.nanoTime();
      x = PartitionAlgs.hillClimbPP(arr);
      long hcppTime = (System.nanoTime() - start) / 1000000;

      start = System.nanoTime();
      x = PartitionAlgs.simAnneal(arr);
      long saTime = (System.nanoTime() - start) / 1000000;

      start = System.nanoTime();
      x = PartitionAlgs.simAnnealPP(arr);
      long sappTime = (System.nanoTime() - start) / 1000000;

      System.out.println("KK: " + kkTime);
      System.out.println("Repeated random: " + rrTime);
      System.out.println("Repeated random (PP): " + rrppTime);
      System.out.println("Hill climb: " + hcTime);
      System.out.println("Hill climb (PP): " + hcppTime);
      System.out.println("Simulated annealing: " + saTime);
      System.out.println("Simulated annealing (PP): " + sappTime);
      System.out.println();
    }
  }


  public static void main(String[] args) throws FileNotFoundException {
    // tests();
    // timeTests();
    int alg = Integer.parseInt(args[1]);
    String path = args[2];

    File file = new File(path);
    Scanner fileScanner = new Scanner(file);

    long[] arr = new long[100];
    ArrayList<Long> arrlist = new ArrayList<Long>();

    for(int i = 0; i < 100; i++) {
      long elt = fileScanner.nextLong();
      arr[i] = elt;
      arrlist.add(elt);
    }

    long residue = 0;

    switch(alg) {
      case 0:
        residue = PartitionAlgs.kk(arrlist);
        break;
      case 1:
        residue = PartitionAlgs.repeatedRandom(arr);
        break;
      case 2:
        residue = PartitionAlgs.hillClimb(arr);
        break;
      case 3:
        residue = PartitionAlgs.simAnneal(arr);
        break;
      case 11:
        residue = PartitionAlgs.repeatedRandomPP(arr);
        break;
      case 12:
        residue = PartitionAlgs.hillClimbPP(arr);
        break;
      case 13:
        residue = PartitionAlgs.simAnnealPP(arr);
    }

    System.out.println(residue);
  }
}
