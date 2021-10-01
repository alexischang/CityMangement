package com.company;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    Scanner sc = new Scanner(System.in);

    public int numberInput(int min, int max) {
        int input = 0;
        try {
            input = sc.nextInt();
            if (input < min || input > max) {
                System.out.println("請輸入正確數字");
                return numberInput(min, max);
            }
        } catch (InputMismatchException e) {
            System.out.println("請輸入正確數字");
            sc = new Scanner(System.in);
            return numberInput(min, max);
        }
        return input;
    }

    //    public int buildingUpgradeNumber() {
//        int input = 0;
//        try {
//            input = sc.nextInt();
//            if (input != 1 || input != 3 || input != 4 || input != 5 || input != 7 || input != 8) { //只有這些建築可以升級
//                System.out.println("請輸入正確數字");
//                return buildingUpgradeNumber();
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("請輸入正確數字");
//            return buildingUpgradeNumber();
//        }
//        return input;
//    }
//
    public int openCloseNumber() {
        int input = 0;
        try {
            input = sc.nextInt();
            if (input != 1 && input != 3 && input != 8) { //只有這些建築可以主動開關
                System.out.println("請輸入正確數字");
                return openCloseNumber();
            }
        } catch (InputMismatchException e) {
            System.out.println("請輸入正確數字");
            sc = new Scanner(System.in);
            return openCloseNumber();
        }
        return input;
    }
}
