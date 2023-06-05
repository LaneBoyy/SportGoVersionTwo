package ru.laneboy.sportgoversiontwo.presentation.participant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.data.network.responses.CompetitionItemResponse
import ru.laneboy.sportgoversiontwo.databinding.MatchItemBinding
import ru.laneboy.sportgoversiontwo.presentation.participant.MatchItemViewHolder
import ru.laneboy.sportgoversiontwo.util.getString

class MatchListAdapter(private val isUser: Boolean) : RecyclerView.Adapter<MatchItemViewHolder>() {

    private var competitionList = listOf<CompetitionItemResponse>()

    var onItemClick: ((competitionId: Int) -> Unit)? = null
    var onButtonClick: ((competitionId: Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchItemViewHolder {
        val binding =
            MatchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MatchItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchItemViewHolder, position: Int) {
        val item = competitionList[position]
        val binding = holder.binding
        with(binding) {
            tvCompetitionName.text = item.competitionName
            tvCompetitionDescription.text = item.competitionDescription
            tvSportType.text = item.sportType
            tvCompetitionDate.text = String.format(
                binding.root.context.getString(R.string.competition_date),
                item.competitionDate
            )
            if (isUser) {
                btnRequest.text = getString(R.string.put_request)
            } else {
                btnRequest.text = getString(R.string.watch_requests)
            }
            root.setOnClickListener {
                onItemClick?.invoke(item.competitionId)
            }
            btnRequest.setOnClickListener {
                onButtonClick?.invoke(item.competitionId)
            }
        }
    }

    override fun getItemCount(): Int {
        return competitionList.size
    }

    fun setList(list: List<CompetitionItemResponse>) {
        competitionList = list
        notifyDataSetChanged()
    }
}

