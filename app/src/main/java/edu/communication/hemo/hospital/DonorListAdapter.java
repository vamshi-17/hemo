package edu.communication.hemo.hospital;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.donor.model.DonorDetails;
import edu.communication.hemo.hospital.model.HospitalDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class DonorListAdapter extends RecyclerView.Adapter<DonorListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<DonorDetails> donorDetailsArrayList;
    private ArrayList<DonorDetails> filterList = new ArrayList<>();
    HospitalDetails hospitalDetails;

    public DonorListAdapter(Context context, ArrayList<DonorDetails> donorDetailsArrayList, HospitalDetails hospitalDetails) {
        this.donorDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.donorDetailsArrayList = donorDetailsArrayList;
        this.hospitalDetails = hospitalDetails;
        this.filterList.addAll(this.donorDetailsArrayList);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.donors_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
        viewHolder.donorName.setText(this.filterList.get(viewHolder.getAdapterPosition()).getName());
        viewHolder.age.setText(this.filterList.get(viewHolder.getAdapterPosition()).getmAge());
        TextView textView = viewHolder.bloodGroup;
        textView.setText("Blood Group: " + this.filterList.get(viewHolder.getAdapterPosition()).getmBloodGroup());
        viewHolder.mobileNumber.setText(this.filterList.get(viewHolder.getAdapterPosition()).getmMobileNumber());
        viewHolder.email.setText(this.filterList.get(viewHolder.getAdapterPosition()).getmEmail());
        TextView textView2 = viewHolder.lastDonated;
        textView2.setText("Last Donated Date: " + this.filterList.get(viewHolder.getAdapterPosition()).getmLastDonated());
        viewHolder.viewDonor.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.DonorListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(DonorListAdapter.this.context, RequestBloodActivity.class);
                    intent.putExtra("donorDetails", (Parcelable) DonorListAdapter.this.filterList.get(viewHolder.getAdapterPosition()));
                    intent.putExtra("hospitalDetails", DonorListAdapter.this.hospitalDetails);
                    DonorListAdapter.this.context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<DonorDetails> arrayList = this.filterList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView age;
        TextView bloodGroup;
        TextView donorName;
        TextView email;
        TextView lastDonated;
        TextView mobileNumber;
        TextView viewDonor;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.donorName = (TextView) itemView.findViewById(R.id.donor_name);
            this.age = (TextView) itemView.findViewById(R.id.tv_gender_age);
            this.bloodGroup = (TextView) itemView.findViewById(R.id.tv_blood_group);
            this.mobileNumber = (TextView) itemView.findViewById(R.id.tv_mobile);
            this.email = (TextView) itemView.findViewById(R.id.tv_email);
            this.lastDonated = (TextView) itemView.findViewById(R.id.tv_last_donated);
            this.viewDonor = (TextView) itemView.findViewById(R.id.tv_view_donor);
        }
    }

    public void filter(final String text) {
        new Thread(new Runnable() { // from class: edu.communication.hemo.hospital.DonorListAdapter.2
            @Override // java.lang.Runnable
            public void run() {
                DonorListAdapter.this.filterList.clear();
                if (TextUtils.isEmpty(text)) {
                    DonorListAdapter.this.filterList.addAll(DonorListAdapter.this.donorDetailsArrayList);
                } else {
                    Iterator it = DonorListAdapter.this.donorDetailsArrayList.iterator();
                    while (it.hasNext()) {
                        DonorDetails donorDetails = (DonorDetails) it.next();
                        if (donorDetails.getmBloodGroup().toLowerCase(Locale.getDefault()).contains(text.toLowerCase())) {
                            DonorListAdapter.this.filterList.add(donorDetails);
                        }
                    }
                }
                ((Activity) DonorListAdapter.this.context).runOnUiThread(new Runnable() { // from class: edu.communication.hemo.hospital.DonorListAdapter.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DonorListAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
