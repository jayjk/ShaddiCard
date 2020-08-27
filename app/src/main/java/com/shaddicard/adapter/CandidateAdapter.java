package com.shaddicard.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shaddicard.R;
import com.shaddicard.model.MasterData;
import com.shaddicard.repositories.database.DatabaseClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> {

    Context context;
    ArrayList<MasterData> candidateData;

    public CandidateAdapter(Context context, ArrayList<MasterData> articles) {
        this.context = context;
        this.candidateData = articles;
    }

    @NonNull
    @Override
    public CandidateAdapter.CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_candidate, parent, false);
        return new  CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateAdapter.CandidateViewHolder holder, final int position) {
        holder.tv_name.setText(candidateData.get(position).getName().getTitle()+". " +candidateData.get(position).getName().getFirst()+" "+candidateData.get(position).getName().getLast());
        holder.tv_mobno.setText(candidateData.get(position).getCell());
        Glide.with(context)
                .load(candidateData.get(position).getPicture().getLarge())
                .override(300, 200)
                .into(holder.iv_userPic);
        holder.tv_email.setText(candidateData.get(position).getEmail());

        holder.tv_dob.setText(convertDate(candidateData.get(position).getDob().getDate()));

        holder.tv_address.setText(candidateData.get(position).getLocation().getStreet().getNumber().trim()+", "+candidateData.get(position).getLocation().getStreet().getName()+"\n"
        +candidateData.get(position).getLocation().getCity()+" "+candidateData.get(position).getLocation().getState()
                +" "+candidateData.get(position).getLocation().getCountry());

        if (candidateData.get(position).getStatus().isEmpty()){
            holder.ll_action.setVisibility(View.VISIBLE);
            holder.tv_msg.setVisibility(View.GONE);
        }else{
            holder.ll_action.setVisibility(View.GONE);
            holder.tv_msg.setVisibility(View.VISIBLE);

            if (candidateData.get(position).getStatus().equalsIgnoreCase("X")){
                holder.tv_msg.setText("Member Declined");
            }else{
                holder.tv_msg.setText("Member Accepted");
            }
        }


        holder.tv_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                candidateData.get(position).setStatus("Y");
                new updateData("Y", position).execute();
                notifyDataSetChanged();
            }
        });

        holder.tv_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                candidateData.get(position).setStatus("X");
                new updateData("X", position).execute();
                notifyDataSetChanged();
            }
        });

    }


    String convertDate(String inputDate) {

        DateFormat theDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;

        try {
            date = theDateFormat.parse(inputDate);
        } catch (ParseException parseException) {
            // Date is invalid. Do what you want.
        } catch(Exception exception) {
            // Generic catch. Do what you want.
        }

        theDateFormat = new SimpleDateFormat("MMM dd, yyyy");

        return theDateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return candidateData.size();
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_mobno;
        ImageView iv_userPic;
        TextView tv_email,tv_dob,tv_address,tv_msg,tv_accept,tv_reject;
        LinearLayout ll_action;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_mobno = itemView.findViewById(R.id.tv_mobno);
            iv_userPic = itemView.findViewById(R.id.iv_userPic);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_dob = itemView.findViewById(R.id.tv_dob);
            tv_address = itemView.findViewById(R.id.tv_address);
            ll_action = itemView.findViewById(R.id.ll_action);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            tv_accept = itemView.findViewById(R.id.tv_accept);
            tv_reject = itemView.findViewById(R.id.tv_reject);

        }
    }


    class updateData extends AsyncTask<Void, Void, Void> {

        String status="";
        int position;
        public updateData(String s, int i) {
            status = s;
            position = i;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseClient.getInstance(context).getAppDatabase()
                    .dataDao().update(status,position+1);

            return null;
        }

    }
}