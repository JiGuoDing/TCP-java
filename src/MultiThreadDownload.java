import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

// 实现多线程同步下载图片
public class MultiThreadDownload {
    public static void main(String[] args) {
        DownloadThread downloadThread0 = new DownloadThread("https://cn.bing.com/images/search?view=detailV2&ccid=UrLF2Mmy&id=84832CDC81AA7B78A0987DC6CF44ACFDAF17C75C&thid=OIP.UrLF2MmyEZbrEdQSLy8p8QHaD6&mediaurl=https%3a%2f%2fn.sinaimg.cn%2fsinakd10117%2f732%2fw2048h1084%2f20200627%2f086d-ivmqpck3629916.jpg&exph=1084&expw=2048&q=%e7%83%a4%e9%b8%ad&simid=608013447018188881&FORM=IRPRST&ck=21905D5218C21B6A35F256B08AE50830&selectedIndex=1&itb=0", "烤鸭0.jpg");
        DownloadThread downloadThread1 = new DownloadThread("https://cn.bing.com/images/search?view=detailV2&ccid=UrLF2Mmy&id=84832CDC81AA7B78A0987DC6CF44ACFDAF17C75C&thid=OIP.UrLF2MmyEZbrEdQSLy8p8QHaD6&mediaurl=https%3a%2f%2fn.sinaimg.cn%2fsinakd10117%2f732%2fw2048h1084%2f20200627%2f086d-ivmqpck3629916.jpg&exph=1084&expw=2048&q=%e7%83%a4%e9%b8%ad&simid=608013447018188881&FORM=IRPRST&ck=21905D5218C21B6A35F256B08AE50830&selectedIndex=1&itb=0", "烤鸭1.jpg");
        DownloadThread downloadThread2 = new DownloadThread("https://cn.bing.com/images/search?view=detailV2&ccid=UrLF2Mmy&id=84832CDC81AA7B78A0987DC6CF44ACFDAF17C75C&thid=OIP.UrLF2MmyEZbrEdQSLy8p8QHaD6&mediaurl=https%3a%2f%2fn.sinaimg.cn%2fsinakd10117%2f732%2fw2048h1084%2f20200627%2f086d-ivmqpck3629916.jpg&exph=1084&expw=2048&q=%e7%83%a4%e9%b8%ad&simid=608013447018188881&FORM=IRPRST&ck=21905D5218C21B6A35F256B08AE50830&selectedIndex=1&itb=0", "烤鸭2.jpg");

        downloadThread0.start();
        downloadThread1.start();
        downloadThread2.start();
    }
}

// 下载器
class WebDownload{
    // 下载方法
    public void download(String url, String filename) throws IOException {
        FileUtils.copyURLToFile(new URL(url), new File(filename));
    }
}

/**
 * 下载线程
 */
class DownloadThread extends Thread{

    private final String url; // 网络图片地址
    private final String filename; // 保存的文件名

    public DownloadThread(String url, String filename){
        this.url = url;
        this.filename = filename;
    }

    @Override
    public void run(){
        super.run();
        WebDownload webDownload = new WebDownload();
        try {
            webDownload.download(url, filename);
            System.out.println("下载了文件名为：" + filename + " 的文件");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
