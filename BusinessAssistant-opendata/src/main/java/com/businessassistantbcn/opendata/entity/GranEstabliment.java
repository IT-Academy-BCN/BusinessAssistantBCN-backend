package com.businessassistantbcn.opendata.entity;


import java.util.Date;

// this entity class we will use just for converting the JSON into Java object
public class GranEstabliment {

    private long id;
    private String name;
    private String surnames;
    private int idCountry;
    private String workplace;
    private Date registerDate;
    private byte idGender;
    private boolean specialExpedient;
    private String documentTypeNumber;
    private boolean registered;
    private byte idProvince;
    private short idCity;

    public GranEstabliment() {
    }

    public GranEstabliment(String name, String surnames, int idCountry, String workplace, Date registerDate, byte idGender, boolean specialExpedient, String documentTypeNumber, boolean registered, byte idProvince, short idCity) {
        this.name = name;
        this.surnames = surnames;
        this.idCountry = idCountry;
        this.workplace = workplace;
        this.registerDate = registerDate;
        this.idGender = idGender;
        this.specialExpedient = specialExpedient;
        this.documentTypeNumber = documentTypeNumber;
        this.registered = registered;
        this.idProvince = idProvince;
        this.idCity = idCity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public byte getIdGender() {
        return idGender;
    }

    public void setIdGender(byte idGender) {
        this.idGender = idGender;
    }

    public boolean isSpecialExpedient() {
        return specialExpedient;
    }

    public void setSpecialExpedient(boolean specialExpedient) {
        this.specialExpedient = specialExpedient;
    }

    public String getDocumentTypeNumber() {
        return documentTypeNumber;
    }

    public void setDocumentTypeNumber(String documentTypeNumber) {
        this.documentTypeNumber = documentTypeNumber;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public byte getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(byte idProvince) {
        this.idProvince = idProvince;
    }

    public short getIdCity() {
        return idCity;
    }

    public void setIdCity(short idCity) {
        this.idCity = idCity;
    }

    @Override
    public String toString() {
        return "GranEstabliment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surnames='" + surnames + '\'' +
                ", idCountry=" + idCountry +
                ", workplace='" + workplace + '\'' +
                ", registerDate=" + registerDate +
                ", idGender=" + idGender +
                ", specialExpedient=" + specialExpedient +
                ", documentTypeNumber='" + documentTypeNumber + '\'' +
                ", registered=" + registered +
                ", idProvince=" + idProvince +
                ", idCity=" + idCity +
                '}';
    }
}