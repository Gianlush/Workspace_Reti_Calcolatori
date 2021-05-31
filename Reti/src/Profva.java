import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Profva {

    public static synchronized void metodo1() throws InterruptedException {
        System.out.println("prima stampa del metodo 1");
        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println("prima stampa del metodo 1");
    }

    public static synchronized void metodo2() throws InterruptedException {
        System.out.println("prima stampa del metodo 2");
        TimeUnit.MILLISECONDS.sleep(3000);
        System.out.println("prima stampa del metodo 2");
    }



    public static void main(String[] args) throws InterruptedException {
        Profva p1 = new Profva();
        Profva p2 = new Profva();
        new ProvaThread(1,p1).start();
        new ProvaThread(2,p2).start();
        System.out.println("Avviati tutti");
    }
}

class  ProvaThread extends Thread {
    private int n;
    private Profva p;
    public ProvaThread(int n,Profva p){
        this.n=n;
        this.p=p;
    }

    public void run(){
        try {
            if (n == 1) p.metodo1();
            else p.metodo2();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


