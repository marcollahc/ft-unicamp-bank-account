/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author marcos-medeiros
 */
public class Validation {
    public static boolean cpf(String content) {
        int firstCheckNumber = 0;
        int calcFirstCheckNumber = 0;
        int secondCheckNumber = 0;
        int calcSecondCheckNumber = 0;
        
        if (content.length() != 11) {
            return false;
        }
        
        String contentNineWithoutCheckNumber = content.substring(0, 9);
        String checkNumber = content.substring(9, 11);        
        
        for (int i = 0, j = 10; i < 9; i++, j--) {
            calcFirstCheckNumber = calcFirstCheckNumber + (Integer.valueOf(contentNineWithoutCheckNumber.substring(i, i + 1)) * j);
        }        
        
        int modCalcFirstCheckNumber = calcFirstCheckNumber % 11;
        
        if (modCalcFirstCheckNumber < 2) {
            firstCheckNumber = 0;
        } else {
            firstCheckNumber = 11 - modCalcFirstCheckNumber;
        }
        
        String contentElevenWithoutCheckNumber = contentNineWithoutCheckNumber.concat(String.valueOf(firstCheckNumber));
        
        for (int i = 0, j = 11; i < 10; i++, j--) {
            calcSecondCheckNumber = calcSecondCheckNumber + (Integer.valueOf(contentElevenWithoutCheckNumber.substring(i, i + 1)) * j);
        }
        
        int modCalcSecondCheckNumber = calcSecondCheckNumber % 11;
        
        if (modCalcSecondCheckNumber < 2) {
            secondCheckNumber = 0;
        } else {
            secondCheckNumber = 11 - modCalcSecondCheckNumber;
        }
        
        String fullContentCalculated = contentElevenWithoutCheckNumber.concat(String.valueOf(secondCheckNumber));
        
        System.out.println("content: " + content + " | fullContentCalculated: " + fullContentCalculated);
        
        if (!content.equals(fullContentCalculated)) {
            return false;
        }
        
        return true;
    }
}
