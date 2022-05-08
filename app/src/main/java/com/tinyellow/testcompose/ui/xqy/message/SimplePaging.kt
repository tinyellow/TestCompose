package com.tinyellow.testcompose.ui.xqy.message

import androidx.paging.PagingSource
import androidx.paging.PagingState

class  SimplePaging<T : Any>(private val rows:Int, val get:suspend (Int)->(List<T>?)) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? =null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 1
            val datas = get(nextPage)
            if (null != datas){
                LoadResult.Page(
                    data = datas,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (datas.size >= rows) nextPage + 1 else null
                )
            }else{
                throw NullPointerException("加载异常")
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}