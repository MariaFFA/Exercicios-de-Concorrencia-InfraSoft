package src.problema_2;

public class Main_sync {

    public static class FioThread extends Thread {
        public String nomeCarro;

        public FioThread(String nomeCarro) {
            this.nomeCarro = nomeCarro;
        }

        public void run() {
            System.out.println(nomeCarro+ " tentando atravessar a ponte...");
            System.out.println("...");
            System.out.println(nomeCarro+ " atravessou a ponte.");
            System.out.println("======================");
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

        Carro2.start();
        Carro2.join();

        Carro3.start();
        Carro3.join();

        Carro4.start();
        Carro4.join();

        Carro5.start();
        Carro5.join();

        Carro6.start();
        Carro6.join();

        Carro7.start();
        Carro7.join();

        Carro8.start();
        Carro8.join();

    }
}

