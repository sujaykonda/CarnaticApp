package com.example.carnaticapp.firebasefirstore;


public class Artist {
    private String userId;
    private String firstName;
    private String lastName;
    private String teacherName;
    private String schoolName;
    private String category;
    private String gender;

    public Artist(){}

    public String getLastName() {
        return lastName;
    }

    public Artist(String userId, String firstName, String lastName, String contact, String teacherName, String schoolName, String gender, String category){
        this.userId = userId;
        this.schoolName = schoolName;
        this.teacherName = teacherName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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
