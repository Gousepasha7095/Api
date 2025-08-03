package Springboot.springboot.Controller;
import Springboot.springboot.Entity.UploadDocuments;
import Springboot.springboot.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("userName") String userName) {
        try {
            if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
                return ResponseEntity.badRequest().body("Only PDF files are allowed.");
            }
            UploadDocuments document = documentService.saveDocument(file, userName);
            return ResponseEntity.status(201).body("Document uploaded successfully with ID: " + document.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload document: " + e.getMessage());
        }
    }

    @GetMapping("/getDocumentById/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable Long id){
        UploadDocuments documents = documentService.getDocumentById(id);
        if (documents != null){
            return ResponseEntity.ok(documents);
        }else{
            return ResponseEntity.status(404).body("Document not found with ID: " + id);
        }
    }
    @GetMapping("/document/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        UploadDocuments document = documentService.getDocumentById(id);
        if (document != null) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + document.getFiletype() + "\"")
                    .header("Content-Type", "application/octet-stream")
                    .body(document.getFilePicture());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/getAllDocuments")
    public List<UploadDocuments> getAll(){
        return documentService.getAllDocuments();
    }

    @GetMapping("/download/{userName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String userName){
        byte[] fileData= documentService.getDocument(userName);
        if(fileData == null) {
            return ResponseEntity.notFound().build();
        }
            String fileName = userName + "_document.pdf";
            String contentType = "application/pdf";


            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(fileData);

    }


}
