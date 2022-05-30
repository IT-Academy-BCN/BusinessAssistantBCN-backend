package com.businessassistantbcn.opendata.dto.input.municipalmarkets;

public class ClassificationDataDto {

    private Long id;
    public Long getId(){return this.id;}//necessary only for activity in MunicipalMarketsDto

    private String name;
    public String getName(){return this.name;}//necessary only for activity in MunicipalMarketsDto

    private String full_path;
    private int dependency_group;
    private int parent_id;
    private int tree_id;
    private String asia_id;
    private String core_type;
    private int level;
}
