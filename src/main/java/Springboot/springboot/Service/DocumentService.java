package Springboot.springboot.Service;

import Springboot.springboot.Entity.UploadDocuments;
import Springboot.springboot.Repository.DocumentRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public UploadDocuments saveDocument(MultipartFile file, String userName) throws IOException, java.io.IOException {
        UploadDocuments document = new UploadDocuments();
        document.setUserName(userName);
        document.setFiletype(file.getContentType());
        document.setFilePicture(file.getBytes());
        document.setUploadedAt(new Timestamp(System.currentTimeMillis()));


        return documentRepository.save(document);
    }

    public UploadDocuments getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public List<UploadDocuments> getAllDocuments() {
        return documentRepository.findAll();
    }
     public byte[] getDocument(String userName){
        return documentRepository.findByUserName(userName)
                .map(UploadDocuments::getFilePicture)
                .orElse(null);
     }


}
