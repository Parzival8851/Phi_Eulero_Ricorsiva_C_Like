import java.util.ArrayList;
import it.unibs.fp.mylib.InputDati;

public class Phi_Eulero
{
    public static final String INSERIRE_UN_INTERO = "Inserire un intero: ";
    public static final String RIFARE = "Vuoi rifare? ";
    public static ArrayList<Integer> divisori = new ArrayList<>();
    public static ArrayList<Integer> fattoriPrimi = new ArrayList<>();
    public static int n=0;
    public static int p=0;
    public static int k=0;
    public static int a=0;
    public static int b=0;


    public static void main(String[] args)
    {
        do
        {
            n=InputDati.leggiInteroNonNegativo(INSERIRE_UN_INTERO);
            System.out.println(phi(n));

        }while(InputDati.yesOrNo(RIFARE));

    }


    private static int phi(int n)
    {
        p=1;
        k=1;
        //a=1;
        //b=1;
        divisori.clear();
        fattoriPrimi.clear();
        return switch (calcoloPhi(n)) {
            case 1 -> 1;
            case 2 -> n - 1;
            case 3 -> (int) ((p - 1) * Math.pow(p, k - 1));
            case 4 -> casoMCD();
            default -> -1;
        };
    }

    private static int casoMCD()
    {
        int alpha=phi(a);
        int beta = phi(b);
        //phi(a) * phi(b);
        return alpha*beta;
    }

    private static int calcoloPhi(int n)
    {
        if (n ==1) return 1; // caso 1
        else if(isPrimo(n)) // caso n è primo, funziona anche senza esplicitare questo caso, ma è più veloce siccome risparmio molti cicli
        {
            p= n;
            k=1;
            return 2;
        }
        else
        {
            // calcolo divisori e fattori primi
            calcoloDivisori(n);
            primeFactor(n);

            // cerco se n=p^k con p primo
            /*ArrayList <Integer> tempDivisori = divisori; // uo una variabile temporanea sui divisori per poterli riutilizzare
            tempDivisori.retainAll(divisori); //intersezione fattori primi e divisori
            if (tempDivisori.size()==1) // se l'intersezione è un singolo numero
            {
                p=tempDivisori.get(0); // vuol dire che tutti i divisori sono potenze dei valori dei fattori primi (che saranno tutti uguali), per questo l'intersezione è unica
                k= fattoriPrimi.size(); // la potenza a cui è elevato p per fare n è il numero di fattori primi (8=2^3, divisiori di 8: 2,4)
                return 3;
            }*/
            if (fattoriPrimi.get(0).equals(fattoriPrimi.get(fattoriPrimi.size()-1)))
            {
                p= fattoriPrimi.get(0);
                k= fattoriPrimi.size();
                return 3;
            }
            else // sono nel caso in cui phi(n)=phi(a)*phi(b) con mcd(a,b)=1
            {
                // non mi serve calcolare mcd, mi basta prendere un elemento qualsiasi tra i fattori primi,
                // moltiplicarlo per tutte le sue potenze (a),
                // e mettere il resto in b
                a= fattoriPrimi.get(0);
                b=1;
                for (int i = 1; i < fattoriPrimi.size(); i++)
                {
                    if (fattoriPrimi.get(i).equals(fattoriPrimi.get(0))) a*= fattoriPrimi.get(i);
                    else b*= fattoriPrimi.get(i);
                }
                // così facendo sono certo che a*b=n && mcd(a,b)=1, e non devo calcolarlo
                return 4;
            }

        }
    }

    public static boolean isPrimo(int n)
     {
         if (n <= 1)
             return false;
         else
             return primo(n, n - 1);
     }

     private static boolean primo(int n, int y)
     {
        if(y==1)
            return true;
        else if(n%y==0)
        {

            return false;
        }

        else
            return primo(n, y - 1);
     }

    /*
    private static int mcd(int m, int n) {
        if (m == n) {
            return m;
        } else if (m > n) {
            return mcd(m - n, n);
        } else {
            return mcd(m, n - m);
        }
    }*/

    /**
     * escluso 1 e n
     * @param n num da cui calcolo i divisori
     */
    private static void calcoloDivisori(int n)
    {
        for (int i = 2; i < Math.round((float) n/2)+1; i++)
            if (n%i==0) divisori.add(i);
    }

    /**
     *calcolo i fattori primi
     * @param n numero su cui calcolare i fattori primi
     */
    private static void primeFactor(int n)
    {
        int p=2;
        do
        {
            if (n%p==0)
            {
                fattoriPrimi.add(p);
                n/=p;
            }
            else p++;
        }while(n>=(p*p));
        fattoriPrimi.add(n);
    }

}
