# InvoiceFileManager

Synopsis

Project walks through the given folder and picks all the valid CSV files inside the directory. It reads the attributes for the files mentioned in the file name and
and create the entity out of it that would be stored in the database. The project also creates the archive for all the read files and puts the file into duplicate folder
if the file already exist in the archives. In last, it stores the entity in database. (Database schema is not committed in github, hence the DAO class is commented out)

Tests

Please run FileHandlerApp.java to execute the project. Please adjust CSV_FOLDER_PATH to give a different directory of CSV files.

Code Owner
Asmat Soomro (soomro.asmat@gmail.com)