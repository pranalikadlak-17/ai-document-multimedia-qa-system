package com.pranali.ai_doc_qa.dto;

public class UploadResponse {   
	
	private Long id;
    private String fileName;
    private String fileType;
    private String message;

    public UploadResponse() {
    }

    public UploadResponse(Long id, String fileName, String fileType, String message) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
