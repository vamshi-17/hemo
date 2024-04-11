package edu.communication.hemo.hospital;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import edu.communication.hemo.R;
import edu.communication.hemo.patient.model.AppointmentDetails;
import java.util.ArrayList;

public class HospAppointmentListAdapter extends RecyclerView.Adapter<HospAppointmentListAdapter.MyViewHolder> {
    private ArrayList<AppointmentDetails> appointmentDetailsArrayList;
    private Context context;
    private MyInterface listener;


    public interface MyInterface {
        void acceptAppointment(AppointmentDetails appointmentDetails);

        void rejectAppointment(String str);
    }

    public HospAppointmentListAdapter(Context context, ArrayList<AppointmentDetails> appointmentDetailsArrayList, MyInterface listener) {
        this.appointmentDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.appointmentDetailsArrayList = appointmentDetailsArrayList;
        this.listener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AppointmentDetails appointmentDetails = this.appointmentDetailsArrayList.get(position);
        TextView textView = holder.patientName;
        textView.setText(appointmentDetails.getPatientName() + " has requested an appointment with Dr." + appointmentDetails.getDoctorName());
        holder.mobile.setText(appointmentDetails.getPatientMobileNumber());
        holder.email.setText(appointmentDetails.getPatientEmail());
        holder.date.setText(this.context.getResources().getString(R.string.date, appointmentDetails.getDate()));
        holder.time.setText(this.context.getResources().getString(R.string.time, appointmentDetails.getTime()));
        if (appointmentDetails.isAccepted()) {
            holder.acceptRejectLL.setVisibility(View.GONE);
        } else {
            holder.acceptRejectLL.setVisibility(View.VISIBLE);
        }
        holder.mobile.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.HospAppointmentListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Dexter.withActivity((Activity) HospAppointmentListAdapter.this.context).withPermission("android.permission.CALL_PHONE").withListener(new PermissionListener() { // from class: edu.communication.hemo.hospital.HospAppointmentListAdapter.1.1
                    @Override // com.karumi.dexter.listener.single.PermissionListener
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if (appointmentDetails.getPatientMobileNumber() != null) {
                            Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + appointmentDetails.getPatientMobileNumber()));
                            HospAppointmentListAdapter.this.context.startActivity(intent);
                        }
                    }

                    @Override // com.karumi.dexter.listener.single.PermissionListener
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                    }

                    @Override // com.karumi.dexter.listener.single.PermissionListener
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    }
                }).check();
            }
        });
        holder.btReject.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.HospAppointmentListAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HospAppointmentListAdapter.this.listener.rejectAppointment(appointmentDetails.getuId());
            }
        });
        holder.btAccept.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.hospital.HospAppointmentListAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HospAppointmentListAdapter.this.listener.acceptAppointment(appointmentDetails);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<AppointmentDetails> arrayList = this.appointmentDetailsArrayList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout acceptRejectLL;
        Button btAccept;
        Button btReject;
        TextView date;
        TextView email;
        TextView mobile;
        TextView patientName;
        TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.patientName = (TextView) itemView.findViewById(R.id.patient_name);
            this.email = (TextView) itemView.findViewById(R.id.tv_email);
            this.mobile = (TextView) itemView.findViewById(R.id.tv_mobile);
            this.date = (TextView) itemView.findViewById(R.id.tv_date);
            this.time = (TextView) itemView.findViewById(R.id.tv_time);
            this.btAccept = (Button) itemView.findViewById(R.id.bt_accept);
            this.btReject = (Button) itemView.findViewById(R.id.bt_reject);
            this.acceptRejectLL = (LinearLayout) itemView.findViewById(R.id.accept_reject_ll);
        }
    }
}
