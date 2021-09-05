package com.saksham.hiltimplementation.repository

import com.saksham.hiltimplementation.model.Blog
import com.saksham.hiltimplementation.network.BlogRetrofit
import com.saksham.hiltimplementation.network.NetworkMapper
import com.saksham.hiltimplementation.room.BlogDao
import com.saksham.hiltimplementation.room.CacheMapper
import com.saksham.hiltimplementation.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(500)
        try {
            val networkBlogs = blogRetrofit.getData()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBLogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBLogs)))
        }
        catch (e:Exception){
            emit(DataState.Error(e))
        }
    }
}