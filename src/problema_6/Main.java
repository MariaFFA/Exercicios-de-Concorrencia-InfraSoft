package src.problema_6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class Main {

    public static class Banheiro{
        private final int capacidadeBanheiro;
        private final char sexoDestinado;
        private int ocupacaoBanheiro;

        public Banheiro(int capacidadeBanheiro, char sexoDestinado){
            this.capacidadeBanheiro = capacidadeBanheiro;
            this.sexoDestinado = sexoDestinado;
        }

        Random numeroAleatorio = new Random();

        private final Lock lock = new ReentrantLock();

        public void entraBanheiro(String nomePessoa, char sexo) throws InterruptedException{

            System.out.println(nomePessoa +" do sexo "+sexo+" tentando entrar no banheiro "+ sexoDestinado +"...");
            System.out.println();

            if (sexo == sexoDestinado){
                ocupacaoBanheiro ++;
            }

            if(ocupacaoBanheiro == capacidadeBanheiro){
                lock.lock();
            }

            try{
                if(sexo == sexoDestinado){
                    System.out.println(nomePessoa+ " do sexo "+sexo+" entrou no banheiro "+ sexoDestinado +"!");
                }else{
                    System.out.println(nomePessoa+ " do sexo "+sexo+" não conseguiu entrar no banheiro "+ sexoDestinado +"!");
                }
            }
            finally {
                Thread.sleep(numeroAleatorio.nextInt(2000)); // Simula um tempo de uso do banheiro aleatório para cada pessoa
                lock.unlock();
                ocupacaoBanheiro --;
            }
        }

    }

    public static class Pessoa extends Thread{
        public Banheiro banheiro;
        private final String nomePessoa;
        private final char sexo;


        public Pessoa(String nomePessoa, char sexo, Banheiro banheiro){
            this.nomePessoa = nomePessoa;
            this.sexo = sexo;
            this.banheiro = banheiro;
        }

        public void run(){
            try{
                banheiro.entraBanheiro(nomePessoa, sexo);
            }
            catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args){

        Banheiro banheiroF = new Banheiro(3, 'f');
        Banheiro banheiroM = new Banheiro(3, 'm');

        Random escolhaBanheiro = new Random();

        Random indiceAleatorio = new Random();

        char sexo1 = 'f';
        char sexo2 = 'm';

        for(int i = 0; i < 10; i++){

            int idx = indiceAleatorio.nextInt(2);
            char sexo = (idx == 0) ? sexo1 : sexo2;

            int idxBanheiro = escolhaBanheiro.nextInt(2);
            Banheiro banheiroEscolhido = (idxBanheiro == 0) ? banheiroF : banheiroM;

            Pessoa pessoa = new Pessoa("Pessoa "+i, sexo, banheiroEscolhido);
            pessoa.start();
        }


    }


}
