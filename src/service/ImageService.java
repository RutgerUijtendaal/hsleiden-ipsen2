package service;

import javafx.scene.image.Image;
import models.Answer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Class to manage posting and getting images from a web server
 *
 * @author Rutger Uijtendaal
 */
public class ImageService {

    // Server location
    private final String url = "http://80.100.250.208:25003";
    // Path to store locally
    private final String writePath = System.getProperty("java.io.tmpdir") + "/";

    /**
     * Save an AnswerImage to the web server. A filename is created based on the
     * dilemma WeekNr and awnserNr (A or B).
     *
     * @param image Image File to upload
     * @param answerId DB Id of the answer
     * @return String: The name of the image stored
     */
    public void saveAnswerImage(File image, int answerId) throws IOException {
        // First build the image name. Name format: $answerId.$extension
        String extension = FilenameUtils.getExtension(image.toString());
        String imageName = Integer.toString(answerId) + "." + extension;
        // Then post the image to the web server
        postImageToWeb(image, imageName);
    }

    /**
     * Get an image from the web server, save it in java temp environment and return the local file as an image.
     *
     * @param answer Answer object of the image to get.
     * @return Image: The loaded image.
     */
    public Image getAnswerImage(Answer answer) throws IOException {
        File file = getFileFromWeb(Integer.toString(answer.getId()) + "." + answer.getUrl());
        // Run toURI first to escape illegal characters
        return new Image(file.toURI().toURL().toString());
    }

    public File getAnswerImageFile(Answer answer) throws IOException {
        return getFileFromWeb(Integer.toString(answer.getId()) + "." + answer.getUrl());
    }

    private void postImageToWeb(File image, String imageName) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Image post script
        String uploadScript = "/upload.php";
        HttpPost post = new HttpPost(url + uploadScript);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("uploaded_file", image, ContentType.create("image"), imageName);

        HttpEntity entity = builder.build();
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent(), StandardCharsets.UTF_8));
        String sResponse;
        StringBuilder s = new StringBuilder();

        while ((sResponse = reader.readLine()) != null) {
            s.append(sResponse);
        }

        response.close();
        client.close();
    }

    /**
     * Gets an image from the webserver
     * @param imageName the of the image
     * @return the image
     * @throws IOException thrown when something went wrong
     */
    private File getFileFromWeb(String imageName) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        // Image directory on server
        String imageDir = "/images/";
        HttpGet get = new HttpGet(url + imageDir + imageName);
        File file = new File(writePath + imageName);

        CloseableHttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        if(entity != null) {
            byte[] content = EntityUtils.toByteArray(response.getEntity());
            FileUtils.writeByteArrayToFile(file, content);
        }
        response.close();
        client.close();

        return file;

    }

}
