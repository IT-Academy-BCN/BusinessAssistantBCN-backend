package com.businessassistantbcn.opendata.dto.largestablishments;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class LargeStablishmentsResponseDto {

    private String help;
    private boolean success;
    private Result result;

    public void setHelp(String help) {
        this.help = help;
    }

    public String getHelp() {
        return help;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public static class Results {

        @JsonProperty("notes_translated")
        private NotesTranslated notesTranslated;
        private String geolocation;
        private String code;
        @JsonProperty("url_tornada")
        private UrlTornada urlTornada;
        @JsonProperty("dataset_fields_description")
        private String datasetFieldsDescription;
        private String fuente;
        @JsonProperty("private")
        private boolean priv;
        @JsonProperty("num_tags")
        private int numTags;
        private String api;
        private String frequency;
        private String agrupacio;
        private String id;
        @JsonProperty("decalatgeDMY")
        private String decalatgedmy;
        @JsonProperty("title_translated")
        private TitleTranslated titleTranslated;
        @JsonProperty("metadata_modified")
        private Date metadataModified;
        private String author;
        @JsonProperty("author_email")
        private String authorEmail;
        @JsonProperty("update_string")
        private String updateString;
        private boolean isopen;
        @JsonProperty("long_format")
        private String longFormat;
        private String state;
        private String version;
        @JsonProperty("relationships_as_object")
        private List<String> relationshipsAsObject;
        private String department;
        @JsonProperty("creator_user_id")
        private String creatorUserId;
        @JsonProperty("incorporated_in_cityos")
        private String incorporatedInCityos;
        private String type;
        private List<Resources> resources;
        private String observations;
        @JsonProperty("token_required")
        private String tokenRequired;
        @JsonProperty("decalatgeNum")
        private String decalatgenum;
        private List<Tags> tags;
        @JsonProperty("fecha_publicacion")
        private Date fechaPublicacion;
        @JsonProperty("load_type")
        private String loadType;
        @JsonProperty("num_resources")
        private int numResources;
        @JsonProperty("license_id")
        private String licenseId;
        @JsonProperty("relationships_as_subject")
        private List<String> relationshipsAsSubject;
        @JsonProperty("license_title")
        private String licenseTitle;
        private List<String> groups;
        private Organization organization;
        private String name;
        private String url;
        private String notes;
        @JsonProperty("owner_org")
        private String ownerOrg;
        private String server;
        private String tag;
        @JsonProperty("license_url")
        private String licenseUrl;
        private String historical;
        @JsonProperty("automatic_generation")
        private String automaticGeneration;
        private String title;
        @JsonProperty("revision_id")
        private String revisionId;
        @JsonProperty("date_deactivation_informed")
        private String dateDeactivationInformed;
        private List<Extras> extras;

        public void setNotesTranslated(NotesTranslated notesTranslated) {
            this.notesTranslated = notesTranslated;
        }

        public NotesTranslated getNotesTranslated() {
            return notesTranslated;
        }

        public void setGeolocation(String geolocation) {
            this.geolocation = geolocation;
        }

        public String getGeolocation() {
            return geolocation;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setUrlTornada(UrlTornada urlTornada) {
            this.urlTornada = urlTornada;
        }

        public UrlTornada getUrlTornada() {
            return urlTornada;
        }

        public void setDatasetFieldsDescription(String datasetFieldsDescription) {
            this.datasetFieldsDescription = datasetFieldsDescription;
        }

        public String getDatasetFieldsDescription() {
            return datasetFieldsDescription;
        }

        public void setFuente(String fuente) {
            this.fuente = fuente;
        }

        public String getFuente() {
            return fuente;
        }

        public void setPrivate(boolean priv) {
            this.priv = priv;
        }

        public boolean getPrivate() {
            return priv;
        }

        public void setNumTags(int numTags) {
            this.numTags = numTags;
        }

        public int getNumTags() {
            return numTags;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getApi() {
            return api;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setAgrupacio(String agrupacio) {
            this.agrupacio = agrupacio;
        }

        public String getAgrupacio() {
            return agrupacio;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setDecalatgedmy(String decalatgedmy) {
            this.decalatgedmy = decalatgedmy;
        }

        public String getDecalatgedmy() {
            return decalatgedmy;
        }

        public void setTitleTranslated(TitleTranslated titleTranslated) {
            this.titleTranslated = titleTranslated;
        }

        public TitleTranslated getTitleTranslated() {
            return titleTranslated;
        }

        public void setMetadataModified(Date metadataModified) {
            this.metadataModified = metadataModified;
        }

        public Date getMetadataModified() {
            return metadataModified;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthorEmail(String authorEmail) {
            this.authorEmail = authorEmail;
        }

        public String getAuthorEmail() {
            return authorEmail;
        }

        public void setUpdateString(String updateString) {
            this.updateString = updateString;
        }

        public String getUpdateString() {
            return updateString;
        }

        public void setIsopen(boolean isopen) {
            this.isopen = isopen;
        }

        public boolean getIsopen() {
            return isopen;
        }

        public void setLongFormat(String longFormat) {
            this.longFormat = longFormat;
        }

        public String getLongFormat() {
            return longFormat;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

        public void setRelationshipsAsObject(List<String> relationshipsAsObject) {
            this.relationshipsAsObject = relationshipsAsObject;
        }

        public List<String> getRelationshipsAsObject() {
            return relationshipsAsObject;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getDepartment() {
            return department;
        }

        public void setCreatorUserId(String creatorUserId) {
            this.creatorUserId = creatorUserId;
        }

        public String getCreatorUserId() {
            return creatorUserId;
        }

        public void setIncorporatedInCityos(String incorporatedInCityos) {
            this.incorporatedInCityos = incorporatedInCityos;
        }

        public String getIncorporatedInCityos() {
            return incorporatedInCityos;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setResources(List<Resources> resources) {
            this.resources = resources;
        }

        public List<Resources> getResources() {
            return resources;
        }

        public void setObservations(String observations) {
            this.observations = observations;
        }

        public String getObservations() {
            return observations;
        }

        public void setTokenRequired(String tokenRequired) {
            this.tokenRequired = tokenRequired;
        }

        public String getTokenRequired() {
            return tokenRequired;
        }

        public void setDecalatgenum(String decalatgenum) {
            this.decalatgenum = decalatgenum;
        }

        public String getDecalatgenum() {
            return decalatgenum;
        }

        public void setTags(List<Tags> tags) {
            this.tags = tags;
        }

        public List<Tags> getTags() {
            return tags;
        }

        public void setFechaPublicacion(Date fechaPublicacion) {
            this.fechaPublicacion = fechaPublicacion;
        }

        public Date getFechaPublicacion() {
            return fechaPublicacion;
        }

        public void setLoadType(String loadType) {
            this.loadType = loadType;
        }

        public String getLoadType() {
            return loadType;
        }

        public void setNumResources(int numResources) {
            this.numResources = numResources;
        }

        public int getNumResources() {
            return numResources;
        }

        public void setLicenseId(String licenseId) {
            this.licenseId = licenseId;
        }

        public String getLicenseId() {
            return licenseId;
        }

        public void setRelationshipsAsSubject(List<String> relationshipsAsSubject) {
            this.relationshipsAsSubject = relationshipsAsSubject;
        }

        public List<String> getRelationshipsAsSubject() {
            return relationshipsAsSubject;
        }

        public void setLicenseTitle(String licenseTitle) {
            this.licenseTitle = licenseTitle;
        }

        public String getLicenseTitle() {
            return licenseTitle;
        }

        public void setGroups(List<String> groups) {
            this.groups = groups;
        }

        public List<String> getGroups() {
            return groups;
        }

        public void setOrganization(Organization organization) {
            this.organization = organization;
        }

        public Organization getOrganization() {
            return organization;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getNotes() {
            return notes;
        }

        public void setOwnerOrg(String ownerOrg) {
            this.ownerOrg = ownerOrg;
        }

        public String getOwnerOrg() {
            return ownerOrg;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getServer() {
            return server;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setHistorical(String historical) {
            this.historical = historical;
        }

        public String getHistorical() {
            return historical;
        }

        public void setAutomaticGeneration(String automaticGeneration) {
            this.automaticGeneration = automaticGeneration;
        }

        public String getAutomaticGeneration() {
            return automaticGeneration;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setRevisionId(String revisionId) {
            this.revisionId = revisionId;
        }

        public String getRevisionId() {
            return revisionId;
        }

        public void setDateDeactivationInformed(String dateDeactivationInformed) {
            this.dateDeactivationInformed = dateDeactivationInformed;
        }

        public String getDateDeactivationInformed() {
            return dateDeactivationInformed;
        }

        public void setExtras(List<Extras> extras) {
            this.extras = extras;
        }

        public List<Extras> getExtras() {
            return extras;
        }

    }

    public static class Resources {
        @JsonProperty("cache_last_updated")
        private String cacheLastUpdated;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("package_id")
        private String packageId;
        @JsonProperty("datastore_active")
        private boolean datastoreActive;
        private String id;
        private String size;
        private String state;
        @JsonProperty("api_access_number")
        private String apiAccessNumber;
        @JsonProperty("api_access_number_absolute")
        private String apiAccessNumberAbsolute;
        @JsonProperty("token_required")
        private String tokenRequired;
        private String hash;
        private String description;
        private String format;
        private String downloads;
        @JsonProperty("mimetype_inner")
        private String mimetypeInner;
        @JsonProperty("url_type")
        private String urlType;
        @JsonProperty("token_provider")
        private String tokenProvider;
        @JsonProperty("file_volum")
        private String fileVolum;
        private String mimetype;
        @JsonProperty("cache_url")
        private String cacheUrl;
        private String name;
        private Date created;
        private String url;
        @JsonProperty("downloads_absolute")
        private String downloadsAbsolute;
        @JsonProperty("map_visualization_res")
        private String mapVisualizationRes;
        private String qa;
        @JsonProperty("last_modified")
        private String lastModified;
        private int position;
        @JsonProperty("revision_id")
        private String revisionId;
        @JsonProperty("resource_type")
        private String resourceType;

        public void setCacheLastUpdated(String cacheLastUpdated) {
            this.cacheLastUpdated = cacheLastUpdated;
        }

        public String getCacheLastUpdated() {
            return cacheLastUpdated;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getPackageId() {
            return packageId;
        }

        public void setDatastoreActive(boolean datastoreActive) {
            this.datastoreActive = datastoreActive;
        }

        public boolean getDatastoreActive() {
            return datastoreActive;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getSize() {
            return size;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setApiAccessNumber(String apiAccessNumber) {
            this.apiAccessNumber = apiAccessNumber;
        }

        public String getApiAccessNumber() {
            return apiAccessNumber;
        }

        public void setApiAccessNumberAbsolute(String apiAccessNumberAbsolute) {
            this.apiAccessNumberAbsolute = apiAccessNumberAbsolute;
        }

        public String getApiAccessNumberAbsolute() {
            return apiAccessNumberAbsolute;
        }

        public void setTokenRequired(String tokenRequired) {
            this.tokenRequired = tokenRequired;
        }

        public String getTokenRequired() {
            return tokenRequired;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getHash() {
            return hash;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }

        public void setDownloads(String downloads) {
            this.downloads = downloads;
        }

        public String getDownloads() {
            return downloads;
        }

        public void setMimetypeInner(String mimetypeInner) {
            this.mimetypeInner = mimetypeInner;
        }

        public String getMimetypeInner() {
            return mimetypeInner;
        }

        public void setUrlType(String urlType) {
            this.urlType = urlType;
        }

        public String getUrlType() {
            return urlType;
        }

        public void setTokenProvider(String tokenProvider) {
            this.tokenProvider = tokenProvider;
        }

        public String getTokenProvider() {
            return tokenProvider;
        }

        public void setFileVolum(String fileVolum) {
            this.fileVolum = fileVolum;
        }

        public String getFileVolum() {
            return fileVolum;
        }

        public void setMimetype(String mimetype) {
            this.mimetype = mimetype;
        }

        public String getMimetype() {
            return mimetype;
        }

        public void setCacheUrl(String cacheUrl) {
            this.cacheUrl = cacheUrl;
        }

        public String getCacheUrl() {
            return cacheUrl;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Date getCreated() {
            return created;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setDownloadsAbsolute(String downloadsAbsolute) {
            this.downloadsAbsolute = downloadsAbsolute;
        }

        public String getDownloadsAbsolute() {
            return downloadsAbsolute;
        }

        public void setMapVisualizationRes(String mapVisualizationRes) {
            this.mapVisualizationRes = mapVisualizationRes;
        }

        public String getMapVisualizationRes() {
            return mapVisualizationRes;
        }

        public void setQa(String qa) {
            this.qa = qa;
        }

        public String getQa() {
            return qa;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public String getLastModified() {
            return lastModified;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public void setRevisionId(String revisionId) {
            this.revisionId = revisionId;
        }

        public String getRevisionId() {
            return revisionId;
        }

        public void setResourceType(String resourceType) {
            this.resourceType = resourceType;
        }

        public String getResourceType() {
            return resourceType;
        }

    }

    public static class NotesTranslated {

        private String ca;
        private String en;
        private String es;

        public void setCa(String ca) {
            this.ca = ca;
        }

        public String getCa() {
            return ca;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return en;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getEs() {
            return es;
        }

    }

    public static class Tags {

        @JsonProperty("vocabulary_id")
        private String vocabularyId;
        private String state;
        @JsonProperty("display_name")
        private String displayName;
        private String id;
        private String name;

        public void setVocabularyId(String vocabularyId) {
            this.vocabularyId = vocabularyId;
        }

        public String getVocabularyId() {
            return vocabularyId;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    public static class Parent {
        private String code;
        @JsonProperty("display_name")
        private String displayName;
        private String description;
        private Date created;
        @JsonProperty("package_count")
        private int packageCount;
        @JsonProperty("image_display_url")
        private String imageDisplayUrl;
        @JsonProperty("description_translated")
        private DescriptionTranslated descriptionTranslated;
        private String name;
        @JsonProperty("is_organization")
        private boolean isOrganization;
        private String state;
        @JsonProperty("image_url")
        private String imageUrl;
        private List<String> groups;
        private String type;
        private String title;
        @JsonProperty("revision_id")
        private String revisionId;
        @JsonProperty("title_translated")
        private TitleTranslated titleTranslated;
        @JsonProperty("num_followers")
        private int numFollowers;
        private String id;
        @JsonProperty("approval_status")
        private String approvalStatus;

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Date getCreated() {
            return created;
        }

        public void setPackageCount(int packageCount) {
            this.packageCount = packageCount;
        }

        public int getPackageCount() {
            return packageCount;
        }

        public void setImageDisplayUrl(String imageDisplayUrl) {
            this.imageDisplayUrl = imageDisplayUrl;
        }

        public String getImageDisplayUrl() {
            return imageDisplayUrl;
        }

        public void setDescriptionTranslated(DescriptionTranslated descriptionTranslated) {
            this.descriptionTranslated = descriptionTranslated;
        }

        public DescriptionTranslated getDescriptionTranslated() {
            return descriptionTranslated;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setIsOrganization(boolean isOrganization) {
            this.isOrganization = isOrganization;
        }

        public boolean getIsOrganization() {
            return isOrganization;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setGroups(List<String> groups) {
            this.groups = groups;
        }

        public List<String> getGroups() {
            return groups;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setRevisionId(String revisionId) {
            this.revisionId = revisionId;
        }

        public String getRevisionId() {
            return revisionId;
        }

        public void setTitleTranslated(TitleTranslated titleTranslated) {
            this.titleTranslated = titleTranslated;
        }

        public TitleTranslated getTitleTranslated() {
            return titleTranslated;
        }

        public void setNumFollowers(int numFollowers) {
            this.numFollowers = numFollowers;
        }

        public int getNumFollowers() {
            return numFollowers;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

    }

    public static class Organization {

        private String code;
        @JsonProperty("display_name")
        private String displayName;
        private String description;
        private Parent parent;
        private Date created;
        @JsonProperty("package_count")
        private int packageCount;
        @JsonProperty("image_display_url")
        private String imageDisplayUrl;
        @JsonProperty("tematica_nti")
        private String tematicaNti;
        @JsonProperty("description_translated")
        private DescriptionTranslated descriptionTranslated;
        private String name;
        @JsonProperty("is_organization")
        private boolean isOrganization;
        private String state;
        @JsonProperty("image_url")
        private String imageUrl;
        private List<Groups> groups;
        private String type;
        private String title;
        @JsonProperty("revision_id")
        private String revisionId;
        @JsonProperty("title_translated")
        private TitleTranslated titleTranslated;
        @JsonProperty("num_followers")
        private int numFollowers;
        private String id;
        @JsonProperty("approval_status")
        private String approvalStatus;

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setParent(Parent parent) {
            this.parent = parent;
        }

        public Parent getParent() {
            return parent;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public Date getCreated() {
            return created;
        }

        public void setPackageCount(int packageCount) {
            this.packageCount = packageCount;
        }

        public int getPackageCount() {
            return packageCount;
        }

        public void setImageDisplayUrl(String imageDisplayUrl) {
            this.imageDisplayUrl = imageDisplayUrl;
        }

        public String getImageDisplayUrl() {
            return imageDisplayUrl;
        }

        public void setTematicaNti(String tematicaNti) {
            this.tematicaNti = tematicaNti;
        }

        public String getTematicaNti() {
            return tematicaNti;
        }

        public void setDescriptionTranslated(DescriptionTranslated descriptionTranslated) {
            this.descriptionTranslated = descriptionTranslated;
        }

        public DescriptionTranslated getDescriptionTranslated() {
            return descriptionTranslated;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setIsOrganization(boolean isOrganization) {
            this.isOrganization = isOrganization;
        }

        public boolean getIsOrganization() {
            return isOrganization;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setGroups(List<Groups> groups) {
            this.groups = groups;
        }

        public List<Groups> getGroups() {
            return groups;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setRevisionId(String revisionId) {
            this.revisionId = revisionId;
        }

        public String getRevisionId() {
            return revisionId;
        }

        public void setTitleTranslated(TitleTranslated titleTranslated) {
            this.titleTranslated = titleTranslated;
        }

        public TitleTranslated getTitleTranslated() {
            return titleTranslated;
        }

        public void setNumFollowers(int numFollowers) {
            this.numFollowers = numFollowers;
        }

        public int getNumFollowers() {
            return numFollowers;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

    }

    public static class Groups {

        private String capacity;
        private String name;

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    public static class Extras {

        private String key;
        private String value;

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    public static class DescriptionTranslated {

        private String ca;
        private String en;
        private String es;

        public void setCa(String ca) {
            this.ca = ca;
        }

        public String getCa() {
            return ca;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return en;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getEs() {
            return es;
        }

    }

    public static class TitleTranslated {

        private String ca;
        private String en;
        private String es;

        public void setCa(String ca) {
            this.ca = ca;
        }

        public String getCa() {
            return ca;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return en;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getEs() {
            return es;
        }

    }

    public static class SearchFacets {

    }

    public static class Result {

        private int count;
        private String sort;
        private Facets facets;
        private List<Results> results;
        @JsonProperty("search_facets")
        private SearchFacets searchFacets;

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getSort() {
            return sort;
        }

        public void setFacets(Facets facets) {
            this.facets = facets;
        }

        public Facets getFacets() {
            return facets;
        }

        public void setResults(List<Results> results) {
            this.results = results;
        }

        public List<Results> getResults() {
            return results;
        }

        public void setSearchFacets(SearchFacets searchFacets) {
            this.searchFacets = searchFacets;
        }

        public SearchFacets getSearchFacets() {
            return searchFacets;
        }

    }

    public static class Facets {

    }

    public static class UrlTornada {

        private String ca;
        private String en;
        private String es;

        public void setCa(String ca) {
            this.ca = ca;
        }

        public String getCa() {
            return ca;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getEn() {
            return en;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getEs() {
            return es;
        }

    }
}

