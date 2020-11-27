package com.mvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.R
import com.mvvm.databinding.ItemAnimalBinding
import com.mvvm.model.Animal
import com.mvvm.util.getProgressDrawable
import com.mvvm.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener
{
    fun updateAnimalList(newAnimalList: List<Animal>)
    {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int)
    {
        holder.view.animal = animalList[position]
        holder.view.listener = this

//        holder.view.animalLayout.setOnClickListener {
//
//        }
    }

    override fun getItemCount(): Int
    {
        return animalList.size
    }

    override fun onClick(v: View)
    {
        for (animal in animalList)
        {
            if (v.tag == animal.name)
            {
                val action = ListFragmentDirections.actionDetail(animal)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }

    class AnimalViewHolder(var view: ItemAnimalBinding): RecyclerView.ViewHolder(view.root)

}