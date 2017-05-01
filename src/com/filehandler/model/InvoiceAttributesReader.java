package com.filehandler.model;

import org.apache.log4j.Logger;

import com.filehandler.entity.InvoiceFile;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ASoomro
 * InvoiceAttibuteReader creates the InvoiceFILE Entity from a filepath
 *
 */
public class InvoiceAttributesReader {

    final static Logger logger = Logger.getLogger(InvoiceAttributesReader.class);

    private final Integer STATUS_ID_NOT_PARSED = 25;

    private final Integer INVOICE_MONTH_DECEMBER = 12;

    public enum EnergyProvider {
        EBW, LDN, RWE
    }

    /**
     * It takes file as an input and parses the file name to return the entity of CDRFile
     * @param file
     * @return InvoiceFile
     */
    public InvoiceFile createCdrEntity(Path file) {

        String fileName = file.getFileName().toString();

        String providerCode = getClientCode(fileName);

        Long fileTimeStamp = getInvoiceTimeStampInMilliSeconds(fileName);


        Date date = new Date(fileTimeStamp);

        final Calendar c = Calendar.getInstance();
        c.setTime(date);

        InvoiceFile invoiceFile = new InvoiceFile();
        invoiceFile.setFileName(fileName);
        invoiceFile.setStatusId(STATUS_ID_NOT_PARSED);
        invoiceFile.setClientCode(providerCode);
        //Invoice month must be set to preceding month
        if (c.get(Calendar.MONTH) == 0){
            invoiceFile.setInvoiceYear(c.get(Calendar.YEAR) - 1);
            invoiceFile.setInvoiceMonth(INVOICE_MONTH_DECEMBER);
        }
        else {
            invoiceFile.setInvoiceYear(c.get(Calendar.YEAR));
            invoiceFile.setInvoiceMonth(c.get(Calendar.MONTH) +1 -1  );
        }

        invoiceFile.setRelativePath(file.toString());

        return invoiceFile;
    }

    /**
     *
     * @param fileName
     * @return Provider Code
     */
    public String getClientCode(String fileName){
        String[] fileNameTokens = getInvoiceFileNameTokens(fileName);
        String clientCode = fileNameTokens[1];
        return clientCode;
    }


    /**
     *
     * @param fileName
     * @return invoiceTimeStamp
     */
     public Long getInvoiceTimeStampInMilliSeconds(String fileName){
        String[] fileNameTokens = getInvoiceFileNameTokens(fileName);
        String[] cdrTimeStamp = fileNameTokens[2].split("\\.");

         Long timeStampinMillsec = Long.parseLong(cdrTimeStamp[0]) * 1000;
         return timeStampinMillsec;
    }


    /**
     *
     * @param fileName
     * @return String array of tokens Seperated by _
     */
    public String[] getInvoiceFileNameTokens(String fileName){
        String[] fileNameTokens = fileName.split("_");
        return  fileNameTokens;
    }


    /**
     * gets the present date
     * @return Date
     */
    public Date getPresentDate(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();

    }



}
