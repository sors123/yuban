package com.hanyuluo.yuban;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanyuluo.yuban.ui.userpage.UserpageActivity;

import java.util.List;

/*
① 创建一个继承RecyclerView.Adapter<VH>的Adapter类
② 创建一个继承RecyclerView.ViewHolder的静态内部类
③ 在Adapter中实现3个方法：
   onCreateViewHolder()
   onBindViewHolder()
   getItemCount()
*/
public class LoginuserAdapter extends RecyclerView.Adapter<LoginuserAdapter.MyViewHolder>{
    private Context context;
    private  List<Loginuser> list;
    private View inflater;
    //构造方法，传入数据
    public LoginuserAdapter(Context context, List<Loginuser> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //将数据和控件绑定
        holder.textView.setText(list.get(position).getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=holder.getAdapterPosition();
                int i = list.get(position).getUserid();
                Intent intent =new Intent(v.getContext(), UserpageActivity.class);
                System.out.println("fuckc"+i+list.get(position).getUsername());
                intent.putExtra("userid", Integer.toString(i));
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
