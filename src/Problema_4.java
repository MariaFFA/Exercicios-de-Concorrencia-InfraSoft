package src;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Problema_4 {
    // Até 3 cadeiras simultaneamente
    private static final Semaphore chairs = new Semaphore(3);

    // Para saber se a mesa está disponível para sentar
    public static class Mesa {
        private boolean cheio;
        private final Lock lock = new ReentrantLock();

        public Mesa() {
            this.cheio = false;
        }
    }

    // Cada Pessoa
    static class Person implements Runnable {
        private final int id;
        private final Mesa conta;

        public Person(int id, Mesa conta) {
            this.id = id;
            this.conta = conta;
        }

        public void run() {
            try {
                // Tenta sentar
                chairs.acquire();
                System.out.println("Person " + id + " occupies a chair.");

                // Dá um tempo para comer
                Thread.sleep(1000);

                // Se esteve cheio, só sai todos juntos
                if (chairs.availablePermits() == 0) {
                    conta.cheio = true;
                    Thread.sleep(10);
                    conta.cheio = false;
                }
                while (conta.cheio) {
                    Thread.sleep(10);
                }
                // Libera a cadeira
                System.out.println("Person " + id + " leaves the chair.");
                chairs.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Mesa conta = new Mesa();
        // Criando as pessoas
        for (int i = 1; i <= 10; i++) {
            Thread person = new Thread(new Person(i, conta));
            person.start();
        }
    }
}
