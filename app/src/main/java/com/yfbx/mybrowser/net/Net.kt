package com.yuxiaor.base.net

import com.google.gson.Gson
import com.google.gson.stream.MalformedJsonException
import com.yfbx.mybrowser.util.toast
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

/**
 * @Author Edward
 * @Date 2019/5/24 0024
 * @Description:
 */
fun CoroutineScope.network(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
    return launch(NetErrorHandler, start, block)
}

fun Job.onError(onError: (code: String, message: String) -> Unit): Job {
    invokeOnCompletion { throwable ->
        throwable?.let {
            onError.invoke(NetErrorHandler.code, NetErrorHandler.msg)
        }
    }
    return this
}

/**
 * Retrofit with:
 * Header
 * Interceptor
 * Gson Converter
 */
val NET = Retrofit.Builder()
        .baseUrl("")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

/**
 * 简单下载器
 * @return ResponseBody
 */
suspend fun download(url: String) = suspendCancellableCoroutine<ResponseBody> {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(url).get().build()
            val response = OkHttpClient().newCall(request).execute()
            val body = response.body() ?: throw Exception("下载失败")
            if (response.isSuccessful) {
                it.resume(body)
            } else {
                throw  HttpException(Response.error<Any>(response.code(), body))
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            toast("下载失败")
        }
    }
}

/**
 * 简单下载器 文件
 * @return File
 */
suspend fun downloadFile(url: String, targetDir: File) = suspendCancellableCoroutine<File> { ctx ->
    GlobalScope.launch(Dispatchers.IO) {
        val fileName = url.substring(url.lastIndexOf("/") + 1)
        val file = File(targetDir, fileName)
        val bytes = download(url).bytes()
        val outputStream = FileOutputStream(file)
        outputStream.write(bytes)
        outputStream.flush()
        outputStream.close()
        ctx.resume(file)
    }
}

/**
 * 全局统一网络请求错误处理
 */
object NetErrorHandler : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    var code = ""
    var msg = ""

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        exception.printStackTrace()
        code = "0"
        when (exception) {
            is KotlinNullPointerException -> msg = ""
            is HttpException -> responseError(exception)
            is UnknownHostException -> msg = "未知的主机地址！"
            is SocketTimeoutException -> msg = "网络连接超时！"
            is ConnectException -> msg = "连接服务器失败！"
            is MalformedJsonException -> msg = "当前为测试环境，请连接内部网络"
            else -> msg = exception.message ?: "未知错误！"
        }
        toast(msg)
    }

    private fun responseError(exception: HttpException) {
        val response = exception.response()
        when (response?.code()) {
            400, 401 -> parse(response.errorBody()?.string())
            500, 502 -> {
                code = "${response.code()}"
                msg = "服务器正在升级，请稍后再试"
            }
            else -> {
                code = "${response?.code()}"
                msg = exception.message()
            }
        }
    }

    private fun parse(json: String?) {
        try {
            val map = Gson().fromJson(json, Map::class.java)
            code = map["errorCode"] as? String ?: ""
            msg = map["message"] as? String ?: ""
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}
