package car.andrej.lab3.RecylceView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import car.andrej.lab3.R;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    public Adapter(List<String> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    List<String> lista;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.textview);


        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(context).inflate(R.layout.view_holder_layout,viewGroup,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String data=lista.get(i);
        myViewHolder.textView.setText(data);


    }

    @Override
    public int getItemCount() {
       if (lista!=null){
           return lista.size();
       }
       return 0;
    }

    public void updateData(List<String> data) {
        this.lista  = data;
        notifyDataSetChanged();
    }


}
