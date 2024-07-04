package com.wly.mvvm.model


import com.wly.mvvm.bean.TranslateStatus
import com.wly.mvvm.urils.OkHttpUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class NewTranslateModel{

    /**
     * model层获取数据
     */
    fun getTranslateData(): Flow<TranslateStatus> {
        return flow {
            val response = OkHttpUtils.mTranslateService.getTranslateByGet().execute()
            if (response.isSuccessful){
                val result = response.body()?.string()?: "null"
                emit(TranslateStatus.Success(result))
            }else{
                val message = response.message()
                emit(TranslateStatus.Failure(message))
            }
        }.catch {
            emit(TranslateStatus.Failure("${it.message}"))
        }.flowOn(Dispatchers.IO)
    }

}
