package edu.communication.hemo.hospital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.communication.hemo.R;
import edu.communication.hemo.hospital.model.HospitalDetails;
import edu.communication.hemo.hospital.model.RequestBloodDetails;
import java.util.ArrayList;

public class BloodDonorNotificationListAdapter extends RecyclerView.Adapter<BloodDonorNotificationListAdapter.MyViewHolder> {
    private Context context;
    HospitalDetails hospitalDetails;
    private ArrayList<RequestBloodDetails> requestBloodDetailsArrayList;

    public BloodDonorNotificationListAdapter(Context context, ArrayList<RequestBloodDetails> requestBloodDetailsArrayList, HospitalDetails hospitalDetails) {
        this.requestBloodDetailsArrayList = new ArrayList<>();
        this.context = context;
        this.requestBloodDetailsArrayList = requestBloodDetailsArrayList;
        this.hospitalDetails = hospitalDetails;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications_list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView textView = holder.notificationTitle;
        textView.setText("Your request for " + this.requestBloodDetailsArrayList.get(position).getDonorBloodGroup() + "Blood Group has been accepted by Donor " + this.requestBloodDetailsArrayList.get(position).getDonorName());
        holder.notificationDetails.setVisibility(View.GONE);
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
        TextView notificationDetails;
        TextView notificationTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.notificationTitle = (TextView) itemView.findViewById(R.id.notification_title);
            this.notificationDetails = (TextView) itemView.findViewById(R.id.tv_notification_details);
        }
    }
}
