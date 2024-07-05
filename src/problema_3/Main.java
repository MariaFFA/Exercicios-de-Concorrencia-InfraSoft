package src.problema_3;

import java.util.concurrent.Semaphore;

public class Main {

    public static class Barbearia{
        private int qtdCadeiras;
        private final int qtdCadeirasInicial;
        private final Semaphore barbeiroDisponivel;
        private final Semaphore barbeiroDormindo;

        public Barbearia(int qtdCadeiras){
            this.qtdCadeiras = qtdCadeiras;
            this.qtdCadeirasInicial = qtdCadeiras;
            barbeiroDisponivel = new Semaphore(1);
            barbeiroDormindo = new Semaphore(0);
        }

        public void novoCliente(String nomeCliente) throws InterruptedException{
            barbeiroDisponivel.acquire(); //Requisitando acesso à barbearia

            if(qtdCadeiras > 0){

                qtdCadeiras --;
                System.out.println(nomeCliente + " sendo atendido.");

                System.out.println();

                barbeiroDisponivel.release(); //Permitindo que o barbeiro trabalhe


            }else{
                System.out.println(nomeCliente +" não foi atendido, barbearia cheia!");
                Thread.sleep(1000); // Simulação do tempo do corte
                qtdCadeiras ++;
                barbeiroDisponivel.release();
            }

            if(qtdCadeiras == qtdCadeirasInicial){
                barbeiroDormindo.acquire();
                System.out.println("Barbeiro dormindo");
                Thread.sleep(1500);
                barbeiroDormindo.release();
            }
        }
    }

    private static class Cliente extends Thread{
        private String nomeCliente;
        private Barbearia barbearia;

        public Cliente(String nomeCliente, Barbearia barbearia){
            this.nomeCliente = nomeCliente;
            this.barbearia = barbearia;
        }

        public void run(){
            try {
                barbearia.novoCliente(nomeCliente);
            }
            catch (InterruptedException e){}
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Barbearia barbearia = new Barbearia(5);

        for(int i = 0; i < 10; i++){
            Cliente cliente = new Cliente("Cliente " + i, barbearia);
            cliente.start();
        }
    }
}
