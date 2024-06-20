package src.problema_2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main_sync {

    public static class Ponte{
        
        private final Lock lock = new ReentrantLock();

        public void atravesaPonte(String nomeCarro) throws InterruptedException{
            System.out.println(nomeCarro +" tentando atravessar ponte...");
            System.out.println();
            lock.lock();

            try{
                System.out.println(nomeCarro+ " atravessou!");
            }
            finally {
                Thread.sleep(1500);
                lock.unlock();
            }
        }

    }

    public static class Carro implements Runnable{
        private final Ponte carro;
        public String nomeCarro;

        public Carro(Ponte carro, String nomeCarro){
            this.carro = carro;
            this.nomeCarro = nomeCarro;
        }

        public void run(){
            try{
                carro.atravesaPonte(nomeCarro);
            }
            catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        Ponte ponte = new Ponte();

        Carro carroA = new Carro(ponte,"Carro A");
        Carro carroB = new Carro(ponte,"Carro B");
        Carro carroC = new Carro(ponte,"Carro C");
        Carro carroD = new Carro(ponte,"Carro D");

        Thread t1 = new Thread(carroA);
        Thread t2 = new Thread(carroB);
        Thread t3 = new Thread(carroC);
        Thread t4 = new Thread(carroD);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}

