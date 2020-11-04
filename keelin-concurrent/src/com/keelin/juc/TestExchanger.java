package keelin.juc;

import java.util.concurrent.Exchanger;

/**
 * @description: 测试Exchange
 * @author: skn
 * @create: 2020-11-04 22:15
 */
public class TestExchanger {
    public static void main(String[] args) {

        Exchanger exchanger = new Exchanger();

        new Thread(new ExchangeRunnable(exchanger, "A")).start();
        new Thread(new ExchangeRunnable(exchanger, "B")).start();

    }

    static class ExchangeRunnable implements Runnable {

        private Exchanger exchanger;
        private Object obj;

        public ExchangeRunnable(Exchanger exchanger, Object obj) {
            this.exchanger = exchanger;
            this.obj = obj;
        }

        public void run() {
            Object currentObj = this.obj;
            try {
                this.obj = exchanger.exchange(this.obj);
                System.out.printf("brfore exchange %s , after exchange %s \n",
                        currentObj, this.obj);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}