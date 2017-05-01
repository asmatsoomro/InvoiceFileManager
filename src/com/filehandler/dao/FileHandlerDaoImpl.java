package com.filehandler.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.filehandler.entity.InvoiceFile;


/**
 * DAO for InvoiceFile. 
 */
public class FileHandlerDaoImpl implements IFileHandlerDao {

	final static Logger logger = Logger.getLogger(FileHandlerDaoImpl.class);

	private final String PERSISTENCE_UNIT_NAME = "ChargingDetailedRecord-FileHandler";

	private static EntityManagerFactory emf =  null;
	private static EntityManager em = null;


	public FileHandlerDaoImpl() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();

	}

	/**
	 * Inserts the InvoiceFile into database table
	 * @param invoiceFileList
	 */
	public void insertIntoDatabase(List<InvoiceFile> invoiceFileList){
		try {
			em.getTransaction().begin();

			for (int i = 0; i < invoiceFileList.size(); i++) {
				em.persist(invoiceFileList.get(i));
				logger.info("Persisted Invoice attributes in entityManager : " + invoiceFileList.get(i).getFileName());
			}
			em.getTransaction().commit();

		} catch (DatabaseException | PersistenceException ex){
			logger.fatal("Could not connect to the database:: "+ ex);
			em.getTransaction().rollback();
			ex.printStackTrace();

		} finally {
			em.close();
			emf.close();
			logger.info("Connection to the database closed.");
		}


	}

}
