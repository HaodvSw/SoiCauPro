package com.toolfindxs.xsmb_soicau.utils;

import android.content.Context;

import com.toolfindxs.xsmb_soicau.MainApp;
import com.toolfindxs.xsmb_soicau.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GenRequestParam {

    public static String getLocal(Context context, String localSelect){
        String local = "";
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = sdf.format(MainApp.getInstance().getDateSelect().toDate());
        String[] localList = context.getResources().getStringArray(R.array.localArray);
        switch (dayOfTheWeek){
            case Constants.T_2:
                if (localSelect.equals(localList[1])) local = "SFC, FAC";
                else if (localSelect.equals(localList[2])) local = "FZMSC, TTC, JOC";
                else local = "hnc"; // ha noi
                break;
            case Constants.T_3:
                if (localSelect.equals(localList[1])) local = "DOLC, GNC";
                else if (localSelect.equals(localList[2])) local = "BZC, TDC, BLC";
                else local = "gnic"; // Quang Ninh gnic
                break;
            case Constants.T_4:
                if (localSelect.equals(localList[1])) local = "XXC, QHC";
                else if (localSelect.equals(localList[2])) local = "TNC, JYC, SZC";
                else local = "bnc"; // Bac ninh
                break;
            case Constants.T_5:
                if (localSelect.equals(localList[1])) local = "PDC, GZC, GPC";
                else if (localSelect.equals(localList[2])) local = "XLC, AJC, PSC";
                else local = "hnc"; // Ha Noi
                break;
            case Constants.T_6:
                if (localSelect.equals(localList[1])) local = "JLC, LSC";
                else if (localSelect.equals(localList[2])) local = "YLC, PYC, CRC";
                else local = "HFC"; // Hai Phong
                break;
//            case Constants.T_7:
//                if (localSelect.equals(localList[1])) local = "XXC, GYC, DNC";
//                else if (localSelect.equals(localList[2])) local = "FZMSC, LAC, PFC, HJC";
//                else local = "ndc"; // Nam Dinh
//                break;
            case Constants.CN:
                if (localSelect.equals(localList[1])) local = "QHC, KGC, SFC";
                else if (localSelect.equals(localList[2])) local = "QJC, JJC, DLC";
                else local = "tpc"; // Thai Binh
                break;
        }
        return local.replace(" ", "");
    }

    private static String checkLocal(Context context, String localSelect){
        String[] localList = context.getResources().getStringArray(R.array.localArray);
        if (localSelect.equals(localList[1])) return "hnc";
        if (localSelect.equals(localList[2])) return "hc";
        else return "hnc";
    }

    public static Date getDate(boolean isAfter){
//        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        if (!isAfter) return new Date();
        return cal.getTime();
    }

    public static String getDateTitle(String dateString){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

    public static String convertNameProvince(String value){
        String nameProvince = "n/n";
        switch (value){
            case "FZMSC": nameProvince = Constants.FZMSC;
                break;
            case "AJC": nameProvince = Constants.AJC;
                break;
            case "BLC": nameProvince = Constants.BLC;
                break;
            case "BZC": nameProvince = Constants.BZC;
                break;
            case "PYC": nameProvince = Constants.PYC;
                break;
            case "PFC": nameProvince = Constants.PFC;
                break;
            case "PSC": nameProvince = Constants.PSC;
                break;
            case "JOC": nameProvince = Constants.JOC;
                break;
            case "JYC": nameProvince = Constants.JYC;
                break;
            case "DLC": nameProvince = Constants.DLC;
                break;
            case "TNC": nameProvince = Constants.TNC;
                break;
            case "TTC": nameProvince = Constants.TTC;
                break;
            case "HJC": nameProvince = Constants.HJC;
                break;
            case "JJC": nameProvince = Constants.JJC;
                break;
            case "LAC": nameProvince = Constants.LAC;
                break;
            case "SZC": nameProvince = Constants.SZC;
                break;
            case "XLC": nameProvince = Constants.XLC;
                break;
            case "QJC": nameProvince = Constants.QJC;
                break;
            case "CRC": nameProvince = Constants.CRC;
                break;
            case "YLC": nameProvince = Constants.YLC;
                break;
            case "TDC": nameProvince = Constants.TDC;
                break;
            case "PDC": nameProvince = Constants.PDC;
                break;
            case "XXC": nameProvince = Constants.XXC;
                break;
            case "DOLC": nameProvince = Constants.DOLC;
                break;
            case "DNC": nameProvince = Constants.DNC;
                break;
            case "JLC": nameProvince = Constants.JLC;
                break;
            case "QHC": nameProvince = Constants.QHC;
                break;
            case "KGC": nameProvince = Constants.KGC;
                break;
            case "LSC": nameProvince = Constants.LSC;
                break;
            case "GPC": nameProvince = Constants.GPC;
                break;
            case "GNC": nameProvince = Constants.GNC;
                break;
            case "GYC": nameProvince = Constants.GYC;
                break;
            case "GZC": nameProvince = Constants.GZC;
                break;
            case "FAC": nameProvince = Constants.FAC;
                break;
            case "SFC": nameProvince = Constants.SFC;
                break;
        }
        return nameProvince;
    }
}
