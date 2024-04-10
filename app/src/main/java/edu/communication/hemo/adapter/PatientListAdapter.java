package edu.communication.hemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.admin.model.DoctorDetails;
import edu.communication.hemo.doctor.ViewPatientDetails;
import edu.communication.hemo.patient.SendPatientMessageActivity;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.MyViewHolder> {
    private Context context;
    DoctorDetails doctorDetails;
    private ArrayList<PatientDetails> patientDetailsArrayList;

    public PatientListAdapter(Context context, ArrayList<PatientDetails> patientDetailsArrayList, DoctorDetails doctorDetails) {
        this.patientDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.patientDetailsArrayList = patientDetailsArrayList;
        this.doctorDetails = doctorDetails;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patients_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        final PatientDetails patientDetails = this.patientDetailsArrayList.get(position);
        viewHolder.patientName.setText(this.patientDetailsArrayList.get(position).getName());
        TextView textView = viewHolder.genderAge;
        textView.setText(this.patientDetailsArrayList.get(position).getmGender() + ", " + this.patientDetailsArrayList.get(position).getmAge() + "Yrs");
        TextView textView2 = viewHolder.bloodGroup;
        StringBuilder sb = new StringBuilder();
        sb.append("Blood Group: ");
        sb.append(this.patientDetailsArrayList.get(position).getmBloodGroup());
        textView2.setText(sb.toString());
        viewHolder.mobileNumber.setText(this.patientDetailsArrayList.get(position).getmMobileNumber());
        viewHolder.email.setText(this.patientDetailsArrayList.get(position).getmEmail());
        viewHolder.chatIcon.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.PatientListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(PatientListAdapter.this.context, SendPatientMessageActivity.class);
                intent.putExtra("patientDetails", patientDetails);
                intent.putExtra("doctorDetails", PatientListAdapter.this.doctorDetails);
                intent.putExtra("isDoctor", true);
                PatientListAdapter.this.context.startActivity(intent);
            }
        });
        viewHolder.viewPatient.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.PatientListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(PatientListAdapter.this.context, ViewPatientDetails.class);
                    intent.putExtra("patientDetails", patientDetails);
                    intent.putExtra("doctorDetails", PatientListAdapter.this.doctorDetails);
                    PatientListAdapter.this.context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.patientDetailsArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bloodGroup;
        TextView chatIcon;
        TextView email;
        TextView genderAge;
        TextView mobileNumber;
        TextView patientName;
        TextView viewPatient;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.patientName = (TextView) itemView.findViewById(R.id.patient_name);
            this.genderAge = (TextView) itemView.findViewById(R.id.tv_gender_age);
            this.bloodGroup = (TextView) itemView.findViewById(R.id.tv_blood_group);
            this.mobileNumber = (TextView) itemView.findViewById(R.id.tv_mobile);
            this.email = (TextView) itemView.findViewById(R.id.tv_email);
            this.chatIcon = (TextView) itemView.findViewById(R.id.chat_icon);
            this.viewPatient = (TextView) itemView.findViewById(R.id.tv_view_patient);
        }
    }
}
