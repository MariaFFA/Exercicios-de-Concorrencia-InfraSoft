package src.problema_2;

public class Main {

    public static class FioThread extends Thread {
    //        public int permit;
        public String nomeCarro;

        public FioThread(String nomeCarro) {
            this.nomeCarro = nomeCarro;
        }

        public void run() {
            System.out.println(nomeCarro+ " tentando atravessar a ponte...");
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException ignored){}
        }
    }

    public static void main(String[] args) throws InterruptedException {

        FioThread Carro1 = new FioThread("Carro 1");
        FioThread Carro2 = new FioThread("Carro 2");
        FioThread Carro3 = new FioThread("Carro 3");
        FioThread Carro4 = new FioThread("Carro 4");
        FioThread Carro5 = new FioThread("Carro 5");
        FioThread Carro6 = new FioThread("Carro 6");
        FioThread Carro7 = new FioThread("Carro 7");
        FioThread Carro8 = new FioThread("Carro 8");

        Carro1.start();
        Carro1.join();
        System.out.println("Carro 1 Atravessou");

        System.out.println("======================");

        Carro2.start();
        Carro2.join();
        System.out.println("Carro 2 Atravessou");

        System.out.println("======================");

        Carro3.start();
        Carro3.join();
        System.out.println("Carro 3 Atravessou");

        System.out.println("======================");

        Carro4.start();
        Carro4.join();
        System.out.println("Carro 4 Atravessou");

        System.out.println("======================");

        Carro5.start();
        Carro5.join();
        System.out.println("Carro 5 Atravessou");

        System.out.println("======================");

        Carro6.start();
        Carro6.join();
        System.out.println("Carro 6 Atravessou");

        System.out.println("======================");

        Carro7.start();
        Carro7.join();
        System.out.println("Carro 7 Atravessou");

        System.out.println("======================");

        Carro8.start();
        Carro8.join();
        System.out.println("Carro 8 Atravessou");


    }
}

