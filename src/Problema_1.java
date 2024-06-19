import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Problema_1 {
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
                System.out.println("+ " + valor + " = " + saldo);
            }finally{
                lock.unlock();
            }

        }

        public void saque(int valor) throws InterruptedException{
            lock.lock();
            try {
                if (valor <= saldo) {
                    saldo -= valor;
                    System.out.println("- " + valor + " = " + saldo);
                } else {
                    System.out.println(valor + " é maior que " + saldo);
                    System.out.println("Saldo Insuficiente: operação anulada");
                }
            }finally{
                lock.unlock();
            }
        }

    }

    public static class Operacao implements Runnable{
        private final ContaCompartilhada conta;
        private final int valor;
        private final String operation;

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
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ContaCompartilhada conta = new ContaCompartilhada(0);

        System.out.println("Saldo Inicial: " + conta.saldo);

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

        Thread_Operacao1.join();
        Thread_Operacao2.join();
        Thread_Operacao3.join();
        Thread_Operacao4.join();
        Thread_Operacao5.join();
        Thread_Operacao6.join();

        System.out.println("Saldo Final: " + conta.saldo);

    }
}