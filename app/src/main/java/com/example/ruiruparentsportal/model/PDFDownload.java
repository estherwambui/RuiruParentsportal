package com.example.ruiruparentsportal.model;

import java.io.Serializable;

public class PDFDownload implements Serializable {
    private String filename;

    public PDFDownload(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
