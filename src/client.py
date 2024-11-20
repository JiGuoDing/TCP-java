import socket
import time

def main():
    # 创建 TCP/IP socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # 连接到服务器
    client_socket.connect(('192.168.68.248', 18876))
    
    while True:
        
        # 定义发送的消息
        message = f"实时数据流{time.time()}\n"

        client_socket.sendall(message.encode('utf-8'))  # 发送消息，注意编码
    
        # 等待服务器的响应
        response = client_socket.recv(1024)  # 接收数据，最多接收 1024 字节
        while(not response):
            response = client_socket.recv(1024)

        print("服务器响应:", response.decode('utf-8'))  # 解码并打印响应
        
        # 休眠1秒
        time.sleep(1)

    # 关闭连接
    client_socket.close()

if __name__ == "__main__":
    main()

