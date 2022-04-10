package com.martial.beemr.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.martialcoder.mathongo.adapters.TestScoreListAdapter
import com.martialcoder.mathongo.databinding.ActivityMainBinding
import com.martial.beemr.models.ScoreCardModel
import com.martial.beemr.ui.add_score.AddScoreActivity
import com.martial.beemr.utils.AppPreference
import com.martial.beemr.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainActivityViewModel by viewModels()

    private lateinit var scoreCardRVAdapter : TestScoreListAdapter
    private lateinit var scoreCardRVLayoutManager : LinearLayoutManager

    private val scoreCardList = ArrayList<ScoreCardModel.TestScores>()

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var pageNo : Int = 0
    private val scoreCardCount : Int = 5
    private var loading : Boolean = true
    private var endScrolling : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                fetchScoreCard(true)
            }
        }

        scoreCardRVAdapter = TestScoreListAdapter(this,scoreCardList)
        scoreCardRVLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        binding.apply {
            scoreCardRecyclerView.adapter = scoreCardRVAdapter
            scoreCardRecyclerView.layoutManager = scoreCardRVLayoutManager

            scoreCardRecyclerView

            addScore.setOnClickListener{
                addScoreIntent()
            }
            
            scoreCardRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(!scoreCardRecyclerView.canScrollVertically(1)){
                        if(!loading && !endScrolling){
                            pageNo += 1
                            fetchScoreCard(false)
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
        fetchScoreCard(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchScoreCard(refresh : Boolean){
        if(refresh){
            pageNo = 0
            endScrolling = false
            scoreCardList.clear()
        }

        val email = AppPreference.fetchSharedPrefString(this, AppPreference.USER_EMAIL)!!
        viewModel.fetchScoreCards(email,pageNo.toString(),scoreCardCount.toString()).observe(this){ res->
            when(res.status){
                Status.LOADING -> {
                    loading = true
                    showToast("Loading Score Cards")
                }
                Status.SUCCESS -> {

                    if(res.data!!.testScores.size < scoreCardCount) endScrolling = true

                    if (!res.data!!.testScores.isNullOrEmpty()){
                        scoreCardList.addAll(res.data.testScores)
                        scoreCardRVAdapter.notifyDataSetChanged()
                    }
                    loading = false
                    showToast("Score Card Loading Completed")
                }
                Status.ERROR -> {
                    showToast("OOPS!! Something went wrong")
                }
            }
        }
    }

    fun deleteScoreCard(scoreCardId : String,position : Int){
        viewModel.deleteScoreCards(scoreCardId).observe(this){ res->
            when(res.status){
                Status.LOADING -> {
                    showToast("Deleting Score Cards")
                }
                Status.SUCCESS -> {
                    showToast("Score Card Deletion Completed")
                    scoreCardList.removeAt(position)
                    scoreCardRVAdapter.notifyItemRemoved(position)
                }
                Status.ERROR -> {
                    showToast("OOPS!! Something went wrong")
                }
            }
        }
    }

    fun showToast(msg : String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    private fun addScoreIntent(){
        val intent = Intent(this, AddScoreActivity::class.java)
        resultLauncher.launch(intent)
    }

    fun updateScore(data : ScoreCardModel.TestScores){
        val intent = Intent(this, AddScoreActivity::class.java)
        intent.putExtra("update","required")
        intent.putExtra("test_id",data._id)
        intent.putExtra("test_series",data.testSeries)
        intent.putExtra("test_name",data.testName)
        intent.putExtra("test_date",data.testLocalDate)
        if(data.score!=null){
            if(data.score.Physics!=null){
                intent.putExtra("test_physics_score",data.score.Physics.toString())
            }else{
                intent.putExtra("test_physics_score","NA")
            }
            if(data.score.Chemistry!=null){
                intent.putExtra("test_chemistry_score",data.score.Chemistry.toString())
            }else{
                intent.putExtra("test_chemistry_score","NA")
            }
            if(data.score.Mathematics!=null){
                intent.putExtra("test_mathematics_score",data.score.Mathematics.toString())
            }else{
                intent.putExtra("test_mathematics_score","NA")
            }
        }else{
            intent.putExtra("test_physics_score","NA")
            intent.putExtra("test_chemistry_score","NA")
            intent.putExtra("test_mathematics_score","NA")
        }

        resultLauncher.launch(intent)
    }
}