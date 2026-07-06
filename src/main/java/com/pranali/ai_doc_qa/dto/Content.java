package com.pranali.ai_doc_qa.dto;

import java.util.List;

public class Content {

	private String role;
    private List<Part> parts;

    public Content() {
    }

    public Content(List<Part> parts) {
        this.parts = parts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
