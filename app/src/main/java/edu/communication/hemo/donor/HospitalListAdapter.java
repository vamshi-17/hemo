package edu.communication.hemo.donor;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.donor.model.DonorDetails;
import edu.communication.hemo.hospital.model.HospitalDetails;
import java.util.ArrayList;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.MyViewHolder> {
    private Context context;
    private DonorDetails donorDetails;
    private ArrayList<HospitalDetails> hospitalDetailsArrayList;

    public HospitalListAdapter(Context context, ArrayList<HospitalDetails> hospitalDetailsArrayList, DonorDetails donorDetails) {
        this.hospitalDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.hospitalDetailsArrayList = hospitalDetailsArrayList;
        this.donorDetails = donorDetails;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HospitalDetails hospitalDetails = this.hospitalDetailsArrayList.get(position);
        holder.pharmaName.setText(hospitalDetails.getmHosName());
        holder.email.setText(Utils.DecodeString(hospitalDetails.getmEmail()));
        holder.address.setText(hospitalDetails.getmAddress());
        holder.bookDonationSlotIv.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.HospitalListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(HospitalListAdapter.this.context, BookDonationSlotActivity.class);
                    intent.putExtra("hospitalDetails", (Parcelable) HospitalListAdapter.this.hospitalDetailsArrayList.get(holder.getAdapterPosition()));
                    intent.putExtra("donorDetails", HospitalListAdapter.this.donorDetails);
                    HospitalListAdapter.this.context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<HospitalDetails> arrayList = this.hospitalDetailsArrayList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        Button bookDonationSlotIv;
        TextView email;
        TextView pharmaName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.pharmaName = (TextView) itemView.findViewById(R.id.tv_hosp_name);
            this.email = (TextView) itemView.findViewById(R.id.tv_hosp_email);
            this.address = (TextView) itemView.findViewById(R.id.tv_hosp_address);
            this.bookDonationSlotIv = (Button) itemView.findViewById(R.id.iv_book_donation_slot);
        }
    }
}
