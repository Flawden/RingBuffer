public class RingBuffer {
    private final int[] buffer;
    private final int capacity;
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    public RingBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new int[capacity];
    }

    public synchronized void put(int value) throws InterruptedException {
        while (size == capacity) {
            wait();
        }
        buffer[tail] = value;
        tail = (tail + 1) % capacity;
        size++;
        System.out.println("Put: " + value + ", size: " + size);
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        int value = buffer[head];
        head = (head + 1) % capacity;
        size--;
        System.out.println("Get: " + value + ", size: " + size);
        notifyAll();
        return value;
    }
}