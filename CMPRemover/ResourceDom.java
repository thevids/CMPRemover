package no.moller.evp.model.ejb;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

/**
 * Domain object.
 */
public class ResourceDom implements no.moller.evp.model.ejb.ResourceData.Store
{

   public String availableFrom;
   public String availableTo;
   public String changedBy;
   public String comment;
   public String createdBy;
   public String dateChanged;
   public String dateCreated;
   private javax.ejb.EntityContext entityContext = null;
   public String fnr;
   public int resourceGroupRef;
   public int resourceID;
   public String resourceName;
   public int resourceType;
   private final static long serialVersionUID = 3206093459760846163L;
   public java.sql.Timestamp timeStampCreated;
   public boolean planConnected;
   public int planID;
   public String skillTypes;
   public String brandTypes;
   public boolean groupPlan;
   public String mnetMekID;
   /**
    * Implementation field for persistent attribute: sortering
    */
   public int sortering;
   /**
    * Implementation field for persistent attribute: colortype
    */
   public int colortype;
   /**
    * Implementation field for persistent attribute: workEffectivity
    */
   public int workEffectivity;
   /**
    * Implementation field for persistent attribute: resourceMustStample
    */
   public int resourceMustStample;
   /**
    * Implementation field for persistent attribute: shiftplanChanged
    */
   public int shiftplanChanged;
   /**
    * Implementation field for persistent attribute: salaryType
    */
   public java.lang.String salaryType;
   /**
    * Implementation field for persistent attribute: employeeNr
    */
   public int employeeNr;
   /**
    * Implementation field for persistent attribute: bookable
    */
   public boolean bookable;
   /**
    * Implementation field for persistent attribute: toolCompensationRate
    */
   public java.lang.Double toolCompensationRate;
   /**
    * Implementation field for persistent attribute: toolCompensation
    */
   public boolean toolCompensation;

   public java.lang.String getAvailableFrom()
   {
      return availableFrom;
   }

   public java.lang.String getAvailableTo()
   {
      return availableTo;
   }

   public java.lang.String getBrandTypes()
   {
      return brandTypes;
   }

   public java.lang.String getChangedBy()
   {
      return changedBy;
   }

   public java.lang.String getComment()
   {
      return comment;
   }

   public java.lang.String getCreatedBy()
   {
      return createdBy;
   }

   public java.lang.String getDateChanged()
   {
      return dateChanged;
   }

   public java.lang.String getDateCreated()
   {
      return dateCreated;
   }

   public javax.ejb.EntityContext getEntityContext()
   {
      return entityContext;
   }

   public boolean getGroupPlan()
   {
      return groupPlan;
   }

   public java.lang.String getMnetMekID()
   {
      return mnetMekID;
   }

   int getNextResourceID() throws javax.ejb.FinderException
   {
      int nextID = 0;

      try
      {
         Resource newestResource = ((ResourceHome) getEntityContext().getEJBHome()).findNewestResource();
         nextID = ((ResourceKey) newestResource.getPrimaryKey()).resourceID + 1;
      }
      catch (ObjectNotFoundException e)
      {
         nextID = 1;
      }
      return nextID;
   }

   public boolean getPlanConnected()
   {
      return planConnected;
   }

   public int getPlanID()
   {
      return planID;
   }

   public int getResourceGroupRef()
   {
      return resourceGroupRef;
   }

   public java.lang.String getResourceName()
   {
      return resourceName;
   }

   public int getResourceType()
   {
      return resourceType;
   }

   public java.lang.String getSkillTypes()
   {
      return skillTypes;
   }

   public java.sql.Timestamp getTimeStampCreated()
   {
      return timeStampCreated;
   }

   public void setAvailableFrom(java.lang.String newValue)
   {
      this.availableFrom = newValue;
   }

   public void setAvailableTo(java.lang.String newValue)
   {
      this.availableTo = newValue;
   }

   public void setBrandTypes(java.lang.String newValue)
   {
      this.brandTypes = newValue;
   }

   public void setChangedBy(java.lang.String newValue)
   {
      this.changedBy = newValue;
   }

   public void setComment(java.lang.String newValue)
   {
      this.comment = newValue;
   }

   public void setCreatedBy(java.lang.String newValue)
   {
      this.createdBy = newValue;
   }

   public void setDateChanged(java.lang.String newValue)
   {
      this.dateChanged = newValue;
   }

   public void setDateCreated(java.lang.String newValue)
   {
      this.dateCreated = newValue;
   }

   public void setEntityContext(javax.ejb.EntityContext ctx)
   {
      entityContext = ctx;
   }

   public void setGroupPlan(boolean newValue)
   {
      this.groupPlan = newValue;
   }

   public void setMnetMekID(java.lang.String newValue)
   {
      this.mnetMekID = newValue;
   }

   public void setPlanConnected(boolean newValue)
   {
      this.planConnected = newValue;
   }

   public void setPlanID(int newValue)
   {
      this.planID = newValue;
   }

   public void setResourceGroupRef(int newValue)
   {
      this.resourceGroupRef = newValue;
   }

   public void setResourceName(java.lang.String newValue)
   {
      this.resourceName = newValue;
   }

   public void setResourceType(int newValue)
   {
      this.resourceType = newValue;
   }

   public void setSkillTypes(java.lang.String newValue)
   {
      this.skillTypes = newValue;
   }

   public void setTimeStampCreated(java.sql.Timestamp newValue)
   {
      this.timeStampCreated = newValue;
   }

   public void unsetEntityContext()
   {
      entityContext = null;
   }

   public no.moller.evp.model.ejb.ResourceData getResourceData()
   {
      return new no.moller.evp.model.ejb.ResourceData(this);
   }

   public void setResourceData(no.moller.evp.model.ejb.ResourceData data) throws com.ibm.etools.ejb.client.runtime.FieldChangedException
   {
      data.copyTo(this);
      if (!data.getIscommentDirty())
      {
         if (this.getComment() != null && data.getComment() != null)
         {
            if (!this.getComment().equals(data.getComment()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getComment() == null && data.getComment() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsdateChangedDirty())
      {
         if (this.getDateChanged() != null && data.getDateChanged() != null)
         {
            if (!this.getDateChanged().equals(data.getDateChanged()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getDateChanged() == null && data.getDateChanged() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsplanIDDirty())
      {
         if (this.getPlanID() != data.getPlanID())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIsavailableToDirty())
      {
         if (this.getAvailableTo() != null && data.getAvailableTo() != null)
         {
            if (!this.getAvailableTo().equals(data.getAvailableTo()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getAvailableTo() == null && data.getAvailableTo() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsresourceNameDirty())
      {
         if (this.getResourceName() != null && data.getResourceName() != null)
         {
            if (!this.getResourceName().equals(data.getResourceName()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getResourceName() == null && data.getResourceName() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsplanConnectedDirty())
      {
         if (this.getPlanConnected() != data.getPlanConnected())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIstimeStampCreatedDirty())
      {
         if (this.getTimeStampCreated() != null && data.getTimeStampCreated() != null)
         {
            if (!this.getTimeStampCreated().equals(data.getTimeStampCreated()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getTimeStampCreated() == null && data.getTimeStampCreated() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsbrandTypesDirty())
      {
         if (this.getBrandTypes() != null && data.getBrandTypes() != null)
         {
            if (!this.getBrandTypes().equals(data.getBrandTypes()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getBrandTypes() == null && data.getBrandTypes() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsmnetMekIDDirty())
      {
         if (this.getMnetMekID() != null && data.getMnetMekID() != null)
         {
            if (!this.getMnetMekID().equals(data.getMnetMekID()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getMnetMekID() == null && data.getMnetMekID() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsresourceTypeDirty())
      {
         if (this.getResourceType() != data.getResourceType())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIsskillTypesDirty())
      {
         if (this.getSkillTypes() != null && data.getSkillTypes() != null)
         {
            if (!this.getSkillTypes().equals(data.getSkillTypes()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getSkillTypes() == null && data.getSkillTypes() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsdateCreatedDirty())
      {
         if (this.getDateCreated() != null && data.getDateCreated() != null)
         {
            if (!this.getDateCreated().equals(data.getDateCreated()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getDateCreated() == null && data.getDateCreated() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIschangedByDirty())
      {
         if (this.getChangedBy() != null && data.getChangedBy() != null)
         {
            if (!this.getChangedBy().equals(data.getChangedBy()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getChangedBy() == null && data.getChangedBy() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsgroupPlanDirty())
      {
         if (this.getGroupPlan() != data.getGroupPlan())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIscreatedByDirty())
      {
         if (this.getCreatedBy() != null && data.getCreatedBy() != null)
         {
            if (!this.getCreatedBy().equals(data.getCreatedBy()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getCreatedBy() == null && data.getCreatedBy() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsavailableFromDirty())
      {
         if (this.getAvailableFrom() != null && data.getAvailableFrom() != null)
         {
            if (!this.getAvailableFrom().equals(data.getAvailableFrom()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getAvailableFrom() == null && data.getAvailableFrom() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsresourceGroupRefDirty())
      {
         if (this.getResourceGroupRef() != data.getResourceGroupRef())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIssorteringDirty())
      {
         if (this.getSortering() != data.getSortering())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIscolortypeDirty())
      {
         if (this.getColortype() != data.getColortype())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIsworkEffectivityDirty())
      {
         if (this.getWorkEffectivity() != data.getWorkEffectivity())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIsresourceMustStampleDirty())
      {
         if (this.getResourceMustStample() != data.getResourceMustStample())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIsshiftplanChangedDirty())
      {
         if (this.getShiftplanChanged() != data.getShiftplanChanged())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIssalaryTypeDirty())
      {
         if (this.getSalaryType() != null && data.getSalaryType() != null)
         {
            if (!this.getSalaryType().equals(data.getSalaryType()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getSalaryType() == null && data.getSalaryType() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIsemployeeNrDirty())
      {
         if (this.getEmployeeNr() != data.getEmployeeNr())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIsbookableDirty())
      {
         if (this.isBookable() != data.isBookable())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
      if (!data.getIstoolCompensationRateDirty())
      {
         if (this.getToolCompensationRate() != null && data.getToolCompensationRate() != null)
         {
            if (!this.getToolCompensationRate().equals(data.getToolCompensationRate()))
            {
               throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
            }
         }
         else if (!(this.getToolCompensationRate() == null && data.getToolCompensationRate() == null))
         {
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
         }
      }
      if (!data.getIstoolCompensationDirty())
      {
         if (this.isToolCompensation() != data.isToolCompensation())
            throw new com.ibm.etools.ejb.client.runtime.FieldChangedException();
      }
   }

   public no.moller.evp.model.ejb.ResourceData syncResourceData(no.moller.evp.model.ejb.ResourceData data)
   {
      data.copyTo(this);
      return this.getResourceData();
   }

   public ResourceKey getPrimaryKey()
   {
      return new ResourceKey(fnr, resourceID);
   }

   public int getSortering()
   {
      return sortering;
   }

   public void setSortering(int newSortering)
   {
      sortering = newSortering;
   }

   public int getColortype()
   {
      return colortype;
   }

   public void setColortype(int newColortype)
   {
      colortype = newColortype;
   }

   public int getWorkEffectivity()
   {
      return workEffectivity;
   }

   public void setWorkEffectivity(int newWorkEffectivity)
   {
      workEffectivity = newWorkEffectivity;
   }

   public int getResourceMustStample()
   {
      return resourceMustStample;
   }

   public void setResourceMustStample(int newResourceMustStample)
   {
      resourceMustStample = newResourceMustStample;
   }

   public int getShiftplanChanged()
   {
      return shiftplanChanged;
   }

   public void setShiftplanChanged(int newShiftplanChanged)
   {
      shiftplanChanged = newShiftplanChanged;
   }

   public java.lang.String getSalaryType()
   {
      return salaryType;
   }

   public void setSalaryType(java.lang.String newSalaryType)
   {
      salaryType = newSalaryType;
   }

   public int getEmployeeNr()
   {
      return employeeNr;
   }

   public void setEmployeeNr(int newEmployeeNr)
   {
      employeeNr = newEmployeeNr;
   }

   public boolean isBookable()
   {
      return bookable;
   }

   public void setBookable(boolean newBookable)
   {
      bookable = newBookable;
   }

   public java.lang.Double getToolCompensationRate()
   {
      return toolCompensationRate;
   }

   public void setToolCompensationRate(java.lang.Double newToolCompensationRate)
   {
      toolCompensationRate = newToolCompensationRate;
   }

   public boolean isToolCompensation()
   {
      return toolCompensation;
   }

   public void setToolCompensation(boolean newToolCompensation)
   {
      toolCompensation = newToolCompensation;
   }

   public java.lang.String getFnr()
   {
      return fnr;
   }

   public void setFnr(java.lang.String fnr)
   {
      this.fnr = fnr;
   }

   public int getResourceID()
   {
      return resourceID;
   }

   public void setResourceID(int resourceID)
   {
      this.resourceID = resourceID;
   }
}
