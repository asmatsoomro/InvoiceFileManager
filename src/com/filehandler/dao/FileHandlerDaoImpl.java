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
 * DAO for CDRFILE. It stores or deletes data from CDR_FILE table in database
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
     * Inserts the CDRFile into database table i.e. CDR_FILE
     * @param cdrFileList
     */
	public void insertIntoDatabase(List<InvoiceFile> cdrFileList){
        try {
            em.getTransaction().begin();

            for (int i = 0; i < cdrFileList.size(); i++) {
                em.persist(cdrFileList.get(i));
                logger.info("Persisted CDR in entityManager : " + cdrFileList.get(i).getFileName());
            }
            em.getTransaction().commit();

        } catch (DatabaseException | PersistenceException ex){
            logger.fatal("Could not connect to the database:: "+ ex);
            em.getTransaction().rollback();
            ex.printStackTrace();

           } finally {
             em.close();
             emf.close();
        }

		logger.info("Connection to the database closed.");

	}

}
