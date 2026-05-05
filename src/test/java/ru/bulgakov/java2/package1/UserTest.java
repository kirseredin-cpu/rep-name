package ru.bulgakov.java2.package1;

public class UserTest {
    public static void main(String[] args) {
     User user1 = new User();
     user1.setAge(user1.randomNumber());
        user1.setFirstName("yo");
        user1.setLastName("lamer");

        user1.greet();

        User user2 = new User();
        user2.setAge(23);
        user2.setFirstName("alibaba");
        user2.setLastName("mandor");

        user2.greet();

    }
}
