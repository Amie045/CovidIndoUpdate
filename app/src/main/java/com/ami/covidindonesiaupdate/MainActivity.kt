package com.ami.covidindonesiaupdate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Adapter
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ami.covidindonesiaupdate.adapter.AdapterProvinsi
import com.ami.covidindonesiaupdate.model.DataItem
import com.ami.covidindonesiaupdate.model.ResponseProvinsi
import com.ami.covidindonesiaupdate.network.ApiService
import com.ami.covidindonesiaupdate.network.RetrofitBuilder.retrofit
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {


    companion object{

        lateinit var adapterProvinsi: AdapterProvinsi
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(textBaru: String?): Boolean {
                adapterProvinsi.filter.filter(textBaru)
                return false
            }
        })

        swipe_refresh.setOnRefreshListener {
            getProvinsi()
            swipe_refresh.isRefreshing = false
        }

        getProvinsi()

    }

    private fun getProvinsi() {
        val api  = retrofit.create(ApiService::class.java)
        api.getAllProvinsi().enqueue(object : Callback<ResponseProvinsi>{
            override fun onFailure(call: Call<ResponseProvinsi>, t: Throwable) {
                progress_bar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<ResponseProvinsi>,
                response: Response<ResponseProvinsi>
            ) {
                if (response.isSuccessful){
                    rv_provinsi.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        progress_bar.visibility = View.GONE
                        adapterProvinsi = AdapterProvinsi(
                            response.body()!!.data as ArrayList<DataItem>
                        ){}
                        adapter = adapterProvinsi
                    }
                } else {
                    progress_bar?.visibility = View.GONE

                }
            }

        })
    }








}