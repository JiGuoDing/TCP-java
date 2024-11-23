/**
 * 用 RunnableThread 接口实现多线程：实现 Runnable 接口，重写 run 方法，执行线程
 * 丢入 runnable 接口实现类，调用 start() 方法
 */
public class RunnableThread implements Runnable{
    @Override
    public void run(){
//         run 方法线程体
        for (int i = 0; i < 10; i++) {
            System.out.println("现在是线程 " + Thread.currentThread().getName() + " 在执行");
        }
    }

    public static void main(String[] args) {
        // 创建 Runnable 接口的实现类对象
        RunnableThread runnableThread = new RunnableThread();
        // 创建线程对象，通过线程对象来开启线程
        Thread thread = new Thread(runnableThread);

        thread.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("现在是线程 " + Thread.currentThread().getName() + " 在执行");
        }

//        new Thread(runnableThread).start();
    }
}
