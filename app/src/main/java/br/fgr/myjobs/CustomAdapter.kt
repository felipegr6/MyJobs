package br.fgr.myjobs

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView

class CustomAdapter(private val list: List<CustomItem>?) : Adapter<CustomAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_item, parent, false) as View

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.tvTitle.text = list?.get(position)?.title ?: ""
        holder.tvDescription.text = list?.get(position)?.description ?: ""
        holder.tvTimestamp.text = list?.get(position)?.ts?.toDate().toString()
        if (list?.get(position)?.result.isNullOrBlank()) {
            holder.tvResult.visibility = GONE
        } else {
            holder.tvResult.visibility = VISIBLE
            holder.tvResult.text = list?.get(position)?.result
        }
        if (position % 2 == 0) {
            holder.vParent.setBackgroundResource(R.color.bg_gray)
        } else {
            holder.vParent.setBackgroundResource(R.color.bg_white)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vParent: View = itemView.findViewById(R.id.view_parent)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
        val tvResult: TextView = itemView.findViewById(R.id.tv_result)
    }
}
