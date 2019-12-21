package com.example.architecturestudy.ui.kin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturestudy.R
import com.example.architecturestudy.data.model.KinData
import com.example.architecturestudy.data.repository.NaverSearchRepositoryImpl
import com.example.architecturestudy.network.Api
import com.example.architecturestudy.network.ApiClient
import kotlinx.android.synthetic.main.fragment_kin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KinFragment : Fragment() {

    val restClient: Api = ApiClient.getRetrofitService(Api::class.java)

    private lateinit var kinAdapter: KinAdapter

    private val naverSearchRepository by lazy { NaverSearchRepositoryImpl() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_kin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        kinAdapter =  KinAdapter()

        recycleview.apply {
            adapter = kinAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            )
        }

        btn_search.setOnClickListener {
            if(input_text != null) {
                val edit = edit_text.text.toString()
                searchKinList(edit)
            }
        }
    }

    private fun searchKinList(keyword : String) {

        naverSearchRepository.getKin(
            keyword = keyword,
            success = { responseKin -> kinAdapter.update(responseKin.items) },
            fail = { e -> error(message = e.toString()) }
        )

//        restClient.requestKin(keyword).enqueue(object : Callback<KinData> {
//
//            override fun onFailure(call: Call<KinData>, t: Throwable) {
//                error(message = t.toString())
//            }
//
//            override fun onResponse(call: Call<KinData>, response: Response<KinData>) {
//                if(response.isSuccessful) {
//                    response.body()?.items?.let {
//                        kinListAdapter()
//                        kinAdapter.update(it)
//                    }
//                }
//            }
//        })
    }

    private fun kinListAdapter() {
        recycleview.apply {
            adapter = kinAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            )
        }
    }
}
