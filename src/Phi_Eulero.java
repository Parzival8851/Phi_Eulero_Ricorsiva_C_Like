import java.util.ArrayList;
import it.unibs.fp.mylib.InputDati;

public class Phi_Eulero
{
    public static final String INSERIRE_UN_INTERO = "Inserire un intero: ";
    public static final String RIFARE = "Vuoi rifare? ";
    public static ArrayList<Integer> divisori = new ArrayList<>();
    public static int n=0;
    public static int p=0;
    public static int k=0;
    public static int a=0;
    public static int b=0;
    public static boolean trovato=false;


    public static void main(String[] args)
    {
        do
        {
            trovato=false;
            n=0;
            p=0;
            k=0;
            a=0;
            b=0;
            divisori.clear();
            n=InputDati.leggiInteroNonNegativo(INSERIRE_UN_INTERO);
            System.out.println(phi(n));

        }while(InputDati.yesOrNo(RIFARE));

    }


    private static int phi(int n)
    {
        return switch (calcoloPhi(n)) {
            case 1 -> 1;
            case 2 -> n - 1;
            case 3 -> (int) ((p - 1) * Math.pow(p, k - 1));
            case 4 -> phi(a) * phi(b);
            default -> -1;
        };
    }

    private static int calcoloPhi(int n)
    {
        calcoloDivisori(n);
        if (n ==1) return 1; // caso 1
        else if(isPrimo(n))
        {
            p= n;
            k=1;
            return 2;

        }
        else
        {

                if(divisori.get(0).equals(divisori.get(divisori.size()-1)))
                {
                    trovato=true;
                    p= divisori.get(0);
                    boolean find = false;
                    for (int j = 2; !find; j++)
                    {
                        if (Math.pow(p,j)==n)
                        {
                            find=true;
                            k=j;
                        }
                    }
                    return 3;
                }



            if(!trovato) // caso mcd
            {
                for (int i = 0; i < divisori.size() && !trovato; i++)
                {
                    for (int j = (divisori.size()-1) ; j > (i+1) && !trovato; j--)
                    {
                        int possibileProdotto= divisori.get(i)* divisori.get(j);
                        if(possibileProdotto== n && mcd(divisori.get(i), divisori.get(j))==1)
                        {
                            a= divisori.get(i);
                            b=divisori.get(j);
                            trovato=true;
                            return 4;

                        }
                    }
                }
            }

        }
        return -1; // errore
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

    private static int mcd(int m, int n) {
        if (m == n) {
            return m;
        } else if (m > n) {
            return mcd(m - n, n);
        } else {
            return mcd(m, n - m);
        }
    }

    private static void calcoloDivisori(int n)
    {
        if (!isPrimo(n))
        {
            for (int i = 2; i<(int) Math.floor(n/2)+1; i++)
            {
                if(n%i==0 && divisori.size()==0) // il primo divisore
                    divisori.add(i);
                else if(n%i==0 && n%divisori.get(0)==0)
                {
                    divisori.add(divisori.get(0));
                }
                else if(n%i==0)
                {
                    divisori.add(i);
                }
            }
        }

    }

}
