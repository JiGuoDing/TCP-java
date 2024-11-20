# TCP-JAVA

---

### ***Closeable接口***
***接口定义***

```Java
package java.io;

import java.io.IOException;

public interface Closeable extends AutoCloseable {
    void close() throws IOException;
}
```

>它定义了一个简单但非常重要的方法，用于关闭资源，
如文件、网络流等  
Closeable 接口的主要作用是释放资源，
它通常用于需要手动关闭的资源，比如：  
文件流 (FileInputStream, FileOutputStream)
网络流 (Socket, ServerSocket)
数据库连接
 

## ***NOTE***
>***坑1***  

```Java
// 网络流的关闭会导致socket关闭
// 在TCPServer.java中
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
```

*若将writer与reader分开定义，即用另一个try-with-resource*
*形式定义writer，则在该try-with-resource结束的地方会将writer清理*
*进而会导致clientSocket.closed变为true*


