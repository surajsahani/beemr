package com.martialcoder.mathongo.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.martialcoder.mathongo.R
import com.martialcoder.mathongo.databinding.LayoutScoreCardBinding
import com.martial.beemr.models.ScoreCardModel
import com.martial.beemr.ui.base.MainActivity

import java.lang.reflect.Method


class TestScoreListAdapter(
    private val context : MainActivity,
    private val dataList : ArrayList<ScoreCardModel.TestScores>
) : RecyclerView.Adapter<TestScoreListAdapter.TestScoreViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestScoreViewHolder {
        val binding = LayoutScoreCardBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return TestScoreViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TestScoreViewHolder, position: Int) {
        with(holder){
            binding.apply {
                val count = position + 1
                serialCount.text = count.toString()
                testName.text = dataList[position].testName
                testSeriesName.text = dataList[position].testSeries
                var fullScore = 0
                var totalScore = 0
                if(dataList[position].score !=null){
                    if(dataList[position].score!!.Physics != null){
                        totalScore += dataList[position].score!!.Physics!!
                        fullScore += 100
                        physicsScore.text = "${dataList[position].score!!.Physics}/100"
                    }else{
                        physicsScore.text = "NA"
                    }
                    if(dataList[position].score!!.Chemistry!=null){
                        totalScore += dataList[position].score!!.Chemistry!!
                        fullScore += 100
                        chemistryScore.text = "${dataList[position].score!!.Chemistry}/100"
                    }else{
                        chemistryScore.text = "NA"
                    }

                    if(dataList[position].score!!.Mathematics!=null){
                        totalScore += dataList[position].score!!.Mathematics!!
                        fullScore += 100
                        mathsScore.text = "${dataList[position].score!!.Mathematics}/100"
                    }else{
                        mathsScore.text = "NA"
                    }
                }
                testDate.text = dataList[position].testLocalDate
                totalScoreText.text = "${totalScore}/${fullScore}"

                options.setOnClickListener {
                    popUpMenu(options,dataList[position],position)
                }
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    class TestScoreViewHolder(val binding : LayoutScoreCardBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("RestrictedApi")
    private fun popUpMenu(view : View, scoreCard : ScoreCardModel.TestScores, scorePosition : Int){
        val menu = PopupMenu(view.context,view)
        //menu.setForceShowIcon(true)
        menu.inflate(R.menu.score_card_options)
        menu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.edit -> {
                    context.showToast("Edit Clicked")
                    context.updateScore(scoreCard)
                }
                R.id.delete-> {
                    context.showToast("Delete Clicked")
                    context.deleteScoreCard(scoreCard._id,scorePosition)
                }
            }
            true
        })

        // show icons on popup menu
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            menu.setForceShowIcon(true)
        }else{
            try {
                val fields = menu.javaClass.declaredFields
                for (field in fields) {
                    if ("mPopup" == field.name) {
                        field.isAccessible = true
                        val menuPopupHelper = field[menu]
                        val classPopupHelper =
                            Class.forName(menuPopupHelper.javaClass.name)
                        val setForceIcons: Method = classPopupHelper.getMethod(
                            "setForceShowIcon",
                            Boolean::class.javaPrimitiveType
                        )
                        setForceIcons.invoke(menuPopupHelper, true)
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        menu.show()
    }
}