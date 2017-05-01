package com.filehandler.dao;

import java.util.List;

import com.filehandler.entity.InvoiceFile;

public interface IFileHandlerDao {
	
	public void insertIntoDatabase(List<InvoiceFile> cdrFileList);

}
