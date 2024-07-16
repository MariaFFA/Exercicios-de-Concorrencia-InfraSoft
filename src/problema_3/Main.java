package src.problema_3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static class Barbeiro{
        private int ocupacaoBarbearia = 0;

        private final Lock lock = new ReentrantLock();

        private void atendeCliente(String nomeCliente) throws InterruptedException{

            int capacidadeBarbearia = 3;
            System.out.println(nomeCliente+ " chega a barbearia.");
            System.out.println();

            while(ocupacaoBarbearia < capacidadeBarbearia){
                ocupacaoBarbearia ++;
            }

            if(ocupacaoBarbearia == capacidadeBarbearia) {
                //Cliente é posto pra dormir
                lock.lock();

                try {
                    System.out.println(nomeCliente+ " atendido!");
                    ocupacaoBarbearia --;
                }
                finally {
                    Thread.sleep(1500);
                    lock.unlock();
                }
            }

        }
    }

    public static class Cliente implements Runnable{
        private final Barbeiro barbeiro;
        private final String nomeCliente;

        public Cliente(Barbeiro barbeiro, String nomeCliente){
            this.barbeiro = barbeiro;
            this.nomeCliente = nomeCliente;
        }

        public void run(){
            try {
                barbeiro.atendeCliente(nomeCliente);
            }
            catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        Barbeiro barbeiro = new Barbeiro();

        Cliente cliente1 = new Cliente(barbeiro, "Cliente 1");
        Cliente cliente2 = new Cliente(barbeiro, "Cliente 2");
        Cliente cliente3 = new Cliente(barbeiro, "Cliente 3");
        Cliente cliente4 = new Cliente(barbeiro, "Cliente 4");
        Cliente cliente5 = new Cliente(barbeiro, "Cliente 5");

        Thread t1 = new Thread(cliente1);
        Thread t2 = new Thread(cliente2);
        Thread t3 = new Thread(cliente3);
        Thread t4 = new Thread(cliente4);
        Thread t5 = new Thread(cliente5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


    }
}