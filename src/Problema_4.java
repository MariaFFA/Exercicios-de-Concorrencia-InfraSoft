package src;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Problema_4 {
    // Até 5 cadeiras simultaneamente
    private static final Semaphore chairs = new Semaphore(5);

    // Para saber se a mesa está disponível para sentar
    public static class Mesa {
        private int lugares;
        private final Lock lock = new ReentrantLock();

        public Mesa() {
            this.lugares = 0;
        }

        // Adiciona um lugar
        public void addLugar() throws InterruptedException {
            lock.lock();
            try {
                lugares += 1;
            }finally{
                lock.unlock();
            }
        }
        // Remove um lugar
        public void remLugar() throws InterruptedException {
            lock.lock();
            try {
                lugares -= 1;
            }finally{
                lock.unlock();
            }
        }

        public int getLugar() throws InterruptedException {
            lock.lock();
            try {
                return lugares;
            }finally{
                lock.unlock();
            }
        }
    }

    // Cada Pessoa
    static class Person implements Runnable {
        private final Mesa conta;

        public Person( Mesa conta) {
            this.conta = conta;
        }

        public void run() {
            try {
                // Tenta sentar
                chairs.acquire();
                System.out.println("Occupies a chair.");
                conta.addLugar();

                // Dá um tempo para comer
                Thread.sleep(600);
                // Se estiver cheio, só saem todos juntos
                if (chairs.availablePermits() == 0) {
                    Thread.sleep(600);
                    if (conta.getLugar() == 5) {
                        conta.remLugar();
                        conta.remLugar();
                        conta.remLugar();
                        conta.remLugar();
                        conta.remLugar();

                        System.out.println("Leaves 5 chairs.");
                        chairs.release();
                        chairs.release();
                        chairs.release();
                        chairs.release();
                        chairs.release();

                    }

                }else {
                    // Libera a cadeira
                    System.out.println("Leaves a chair.");
                    conta.remLugar();
                    chairs.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args){
        Mesa conta = new Mesa();
        // Criando as pessoas
        for (int i = 1; i <= 14; i++) {
            Thread person = new Thread(new Person(conta));
            person.start();
        }
    }
}