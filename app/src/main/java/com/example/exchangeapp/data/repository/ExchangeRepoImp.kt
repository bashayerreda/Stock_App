package com.example.exchangeapp.data.repository

import com.example.exchangeapp.data.local.ExchangeDao
import com.example.exchangeapp.data.local.ExchangeDataBase
import com.example.exchangeapp.data.mapper.toCompanyListing
import com.example.exchangeapp.data.mapper.toCompanyListingEntity
import com.example.exchangeapp.data.remote.ExchangApi
import com.example.exchangeapp.data.vcs.CompanyListVcsParser
import com.example.exchangeapp.domain.model.CompanyListing
import com.example.exchangeapp.domain.repository.ExchangeRepository
import com.example.exchangeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepoImp @Inject constructor(private val api : ExchangApi ,  private val dao: ExchangeDao,private val vcsParser: CompanyListVcsParser<CompanyListing>) : ExchangeRepository {
    override suspend fun getData(
        returnDataFromAPi: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
      return flow {
          emit(Resource.Loading(true))
          val returnDataFromDataBase = dao.searchOnCompaniesList(query)
          emit(Resource.Success(
              returnDataFromDataBase.map {
                  it.toCompanyListing()
              }
          ))

          val checkDataBaseIfEmpty = returnDataFromDataBase.isEmpty() && query.isBlank()
          val returnDataFromDbAfterCheck = !checkDataBaseIfEmpty && !returnDataFromAPi
          if (returnDataFromDbAfterCheck){
              emit(Resource.Loading(false))
              return@flow
          }
          val requestDataFromApi =  try {
             val dataFromApi =  api.getAllLists()
              vcsParser.parse(dataFromApi.byteStream())

          }catch (e: IOException){
              e.printStackTrace()
              emit(Resource.Error(e.message.toString()))
              null

          }catch (e : HttpException){
              e.printStackTrace()
              emit(Resource.Error(e.message.toString()))
              null
          }

          requestDataFromApi?.let { lists ->
              dao.deleteCompaniesList()
              dao.insertCompaniesLists(
                  lists.map {
                      it.toCompanyListingEntity()
                  }
              )
              emit(Resource.Success(
                  data = dao.searchOnCompaniesList("")
                      .map { it.toCompanyListing() }
              ))
              emit(Resource.Loading(false))


          }

      }
    }
}