/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.programalady1;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author ladyl
 */
public class aritmetica {

    Scanner leer = new Scanner(System.in);

    public aritmetica() {
        System.out.println("Escribe una expresión: ");

        String infi = leer.nextLine();
        String infix = espacios(infi);

        System.out.printf("Postfija: %s\n", infixToPostfix(infix));

        String[] post = infixToPostfix(infix).split(" ");

        //declaracion de pilas
        Stack< String> entrada = new Stack< String>(); //pila entrada
        Stack< String> op = new Stack< String>(); //pila operandos

        for (int i = post.length - 1; i >= 0; i--) {//ingresa los elementos a la pila
            entrada.push(post[i]);
        }

        String operadores = "+-*/%^";
        while (!entrada.isEmpty()) { //separa y organiza las op en las pilas
            if (operadores.contains("" + entrada.peek())) {

                op.push(evaluar(entrada.pop(), op.pop(), op.pop()) + ""); //se realiza la operación

            } else {
                op.push(entrada.pop());
            }
        }

        System.out.println("Resultado: " + op.peek());//imprime el resultado devolviendo el ultimo elemento de la pila

    }

    static String infixToPostfix(String infix) { //separar y ordena

        final String ops = "-+/*^";//operadores

        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty()) {
                continue;
            }
            char c = token.charAt(0);
            int idx = ops.indexOf(c);
            if (idx != -1) {
                if (s.isEmpty()) {
                    s.push(idx);
                } else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^')) {
                            sb.append(ops.charAt(s.pop())).append(' ');
                        } else {
                            break;
                        }
                    }
                    s.push(idx);
                }
            } else if (c == '(') {
                s.push(-2);
            } else if (c == ')') {

                while (s.peek() != -2) {
                    sb.append(ops.charAt(s.pop())).append(' ');
                }
                s.pop();
            } else {
                sb.append(token).append(' ');
            }
        }
        while (!s.isEmpty()) {
            sb.append(ops.charAt(s.pop())).append(' ');
        }
        return sb.toString();
    }

    private static double evaluar(String op, String n2, String n1) {
        double num1 = Double.parseDouble(n1);
        double num2 = Double.parseDouble(n2);
        if (op.equals("+")) {
            return (num1 + num2);
        }
        if (op.equals("-")) {
            return (num1 - num2);
        }
        if (op.equals("*")) {
            return (num1 * num2);
        }
        if (op.equals("/")) {
            return (num1 / num2);
        }
        if (op.equals("%")) {
            return (num1 % num2);
        }
        if (op.equals("^")) {
            return (Math.pow(num1, num2));
        }

        return 0;
    }

    private static String espacios(String exp) {

        String simbols = "+-*/()^";
        String expresion = "";

        for (int i = 0; i < exp.length(); i++) {
            if (simbols.contains("" + exp.charAt(i))) {
                expresion += " " + exp.charAt(i) + " ";
            } else {
                expresion += exp.charAt(i);
            }
        }
        return expresion.replaceAll("\\s+", " ").trim();
    }

}
