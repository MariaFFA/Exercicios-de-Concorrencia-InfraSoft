package src;
import java.util.Random;
public class teste {

    public static void main(String[] args) {
        Random random = new Random();

        // Letras específicas entre as quais você deseja escolher
        char letra1 = 'f';
        char letra2 = 'm';

        // Gerar um índice aleatório (0 ou 1)
        int indiceAleatorio = random.nextInt(2);

        // Escolher a letra com base no índice aleatório
        char letraAleatoria = (indiceAleatorio == 0) ? letra1 : letra2;

        System.out.println("Letra aleatória: " + letraAleatoria);
    }

}
