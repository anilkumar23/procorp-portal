package com.procorp.chat.util;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.procorp.chat.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Configuration
public class ImageUtil {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;
    private final static Logger LOG = LoggerFactory.getLogger(ImageUtil.class);
    public static byte[] compressImage(byte[] data) {

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
        return outputStream.toByteArray();
    }
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String uploadProfileImageToS3(String url, String userName) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formattedTimestamp = sdf3.format(timestamp);
        String fileName = userName + "_" + formattedTimestamp + ".png";
        String s3Url = "";
        URL imageURL= null;
        try {
            imageURL = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try(InputStream is = getImageInputStream(imageURL)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            metadata.setContentLength(getContentLength(imageURL));
            PutObjectResult s3response = s3Client.putObject(new PutObjectRequest(bucketName, fileName,is,metadata));
            if (s3response != null && s3response.getMetadata() !=null) {
                s3Url = s3Client.getUrl(bucketName, fileName).toString();
            }
        }catch (Exception ex){
            LOG.error("error occurred while uploading profile image of member {}", userName);
        }
        return s3Url;
    }

    public static Long getContentLength(URL urlStr) {
        Long contentLength = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) urlStr.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");
            conn.setRequestMethod("HEAD");
            contentLength = conn.getContentLengthLong();
            LOG.info("Content length {}", contentLength);
        } catch (Exception e) {
            LOG.info("Error getting content length: {}", e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return contentLength;
    }
    private static InputStream getImageInputStream(URL url) throws IOException {


        // This user agent is for if the server wants real humans to visit
        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

        // This socket type will allow to set user_agent
        URLConnection con = url.openConnection();

        // Setting the user agent
        con.setRequestProperty("User-Agent", USER_AGENT);

        //Getting content Length
        int contentLength = con.getContentLength();
        System.out.println("\n\n\n\n\n *****************File contentLength = " + contentLength + " bytes ****************\n\n\n\n");


        // Requesting input data from server
        return con.getInputStream();

    }
    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }
}
