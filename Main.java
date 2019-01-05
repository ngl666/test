import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

public class Main {
    public  static  String fileName;
    public   static String path;

    public static void main(String[] args) {
        System.out.println("开始备份...");
        backup();
        System.out.println("备份成功...");

    }

    public static void backup() {
        try {
            Runtime rt = Runtime.getRuntime();

            // 调用 调用mysql的安装目录的命令
            Process child = rt.exec("sh -c /usr/local/mysql/bin/mysqldump --column-statistics=0 -h localhost -p3306 -uroot -proot  demo");
            // 设置导出编码为utf-8。这里必须是utf-8
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

            InputStreamReader xx = new InputStreamReader(in, "utf-8");
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码

            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            getfilePath();
            // 要用来做导入用的sql目标文件：
            FileOutputStream fout = new FileOutputStream(
                    fileName);
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            writer.flush();
            in.close();
            xx.close();
            br.close();
            writer.close();
            fout.close();

            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public  static void getfilePath()
    {
        long msec = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dataStr = dateFormat.format(msec);
        fileName="/usr/local/MysqlBackup/"+dataStr+".sql";
        System.out.println("生成文件路径是"+fileName);
        path="47.106.11.120:8080/usr/local/MysqlBackup/"+dataStr+".sql";
        System.out.println("url是"+path);
    }
    }

