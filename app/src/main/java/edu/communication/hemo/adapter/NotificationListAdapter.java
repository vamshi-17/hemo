package edu.communication.hemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.patient.model.AppointmentDetails;
import edu.communication.hemo.patient.model.PatientDetails;
import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    private ArrayList<AppointmentDetails> appointmentDetailsArrayList;
    private Context context;
    PatientDetails patientDetails;

    public NotificationListAdapter(Context context, ArrayList<AppointmentDetails> appointmentDetailsArrayList, PatientDetails patientDetails) {
        this.appointmentDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.appointmentDetailsArrayList = appointmentDetailsArrayList;
        this.patientDetails = patientDetails;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView textView = holder.notificationTitle;
        textView.setText("You have an Appointment with " + this.appointmentDetailsArrayList.get(position).getDoctorName());
        TextView textView2 = holder.notificationDetails;
        textView2.setText("On Date : " + this.appointmentDetailsArrayList.get(position).getDate() + " and Time is: " + this.appointmentDetailsArrayList.get(position).getTime());
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
        TextView notificationDetails;
        TextView notificationTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.notificationTitle = (TextView) itemView.findViewById(R.id.notification_title);
            this.notificationDetails = (TextView) itemView.findViewById(R.id.tv_notification_details);
        }
    }
}
