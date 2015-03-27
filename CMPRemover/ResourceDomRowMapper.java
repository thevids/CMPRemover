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
      ResourceData data = new ResourceData();
      final ResourceKey pk = new ResourceKey(rs.getString(trim("FNR")),
            rs.getInt("RESOURCEID"), rs.getLong("SERIALVERSIONUID"));
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
      data.setResourceID(rs.getInt("RESOURCEID"));
      data.setMnetMekID(rs.getString(trim("MNETMEKID")));
      data.setPlanID(rs.getInt("PLANID"));
      data.setChangedBy(rs.getString(trim("CHANGEDBY")));
      data.setResourceName(rs.getString(trim("RESOURCENAME")));
      data.setResourceGroupRef(rs.getInt("RESOURCEGROUPREF"));
      data.setBrandTypes(rs.getString(trim("BRANDTYPES")));
      data.setFnr(rs.getString(trim("FNR")));
      data.setSortering(rs.getInt("SORTERING"));
      data.setColortype(rs.getInt("COLORTYPE"));
      data.setWorkEffectivity(rs.getInt("WORKEFFECTIVITY"));
      data.setResourceMustStample(rs.getInt("RESOURCEMUSTSTAMPLE"));
      data.setShiftplanChanged(rs.getInt("SHIFTPLANCHANGED"));
      data.setSalaryType(rs.getString(trim("SALARYTYPE")));
      data.setEmployeeNr(rs.getInt("EMPLOYEENR"));
      data.setBookable(null);
      data.setToolCompensation(null);
      data.setToolCompensationRate(rs.getDouble("TOOLCOMPENSATIONRATE"));
      ResourceDom domOjbect = new ResourceDom(pk);
      data.copyTo(domOjbect);
      return domOjbect;
   }
}