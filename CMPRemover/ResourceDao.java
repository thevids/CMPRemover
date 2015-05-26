package no.moller.evp.model.ejb;

import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Public interface for JdbcDao.
 */
public interface ResourceDao
{
   ResourceDom create(java.lang.String argFnr, int argResourceID) throws SQLException;

   ResourceDom create(java.lang.String argFnr, java.lang.String argName, int argType, java.lang.String argFrom, java.lang.String argTo, int argRef, java.lang.String argCreatedBy, java.lang.String argDateCreated, java.lang.String argComment) throws SQLException;

   ResourceDom create(java.lang.String argFnr, java.lang.String argName, int argType, java.lang.String argFrom, java.lang.String argTo, int argRef, java.lang.String argCreatedBy, java.lang.String argDateCreated, java.lang.String argComment, java.lang.String argSkillTypes, java.lang.String argBrandTypes, boolean argPlanConnected, int argPlanID) throws SQLException;

   ResourceDom create(java.lang.String argFnr, java.lang.String argName, int argType, java.lang.String argFrom, java.lang.String argTo, int argRef, java.lang.String argCreatedBy, java.lang.String argDateCreated, java.lang.String argComment, java.lang.String argSkillTypes, java.lang.String argBrandTypes, boolean argPlanConnected, int argPlanID, java.lang.String argMnetMekID) throws SQLException;

   ResourceDom findByPrimaryKey(no.moller.evp.model.ejb.ResourceKey key);

   ResourceDom findNewestResource();

   ResourceDom findResource(String argFnr, int argResourceID);

   ResourceDom findResourceByMekID(java.lang.String argFnr, java.lang.String argMekID);

   Enumeration<ResourceDom> findResources(java.lang.String argFnr);

   Enumeration<ResourceDom> findResourcesByShiftPlan(String argFnr, int argPlanID);

   Enumeration<ResourceDom> findResourcesBySkills(String argFnr, String argSkills);

   Enumeration<ResourceDom> findResourcesInGroup(String argFnr, int argResourceGroupRef);

   public ResourceDom create(
         String argFnr,
         String argName,
         int argType,
         String argFrom,
         String argTo,
         int argRef,
         String argCreatedBy,
         String argDateCreated,
         String argComment,
         String argSkillTypes,
         String argBrandTypes,
         boolean argPlanConnected,
         int argPlanID,
         String argMnetMekID,
         int sortering)
         throws SQLException;

   public Enumeration<ResourceDom> findAllResources();

   int remove(ResourceKey pk);

   /**
    * Insert all fields from domain object.
    */
   public int create(ResourceDom resource) throws SQLException;
}
