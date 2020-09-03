package com.snatap.myway.network.models

data class EventsResp(
    val events: List<Event>,
    val success: Boolean
)

data class EventResp(
    val event: Event,
    val success: Boolean
)

data class Event(
    val city: City,
    val city_id: Int,
    val comments_count: Int,
    val created_at: String,
    val description: String,
    val end_date: String,
    val id: Int,
    val lat: String,
    val likes_count: Int,
    val is_liked: Boolean,
    val shares_count: Int,
    val is_shared: Boolean,
    val lng: String,
    val location: String,
    val max_tickets: Int,
    val participants_count: Int,
    val gallery_items_count: Int,
    val phone: String,
    val photo: String,
    val price: Int,
    val short_description: String,
    val start_date: String,
    val tags: List<Tag>,
    val sponsors: List<Sponsor>,
    val comments: List<Comment>,
    val gallery_items: List<Gallery>,
    val tickets_count: Int,
    val title: String,
    val updated_at: String
)

data class City(
    val created_at: String,
    val id: Int,
    val title: String,
    val updated_at: String
)

data class Sponsor(
    val created_at: String,
    val id: Int,
    val photo: String,
    val pivot: PivotSponsor,
    val title: String,
    val updated_at: String
)

data class Gallery(
    val created_at: String,
    val event_id: Int,
    val id: Int,
    val photo: String?,
    val updated_at: String,
    val video: String?
)

data class PivotSponsor(
    val event_id: Int,
    val event_sponsor_id: Int
)