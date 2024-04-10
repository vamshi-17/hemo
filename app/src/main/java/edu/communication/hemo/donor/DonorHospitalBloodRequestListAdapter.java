package edu.communication.hemo.donor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.hospital.model.RequestBloodDetails;
import java.util.ArrayList;

public class DonorHospitalBloodRequestListAdapter extends RecyclerView.Adapter<DonorHospitalBloodRequestListAdapter.MyViewHolder> {
    private Context context;
    private MyInterface listener;
    private ArrayList<RequestBloodDetails> requestBloodDetailsArrayList;


    public interface MyInterface {
        void acceptAppointment(RequestBloodDetails requestBloodDetails);

        void rejectAppointment(String str);
    }

    public DonorHospitalBloodRequestListAdapter(Context context, ArrayList<RequestBloodDetails> requestBloodDetailsArrayList, MyInterface listener) {
        this.requestBloodDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.requestBloodDetailsArrayList = requestBloodDetailsArrayList;
        this.listener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_hosp_blood_request_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RequestBloodDetails requestBloodDetails = this.requestBloodDetailsArrayList.get(position);
        TextView textView = holder.hospName;
        textView.setText(requestBloodDetails.getHospitalName() + "Hospital has requested " + requestBloodDetails.getDonorBloodGroup() + " Blood");
        holder.address.setText(requestBloodDetails.getHospAddress());
        holder.email.setText(requestBloodDetails.getHospEmail());
        if (requestBloodDetails.getIsDonationAccepted()) {
            holder.acceptRejectLL.setVisibility(8);
        } else {
            holder.acceptRejectLL.setVisibility(0);
        }
        holder.btReject.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.DonorHospitalBloodRequestListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DonorHospitalBloodRequestListAdapter.this.listener.rejectAppointment(requestBloodDetails.getuId());
            }
        });
        holder.btAccept.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.donor.DonorHospitalBloodRequestListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DonorHospitalBloodRequestListAdapter.this.listener.acceptAppointment(requestBloodDetails);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<RequestBloodDetails> arrayList = this.requestBloodDetailsArrayList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout acceptRejectLL;
        TextView address;
        Button btAccept;
        Button btReject;
        TextView email;
        TextView hospName;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.hospName = (TextView) itemView.findViewById(R.id.hospital_name);
            this.email = (TextView) itemView.findViewById(R.id.tv_email);
            this.address = (TextView) itemView.findViewById(R.id.tv_address);
            this.btAccept = (Button) itemView.findViewById(R.id.bt_accept);
            this.btReject = (Button) itemView.findViewById(R.id.bt_reject);
            this.acceptRejectLL = (LinearLayout) itemView.findViewById(R.id.accept_reject_ll);
        }
    }
}
