package com.calldorado.appvestor.activities.ui.investment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.calldorado.appvestor.R;
import com.calldorado.appvestor.MainActivity;
import com.calldorado.appvestor.activities.ui.investment.adapters.InvestmentAdapter;
import com.calldorado.appvestor.activities.ui.investment.interfaces.RevenueInterface;
import com.calldorado.appvestor.activities.ui.investment.interfaces.WalletInterface;
import com.calldorado.appvestor.data.db.entity.GraphItem;
import com.calldorado.appvestor.data.models.DataPoints;
import com.calldorado.appvestor.data.models.DataPointsHolder;
import com.calldorado.appvestor.data.models.GraphItems;
import com.calldorado.appvestor.data.db.entity.Investments;
import com.calldorado.appvestor.utils.ChartValueFomatter2;
import com.calldorado.appvestor.utils.ChartValueFormatter;
import com.calldorado.appvestor.data.db.network.InvestmentTask;
import com.calldorado.appvestor.data.db.network.RevenueTask;
import com.calldorado.appvestor.data.db.network.WalletTask;
import com.calldorado.appvestor.utils.TimerUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.Context.MODE_PRIVATE;


public class InvestmentFragment extends Fragment {

    private static final String TAG = "InvestmentFragment";


    private InvestmentViewModel investmentViewModel;
    private GraphItemViewModel graphItemViewModel;
    private InvestmentAdapter investmentAdapter;
    private LineChart chart;
    private CardView mDashboardOverView;
    private CardView mChartOverView;
    private Typeface tf;
    SharedPreferences mSharedPreferences;

    private ArrayList<LineDataSet> lineData = new ArrayList<>();
    private ArrayList<DataPointsHolder> myDataPointsArrayList = new ArrayList<>();

    private List<Investments> investmentsList = new ArrayList<>();
    private List<Investments> investments = new ArrayList<>();

    final private String[] dates = new String[30];

    //wallet
    TextView mTextViewRevenue;
    TextView mTextViewCredit;
    TextView mTextViewHold;

    ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mSharedPreferences = getActivity().getSharedPreferences("UserLoginState", Activity.MODE_PRIVATE);

        View root = inflater.inflate(R.layout.fragment_investment, container, false);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        mDashboardOverView = root.findViewById(R.id.include);
        mChartOverView = root.findViewById(R.id.include2);

        if(!mSharedPreferences.getBoolean("USE_BIOMETRIC_SHOWN", false)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Biometric login?")
                    .setMessage("Do you want to use a Biometric login next time?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mSharedPreferences.edit().putBoolean("USE_BIOMETRIC", true).commit();
                            mSharedPreferences.edit().putBoolean("USE_BIOMETRIC_SHOWN", true).commit();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mSharedPreferences.edit().putBoolean("USE_BIOMETRIC", false).commit();
                            mSharedPreferences.edit().putBoolean("USE_BIOMETRIC_SHOWN", true).commit();

                        }
                    })
                    .show();
        }


        investmentAdapter = new InvestmentAdapter(getContext());

        investmentViewModel = ViewModelProviders.of(getActivity()).get(InvestmentViewModel.class);
        graphItemViewModel = ViewModelProviders.of(getActivity()).get(GraphItemViewModel.class);


        // Check time elapsed
            // time has elapsed
        final boolean[] isdoneG = {false};
        final int[] sizeG = {0};
        final boolean[] weGotDataa = {false};

        Log.d(TAG, "onCreateView: " + TimerUtil.hasTimePassed(getContext()));
        if(TimerUtil.hasTimePassed(getContext())) {
            new InvestmentTask(getContext()).InvestmentRequest(mSharedPreferences.getString("Id", ""), data -> {
                if (!MainActivity.hasRun) investmentViewModel.deleteAll();
                graphItemViewModel.deleteAll();
                Log.d(TAG, "onCreateView: " + data.size());
                for (Investments investments : data) {
                    if (!MainActivity.hasRun) {
                        investmentViewModel.savePost(investments);
                    } else {
                        investmentViewModel.updateInvestment(investments);
                    }
                }
                MainActivity.hasRun = true;
                investmentsList.clear();
                investmentsList = data;
                new RevenueTask(getContext()).RevenueRequest(mSharedPreferences.getString("Id", ""), dateArrayListMap -> {
                    sizeG[0] = dateArrayListMap.size();
                    graphItemViewModel.savegraph(dateArrayListMap);
                    isdoneG[0] = true;
                    getActivity().getSharedPreferences("hasTimePassed", MODE_PRIVATE).edit().putLong("time", Calendar.getInstance().getTimeInMillis()).commit();
                });
            });
        }else{
            investmentsList.clear();
            investmentsList = investmentViewModel.getAllInvestments().getValue();
            weGotDataa[0] = true;
        }


        investmentViewModel.getAllInvestments().observe(getActivity(), posts -> {
            investmentAdapter.setData(posts);
            investmentsList = posts;
            if(weGotDataa[0]) createGraphData();
        });
        graphItemViewModel.getAllByIdAndDate().observe(getActivity(), graphItems -> {
            if(isdoneG[0] && graphItems.size() == sizeG[0] && !weGotDataa[0] && graphItems.size()> 0){
                createGraphData();
            }

        });

        setUpWallet();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(investmentAdapter);

        Log.d(TAG, "onCreateView: "+ investmentViewModel.getAllInvestments());



        chart = mChartOverView.findViewById(R.id.chart);
        chart.setNoDataText("Getting data from server...");

       /* Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);*/

        mChartOverView.findViewById(R.id.imageView2).setOnClickListener(view -> {
            toggleVisibility();
        });

        return root;
    }

    private void toggleVisibility() {
        if (chart.getVisibility() == View.GONE || chart.getVisibility() == View.INVISIBLE) {
            mChartOverView.findViewById(R.id.imageView2).animate().rotation(180f).start();
            TransitionManager.beginDelayedTransition(mChartOverView , new AutoTransition());
            chart.setVisibility(View.VISIBLE);
        }
        else {
            chart.setVisibility(View.GONE);

            mChartOverView.findViewById(R.id.imageView2).animate().rotation(0f).start();

            TransitionManager.beginDelayedTransition(mChartOverView, new AutoTransition());
        }
    }


    private void setUpWallet(){

        mTextViewRevenue = mDashboardOverView.findViewById(R.id.textView2);
        mTextViewCredit = mDashboardOverView.findViewById(R.id.textView5);
        mTextViewHold = mDashboardOverView.findViewById(R.id.textView7);
        progressBar = mDashboardOverView.findViewById(R.id.progressBarMain);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        new WalletTask(getContext()).WalletRequest(mSharedPreferences.getString("Id", ""), new WalletInterface() {
            @Override
            public void onDataDone(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    progressBar.setVisibility(View.GONE);

                    mTextViewRevenue.setText("€ " +jsonObject.getString("AmountRevenueAll"));
                    mTextViewCredit.setText("€ " +jsonObject.getString("AmountAvailable"));
                    mTextViewHold.setText("€ " +jsonObject.getString("AmountOnHold"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String response) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });



    }

    private void createGraphData() {


        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM");

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        List<String> iDs = graphItemViewModel.getIvenstmentIds();
        Log.d(TAG, "createGraphData: " + iDs);
        lineData.clear();

        for(int i = 0; i < iDs.size();i++) {
            List<GraphItem> graphItemList = graphItemViewModel.getAllByIdAndDate(iDs.get(i));
            Log.d(TAG, "createGraphData: " + graphItemList.size());
            List<Entry> valsComp = new ArrayList<>();

            for(int j = 0; j < graphItemList.size(); j++){
                Entry c1e1 = new Entry(j,Float.parseFloat(graphItemList.get(j).getAmount_()));
                dates[j] = simpleDate.format(new Date(graphItemList.get(j).getDate_()));

                Log.d(TAG, "createGraphData: " + graphItemList.get(j).getDate_() + " : " + graphItemList.get(j).getAmount_());

                valsComp.add(c1e1);

            }
            LineDataSet setComp;
            if(i == 0){
                setComp = new LineDataSet(valsComp, "Total");
            }else{
                setComp = new LineDataSet(valsComp, investmentsList.get(i-1).getApplication_name_());
            }
            lineData.add(setComp);


        }


        Log.d(TAG, "createGraphData: " + lineData.size());

        for (int i = 0; i < lineData.size(); i++) {
            LineDataSet lineDataSet = lineData.get(i);
            if(i == 0){
                lineDataSet.setColor(Color.parseColor("#000000"));
                lineDataSet.setCircleColor(Color.parseColor("#000000"));
                lineDataSet.setCircleHoleColor(Color.parseColor("#000000"));
            }
            else{
                lineDataSet.setColor(Color.parseColor(investmentsList.get(i-1).getColor_()));
                lineDataSet.setCircleColor(Color.parseColor(investmentsList.get(i-1).getColor_()));
                lineDataSet.setCircleHoleColor(Color.parseColor(investmentsList.get(i-1).getColor_()));
            }

            dataSets.add(lineDataSet);
        }

        createGraph(dataSets);


    }

    /**
     * Method using the data map provided by above method to draw graph
     *
     * @param dataSets
     */
    private void createGraph(List<ILineDataSet> dataSets) {

        Log.d(TAG, "dataset size = " + dataSets.size());

        XAxis xAxis = chart.getXAxis();
        YAxis yAxis = chart.getAxisRight();
        chart.getXAxis().setValueFormatter(new ChartValueFomatter2(dates));
        chart.getAxisLeft().setValueFormatter(new ChartValueFormatter());

        yAxis.setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-40);
        xAxis.setLabelCount(15, true);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setAxisMinimum(-0.2f);

        yAxis.setGranularity(2f); // minimum axis-step (interval) is 1
        xAxis.setGranularityEnabled(true);

        LineData data = new LineData(dataSets);
        chart.getDescription().setEnabled(false);
        chart.setData(data);
        chart.getLegend().setEnabled(true);
        chart.invalidate();

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setYOffset(5f);

        /*int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            for (int i = 0; i < lineData.size(); i++) {
                lineData.get(i).setValueTextSize(10);
            }
        } else {
            for (int i = 0; i < lineData.size(); i++) {
                lineData.get(i).setValueTextSize(8);
            }
        }*/
        chart.setVisibleXRangeMaximum(10);
        chart.moveViewToX(30);

    }

    /**
     * Method generating graph data from data in map used to draw graph
     *
     * @param map
     */



}