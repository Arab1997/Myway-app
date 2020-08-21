package com.snatap.myway.network

import com.snatap.myway.network.models.*
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @POST("auth/login")
    fun login(@Body body: LoginRequest): Single<Token>

    @POST("auth/register")
    fun register(@Body body: RegisterRequest): Single<RegisterRequest>

    @POST("auth/logout")
    fun logout(): Single<MessageResp>

    @POST("auth/forgot_password")
    fun forgotPassword(): Single<MessageResp>

    @GET("news_items")
    fun getNews(): Single<NewsResp>

    @GET("news_items/{id}")
    fun getNewsDetail(@Path("id") id: Int): Single<NewsDetailResp>

    @GET("news_items/{id}/comments")
    fun getComments(@Path("id") id: Int): Single<CommentsResp>

    @POST("news_items/{id}/comment")
    @FormUrlEncoded
    fun addComment(
        @Path("id") id: Int,
        @Field("text") comment: String
    ): Single<AddCommentResp>

    @POST("news_items/{id}/bookmark")
    fun addBookmark(@Path("id") id: Int): Single<Bookmark>

    @POST("news_items/{id}/like")
    fun addLike(@Path("id") id: Int): Single<Like>

    @GET("user/chat/messages")
    fun getChats(): Single<ChatsResp>

    @POST("user/chat/send_message")
    @FormUrlEncoded
    fun sendMessageChats(@Field("text") text: String): Single<SendMessageChatResp>

    @POST("user/chat/read")
    @FormUrlEncoded
    fun readMessageChats(@Field("chat_items_ids") chat_items_ids: Int): Single<ReadMessageChatResp>

    @GET("user/info")
    fun getUser(): Single<UserResp>

    @GET("user/notifications")
    fun getUserNotifications(): Single<NotificationsResp>

    @GET("user/achievements")
    fun getUserAchievements(): Single<AchievementsResp>

    @GET("streams")
    fun getStreams(): Single<StreamsResp>

    @GET("streams/{id}/messages")
    fun getStreamMessages(@Path("id") id: Int): Single<StreamMessagesResp>

    @POST("streams/{id}/send_message")
    @FormUrlEncoded
    fun sendStreamMessages(
        @Path("id") id: Int, @Field("text") text: String
    ): Single<StreamMessageResp>

    @POST("streams/{id}/join")
    fun joinStream(@Path("id") id: Int): Single<SuccessResp>

    @GET("store_items/categories")
    fun getStoreCategories(): Single<StoreCategories>

    @GET("store_items")
    fun getStoreItems(): Single<StoreResp>

    @GET("user/lesson_day_items")
    fun getLessonsDay(): Single<LessonsDayResp>

}

data class ErrorResp(val message: String, val errors: Any? = null)

data class MessageResp(val message: String)

data class Token(
    val access_token: String,
    val token_type: String,
    val unique_id: String
)

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val phone: String
)

data class LoginRequest(
    val phone: String,
    val password: String
)