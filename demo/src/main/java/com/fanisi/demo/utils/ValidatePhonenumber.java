package com.fanisi.demo.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Component;

@Component
public class ValidatePhonenumber {

    public boolean validatePhoneNumber(String msisdn, String country)
    {
        boolean valid = false;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try
        {
            Phonenumber.PhoneNumber userMsisdn = phoneUtil.parse(msisdn, country);
            valid = phoneUtil.isValidNumberForRegion(userMsisdn,country);
        }
        catch (NumberParseException e)
        {
            System.err.println("NumberParseException was thrown: " + e);
        }

        return valid;
    }

    public String formatNumber(String msisdn) throws Exception
    {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber userMsisdn = phoneUtil.parse(msisdn, "KE");
        String format = phoneUtil.format(userMsisdn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        String formated = format.replace("+","");
        return formated.replace(" ","");
    }

    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
