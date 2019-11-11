package com.example.asm6;
public class Contact {
    String firstName, lastName, phoneNumber, educationLevel, hobbies;

    public Contact (String first, String last, String phone, String edu, String hobbies) {
        this.firstName = first;
        this.lastName = last;
        this.phoneNumber = phone;
        this.educationLevel = edu;
        this.hobbies = hobbies;
    }

    public boolean Search (String term) {
        if ((this.firstName.equals(term)) || (this.lastName.equals(term)) || (this.phoneNumber.equals(term))) {
            return true;
        } else return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    @Override
    public String toString() {
        return "First Name: " + this.firstName + "\t Last Name: " + this.lastName + "\t Phone: " + this.phoneNumber + "\t Education: " + this.educationLevel + "\t Hobby: " + this.hobbies;
    }
}
