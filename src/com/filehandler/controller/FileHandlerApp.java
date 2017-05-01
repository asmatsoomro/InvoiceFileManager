package com.filehandler.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.filehandler.entity.InvoiceFile;
import com.filehandler.model.ArchiveManager;
import com.filehandler.model.DirectoryParser;
import com.filehandler.model.InvoiceAttributesReader;

public class FileHandlerApp {
	
	final static Logger logger = Logger.getLogger(FileHandlerApp.class);
	
	final static String CSV_FOLDER_PATH = "InputCsvFolder/CSV";
	 
	
	public static void main(String args[]){
		
		String path = "InputCsvFolder//DuplicateFilesTestData//CSV";
		
		  DirectoryParser dirParser  = new DirectoryParser();
	        List<Path> files = null;
			try {
				files = dirParser.getListOfFiles(CSV_FOLDER_PATH);
			logger.info("Number of files to be parsed : " + files.size());
			System.out.println("Number of files to be parsed : " + files.size());

			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        List<InvoiceFile> invoiceFileList = new CopyOnWriteArrayList<InvoiceFile>();

	        InvoiceAttributesReader cdrAttributesReader = new InvoiceAttributesReader();

	        files.forEach(file->{
	            InvoiceFile cdrFileEntity = cdrAttributesReader.createCdrEntity(file);
	            invoiceFileList.add(cdrFileEntity);
	        });

	      invoiceFileList.forEach(cdrfile ->
	      	System.out.println(cdrfile.toString())
	      );
		
	      ArchiveManager archiveManager = new ArchiveManager();
	      List<InvoiceFile> cdrFileListUpdated = archiveManager.moveFileToArchiveAndRemoveDuplicatesFromList(invoiceFileList, path);

//	      CDRDao cdrDao = new CDRDao();
//	      cdrDao.insertIntoDatabase(cdrFileListUpdated);

	}

}
