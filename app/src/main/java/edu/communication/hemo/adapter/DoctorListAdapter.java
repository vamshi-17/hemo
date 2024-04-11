package edu.communication.hemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.patient.PreferredDoctorDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<DoctorDetails> doctorDetailsArrayList;
    private ArrayList<DoctorDetails> filterList = new ArrayList<>();
    PatientDetails patientDetails;

    public DoctorListAdapter(Context context, ArrayList<DoctorDetails> doctorDetailsArrayList, PatientDetails patientDetails) {
        this.doctorDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.doctorDetailsArrayList = doctorDetailsArrayList;
        this.filterList.addAll(this.doctorDetailsArrayList);
        this.patientDetails = patientDetails;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctors_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        viewHolder.doctorName.setText(this.filterList.get(position).getmName());
        viewHolder.specialization.setText(this.filterList.get(position).getmSpecialization());
        viewHolder.hospital.setText(this.filterList.get(position).getmHospital());
        viewHolder.viewDoctor.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.DoctorListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(DoctorListAdapter.this.context, PreferredDoctorDetails.class);
                    intent.putExtra("doctorDetails", (Parcelable) DoctorListAdapter.this.filterList.get(position));
                    intent.putExtra("patientDetails", DoctorListAdapter.this.patientDetails);
                    DoctorListAdapter.this.context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<DoctorDetails> arrayList = this.filterList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName;
        TextView hospital;
        TextView specialization;
        Button viewDoctor;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.doctorName = (TextView) itemView.findViewById(R.id.doctor_name);
            this.specialization = (TextView) itemView.findViewById(R.id.tv_specialization);
            this.hospital = (TextView) itemView.findViewById(R.id.tv_hospital);
            this.viewDoctor = (Button) itemView.findViewById(R.id.view_icon);
        }
    }

    public void filter(final String text) {
        new Thread(new Runnable() { // from class: edu.communication.hemo.adapter.DoctorListAdapter.2
            @Override // java.lang.Runnable
            public void run() {
                DoctorListAdapter.this.filterList.clear();
                if (TextUtils.isEmpty(text)) {
                    DoctorListAdapter.this.filterList.addAll(DoctorListAdapter.this.doctorDetailsArrayList);
                } else {
                    Iterator it = DoctorListAdapter.this.doctorDetailsArrayList.iterator();
                    while (it.hasNext()) {
                        DoctorDetails doctorDetails = (DoctorDetails) it.next();
                        if (doctorDetails.getmSpecialization().toLowerCase(Locale.getDefault()).contains(text.toLowerCase())) {
                            DoctorListAdapter.this.filterList.add(doctorDetails);
                        }
                    }
                }
                ((Activity) DoctorListAdapter.this.context).runOnUiThread(new Runnable() { // from class: edu.communication.hemo.adapter.DoctorListAdapter.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DoctorListAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
