package src;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;

public class Problema_4 {
    public static class ContaCompartilhada {
        private boolean cheio;
        private boolean naoSentou;
        private int cadeiras;

        public final Lock lock = new ReentrantLock();

        public ContaCompartilhada() {
            this.cheio = false;
        }

        public void chegou() throws InterruptedException {
                lock.lock();
                if (!cheio ) {
                    try {
                        cadeiras += 1;
                        System.out.println("Cadeiras ocupadas " + cadeiras);


                    } finally {
                        if (cadeiras == 3) {
                            cheio = true;
                        }
                        lock.unlock();
                        Thread.sleep(600);
                        lock.lock();
                        cadeiras -= 1;
                        System.out.println("Cadeiras ocupadas " + cadeiras);
                    }
                }
                Operacao.sentou = true;
                lock.unlock();


        }

        public void saiu() throws InterruptedException {
            lock.lock();
            try {
                cadeiras -= 1;
                System.out.println("Cadeiras ocupadas " + cadeiras);

            } finally {
                if (cadeiras == 0) {
                    cheio = false;
                }
                lock.unlock();
            }

        }

        public void isCheio() throws InterruptedException {
            lock.lock();
            if (!cheio) {
                lock.unlock();
            }

        }

        public static class Operacao implements Runnable {
            private final ContaCompartilhada conta;
            private static boolean sentou;
            private int numero;

            public Operacao(ContaCompartilhada conta) {
                this.conta = conta;
                this.sentou = false;
            }

            public void run() {
                while (!sentou) {
                    try {
                        conta.chegou();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                sentou = false;
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ContaCompartilhada conta = new ContaCompartilhada();
            List<Integer> fila = new ArrayList<>();
            System.out.println("Saldo Inicial: " + conta.cheio);
            Operacao operacao = new Operacao(conta);
            for (int i = 0; i < 5; i++) {
                Thread tOperacao = new Thread(operacao);
                fila.add(i, tOperacao.getPriority());
                tOperacao.start();
            }

        }
    }
}