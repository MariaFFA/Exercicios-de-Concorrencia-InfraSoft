package src.problema_6;

import javax.lang.model.type.NullType;
import java.util.concurrent.Semaphore;
import java.util.Random;

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
               else if(pessoasBanheiro[0] != pessoaEntrar){
                   return 0; // Há alguma pessoa do sexo oposto no banheiro
               }

            return 1; //Não há ninguém do sexo oposot no banheiro
        }

        public void entraBanheiro(String nomePessoa, char sexo) throws InterruptedException{

            banheiroDisponivel.acquire();

            System.out.println(nomePessoa +" do sexo "+sexo+" tentando entrar no banheiro...");
            System.out.println();

            int possivelEntrar = verificaPessoasBanheiro(pessoasBanheiro, sexo);

            if(ocupacaoBanheiro == capacidadeBanheiro || possivelEntrar == 0){
                Thread.sleep(numeroAleatorio.nextInt(2000)); // Simula um tempo de uso do banheiro aleatório
                System.out.println(nomePessoa +" do sexo "+sexo+" não conseguiu entrar no banheiro");
                System.out.println();

                ocupacaoBanheiro--;

                if(ocupacaoBanheiro == 0){
                    pessoasBanheiro[0] = ' ';
                }
                banheiroDisponivel.release();
            }else{
                System.out.println(nomePessoa+ " do sexo "+sexo+" entrou no banheiro!");
                System.out.println();

                ocupacaoBanheiro++;
                banheiroDisponivel.release();
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
