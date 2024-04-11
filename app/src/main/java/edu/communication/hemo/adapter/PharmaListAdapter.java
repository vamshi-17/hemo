package edu.communication.hemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.doctor.model.PrescriptionDetails;
import edu.communication.hemo.pharmacy.model.PharmacyDetails;
import java.util.ArrayList;

public class PharmaListAdapter extends RecyclerView.Adapter<PharmaListAdapter.MyViewHolder> {
    private Context context;
    private MyInterface listener;
    private ArrayList<PharmacyDetails> pharmacyDetailsArrayList;
    private PrescriptionDetails prescriptionDetails;


    public interface MyInterface {
        void sendPrescriptionToActivity(PrescriptionDetails prescriptionDetails, PharmacyDetails pharmacyDetails);
    }

    public PharmaListAdapter(Context context, ArrayList<PharmacyDetails> pharmacyDetailsArrayList, PrescriptionDetails prescriptionDetails, MyInterface listener) {
        this.pharmacyDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.pharmacyDetailsArrayList = pharmacyDetailsArrayList;
        this.prescriptionDetails = prescriptionDetails;
        this.listener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacy_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PharmacyDetails pharmacyDetails = this.pharmacyDetailsArrayList.get(position);
        holder.pharmaName.setText(pharmacyDetails.getName());
        holder.mobile.setText(pharmacyDetails.getmMobileNumber());
        holder.email.setText(pharmacyDetails.getmEmail());
        holder.address.setText(pharmacyDetails.getmAddress());
        holder.sharePresc.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.PharmaListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PharmaListAdapter.this.listener.sendPrescriptionToActivity(PharmaListAdapter.this.prescriptionDetails, pharmacyDetails);
            }
        });
        holder.sharePrescription.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.PharmaListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PharmaListAdapter.this.listener.sendPrescriptionToActivity(PharmaListAdapter.this.prescriptionDetails, pharmacyDetails);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<PharmacyDetails> arrayList = this.pharmacyDetailsArrayList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        TextView email;
        TextView mobile;
        TextView pharmaName;
        ImageView sharePresc;
        TextView sharePrescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.pharmaName = (TextView) itemView.findViewById(R.id.tv_pharma_name);
            this.email = (TextView) itemView.findViewById(R.id.tv_pharma_email);
            this.mobile = (TextView) itemView.findViewById(R.id.tv_pharma_mobile);
            this.address = (TextView) itemView.findViewById(R.id.tv_pharma_address);
            this.sharePrescription = (TextView) itemView.findViewById(R.id.tv_share_prescriptions);
            this.sharePresc = (ImageView) itemView.findViewById(R.id.iv_share_presc);
        }
    }
}
