package com.example.carnaticapp.firebasefirstore;


public class Artist {
    private String userId;
    private String firstName;
    private String lastName;
    private String teacherName;
    private String schoolName;
    private String category;
    private String gender;
    private String contact;

    public Artist(){}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Artist(String userId, String firstName, String lastName, String contact, String teacherName, String schoolName, String gender, String category){
        this.userId = userId;
        this.schoolName = schoolName;
        this.teacherName = teacherName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.contact = contact;
        this.category = category;
    }

    public Artist(String userId){
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId(){
        return  userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getCategory() {
        return category;
    }

    public String getGender() {
        return gender;
    }
}
