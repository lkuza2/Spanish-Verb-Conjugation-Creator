package com.darkprograms.spanishconj;

import com.darkprograms.spanishconj.document.WriteToDocument;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 6/6/12
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String args[]) {
        System.out.println("Verb Conjugator Generator");
        System.out.println("By Luke Kuza v1.00");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter path to Verb List: ");
        String verbList = scanner.nextLine();

        System.out.println("Enter path to output Word Doc: ");
        String outputDoc = scanner.nextLine();
        System.out.println();

        new WriteToDocument().start(verbList, outputDoc);
    }

}
