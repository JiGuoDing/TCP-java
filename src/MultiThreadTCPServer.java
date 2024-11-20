import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadTCPServer {
    public static void main(String[] args) throws IOException {
        // 服务器socket列表
        List<ServerSocket> serverSocketList = new ArrayList<>(2);
        // 监听端口号
        final int PORT = 17777;
        // 计数线程任务
        CountDownLatch countDownLatch = new CountDownLatch(2);
        try(
                // TCP 服务器0的socket
                ServerSocket serverSocket0 = new ServerSocket(PORT);
                // TCP 服务器1的socket
                ServerSocket serverSocket1 = new ServerSocket(PORT+1);
                )
        {
            serverSocketList.add(serverSocket0);
            System.out.println("TCP服务器0正在监听17777端口");
            serverSocketList.add(serverSocket1);
            System.out.println("TCP服务器1正在监听17778端口");

            // 创建线程池
            ExecutorService executorService = Executors.newFixedThreadPool(2);

            // 客户端socket列表
//            List<Socket> clientSocketList = new ArrayList<>(2);

            // 线程执行
            for (int i = 0; i < 2; i++) {
                int finalI = i;
                /*
                    execute()：用于提交一个 Runnable 任务，并且没有返回值。它只是将任务提交给线程池，而没有任何关于任务执行状态或结果的信息。
                    submit()：用于提交一个 Runnable 或 Callable 任务，并返回一个 Future 对象，允许你获取任务的执行结果或者检查任务的状态。
                 */
                // 等待客户端连接
                executorService.submit(() -> {
                    try(Socket clientSocket = serverSocketList.get(finalI).accept()){
                        System.out.println("服务器 " + finalI + " 使用线程： " + Thread.currentThread().getName() + " 成功连接至客户端");
                        // 设置超时（5秒）如果在指定时间内没有任何 I/O 操作，判断连接可能已失效。
                        clientSocket.setSoTimeout(5000);
                        try(BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
                            String line;
                            while (!clientSocket.isClosed()){
                                if ((line = reader.readLine()) != null){
                                    System.out.println("线程 " + Thread.currentThread().getName() + " 从客户端读取到：" + line);
                                    writer.println("服务器已收到：" + line);
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("线程 " + Thread.currentThread().getName() + " 非正常结束");
                            countDownLatch.countDown();
                            throw new RuntimeException(e);
                        }
                    } catch (IOException e){
                        System.out.println("线程 " + Thread.currentThread().getName() + " 非正常结束");
                        countDownLatch.countDown();
                        e.printStackTrace();
                    }
                    System.out.println("线程 " + Thread.currentThread().getName() + " 执行完毕");
                    countDownLatch.countDown();
                });
            }

            // 等待所有线程完毕
            countDownLatch.await();
            // 关闭线程池
            executorService.shutdown();
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

