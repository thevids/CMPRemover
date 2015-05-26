package no.moller.evp.model.ejb;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.moller.util.jdbc.UtilRowMapper;

/**
 * Rowmapper for JdbcDao
 */
public class ResourceDomRowMapper extends UtilRowMapper<ResourceDom>
{

   public ResourceDom mapRow(final ResultSet rs, final int rowNum)
         throws SQLException
   {
      ResourceDom domObject = new ResourceDom();
      final ResourceKey pk = new ResourceKey(rs.getString(trim("FNR")),
            rs.getInt("RESOURCEID"));
      ResourceData data = new ResourceData();
      data.setPrimaryKey(pk);
      data.setDateCreated(rs.getString(trim("DATECREATED")));
      data.setResourceType(rs.getInt("RESOURCETYPE"));
      data.setGroupPlan(rs.getBoolean("GROUPPLAN"));
      data.setCreatedBy(rs.getString(trim("CREATEDBY")));
      data.setSkillTypes(rs.getString(trim("SKILLTYPES")));
      data.setTimeStampCreated(rs.getTimestamp("TIMESTAMPCREATED"));
      data.setAvailableFrom(rs.getString(trim("AVAILABLEFROM")));
      data.setComment(rs.getString(trim("COMMENT")));
      data.setAvailableTo(rs.getString(trim("AVAILABLETO")));
      data.setPlanConnected(rs.getBoolean("PLANCONNECTED"));
      data.setDateChanged(rs.getString(trim("DATECHANGED")));
      data.setMnetMekID(rs.getString(trim("MNETMEKID")));
      data.setPlanID(rs.getInt("PLANID"));
      data.setChangedBy(rs.getString(trim("CHANGEDBY")));
      data.setResourceName(rs.getString(trim("RESOURCENAME")));
      data.setResourceGroupRef(rs.getInt("RESOURCEGROUPREF"));
      data.setBrandTypes(rs.getString(trim("BRANDTYPES")));
      data.setSortering(rs.getInt("SORTERING"));
      data.setColortype(rs.getInt("COLORTYPE"));
      data.setWorkEffectivity(rs.getInt("WORKEFFECTIVITY"));
      data.setResourceMustStample(rs.getInt("RESOURCEMUSTSTAMPLE"));
      data.setShiftplanChanged(rs.getInt("SHIFTPLANCHANGED"));
      data.setSalaryType(rs.getString(trim("SALARYTYPE")));
      data.setEmployeeNr(rs.getInt("EMPLOYEENR"));
      data.setBookable(rs.getBoolean("BOOKABLE"));
      data.setToolCompensation(rs.getBoolean("TOOLCOMPENSATION"));
      data.setToolCompensationRate(rs.getDouble("TOOLCOMPENSATIONRATE"));
      data.copyTo(domObject);
      domObject.setFnr(pk.fnr);
      domObject.setResourceID(pk.resourceID);
      return domObject;
   }
}