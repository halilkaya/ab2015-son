package tr.org.ab.ab2015ornek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<MessageModel> {

    private Context context;
    private int resource;
    private ArrayList<MessageModel> messages;

    public CustomListAdapter(Context context,
                             int resource,
                             ArrayList<MessageModel> messages) {
        super(context, resource, messages);
        this.context = context;
        this.resource = resource;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        TextView message = (TextView) view.findViewById(R.id.message);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView date_sent = (TextView) view.findViewById(R.id.date_sent);

        message.setText(messages.get(position).getMessage());
        name.setText(messages.get(position).getName());
        date_sent.setText(messages.get(position).getDate_sent());

        return view;
    }

}
