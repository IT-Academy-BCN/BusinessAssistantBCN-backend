package com.businessassistantbcn.opendata.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Getter
@Setter
public class ActivityInfoDto
{
    private Long idActivity;
    private String activityName;

    public ActivityInfoDto() { }

    public ActivityInfoDto(Long idActivity, String activityName)
    {
        this.idActivity = idActivity;
        this.activityName = activityName;
    }

}
