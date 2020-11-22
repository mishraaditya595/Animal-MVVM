package com.mvvm.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mvvm.R
import com.mvvm.model.Animal
import com.mvvm.util.getProgressDrawable
import com.mvvm.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment()
{

    var animal: Animal? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let {
            animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }

        animalName.text = animal?.name
        animalLocation.text = animal?.location
        diet.text = animal?.location
        lifespan.text = animal?.lifespan

        animal?.imageUrl?.let{
            setupBGColour(it)
        }
    }

    private fun setupBGColour(imageUrl: String?)
    {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?)
                {
                    Palette.from(resource).generate {
                        val intColor = it?.lightMutedSwatch?.rgb ?: 0
                        detailLayout.setBackgroundColor(intColor)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?)
                {
                }
            })

    }

}