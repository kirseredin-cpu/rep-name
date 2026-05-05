package ru.bulgakov.java2.package1;


public class User {
    private String firstName;
    private String lastName;
    private Integer age;

    public Integer randomNumber() {
        return (int) (Math.random() * (65-18+1)) + 18;
    }


    public void greet() {
        System.out.println("My name " + getFirstName() + " " + getLastName() + "i mne " + getAge());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.length() > 2 && firstName.length() < 50) {
            this.firstName = firstName;
        } else {
            System.out.println("Не валидно");
        }
    }

        public String getLastName () {
            return lastName;
        }

        public void setLastName (String lastName){
            this.lastName = lastName;
        }

        public int getAge () {
            return age;
        }

        public void setAge (Integer age){
            if (age >= 18 && age <= 65) {
                this.age = age;
            } else {
                System.out.println("не получится");
            }
        }
    }