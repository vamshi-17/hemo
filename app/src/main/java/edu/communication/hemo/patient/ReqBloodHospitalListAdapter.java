package edu.communication.hemo.patient;

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
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class ReqBloodHospitalListAdapter extends RecyclerView.Adapter<ReqBloodHospitalListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<HospitalDetails> hospitalDetailsArrayList;
    private PatientDetails patientDetails;

    public ReqBloodHospitalListAdapter(Context context, ArrayList<HospitalDetails> hospitalDetailsArrayList, PatientDetails patientDetails) {
        this.hospitalDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.hospitalDetailsArrayList = hospitalDetailsArrayList;
        this.patientDetails = patientDetails;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.req_blood_hospital_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        HospitalDetails hospitalDetails = this.hospitalDetailsArrayList.get(position);
        holder.hospName.setText(hospitalDetails.getmHosName());
        holder.email.setText(Utils.DecodeString(hospitalDetails.getmEmail()));
        holder.address.setText(hospitalDetails.getmAddress());
        holder.hospCode.setText(hospitalDetails.getmHosCode());
        holder.requestBloodBtn.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.patient.ReqBloodHospitalListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(ReqBloodHospitalListAdapter.this.context, SendRequestBloodPlasmaActivity.class);
                    intent.putExtra("hospitalDetails", (Parcelable) ReqBloodHospitalListAdapter.this.hospitalDetailsArrayList.get(holder.getAdapterPosition()));
                    intent.putExtra("patientDetails", ReqBloodHospitalListAdapter.this.patientDetails);
                    ReqBloodHospitalListAdapter.this.context.startActivity(intent);
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
        TextView email;
        TextView hospCode;
        TextView hospName;
        Button requestBloodBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.hospName = (TextView) itemView.findViewById(R.id.tv_hosp_name);
            this.email = (TextView) itemView.findViewById(R.id.tv_hosp_email);
            this.address = (TextView) itemView.findViewById(R.id.tv_hosp_address);
            this.hospCode = (TextView) itemView.findViewById(R.id.tv_hosp_code);
            this.requestBloodBtn = (Button) itemView.findViewById(R.id.iv_request_blood_plasma);
        }
    }
}
