package ru.laneboy.sportgoversiontwo.presentation.organizer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.data.network.responses.CompetitionItemResponse
import ru.laneboy.sportgoversiontwo.databinding.MatchItemForOrganizerBinding

class MatchListAdapter : RecyclerView.Adapter<MatchItemForOrganizerViewHolder>() {

    private var competitionList = listOf<CompetitionItemResponse>()
    private val onButtonClick: ((id: Int) -> Unit)? = null
    private val onItemClick: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchItemForOrganizerViewHolder {
        val binding =
            MatchItemForOrganizerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchItemForOrganizerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchItemForOrganizerViewHolder, position: Int) {
        val item = competitionList[position]
        val binding = holder.binding
        binding.tvCompetitionName.text = item.competitionName
        binding.tvCompetitionDescription.text = item.competitionDescription
        binding.tvSportType.text = item.sportType
        binding.tvCompetitionDate.text = String.format(
            binding.root.context.getString(R.string.competition_date),
            item.competitionDate
        )
        binding.root.setOnClickListener {
            onItemClick?.invoke(item.competitionId)
        }
        binding.appCompatButton.setOnClickListener {
            onButtonClick?.invoke(item.competitionId)
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