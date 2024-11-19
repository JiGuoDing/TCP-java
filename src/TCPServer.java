import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws IOException {
        try {
            // 创建 ServerSocket，监听18876端口
            ServerSocket serverSocket = new ServerSocket(18876);
            System.out.println("TCP服务器正在监听18876端口");

            // 接受客户端的连接请求
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端连接成功");

            // 获取输入流，读取客户端消息
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String msg = reader.readLine();
            System.out.println("接收到客户端消息： " + msg);

            // 获取输出流，向客户端发送消息
            PrintWriter writer = new PrintWriter((clientSocket.getOutputStream()), true);
            writer.println("服务器已接受到消息：" + msg);

            // 关闭流和套接字
            reader.close();
            writer.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
