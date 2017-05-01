package com.filehandler.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

public class DirectoryParser {
	
	 final static Logger logger = Logger.getLogger(DirectoryParser.class);
	 public static final String ARCHIVE_FOLDER_NAME = "archive";

	
	  public List<Path> getListOfFiles(String path) throws IOException {

	        if (path == null || path.equals("")) throw new IllegalArgumentException("No folder path provided as an input");

	        List<Path> filesToParse = new ArrayList<Path>();

	        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
	            paths.forEach(filePath ->
	            {
	                try {
	                    if (Files.probeContentType(filePath.getFileName()) != null) { 
	                        if (!filePath.toString().contains(ARCHIVE_FOLDER_NAME)) {
	                            if (isValidCsvFileName(filePath.getFileName().toString())) {
	                                logger.info("File Name is ::" + filePath.getFileName().toString());
	                                logger.info("Adding the file to list : " + filePath.toString());
	                                filesToParse.add(filePath);
	                            }
	                        }
	                    }

	                } catch (IOException e) {
	                    logger.fatal(e.getMessage());
	                    e.printStackTrace();
	                }
	            }
	            );
	        } catch (NoSuchFileException e) {
	            logger.fatal("No Such file Exception: " + e.getMessage());
	            throw new NoSuchFileException(e.getMessage());
	        } catch (IOException e) {
	            logger.fatal("IO Exception :" + e.getMessage());
	            throw new IOException(e.getCause());
	        }

	        return filesToParse;
	    }
	  

	    /**
	     *
	     * @param fileName
	     * @return true or false
	     * Checks whether the filename adheres to the standard i.e. Invoice_<CLIENT_CODE>_<TIMESTAMP>.csv
	     */
	    public boolean isValidCsvFileName(String fileName) {

	       InvoiceAttributesReader cdrAttributesReader = new InvoiceAttributesReader();

	        boolean isValidFileFormat = true;
	        if (!fileName.endsWith(".csv")) {
	            return false;
	        }

	        if (!fileName.startsWith("EMPInvoice_")) {
	            logger.warn("File Format is wrong."+ fileName +" does not start with EMPInvoice_");
	            return false;
	        }

	        String[] cdrFileNameTokens = cdrAttributesReader.getInvoiceFileNameTokens(fileName);
	        if (cdrFileNameTokens.length != 3) {
	            logger.warn("File Format is wrong."+ fileName +" does not not contain 3 tokens seperated by _ ");
	            return false;
	        }
	        String providerCode = cdrAttributesReader.getClientCode(fileName);

	        if ((providerCode.equals(InvoiceAttributesReader.EnergyProvider.valueOf("EBW").toString()))
	                || (providerCode.equals(InvoiceAttributesReader.EnergyProvider.valueOf("RWE").toString()))
	                || (providerCode.equals(InvoiceAttributesReader.EnergyProvider.valueOf("LDN").toString()))) {
	            isValidFileFormat = true;
	        } else {
	            logger.warn("File Format is wrong. File "+fileName +" does not have a valid energy provider code");
	            return false;
	        }

	        Long cdrTimeStamp = cdrAttributesReader.getInvoiceTimeStampInMilliSeconds(fileName);
	        try {
	            Date date = new Date(cdrTimeStamp);
	        } catch (NumberFormatException ne) {
	            logger.fatal("Number Format Exception Occurred: "+ ne.getMessage());
	            logger.fatal("File "+fileName +" Does not have a valid timestamp");
	            ne.printStackTrace();
	            return false;
	        }

	        return isValidFileFormat;

	    }
 



}
