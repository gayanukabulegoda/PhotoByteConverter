package lk.ijse.photobyteconverter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/photo")
@MultipartConfig
public class PhotoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        Part filePart = request.getPart("file");

        if (filePart != null) {
            try (InputStream inputStream = filePart.getInputStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

                // Serialization: Convert the uploaded file to a byte array
                IOUtils.copy(inputStream, buffer);
                byte[] photoBytes = buffer.toByteArray();

                // Set the response content type and length
                response.setContentType("application/octet-stream");
                response.setContentLength(photoBytes.length);

                // Deserialization: Send the byte array back to the client
                response.getOutputStream().write(photoBytes);

            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing file");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file uploaded");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle CORS preflight request
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
