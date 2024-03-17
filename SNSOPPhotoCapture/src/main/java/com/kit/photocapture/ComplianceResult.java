package com.kit.photocapture;

public enum ComplianceResult {
    COMPLIED("Complied"),
    NO_FACE("No face present. Please recapture."),
    MULTIPLE_FACE("Multiple faces present. Please recapture."),
    UNKNOWN_ERROR("Unknown error occured"),
    NOT_INITIALIZED("Preview not initialized");

    private String complianceTxt;

    private ComplianceResult(String complianceTxt) {
        this.complianceTxt = complianceTxt;
    }

    public String getComplianceTxt() {
        return complianceTxt;
    }

    public void setComplianceTxt(String complianceTxt) {
        this.complianceTxt = complianceTxt;
    }
}
