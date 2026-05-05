package ru.bulgakov.java2.package1;

public class CounterTest {

    public static void main(String[] args) {
        Counter counter1 = new Counter();
        counter1.increment();
        counter1.increment();
        counter1.increment();
        counter1.increment();

        System.out.println(counter1.getCount());

    Counter counter2 = new Counter();
        counter2.increment();
        counter2.increment();

        System.out.println(counter2.getCount());

}}
