package edu.communication.hemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import edu.communication.hemo.R;
import edu.communication.hemo.doctor.model.PrescriptionDetails;
import edu.communication.hemo.patient.ImagePreviewActivity;
import edu.communication.hemo.patient.PharmacyListActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<PrescriptionDetails> filterList = new ArrayList<>();
    private MyInterface listener;
    private ArrayList<PrescriptionDetails> prescriptionDetailsArrayList;


    public interface MyInterface {
        void sendCountToActivity(int i);
    }

    public PrescriptionListAdapter(Context context, ArrayList<PrescriptionDetails> prescriptionDetailsArrayList, MyInterface listener) {
        this.prescriptionDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.prescriptionDetailsArrayList = prescriptionDetailsArrayList;
        this.filterList.addAll(this.prescriptionDetailsArrayList);
        this.listener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prescriptions_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final PrescriptionDetails prescriptionDetails = this.filterList.get(position);
        viewHolder.patientName.setText(prescriptionDetails.getPatientName());
        viewHolder.patientMobile.setText(prescriptionDetails.getPatientMobileNumber());
        viewHolder.patientEmail.setText(prescriptionDetails.getPatientEmail());
        viewHolder.docName.setText(prescriptionDetails.getDoctorName());
        viewHolder.docEmail.setText(prescriptionDetails.getDoctorEmail());
        viewHolder.consultType.setText(prescriptionDetails.getConsultType());
        viewHolder.consultDate.setText(prescriptionDetails.getConsultDate());
        Glide.with(this.context).load(prescriptionDetails.getPrescriptionImageURL()).into(viewHolder.prescribeThumbnail);
        viewHolder.prescribeThumbnail.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.PrescriptionListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(PrescriptionListAdapter.this.context, ImagePreviewActivity.class);
                    intent.putExtra("imageURL", prescriptionDetails.getPrescriptionImageURL());
                    PrescriptionListAdapter.this.context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewHolder.sharePresc.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.PrescriptionListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionListAdapter.this.context, PharmacyListActivity.class);
                intent.putExtra("prescriptionDetails", prescriptionDetails);
                PrescriptionListAdapter.this.context.startActivity(intent);
            }
        });
        viewHolder.viewPharmacy.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.PrescriptionListAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionListAdapter.this.context, PharmacyListActivity.class);
                intent.putExtra("prescriptionDetails", prescriptionDetails);
                PrescriptionListAdapter.this.context.startActivity(intent);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<PrescriptionDetails> arrayList = this.filterList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView consultDate;
        TextView consultType;
        TextView docEmail;
        TextView docName;
        TextView patientEmail;
        TextView patientMobile;
        TextView patientName;
        ImageView prescribeThumbnail;
        ImageView sharePresc;
        TextView viewPharmacy;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.patientName = (TextView) itemView.findViewById(R.id.tv_patient_name);
            this.patientMobile = (TextView) itemView.findViewById(R.id.tv_pharma_mobile);
            this.patientEmail = (TextView) itemView.findViewById(R.id.tv_patient_email);
            this.docName = (TextView) itemView.findViewById(R.id.tv_doc_name);
            this.docEmail = (TextView) itemView.findViewById(R.id.tv_doctor_email);
            this.consultType = (TextView) itemView.findViewById(R.id.tv_consult_date_value);
            this.consultDate = (TextView) itemView.findViewById(R.id.tv_consult_type);
            this.prescribeThumbnail = (ImageView) itemView.findViewById(R.id.iv_presc_thumbnail);
            this.sharePresc = (ImageView) itemView.findViewById(R.id.iv_share_presc);
            this.viewPharmacy = (TextView) itemView.findViewById(R.id.tv_view_pharmacy);
        }
    }

    public void filter(final String text) {
        new Thread(new Runnable() { // from class: edu.communication.hemo.adapter.PrescriptionListAdapter.4
            @Override // java.lang.Runnable
            public void run() {
                PrescriptionListAdapter.this.filterList.clear();
                if (TextUtils.isEmpty(text)) {
                    PrescriptionListAdapter.this.filterList.addAll(PrescriptionListAdapter.this.prescriptionDetailsArrayList);
                } else {
                    Iterator it = PrescriptionListAdapter.this.prescriptionDetailsArrayList.iterator();
                    while (it.hasNext()) {
                        PrescriptionDetails prescriptionDetails = (PrescriptionDetails) it.next();
                        if (prescriptionDetails.getPatientEmail().toLowerCase(Locale.getDefault()).contains(text.toLowerCase())) {
                            PrescriptionListAdapter.this.filterList.add(prescriptionDetails);
                        }
                    }
                }
                ((Activity) PrescriptionListAdapter.this.context).runOnUiThread(new Runnable() { // from class: edu.communication.hemo.adapter.PrescriptionListAdapter.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PrescriptionListAdapter.this.listener.sendCountToActivity(PrescriptionListAdapter.this.filterList.size());
                        PrescriptionListAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
