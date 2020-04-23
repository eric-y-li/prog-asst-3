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

public class Partition {

    public static int kk(ArrayList<Integer> arr) {
        maxHeap heap = new maxHeap(arr);
        while(heap.getSize() > 1) {
            int largest = heap.deleteMax();
            int second = heap.deleteMax();
            heap.insert(largest-second);
        }
        return heap.getHeap().get(0);
    }

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
        System.out.println(kk(nums));
    }
}
