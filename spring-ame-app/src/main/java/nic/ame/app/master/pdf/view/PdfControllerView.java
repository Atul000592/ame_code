package nic.ame.app.master.pdf.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.esign.StringConstants;
import nic.ame.app.master.medical.model.AmeDeclarationFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class PdfControllerView {

    @Autowired
    private AmeDeclarationFilesRepo ameDeclarationFilesRepo;

    @CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL,StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
    @GetMapping(value ="/declaration-view",produces = MediaType.APPLICATION_PDF_VALUE)
    public void getPdf(@RequestParam("ameId") String ameId, HttpServletResponse response) throws IOException {
        // Fetch the file path from the repository
        List<AmeDeclarationFiles> ameDeclarationFiles = ameDeclarationFilesRepo.findByAmeIdFileExist(ameId);

        if (ameDeclarationFiles.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        File file = new File(ameDeclarationFiles.get(0).getFilePath());

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        // Set response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

        // Stream the file content
        try (FileInputStream fis = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        } catch (IOException e) {
            // Handle broken pipe exceptions gracefully
            if (e.getMessage().contains("Broken pipe")) {
                System.out.println("Client disconnected while streaming the file.");
            } else {
                throw e;
            }
        }
    }
}
