package cn.liuyiyou.utils.file;

import cn.liuyiyou.utils.Constants;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.fileupload.FileItemStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadUtils {


    /**
     * 获取操作HTML保存的图片
     *
     * @param filePath
     * @return
     */
    public static String getHtmlImageSrc(String filePath) {
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(filePath);
            String str = readerFile(file);
            String[] strs = str.split("<img");
            for (int i = 1; i < strs.length; i++) {
//				strs[i].toString().indexOf("title")
//				strs[i].substring(0,strs[i].toString().indexOf("title")).lastIndexOf("/")
                //(strs[i].substring(0,strs[i].toString().indexOf("title")).length())-2));
                try {
                    if (i == strs.length - 1) {
                        sb.append(strs[i].substring(0, strs[i].toString().indexOf("title")).substring(strs[i].substring(0, strs[i].toString().indexOf("title")).lastIndexOf("/") + 1,
                                (strs[i].substring(0, strs[i].toString().indexOf("title")).length()) - 2));
                    } else {
                        sb.append(strs[i].substring(0, strs[i].toString().indexOf("title")).substring(strs[i].substring(0, strs[i].toString().indexOf("title")).lastIndexOf("/") + 1,
                                (strs[i].substring(0, strs[i].toString().indexOf("title")).length()) - 2) + ",");
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 读取文件操作
     *
     * @param f
     * @return
     * @throws IOException
     */
    public static String readerFile(File f) throws IOException {
        String str = "";
        String line = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(f);
            isr = new InputStreamReader(fis, Constants.ENCODE_GBK);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                str = str + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
        return str;
    }

    public static void sendPost(String uploadUrl, String filePath) {
        try {
            /**   开启新连接  传递图片名称参数 */
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            PrintWriter out = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            String str = getHtmlImageSrc(filePath);
            str = str.substring(0, str.lastIndexOf(","));
            out.print("htmlImg=" + str);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存HTML操作
     *
     * @param uploadUrl
     * @param filePath
     * @throws Exception
     */
    public static void uploadFile(String uploadUrl, String filePath) throws Exception {
        String boundary = "******";

        HttpURLConnection hc = null;  //http连接器
        ByteArrayOutputStream bos = null;//byte输出流，用来读取服务器返回的信息   
        InputStream is = null;//输入流，用来读取服务器返回的信息  
        try {
            URL url = new URL(uploadUrl);
            hc = (HttpURLConnection) url.openConnection();

            hc.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            hc.setRequestProperty("Charsert", Constants.ENCODE_UTF8);
            // 发送POST请求必须设置如下两行 
            hc.setDoOutput(true);
            hc.setDoInput(true);
            hc.setUseCaches(false);
            hc.setRequestMethod("POST");

            OutputStream dout = hc.getOutputStream();
            ////1.先写文字形式的post流 
            //头 
            //中 
            StringBuffer resSB = new StringBuffer("\r\n");
            //尾 
            String endBoundary = "\r\n--" + boundary + "--\r\n";
            //strParams 1:key 2:value 
            String strParams = getHtmlImageSrc(filePath);
            if (strParams != null) {
                resSB.append("Content-Disposition: form-data; name=\"").append("htmlImg").append("\"\r\n").append("\r\n").append(getHtmlImageSrc(filePath)).append("\r\n").append("--").append(boundary).append("\r\n");
            }
            String boundaryMessage = resSB.toString();

            //写出流 
            dout.write(("--" + boundary + boundaryMessage).getBytes(Constants.ENCODE_UTF8));

            //2.再写文件开式的post流 
            //fileParams 1:fileField, 2.fileName, 3.fileType, 4.filePath 
            resSB = new StringBuffer();
            String savePath = filePath.substring(filePath.lastIndexOf("/") + 1);
            resSB.append("Content-Disposition: form-data; name=\"").append("file").append("\"; filename=\"").append(
                    savePath).append("\"\r\n").append("Content-Type: ").append("multipart/form-data;boundary=" + boundary).append("\r\n\r\n");

            dout.write(resSB.toString().getBytes(Constants.ENCODE_UTF8));

            //开始写文件 
            File file = new File(filePath);
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024 * 5];
            while ((bytes = in.read(bufferOut)) != -1) {
                dout.write(bufferOut, 0, bytes);
            }

            dout.write(endBoundary.getBytes(Constants.ENCODE_UTF8));

            in.close();

            //3.最后写结尾 
            dout.write(endBoundary.getBytes(Constants.ENCODE_UTF8));
            dout.close();

            BufferedReader inr = new BufferedReader(new InputStreamReader(hc.getInputStream()));
            String result1 = "";
            String line;
            while ((line = inr.readLine()) != null) {
                result1 += line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (is != null)
                    is.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 上传图片操作
     *
     * @param uploadUrl
     * @param in
     * @param savePath
     */
    public static void uploadFile(String uploadUrl, InputStream in, String savePath) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", Constants.ENCODE_UTF8);
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            savePath = savePath.substring(savePath.lastIndexOf("\\") + 1);

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + savePath + "\"" + end);
            dos.writeBytes(end);

            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            // 读取文件
            while ((count = in.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
            }
            in.close();

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, Constants.ENCODE_UTF8);
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            dos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(String uploadUrl, String text, String savePath) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", Constants.ENCODE_UTF8);
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            //savePath = savePath.substring(savePath.lastIndexOf("\\") + 1);

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + savePath + "\"" + end);
            dos.writeBytes(end);

            dos.writeBytes(text);

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, Constants.ENCODE_UTF8);
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            dos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downLoadFile(InputStream in, OutputStream out) {
        try {
            //int bytesum = 0;
            int byteread = 0;

            byte[] buffer = new byte[4028];
            while ((byteread = in.read(buffer)) != -1) {
                //bytesum += byteread;
                out.write(buffer, 0, byteread);
            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                in = null;
            }
        }

    }

    /**
     * 图片压缩
     *
     * @param fileStream
     * @param suffix
     * @return
     */
    public static InputStream compressImg(FileItemStream fileStream, String suffix) {
        File file = new File("tmp.jpg");
        try {
            file.createNewFile();
            Image srcFile = ImageIO.read(fileStream.openStream());
            int w = srcFile.getWidth(null);
            int h = srcFile.getHeight(null);
            if (w > 1280 || h > 1280) { //等比压缩
                if (w > 1280 && h < 1280) {
                    h = (int) ((((double) 1280 / (double) w)) * h);
                    w = 1280;
                } else if (h > 1280 && w < 1280) {
                    w = (int) (((double) 1280 / (double) h) * w);
                    h = 1280;
                } else if (h > 1280 && w > 1280) {
                    if (h > w) {
                        w = (int) (((double) 1280 / (double) h) * w);
                        h = 1280;
                    } else {
                        h = (int) (((double) 1280 / (double) w) * h);
                        w = 1280;
                    }
                }
            }
            BufferedImage buffImg = null;
            if (suffix.toLowerCase().equals("png")) {
                buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            } else {
                buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            }
            buffImg.getGraphics().drawImage(srcFile.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
            FileOutputStream out = new FileOutputStream("tmp.jpg");
            // JPEGImageEncoder可适用于其他图片类型的转换   
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(buffImg);
            out.close();
//            ImageIO.write(buffImg, suffix, file);
            return new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            file.delete();
        }
        return null;
    }
}
