package com.happybaby.kcs.utils;

public class Util {

    final private static String CURRENCY_EURO = "EUR";
    final private static String CURRENCY_DOLLAR = "USD";

    final private static String CURRENCY_CHARACTER_EURO = "€";
    final private static String CURRENCY_CHARACTER_DOLLAR = "$";
    final private static String NO_CURRENCY = "";

    static public String getSymbol(String currency) {
        if(CURRENCY_EURO.equals(currency)){
            return CURRENCY_CHARACTER_EURO;
        } else if (CURRENCY_DOLLAR.equals(currency)){
            return CURRENCY_CHARACTER_DOLLAR;
        } else {
            return NO_CURRENCY;
        }
    }




}
