package androidapp.musicshare;

/**
 * Created by Tanya on 21-04-2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import Model.PojoClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import Model.PojoClass;
import java.util.ArrayList;

/**
 * Created by Tanya on 21-04-2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<PojoClass> pojoClassArrayList;

    public ListAdapter(Context context, ArrayList<PojoClass> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {

        holder.song_name.setText(pojoClassArrayList.get(position).getSong_name());
        holder.singer_name.setText(pojoClassArrayList.get(position).getSinger_name());
        holder.time.setText(pojoClassArrayList.get(position).getTime());

    }

    @Override
    public int getItemCount()   {
        return pojoClassArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView song_name,singer_name,time;

        public ViewHolder(View view) {
            super(view);

            song_name =  view.findViewById(R.id.song_name);
            singer_name =  view.findViewById(R.id.singer_name);
            time =  view.findViewById(R.id.time);
        }
    }
}
