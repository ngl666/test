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
        System.out.println("...");
        backup();
        System.out.println("...");

    }

    public static void backup() {
        try {
            Runtime rt = Runtime.getRuntime();

           
            Process child = rt.exec("sh -c /usr/local/mysql/bin/mysqldump --column-statistics=0 -h localhost -p3306 -uroot -proot  demo");
            
            
            InputStream in = child.getInputStream();

            InputStreamReader xx = new InputStreamReader(in, "utf-8");
            

            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            getfilePath();
            
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
        System.out.println(" "+fileName);
        path="47.106.11.120:8080/usr/local/MysqlBackup/"+dataStr+".sql";
        System.out.println("url "+path);
    }
    }

