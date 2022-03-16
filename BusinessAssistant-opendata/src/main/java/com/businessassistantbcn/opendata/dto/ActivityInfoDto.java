package com.businessassistantbcn.opendata.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ActivityInfoDto
{
    private Long activityId;
    private String activityName;

    public ActivityInfoDto() { }

    public ActivityInfoDto(Long activityId, String activityName)
    {
        this.activityId = activityId;
        this.activityName = activityName;
    }

}
