package com.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
   private fun loadMeme(){
       progressBar.visibility=View.VISIBLE;

       val url = "https://meme-api.herokuapp.com/gimme"

       val jsonObjectRequest = JsonObjectRequest(
           Request.Method.GET, url, null, Response.Listener { response ->
               currentImageUrl =response.getString(" url")
               Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>
               {
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressBar.visibility=View.GONE;
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                    progressBar.visibility=View.GONE;
                       return false
                   }

               })
           },
            Response.ErrorListener {
            Toast.makeText(this, "Something is Wrong", Toast.LENGTH_LONG).show()
            }

       )


        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun ShareApp(view: View) {
    val intent=Intent(Intent.ACTION_SEND)
        intent.type="plain/text"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey! Check this Amazing meme i got from Reddit $currentImageUrl")
        val chooser=Intent.createChooser(intent,"Share this meme via..")
        startActivity(chooser)

    }
    fun NextMeme(view: View) {
        loadMeme()
    }
}