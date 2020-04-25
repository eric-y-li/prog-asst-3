import java.util.*;

class maxHeap {
    private ArrayList<Integer> heap;

    public maxHeap() {
        heap = new ArrayList<Integer>();
    }

    public maxHeap(ArrayList<Integer> arr) {
        heap = arr;
        for (int i = (heap.size()-1)/2; i>=0; i--) {
            heapify(i);
        }
    }

    private int parent(int node) {
        return (node-1)/2;
    }

    private int left(int node) {
        return 2*node + 1;
    }

    private int right(int node) {
        return 2*node + 2;
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
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

    public int deleteMax() {
        int max = heap.get(0);
        heap.set(0, heap.get(heap.size()-1));
        heap.remove(heap.size()-1);
        heapify(0);
        return max;
    }

    public void insert(int val) {
        heap.add(val);
        int currentNode = heap.size()-1;
        while (currentNode != 0 && heap.get(parent(currentNode))<heap.get(currentNode)) {
            swap(parent(currentNode), currentNode);
            currentNode = parent(currentNode);
        }
    }

    public String toString() {
        return Arrays.toString(heap.toArray());
    }

    public ArrayList<Integer> getHeap() {
        return heap;
    }
}

class partitionAlgs {

    private partitionAlgs(){};

    public static int residue(int[] arr, int[] signs) {
        int res = 0;
        for (int i=0; i<arr.length; i++) {
            res += arr[i]*signs[i];
        }
        return Math.abs(res);
    }

    public static int residuePP(int[] arr, int[] prepartition) {
        int[] arr2 = new int[arr.length];
        for (int j=0; j<arr.length;j++) {
            arr2[prepartition[j]] += arr[j];
        }
        ArrayList<Integer> arrlist = new ArrayList<Integer>();
        for (int j=0; j<arr.length;j++) {
            arrlist.add(arr2[j]);
        }
        return kk(arrlist);
    }

    public static int kk(ArrayList<Integer> arr) {
        maxHeap heap = new maxHeap(arr);
        while(heap.getSize() > 1) {
            int largest = heap.deleteMax();
            int second = heap.deleteMax();
            heap.insert(largest-second);
        }
        return heap.getHeap().get(0);
    }

    public static int repeatedRandom(int[] arr) {
        Random r = new Random();
        int[] rand = {-1, 1};

        int[] solution = new int[arr.length];
        for (int j=0; j<arr.length; j++) {
            solution[j] = rand[r.nextInt(2)];
        }

        int residue = residue(arr, solution);

        int[] currSolution = new int[arr.length];

        int MAX_ITER = 1000;
        for (int i=0; i<MAX_ITER; i++) {
            for (int j=0; j<arr.length; j++) {
                currSolution[j] = rand[r.nextInt(2)];
            }
            if (residue(arr, currSolution) < residue) {
                solution = currSolution;
                residue = residue(arr, solution);
            }
        }

        return residue;
    }



    public static int repeatedRandomPP(int[] arr) {
        Random r = new Random();

        int[] solution = new int[arr.length];
        for (int j=0; j<arr.length; j++) {
            solution[j] = r.nextInt(arr.length-1);
        }

        int residue = residuePP(arr, solution);

        int[] currPrePartition = new int[arr.length];

        int MAX_ITER = 1000;
        for (int i=0; i<MAX_ITER; i++) {
            for (int j=0; j<arr.length; j++) {
                currPrePartition[j] = r.nextInt(arr.length-1)+1;
            }
            if (residuePP(arr, currPrePartition) < residue) {
                solution = currPrePartition;
                residue = residuePP(arr, solution);
            }
        }

        return residue;
    }
}

public class Partition {



    public static void heapTests() {
        maxHeap heap = new maxHeap();
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

        maxHeap heap2 = new maxHeap(new ArrayList<Integer>(Arrays.asList(10,8,7,6,5)));
        System.out.println(heap2.toString());
    }

    public static void main(String[] args) {
        // heapTests();
        ArrayList<Integer> nums = new ArrayList<Integer>(Arrays.asList(10,8,7,6,5));
        System.out.println(partitionAlgs.kk(nums));
        System.out.println(partitionAlgs.repeatedRandom(new int[] {10,8,7,6,5}));
    }
}
