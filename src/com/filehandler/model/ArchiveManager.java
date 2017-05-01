package com.filehandler.model;

import org.apache.log4j.Logger;

import com.filehandler.entity.InvoiceFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ASoomro on 15.11.2016.
 */
public class ArchiveManager {

    private final static Logger logger = Logger.getLogger(ArchiveManager.class);

    private static final String ARCHIVE_PATH = "/CDR/archive";
    private static final String ARCHIVE_PATH_FOR_DUPLICATES = "/CDR/archive/duplicates";
    private StringBuilder relativePath;
    private static final String FOLDER_SEPERATOR = "/";
    
    
    private static final Integer INVOICE_MONTH_DECEMBER = 12;


    /**
     * Moves the Invoice files to archives. If the files are already present in archives, it places them in
     * duplicates folder in archive
     * @param invoiceFileList Files for moving operations, will be cleaned from duplicates
     * @return List of modified InvoiceFiles without duplicate files that already existed in the file system
     */
    public List<InvoiceFile> moveFileToArchiveAndRemoveDuplicatesFromList(List<InvoiceFile> invoiceFileList, String rootPath) {
        invoiceFileList.forEach(cdrFile -> {
            {
                Path sourcePath = Paths.get(cdrFile.getRelativePath());
                Path destinationPath = getDestinationPath(cdrFile, false, rootPath);
                try {

                    if (!Files.exists(destinationPath)) {
                        Files.createDirectories(destinationPath);
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        cdrFile.setRelativePath(getRelativePath());
                        logger.info(sourcePath + " moved to "+destinationPath);
                       
                    }
                    else
                    {
                        logger.info("Duplicate file found : "+cdrFile.getFileName());
                        destinationPath = getDestinationPath(cdrFile, true, rootPath);
                        Files.createDirectories(destinationPath);
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        logger.info(sourcePath + " moved to "+destinationPath);
                        //removes duplicate files from the list that will be used for DB inserts
                        invoiceFileList.remove(cdrFile);

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

            return invoiceFileList;
    }


    /**
     * The method get CDRFile as an input and returns the folderdate for the archive
     * @param cdrFile CDRFILE will be used to get the Invoice month and Invoice year to form a folder date
     * @return Folder date e.g 2015-11
     */
    private String getFolderDate(InvoiceFile cdrFile) {
        String folderDate;
        if (cdrFile.getInvoiceMonth() == 0) {
            folderDate = (cdrFile.getInvoiceYear() - 1) + "-" + INVOICE_MONTH_DECEMBER;
        } else {
            folderDate = cdrFile.getInvoiceYear() + "-" + String.format("%02d", cdrFile.getInvoiceMonth()) ;
        }
        return folderDate;
    }


    /**
     * @param cdrFile
     * @param isDuplicate
     * @return The whole destination path where the archives will be moved to
     */
    private Path getDestinationPath(InvoiceFile cdrFile, boolean isDuplicate, String rootPath){

        StringBuilder path = new StringBuilder(rootPath);


        if (!isDuplicate){
            path.append(ARCHIVE_PATH);
            path.append(FOLDER_SEPERATOR);
        }
        else
        {
            path.append(ARCHIVE_PATH_FOR_DUPLICATES);
            path.append(FOLDER_SEPERATOR);
        }
        String folderDate = getFolderDate(cdrFile);
        path.append(folderDate);

        path.append(FOLDER_SEPERATOR);
        path.append(cdrFile.getClientCode());
        path.append(FOLDER_SEPERATOR);

        if (!isDuplicate) {
            path.append(cdrFile.getFileName());
        } else {
            //path.append(getDuplicateFileName(cdrFile.getFileName()));
            path.append(cdrFile.getFileName());
        }


        Path destinationPath = java.nio.file.FileSystems.getDefault()
                .getPath(path.toString());

        createRelativePath(folderDate, cdrFile.getClientCode());

        return destinationPath;
    }

    /**
     *
     * @param fileName
     * @return String
     * Renames the files to duplicate FileName Convention i.e. EMPInvoice_<PROVIDERCODE>_<TIMESTAMP>_<DATE>_<TIME>.csv
     */
    String getDuplicateFileName(String fileName){

        InvoiceAttributesReader cdrAttributesReader = new InvoiceAttributesReader();
        Long fileTimeStamp = cdrAttributesReader.getInvoiceTimeStampInMilliSeconds(fileName);

        Date date = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(date);

        String[] fileNameWithOutExt = fileName.split("\\.");

        StringBuilder duplicateFileName = new StringBuilder(fileNameWithOutExt[0]);
        duplicateFileName.append("__");
        duplicateFileName.append(c.get(Calendar.YEAR));
        duplicateFileName.append(c.get(Calendar.MONTH));
        duplicateFileName.append(c.get(Calendar.DAY_OF_MONTH));
        duplicateFileName.append("_");
        duplicateFileName.append( c.get(Calendar.HOUR_OF_DAY ));
        duplicateFileName.append( c.get(Calendar.MINUTE ));
        duplicateFileName.append( c.get(Calendar.SECOND ));
        duplicateFileName.append(".");
        duplicateFileName.append( fileNameWithOutExt[1]);

        return duplicateFileName.toString();
    }


    /**
     *
     * @param folderDate
     * @param providerCode
     * Creates the relative path for a file in archives
     */
    private void createRelativePath(String folderDate, String providerCode){

        relativePath = new StringBuilder(FOLDER_SEPERATOR);
        relativePath.append(folderDate);
        relativePath.append(FOLDER_SEPERATOR);
        relativePath.append(providerCode);
        relativePath.append(FOLDER_SEPERATOR);

    }


    /**
     * @return String : A relative path for the file in archive
     */
    private String getRelativePath(){
        return relativePath.toString();
    }

}



