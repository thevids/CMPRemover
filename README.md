# CMPRemover

by Vidar Berentsen Folden

Requires java 8 with Lambdas.

Reads old EJB1.1 entity beans and generates a good starting point for Spring JDBC DAO.

Made to ease the transformation from old CMP beans to direct JDBC-DAOs in a project I had to migrate out of EJBs. It probably needs to be customized for certain local differences in package structure etc. Most customization will be made in the main runner-class that passes parameters on to the working classes (sic), the actual work is done with passed parameters and could easily be made into eg. a REST-service.
