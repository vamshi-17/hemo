package edu.communication.hemo.hospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.patient.BloodPlasmaRequestDetails;
import java.util.ArrayList;

public class BloodPlasmaListAdapter extends RecyclerView.Adapter<BloodPlasmaListAdapter.MyViewHolder> {
    private ArrayList<BloodPlasmaRequestDetails> bloodPlasmaRequestDetailsArrayList;
    private Context context;
    private MyInterface listener;


    public interface MyInterface {
        void acceptRequest(BloodPlasmaRequestDetails bloodPlasmaRequestDetails);

        void rejectRequest(BloodPlasmaRequestDetails bloodPlasmaRequestDetails);
    }

    public BloodPlasmaListAdapter(Context context, ArrayList<BloodPlasmaRequestDetails> bloodPlasmaRequestDetailsArrayList, MyInterface listener) {
        this.bloodPlasmaRequestDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.bloodPlasmaRequestDetailsArrayList = bloodPlasmaRequestDetailsArrayList;
        this.listener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blood_plama_request_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
        String name = this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getPatientName();
        String requestFor = this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getRequestFor();
        String requestedType = this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getRequestType();
        String noOfUnits = this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getNoOfUnits();
        TextView textView = viewHolder.patientName;
        textView.setText("Patient " + name + " has requested " + noOfUnits + " units of " + requestedType + " for " + requestFor);
        TextView textView2 = viewHolder.age;
        StringBuilder sb = new StringBuilder();
        sb.append(this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getPatientGender());
        sb.append(",");
        sb.append(this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getPatientAge());
        textView2.setText(sb.toString());
        TextView textView3 = viewHolder.bloodGroup;
        textView3.setText("Blood Group: " + this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getPatientBloodGroup());
        viewHolder.mobileNumber.setText(this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getPatientMobile());
        viewHolder.email.setText(this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getPatientEmail());
        TextView textView4 = viewHolder.requiredDate;
        textView4.setText("Required Date: " + this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()).getRequiredDate());
        viewHolder.reject.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.BloodPlasmaListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BloodPlasmaListAdapter.this.listener.rejectRequest((BloodPlasmaRequestDetails) BloodPlasmaListAdapter.this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()));
            }
        });
        viewHolder.accept.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.BloodPlasmaListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BloodPlasmaListAdapter.this.listener.acceptRequest((BloodPlasmaRequestDetails) BloodPlasmaListAdapter.this.bloodPlasmaRequestDetailsArrayList.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<BloodPlasmaRequestDetails> arrayList = this.bloodPlasmaRequestDetailsArrayList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button accept;
        TextView age;
        TextView bloodGroup;
        TextView email;
        TextView mobileNumber;
        TextView patientName;
        Button reject;
        TextView requiredDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.patientName = (TextView) itemView.findViewById(R.id.patient_name);
            this.age = (TextView) itemView.findViewById(R.id.tv_gender_age);
            this.bloodGroup = (TextView) itemView.findViewById(R.id.tv_blood_group);
            this.mobileNumber = (TextView) itemView.findViewById(R.id.tv_mobile);
            this.email = (TextView) itemView.findViewById(R.id.tv_email);
            this.requiredDate = (TextView) itemView.findViewById(R.id.tv_request_date);
            this.accept = (Button) itemView.findViewById(R.id.bt_accept);
            this.reject = (Button) itemView.findViewById(R.id.bt_reject);
        }
    }
}
