package src.problema_2;

public class Main_noSync {

    public static class FioThread extends Thread{
        public String nomeCarro;

        public FioThread(String nomeCarro){
            this.nomeCarro = nomeCarro;
        }

        public void run(){
            System.out.println(nomeCarro+ " tentando atravessar a ponte..");
            System.out.println("...");
            System.out.println(nomeCarro+ " atravessou a ponte.");
            System.out.println("============================");

            try{
                Thread.sleep(3000);
            }
            catch (InterruptedException ignored){
                throw new RuntimeException(ignored);
            }

        }
    }

    public static void main(String[] args) {
        FioThread Carro1 = new FioThread("Carro 1");
        FioThread Carro2 = new FioThread("Carro 2");
        FioThread Carro3 = new FioThread("Carro 3");
        FioThread Carro4 = new FioThread("Carro 4");
        FioThread Carro5 = new FioThread("Carro 5");
        FioThread Carro6 = new FioThread("Carro 6");
        FioThread Carro7 = new FioThread("Carro 7");
        FioThread Carro8 = new FioThread("Carro 8");

        Carro1.start();
        Carro2.start();
        Carro3.start();
        Carro4.start();
        Carro5.start();
        Carro6.start();
        Carro7.start();
        Carro8.start();

    }

}

