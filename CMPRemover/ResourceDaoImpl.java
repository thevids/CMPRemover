package no.moller.evp.model.ejb;

import no.moller.evp.model.ejb.ResourceDao;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of JdbcDao
 */
public class ResourceDaoImpl implements ResourceDao
{

   @Autowired
   private NamedParameterJdbcTemplate mwinNamedTemplate;
   private SimpleJdbcInsert simpleInsert;
   private RowMapper<ResourceDom> mapper = new ResourceDomRowMapper();
   /**
    * Select-statement with ALL fields.
    */
   public final static String SELECT = "select dateCreated, resourceType, groupPlan, createdBy, skillTypes, timeStampCreated, availableFrom, comment, availableTo, planConnected, dateChanged, resourceID, mnetMekID, planID, changedBy, resourceName, resourceGroupRef, brandTypes, fnr, sortering, colortype, workEffectivity, resourceMustStample, shiftplanChanged, salaryType, employeeNr, bookable, toolCompensation, toolCompensationRate from MWIN.Resource T1 ";

   public ResourceDom create(java.lang.String argFnr, int argResourceID)
         throws SQLException
   {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("FNR", argFnr);
      parameters.put("RESOURCEID", argResourceID);
      int nr = simpleInsert.execute(parameters);
      if (nr == 0)
      {
         throw new SQLException("Failure to insert " + argFnr
               + argResourceID);
      }
      return null;
   }

   public ResourceDom create(java.lang.String argFnr,
         java.lang.String argName, int argType, java.lang.String argFrom,
         java.lang.String argTo, int argRef, java.lang.String argCreatedBy,
         java.lang.String argDateCreated, java.lang.String argComment)
         throws SQLException
   {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("FNR", argFnr);
      parameters.put("NAME", argName);
      parameters.put("TYPE", argType);
      parameters.put("FROM", argFrom);
      parameters.put("TO", argTo);
      parameters.put("REF", argRef);
      parameters.put("CREATEDBY", argCreatedBy);
      parameters.put("DATECREATED", argDateCreated);
      parameters.put("COMMENT", argComment);
      int nr = simpleInsert.execute(parameters);
      if (nr == 0)
      {
         throw new SQLException("Failure to insert " + argFnr + argName
               + argType + argFrom + argTo + argRef + argCreatedBy
               + argDateCreated + argComment);
      }
      return null;
   }

   public ResourceDom create(java.lang.String argFnr,
         java.lang.String argName, int argType, java.lang.String argFrom,
         java.lang.String argTo, int argRef, java.lang.String argCreatedBy,
         java.lang.String argDateCreated, java.lang.String argComment,
         java.lang.String argSkillTypes, java.lang.String argBrandTypes,
         boolean argPlanConnected, int argPlanID) throws SQLException
   {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("FNR", argFnr);
      parameters.put("NAME", argName);
      parameters.put("TYPE", argType);
      parameters.put("FROM", argFrom);
      parameters.put("TO", argTo);
      parameters.put("REF", argRef);
      parameters.put("CREATEDBY", argCreatedBy);
      parameters.put("DATECREATED", argDateCreated);
      parameters.put("COMMENT", argComment);
      parameters.put("SKILLTYPES", argSkillTypes);
      parameters.put("BRANDTYPES", argBrandTypes);
      parameters.put("PLANCONNECTED", argPlanConnected);
      parameters.put("PLANID", argPlanID);
      int nr = simpleInsert.execute(parameters);
      if (nr == 0)
      {
         throw new SQLException("Failure to insert " + argFnr + argName
               + argType + argFrom + argTo + argRef + argCreatedBy
               + argDateCreated + argComment + argSkillTypes
               + argBrandTypes + argPlanConnected + argPlanID);
      }
      return null;
   }

   public ResourceDom create(java.lang.String argFnr,
         java.lang.String argName, int argType, java.lang.String argFrom,
         java.lang.String argTo, int argRef, java.lang.String argCreatedBy,
         java.lang.String argDateCreated, java.lang.String argComment,
         java.lang.String argSkillTypes, java.lang.String argBrandTypes,
         boolean argPlanConnected, int argPlanID,
         java.lang.String argMnetMekID) throws SQLException
   {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("FNR", argFnr);
      parameters.put("NAME", argName);
      parameters.put("TYPE", argType);
      parameters.put("FROM", argFrom);
      parameters.put("TO", argTo);
      parameters.put("REF", argRef);
      parameters.put("CREATEDBY", argCreatedBy);
      parameters.put("DATECREATED", argDateCreated);
      parameters.put("COMMENT", argComment);
      parameters.put("SKILLTYPES", argSkillTypes);
      parameters.put("BRANDTYPES", argBrandTypes);
      parameters.put("PLANCONNECTED", argPlanConnected);
      parameters.put("PLANID", argPlanID);
      parameters.put("MNETMEKID", argMnetMekID);
      int nr = simpleInsert.execute(parameters);
      if (nr == 0)
      {
         throw new SQLException("Failure to insert " + argFnr + argName
               + argType + argFrom + argTo + argRef + argCreatedBy
               + argDateCreated + argComment + argSkillTypes
               + argBrandTypes + argPlanConnected + argPlanID
               + argMnetMekID);
      }
      return null;
   }

   public ResourceDom findByPrimaryKey(no.moller.evp.model.ejb.ResourceKey key)
   {
      String whereSQL = "  T1 WHERE  T1.FNR = :fnr  AND  T1.RESOURCEID = :resourceid ";
      final MapSqlParameterSource parameters = new MapSqlParameterSource();
      parameters.addValue("fnr", key.fnr);
      parameters.addValue("resourceid", key.resourceID);
      return SafeReturn.ret(
            mwinNamedTemplate.query(SELECT + whereSQL, parameters, mapper),
            ResourceDom.class);
   }

   public ResourceDom findNewestResource()
   {
      String whereSQL = " WHERE T1.RESOURCEID = (SELECT MAX(RESOURCEID) FROM MWIN.RESOURCE)";
      final MapSqlParameterSource parameters = new MapSqlParameterSource();
      return SafeReturn.ret(
            mwinNamedTemplate.query(SELECT + whereSQL, parameters, mapper),
            ResourceDom.class);
   }

   public ResourceDom findResource(String argFnr, int argResourceID)
   {
      throw new java.lang.UnsupportedOperationException("Not yet implemented");
   }

   public ResourceDom findResourceByMekID(java.lang.String argFnr,
         java.lang.String argMekID)
   {
      String whereSQL = " WHERE T1.FNR = :argfnr AND T1.MNETMEKID = :argmekid WITH UR";
      final MapSqlParameterSource parameters = new MapSqlParameterSource();
      parameters.addValue("argfnr", argFnr);
      parameters.addValue("argmekid", argMekID);
      return SafeReturn.ret(
            mwinNamedTemplate.query(SELECT + whereSQL, parameters, mapper),
            ResourceDom.class);
   }

   public java.util.Enumeration findResources(java.lang.String argFnr)
   {
      String whereSQL = " WHERE T1.FNR = :argfnr";
      final MapSqlParameterSource parameters = new MapSqlParameterSource();
      parameters.addValue("argfnr", argFnr);
      return SafeReturn.ret(
            mwinNamedTemplate.query(SELECT + whereSQL, parameters, mapper),
            java.util.Enumeration.class);
   }

   public java.util.Enumeration findResourcesByShiftPlan(String argFnr,
         int argPlanID)
   {
      throw new java.lang.UnsupportedOperationException("Not yet implemented");
   }

   public java.util.Enumeration findResourcesBySkills(String argFnr,
         String argSkills)
   {
      throw new java.lang.UnsupportedOperationException("Not yet implemented");
   }

   public java.util.Enumeration findResourcesInGroup(String argFnr,
         int argResourceGroupRef)
   {
      throw new java.lang.UnsupportedOperationException("Not yet implemented");
   }

   public ResourceDom create(String argFnr, String argName, int argType,
         String argFrom, String argTo, int argRef, String argCreatedBy,
         String argDateCreated, String argComment, String argSkillTypes,
         String argBrandTypes, boolean argPlanConnected, int argPlanID,
         String argMnetMekID, int sortering) throws SQLException
   {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("FNR", argFnr);
      parameters.put("NAME", argName);
      parameters.put("TYPE", argType);
      parameters.put("FROM", argFrom);
      parameters.put("TO", argTo);
      parameters.put("REF", argRef);
      parameters.put("CREATEDBY", argCreatedBy);
      parameters.put("DATECREATED", argDateCreated);
      parameters.put("COMMENT", argComment);
      parameters.put("SKILLTYPES", argSkillTypes);
      parameters.put("BRANDTYPES", argBrandTypes);
      parameters.put("PLANCONNECTED", argPlanConnected);
      parameters.put("PLANID", argPlanID);
      parameters.put("MNETMEKID", argMnetMekID);
      parameters.put("SORTERING", sortering);
      int nr = simpleInsert.execute(parameters);
      if (nr == 0)
      {
         throw new SQLException("Failure to insert " + argFnr + argName
               + argType + argFrom + argTo + argRef + argCreatedBy
               + argDateCreated + argComment + argSkillTypes
               + argBrandTypes + argPlanConnected + argPlanID
               + argMnetMekID + sortering);
      }
      return null;
   }

   public java.util.Enumeration findAllResources()
   {
      throw new java.lang.UnsupportedOperationException("Not yet implemented");
   }

   public int remove(ResourceKey pk)
   {
      String sql = "DELETE FROM MWIN.RESOURCE T1 WHERE  T1.FNR = :fnr  AND  T1.RESOURCEID = :resourceid ";
      final MapSqlParameterSource parameters = new MapSqlParameterSource();
      parameters.addValue("fnr", pk.fnr);
      parameters.addValue("resourceid", pk.resourceID);
      return mwinNamedTemplate.update(sql, parameters);
   }

   public boolean create(ResourceBean resource) throws SQLException
   {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("DATECREATED", resource.getDateCreated());
      parameters.put("RESOURCETYPE", resource.getResourceType());
      parameters.put("GROUPPLAN", resource.getGroupPlan());
      parameters.put("CREATEDBY", resource.getCreatedBy());
      parameters.put("SKILLTYPES", resource.getSkillTypes());
      parameters.put("TIMESTAMPCREATED", resource.getTimeStampCreated());
      parameters.put("AVAILABLEFROM", resource.getAvailableFrom());
      parameters.put("COMMENT", resource.getComment());
      parameters.put("AVAILABLETO", resource.getAvailableTo());
      parameters.put("PLANCONNECTED", resource.getPlanConnected());
      parameters.put("DATECHANGED", resource.getDateChanged());
      parameters.put("RESOURCEID", resource.getResourceID());
      parameters.put("MNETMEKID", resource.getMnetMekID());
      parameters.put("PLANID", resource.getPlanID());
      parameters.put("CHANGEDBY", resource.getChangedBy());
      parameters.put("RESOURCENAME", resource.getResourceName());
      parameters.put("RESOURCEGROUPREF", resource.getResourceGroupRef());
      parameters.put("BRANDTYPES", resource.getBrandTypes());
      parameters.put("FNR", resource.getFnr());
      parameters.put("SORTERING", resource.getSortering());
      parameters.put("COLORTYPE", resource.getColortype());
      parameters.put("WORKEFFECTIVITY", resource.getWorkEffectivity());
      parameters
            .put("RESOURCEMUSTSTAMPLE", resource.getResourceMustStample());
      parameters.put("SHIFTPLANCHANGED", resource.getShiftplanChanged());
      parameters.put("SALARYTYPE", resource.getSalaryType());
      parameters.put("EMPLOYEENR", resource.getEmployeeNr());
      parameters.put("BOOKABLE", resource.getBookable());
      parameters.put("TOOLCOMPENSATION", resource.getToolCompensation());
      parameters.put("TOOLCOMPENSATIONRATE",
            resource.getToolCompensationRate());
      int nr = simpleInsert.execute(parameters);
      if (nr == 0)
      {
         throw new SQLException("Failure to insert " + resource);
      }
      return true;
   }
}