import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exercicio1 {
    public static class ContaCompartilhada{
        private int saldo;

        private final Lock lock = new ReentrantLock();

        public ContaCompartilhada(int saldoInicial) {
            this.saldo = saldoInicial;
        }

        public void deposito(int valor) throws InterruptedException {
            lock.lock();
            try {
                saldo += valor;
            }finally{
                lock.unlock();
            }

        }

        public void saque(int valor) throws InterruptedException{
            lock.lock();
            try {
                if (valor <= saldo) {
                    saldo -= valor;
                } else {
                    System.out.println("Saldo Insuficiente");
                }
            }finally{
                lock.unlock();
            }
        }

        public void getSaldo() throws InterruptedException{
            lock.lock();
            try {
                System.out.println(saldo);
            }finally{
                lock.unlock();
            }
        }
    }

    public static class Operacao implements Runnable{
        private ContaCompartilhada conta;
        private int valor;
        private String operation;

        public Operacao(ContaCompartilhada conta, int valor, String operation){
            this.conta = conta;
            this.valor = valor;
            this.operation = operation;
        }
        public void run(){
            try{
                if (operation.equals("deposito")){
                    conta.deposito(valor);
                }
                else if (operation.equals("saque")){
                    conta.saque(valor);
                }
                conta.getSaldo();
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) {
        ContaCompartilhada conta = new ContaCompartilhada(0);

        Operacao operacao1 = new Operacao(conta, 100, "deposito");
        Operacao operacao2 = new Operacao(conta, 50, "saque");
        Operacao operacao3 = new Operacao(conta, 25, "deposito");
        Operacao operacao4 = new Operacao(conta, 80, "saque");
        Operacao operacao5 = new Operacao(conta, 500, "deposito");
        Operacao operacao6 = new Operacao(conta, 100, "saque");


        Thread Thread_Operacao1 = new Thread(operacao1);
        Thread Thread_Operacao2 = new Thread(operacao2);
        Thread Thread_Operacao3 = new Thread(operacao3);
        Thread Thread_Operacao4 = new Thread(operacao4);
        Thread Thread_Operacao5 = new Thread(operacao5);
        Thread Thread_Operacao6 = new Thread(operacao6);


        Thread_Operacao1.start();
        Thread_Operacao2.start();
        Thread_Operacao3.start();
        Thread_Operacao4.start();
        Thread_Operacao5.start();
        Thread_Operacao6.start();

        ;
    }
}