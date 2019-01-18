package com.zgkjd.socketbase

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import com.zgkjd.baseui.mvp.normal.BaseMvpActivity
import com.zgkjd.kjdsdk.bean.response.DevTypeStaInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainContract.Present>(),MainContract.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): MainContract.Present {
        return MainContract.Present()
    }

    override fun initData() {
        rv_main.layoutManager = LinearLayoutManager(this)
        getPresenter().getAreaDevInfo()
    }

    override fun initEvent() {
    }


    override fun setRoomAdapterData(data: List<RoomHead>) {
        rv_main.adapter = RoomAdapter(data)
        (rv_main.adapter as RoomAdapter).setOnItemClickListener { adapter, view, position ->
            getPresenter().sendCMD(adapter.data[position] as RoomHead.DevInfo)
        }
    }

    override fun updateDevStaUi(info: DevTypeStaInfo) {
        if (info.dev_id.isEmpty()) return
        val roomAdapter = rv_main.adapter as RoomAdapter
        val i = roomAdapter.mDevIdMap[info.dev_id]
        if (i != null && i > 0){
            val multiItemEntity = roomAdapter.data[i]
            val devInfo = multiItemEntity as RoomHead.DevInfo
            devInfo.typeSta = info
            roomAdapter.notifyItemChanged(i)
        }
    }

}
