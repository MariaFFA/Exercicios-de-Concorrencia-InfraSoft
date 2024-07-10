package src;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Problema_5 {
    // Até 50 cadeiras simultaneamente
    private static final Semaphore chairs = new Semaphore(10);

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
            } finally {
                lock.unlock();
            }
        }

        // Remove um lugar
        public void remLugar() throws InterruptedException {
            lock.lock();
            try {
                lugares -= 1;
            } finally {
                lock.unlock();
            }
        }

        public int getLugar() throws InterruptedException {
            lock.lock();
            try {
                return lugares;
            } finally {
                lock.unlock();
            }
        }

        public void tentaSentar() throws InterruptedException {
            chairs.acquire();


            // Dá um tempo para comer
            // Se estiver cheio, só saem todos juntos
            if (chairs.availablePermits() == 0) {
                if (getLugar() == 5) {
                    remLugar();
                    remLugar();
                    remLugar();
                    remLugar();
                    remLugar();

                    System.out.println("Leaves 5 chairs.");
                    chairs.release();
                    chairs.release();
                    chairs.release();
                    chairs.release();
                    chairs.release();

                }

            } else {
                // Libera a cadeira
                System.out.println("Leaves a chair.");
                remLugar();
                chairs.release();
            }
        }


        // Cada Pessoa
        static class Person implements Runnable {
            private final Mesa conta;

            public Person(Mesa conta) {
                this.conta = conta;
            }

            public void run() {
                try {
                    // Simulando tempo aleatório até chegar na parada
                    Thread.sleep(6);

                    // Tenta sentar
                    chairs.acquire();
                    System.out.println("Sentou: " + (10-chairs.availablePermits()));

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        static class Onibus implements Runnable {
            private final Mesa conta;

            public Onibus(Mesa conta) {
                this.conta = conta;
            }

            public void run() {
                try {
                    // Simulando tempo entre 1 a 3 segundo par chegar vazio na parada
                    Thread.sleep(600);

                    // Quantos assentos vazios?
                    System.out.println(chairs.availablePermits());

                    // Reiniciar onibus, esvaziando os assentos
                    chairs.release(10- chairs.availablePermits());

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public static void main(String[] args) {
            Mesa conta = new Mesa();

            // Criando as pessoas
            for (int i = 1; i <= 103; i++) {
                Thread person = new Thread(new Person(conta));
                person.start();
            }
            for (int i = 1; i <= 10; i++) {
                Thread onibus = new Thread(new Onibus(conta));
                onibus.start();
            }
        }
    }
}