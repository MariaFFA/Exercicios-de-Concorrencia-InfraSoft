package src.problema_6;

import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

    public static class Banheiro{
        private final int capacidadeBanheiro;
        private int ocupacaoBanheiro;
        private final Semaphore banheiroDisponivel;

        public Banheiro(int capacidadeBanheiro){
            this.capacidadeBanheiro = capacidadeBanheiro;
            this.banheiroDisponivel = new Semaphore(capacidadeBanheiro);
        }

        Random numeroAleatorio = new Random();
        char[] pessoasBanheiro = {' '};

        public int verificaPessoasBanheiro(char[] pessoasBanheiro, char pessoaEntrar){

            if(pessoasBanheiro[0] == ' '){
                pessoasBanheiro[0] = pessoaEntrar;
            }
            if(pessoasBanheiro[0] != pessoaEntrar){
                return 0; // Há alguma pessoa do sexo oposto no banheiro
            }

            return 1; //Não há ninguém do sexo oposot no banheiro
        }

        private final Lock lock = new ReentrantLock();
        public void entraBanheiro(String nomePessoa, char sexo) throws InterruptedException{

            System.out.println(nomePessoa+" - "+sexo);

            int possivelEntrar = verificaPessoasBanheiro(pessoasBanheiro, sexo);

            lock.lock();
            while(ocupacaoBanheiro == capacidadeBanheiro || possivelEntrar == 0){
                lock.unlock();

                System.out.println(nomePessoa +" do sexo "+sexo+" não conseguiu entrar no banheiro("+pessoasBanheiro[0]+") - "+ocupacaoBanheiro+"/"+capacidadeBanheiro);
                System.out.println();

                Thread.sleep(1000);

                lock.lock();

                possivelEntrar = verificaPessoasBanheiro(pessoasBanheiro, sexo);

            }
            ocupacaoBanheiro++;
            pessoasBanheiro[0] = sexo;
            lock.unlock();


            banheiroDisponivel.acquire();

            System.out.println(nomePessoa+ " do sexo "+sexo+" entrou no banheiro ("+pessoasBanheiro[0]+")!"+" - "+ocupacaoBanheiro+"/"+capacidadeBanheiro);
            System.out.println();

            Thread.sleep(numeroAleatorio.nextInt(1000,3000)); // Simula um tempo de uso do banheiro aleatório

            lock.lock();

            ocupacaoBanheiro--;
            System.out.println(nomePessoa +" do sexo "+sexo+" SAIU"+" - "+ocupacaoBanheiro+"/"+capacidadeBanheiro);

            if(ocupacaoBanheiro == 0){
                System.out.println("Banheiro vazio!"+" - "+ocupacaoBanheiro+"/"+capacidadeBanheiro);
                System.out.println();
                pessoasBanheiro[0] = ' ';
            }

            banheiroDisponivel.release();
            lock.unlock();

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


    public static void main(String[] args)throws InterruptedException{

        Banheiro banheiro = new Banheiro(3);

        Random random = new Random();

        char sexo1 = 'f';
        char sexo2 = 'm';

        for(int i = 0; i < 5; i++){

            int idx = random.nextInt(2);
            char sexo = (idx == 0) ? sexo1 : sexo2;

            Pessoa pessoa = new Pessoa("Pessoa "+i, sexo, banheiro);
            Thread.sleep(random.nextInt(100, 500));
            pessoa.start();
        }
    }
}
