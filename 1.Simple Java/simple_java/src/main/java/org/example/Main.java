package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //sout - якщо введемо цю команду і нажмемо tab  то воно само напише
        //типи даних на Java
        //int,boolean,short,double,float,char,long,String
        //Щоб вводити дані треба створити об'єкт Scanner

        ExampleOne();
        ExampleTwo();


    }

    public static void ExampleOne(){
        System.out.println("Hello and welcome!");
        int age = 0;
        System.out.println("Вкажіть ваш вік");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        //Переводимо string в integer
        age = Integer.parseInt(str);
        System.out.println("Вам " + age + " років");
    }
    public static void  ExampleTwo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть розмір масиву");
        int n = Integer.parseInt(scanner.nextLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = GetRandom(1, 100);
        }
        for(var item : array) {
            System.out.print(item + "\t");
        }
    }

    public static int GetRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }


}