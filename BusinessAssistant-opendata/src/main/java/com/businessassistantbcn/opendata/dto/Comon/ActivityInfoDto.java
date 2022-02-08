package com.businessassistantbcn.opendata.dto.Comon;

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

 
}

