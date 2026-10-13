public class Main {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(() -> {
            Thread fibThread = new Thread(new FibonacciRunnable(10), "Fibonacci-Thread");
            fibThread.setPriority(7);
            fibThread.start();

            Thread.State lastState = null;

            while (fibThread.getState() != Thread.State.TERMINATED) {
                Thread.State currentState = fibThread.getState();

                if (currentState != lastState) {
                    System.out.println("[DAEMON] State of " + fibThread.getName() + ": " + currentState);
                    lastState = currentState;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }

            while (true) {
                try {
                    System.out.println("[DAEMON] State of " + fibThread.getName() + ": " + fibThread.getState());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Monitor-Daemon");

        daemonThread.setDaemon(true);
        daemonThread.start();

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[MAIN] The main thread has finished execution.");
    }

    static class FibonacciRunnable implements Runnable {
        private final int count;

        public FibonacciRunnable(int count) {
            this.count = count;
        }

        public void run() {
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
}
