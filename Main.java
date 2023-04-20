import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
    String st = "Hello";

        Thread me = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    try {
                        System.out.println("Hello");
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        System.out.println(e);
                    }
                    }}
        }){
        };
        Thread you = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++){
                    try {
                        System.out.println("Hi");
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        System.out.println(e);
                    }
                }}
        }){
        };



        me.start();
        you.start();
    }
}
