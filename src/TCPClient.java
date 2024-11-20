import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("192.168.68.248", 18876)) {  // 连接到服务器
            System.out.println("连接到服务器成功");

            // 获取输出流，发送消息给服务器
            try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)) {
                // 获取输入流，从控制台读取消息
                Scanner scanner = new Scanner(System.in);
                String message;
                while (true) {
                    System.out.print("请输入消息：");
                    message = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(message)) {
                        break; // 输入 "exit" 时退出
                    }
                    writer.println(message);  // 发送消息给服务器

                    // 获取输入流，接收服务器响应
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        String response = reader.readLine();
                        if (response != null) {
                            System.out.println("服务器响应： " + response);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
