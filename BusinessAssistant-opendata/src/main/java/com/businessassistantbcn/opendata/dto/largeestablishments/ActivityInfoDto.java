package com.businessassistantbcn.opendata.dto.largeestablishments;

import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ActivityInfoDto 
{
 
   private int   idActivity;
   private String activityName;

   public ActivityInfoDto() { }
   
   public ActivityInfoDto(int idActivity, String activityName) 
   { this.idActivity = idActivity;
     this.activityName = activityName;
   }
   
   @Override
   public int hashCode() {
 		return Objects.hash(idActivity, activityName);
 	}

 	@Override
 	public boolean equals(Object obj) {
 		if (this == obj)
 			return true;
 		if (obj == null)
 			return false;
 		if (getClass() != obj.getClass())
 			return false;
 		
 		ActivityInfoDto other = (ActivityInfoDto) obj; 		
 		if( (this.activityName==null) && (other.getActivityName()) != null ) return false; 
 		if( (this.activityName!=null) && (other.getActivityName()) == null ) return false;
 		
 		return this.idActivity == other.idActivity && Objects.equals(this.activityName, other.activityName);
 	}

}



