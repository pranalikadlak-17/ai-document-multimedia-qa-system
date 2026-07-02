package com.pranali.ai_doc_qa.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pranali.ai_doc_qa.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
