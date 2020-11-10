package com.ami.covidindonesiaupdate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ami.covidindonesiaupdate.R
import com.ami.covidindonesiaupdate.model.DataItem
import kotlinx.android.synthetic.main.list_kota.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterProvinsi(
    private val provinsi: ArrayList<DataItem>, private val clickList: (DataItem) -> Unit) :
        RecyclerView.Adapter<ProvinsiViewHolder>(), Filterable{

    var provinsiList = ArrayList<DataItem>()

    init {
        provinsiList = provinsi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinsiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_kota, parent,false)
        return ProvinsiViewHolder (view)
    }

    override fun getItemCount(): Int {
        return provinsiList.size
    }

    override fun onBindViewHolder(holder: ProvinsiViewHolder, position: Int) {
        holder.bind(provinsiList[position], clickList)
    }


    override fun getFilter(): Filter {
        return object : Filter(){

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                provinsiList = if (charSearch.isEmpty()){
                    provinsi
                } else {
                    val resultList = ArrayList<DataItem>()
                    for (row in provinsi){
                        val search = row.provinsi?.toLowerCase(Locale.ROOT) ?: ""
                        if (search.contains(charSearch.toLowerCase(Locale.ROOT))){
                            resultList.add(row)

                        }
                    }
                    resultList
                }
//                val filterResult = FilterListener()
//                filterResult.values = provinsiList
//                return filterResult

                val filterResult = FilterResults ()
                filterResult.values = provinsi
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, result: FilterResults?) {
                provinsiList = result?.values as ArrayList<DataItem>
                notifyDataSetChanged()
            }
        }
    }
}


class ProvinsiViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    fun bind(provinsi: DataItem, clickList: (DataItem) -> Unit){

        val provinsi_name: TextView = itemView.tvProvinsi
        val provinsi_kasus: TextView = itemView.tvTotalKena
        val provinsi_pulih: TextView = itemView.tvTotalSembuh
        val provinsi_mati: TextView = itemView.tvTotalMati

        val formatter: NumberFormat = DecimalFormat("#,###")


        provinsi_name.tvProvinsi.text = provinsi.provinsi
        provinsi_kasus.tvTotalKena.text = formatter.format(provinsi.kasusPosi?.toDouble())
        provinsi_pulih.tvTotalSembuh.text = formatter.format(provinsi.kasusSemb?.toDouble())
        provinsi_mati.tvTotalMati.text = formatter.format(provinsi.kasusMeni?.toDouble())



    }


}