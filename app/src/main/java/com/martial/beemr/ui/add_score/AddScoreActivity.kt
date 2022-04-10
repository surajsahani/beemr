package com.martial.beemr.ui.add_score

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.martialcoder.mathongo.databinding.ActivityAddScoreBinding
import com.martial.beemr.models.AddScoreModel
import com.martial.beemr.models.PatchScoreModel
import com.martial.beemr.utils.AppPreference
import com.martial.beemr.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddScoreActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddScoreBinding
    private val viewModel : AddScoreActivityViewModel by viewModels()

    private val testSeriesList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null){
            fillData()
        }else{
            fetchTestSeries()
        }

        binding.saveScore.setOnClickListener {
            val extras = intent.extras
            if(extras!=null && extras.containsKey("update")){
                val testId = intent.extras!!.getString("test_id")!!
                updateScore(testId,patchScoreModel())
            }else{
                val scoreCard = makeScoreCard()
                addScore(scoreCard)
            }
        }
    }

    private fun fetchTestSeries(){
        viewModel.fetchTestSeries().observe(this){res->
            when(res.status){
                Status.LOADING -> {
                    showToast("Fetching Test Series")
                }
                Status.SUCCESS -> {
                    showToast("Fetching Test Series Success")
                    testSeriesList.addAll(res.data!!.testSeries)
                    val arrayAdapter = ArrayAdapter(
                        this,android.R.layout.simple_spinner_item,testSeriesList
                    )

                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.testSeriesSpinner.adapter = arrayAdapter
                }
                Status.ERROR -> {
                    showToast("Fetching Test Series Failed")
                }
            }
        }
    }

    private fun addScore(scoreCard : AddScoreModel){
        viewModel.addScoreCards(scoreCard).observe(this){res->
            when(res.status){
                Status.LOADING -> {
                    showToast("Adding Score Card")
                }
                Status.SUCCESS -> {
                    showToast("Adding Score Card Successful")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                Status.ERROR -> {
                    showToast("Adding Score Card Failed")
                }
            }
        }
    }

    private fun updateScore(scoreId : String, scoreCard : PatchScoreModel){
        viewModel.updateScoreCards(scoreId,scoreCard).observe(this){res->
            when(res.status){
                Status.LOADING -> {
                    showToast("Updating Score Card")
                }
                Status.SUCCESS -> {
                    showToast("Updating Score Card Successful")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                Status.ERROR -> {
                    showToast("Updating Score Card Failed")
                }
            }
        }
    }

    private fun makeScoreCard() : AddScoreModel {
        val testSeries = binding.testSeriesSpinner.selectedItem.toString()
        val testName = binding.etTestName.text.toString()
        val testTakenOn = binding.etTakenOn.text.toString()
        val scoreModel = makeScoreModel()

        return AddScoreModel(
            AppPreference.fetchSharedPrefString(this, AppPreference.USER_EMAIL)!!,
            testSeries,testName,testTakenOn, AddScoreModel.Score(
            scoreModel.Physics,scoreModel.Chemistry,scoreModel.Mathematics
            ))
    }

    private fun makeScoreModel() : AddScoreModel.Score{
        var physics : Int? = null
        if(binding.physics.isChecked){
            physics = binding.etPhysicsScore.text.toString().toInt()
        }

        var chemistry : Int? = null
        if(binding.chemistry.isChecked){
            chemistry = binding.etChemistryScore.text.toString().toInt()
        }

        var maths : Int? = null
        if(binding.maths.isChecked){
            maths = binding.etMathsScore.text.toString().toInt()
        }
        return AddScoreModel.Score(physics,chemistry,maths)
    }

    private fun patchScoreModel() : PatchScoreModel {
        val scores = makeScoreModel()
        val patch = PatchScoreModel(scores)
        return patch
    }

    private fun showToast(msg : String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    private fun fillData(){
        binding.apply {
            etTestName.setText(intent.getStringExtra("test_name"))
            val testSeriesName = intent.getStringExtra("test_series")
            val arrayAdapter = ArrayAdapter(this@AddScoreActivity,android.R.layout.simple_spinner_item,arrayOf(testSeriesName!!))
            testSeriesSpinner.adapter = arrayAdapter
            etTakenOn.setText(intent.getStringExtra("test_date"))
            etPhysicsScore.setText(intent.getStringExtra("test_physics_score"))
            etChemistryScore.setText(intent.getStringExtra("test_chemistry_score"))
            etMathsScore.setText(intent.getStringExtra("test_mathematics_score"))
        }
    }
}