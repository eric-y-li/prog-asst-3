import java.util.*;

class RandomArray {
    private RandomArray(){}

    public static int[] generateArray(int size) {
        Random r = new Random();
        int[] arr = new int[size];
        for (int i=1; i<size; i++) {
            arr[i] = r.nextInt((int)(Math.pow(2,29)));
        }
        return arr;
    }
}

class MaxHeap {
    private ArrayList<Long> heap;

    public MaxHeap() {
        heap = new ArrayList<Long>();
    }

    public MaxHeap(ArrayList<Long> arr) {
        heap = arr;
        for (int i = (heap.size()-1)/2; i>=0; i--) {
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
    private int MAX_ITER = 10000;
    private PartitionAlgs(){};


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
          arr2[prepartition[j]] += arr[j];
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
          for (int j = 0; j < arr.length; j++) {
            currSolution[j] = rand[r.nextInt(2)];
          }
          if (residue(arr, currSolution) < residue(arr, solution)) {
              solution = currSolution;
          }
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
          if (residuePP(arr, currPrePartition) < residuePP(arr, solution)) {
            solution = currPrePartition;
          }
        }

        return residuePP(arr, solution);
    }


    public static long hillClimb(long[] arr) {
        Random r = new Random();
        long[] rand = {-1, 1};

        long[] solution = new long[arr.length];
        for (int j = 0; j < arr.length; j++) {
          solution[j] = rand[r.nextInt(2)];
        }

        long[] currSolution = solution;

        for (int i = 0; i < MAX_ITER; i++) {
          int ind1 = r.nextInt(arr.length);
          int ind2;
          do {
            ind2 = r.nextInt(arr.length);
          } while(ind1 == ind2);
          currSolution[ind1] *= -1;
          if (r.nextInt(2) == 1) currSolution[ind2] *= -1;
          if (residue(arr, currSolution) < residue(arr, solution)) {
            solution = currSolution;
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

        long[] currPrePartition = solution;

        for (int i = 0; i < MAX_ITER; i++) {
            int ind1 = r.nextInt(arr.length);
            int ind2;
            do {
                ind2 = r.nextInt(arr.length);
            } while(solution[ind1] == ind2);
            currPrePartition[ind1] = ind2;
            if (residuePP(arr, currPrePartition) < residuePP(arr, solution)) {
                residue = residuePP(arr, currPrePartition);
                solution = currPrePartition;
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
      for (int j = 0; j < arr.length; j++) {
          solution[j] = rand[r.nextInt(2)];
      }

      long[] globalSol = solution;

      long[] currSolution = solution;

      for(int i = 0; i < MAX_ITER; i++) {
        int ind1 = r.nextInt(arr.length-1);
        int ind2;
        do {
          ind2 = r.nextInt(arr.length-1);
        } while(ind1 == ind2);
        currSolution[ind1] *= -1;
        if (r.nextInt(2) == 1) currSolution[ind2] *= -1;
        if (residue(arr, currSolution) < residue(arr, solution)) {
          solution = currSolution;
        }
        else {
          if (r.nextDouble() < Math.exp(-(residue(arr, currSolution) -
            residue(arr, solution)) / cooling(i))) {
            solution = currSolution;
          }
        }
        if (residue(arr, solution) < residue(arr, globalSol)) {
            globalSol = solution;
        }
      }

      return residue(arr, globalSol);
    }


    public static long simAnnealPP(long[] arr) {
      Random r = new Random();

      long[] solution = new long[arr.length];
      for (int j = 0; j < arr.length; j++) {
        Integer z = new Integer(r.nextInt(arr.length))
        solution[j] = z.longValue();
      }

      long[] globalSolution = solution;

      for (int i = 0; i < MAX_ITER; i++) {
        long[] currentSolution = solution;

        int j = r.nextInt(arr.length);
        int k;

        do {
          k = r.nextInt(arr.length);
        } while (currentSolution[j] == k);

        Integer z = new Integer(k);
        currentSolution[j] = z.longValue();

        if (residuePP(arr, currentSolution) < residuePP(arr, solution))
          solution = currentSolution;
        else if (r.nextDouble() < Math.exp(-(residuePP(arr, currentSolution) -
          residuePP(arr, solution)) / cooling(i))) {
          solution = currentSolution;
        }
        if (residuePP(arr, solution) < residuePP(arr, globalSolution))
          globalSolution = solution;
      }

      return residuePP(arr, globalSolution);
    }
  }
}

public class Partition {

    public static void heapTests() {
        MaxHeap heap = new MaxHeap();
        heap.insert(124);
        heap.insert(109);
        heap.insert(121);
        heap.insert(50);
        heap.insert(1);
        heap.insert(51);
        heap.insert(61);
        System.out.println(heap.toString());
        heap.deleteMax();
        System.out.println(heap.toString());
        heap.insert(151);
        System.out.println(heap.toString());

        MaxHeap heap2 = new MaxHeap(new ArrayList<Integer>(Arrays.asList(10,8,7,6,5)));
        System.out.println(heap2.toString());
    }

    public static void main(String[] args) {
        //System.out.println(Arrays.toString(RandomArray.generateArray(100)));
        //System.out.println(PartitionAlgs.residuePP(new int[] {10,8,7,6,5}, new int[] {0,1,1,3,4}));
        // heapTests();
        // ArrayList<Integer> nums = new ArrayList<Integer>(Arrays.asList(10,8,7,6,5));
        int[] randArr = RandomArray.generateArray(100);
        System.out.println(PartitionAlgs.repeatedRandom(randArr));
        System.out.println(PartitionAlgs.hillClimb(randArr));
        System.out.println(PartitionAlgs.simAnneal(randArr));
        // System.out.println(PartitionAlgs.kk(nums));
        // System.out.println(PartitionAlgs.repeatedRandom(new int[] {10,8,7,6,5}));
    }
}
