/*
 * Copyright 2016-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eblans.shekel

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface ShekelApi {
    @GET("/login")
    fun login(
            @Query("username") username: String,
            @Query("password") password: String
    ): Observable<LoginResponse>

    @GET("/purchase/{id}")
    fun findPurchase(
            @Path("id") id: Int
    ): Observable<PurchaseEntity>
}

private fun buildApiFactory() =
        Retrofit.Builder().apply {
            baseUrl("http://shekel.eblans.org")
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        }.build()

fun buildShekelApi() = buildApiFactory().create(ShekelApi::class.java)

data class LoginResponse(val access_token: String, val result: Int)
data class PurchaseEntity(
        val id: Int,
        val name: String,
        val cost: Int,
        val owner_id: Int,
        val party_id: Int,
        val shared_ids: List<Int>
)