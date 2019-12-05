package com.calldorado.appvestor.utils;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.calldorado.appvestor.activities.ui.investment.GraphItemViewModel;
import com.calldorado.appvestor.activities.ui.investment.InvestmentViewModel;
import com.calldorado.appvestor.data.db.entity.GraphItem;
import com.calldorado.appvestor.data.models.GraphItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * JsonUtil
 * Helper class for converting response data to usable data
 */
public class JsonUtil {

    private static  final String TAG = JsonUtil.class.getSimpleName();

    /**
     * Takes String reponse from server and converts it to usable data and returns a Map with the needed data for the graph
     * @param response
     * @param startDate
     * @param endDate
     * @return
     */

    public static Map<Date, ArrayList<GraphItems>> readInvestmentResponse(Context context, String response, String startDate, String endDate){


        Map<Date, ArrayList<GraphItems>> myGrafItems = new TreeMap<Date, ArrayList<GraphItems>>(new DataComparator());
        List<Date> listDates = createListOfdatesBetweenTwoDates(startDate,endDate);

        listDates.remove(listDates.size()-1);

        Log.d(TAG, "readInvestmentResponse: 1" + "listDates : " + listDates.size());

        try {

            JSONArray jsonArray = new JSONArray(response);
            Log.d(TAG, "readInvestmentResponse: 2" + "JsonArray : " + jsonArray.length());
            for (int k = 0; k <= jsonArray.length(); k++) { //dato loop

                JSONObject wallet = jsonArray.getJSONObject(k);
                Log.d(TAG, "readInvestmentResponse: wallet :" + wallet);

                JSONArray wallet_array = wallet.getJSONArray("wallet");
                Log.d(TAG, "readInvestmentResponse: wallet_array :" + wallet);


                ArrayList<GraphItems> grafSet = new ArrayList<>();

                for (int i = 0; i < wallet_array.length(); i++){

                    JSONObject wallet_id = wallet_array.getJSONObject(i);
                    JSONArray investment_array = wallet_id.getJSONArray("investor-investment");

                    for (int j = 0; j < investment_array.length(); j++) {

                        JSONObject test = investment_array.getJSONObject(j);

                        JSONArray campaign_array = test.getJSONArray("campaign");

                        JSONObject test2 = campaign_array.getJSONObject(0);

                        Log.d(TAG, "readInvestmentResponse: " + test.get("investor-investment-id").toString());
                        Log.d(TAG, "readInvestmentResponse: " + test2.get("revenue").toString());

                        GraphItems grafItems = new GraphItems();
                        grafItems.setInvestment_id(test.get("investor-investment-id").toString());
                        grafItems.setAmount(test2.get("revenue").toString());
                        grafSet.add(grafItems);

                    }
                   /* GraphItems grafItems = new GraphItems();

                        grafItems.setInvestment_id(temp2.get("investor-investment-id").toString());
                        JSONArray campaign = temp2.getJSONArray("campaign");

                        grafItems.setAmount(campaign.getJSONObject(0).get("revenue").toString());
                        grafSet.add(grafItems);*/
                }

                myGrafItems.put(listDates.get(k),grafSet);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return myGrafItems;
    }

    public static ArrayList<GraphItem> readInvestmentResponse1(Context context, String response, String startDate, String endDate){

        ArrayList<GraphItem> myGrafItems = new ArrayList<>();
        List<Date> listDates = createListOfdatesBetweenTwoDates(startDate,endDate);

        listDates.remove(listDates.size()-1);

        Log.d(TAG, "readInvestmentResponse: 1" + "listDates : " + listDates.size());
        Log.d(TAG, "readInvestmentResponse1: " + response);
        try {

            JSONArray jsonArray = new JSONArray(response);
            Log.d(TAG, "readInvestmentResponse: 2" + "JsonArray : " + jsonArray.length());
            for (int k = 0; k < jsonArray.length(); k++) { //dato loop

                JSONObject wallet = jsonArray.getJSONObject(k);

                JSONArray wallet_array = wallet.getJSONArray("wallet");

                Log.d(TAG, "readInvestmentResponse1: " + wallet_array.toString());
                for (int i = 0; i < wallet_array.length(); i++){

                    JSONObject wallet_id = wallet_array.getJSONObject(i);
                    JSONArray investment_array = wallet_id.getJSONArray("investor-investment");

                    for (int j = 0; j < investment_array.length(); j++) {

                        JSONObject test = investment_array.getJSONObject(j);

                        JSONArray campaign_array = test.getJSONArray("campaign");

                        JSONObject test2 = campaign_array.getJSONObject(0);


                        /*grafItemsGraphItems grafItems = new GraphItems();
                        grafItems.setInvestment_id(test.get("investor-investment-id").toString());
                        grafItems.setAmount(test2.get("revenue").toString());
                        grafSet.add(grafItems);*/
                        myGrafItems.add(new GraphItem(listDates.get(k).toString(), test.get("investor-investment-id").toString(), test2.get("revenue").toString(), "qwe"));
                    }


                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return myGrafItems;
    }

    /**
     * Creates and list of datas used for the readInvestmentResponse to create the correct map with dates.
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> createListOfdatesBetweenTwoDates(String startDate, String endDate){
        ArrayList<Date> dates = new ArrayList<>();
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");

        Date dateStart = null;
        Date dateEnd = null;

        try {
            dateStart = df1 .parse(startDate);
            dateEnd = df1 .parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dateStart);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dateEnd);

        while(!calStart.after(calEnd))
        {

            dates.add(calStart.getTime());
            calStart.add(Calendar.DATE, 1);
        }
        return dates;
    }

}
