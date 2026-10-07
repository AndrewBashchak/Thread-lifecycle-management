public class Main {
    public static void main(String[] args) {
        Thread fibThread = new Thread(new FibonacciRunnable(10), "Fibonacci-Thread");
        fibThread.start();
    }
}

class FibonacciRunnable implements Runnable {
    private final int count;

    public FibonacciRunnable(int count) {
        this.count = count;
    }

    public void run() {
        Thread childThread = new Thread(() -> {
            Thread current = Thread.currentThread();

            try {
                System.out.println(current.getName() + " sleeping for a second.");
                Thread.sleep(1000);
                System.out.println(current.getName() + " waked up.");
            } catch (InterruptedException e) {
                return;
            }
        }, "Child-Thread");
        childThread.start();

        try {
            childThread.join();
        } catch (InterruptedException e) {
            return;
        }

        long a = 0, b = 1;

        for (int i = 0; i < count; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }

            System.out.print(a + " ");

            long next = a + b;
            a = b;
            b = next;

            Thread.yield();
        }
    }
}
