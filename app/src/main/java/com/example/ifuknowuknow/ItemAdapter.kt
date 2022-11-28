package com.example.ifuknowuknow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ifuknowuknow.databinding.ItemListBinding

class ItemAdapter(val itemlist: ArrayList<String>):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var binding: ItemListBinding? = null

    inner class ItemViewHolder(itemBinding: ItemListBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemlist[position]
        holder.itemView.apply {
            binding?.tvItem?.text = "$item"
//            if(position%2==0){
//                binding?.tvItem?.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
//            }else{
//                binding?.tvItem?.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
//            }
        }
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

}