package Springboot.springboot.Repository;

import Springboot.springboot.Entity.UploadDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<UploadDocuments,Long> {
    Optional<UploadDocuments> findByUserName(String userName);

}
