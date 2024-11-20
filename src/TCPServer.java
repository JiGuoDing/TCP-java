import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(18876)){
            System.out.println("TCP服务器正在监听18876端口");

            // 接受客户端的连接请求
            try (Socket clientSocket = serverSocket.accept()){
                System.out.println("客户端连接成功");
                // 获取输入流
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    String line;
                    while (!clientSocket.isClosed()) {
                        if ((line = reader.readLine()) != null) {
                            System.out.println("接收到客户端消息： " + line);
                            // 告知客户端消息
                            writer.println("服务器已接受到消息：" + line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
