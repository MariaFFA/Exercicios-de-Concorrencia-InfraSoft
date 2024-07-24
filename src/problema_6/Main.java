package src.problema_6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class Main {

    public static class Banheiro{
        private final int capacidadeBanheiro;
        private int ocupacaoBanheiro;

        public Banheiro(int capacidadeBanheiro){
            this.capacidadeBanheiro = capacidadeBanheiro;
        }

        Random numeroAleatorio = new Random();

        private final Lock lock = new ReentrantLock();

        char[] pessoasBanheiro = new char[3];

        public int verificaPessoasBanheiro(char[] pessoasBanheiro, char pessoaEntrar){

            for(int i = 0; i < 3; i++){
               if(pessoasBanheiro[i] != pessoaEntrar){
                   return 0; // Há alguma pessoa do sexo oposto no banheiro
               }
            }
            return 1; //Não há ninguém do sexo oposot no banheiro
        }

        public void entraBanheiro(String nomePessoa, char sexo) throws InterruptedException{

            System.out.println(nomePessoa +" do sexo "+sexo+" tentando entrar no banheiro...");
            System.out.println();

            int possivelEntrar = verificaPessoasBanheiro(pessoasBanheiro, sexo);

            if(ocupacaoBanheiro == capacidadeBanheiro || possivelEntrar == 0){
                System.out.println(nomePessoa+ " do sexo "+sexo+" não conseguiu entrar no banheiro!");
                lock.lock();
            }

            try{
                for(int i = 0; i < 3; i++){
                    pessoasBanheiro[i] = sexo;
                }
                System.out.println(nomePessoa+ " do sexo "+sexo+" entrou no banheiro!");
            }
            finally {
                Thread.sleep(numeroAleatorio.nextInt(2000)); // Simula um tempo de uso do banheiro aleatório para cada pessoa
                lock.unlock();
                ocupacaoBanheiro --;
                pessoasBanheiro[ocupacaoBanheiro] = ' ';
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

        Banheiro banheiro = new Banheiro(3);

        Random indiceAleatorio = new Random();

        char sexo1 = 'f';
        char sexo2 = 'm';

        for(int i = 0; i < 10; i++){

            int idx = indiceAleatorio.nextInt(2);
            char sexo = (idx == 0) ? sexo1 : sexo2;

            Pessoa pessoa = new Pessoa("Pessoa "+i, sexo, banheiro);
            pessoa.start();
        }


    }


}
