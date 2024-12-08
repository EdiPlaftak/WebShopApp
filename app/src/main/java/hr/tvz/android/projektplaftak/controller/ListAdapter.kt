package hr.tvz.android.projektplaftak.controller

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import hr.tvz.android.projektplaftak.R
import hr.tvz.android.projektplaftak.model.Jersey

class ListAdapter (val jerseys: List<Jersey?>, val orientation: String):  RecyclerView.Adapter<ListAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val textView: TextView = itemView.findViewById(R.id.listItemText)
        val draweeView: SimpleDraweeView = itemView.findViewById(R.id.listItemDraweeView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        Fresco.initialize(parent.context)

        var view: View? = null

        if(orientation == parent.resources.getString(R.string.landOrientation)){
            view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_land, parent, false)
        }
        else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        }

        return ViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = jerseys[position]?.team

        val imageUri = Uri.parse(jerseys[position]?.imageUrl)

        holder.draweeView.setImageURI(imageUri)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, JerseyActivity::class.java)
            intent.putExtra(holder.itemView.resources.getString(R.string.jersey), jerseys[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return jerseys.size
    }

}