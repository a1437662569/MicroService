package com.xxl.entity.vo;

public class EmailTempNoticeVo {

    /**
     * 用户名
     */
    private String source;

    private String templateType;

    private Object objJson;

    private String customerEmail;

    private String subject;

    private String ccStr;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public Object getObjJson() {
        return objJson;
    }

    public void setObjJson(Object objJson) {
        this.objJson = objJson;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCcStr() {
        return ccStr;
    }

    public void setCcStr(String ccStr) {
        this.ccStr = ccStr;
    }

    @Override
    public String toString() {
        return "EmailTempNoticeReq [source=" + source + ", templateType=" + templateType
                + ", objJson=" + objJson + ", customerEmail=" + customerEmail + ", subject="
                + subject + ", ccStr=" + ccStr + "]";
    }

}
