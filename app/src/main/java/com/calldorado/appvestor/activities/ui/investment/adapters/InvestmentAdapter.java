package com.calldorado.appvestor.activities.ui.investment.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.calldorado.appvestor.R;
import com.calldorado.appvestor.data.db.entity.Investments;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvestmentAdapter extends RecyclerView.Adapter<InvestmentAdapter.InvestmentViewHolder>{

    private List<Investments> data;
    private Context context;
    private LayoutInflater layoutInflater;

    private DateFormat originalFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss", Locale.ENGLISH);
    private DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

    public InvestmentAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public InvestmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.app_item, parent, false);
        return new InvestmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InvestmentViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Investments> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            // first initialization
            data = newData;
        }
        notifyDataSetChanged();
    }

    class InvestmentViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView color;
        TextView investment;
        TextView date;
        TextView revenue;
        TextView status;
        TextView name;


        InvestmentViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_card);
            investment = itemView.findViewById(R.id.tv_amount);
            date = itemView.findViewById(R.id.tv_date);
            revenue = itemView.findViewById(R.id.tv_rev);
            status = itemView.findViewById(R.id.status);
            name = itemView.findViewById(R.id.tv_title);
            color = itemView.findViewById(R.id.img_status);

        }

        void bind(final Investments investments) {
            if (investments != null) {
                Picasso.get().load("https://myappinvestor.calldorado.com/Images/applicationicons/"+investments.getApplication_package_()+".png").into(imageView);

                investment.setText("€ "+investments.getAmount_invested_());
                Date dateNew;
                try {
                    dateNew = originalFormat.parse(investments.getCreated_());
                    String formattedDate = targetFormat.format(dateNew);
                    date.setText(formattedDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                revenue.setText("€ "+investments.getAmount_earned_());
                status.setText(investments.getState_());
                name.setText(investments.getApplication_name_());
                color.setColorFilter(Color.parseColor(investments.getColor_()));
            }
        }

    }

}
