package src;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Problema_5 {
    // Até 50 cadeiras simultaneamente
    private static final Semaphore chairs = new Semaphore(50);

    // Para saber se a mesa está disponível para sentar
    public static class Ponto {
        private int lugares;
        private final Lock lock = new ReentrantLock();

        public Ponto() {
            this.lugares = 0;
        }

        // Adiciona um lugar
        public void addLugar() throws InterruptedException {
            lock.lock();
            try {
                lugares += 1;
                System.out.println("No ponto: " + lugares);
            } finally {
                lock.unlock();
            }
        }


        public void encherOnibus() throws InterruptedException {
            lock.lock();
            try {
                // Caso sobre pessoas no ponto
                if (lugares > 50) {
                    chairs.acquire(50);
                    lugares -= 50;
                    // Quantos passageiros?
                    System.out.println("Onibus com 50 passageiros");
                // Caso esvazie a parada
                } else {
                    // Quantos passageiros?
                    System.out.println("Onibus com " + lugares + " passageiros");
                    lugares = 0;
                    chairs.acquire(lugares);
                }
            } finally {
                lock.unlock();
            }
        }

        // Cada Pessoa
        static class Person implements Runnable {
            private final Ponto conta;

            public Person(Ponto conta) {
                this.conta = conta;
            }

            public void run() {
                try {
                    // Simulando tempo aleatório até chegar na parada
                    Random gerador = new Random();
                    int tempoChegar = gerador.nextInt(25000);
                    Thread.sleep(tempoChegar);

                    // Adicionar e quantidade de pessoas na parada
                    conta.addLugar();


                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        static class Onibus implements Runnable {
            private final Ponto conta;

            public Onibus(Ponto conta) {
                this.conta = conta;
            }

            public void run() {
                try {
                    // Chegou o Onibus
                    conta.encherOnibus();

                    // Reiniciar onibus, esvaziando os assentos
                    chairs.release(50 - chairs.availablePermits());

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        public static void main(String[] args) throws InterruptedException {
            Ponto conta = new Ponto();

            // Criando as pessoas
            for (int i = 1; i <= 500; i++) {
                Thread person = new Thread(new Person(conta));
                person.start();
            }
            for (int i = 1; i <= 15; i++) {
                Thread onibus = new Thread(new Onibus(conta));
                Random gerador = new Random();
                int tempoVolta = gerador.nextInt((3000 - 1000) + 1) + 1000;
                Thread.sleep(tempoVolta);
                onibus.start();
            }
        }
    }
}