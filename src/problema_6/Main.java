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
                Thread.sleep(numeroAleatorio.nextInt(1000,3000)); // Simula um tempo de uso do banheiro aleatório
                ocupacaoBanheiro -= numeroAleatorio.nextInt(1, 4);
                System.out.println(nomePessoa +" do sexo "+sexo+" não conseguiu entrar no banheiro");
               // System.out.println("Capacidade banheiro: "+(ocupacaoBanheiro));
                System.out.println();

                if(ocupacaoBanheiro == 0){
                    System.out.println("Banheiro vazio!");
                    System.out.println();
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


    public static void main(String[] args)throws InterruptedException{

        Banheiro banheiro = new Banheiro(3);

        Random random = new Random();

        char sexo1 = 'f';
        char sexo2 = 'm';

        for(int i = 0; i < 100; i++){

            int idx = random.nextInt(2);
            char sexo = (idx == 0) ? sexo1 : sexo2;

            Pessoa pessoa = new Pessoa("Pessoa "+i, sexo, banheiro);
            Thread.sleep(random.nextInt(100, 500));
            pessoa.start();
        }
    }
}
