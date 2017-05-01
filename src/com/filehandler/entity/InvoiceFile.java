package com.filehandler.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INVOICE_FILE", schema = "PAYMENT_SYSTEM")
public class InvoiceFile {

    @Id
    @SequenceGenerator(schema = "PAYMENT_SYSTEM", name = "seqGenerator",
            sequenceName  = "INVOICE_FILE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "seqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "INVOICE_FILE_ID")
    private Integer invoiceFileId;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "Client_CODE")
    private String clientCode;

    @Column(name = "INVOICE_YEAR")
    private Integer invoiceYear;

    @Column(name = "INVOICE_MONTH")
    private Integer invoiceMonth;

    @Column(name = "RELATIVE_PATH")
    private String relativePath;

    @Column(name = "STATUS_ID")
    private Integer statusId;

    @Column(name = "CREATION_DATE", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "UPDATE_DATE", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;


    public Integer getInvoiceFileId() {
        return invoiceFileId;
    }

    /**
     *
     * @param cdrFileId
     * cdrFileId
     */
    public void setCdrFileId(Integer InvoiceFileId) {
        this.invoiceFileId = InvoiceFileId;
    }

    /**
     *
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     *
     * @param fileName
     * fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @return clientCode
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     *
     * @param clientCode
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    /**
     *
     * @return year
     */
    public Integer getInvoiceYear() {
        return invoiceYear;
    }

    /**
     *
     * @param invoiceYear
     * invoiceYear
     */
    public void setInvoiceYear(Integer invoiceYear) {
        this.invoiceYear = invoiceYear;
    }

    /**
     *
     * @return month
     */
    public Integer getInvoiceMonth() {
        return invoiceMonth;
    }

    /**
     *
     * @param invoiceMonth
     * invoiceMonth
     */
    public void setInvoiceMonth(Integer invoiceMonth) {
        this.invoiceMonth = invoiceMonth;
    }

    /**
     *
     * @return relativePath
     */
    public String getRelativePath() {
        return relativePath;
    }

    /**
     *
     * @param relativePath
     * relativePath
     */
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

     /**
     *
     * @return statusId
     */
    public Integer getStatusId() {
        return statusId;
    }

    /**
     *
     * @param statusId
     */
    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
    /**
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     *
     * @param creationDate
     * creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    /**
     *
     * @return updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     *
     * @param updateDate
     * updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * returns true if the current InvoiceFile object equals to the other InvoiceFile
     * object
     *
     * @param o
     * object
     * @return true if the current object InvoiceFile equals to the other object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceFile invoiceFile = (InvoiceFile) o;

        if (invoiceFileId != null ? !invoiceFileId.equals(invoiceFile.invoiceFileId) : invoiceFile.invoiceFileId != null) return false;
        if (fileName != null ? !fileName.equals(invoiceFile.fileName) : invoiceFile.fileName != null) return false;
        if (clientCode != null ? !clientCode.equals(invoiceFile.clientCode) : invoiceFile.clientCode != null)
            return false;
        if (invoiceYear != null ? !invoiceYear.equals(invoiceFile.invoiceYear) : invoiceFile.invoiceYear != null) return false;
        if (invoiceMonth != null ? !invoiceMonth.equals(invoiceFile.invoiceMonth) : invoiceFile.invoiceMonth != null) return false;
        if (relativePath != null ? !relativePath.equals(invoiceFile.relativePath) : invoiceFile.relativePath != null)
            return false;
        if (statusId != null ? !statusId.equals(invoiceFile.statusId) : invoiceFile.statusId != null) return false;
        if (creationDate != null ? !creationDate.equals(invoiceFile.creationDate) : invoiceFile.creationDate != null)
            return false;
        return updateDate != null ? updateDate.equals(invoiceFile.updateDate) : invoiceFile.updateDate == null;

    }

    /**
     * Gets the hash code of the current CDRFile object
     *
     * @return hash code of the current CDRFile object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((invoiceFileId == null) ? 0 : invoiceFileId.hashCode());
        return result;
    }

    public String toString(){
        return "[FileName: "+fileName + " ProviderCode: "+clientCode + " InvoiceYear: "+ invoiceYear +
                " InoviceMonth: "+ invoiceMonth + " Status ID:"+ statusId+ " Relative Path: "+relativePath +
                "CreationDate"+ creationDate +"UpdateDate"+ updateDate+"]";
    }

}
