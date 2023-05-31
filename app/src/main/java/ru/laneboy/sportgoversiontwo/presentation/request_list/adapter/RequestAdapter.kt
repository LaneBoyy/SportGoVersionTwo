package ru.laneboy.sportgoversiontwo.presentation.request_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.RvRequestItemBinding
import ru.laneboy.sportgoversiontwo.domain.RequestItem
import ru.laneboy.sportgoversiontwo.domain.RequestItem.RequestStatus.*
import ru.laneboy.sportgoversiontwo.util.getString
import ru.laneboy.sportgoversiontwo.util.gone
import ru.laneboy.sportgoversiontwo.util.visible

class RequestAdapter(private val isUserAccount: Boolean) :
    RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    var onRequestClick: ((Int, Boolean)-> Unit)? = null

    class RequestViewHolder(val binding: RvRequestItemBinding) : ViewHolder(binding.root)

    private var list: List<RequestItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = RvRequestItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val item = list[position]
        val binding = holder.binding
        with(binding) {
            tvTeamName.text = item.teamName
            tvSportType.text = item.selectedCompetition
            tvCaptain.text = item.teamCaptain
            tvStatus.text = when (item.requestStatus) {
                ACCEPTED -> getString(R.string.status_accept)
                REJECTED -> getString(R.string.status_reject)
                NOT_DETERMINED -> getString(R.string.status_in_progress)
                UNKNOWN -> getString(R.string.status_unknown)
            }
            if (isUserAccount) {
                btnAccept.gone()
                btnReject.gone()
                btnReject.setOnClickListener(null)
                btnAccept.setOnClickListener(null)
            } else {
                btnAccept.visible()
                btnReject.visible()
                btnAccept.setOnClickListener {
                    onRequestClick?.invoke(item.requestId, true)
                }
                btnReject.setOnClickListener {
                    onRequestClick?.invoke(item.requestId, false)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(newList: List<RequestItem>) {
        list = newList
        notifyDataSetChanged()
    }

}