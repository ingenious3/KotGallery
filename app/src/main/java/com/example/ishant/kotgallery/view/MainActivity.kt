package com.example.ishant.kotgallery.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.ishant.kotgallery.R
import com.example.ishant.kotgallery.adapter.PhotoAdapter
import com.example.ishant.kotgallery.api.PhotoRetriever
import com.example.ishant.kotgallery.models.Photo
import com.example.ishant.kotgallery.models.PhotoList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var photos: List<Photo>? = null
    var photoAdapter: PhotoAdapter? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        var retriever =  PhotoRetriever()

        val callback = object : Callback<PhotoList> {
            override fun onFailure(call: Call<PhotoList>?, t: Throwable?) {
                Log.e("MainActivity", "Problems calling API", t)
            }

            override fun onResponse(call: Call<PhotoList>?, response: Response<PhotoList>?) {
                response?.isSuccessful.let {
                    this@MainActivity.photos = response?.body()?.hits
                    photoAdapter = PhotoAdapter(this@MainActivity.photos!!, this@MainActivity)
                    recyclerView.adapter = photoAdapter
                }
            }

        }
        retriever.getPhotos(callback)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, DetailActivity::class.java)
        val holder = view?.tag as PhotoAdapter.PhotoHolder
        intent.putExtra(DetailActivity.PHOTO, photoAdapter?.getPhoto(holder.adapterPosition))
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

//
//        if (id == R.id.action_settings) {
//            return true
//        }

        return super.onOptionsItemSelected(item)
    }
}
