import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class App {

    public static void imprimirMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int substituicaoNRU(int[][] matrizRAM) {
        Random numAleatorio = new Random();
        int classe;

        for (int i = 0; i < matrizRAM.length; i++) {
            classe = matrizRAM[i][3] << 1 | matrizRAM[i][4]; 
            if (classe == 0) {
                return i; 
            }
        }
        for (int i = 0; i < matrizRAM.length; i++) {
            classe = matrizRAM[i][3] << 1 | matrizRAM[i][4]; 
            if (classe == 1) {
                return i; 
            }
        }
        for (int i = 0; i < matrizRAM.length; i++) {
            classe = matrizRAM[i][3] << 1 | matrizRAM[i][4];
            if (classe == 2) {
                return i; 
            }
        }
        return numAleatorio.nextInt(matrizRAM.length);
    }

    public static int substituicaoFIFO(Queue<Integer> fifoQueue) {
        return fifoQueue.poll();
    }

    public static int substituicaoFIFOSC(Queue<Integer> fifoQueue, int[][] matrizRAM) {
        int pagina;

        do {
            pagina = fifoQueue.poll();
            if (matrizRAM[pagina][3] == 1) {
                fifoQueue.offer(pagina);
                matrizRAM[pagina][3] = 0; 
            }
        } while (matrizRAM[pagina][3] == 1);
        return pagina;
    }

    public static int substituicaoRelogio(int[][] matrizRAM, int ponteiro) {
        while (true) {
            if (matrizRAM[ponteiro][3] == 0) {
                return ponteiro;
            } else {
                matrizRAM[ponteiro][3] = 0; 
            }
            ponteiro = (ponteiro + 1) % matrizRAM.length;
        }
    }

    public static int substituicaoWSClock(int[][] matrizRAM, int[] envelhecimento) {
        Random numAleatorio = new Random();
        int ponteiro = numAleatorio.nextInt(matrizRAM.length);
        int ep = numAleatorio.nextInt(9900) + 100; 

        while (true) {
            if (envelhecimento[ponteiro] <= ep) {
                return ponteiro;
            }
            ponteiro = (ponteiro + 1) % matrizRAM.length;
        }
    }

    public static void main(String[] args) {
        int[][] matrizSwap = new int[100][6];
        int[][] matrizRAM = new int[10][6];
        int[] envelhecimento = new int[10]; 
        Queue<Integer> fifoQueue = new LinkedList<>(); 

        Random numAleatorio = new Random();
        for (int i = 0; i < 100; i++) {
            matrizSwap[i][0] = i;  
            matrizSwap[i][1] = i + 1;  
            matrizSwap[i][2] = numAleatorio.nextInt(50) + 1;  
            matrizSwap[i][3] = 0;  
            matrizSwap[i][4] = 0;  
            matrizSwap[i][5] = numAleatorio.nextInt(9900) + 100;  
        }
        for (int i = 0; i < 10; i++) {
            int indiceSorteado = numAleatorio.nextInt(100);
            matrizRAM[i] = Arrays.copyOf(matrizSwap[indiceSorteado], 6);
            fifoQueue.offer(i); 
        }

        System.out.println("Matriz SWAP no início:");
        imprimirMatriz(matrizSwap);

        System.out.println("\nMatriz RAM no início:");
        imprimirMatriz(matrizRAM);

        for (int i = 0; i < 1000; i++) {
            int instrucaoRequisitada = numAleatorio.nextInt(100) + 1;
            boolean instrucaoEncontrada = false;
            for (int j = 0; j < matrizRAM.length; j++) {
                if (matrizRAM[j][1] == instrucaoRequisitada) {
                    matrizRAM[j][3] = 1;
                    if (numAleatorio.nextDouble() < 0.3) {
                        matrizRAM[j][2] = matrizRAM[j][2] + 1;
                        matrizRAM[j][4] = 1;
                    }
                    instrucaoEncontrada = true;
                    break;  
                }
            }

            if (!instrucaoEncontrada) {
                int paginaSubstituida;
                paginaSubstituida = substituicaoNRU(matrizRAM);
                matrizRAM[paginaSubstituida] = Arrays.copyOf(matrizSwap[instrucaoRequisitada - 1], 6);
                fifoQueue.offer(paginaSubstituida);
                fifoQueue.poll();
                envelhecimento[paginaSubstituida] = matrizRAM[paginaSubstituida][5];
            }
            if ((i + 1) % 10 == 0) {
                for (int j = 0; j < matrizRAM.length; j++) {
                    matrizRAM[j][3] = 0;
                }
            }
            for (int j = 0; j < matrizRAM.length; j++) {
                if (matrizRAM[j][4] == 1) {
                    matrizSwap[matrizRAM[j][0]][2] = matrizRAM[j][2];
                    matrizSwap[matrizRAM[j][0]][3] = 0;
                    matrizSwap[matrizRAM[j][0]][4] = 0;
                }
            }
        }

        System.out.println("\nMatriz SWAP no final:");
        imprimirMatriz(matrizSwap);

        System.out.println("\nMatriz RAM no final após 1000 instruções:");
        imprimirMatriz(matrizRAM);
    }
}
