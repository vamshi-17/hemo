package edu.communication.hemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import edu.communication.hemo.R;
import edu.communication.hemo.custom.Utils;
import edu.communication.hemo.patient.ImagePreviewActivity;
import edu.communication.hemo.patient.model.ChatDetails;
import edu.communication.hemo.patient.model.PatientDetails;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private ArrayList<ChatDetails> chatDetailsArrayList;
    private Context context;
    private boolean isDoctorScreen;
    PatientDetails patientDetails;

    public MessageAdapter(Context context, ArrayList<ChatDetails> chatDetailsArrayList, PatientDetails patientDetails, boolean isDoctorScreen) {
        this.context = context;
        this.chatDetailsArrayList = chatDetailsArrayList;
        this.patientDetails = patientDetails;
        this.isDoctorScreen = isDoctorScreen;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = null;
        if (viewType == 1) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_right, viewGroup, false);
        } else if (viewType == 0) {
            View itemView2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MyViewHolder(itemView2);
        }
        return new MyViewHolder(itemView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ChatDetails chatDetails = this.chatDetailsArrayList.get(position);
        if (chatDetails.getMessageType().equals("text")) {
            holder.show_message.setText(Utils.DecodeString(chatDetails.getMessage()));
            holder.show_message.setVisibility(View.VISIBLE);
            holder.showImage.setVisibility(View.GONE);
        } else if (chatDetails.getMessageType().equals("image")) {
            holder.show_message.setVisibility(View.GONE);
            holder.showImage.setVisibility(View.VISIBLE);
            Glide.with(this.context).load(chatDetails.getImageURL()).into(holder.showImage);
            holder.showImage.setOnClickListener(new View.OnClickListener() { // from class: edu.communication.hemo.adapter.MessageAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Intent intent = new Intent(MessageAdapter.this.context, ImagePreviewActivity.class);
                    intent.putExtra("imageURL", chatDetails.getImageURL());
                    MessageAdapter.this.context.startActivity(intent);
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<ChatDetails> arrayList = this.chatDetailsArrayList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView showImage;
        TextView show_message;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.show_message = (TextView) itemView.findViewById(R.id.show_message);
            this.showImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.isDoctorScreen ? this.chatDetailsArrayList.get(position).getSenderMail().equals(this.patientDetails.getmEmail()) ? 0 : 1 : this.chatDetailsArrayList.get(position).getSenderMail().equals(this.patientDetails.getmEmail()) ? 1 : 0;
    }
}
