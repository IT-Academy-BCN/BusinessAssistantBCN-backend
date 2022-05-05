package com.businessassistantbcn.opendata.dto.input.municipalmarkets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValueDto{
    @JsonIgnore
    @JsonProperty("id")
    public int getId() {
        return this.id; }
    public void setId(int id) {
        this.id = id; }
    private int id;

    @JsonIgnore
    @JsonProperty("value")
    public String getValue() {
        return this.value; }
    public void setValue(String value) {
        this.value = value; }
    private String value;

    @JsonIgnore
    @JsonProperty("integer_value")
    public Object getInteger_value() {
        return this.integer_value; }
    public void setInteger_value(Object integer_value) {
        this.integer_value = integer_value; }
    private Object integer_value;

    @JsonIgnore
    @JsonProperty("float_value")
    public Object getFloat_value() {
        return this.float_value; }
    public void setFloat_value(Object float_value) {
        this.float_value = float_value; }
    private Object float_value;

    @JsonIgnore
    @JsonProperty("char_value")
    public String getChar_value() {
        return this.char_value; }
    public void setChar_value(String char_value) {
        this.char_value = char_value; }
    private String char_value;

    @JsonIgnore
    @JsonProperty("text_value")
    public Object getText_value() {
        return this.text_value; }
    public void setText_value(Object text_value) {
        this.text_value = text_value; }
    private Object text_value;

    @JsonIgnore
    @JsonProperty("datetime_value")
    public Object getDatetime_value() {
        return this.datetime_value; }
    public void setDatetime_value(Object datetime_value) {
        this.datetime_value = datetime_value; }
    private Object datetime_value;

    @JsonIgnore
    @JsonProperty("option_value")
    public Object getOption_value() {
        return this.option_value; }
    public void setOption_value(Object option_value) {
        this.option_value = option_value; }
    private Object option_value;

    @JsonIgnore
    @JsonProperty("email_value")
    public String getEmail_value() {
        return this.email_value; }
    public void setEmail_value(String email_value) {
        this.email_value = email_value; }
    private String email_value;

    @JsonIgnore
    @JsonProperty("phone_value")
    public String getPhone_value() {
        return this.phone_value; }
    public void setPhone_value(String phone_value) {
        this.phone_value = phone_value; }
    private String phone_value;

    @JsonIgnore
    @JsonProperty("url_value")
    public String getUrl_value() {
        return this.url_value; }
    public void setUrl_value(String url_value) {
        this.url_value = url_value;
            }
    private String url_value;

    @JsonIgnore
    @JsonProperty("url_visible_value")
    public Object getUrl_visible_value() {
        return this.url_visible_value; }
    public void setUrl_visible_value(String url_visible_value) {
        this.url_visible_value = url_visible_value; }
    private String url_visible_value;

    @JsonIgnore
    @JsonProperty("contact_person_value")
    public Object getContact_person_value() {
        return this.contact_person_value; }
    public void setContact_person_value(Object contact_person_value) {
        this.contact_person_value = contact_person_value; }
    private Object contact_person_value;

    @JsonIgnore
    @JsonProperty("responsible_value")
    public Object getResponsible_value() {
        return this.responsible_value; }
    public void setResponsible_value(Object responsible_value) {
        this.responsible_value = responsible_value; }
    private Object responsible_value;

    @JsonIgnore
    @JsonProperty("icon_option_value")
    public Object getIcon_option_value() {
        return this.icon_option_value; }
    public void setIcon_option_value(Object icon_option_value) {
        this.icon_option_value = icon_option_value; }
    private Object icon_option_value;

    @JsonIgnore
    @JsonProperty("outstanding")
    public boolean getOutstanding() {
        return this.outstanding; }
    public void setOutstanding(boolean outstanding) {
        this.outstanding = outstanding; }
    boolean outstanding;

    @JsonIgnore
    @JsonProperty("description")
    public String getDescription() {
        return this.description; }
    public void setDescription(String description) {
        this.description = description; }
    private String description;

    @JsonIgnore
    @JsonProperty("attribute")
    public int getAttribute() {
        return this.attribute; }
    public void setAttribute(int attribute) {
        this.attribute = attribute; }
    private int attribute;

    @JsonIgnore
    @JsonProperty("category_id")
    public String getCategory_id() {
        return this.category_id; }
    public void setCategory_id(String category_id) {
        this.category_id = category_id; }
    private String category_id;

    @JsonIgnore
    @JsonProperty("category_name")
    public String getCategory_name() {
        return this.category_name; }
    public void setCategory_name(String category_name) {
        this.category_name = category_name; }
    private String category_name;

    @JsonIgnore
    @JsonProperty("attribute_name")
    public String getAttribute_name() {
        return this.attribute_name; }
    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name; }
    private String attribute_name;

    @JsonIgnore
    @JsonProperty("attribute_type")
    public String getAttribute_type() {
        return this.attribute_type; }
    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type; }
    private String attribute_type;

    @JsonIgnore
    @JsonProperty("option_value_data")
    public Object getOption_value_data() {
        return this.option_value_data; }
    public void setOption_value_data(Object option_value_data) {
        this.option_value_data = option_value_data; }
    private Object option_value_data;

    @JsonIgnore
    @JsonProperty("icon_option_value_data")
    public Object getIcon_option_value_data() {
        return this.icon_option_value_data; }
    public void setIcon_option_value_data(Object icon_option_value_data) {
        this.icon_option_value_data = icon_option_value_data; }
    private Object icon_option_value_data;

    @JsonIgnore
    @JsonProperty("responsible_title")
    public Object getResponsible_title() {
        return this.responsible_title; }
    public void setResponsible_title(Object responsible_title) {
        this.responsible_title = responsible_title; }
    private Object responsible_title;

    @JsonIgnore
    @JsonProperty("responsible_jobtitle")
    public Object getResponsible_jobtitle() {
        return this.responsible_jobtitle; }
    public void setResponsible_jobtitle(Object responsible_jobtitle) {
        this.responsible_jobtitle = responsible_jobtitle; }
    private Object responsible_jobtitle;
}
