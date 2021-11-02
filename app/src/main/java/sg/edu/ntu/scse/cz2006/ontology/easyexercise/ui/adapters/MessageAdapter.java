package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final List<Message> myMessageList;

    public MessageAdapter(List<Message> messageList) {
        myMessageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Message message = myMessageList.get(position);
        holder.userName.setText(message.getMessageUsername());
        try {
            Glide.with(holder.itemView.getContext())
                    .load(new URL(message.getMessageAvatarUrl()))
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.avatar);
        } catch (NullPointerException e) {
            Toast.makeText(holder.itemView.getContext(), "Image not found", Toast.LENGTH_LONG).show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        holder.messageContent.setText(message.getMessageText());
        holder.time.setText(message.getMessageTime());
    }

    @Override
    public int getItemCount() {
        return myMessageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView messageContent;
        ImageView avatar;
        TextView time;

        public ViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.messageUserName);
            messageContent = view.findViewById(R.id.messageContent);
            avatar = view.findViewById(R.id.messageAvatar);
            time = view.findViewById(R.id.messageTime);
        }
    }
}
