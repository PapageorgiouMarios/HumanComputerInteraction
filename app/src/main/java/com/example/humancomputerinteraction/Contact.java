package com.example.humancomputerinteraction;

public class Contact
{
    private String first_name;
    private String last_name;
    private String phone_number;

    public Contact(String first_name, String last_name, String phone_number)
    {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
    }

    private void printStats()
    {
        System.out.println("Contact first name: " + first_name);
        System.out.println("Contact last name: " + last_name);
        System.out.println("Contact number: " + phone_number);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
