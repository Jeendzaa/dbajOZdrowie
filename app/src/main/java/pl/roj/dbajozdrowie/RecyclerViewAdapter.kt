package pl.roj.dbajozdrowie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.roj.dbajozdrowie.db.Med

class MedAdapter(private var meds: List<Med>) : RecyclerView.Adapter<MedAdapter.MedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MedViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedViewHolder, position: Int) {
        val med = meds[position]
        holder.medName.text = med.medName
        holder.medQuantity.text = med.medCount.toString()
        holder.medUnit.text = med.medUnit
    }

    override fun getItemCount(): Int = meds.size

    // Metoda do zaktualizowania danych w adapterze
    fun updateData(newMeds: List<Med>) {
        meds = newMeds
        notifyDataSetChanged()  // Powiadomienie adaptera o zmianie danych
    }

    inner class MedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medName: TextView = itemView.findViewById(R.id.medName)
        val medQuantity: TextView = itemView.findViewById(R.id.medCount)
        val medUnit: TextView = itemView.findViewById(R.id.medUnit)
    }
}
