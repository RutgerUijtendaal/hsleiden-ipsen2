package util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

/**
 * Class to manage posting and getting images from a web server
 */
public class ImageService {

    // Server location
    private String url = "http://192.168.11.33/";
    // Image post script
    private String uploadScript = "/upload.php";
    // Image directory on server
    private String imageDir = "/images/";
    // Path to store locally
    private String writePath = "C:\\subfolder\\";

    /**
     * Save an AnswerImage to the web server. A filename is created based on the
     * dilemma WeekNr and awnserNr (A or B).
     *
     * @param image Image File to upload
     * @param weekNr WeekNr of the Answer
     * @param answerNr AnswerNr always A or B
     * @return String: The set name of the image stored
     */
    public String saveAnswerImage(File image, String weekNr, String answerNr) {
        String extension = FilenameUtils.getExtension(image.toString());
        String imageName = "dilemma" + weekNr + "_antwoord" + answerNr + "." + extension;
        postImageToWeb(image, imageName);
        return imageName;
    }

    /**
     * Get an image from the webserver, save it locally and return the local file.
     *
     * @param imageName Name of the image to get.
     * @return File: The loaded image.
     */
    public File getAnswerImage(String imageName) {
        getImageFromWeb(imageName);
        return new File(writePath + imageName);
    }

    private void getImageFromWeb(String imageName) {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet(url + imageDir + imageName);

        try {
            CloseableHttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                byte[] content = EntityUtils.toByteArray(response.getEntity());
                FileUtils.writeByteArrayToFile(new File(writePath + imageName), content);
            }
            response.close();
            client.close();
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void postImageToWeb(File image, String imageName) {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost post = new HttpPost(url + uploadScript);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("uploaded_file", image, ContentType.create("image"), imageName);

        HttpEntity entity = builder.build();
        post.setEntity(entity);

        try {
            CloseableHttpResponse response = client.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);

            response.close();
            client.close();
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
