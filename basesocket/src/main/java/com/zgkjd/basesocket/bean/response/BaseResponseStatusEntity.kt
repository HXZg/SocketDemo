package com.zgkjd.basesocket.bean.response

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import org.json.JSONObject
import java.io.StringReader

/**
 * Created by xian_zhong on 2019/1/10.
 * des : 基类响应体
 *
 *
 */
open class BaseResponseStatusEntity {

    val app : String = ""
    val co : String = ""
    val api : String = ""
    val retcode : String = ""
    val retmsg : String = ""

    var dataJson = ""
    get() {
        val jsonObject = JSONObject(rawJsonData)
        val optJSONObject = jsonObject.optJSONObject("data")
        return optJSONObject.toString()
    }

    //json 源数据
    var rawJsonData : String = ""

    companion object {
        const val SUCCESS = "0"
    }

    fun getRawData() : JSONObject? {
        return if (rawJsonData.isNotEmpty())
            JSONObject(rawJsonData)
        else null
    }

    //json 数据：取该实体类没有的属性
    fun getRawAttribute(key : String) : String?{
        return getRawData()?.optString(key)
    }

    /**
     * 接口请求是否成功
     */
    fun isSuccess() = retcode == SUCCESS
}