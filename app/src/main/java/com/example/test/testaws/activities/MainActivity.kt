package com.example.test.testaws.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.test.testaws.R
import com.example.test.testaws.models.SubwayLine
import com.example.test.testaws.models.TestPostRequest
import com.example.test.testaws.services.TestAWSAPI
import com.example.test.testaws.services.TestAWSAPIProvider.getRegionAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var postCount: Int = 0
    private var getCount: Int = 0
    private var selectedRegion = "东京"
    private val postDurations: MutableList<Long> = mutableListOf()
    private val getDurations: MutableList<Long> = mutableListOf()
    private var regions = listOf("东京", "新加坡", "加州")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener({ v ->
            postCount = 0
            getCount = 0
            postDurations.clear()
            getDurations.clear()
            result1.text = ""
            result2.text = ""
            v.isEnabled = false
            progressBar.visibility = View.VISIBLE

            val testAWSAPI = getRegionAPI(selectedRegion)
            testAWSAPI ?: return@setOnClickListener
            testPost(testAWSAPI)
            testGet(testAWSAPI)
            testPost(testAWSAPI)
            testGet(testAWSAPI)
            testPost(testAWSAPI)
            testGet(testAWSAPI)
            testPost(testAWSAPI)
            testGet(testAWSAPI)
            testPost(testAWSAPI)
            testGet(testAWSAPI)

            v.isEnabled = true
        })

        val dataAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regions)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        selectedRegion = parent.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("关闭", { _, _ ->
                    finish()
                })
                .show()
    }

    private fun testPost(testAWSAPI: TestAWSAPI) {
        val subwayLine = SubwayLine("A", 1, 2, "MetroTech")
        val testPostRequest = TestPostRequest("Manhattan", 100, true, listOf(subwayLine))
        val startTimePost = System.currentTimeMillis()

        testAWSAPI.testPost(testPostRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onError = {
                            val locMessage = it.message ?: return@subscribeBy
                            showAlert("testPost failed", locMessage)
                            postCount++
                        },
                        onSuccess = {
                            if (it.message == "borough is Manhattan price is 100 includeFee is true subwayTime is 1") {
                                val duration = System.currentTimeMillis() - startTimePost
                                postDurations.add(duration)
                            } else {
                                showAlert("testPost failed", it.message)
                            }
                            postCount++
                            checkAndShowResults()
                        }
                )
    }

    private fun testGet(testAWSAPI: TestAWSAPI) {
        val startTimePost = System.currentTimeMillis()

        testAWSAPI.testGet("jiechao", 22, true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onError = {
                            val locMessage = it.message ?: return@subscribeBy
                            showAlert("testGet failed", locMessage)
                            getCount++
                        },
                        onSuccess = {
                            if (it.message == "name is jiechao id is 22 correct is true") {
                                val duration = System.currentTimeMillis() - startTimePost
                                getDurations.add(duration)
                            } else {
                                showAlert("testGet failed", it.message)
                            }
                            getCount++
                            checkAndShowResults()
                        }
                )
    }

    private fun checkAndShowResults() {
        if (postCount == 5 && getCount == 5) {
            progressBar.visibility = View.GONE
            result1.text = "testPost succeeded: ${postDurations.average()}"
            result2.text = "testGet succeeded: ${getDurations.average()}"
        }
    }
}
